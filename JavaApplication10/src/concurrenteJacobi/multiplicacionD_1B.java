package concurrenteJacobi;

public class multiplicacionD_1B extends Thread{
    private int elementoD, elementoB;
    private int resultado;

    public multiplicacionD_1B(int elementoD, int elementoB) {
        this.elementoD = elementoD;
        this.elementoB = elementoB;
    }

    @Override
    public void run(){
        resultado = elementoD * elementoB;
    }

    public int getResultado() {
        return resultado;
    }
}
