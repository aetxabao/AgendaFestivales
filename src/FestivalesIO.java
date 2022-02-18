

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Scanner;

/**
 * La clase contiene méodos estáticos que permiten
 * cargar la agenda de festivales leyendo los datos desde
 * un fichero
 * @autor Evelin Virunurm
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
       //TODO
        String nombre = extractNombre(lineaFestival);
        String lugar = extractLugar(lineaFestival);
        LocalDate fechaInicio = extractFecha(lineaFestival);
        int duracion = extractDuracion(lineaFestival);
        HashSet<Estilo> estilos = FestivalesIO.extractEstilos(lineaFestival);

        Festival festival = new Festival(nombre, lugar, fechaInicio, duracion, estilos);
        return festival;
    }


    public static String extractNombre(String lineaFestival) {
        String[] array = lineaFestival.trim().split(":");
        String[] nombreArray = array[0].trim().split(" ");
        StringBuilder nombre = new StringBuilder();
        for (int i = 0; i < nombreArray.length; i++) {
            if (nombreArray[i].equals(" ") || nombreArray[i].equals("")) {
                continue;
            }
            nombreArray[i] = String.valueOf(nombreArray[i].charAt(0)).toUpperCase() + nombreArray[i].substring(1);
            nombre.append(nombreArray[i]).append(" ");
        }
        return nombre.toString().trim();
    }

    public static String extractLugar(String lineaFestival) {
        String[] array = lineaFestival.trim().split(":");
        return array[1].trim().toUpperCase();
    }

    public static LocalDate extractFecha(String lineaFestival) {
        String[] array = lineaFestival.trim().split(":");
        String fechaS = array[2].trim();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return LocalDate.parse(fechaS, formatter);
    }

    public static int extractDuracion(String lineaFestival) {
        String[] array = lineaFestival.trim().split(":");
        String dur = array[3].trim();
        return Integer.parseInt(dur);
    }

    public static HashSet<Estilo> extractEstilos(String lineaFestival) {
        HashSet<Estilo> estilos = new HashSet<>();
        String[] array = lineaFestival.trim().split(":");
        for (int i = 4; i < array.length; i++) {
            estilos.add(Estilo.valueOf(array[i].toUpperCase().trim()));
        }
        return estilos;
    }


    
   
    
    
}
