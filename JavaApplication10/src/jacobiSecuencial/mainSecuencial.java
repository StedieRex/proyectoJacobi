package jacobiSecuencial;

import java.text.DecimalFormat;

public class mainSecuencial {
    
    public static double[][] invertirMatrizDiagonal(double[][] matrizDiagonal) {
        int n = matrizDiagonal.length;
        double[][] matrizInversa = new double[n][n];

        for (int i = 0; i < n; i++) {
            // Verificar que el elemento diagonal no sea cero antes de invertir
            if (matrizDiagonal[i][i] != 0) {
                matrizInversa[i][i] = 1 / matrizDiagonal[i][i];
            } else {
                // Si el elemento diagonal es cero, la matriz no es invertible
                System.out.println("La matriz diagonal no es invertible (tiene un elemento diagonal igual a cero).");
                return null;
            }
        }

        return matrizInversa;
    }
    
    public static double[][] getMatrizDiagonal(double[][] matriz){
        
        int n = matriz.length;
        double[][] matrizDiagonal = new double[n][n];
        
        for(int i = 0; i < n; i++){
            matrizDiagonal[i][i] = matriz[i][i];
        }
        
        return matrizDiagonal;
    }
        
    public static double[][] getSuperiorInvertido(double[][] matriz){
        
        int n = matriz.length;
        double[][] superiorInvertido = new double[n][n];
        
        for(int i = 0; i < n; i++){
            for( int j = i; j < n; j++){
                
                if (i != j){
                    superiorInvertido[i][j] = (-1 * matriz[i][j]);
                }
                
            }
        }
        
        return superiorInvertido;
    }
    
    public static double[][] getInferiorInvertido(double[][] matriz){
        
        int n = matriz.length;
        double[][] inferiorInvertido = new double[n][n];
        
        for(int i = 0; i < n; i++){
            for( int j = 0; j <= i; j++){
                
                if (i!= j){
                    inferiorInvertido[i][j] = (-1 * matriz[i][j]);
                }
                
            }
        }
        
        return inferiorInvertido;
    }
    
    public static double[][] multMatriz(double[][] matrizA, double[] matrizB) {
        int n = matrizA.length;

        double[][] resultado = new double[n][1];

        for (int i = 0; i < n; i++) {
            for (int k = 0; k < n; k++) {
                resultado[i][0] += matrizA[i][k] * matrizB[k];
            }
        }

        return resultado;
    }
    
    public static double[][] multMatricesNxM(double[][] matrizA, double[][] matrizB) {
        int filasA = matrizA.length;
        int columnasA = matrizA[0].length;
        int filasB = matrizB.length;
        int columnasB = matrizB[0].length;

        // Verificar si las matrices son compatibles para la multiplicación
        if (columnasA != filasB) {
            System.out.println("No se pueden multiplicar las matrices: las dimensiones no son compatibles.");
            return null;
        }

        // Crear la matriz resultado
        double[][] matrizResultado = new double[filasA][columnasB];

        // Calcular la multiplicación
        for (int i = 0; i < filasA; i++) {
            for (int j = 0; j < columnasB; j++) {
                for (int k = 0; k < columnasA; k++) {
                    matrizResultado[i][j] += matrizA[i][k] * matrizB[k][j];
                }
            }
        }

        return matrizResultado;
    }
    
