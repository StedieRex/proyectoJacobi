/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package probando;

public class Probando {

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
    
    public static void main(String[] args) {
        double[][] prueba = {
            {0.7,0.1,-8.2},
            {25,-0.9,-0.3},
            {3.7,7.3,-0.1},
        };
        double b[] = {-56.4,20.2,-18.9};/* */
        /*double[][] prueba = {
            {3,-1,-1},
            {-1,3,1},
            {2,1,4},
        };
        double b[] = {1,3,7};/* */
        double[] arregloIteracion = new double[b.length];
        for(int i=0;i<b.length;i++)
            arregloIteracion[i]=0;
        
        
        
        double[] masGrande=new double[b.length];
        int[] indiceFila=new int[b.length];
        for(int i=0;i<b.length;i++)
            masGrande[i]=0;
        
        for(int j=0;j<prueba.length;j++){
            for(int i=0;i<prueba.length;i++){
                if(prueba[i][j]>masGrande[j] || i==0){
                    masGrande[j]=prueba[i][j];
                    indiceFila[j]=i;
                }
            }
        }
        imprimirVector(masGrande);

        //todos los indices en 0, son otro indice que se agregara mas tarde
        double suma=0;
        double[]vectorError=new double[b.length];
        int tamMatriz=masGrande.length;
        int similar=0;
        //el for es para la suma
        for(int i=0; i<tamMatriz; i++){
            if(similar!=i){
                suma+=prueba[0][i]*arregloIteracion[i];
                suma+=b[indiceFila[0]];
            }
        }
        arregloIteracion[0]=suma/masGrande[0];
        imprimirVector(arregloIteracion);
    }
    
}
