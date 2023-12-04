package tridiagonal.parallel;
import main.lin_alg.Linalg;
import main.tridiagonal.TridiagonalMatrix;
import org.junit.jupiter.api.Test;
import main.tridiagonal.parallel.LESolveTridiagonalParallel;

import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.*;

class LESolveTridiagonalParallelTest {

    private double eps = 1e-3;
    private double eps2 = eps * eps;

    @Test
    void solveExample() {
        double[] diagonal = new double[] {6, 7, 8, 9, 10, 11};
        double[] underDiagonal = new double[] {2, 2, 2, 2, 2};
        double[] upDiagonal = new double[] {3, 3, 3, 3, 3};
        double[] rightCoef = new double[] {12, 25, 40, 57, 76, 76};
        TridiagonalMatrix tridiagonalMatrix = new TridiagonalMatrix(underDiagonal, diagonal, upDiagonal, rightCoef);
        double[] solution = new double[] {1, 2, 3, 4, 5, 6};
        LESolveTridiagonalParallel solver = new LESolveTridiagonalParallel(3);
        double[] algoSolutionArr =  solver.solve(tridiagonalMatrix);
        assertTrue(Linalg.vectorEqNormSq(algoSolutionArr, solution) < eps2);
    }

    @Test
    void solveGeneratedDataTest() {
        double randomOrigin = -100;
        double randomBound = 100;
        int maxNumThreads = 10;
        int maxSize = 100;
        int iterationsForSize = 10;
        for (int numThreads = 1; numThreads <= maxNumThreads; numThreads++) {
            for (int size = numThreads; size <= maxSize; size += numThreads) {
                double[] solution = new double[size];
                for (int i = 0; i < size; i++) {
                    solution[i] = i + 1;
                }

                for (int it = 0; it < iterationsForSize; it++) {
                    LESolveTridiagonalParallel solver = new LESolveTridiagonalParallel(numThreads);
                    double[] algoSolutionArr = solver.solve(
                            Linalg.randomDiagonalDominantTridiagonalProblem(size, randomOrigin, randomBound, solution));
                    double error = Linalg.vectorEqNormSq(algoSolutionArr, solution);
                    assertTrue(error < eps2);
                }
            }
        }
    }
}