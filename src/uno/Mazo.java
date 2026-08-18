package uno;

import java.io.File;
import java.util.Stack;

public class Mazo {
    Carta cartas[] = new Carta[108]; //SE CREA UN VECTOR DE TIPO CARTA LLAMADO CARTAS, EL CUAL CONTIENE 108 ESPACIOS
    File ficheroCartas = new File("src\\uno\\Imagenes"); //OBJETO DE TIPO FILE EL CUAL SE LE PASA LA DIRECCION EN DONDE SE ENCUENTRAN LAS IMAGENES
    File[] archivos = ficheroCartas.listFiles(); //SE CREA UN VECTOR DE TIPO FILE, EL CUAL CONTENDRA LA DIRECCION DE TODOS LOS ARCHIVOS DENTRO DE LA CARPETA
   
    private String getColor(String str){ //METODO PARA ASIGNAR EL COLOR A UNA CARTA, EL CUAL RECIBE COMO PARAMETRO UN STRING
        if( str.matches("(.*)Rojo(.*)") ) return "rojo"; //SI EL STRING TIENE LA PALABRA "X" ENTRE TODA LA CADENA
        if( str.matches("(.*)Azul(.*)") ) return "azul"; //SE RETORNARA UN STRING CON EL COLOR CON EL QUE COINCIDA
        if( str.matches("(.*)Verde(.*)") ) return "verde";
        if( str.matches("(.*)Amarillo(.*)") ) return "amarillo";
        if( str.matches("(.*)Negro(.*)") ) return "negro";
        
        return null;
    }
    
    private int getNum(String str){ //METODO DE TIPO ENTERO PARA ASIGNAR EL NUMERO DE LA CARTA, EL CUAL RECIBE COMO PARAMETRO UN STRING
        if( str.matches("Mas2(.*)") ) return 12; //SI AL INICIO DE LA CADENA TIENE LA PALABRA "X" Y DESPUES MAS TEXTO
        if( str.matches("Mas4(.*)") ) return 14; //SE RETORNARA UN INT CON EL NUMERO DE LA CARTA QUE COINCIDA
        if( str.matches("1(.*)") ) return 1;
        if( str.matches("2(.*)") ) return 2;
        if( str.matches("3(.*)") ) return 3;
        if( str.matches("4(.*)") ) return 4;
        if( str.matches("5(.*)") ) return 5;
        if( str.matches("6(.*)") ) return 6;
        if( str.matches("7(.*)") ) return 7;
        if( str.matches("8(.*)") ) return 8;
        if( str.matches("9(.*)") ) return 9;
        if( str.matches("0(.*)") ) return 0;
        if( str.matches("CC(.*)") ) return -1; //CAMBIO COLOR
        if( str.matches("R(.*)") ) return -2; //REVERSA
        if( str.matches("S(.*)") ) return -3; //STOP
        
        return 0;
    }
    
    
    private void Arreglo(){ //METODO DE TIPO VACIO PARA LLENAR EL ARREGLO DE CARTAS CON SUS RESPECTIVOS ATRIBUTOS
        int c=0; //VARIABLE PARA LLEVAR EL CONTADOR DE LAS 108 CARTAS
        
        for (int i = 0; i < cartas.length; i++) { //DESDE I=0 HASTA MENOR EL TAMAÑO DEL VECTOR (108)
            cartas[i] = new Carta(); //SE LE INSTANCIA A CADA POSICION UNA NUEVA CARTA
        }
        int n=0; //VARIABLE PARA LLEVAR LA CANTIDAD DE REPETICIONES QUE DEBE TENER CADA CARTA
        for (int i = 0; i < archivos.length; i++) { //DESDE I=0 HASTA MENOR EL TAMAÑO DEL VECTOR (108)
            String nombreImagen = archivos[i].getName(); //SE RECUPERA EL NOMBRE DEL ARCHIVO DE LA IMAGEN Y SE ALMACENA EN UNA CADENA
            //OPERADOR TERNARIO, SI EL NUMERO DEVUELTO SEGUN EL NOMBRE DE LA IMAGEN ES 0 (CARTA DE NUMERO 0) N VALDRA 1, SI NO, VALDRA 2
            n = getNum(nombreImagen) == 0? 1 : 2; //
            //OPERADOR TERNARIO, SI EL NOMBRE DEVUELTO SEGUN EL NOMBRE DE LA IMAGEN ES "NEGRO" (CARTA DE COLOR NEGRO) N VALDRA 4, SI NO, SEGUIRA VALIENDO LO ANTERIOR
            n = getColor(nombreImagen).equals("negro")?4:n;
            
            for(int j=0; j<n; j++){ //CICLO PARA ASIGNAR LA CANTIDAD DE REPETICIONES DE LA CARTA, TODAS LAS CARTAS ENTRAN A ESTE CICLO
                cartas[c].setColor(getColor(nombreImagen)); //EN LA POSICION ACTUAL DEL VECTOR DE CARTAS, SE LE ASIGNARA A ESE CARTA
                cartas[c].setNumero(getNum(nombreImagen));  //EL COLOR DEVUELTO POR EL METODO GETCOLOR, Y EL NUMERO
                cartas[c].setDireccion(archivos[i]); //SU DIRECCION SE RECUPERA DEL VECTOR DE ARCHIVOS
                c++; //AUMENTA EL CONTADOR DE CARTAS
            }
        }
    }
    
    public Stack getMazo(){ //METODO PARA OBTENER EL STACK DE CARTAS
        Stack<Carta> mazo = new Stack<Carta>(); //SE CREA UNA NUEVA PILA DE TIPO CARTA LLAMADO MAZO
        Arreglo(); //SE LLAMA AL METODO ARREGLO PARA LLENAR EL VECTOR DE CARTAS
        for (int i = 0; i < cartas.length; i++) { //DESDE I=0 HASTA MENOR AL TAMAÑO DEL VECTOR (108)
            mazo.push(cartas[i]); //SE VAN AGREGANDO A LA PILA LAS CARTAS SEGUN SU POSICION
        }
        return mazo; //RETORNA LA PILA DE CARTAS A DONDE FUE LLAMADO
    }
}