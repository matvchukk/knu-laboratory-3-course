#pragma once

#include <iostream>
#include <random>
#include <time.h>
#include <math.h>
#include <mpi.h>

static int processes = 0;
static int rank = 0;
static MPI_Comm ColComm;
static MPI_Comm RowComm;

namespace MatrixOperations {
	double* RandomDataInitialization(int Size) {
		int MatrixSize = Size * Size;
		double* Matrix = new double[MatrixSize];
		for (int i = 0; i < MatrixSize; i++) {
			Matrix[i] = (rand() % (5));
		}
		return Matrix;
	}


	void simpleMultiplication(double* pAMatrix, double*& pBMatrix, double*& pCMatrix, int Size) {
		for (int i = 0; i < Size; i++) {
			for (int j = 0; j < Size; j++) {
				for (int k = 0; k < Size; k++) {
					pCMatrix[i * Size + j] += pAMatrix[i * Size + k] * pBMatrix[k * Size + j];
				}
			}
		}
	}

	void TransposeMatrix(double* matrix, int size) {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				double t = matrix[i * size + j];
				matrix[i * size + j] = matrix[j * size + i];
				matrix[j * size + i] = t;
			}
		}
	}
}