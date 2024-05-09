package concurrenteJacobi;

import java.lang.reflect.Array;
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
        double b[] = {1,3,7};

        /*matriz de aleatoria */
        // int prueba[][] = new int[n][n];
        // // numeros aleatorios del -10 al 10 para rellenar la matriz
        // for(int i=0; i<n; i++){
        //     for(int j=0; j<n; j++){
        //         prueba[i][j] = (int)(Math.random()*21)-10;
        //     }
        // }
        // double b[] = new int[n];
        // // numeros aleatorios del -10 al 10 para rellenar el vector
        // for(int i=0; i<n; i++){
        //     b[i] = (int)(Math.random()*21)-10;
        // }

        double []arregloIteracion = new double[b.length];
        for(int i=0; i<b.length; i++){
            arregloIteracion[i] = 0;
        }

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

        double [] resultado = new double[b.length];
        //do{
            int faltante2 = 0;
            for(int i=0; i<listaArreglos.size()-1; i+=numHilos){
                System.out.println("i: "+i);
                multiplicandoArreglos_suma hilo = new multiplicandoArreglos_suma(listaArreglos.get(i).getArreglo(), arregloIteracion, resultadoParte1[i]);
                multiplicandoArreglos_suma hilo2 = new multiplicandoArreglos_suma(listaArreglos.get(i+1).getArreglo(), arregloIteracion, resultadoParte1[i+1]);
                // multiplicandoArreglos hilo3 = new multiplicandoArreglos(listaArreglos.get(i+2).getArreglo(), b);
                // multiplicandoArreglos hilo4 = new multiplicandoArreglos(listaArreglos.get(i+3).getArreglo(), b);
                // multiplicandoArreglos hilo5 = new multiplicandoArreglos(listaArreglos.get(i+4).getArreglo(), b);

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
                resultado[i] = hilo.getResultado();
                resultado[i+1] = hilo2.getResultado();
                // resultado[i+2] = (int)hilo3.getResultado();
                // resultado[i+3] = (int)hilo4.getResultado();
                // resultado[i+4] = (int)hilo5.getResultado();
                faltante2 = i;
            }

            if(faltante2<(listaArreglos.size()-1)){
                for(int i=faltante2; i<listaArreglos.size(); i++){
                    multiplicandoArreglos_suma hilo = new multiplicandoArreglos_suma(listaArreglos.get(i).getArreglo(), arregloIteracion, resultadoParte1[i]);
                    hilo.start();
                    try{
                        hilo.join();
                    }catch(InterruptedException e){
                        
                    }
                    resultado[i] = hilo.getResultado();
                }
            }

            imprimirVector(resultado);
        //}while(comprandoResultado(b,resultado));
        //--------------------obteniendo tiempo de ejecucion---------------------
        // Obtener el tiempo de finalización
        long endTime = System.nanoTime();
        // Calcular la duración de la ejecución en milisegundos
        long duration = (endTime - startTime) / 1000000; // Convertir a milisegundos
        // Imprimir la duración de la ejecución
        System.out.println("Tiempo de ejecución, suma con 2 hilos: " + duration + " milisegundos");
    }
    
}
