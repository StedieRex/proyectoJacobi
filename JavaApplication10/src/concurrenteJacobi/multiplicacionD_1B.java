package concurrenteJacobi;

public class multiplicacionD_1B extends Thread{
    private double elementoB, elementoD;
    private double resultado;

    public multiplicacionD_1B(double elementoD, double elementoB) {
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
