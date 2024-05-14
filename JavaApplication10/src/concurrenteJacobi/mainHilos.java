package concurrenteJacobi;

import java.util.ArrayList;


public class mainHilos {

    private static double[] antesecesores;
    private static boolean bandera=false;
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

    public static double[] transpuestaNegativaMatrix(double[][] matrix){
        int n = matrix.length;
        double[] D = new double[n];
        for(int i=0; i<n; i++){
            D[i] = matrix[i][i];
        }
        return D;
    }

    // da la matriz triangular inferior con los elementos negativos, sin diagonal-
    public static double[][] obteniendoLmasU(double[][] matrix){
        int n = matrix.length;
        double[][] LmasU = new double[n][n];
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

    public static double[] restarMatrices(double[] matrizA, double[] matrizB) {
        int filas = matrizA.length;
        
        // Verificar si las matrices tienen el mismo tamaño
        if (filas != matrizB.length ) {
            System.out.println("No se pueden sumar las matrices: tienen tamaños diferentes.");
            return null;
        }

        // Crear la matriz resultado
        double[] matrizResultado = new double[filas];

        // Realizar la suma elemento por elemento
        for (int i = 0; i < filas; i++) {
            matrizResultado[i] = matrizA[i]- matrizB[i];
            
        }

        return matrizResultado;
    }

    public static float normaMaxima(double[] vector) {
        float max = (float) Math.abs(vector[0]); // Inicializar el máximo con el primer elemento del vector

        // Recorrer el vector para encontrar el valor absoluto máximo
        for (int i = 0; i < vector.length; i++) {
            float absValue = (float) Math.abs(vector[i]);
            if (absValue > max) {
                max = absValue;
            }   
        }

        return max;
    }

    public static boolean comprandoResultado(double[] resultado){
        double error = 0.0001;	
        if(bandera){
            for(int i=0; i<resultado.length; i++){
                double comparacion= Math.abs(1-antesecesores[i]/resultado[i]);
                System.out.println(comparacion+"<"+error);
                if(comparacion<=error){
                    return false;
                }
                antesecesores[i] = resultado[i];
            }

            return true;
        }
        antesecesores = new double[resultado.length];
        for(int i=0; i<resultado.length; i++){
            antesecesores[i] = resultado[i];
        }
        bandera = true;
        return true;
    }

    public static void main(String[] args) {
        //obtener tiempo de incio
        long startTime = System.nanoTime();

        //inicio de variables
        double[] D;
        double[][] LmasU;
        ArrayList <guardandoArreglos> listaArreglos = new ArrayList<guardandoArreglos>();
        //la parte 1 representa la multiplicacion de D-1 por b, la parte 2 es la suma de D-1(L+U)
        double[] resultadoParte1; 
        double[] resultadoParte2;

        /*matriz de prueba */
        //dame una matriz de 6x6
        double[][] prueba = {
            {1,1,-1,1,-1},
            {2,2,1,-1,1},
            {3,1,-3,-2,3},
            {4,1,-1,4,-5},
            {16,-1,1,-1,-1}
        };
        double b[] = {2,4,8,16,32};/* */

        /*double [][] prueba = {
            {4,-1,1,0,0},
            {2,5,2,0,0},
            {1,2,4,1,0},
            {0,1,2,4,-1},
            {0,0,1,2,4}
        };
        double b[] = {8,3,11,3,8};/* */
        /*double[][] prueba = {
            {4,-1,1},
            {2,5,2},
            {1,2,4},
        };
        double b[] = {8,3,11};/* */

        /*double[][] prueba = {
            {3,-1,-1},
            {-1,3,1},
            {2,1,4}
        };
        double b[] = {1,3,7};/* */

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

            hilo.start();
            hilo2.start();

            try{
                hilo.join();
                hilo2.join();
            }catch(InterruptedException e){
                
            }
            resultadoParte1[i] = hilo.getResultado();
            resultadoParte1[i+1] = hilo2.getResultado();
            
            faltante = i;
            
        }
        if(faltante<(b.length-1)){
            for(int i=faltante; i<b.length; i++){
                resultadoParte1[i] = (1.0/D[i])*b[i];
            }
        }

        /*version seucneial, parte 2*/
        for(int i=0; i<tamMatriz; i++){
            resultadoParte2 = new double[tamMatriz];
            for(int j=0; j<tamMatriz; j++){
                resultadoParte2[j]= (1.0/D[i])*LmasU[i][j];
            }
            listaArreglos.add(new guardandoArreglos(resultadoParte2));
        }
    
        /*for(int i=0; i<listaArreglos.size(); i++){
            imprimirVector(listaArreglos.get(i).getArreglo());
        }/* */

        double [] resultado = new double[b.length];
        float error = 0;
        float epsilon = (float)0.001;
        int iteraciones = 0;
        do{

            int faltante2 = 0;
            for(int i=0; i<listaArreglos.size()-1; i+=numHilos){
               
                multiplicandoArreglos_suma hilo = new multiplicandoArreglos_suma(listaArreglos.get(i).getArreglo(), arregloIteracion, resultadoParte1[i]);
                multiplicandoArreglos_suma hilo2 = new multiplicandoArreglos_suma(listaArreglos.get(i+1).getArreglo(), arregloIteracion, resultadoParte1[i+1]);


                hilo.start();
                hilo2.start();

                try{
                    hilo.join();
                    hilo2.join();

                }catch(InterruptedException e){
                    
                }
                resultado[i] = hilo.getResultado();
                resultado[i+1] = hilo2.getResultado();

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
            iteraciones++;
            System.out.println("Iteracion "+iteraciones+":");
            imprimirVector(resultado);
            /*System.out.println(normaMaxima(restarMatrices(resultado, arregloIteracion)));
            System.out.println("/");
            System.out.println(normaMaxima(resultado));/* */
            error = normaMaxima(restarMatrices(resultado,arregloIteracion))/normaMaxima(resultado);
            for(int i=0; i<resultado.length; i++){
                arregloIteracion[i] = resultado[i];
            }
            
        }while(error>epsilon);

        //--------------------obteniendo tiempo de ejecucion---------------------
        // Obtener el tiempo de finalización
        long endTime = System.nanoTime();
        // Calcular la duración de la ejecución en milisegundos
        long duration = (endTime - startTime) / 1000000; // Convertir a milisegundos
        // Imprimir la duración de la ejecución
        System.out.println("Tiempo de ejecución, suma con 2 hilos: " + duration + " milisegundos");
    }
    
}
