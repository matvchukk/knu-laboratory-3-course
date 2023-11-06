package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class PageRank {

    public static double[][] computeTransitionMatrix(double[][] inputMatrix) {
        int n = inputMatrix.length;
        double[][] transitionMatrix = new double[n][n];
        for (int j = 0; j < n; j++) {
            double sum = 0;
            for (int i = 0; i < n; i++) {
                sum += inputMatrix[i][j];
            }
            if (sum != 0) {
                for (int i = 0; i < n; i++) {
                    transitionMatrix[i][j] = inputMatrix[i][j] / sum;
                }
            }
        }
        return transitionMatrix;
    }



    public static double[] computePageRank(double[][] transitionMatrix, double precision) {
        int n = transitionMatrix.length;
        double[] rankVector = new double[n];
        for (int i = 0; i < n; i++) {
            rankVector[i] = 1.0;
        }
        int iterations = 1;
        double maxEigenValue = 0;

        while (true) {
            double[] newRankVector = multiplyMatrixVector(transitionMatrix, rankVector);
            double maxElement = findMaxElement(newRankVector);
            for (int i = 0; i < n; i++) {
                newRankVector[i] /= maxElement;
            }
            double normDiff = computeNormDifference(rankVector, newRankVector);
            if (normDiff < precision) {
                break;
            }
            rankVector = newRankVector;
            iterations++;
            maxEigenValue = maxElement;
        }
        System.out.println("Number of iterations: " + iterations);
        return rankVector;
    }

    private static double findMaxElement(double[] array) {
        double max = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }
        return max;
    }


    public static double computeNorm(double[] vector) {
        double sumSquares = 0;
        for (double val : vector) {
            sumSquares += val * val;
        }
        return Math.sqrt(sumSquares);
    }

    public static double computeNormDifference(double[] vector1, double[] vector2) {
        int n = vector1.length;
        double sumSquaresDiff = 0;
        for (int i = 0; i < n; i++) {
            double diff = vector1[i] - vector2[i];
            sumSquaresDiff += diff * diff;
        }
        return Math.sqrt(sumSquaresDiff);
    }

    public static double[] multiplyMatrixVector(double[][] matrix, double[] vector) {
        int n = matrix.length;
        double[] result = new double[n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                result[i] += matrix[i][j] * vector[j];
            }
        }
        return result;
    }


    public static boolean checkColumnSum(double[][] matrix) {
        int n = matrix.length;
        for (int j = 0; j < n; j++) {
            double sum = 0;
            for (int i = 0; i < n; i++) {
                sum += matrix[i][j];
            }
            if (Math.abs(sum - 1.0) > 1e-9) {
                return false;
            }
        }
        return true;
    }

    public static void printMatrix(double[][] matrix) {
        for (double[] row : matrix) {
            for (double val : row) {
                System.out.print(val + " ");
            }
            System.out.println();
        }
    }

    public static void printVector(double[] vector) {
        for (double val : vector) {
            System.out.print(val + " ");
        }
        System.out.println();
    }


    public static double[][] readMatrixFromFile(String fileName) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(fileName));
        int n = 0;
        while (scanner.hasNextLine()) {
            scanner.nextLine();
            n++;
        }
        scanner = new Scanner(new File(fileName));
        double[][] matrix = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = scanner.nextDouble();
            }
        }
        return matrix;
    }


    public static void main(String[] args) {
        try {
            double[][] inputMatrix = readMatrixFromFile("C:\\Users\\Анастасія\\Desktop\\Роботи 3 курс\\Чисельні методи\\lab3\\graph2.txt");
            System.out.println("Input Matrix:");
            printMatrix(inputMatrix);

            double[][] transitionMatrix = computeTransitionMatrix(inputMatrix);
            System.out.println("\nTransition Matrix:");
            printMatrix(transitionMatrix);

            double precision = 0.00000000000000001;
            double[] rankVector = computePageRank(transitionMatrix, precision);

            System.out.println("\nRank Vector:");
            printVector(rankVector);
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found.");
        }
    }


}
