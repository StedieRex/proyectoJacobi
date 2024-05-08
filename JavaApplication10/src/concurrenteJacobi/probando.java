package concurrenteJacobi;

public class probando {
    public static void main(String[] args){
        int contador=0;
        System.out.println("contador de 0 a 30 de 2 en 2:");
        for(int i=0; i<31; i+=2){
            contador = i;
            System.out.println(contador);
        }
        contador+=1;
        System.out.println("lo que se guardo en el contador fue: "+contador);
    }
}
