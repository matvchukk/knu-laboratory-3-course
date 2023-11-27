package str;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.util.function.Function;

public class InterpolationExample extends JFrame {

    public InterpolationExample(String title, Function<Double, Double> function, double a, double b, int n) {
        super(title);

        XYSeries exactSeries = createFunctionSeries("Exact Function", function, a, b, 1000);
        XYSeries lagrangeSeries = createInterpolationSeries("Lagrange's Polynomial", this::lagrangeInterpolation, function, a, b, n, 1000);
        XYSeries newtonSeries = createInterpolationSeries("Newton's Polynomial", this::newtonInterpolation, function, a, b, n, 1000);
        XYSeries splineSeries = createInterpolationSeries("Cubic Splines", this::cubicSplineInterpolation, function, a, b, n, 1000);

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(exactSeries);
        //dataset.addSeries(lagrangeSeries);
        dataset.addSeries(newtonSeries);
        dataset.addSeries(splineSeries);

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Interpolation",
                "x",
                "f(x)",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 600));
        setContentPane(chartPanel);
    }




    private XYSeries createFunctionSeries(String name, Function<Double, Double> function, double a, double b, int numPoints) {
        XYSeries series = new XYSeries(name);
        double step = (b - a) / numPoints;
        for (double x = a; x <= b; x += step) {
            series.add(x, function.apply(x));
        }
        return series;
    }

    private XYSeries createInterpolationSeries(String name, InterpolationMethod method, Function<Double, Double> function, double a, double b, int n, int numPoints) {
        XYSeries series = new XYSeries(name);
        double[] xVec = getPoints(a, b, n);
        double[] yVec = new double[n];
        for (int i = 0; i < n; i++) {
            yVec[i] = function.apply(xVec[i]);
        }

        double step = (b - a) / numPoints;
        for (double x = a; x <= b; x += step) {
            series.add(x, method.interpolate(xVec, yVec, x));
        }
        return series;
    }

    private static double[] getPoints(double a, double b, int num) {
        double[] points = new double[num];
        double step = (b - a) / (num - 1);
        for (int i = 0; i < num; i++) {
            points[i] = a + i * step;
        }
        return points;
    }

    private double lagrangeInterpolation(double[] vectorX, double[] vectorY, double x) {
        double result = 0.0;
        int n = vectorX.length;

        for (int i = 0; i < n; i++) {
            double lagrangeTerm = vectorY[i];
            for (int j = 0; j < n; j++) {
                if (j != i) {
                    lagrangeTerm *= (x - vectorX[j]) / (vectorX[i] - vectorX[j]);
                }
            }
            result += lagrangeTerm;
        }

        return result;
    }
    private static String buildLagrangePolynomial(double[] vectorX, double[] vectorY) {
        int n = vectorX.length;
        StringBuilder polynomial = new StringBuilder("");
        for (int i = 0; i < n; i++) {
            StringBuilder term = new StringBuilder();
            double coefficient = vectorY[i];
            if (coefficient != 0) {
                term.append(coefficient < 0 ? "-" : "+");
                term.append(Math.abs(coefficient));
            }

            for (int j = 0; j < n; j++) {
                if (j != i) {
                    term.append(String.format(" * (x - %.2f) / (%.2f - %.2f)", vectorX[j], vectorX[i], vectorX[j]));

                }
            }
            polynomial.append(term.toString()).append("\n");
        }

        return polynomial.toString();

    }


    private double newtonInterpolation(double[] vectorX, double[] vectorY, double x) {
        int n = vectorX.length;
        double result = vectorY[0];

        double[] newtonCoefficients = calculateNewtonCoefficients(vectorX, vectorY);
        for (int i = 1; i < n; i++) {
            double newtonTerm = 1;
            for (int j = 0; j < i; j++) {
                newtonTerm *= (x - vectorX[j]);
            }
            result += newtonTerm * newtonCoefficients[i];
        }

        return result;
    }
    private static String buildNewtonPolynomial(double[] vectorX, double[] vectorY) {
        int n = vectorX.length;
        StringBuilder polynomial = new StringBuilder("");
        polynomial.append(vectorY[0]).append("\n");
        //double result = vectorY[0];
        double[] newtonCoefficients = calculateNewtonCoefficients(vectorX, vectorY);
        for (int i = 1; i < n; i++) {
            StringBuilder term = new StringBuilder();
            double newtonTerm = 1;
            if (newtonCoefficients[i] != 0) {
                term.append(newtonCoefficients[i] < 0 ? "-" : "+");
                term.append(Math.abs(newtonCoefficients[i]));
            }
            for (int j = 0; j < i; j++){
                term.append(String.format(" * (x - %.2f)", vectorX[j]));
                //term.append(String.format(" * (x - %.2f) / (%.2f - %.2f)", vectorX[j], vectorX[i], vectorX[j]));
            }
            polynomial.append(term.toString()).append("\n");
        }
        return polynomial.toString();
    }

    private static double[] calculateNewtonCoefficients(double[] vectorX, double[] vectorY) {
        int n = vectorX.length;
        double[] table = new double[n];

        for (int i = 0; i < n; i++) {
            table[i] = vectorY[i];
        }

        for (int i = 1; i < n; i++) {
            for (int j = n - 1; j >= i; j--) {
                table[j] = (table[j] - table[j - 1]) / (vectorX[j] - vectorX[j - i]);
            }
        }

        return table;
    }

    private double cubicSplineInterpolation(double[] vectorX, double[] vectorY, double x) {
        int n = vectorX.length;
        double[][] coefficients = getSplineCoefficients(vectorX, vectorY);

        for (int i = 0; i < n - 1; i++) {
            if (vectorX[i] <= x && x <= vectorX[i + 1]) {
                double xInterval = x - vectorX[i];
                return coefficients[i][0] + coefficients[i][1] * xInterval + coefficients[i][2] * Math.pow(xInterval, 2) + coefficients[i][3] * Math.pow(xInterval, 3);
            }
        }

        return 0.0;
    }

    private double[][] getSplineCoefficients(double[] vectorX, double[] vectorY) {
        int n = vectorX.length;
        double[] h = new double[n - 1];
        for (int i = 0; i < n - 1; i++) {
            h[i] = vectorX[i + 1] - vectorX[i];
        }

        double[] b = new double[n];
        double[] u = new double[n];
        double[] v = new double[n];

        for (int i = 1; i < n - 1; i++) {
            b[i] = 6 * ((vectorY[i + 1] - vectorY[i]) / h[i] - (vectorY[i] - vectorY[i - 1]) / h[i - 1]);
        }

        for (int i = 1; i < n - 1; i++) {
            double t = h[i] / (h[i - 1] + h[i]);
            u[i] = 2 * (t * u[i - 1] + 2);
            v[i] = t * (b[i] - v[i - 1]);
        }

        double[] z = new double[n];
        for (int i = n - 2; i > 0; i--) {
            z[i] = (v[i] - h[i] * z[i + 1]) / u[i];
        }

        double[][] coefficients = new double[n - 1][4];
        for (int i = 0; i < n - 1; i++) {
            double a = vectorY[i];
            double bb = (vectorY[i + 1] - vectorY[i]) / h[i] - h[i] * (z[i + 1] + 2 * z[i]) / 6;
            double c = z[i] / 2;
            double d = (z[i + 1] - z[i]) / (6 * h[i]);

            coefficients[i][0] = a;
            coefficients[i][1] = bb;
            coefficients[i][2] = c;
            coefficients[i][3] = d;
        }

        return coefficients;
    }

    public static void main(String[] args) {
        double a = -1;
        double b = 1;
        int n = 3;

        SwingUtilities.invokeLater(() -> {
            InterpolationExample example = new InterpolationExample("Interpolation Plots", x -> Math.pow(3, x), a, b, n);
            example.setSize(800, 600);
            example.setLocationRelativeTo(null);
            example.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            example.setVisible(true);

            // Lagrange Interpolation
            double[] xVec = getPoints(a, b, n);
            double[] yVec = new double[n];
            for (int i = 0; i < n; i++) {
                yVec[i] = Math.pow(3, xVec[i]);
            }
            System.out.println("Lagrange Interpolation Polynomial L(x):");
            System.out.println(buildLagrangePolynomial(xVec, yVec));
            System.out.println("L(" + 0.5 + ") = " + example.lagrangeInterpolation(xVec, yVec, 0.5));

            for (double x : xVec) {
                System.out.println("L(" + x + ") = " + example.lagrangeInterpolation(xVec, yVec, x));
            }

            // Newton Interpolation
            System.out.println("\nNewton Interpolation Polynomial N(x):");
            System.out.println(buildNewtonPolynomial(xVec, yVec));
            System.out.println("N(" + 0.5 + ") = " + example.newtonInterpolation(xVec, yVec, 0.5));

            for (double x : xVec) {
                System.out.println("N(" + x + ") = " + example.newtonInterpolation(xVec, yVec, x));
            }

            // Cubic Spline Interpolation
            System.out.println("\nCubic Spline Interpolation:");
            System.out.println("Spline(" + 0.5 + ") = " + example.cubicSplineInterpolation(xVec, yVec, 0.5));
            for (double x : xVec) {
                System.out.println("Spline(" + x + ") = " + example.cubicSplineInterpolation(xVec, yVec, x));
            }
        });

//        double a = -2;
//        double b = 2;
//        int n = 3;
//
//        SwingUtilities.invokeLater(() -> {
//            InterpolationExample example = new InterpolationExample("Interpolation Plots", x -> 2 * Math.sin(3 * x), a, b, n);
//            example.setSize(800, 600);
//            example.setLocationRelativeTo(null);
//            example.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//            example.setVisible(true);
//
//            // Lagrange Interpolation
//            double[] xVec = getPoints(a, b, n);
//            double[] yVec = new double[n];
//            for (int i = 0; i < n; i++) {
//                yVec[i] = 2 * Math.sin(3 * xVec[i]);
//            }
//            System.out.println("Lagrange Interpolation Polynomial L(x):");
//            System.out.println(buildLagrangePolynomial(xVec, yVec));
//            System.out.println("L(" + 0.5 + ") = " + example.lagrangeInterpolation(xVec, yVec, 0.5));
//
//            for (double x : xVec) {
//                System.out.println("L(" + x + ") = " + example.lagrangeInterpolation(xVec, yVec, x));
//            }
//
//            // Newton Interpolation
//            System.out.println("\nNewton Interpolation Polynomial N(x):");
//            System.out.println(buildNewtonPolynomial(xVec, yVec));
//            System.out.println("N(" + 0.5 + ") = " + example.newtonInterpolation(xVec, yVec, 0.5));
//
//            for (double x : xVec) {
//                System.out.println("N(" + x + ") = " + example.newtonInterpolation(xVec, yVec, x));
//            }
//
//            // Cubic Spline Interpolation
//            System.out.println("\nCubic Spline Interpolation:");
//            System.out.println("Spline(" + 0.5 + ") = " + example.cubicSplineInterpolation(xVec, yVec, 0.5));
//            for (double x : xVec) {
//                System.out.println("Spline(" + x + ") = " + example.cubicSplineInterpolation(xVec, yVec, x));
//            }
//        });

//        SwingUtilities.invokeLater(() -> {
//            InterpolationExample example = new InterpolationExample("Interpolation Plots", x -> x * x - 3 * Math.sin(3 * x), a, b, n);
//            example.setSize(800, 600);
//            example.setLocationRelativeTo(null);
//            example.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//            example.setVisible(true);
//
//            // Lagrange Interpolation
//            double[] xVec = getPoints(a, b, n);
//            double[] yVec = new double[n];
//            for (int i = 0; i < n; i++) {
//                yVec[i] = 3 * Math.sin(3 * xVec[i]);
//            }
//            System.out.println("Lagrange Interpolation Polynomial L(x):");
//            System.out.println(buildLagrangePolynomial(xVec, yVec));
//            System.out.println("L(" + 0.5 + ") = " + example.lagrangeInterpolation(xVec, yVec, 0.5));
//
//            for (double x : xVec) {
//                System.out.println("L(" + x + ") = " + example.lagrangeInterpolation(xVec, yVec, x));
//            }
//
//            // Newton Interpolation
//            System.out.println("\nNewton Interpolation Polynomial N(x):");
//            System.out.println(buildNewtonPolynomial(xVec, yVec));
//            System.out.println("N(" + 0.5 + ") = " + example.newtonInterpolation(xVec, yVec, 0.5));
//
//            for (double x : xVec) {
//                System.out.println("N(" + x + ") = " + example.newtonInterpolation(xVec, yVec, x));
//            }
//
//            // Cubic Spline Interpolation
//            System.out.println("\nCubic Spline Interpolation:");
//            System.out.println("Spline(" + 0.5 + ") = " + example.cubicSplineInterpolation(xVec, yVec, 0.5));
//            for (double x : xVec) {
//                System.out.println("Spline(" + x + ") = " + example.cubicSplineInterpolation(xVec, yVec, x));
//            }
//        });
    }
}

interface InterpolationMethod {
    double interpolate(double[] xVec, double[] yVec, double x);
}