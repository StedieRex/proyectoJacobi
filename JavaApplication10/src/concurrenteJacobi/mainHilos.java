package concurrenteJacobi;

import java.util.ArrayList;
import java.util.Scanner;
import concurrenteJacobi.guardandoArreglos;

public class mainHilos {

    public static void imprimirMatriz(double[][] matrix){
        for(int i=0; i<matrix.length; i++){
            for(int j=0; j<matrix.length; j++){
                System.out.print(matrix[i][j]+" ");
            }
            System.out.println();
        }
    }

    public static void imprimirMatriz(int[][] matrix){
        for(int i=0; i<matrix.length; i++){
            for(int j=0; j<matrix.length; j++){
                System.out.print(matrix[i][j]+" ");
            }
            System.out.println();
        }
    }

    public static void imprimirVector(double[] vector){
        for(int i=0; i<vector.length; i++){
            System.out.print(vector[i]+" ");
        }
        System.out.println();
    }

    public static void imprimirVector(int[] vector){
        for(int i=0; i<vector.length; i++){
            System.out.print(vector[i]+" ");
        }
        System.out.println();
    }

    public static int[] transpuestaNegativaMatrix(int[][] matrix){
        int n = matrix.length;
        int[] D = new int[n];
        for(int i=0; i<n; i++){
            D[i] = matrix[i][i];
        }
        return D;
    }

    // da la matriz triangular inferior con los elementos negativos, sin diagonal-
    public static int[][] obteniendoLmasU(int[][] matrix){
        int n = matrix.length;
        int[][] LmasU = new int[n][n];
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                if(i!=j)
                    LmasU[i][j] = -1*matrix[i][j];
                else
                    LmasU[i][j] = 0;
            }
        }
        return LmasU;
    }

    public static void main(String[] args) {
        //obtener tiempo de incio
        long startTime = System.nanoTime();

        //inicio de variables
        int[] D;
        int[][] LmasU;
        int n=100;
        ArrayList <guardandoArreglos> listaArreglos = new ArrayList<guardandoArreglos>();

        //la parte 1 representa la multiplicacion de D-1 por b, la parte 2 es la suma de D-1(L+U)
        double[] resultadoParte1; 
        double[] resultadoParte2;

        /*matriz de prueba */
        int[][] prueba = {
            {3,-1,-1},
            {-1,3,1},
            {2,1,4},
        };
        int b[] = {1,3,7};

        /*matriz de aleatoria */
        // int prueba[][] = new int[n][n];
        // // numeros aleatorios del -10 al 10 para rellenar la matriz
        // for(int i=0; i<n; i++){
        //     for(int j=0; j<n; j++){
        //         prueba[i][j] = (int)(Math.random()*21)-10;
        //     }
        // }
        // int b[] = new int[n];
        // // numeros aleatorios del -10 al 10 para rellenar el vector
        // for(int i=0; i<n; i++){
        //     b[i] = (int)(Math.random()*21)-10;
        // }

        resultadoParte1 = new double[b.length];
        int tamMatriz = prueba.length;
        

        D=transpuestaNegativaMatrix(prueba);
        /*prueba transpuesta*/
        //imprimirVector(D);

        LmasU = obteniendoLmasU(prueba);
        /*prueba triangulo inferior*/
        //imprimirMatriz(LmasU);
        

        /*multiplicacion D-1 por b, parte 1, version paralela*/
        int faltante = 0;
        int numHilos = 2;
        for(int i=0; i<b.length-1; i+=numHilos){
            multiplicacionD_1B hilo = new multiplicacionD_1B(D[i], b[i]);
            multiplicacionD_1B hilo2 = new multiplicacionD_1B(D[i+1], b[i+1]);
            // multiplicacionD_1B hilo3 = new multiplicacionD_1B(D[i+2][i+2], b[i+2]);
            // multiplicacionD_1B hilo4 = new multiplicacionD_1B(D[i+3][i+3], b[i+3]);
            // multiplicacionD_1B hilo5 = new multiplicacionD_1B(D[i+4][i+4], b[i+4]);

            hilo.start();
            hilo2.start();
            // hilo3.start();
            // hilo4.start();
            // hilo5.start();
            try{
                hilo.join();
                hilo2.join();
                // hilo3.join();
                // hilo4.join();
                // hilo5.join();
            }catch(InterruptedException e){
                
            }
            resultadoParte1[i] = hilo.getResultado();
            resultadoParte1[i+1] = hilo2.getResultado();
            // resultadoParte1[i+2] = hilo3.getResultado();
            // resultadoParte1[i+3] = hilo4.getResultado();
            // resultadoParte1[i+4] = hilo5.getResultado();
            
            faltante = i;
            
        }
        if(faltante<(b.length-1)){
            for(int i=faltante; i<b.length; i++){
                resultadoParte1[i] = (1.0/D[i])*b[i];
            }
        }
        //imprimirVector(resultadoParte1);

        /*version secuencial parte 1*/
        // for(int i=0; i<b.length; i++){
        //     resultadoParte1[i] = (1.0/D[i][i])*b[i];
        // }
        // imprimirVector(resultadoParte1);

        /*suma de D-1(L+U), parte 2, version paralela*/

        /*version seucneial, parte 2*/
        for(int i=0; i<tamMatriz; i++){
            resultadoParte2 = new double[tamMatriz];
            for(int j=0; j<tamMatriz; j++){
                resultadoParte2[j]= (1.0/D[i])*LmasU[i][j];
            }
            listaArreglos.add(new guardandoArreglos(resultadoParte2));
        }

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
