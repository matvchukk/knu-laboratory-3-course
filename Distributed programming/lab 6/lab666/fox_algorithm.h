#pragma once

#include <iostream>
#include <mpi.h>
#include "matrix_operations.h"


namespace Fox {

	int GridCoords[2];
	int GridSize;
	MPI_Comm GridComm;
	MPI_Comm ColComm;
	MPI_Comm RowComm;


	void createGridCommunicators() {
		int SizeDim[2];
		int Periodic[2];
		int SubDims[2];

		SizeDim[0] = GridSize;
		SizeDim[1] = GridSize;
		Periodic[0] = 0;
		Periodic[1] = 0;

		MPI_Cart_create(MPI_COMM_WORLD, 2, SizeDim, Periodic, 1, &GridComm);
		MPI_Cart_coords(GridComm, rank, 2, GridCoords);

		SubDims[0] = 0;
		SubDims[1] = 1;
		MPI_Cart_sub(GridComm, SubDims, &RowComm);

		SubDims[0] = 1;
		SubDims[1] = 0;
		MPI_Cart_sub(GridComm, SubDims, &ColComm);
	}

	void ProcessInitialization(double*& pAMatrix, double*& pBMatrix, double*& pCMatrix, double*& pAblock, double*& pBblock, double*& pCblock, double*& pTemporaryAblock, int& Size, int& BlockSize) {
		BlockSize = Size / GridSize;

		pAblock = new double[BlockSize * BlockSize];
		pBblock = new double[BlockSize * BlockSize];
		pCblock = new double[BlockSize * BlockSize];
		pTemporaryAblock = new double[BlockSize * BlockSize];

		if (rank == 0) {
			pAMatrix = new double[Size * Size];
			pBMatrix = new double[Size * Size];
			pCMatrix = new double[Size * Size];
			pAMatrix = MatrixOperations::RandomDataInitialization(Size);
			pBMatrix = MatrixOperations::RandomDataInitialization(Size);
		}
	}

	void DataDistributionMatrices(double* matrix, double* matrixBlock, int Size, int BlockSize) {
		double* Row = new double[BlockSize * Size];
		if (GridCoords[1] == 0) {
			MPI_Scatter(matrix, BlockSize * Size, MPI_DOUBLE, Row, BlockSize * Size, MPI_DOUBLE, 0, ColComm);
		}
		for (int i = 0; i < BlockSize; i++) {
			MPI_Scatter(&Row[i * Size], BlockSize, MPI_DOUBLE, &(matrixBlock[i * BlockSize]), BlockSize, MPI_DOUBLE, 0, RowComm);
		}
		delete[] Row;
	}

	void DataDistribution(double* pAMatrix, double* pBMatrix, double* pAblock, double* pBblock, int Size, int BlockSize) {
		DataDistributionMatrices(pAMatrix, pAblock, Size, BlockSize);
		DataDistributionMatrices(pBMatrix, pBblock, Size, BlockSize);
	}

	void ABlockCommunication(int iter, double* pAblock, double* pMatrixAblock, int BlockSize) {
		int Pivot = (GridCoords[0] + iter) % GridSize;
		if (GridCoords[1] == Pivot) {
			for (int i = 0; i < BlockSize * BlockSize; i++)
				pAblock[i] = pMatrixAblock[i];
		}
		MPI_Bcast(pAblock, BlockSize * BlockSize, MPI_DOUBLE, Pivot, RowComm);
	}

	void BblockCommunication(double* pBblock, int BlockSize) {
		MPI_Status Status;
		int NextProc = GridCoords[0] + 1;
		if (GridCoords[0] == GridSize - 1) NextProc = 0;
		int  PrevProc = GridCoords[0] - 1;
		if (GridCoords[0] == 0)  PrevProc = GridSize - 1;
		MPI_Sendrecv_replace(pBblock, BlockSize * BlockSize, MPI_DOUBLE, NextProc, 0, PrevProc, 0, ColComm, &Status);
	}

	void ParallelResultCalculation(double* pAblock, double* pMatrixAblock,
		double* pBblock, double* pCblock, int BlockSize) {
		for (int i = 0; i < GridSize; i++) {
			ABlockCommunication(i, pAblock, pMatrixAblock, BlockSize);
			MatrixOperations::simpleMultiplication(pAblock, pBblock, pCblock, BlockSize);
			BblockCommunication(pBblock, BlockSize);
		}
	}

	void ResultCollection(double* pCMatrix, double* pCblock, int Size, int BlockSize, int GridCoords[]) {
		double* Result = new double[Size * BlockSize];
		for (int i = 0; i < BlockSize; i++) {
			MPI_Gather(&pCblock[i * BlockSize], BlockSize, MPI_DOUBLE, &Result[i * Size], BlockSize, MPI_DOUBLE, 0, RowComm);
		}
		if (GridCoords[1] == 0) {
			MPI_Gather(Result, BlockSize * Size, MPI_DOUBLE, pCMatrix, BlockSize * Size, MPI_DOUBLE, 0, ColComm);
		}
		delete[] Result;
	}

	void Deconstruct(double*& pAMatrix, double*& pBMatrix, double*& pCMatrix, double* pAblock, double* pBblock, double* pCblock, double* pTemporaryAblock = NULL) {
		if (rank == 0) {
			delete[] pAMatrix;
			delete[] pBMatrix;
			delete[] pCMatrix;
		}
		delete[] pAblock;
		delete[] pBblock;
		delete[] pCblock;
		if (!pTemporaryAblock) {
			delete[] pTemporaryAblock;
		}
	}

	void runFoxAlg(int argc, char* argv[], int Size) {
		double* pAMatrix;
		double* pBMatrix;
		double* pCMatrix;
		int BlockSize;
		double* pAblock;
		double* pBblock;
		double* pCblock;
		double* pMatrixAblock;
		double Start, Finish, Duration;

		GridSize = sqrt((double)processes);
		if (processes != GridSize * GridSize) {
			if (rank == 0) {
				std::cout << "\nNumber of processes must be a perfect square \n";
			}
			return;
		}

		createGridCommunicators();
		ProcessInitialization(pAMatrix, pBMatrix, pCMatrix, pAblock, pBblock, pCblock, pMatrixAblock, Size, BlockSize);
		DataDistribution(pAMatrix, pBMatrix, pMatrixAblock, pBblock, Size, BlockSize);

		Start = MPI_Wtime();
		ParallelResultCalculation(pAblock, pMatrixAblock, pBblock,
			pCblock, BlockSize);
		Finish = MPI_Wtime();
		ResultCollection(pCMatrix, pCblock, Size, BlockSize, GridCoords);
		Deconstruct(pAMatrix, pBMatrix, pCMatrix, pAblock, pBblock, pCblock, pAblock);

		Duration = Finish - Start;
		if (rank == 0)
			std::cout << "Fox " << Duration << std::endl;
	}

}
