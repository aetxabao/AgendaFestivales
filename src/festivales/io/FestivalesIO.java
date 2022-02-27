package festivales.io;

import festivales.modelo.AgendaFestivales;
import festivales.modelo.Estilo;
import festivales.modelo.Festival;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;


/**
 * La clase contiene méodos estáticos que permiten
 * cargar la agenda de festivales leyendo los datos desde
 * un fichero
 *
 * @author Adrian Garcia Galera
 *
 */
public class FestivalesIO {

    
    public static void cargarFestivales(AgendaFestivales agenda) {
        Scanner sc = null;
        try {
            sc = new Scanner(FestivalesIO.class.
                    getResourceAsStream("/festivales.csv"));
            while (sc.hasNextLine()) {
                String lineaFestival = sc.nextLine();
                Festival festival = parsearLinea(lineaFestival);
                agenda.addFestival(festival);
                
            }
        } finally {
            if (sc != null) {
                sc.close();
            }
        }
    }

    /**
     * se parsea la línea extrayendo sus datos y creando y
     * devolviendo un objeto festivales.modelo.Festival
     * @param lineaFestival los datos de un festival
     * @return el festival creado
     */
    public static Festival parsearLinea(String lineaFestival) {

        Festival festival = new Festival(getNombre(lineaFestival), getLugar(lineaFestival),
                getFecha(lineaFestival), getDuracion(lineaFestival), getEstilos(lineaFestival));
        return festival;

    }

    public static String getNombre(String str){
        String nombre = str.substring(0,str.indexOf(':'));
        nombre = nombre.trim();

        String palabras[] = nombre.split("\\s");
        String nombreFinal = "";

        for (String palabra : palabras) {
            String inicial = palabra.substring(0,1);
            String resto = palabra.substring(1);

            nombreFinal+= inicial.toUpperCase() + resto + " ";
        }
        return nombreFinal.trim();
    }

    public static String getLugar(String str){
        String palabras[] = str.split(":");
        String lugar = palabras[1];
        lugar = lugar.toUpperCase();
        return lugar.trim();
    }

    public static LocalDate getFecha(String str){
        String palabras[] = str.split(":");
        String fecha = palabras[2];
        fecha = fecha.trim();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse(fecha, formatter);
        return localDate;
    }

    public static int getDuracion(String str){
        String palabras[] = str.split(":");
        String duracion = palabras[3];
        duracion = duracion.trim();
        return Integer.parseInt(duracion);
    }

    public static HashSet<Estilo> getEstilos(String str){
        String palabras[] = str.split(":");
        HashSet<Estilo> estilos = new HashSet<>();

        for (int i = 4; i < palabras.length; i++) {
            palabras[i] = palabras[i].trim();
            estilos.add(Estilo.valueOf(palabras[i].toUpperCase()));
        }
        return estilos;
    }



    
   
    
    
}
