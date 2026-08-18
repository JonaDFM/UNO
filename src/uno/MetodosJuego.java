package uno;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.Stack;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class MetodosJuego {
    Mazo maz = new Mazo(); //OBJETO DE TIPO MAZO LLAMADO MAZ
    Stack<Carta> mazo = maz.getMazo(); //SE LLAMA AL METODO GETMAZO DE MAZ, OBTENIENDO LAS CAARTAS YA REVUELTAS, SE ALMACENAN EN UNA PILA
    private ActionListener tirarCartasListener; //ACTIONLISTENER TIRARCARTASLISTENER PARA ASIGNARLAS A LOS BOTONES NUEVOS QUE SE CREAN
    
    // CONSTRUCTOR QUE RECIBE EL ACTION LISTENER
    public MetodosJuego(ActionListener tirarCartasListener) {
        this.tirarCartasListener = tirarCartasListener; //SE LE PASA EL PARAMETRO AL ATRIBUTO DE LA CLASE
    }
    
    public void repartirCartas(Jugador jugador){ //METODO REPARTIR CARTA QUE REQUIERE UN JUGADOR
        
        for (int i = 0; i < 7; i++) { //DESDE CERO HASTA MENOR A 7
            Carta carta = mazo.pop(); //SE SACA UNA CARTA DEL MAZO Y SE ALMACENA EN UN OBJETO DE TIPO CARTA LLAMADO CARTA
            jugador.juegoCartas.add(carta); //SE LE AGREGA AL ARRAYLIST DEL JUGADOR LA NUEVA CARTA
            
            JButton nuevaCarta = new JButton(); //SE CREA UN OBJETO DE TIPO JBUTTON LLAMADO NUEVA CARTA
            nuevaCarta.setSize(120, 180); //SE LE ASIGNA UN TAMAÑO A LA NUEVA CARTA (BOTON)
            ImageIcon imagen = new ImageIcon(""+carta.getDireccion()); //SE CREA UNA IMAGEN CON LA DIRECCION DE LA CARTA
            //SE ESCALA LA ANTERIOR IMAGEN EN UNA NUEVA, AL TAMAÑO DEL BOTON
            ImageIcon imageIcon = new ImageIcon(imagen.getImage().getScaledInstance(nuevaCarta.getWidth(), nuevaCarta.getHeight(), java.awt.Image.SCALE_SMOOTH)); 
            nuevaCarta.setIcon(imageIcon); //AL BOTON DE LA NUEVA CARTA SE LE ASIGNA LA IMAGEN
            
            nuevaCarta.setBackground(Color.decode("#BE7BA4")); //SE LE DA UN COLOR DE FONDO AL BOTON
            nuevaCarta.addActionListener(tirarCartasListener); //SE LE AGREGA EL ACTIONLISTENER AL QUE RESPONDERA EL BOTON
            jugador.botonesCartas.add(nuevaCarta); //SE AGREGA EL NUEVO BOTON AL ARRAYLIST DE BOTONES DEL JUGADOR
        }
        
    }
    
    public void robarDelMazo(Jugador jugador){ //METODO ROBARDELMAZO QUE RECIBE COMO PARAMETRO UN JUGADOR
        Carta carta = mazo.pop(); //SE SACA UNA CARTA DEL MAZO Y SE GUARDA EN UN OBJETO DE TIPO CARTA LLAMADO CARTA
        jugador.juegoCartas.add(carta); //SE LE AGREGA AL ARRAYLIST DE CARTAS DEL JUGADOR LA NUEVA CARTA
        
        JButton nuevaCarta = new JButton(); //SE CREA UN OBJETO DE TIPO JBUTTON LLAMADO NUEVA CARTA
        nuevaCarta.setSize(120, 180); //SE LE ASIGNA UN TAMAÑO A LA NUEVA CARTA (BOTON)
        ImageIcon imagen = new ImageIcon(""+carta.getDireccion()); //SE CREA UNA IMAGEN CON LA DIRECCION DE LA CARTA
        //SE ESCALA LA ANTERIOR IMAGEN EN UNA NUEVA, AL TAMAÑO DEL BOTON
        ImageIcon imageIcon = new ImageIcon(imagen.getImage().getScaledInstance(nuevaCarta.getWidth(), nuevaCarta.getHeight(), java.awt.Image.SCALE_SMOOTH)); 
        nuevaCarta.setIcon(imageIcon); //AL BOTON DE LA NUEVA CARTA SE LE ASIGNA LA IMAGEN
        
        nuevaCarta.setBackground(Color.decode("#BE7BA4")); //SE LE DA UN COLOR DE FONDO AL BOTON
        nuevaCarta.addActionListener(tirarCartasListener); //SE LE AGREGA EL ACTIONLISTENER AL QUE RESPONDERA EL BOTON
        jugador.botonesCartas.add(nuevaCarta); //SE AGREGA EL NUEVO BOTON AL ARRAYLIST DE BOTONES DEL JUGADOR
    }
    
    public void tirarCarta(Jugador jugador, JButton e, int index){ //METODO PARA LANZAR CARTA DE UN JUGADOR
        jugador.juegoCartas.remove(index); //INDEX RECUPERADO EN JUEGOGRAFICO PARA SABER LA POSICION EN EL ARRAYLIST DE LA CARTA A ELIMINAR
        jugador.botonesCartas.remove(e); //DIRECCION DE MEMORIA DEL BOTON ELIMINADO, PARA PODER ELIMINARLO DEL ARRAYLIST DE BOTONES DEL JUGADOR
    }
    
    public Carta recuperarCarta(Jugador jugador, int index){ //METODO PARA RECUPERAR LA CARTA ANTES DE ELIMINARLA
        return jugador.juegoCartas.get(index); //RETORNARA UN OBJETO DE TIPO CARTA, SEGUN EL INDICE SOLICITADO SE RETORNARA LA CARTA EN ESA POSICION EN EL ARRAYLIST DEL JUGADOR
    }
    
}
