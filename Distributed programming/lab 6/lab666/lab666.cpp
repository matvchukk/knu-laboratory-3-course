// lab666.cpp : This file contains the 'main' function. Program execution begins and ends there.
//

#include <iostream>
#include <mpi.h>
#include "matrix_operations.h"
#include "block_striped.h"
#include "fox_algorithm.h"
#include "cannon_algorithm.h"


int main(int argc, char** argv)
{
	srand(time(0));
	setvbuf(stdout, 0, _IONBF, 0);
	MPI_Init(&argc, &argv);

	MPI_Comm_rank(MPI_COMM_WORLD, &rank);
	MPI_Comm_size(MPI_COMM_WORLD, &processes);
	//std::cout << processes << std::endl;

	switch (processes) {
	case 1:
		Fox::runFoxAlg(argc, argv, 300);
		Fox::runFoxAlg(argc, argv, 1200);
		Cannon::runCannonAlg(argc, argv, 300);
		Cannon::runCannonAlg(argc, argv, 1200);
		BlockStriped::runBlockStripedAlg(argc, argv, 300);
		BlockStriped::runBlockStripedAlg(argc, argv, 1200);

		break;

	case 4:
		/*Fox::runFoxAlg(argc, argv, 288);
		Fox::runFoxAlg(argc, argv, 1152);
		Cannon::runCannonAlg(argc, argv, 288);
		Cannon::runCannonAlg(argc, argv, 1152);
		BlockStriped::runBlockStripedAlg(argc, argv, 288);*/
		BlockStriped::runBlockStripedAlg(argc, argv, 576);

		break;

	case 9:
		Fox::runFoxAlg(argc, argv, 324);
		Fox::runFoxAlg(argc, argv, 1296);
		Cannon::runCannonAlg(argc, argv, 324);
		Cannon::runCannonAlg(argc, argv, 1296);
		BlockStriped::runBlockStripedAlg(argc, argv, 324);
		BlockStriped::runBlockStripedAlg(argc, argv, 1296);


		break;

	default:
		std::cout << "\n Wrong number of processes";
	}

	MPI_Finalize();
	return 0;
}