package concurrenteJacobi;

import java.util.Scanner;

public class main {

    public static void imprimirMatriz(int[][] matrix){
        for(int i=0; i<matrix.length; i++){
            for(int j=0; j<matrix.length; j++){
                System.out.print(matrix[i][j]+" ");
            }
            System.out.println();
        }
    }

    public static int[][] transpuestaMatrix(int[][] matrix){
        int n = matrix.length;
        int[][] D = new int[n][n];
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                if(i==j){
                    D[i][j] = matrix[i][j];
                }else{
                    D[i][j] = 0;
                }
            }
        }
        return D;
    }

    // da la matriz triangular inferior con los elementos negativos, sin diagonal-
    public static int[][] trianguloInferiorNegativo(int[][] matrix){
        int n = matrix.length;
        int[][] L = new int[n][n];
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                if(i>j){
                    L[i][j] = -1*matrix[i][j];
                }else{
                    L[i][j] = 0;
                }
            }
        }
        return L;
    }

    //da el triangulo superior de la matriz, sin la diagonal, y con los valores negativos
    public static int[][] trianguloSuperiorNegativo(int[][] matrix){
        int n = matrix.length;
        int[][] U = new int[n][n];
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                if(i<j){
                    U[i][j] = -1*matrix[i][j];
                }else{
                    U[i][j] = 0;
                }
            }
        }
        return U;
    }
    public static void main(String[] args) {
        //obtener tiempo de incio
        long startTime = System.nanoTime();

        //inicio de variables
        int[][] D;
        int[][] L;
        int[][] U;

        //matriz de prueba
        int[][] prueba = {
            {3,-1,-1},
            {-1,3,1},
            {2,1,4},
        };
        
        D=transpuestaMatrix(prueba);
        /*prueba transpuesta*/
        // imiprimirMatriz(D);

        L = trianguloInferiorNegativo(prueba);
        /*prueba triangulo inferior*/
        // imprimirMatriz(L);
        U = trianguloSuperiorNegativo(prueba);
        /*prueba triangulo superior */
        //imprimirMatriz(U);
        
        //referencia de la concurrencia
        // HiloSumaPar hilo1= new HiloSumaPar("par",x0,xn,n);
        // HiloSumaImpar hilo2= new HiloSumaImpar("impar",x0,xn,n);
        
        // hilo1.start();
        // hilo2.start();
        
        // try{
        //     hilo1.join();
        //     hilo2.join();
        // }catch(InterruptedException e){
            
        // }
        // suma = (h/3)*(fx(x0)+fx(xn)+hilo1.return_suma()+hilo2.return_suma());
        // System.out.println("La integral es: "+suma);

        //--------------------obteniendo tiempo de ejecucion---------------------
        // Obtener el tiempo de finalización
        long endTime = System.nanoTime();
        // Calcular la duración de la ejecución en milisegundos
        long duration = (endTime - startTime) / 1000000; // Convertir a milisegundos
        // Imprimir la duración de la ejecución
        System.out.println("Tiempo de ejecución, suma con 2 hilos: " + duration + " milisegundos");
    }
    
}
