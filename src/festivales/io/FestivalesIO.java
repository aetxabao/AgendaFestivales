package festivales.io;

import festivales.modelo.AgendaFestivales;
import festivales.modelo.Estilo;
import festivales.modelo.Festival;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

/**
 * La clase contiene métodos estáticos que permiten
 * cargar la agenda de festivales leyendo los datos desde
 * un fichero
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
     * devolviendo un objeto Festival
     * @param lineaFestival los datos de un festival
     * @return el festival creado
     */
    public static Festival parsearLinea(String lineaFestival) {
        String[] datos = lineaFestival.trim().split(":+");
        String nombre = obtenerNombre(datos[0].trim());
        String lugar = obtenerLugar(datos[1].trim());
        LocalDate fechaInicio = obtenerFechaInicio(datos[2].trim());
        int duracion = obtenerDuracion(datos[3].trim());
        HashSet<Estilo> estilos = obtenerEstilos(Arrays.copyOfRange(datos, 4,
                datos.length));
        return new Festival(nombre, lugar, fechaInicio, duracion, estilos);
    }
    
    private static int obtenerDuracion(String dato) {
        return Integer.parseInt(dato);
    }
    
    private static String obtenerLugar(String dato) {
        return dato.toUpperCase();
    }
    
    private static HashSet<Estilo> obtenerEstilos(String[] estilos) {
        HashSet<Estilo> setEstilos = new HashSet<>();
        for (String estilo : estilos) {
            setEstilos.add(Estilo.valueOf(estilo.trim().toUpperCase()));
        }
        return setEstilos;
    }
    
    private static String obtenerNombre(String nombre) {
        String nuevo = "";
        for (String dato : nombre.split("\\s+")) {
            nuevo += Character.toUpperCase(dato.charAt(0)) +
                    dato.substring(1).toLowerCase() + " ";
        }
        return nuevo;
        
    }
    
    private static LocalDate obtenerFechaInicio(String inicio) {
        inicio = inicio.trim();
        return LocalDate.parse(inicio, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }
    
    
}