    public static double[][] sumarMatrices(double[][] matrizA, double[][] matrizB) {
        int filas = matrizA.length;
        int columnas = matrizA[0].length;

        // Verificar si las matrices tienen el mismo tamaño
        if (filas != matrizB.length || columnas != matrizB[0].length) {
            System.out.println("No se pueden sumar las matrices: tienen tamaños diferentes.");
            return null;
        }

        // Crear la matriz resultado
        double[][] matrizResultado = new double[filas][columnas];

        // Realizar la suma elemento por elemento
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                matrizResultado[i][j] = matrizA[i][j] + matrizB[i][j];
            }
        }

        return matrizResultado;
    }
    
    public static double[][] restarMatrices(double[][] matrizA, double[][] matrizB) {
        int filas = matrizA.length;
        int columnas = matrizA[0].length;

        // Verificar si las matrices tienen el mismo tamaño
        if (filas != matrizB.length || columnas != matrizB[0].length) {
            System.out.println("No se pueden sumar las matrices: tienen tamaños diferentes.");
            return null;
        }

        // Crear la matriz resultado
        double[][] matrizResultado = new double[filas][columnas];

        // Realizar la suma elemento por elemento
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                matrizResultado[i][j] = matrizA[i][j] - matrizB[i][j];
            }
        }

        return matrizResultado;
    }
    
    public static float normaMaxima(double[][] vector) {
        float max = (float) Math.abs(vector[0][0]); // Inicializar el máximo con el primer elemento del vector

        // Recorrer el vector para encontrar el valor absoluto máximo
        for (int i = 0; i < vector.length; i++) {
            for (int j = 0; j < vector[0].length; j++) {
                float absValue = (float) Math.abs(vector[i][j]);
                if (absValue > max) {
                    max = absValue;
                }
            }
        }

        return max;
    }
    
    private static void imprimirMatriz(double[][] matriz) {
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[0].length; j++) {
                System.out.print(matriz[i][j] + " ");
            }
            System.out.println("");
        }
    }
    
    public static void main(String[] args) {
        
        //obtener tiempo de incio
        long startTime = System.nanoTime();
        
        /*matriz de prueba */

        //dame una matriz de 8x8
        double[][] prueba = {
            {1,1,-1,1,-1},
            {2,2,1,-1,1},
            {3,1,-3,-2,3},
            {4,1,-1,4,-5},
            {16,-1,1,-1,-1}
        };
        double b[] = {2,4,8,16,32};/* */
        /*double[][] prueba = {
            {4,-1,1,0,0,0},
            {2,5,2,0,0,0},
            {1,2,4,1,0,0},
            {0,1,2,4,-1,0},
            {0,0,1,2,4,-1},
            {0,0,0,1,2,4}
        };
        double b[] = {8,3,11,3,8,3};/* */
        /*double [][] prueba = {
            {4,-1,1,0,0},
            {2,5,2,0,0},
            {1,2,4,1,0},
            {0,1,2,4,-1},
            {0,0,1,2,4}
        };
        double b[] = {8,3,11,3,8};/* */
        /*matriz de prueba2 */
        /*double[][] prueba = {
            {3,-1,-1},
            {-1,3,1},
            {2,1,4},
        };
        double b[] = {1,3,7};/* */
        
        /*//ejemplo del libro
        double[][] prueba = {
            {10,-1,2,0},
            {-1,11,-1,3},
            {2,-1,10,-1},
            {0, 3, -1, 8}
        };
        
        double b[] = {6, 25, -11, 15};/**/
        
        //Definimos variables
        float epsilon = (float) 0.001;
        
        System.out.println("Epsilon: " + epsilon);
        
        //Obtenemos D invertida en base a la matriz de prueba
        double[][] D = invertirMatrizDiagonal(prueba);
        
        //Obtenemos L y U
        double[][] L = getInferiorInvertido(prueba);
        double[][] U = getSuperiorInvertido(prueba);
        
        double[][] Dxb = multMatriz(D, b);
        
        double[][] LmasU = sumarMatrices(L, U);
        
        double[][] DxLmasU = multMatricesNxM(D, LmasU);
        
        double[][] x = new double[prueba.length][1];//aproximacion inicial
        
        //iniciamos las iteraciones:
        
        float error = 0;
        int epoca = 0;
        int maxIter = 20;
        
        do{
            //System.out.println("");
            //System.out.println("Epoca: " + (epoca+1));
            double[][] xAnterior = x;
            
            //actualizamos x
            x = sumarMatrices(Dxb, multMatricesNxM(DxLmasU, x));
            
            /*
            imprimirMatriz(x);
            System.err.println("");
            imprimirMatriz(xAnterior);/* */
            /*
            System.out.println("Norma maxima x(" + epoca +"): " + normaMaxima(xAnterior));
            System.out.println("Norma maxima x(" + (epoca+1) +"): " + normaMaxima(x));
            System.out.println("||x("+ (epoca+1) + ") - x("+ epoca +")|| = " + normaMaxima(restarMatrices(x,xAnterior)));
            */
            /*System.err.println(normaMaxima(restarMatrices(x,xAnterior)));
            System.err.println("/");
            System.err.println(normaMaxima(x));*/
            error = normaMaxima(restarMatrices(x,xAnterior))/normaMaxima(x);
            
            epoca++;
            
            /*
            System.out.println("Error:" + error);
            System.out.println();
            System.out.println(error<epsilon);*/
            
            if(epoca == maxIter){
                break;
            }
                    
        }while(error > epsilon);
        
        DecimalFormat formato = new DecimalFormat("#.########");
        
        //Imprimimos los resultados
        System.out.println("Iteracion #" + epoca);
        System.out.println("Error:" + formato.format(error));
        System.out.println("\nResultados aproximados:");
        imprimirMatriz(x);
        System.out.println();
        
        /*System.out.println(b.length);
        
        imprimirMatriz(D);
        System.out.println();
        imprimirMatriz(L);
        System.out.println();
        imprimirMatriz(U);
        System.out.println();
        imprimirMatriz(multMatriz(D, b));*/
        
        //--------------------obteniendo tiempo de ejecucion---------------------
        // Obtener el tiempo de finalización
        long endTime = System.nanoTime();
        // Calcular la duración de la ejecución en milisegundos
        long duration = (endTime - startTime) / 1000000; // Convertir a milisegundos
        // Imprimir la duración de la ejecución
        System.out.println("Tiempo de ejecucion, secuencial: " + duration + " milisegundos");
        
    }
    
}
