package probando;

import java.util.Arrays;

public class JacobiMethod {

    public static double[] jacobi(double[][] A, double[] b, double[] x0, double tol, int maxIter) {
        int n = b.length;
        double[] x = Arrays.copyOf(x0, n);
        double[] xNew = new double[n];
        int iterCount = 0;

        while (iterCount < maxIter) {
            for (int i = 0; i < n; i++) {
                double sigma = 0;
                for (int j = 0; j < n; j++) {
                    if (j != i) {
                        sigma += A[i][j] * x[j];
                    }
                }
                xNew[i] = (b[i] - sigma) / A[i][i];
            }
            if (norm(subtract(xNew, x)) < tol) {
                break;
            }
            x = Arrays.copyOf(xNew, n);
            iterCount++;
        }

        return x;
    }

    public static double norm(double[] array) {
        double sum = 0;
        for (double value : array) {
            sum += value * value;
        }
        return Math.sqrt(sum);
    }

    public static double[] subtract(double[] array1, double[] array2) {
        int n = array1.length;
        double[] result = new double[n];
        for (int i = 0; i < n; i++) {
            result[i] = array1[i] - array2[i];
        }
        return result;
    }

    public static void main(String[] args) {
        double[][] A = {
            {1,1,-1},
            {1,2,2},
            {2,1,-1},
        };
        double b[] = {-1,0,1};/* */
        double[] x0 = new double[b.length];

        double[] sol = jacobi(A, b, x0, 1e-6, 1000);
        System.out.println("Solución: " + Arrays.toString(sol));
    }
}

