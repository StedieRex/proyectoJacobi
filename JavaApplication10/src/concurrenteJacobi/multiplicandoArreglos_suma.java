package concurrenteJacobi;

public class multiplicandoArreglos_suma extends Thread{
    private double[] arreglo1, arreglo2;
    private double suma;
    private double resultado;

    public multiplicandoArreglos_suma(double[] arreglo1, double[] arreglo2, double suma) {
        this.arreglo1 = arreglo1;
        this.arreglo2 = arreglo2;
        this.suma = suma;
    }

    @Override
    public void run(){
        int n = arreglo1.length;
        resultado = 0;
        for(int i=0; i<n; i++){
            resultado += arreglo1[i]*arreglo2[i];
        }
    }

    public double getResultado() {
        return resultado+suma;
    }
}
