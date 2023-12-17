#pragma once

#include <iostream>
#include <mpi.h>
#include "matrix_operations.h"

namespace Cannon {

	int GridCoords[2];
	int GridSize;
	MPI_Comm GridComm;
	MPI_Comm ColComm;
	MPI_Comm RowComm;

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

	void ProcessInitialization(double*& pAMatrix, double*& pBMatrix, double*& pCMatrix, double*& pAblock, double*& pBblock, double*& pCblock, int& Size, int& BlockSize) {
		BlockSize = Size / GridSize;

		pAblock = new double[BlockSize * BlockSize];
		pBblock = new double[BlockSize * BlockSize];
		pCblock = new double[BlockSize * BlockSize];


		if (rank == 0) {
			pAMatrix = new double[Size * Size];
			pBMatrix = new double[Size * Size];
			pCMatrix = new double[Size * Size];
			pAMatrix = MatrixOperations::RandomDataInitialization(Size);
			pBMatrix = MatrixOperations::RandomDataInitialization(Size);
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

	void createGridCommunicators() {
		int DimSize[2];
		int Periodic[2];
		int SubDims[2];

		DimSize[0] = GridSize;
		DimSize[1] = GridSize;
		Periodic[0] = 0;
		Periodic[1] = 0;

		MPI_Cart_create(MPI_COMM_WORLD, 2, DimSize, Periodic, 1, &GridComm);
		MPI_Cart_coords(GridComm, rank, 2, GridCoords);

		SubDims[0] = 0;
		SubDims[1] = 1;
		MPI_Cart_sub(GridComm, SubDims, &RowComm);

		SubDims[0] = 1;
		SubDims[1] = 0;
		MPI_Cart_sub(GridComm, SubDims, &ColComm);
	}

	void ABlockCommunication(double* pAMatrix, int Size, int BlockSize) {
		int NextCoord = GridCoords[1] + 1;
		if (GridCoords[1] == GridSize - 1) NextCoord = 0;
		int PrevCoord = GridCoords[1] - 1;
		if (GridCoords[1] == 0) PrevCoord = GridSize - 1;
		MPI_Status Status;
		MPI_Sendrecv_replace(pAMatrix, BlockSize * BlockSize, MPI_DOUBLE, NextCoord, 0, PrevCoord, 0, RowComm, &Status);
	}

	void BBlockCommunication(double* pBMatrix, int Size, int BlockSize) {
		MPI_Status Status;
		int NextProc = GridCoords[0] + 1;
		if (GridCoords[0] == GridSize - 1) NextProc = 0;
		int PrevProc = GridCoords[0] - 1;
		if (GridCoords[0] == 0) PrevProc = GridSize - 1;
		MPI_Sendrecv_replace(pBMatrix, BlockSize * BlockSize, MPI_DOUBLE, NextProc, 0, PrevProc, 0, ColComm, &Status);
	}

	void ParallelResultCalculation(double* pAMatrix, double* pBMatrix, double* pCMatrix, int Size, int BlockSize) {
		for (int i = 0; i < GridSize; ++i) {
			MatrixOperations::simpleMultiplication(pAMatrix, pBMatrix, pCMatrix, BlockSize);
			ABlockCommunication(pAMatrix, Size, BlockSize);
			BBlockCommunication(pBMatrix, Size, BlockSize);
		}
	}

	void  DataDistributionBlock(double* Matrix, double* Block, int Row, int Col, int Size, int BlockSize) {
		int Position = Col * BlockSize * Size + Row * BlockSize;
		for (int i = 0; i < BlockSize; ++i, Position += Size) {
			MPI_Scatter(&Matrix[Position], BlockSize, MPI_DOUBLE, &(Block[i * BlockSize]), BlockSize, MPI_DOUBLE, 0, GridComm);
		}
	}

	void  DataDistribution(double* pAMatrix, double* pBMatrix, double* pAblock, double* pBblock, int Size, int BlockSize) {
		DataDistributionBlock(pAMatrix, pAblock, GridCoords[0], (GridCoords[0] + GridCoords[1]) % GridSize, Size, BlockSize);
		DataDistributionBlock(pBMatrix, pBblock, (GridCoords[0] + GridCoords[1]) % GridSize, GridCoords[1], Size, BlockSize);
	}

	void runCannonAlg(int argc, char* argv[], int Size) {
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
		ProcessInitialization(pAMatrix, pBMatrix, pCMatrix, pAblock, pBblock, pCblock, Size, BlockSize);
		DataDistribution(pAMatrix, pBMatrix, pAblock, pBblock, Size, BlockSize);

		Start = MPI_Wtime();
		ParallelResultCalculation(pAblock, pBblock, pCblock, Size, BlockSize);
		Finish = MPI_Wtime();
		ResultCollection(pCMatrix, pCblock, Size, BlockSize, GridCoords);
		Deconstruct(pAMatrix, pBMatrix, pCMatrix, pAblock, pBblock, pCblock, pAblock);

		Duration = Finish - Start;
		if (rank == 0)
			std::cout << "Cannon " << Duration << std::endl;
	}
}
