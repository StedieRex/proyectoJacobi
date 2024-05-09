package concurrenteJacobi;

public class multiplicacionD_1B extends Thread{
    private int elementoD;
    private double elementoB;
    private double resultado;

    public multiplicacionD_1B(int elementoD, double elementoB) {
        this.elementoD = elementoD;
        this.elementoB = elementoB;
    }

    @Override
    public void run(){
        //System.out.println((1.0/elementoD));
        resultado = (1.0/elementoD) * elementoB;
    }

    public double getResultado() {
        return resultado;
    }
}
