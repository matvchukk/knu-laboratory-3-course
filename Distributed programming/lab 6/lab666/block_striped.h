#pragma once

#include <iostream>
#include <mpi.h>
#include "matrix_operations.h"

namespace BlockStriped {

	int Coords;

	void Decomposition(double* LineB, int Length, int Size) {
		MPI_Status Status;
		int NextCoord = Coords + 1;
		if (NextCoord == processes) NextCoord = 0;
		int PrevCoord = Coords - 1;
		if (PrevCoord == -1) PrevCoord = processes - 1;
		MPI_Sendrecv_replace(LineB, Length * Size, MPI_DOUBLE, NextCoord, 0, PrevCoord, 0, ColComm, &Status);
	}

	void BlockStripedMultiplication(double* LineA, double* LineB, double* Result, int Size, int Length, int iter) {
		int l = Length * iter;
		for (int i = 0; i < Length; i++) {
			for (int j = 0; j < Length; j++) {
				for (int k = 0; k < Size; k++) {
					Result[Length * iter] += LineA[i * Size + k] * LineB[j * Size + k];
				}
				l++;
			}
			l += Size - Length;
		}
	}

	void ComputeLine(double* LineA, double* LineB, double* LineC, int Length, int Size) {
		int iter = Coords;
		for (int i = 0; i < processes; i++) {
			BlockStripedMultiplication(LineA, LineB, LineC, Size, Length, iter);
			iter++;
			if (iter == processes) {
				iter = 0;
			}
			Decomposition(LineB, Length, Size);
		}
	}

	void ProcessInitialization(double*& pAMatrix, double*& pBMatrix, double*& pCMatrix, double*& pAblock, double*& pBblock, double*& pCblock, int& Size, int& BlockSize) {
		BlockSize = Size / processes;

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

	void  DataDistribution(double* A, double* B, double* LineAd, double* LineBd, int Size, int Length) {
		if (rank == 0) {
			MatrixOperations::TransposeMatrix(B, Size);
		}
		MPI_Scatter(&(A[Size * Length * Coords]), Length * Size, MPI_DOUBLE, LineAd, Length * Size, MPI_DOUBLE, 0, RowComm);
		MPI_Scatter(&(B[Size * Length * Coords]), Length * Size, MPI_DOUBLE, LineBd, Length * Size, MPI_DOUBLE, 0, RowComm);

	}

	void ResultCollection(double* C, double* LineCd, int Length, int Size) {
		MPI_Gather(LineCd, Length * Size, MPI_DOUBLE, C, Length * Size, MPI_DOUBLE, 0, RowComm);
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

	void runBlockStripedAlg(int argc, char* argv[], int Size) {
		double* pAMatrix;
		double* pBMatrix;
		double* pCMatrix;
		int Length;
		double* pAline;
		double* pBline;
		double* pCline;

		double Start, Finish, Duration;

		Coords = rank;
		if (Size % processes != 0) {
			if (rank == 0) {
				std::cout << "\n Invalid number of proesses for multiplication of matrices of these Sizes";
			}
			return;
		}
		ProcessInitialization(pAMatrix, pBMatrix, pCMatrix, pAline, pBline, pCline, Size, Length);
		MPI_Comm_split(MPI_COMM_WORLD, rank / Length, rank, &RowComm);
		MPI_Comm_split(MPI_COMM_WORLD, rank / Length, rank, &ColComm);

		Start = MPI_Wtime();
		DataDistribution(pAMatrix, pBMatrix, pAline, pBline, Size, Length);
		ComputeLine(pAline, pBline, pCline, Length, Size);
		Finish = MPI_Wtime();
		ResultCollection(pCMatrix, pCline, Length, Size);
		Deconstruct(pAMatrix, pBMatrix, pCMatrix, pAline, pBline, pCline);
		Duration = Finish - Start;
		if (rank == 0) {
			std::cout << "Block-Striped " << Duration << std::endl;
		}
	}
}