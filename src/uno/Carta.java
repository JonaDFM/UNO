package uno;
import java.io.File;

public class Carta { //CLASE CARTA LA CUAL TIENE COMO ATRIBUTOS
    private int numero; //UN ENTERO EL CUAL SERA EL NUMERO DE LA CARTA
    private String color; //UN STRING EL CUAL SERA EL COLOR DE LA CARTA
    private File direccion; //UN FILE EL CUAL ALMACENARA LA DIRECCION DEL ARCHIVO DE LA IMAGEN LA CARTA

    public Carta(int numero, String color, File direccion) { //CONSTRUCTOR DE CARTA
        this.numero = numero;
        this.color = color;
        this.direccion = direccion;
    }
    
    public Carta() { //CONSTRUCTOR VACIO DE CARTA
        
    }

    public int getNumero() { //GET NUMERO PARA RETORNAR EL NUMERO DE LA CARTA
        return numero;
    }

    public void setNumero(int numero) { //SETNUMERO PARA ASIGNAR UN NUMERO A LA CARTA
        this.numero = numero;
    }

    public String getColor() { //GETCOLOR PARA RETORNAR EL COLOR DE LA CARTA
        return color;
    }

    public void setColor(String color) { //SETCOLOR PARA ASIGNAR EL COLOR DE LA CARTA
        this.color = color;
    }

    public File getDireccion() { //GETDIRECCION PARA RETORNAR LA DIRECCION DE LA CARTA
        return direccion;
    }

    public void setDireccion(File direccion) { //SETDIRECCION PARA ASIGNAR LA DIRECCION DE LA CARTA
        this.direccion = direccion;
    }
    
    public String imp(){ //METODO IMP DE PRUEBA PARA IMPRIMIR LOS ATRIBUTOS DE LA CARTA
        return this.color+" "+this.numero+" "+this.direccion;
    }
}
