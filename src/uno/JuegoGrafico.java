package uno;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class JuegoGrafico extends JFrame {

    private JPanel panel = new JPanel();
    private JPanel panelBarajas = new JPanel(new FlowLayout()); //FlowLayout permite agregar los botones de las cartas al panelBarajas sin importar su ubicación antes dada
    private JScrollPane scrollPane = new JScrollPane(panelBarajas, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    private ArrayList<Jugador> jugadores = new ArrayList();
    private Stack<Carta> cartasLanzadas = new Stack();
    private Carta tiradoPantallaCarta = new Carta();
    private MetodosJuego opciones;

    private JLabel tiradoPantalla = new JLabel();
    private JLabel status = new JLabel();

    private int indexJugador = 0;
    private int cantidadJugadores;
    private boolean reversa = false;
    
    private JButton cortina = new JButton();

    public JuegoGrafico() {
        setTitle("Uno"); //TITULO DEL JFRAME
        setExtendedState(JFrame.MAXIMIZED_BOTH); //AMPLIA LA VENTANA DEL JFRAME MAXIMIZADA
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //AL CERRAR LA VENTANA EL PROGRAMA TERMINARA
        iniciarComponentes();
    }

    public void iniciarComponentes() {
        ActionListener tirarCartasListener = new tirarCartasListener(); //SE CREA OBJETO DE TIPO ACTIONLISTENER INSTANCIADO DE LA CLASE TIRARCARTASLISTENER
        opciones = new MetodosJuego(tirarCartasListener); // AL OBJETO OPCIONES SE LE PASA A SU CONSTRUCTOR EL OBJETO INSTANCIADO ANTERIORMENTE

        panel.setBackground(Color.decode("#0D0C2B")); //SE LE ASIGNA FONDO AL PANEL PRINCIPAL QUE CUBRE TODA LA PANTALLA
        panel.setLayout(null); //SE COLOCA EN NULL PARA COLOCAR LIBREMENTE LOS COMPONENTES EN EL PANEL

        panelBarajas.setBackground(Color.decode("#633A8C")); //SE LE ASIGNA EL FONDO AL PANEL QUE CONTENDRÁ LAS CARTAS
        scrollPane.setBounds(100, 500, 1150, 200); //AL SCROLLPANE SE LE ASIGNA SU POSICION Y TAMAÑO
        panel.add(scrollPane); // SE AGREGA EL SCROLLPANE AL PANEL CADA VEZ QUE SE LLAMA AL METODO, PARA ACTUALIZARLO

        status.setFont(new Font("Impact", Font.BOLD, 25)); //TIPO DE LETRA Y TAMAÑO PARA LA ETIQUETA
        status.setForeground(Color.decode("#BE52D9")); //COLOR DE TEXTO DE LA ETIQUETA
        status.setBounds(90, 10, 300, 200); //POSICION Y TAMAÑO DE LA ETIQUETA}
        panel.add(status); //SE AGREGA LA ETIQUETA AL PANEL
        
        cortina.setVisible(false); //SE OCULTA LA CORTINA DE PRINCIPIO
        cortina.setFont(new Font("Impact", Font.BOLD, 60)); //FUENTE DE TEXTO
        cortina.setBackground(Color.decode("#111038")); //COLOR DE FONDO
        cortina.setForeground(Color.decode("#74D8EF")); //COLOR DE TEXTO
        Dimension tamaño = Toolkit.getDefaultToolkit().getScreenSize(); //SE RECUPERA LAS DIMENSIONES DE LA PANTALLA
        cortina.setSize(tamaño); //SE LE ASIGNAN LAS DIMENSIONES DE LA PANTALLA AL BOTON CORTINA
        cortina.addActionListener(new cortinaListener()); //SE LE ASIGNA EL ACTIONLISTENER A LA CORTINA
        add(cortina); //SE AGREGA LA CORTINA AL JFRAME
        
        add(panel); //SE AGREGA EL PANEL AL JFRAME
        iniciarMazo();
        misBarajas();
        iniciarStatus();
    }

    public void iniciarMazo() {
        JButton mazo = new JButton(); //SE CREA UN BOTON LLAMADO MAZO
        mazo.setBounds(1030, 280, 120, 180); //SE LE ASIGNA LA POSICION Y TAMAÑO DEL BOTON
        ImageIcon imagen = new ImageIcon("src\\uno\\ImagenesS\\CartaBocaAbajo.jpg"); //SE CREA UN IMAGEICON CON LA IMAGEN
        ImageIcon imageIcon = new ImageIcon(imagen.getImage().getScaledInstance(mazo.getWidth(), mazo.getHeight(), java.awt.Image.SCALE_SMOOTH)); //SE CREA OTRO IMAGEICON, ESCALANDO EL ANTERIOR
        mazo.setIcon(imageIcon); //SE LE AGREGA LA IMAGEN AL BOTON MAZO
        mazo.addActionListener(new robarCartasListener()); //SE LE ASIGNA EL ACTION LISTENER AL BOTON

        tiradoPantalla.setBounds(200, 280, 120, 180); //ETIQUETA QUE TENDRIA LA PRIMERA CARTA LANZADA DEL JUEGO
        do {
            Collections.shuffle(opciones.mazo);//METODO SHUFFLE DE TIPO COLLECTIONS PARA REVOLVER LOS OBJETOS ALMACENADOS EN MAZO, EN ESTE CASO LAS CARTAS
            tiradoPantallaCarta = opciones.mazo.peek(); //OBJETO DE TIPO CARTA QUE ALMACENA LA PRIMERA CARTA SACADA DEL MAZO
        } while (tiradoPantallaCarta.getColor()=="negro" || tiradoPantallaCarta.getNumero()==12 || tiradoPantallaCarta.getNumero()==-2 || tiradoPantallaCarta.getNumero()==-3);
        tiradoPantallaCarta = opciones.mazo.pop();
        cartasLanzadas.push(tiradoPantallaCarta); //ALMACENO EN EL STACK DE CARTAS LANZADAS
        ImageIcon ic = new ImageIcon("" + tiradoPantallaCarta.getDireccion()); //SE CREA UN IMAGEICON CON LA IMAGEN
        ImageIcon iIcon = new ImageIcon(ic.getImage().getScaledInstance(tiradoPantalla.getWidth(), tiradoPantalla.getHeight(), java.awt.Image.SCALE_SMOOTH)); //SE CREA OTRO IMAGEICON, ESCALANDO EL ANTERIOR
        tiradoPantalla.setIcon(iIcon); //SE LE AGREGA EL ICONO AL LABEL TIRADOPANTALLA

        panel.add(tiradoPantalla); //SE AGREGA AL PANEL EL LABEL
        panel.add(mazo); //SE AGREGA AL PANEL EL BOTON MAZO
        //ELEGIR LA CANTIDAD DE JUGADORES
        String[] arreglo = {"Dos", "Tres", "Cuatro"}; //SE CREA UN ARREGLO CON LAS OPCIONES DE JUGADORES
        cantidadJugadores = JOptionPane.showOptionDialog(null, "Elige la cantidad de jugadores", "Jugadores",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
                arreglo, "Option"); // AL INT CANTIDADJUGADORES SE LE ASIGNA LA OPCION DEVUELTA POR EL SHOWOPTIONDIALOG, COMENZANDO CON EL INDICE 0
        cantidadJugadores += 1; //AL INDICE 0 SE LE SUMA 1

        for (int i = 0; i <= cantidadJugadores; i++) { //SE CREARAN OBJETOS DE TIPO JUGADOR DESDE 0 HASTA MENOR IGUAL LA CANTIDAD DE JUGADORES-1
            Jugador jugador = new Jugador(); //SE CREA UN OBJETO DE TIPO JUGADOR
            jugadores.add(jugador); //AL ARRAYLIST DE TIPO JUGADOR "JUGADORES" SE LE AGREGA EL NUEVO JUGADOR
            opciones.repartirCartas(jugadores.get(i)); //SE LLAMA AL METODO REPARTIRCARTAS, MANDANDOLE COMO PARAMETRO AL NUEVO JUGADOR CREADO
        }
    }

    private void misBarajas() { //ESTE METODO SE LLAMARÁ VARIAS VECES, POR LO QUE ES NECESARIO REALIZAR AJUSTES DE ACUERDO A CADA JUGADOR
        panelBarajas.removeAll(); //AL LLAMAR AL METODO, SE BORRAN TODOS LOS COMPONENTES ALMACENADOS EN EL
        //DESDE 0 HASTA MENOR LA CANTIDAD DE CARTAS DEL JUGADOR ACTUAL SE LE AGREGARA AL PANEL DE BARAJAS 
        for (int i = 0; i < jugadores.get(indexJugador).botonesCartas.size(); i++) {
            panelBarajas.add(jugadores.get(indexJugador).botonesCartas.get(i)); //LOS BOTONES DE CARTAS DEL JUGADOR ACTUAL
        }
    }

    public void iniciarStatus() { //METODO PARA MOSTRAR LA ETIQUETA DE STATUS DE LOS JUGADORES, SE LLAMARÁ VARIAS VECES
        //SE CREA UNA CADENA, DANDO SALTOS DE LINEA CON ETIQUETAS HTML PARA SEPARAR CADA PARRAFO, RECUPERANDO
        //INFORMACION DE LOS JUGADORES
        String textoTurno = "<html>Turno del jugador: " + (indexJugador + 1) + "<br>";
        for (int i = 0; i < jugadores.size(); i++) {
            textoTurno += "<br>Cartas del jugador " + (i + 1) + ": " + jugadores.get(i).juegoCartas.size();
        }
        textoTurno += "</html>";
        status.setText(textoTurno); //SE LE AGREGA EL STRING AL JLABEL
    }

    private void sigJugador() { //METODO QUE SE LLAMARA VARIAS VECES, PARA ASIGNAR AL SIGUIENTE JUGADOR
        if (reversa == false) { //SI NO SE HA TIRADO REVERSA PARA CAMBIAR DIRECCIÓN
            if (indexJugador < cantidadJugadores) { // SI EL JUGADOR ACTUAL ES MENOR A LA CANTIDAD DE JUGADORES, YA QUE EL INDEX VA DE 0 HASTA JUGADORES-1
                indexJugador++; //SE PASA AL SIGUIENTE JUGADOR ASCENDENTEMENTE
            } else { //SI YA SE ENCUENTRA EN EL INDICE DEL ULTIMO JUGADOR
                indexJugador = 0; //EL INDEX REGRESA AL PRIMER JUGADOR DEL INDICE
            }
        } else { //SK REVERSA ES TRUE, OSEA, YA SE HIZO CAMBIO DE DIRECCION
            if (indexJugador > 0) { //SI EL JUGADOR ACTUAL ES MAYOR A 0, YA QUE EL ULTIMO JUGADOR AHORA ES EL QUE SE ENCUENTRA EN LA POSICION 0
                indexJugador--; //SE PASA AL ANTERIOR JUGADOR DESCENDENTEMENTE
            } else { //SI EL INDEXJUGADOR YA ES EL 0, OSEA EL PRIMERO
                indexJugador = cantidadJugadores; //EL INDEX AHORA SERA EL ULTIMO JUGADOR
            }
        }
        misBarajas(); //SE LLAMA AL METODO MISBARAJAS PARA HACER EL CAMBIO DE CARTAS AL JUGADOR ACTUAL
        iniciarStatus(); //SE LLAMA AL METODO INICIARSTATUS PARA CAMBIAR EL TEXTO DE JUGADOR ACTUAL
    }

    public class robarCartasListener implements ActionListener { //CLASE INTERNA QUE IMPLEMENTA ACTIONLISTENER

        @Override
        public void actionPerformed(ActionEvent e) {//LISTENER DEL BOTON PARA ROBAR CARTAS
            if (opciones.mazo.isEmpty()) { //SI SE LLEGARAN A ACABAR LAS CARTAS PARA ROBAR, SE MUESTRA UN MENSAJE
                JOptionPane.showMessageDialog(null, "Se han acabado las cartas del mazo. Revolviendo las cartas lanzadas");
                revolverCartasLanzadas(); //SE REVUELVEN LAS CARTAS LANZADAS Y SE AÑADEN NUEVAMENTE AL MAZO
            }
            opciones.robarDelMazo(jugadores.get(indexJugador)); //LLAMADA DEL METODO ROBARDELMAZO, PASANDOLE COMO PARAMETRO AL JUGADOR ACTUAL
            panelBarajas.add(jugadores.get(indexJugador).botonesCartas.getLast()); //SE AGREGA AL PANELBARAJAS LA ULTIMA CARTA (BOTON) DEL JUGADOR ACTUAL
            iniciarStatus(); //SE LLAMA AL METODO INICIARSTATUS PARA ACTUALIZAR EL NUMERO DE CARTAS DEL JUGADOR ACTUAL
        }

        public void revolverCartasLanzadas() { //METODO PARA REVOLVER LAS CARTAS LANZADAS EN CASO DE QUE SE ACABEN LAS DEL MAZO
            Collections.shuffle(cartasLanzadas); //SE LLAMA AL METODO SHUFFLE DE LA CLASE COLLECTIONS Y SE LE PASA COMO PARAMETRO LA PILA DE CARTAS LANZADAS
            opciones.mazo.addAll(cartasLanzadas); //UNA VEZ REVUELTAS LAS CARTAS LANZADAS, SE AGREGAN AL MAZO CON EL METODO ADDALL
            cartasLanzadas.clear(); //SE BORRAN TODOS LOS ELEMENTOS DE LA PILA DE CARTAS LANZADAS
        }

    }

    public class tirarCartasListener implements ActionListener { //CLASE INTERNA QUE IMPLEMENTA ACTIONLISTENER

        @Override
        public void actionPerformed(ActionEvent e) { //LISTENER DE LOS BOTONES PARA SER LANZADOS
            JButton lanzar = (JButton) e.getSource(); //SE RECUPERA LA DIRECCION DE MEMORIA DEL COMPONENTE Y SE HACE UN CAST A JBUTTON, EL CUAL SE LE ASIGNA LA MISMA DIRECCION DE MEMORIA A UN NUEVO BOTON LLAMADO LANZAR
            int index = panelBarajas.getComponentZOrder(lanzar); //EN UN INT, SE ALMACENA EL NUMERO DE POSICION QUE OCUPABA EL BOTON EN EL PANELBARAJAS CON EL METODO GETCOMPONENTZORDER
            Carta intentarTirar = opciones.recuperarCarta(jugadores.get(indexJugador), index); //RECUPERO LA CARTA ANTES DE ELIMINARLA, CON EL METODO RECUPERAR CARTA, LE MANDO EL JUGADOR, EL BOTON Y EL INDICE DEL BOTON

            if (intentarTirar.getColor() == tiradoPantallaCarta.getColor() //SI LA CARTA A INTENTAR TIRAR ES DEL MISMO COLOR QUE LA ULTIMA CARTA LANZADA
                    || intentarTirar.getNumero() == tiradoPantallaCarta.getNumero() //O SI LA CARTA A INTENTAR TIRAR TIENE EL MISMO NUMERO QUE LA CARTA LANZADA ANTERIORMENTE
                    || intentarTirar.getNumero() == -1 //O SI LA CARTA ES UN CAMBIO DE COLOR
                    || intentarTirar.getNumero() == 14) { // O SI ES UN CAMBIO DE COLOR +4
                tirarCarta(index, lanzar); //SE LLAMA AL METODO TIRARCARTA, ENVIANDO COMO PARAMETRO EL INDICE DE LA CARTA Y EL BOTON
            }
        }

        public void tirarCarta(int index, JButton lanzar) { //METODO PARA TIRAR CARTA Y ELIMINARLA DEL MAZO DEL JUGADOR
            panelBarajas.remove(lanzar); //SE REMUEVE DEL PANELBARAJAS LA CARTA LANZADA, PASANDOLE LA DIRECCION DE MEMORIA
            panelBarajas.updateUI(); //SE ACTUALIZA EL PANEL

            tiradoPantallaCarta = opciones.recuperarCarta(jugadores.get(indexJugador), index); //RECUPERO LA CARTA
            cartasLanzadas.push(tiradoPantallaCarta); //ALMACENO EN EL STACK DE CARTAS LANZADAS
            opciones.tirarCarta(jugadores.get(indexJugador), lanzar, index); //ELIMINO LA CARTA DEL MAZO DEL JUGADOR

            tiradoPantalla.setIcon(lanzar.getIcon()); //ACTUALIZO EL ICONO DEL JLABEL CON LA ULTIMA CARTA LANZADA

            comprobarReglas(); //SE COMPRUEBAN LAS REGLAS PARA LA CARTA LANZADA, EN CASO QUE SEA UNA CARTA ESPECIAL
            sigJugador(); //AL TERMINAR DE APLICAR LAS REGLAS, SE PASA AL SIGUIENTE JUGADOR
            
            panel.setVisible(false); //SE OCULTA LA VISIBILIDAD DEL PANEL Y TODO LO QUE CONTIENE
            cortina.setVisible(true); //SE HACE VISIBLE LA CORTINA
            cortina.setText("TURNO DEL JUGADOR: "+(indexJugador+1)); //SE AGREGA EL TEXTO DE LA CORTINA DEL TURNO DEL SIGUIENTE JUGADOR
            
        }

        public void comprobarReglas() { //METODO PARA COMPROBAR Y APLICAR LAS REGLAS DE LAS CARTAS ESPECIALES
            //CAMBIO DE COLOR, SI LA CARTA LANZADA TIENE EL NUMERO DE UN +4 O UN CAMBIO DE COLOR
            if (tiradoPantallaCarta.getNumero() == -1 || tiradoPantallaCarta.getNumero() == 14) {
                String[] arreglo = {"Verde", "Rojo", "Azul", "Amarillo"}; //SE CREA UN ARREGLO CON LAS OPCIONES DE COLORES
                //EN UN INT SE ALMACENA LA OPCION SELECCIONADA, RECORDANDO QUE VA DE 0 A 3, SE LE PASA EL ARREGLO AL SHOWOPTIONDIALOG
                int opcion = JOptionPane.showOptionDialog(null, "Selecciona un nuevo color", "Cambio Color",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
                        arreglo, "Option");
                ImageIcon imagen = new ImageIcon(); //SE CREA UN OBJETO IMAGEN DE TIPO IMAGENICON
                switch (opcion) { //EVALUAMOS LA OPCION SELECCIONADA
                    //OPERADOR TERNARIO, PREGUNTA SI LA CARTA TIRADA EN PANTALLA TIENE EL NUMERO -1 (SIMPLE CAMBIO DE COLOR) SI ES EL CASO, EL OBJETO IMAGEN TENDRA LA IMAGEN DE CAMBIO DE COLOR
                    //SI NO ES -1, ENTONCES QUIERE DECIR QUE ES 14, POR LO QUE LA IMAGEN SERÁ DE UN CAMBIO DE COLOR CON +4
                    //SI ELIGIO EL COLOR X, A LA CARTA ESPECIAL LANZADA SE LE CAMBIA SU COLOR A SU ATRIBUTO
                    case 0:
                        tiradoPantallaCarta.setColor("verde");
                        imagen = tiradoPantallaCarta.getNumero() == -1 ? new ImageIcon("src\\uno\\ImagenesS\\CCVerde.jpg") : new ImageIcon("src\\uno\\ImagenesS\\Mas4Verde.jpg");
                        break;

                    case 1:
                        tiradoPantallaCarta.setColor("rojo");
                        imagen = tiradoPantallaCarta.getNumero() == -1 ? new ImageIcon("src\\uno\\ImagenesS\\CCRojo.jpg") : new ImageIcon("src\\uno\\ImagenesS\\Mas4Rojo.jpg");
                        break;

                    case 2:
                        tiradoPantallaCarta.setColor("azul");
                        imagen = tiradoPantallaCarta.getNumero() == -1 ? new ImageIcon("src\\uno\\ImagenesS\\CCAzul.jpg") : new ImageIcon("src\\uno\\ImagenesS\\Mas4Azul.jpg");
                        break;

                    case 3:
                        tiradoPantallaCarta.setColor("amarillo");
                        imagen = tiradoPantallaCarta.getNumero() == -1 ? new ImageIcon("src\\uno\\ImagenesS\\CCAmarillo.jpg") : new ImageIcon("src\\uno\\ImagenesS\\Mas4Amarillo.jpg");
                        break;
                    default:
                        throw new AssertionError();
                }
                //SE CREA OTRO IMAGEICON ESCALANDO LA IMAGEN ANTERIOR
                ImageIcon imageIcon = new ImageIcon(imagen.getImage().getScaledInstance(tiradoPantalla.getWidth(), tiradoPantalla.getHeight(), java.awt.Image.SCALE_SMOOTH));
                tiradoPantalla.setIcon(imageIcon); //AL JLABEL SE LE CAMBIA EL ICONO DE LA CARTA LANZADA
            }

            //CAMBIO DE COLOR Y AUMENTAR CARTAS
            if (tiradoPantallaCarta.getNumero() == 12 || tiradoPantallaCarta.getNumero() == 14) {
                int jSiguiente = indexJugador; //SE CREA UN ENTERO QUE ES IGUAL AL INDEX DEL JUGADOR ACTUAL, SE APLICAN LAS MISMAS REGLAS PARA DETERMINAR EL SIGUIENTE JUGADOR
                //AL CUAL SE LE SUMARAN LAS CARTAS
                if (reversa == false) {
                    if (jSiguiente < cantidadJugadores) {
                        jSiguiente++;
                    } else {
                        jSiguiente = 0;
                    }
                } else {
                    if (jSiguiente > 0) {
                        jSiguiente--;
                    } else {
                        jSiguiente = cantidadJugadores;
                    }
                }
                
                int j; //SE CREA LA VARIABLE J, LA CUAL TENDRÁ LA CANTIDAD DE VECES QUE SE REPETIRA LA ASIGNACION DE CARTAS
                //OPERADOR TERNARIO, SI EL NUMERO DE LA CARTA TIRADA ES 12 (+2), SE REPETIRA DOS VECES, SI NO, SE REPETIRA 4 VECES
                j = tiradoPantallaCarta.getNumero() == 12 ? 2 : 4;
                for (int i = 0; i < j; i++) { // CICLO QUE ROBARA CARTAS DEL MAZO N VECES Y SE LE ASIGNARAN AL SIGUIENTE JUGADOR
                    opciones.robarDelMazo(jugadores.get(jSiguiente)); //SE LLAMA AL METODO ROBARDELMAZO, PASANDOLE COMO PARAMETRO AL JUGADOR SIGUIENTE
                }
            }

            //REVERSA, SI EL NUMERO DE LA CARTA TIRADA ES IGUAL A -2(REVERSA)
            if (tiradoPantallaCarta.getNumero() == -2) { 
                reversa = !reversa; //SE CAMBIA EL VALOR DE REVERSA (TRUE O FALSE)
            }
            //STOP, SI EL NUMERO DE LA CARTA TIRADA ES IGUAL A -3(STOP)
            if (tiradoPantallaCarta.getNumero() == -3) {
                sigJugador(); //SE VUELVE A LLAMAR AL METODO SIGJUGADOR, SALTANDO EL TURNO DEL ANTERIOR
            }
            //COMPROBAR UNO, SI EL NUMERO DE CARTAS DEL JUGADOR ACTUAL ES IGUAL A 1
            if (jugadores.get(indexJugador).juegoCartas.size() == 1) {
                JOptionPane.showMessageDialog(null, "UNO"); //MUESTRA UN MENSAJE DICIENDO "UNO"
            }
            //COMPROBAR GANADOR
            if (jugadores.get(indexJugador).juegoCartas.size() == 0) { //SI AL JUGADOR ACTUAL SE QUEDA CON CERO CARTAS
                JOptionPane.showMessageDialog(null, "HAS GANADO JUGADOR " + (indexJugador + 1)); //MUESTRA UN MENSAJE DICIENDO QUE HA GANADO
                System.exit(0); //TERMINA LA EJECUCION DEL PROGRAMA
            }
        }
    }
    
    public class cortinaListener implements ActionListener { //CLASE INTERNA QUE IMPLEMENTA ACTIONLISTENER
        @Override
        public void actionPerformed(ActionEvent e) {//LISTENER DEL BOTON PARA LA CORTINA
            cortina.setVisible(false); //SE DESACTIVA LA VISIBILIDAD DE LA CORTINA
            panel.setVisible(true); //SE ACTIVA LA VISIBILIDAD DE PANEL PRINCIPAL
        }

    }
}
