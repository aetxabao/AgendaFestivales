

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

/**
 * La clase contiene méodos estáticos que permiten
 * cargar la agenda de festivales leyendo los datos desde
 * un fichero
 *
 * @Autor Juan Garbayo
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
     * devolviendo un objeto Festival
     * @param lineaFestival los datos de un festival
     * @return el festival creado
     */
    public static Festival parsearLinea(String lineaFestival) {
       //DONE
        String[] dividido = lineaFestival.split(":");
        String nombre = sacarNombre(lineaFestival);
        String lugar = dividido[1].toUpperCase();
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate fecha = LocalDate.parse(dividido[2].trim(), formateador);
        int duracion = Integer.valueOf(dividido[3].trim());
        HashSet<Estilo> estilos = sacarEstilos(lineaFestival);
        Festival festival = new Festival(nombre, lugar, fecha, duracion, estilos);
        return festival;
    }

    public static String sacarNombre(String lineaFestival) {
        String[] dividido = lineaFestival.split(":");
        String[] nombreFestival = dividido[0].trim().split(" ");
        String nombreMayus = "";
        for (int i = 0; i < nombreFestival.length; i++) {
            String str = nombreFestival[i].trim().toUpperCase().substring(0,1);
            nombreFestival[i] = str.concat(nombreFestival[i].substring(1));
            nombreMayus += nombreFestival[i] + " ";
        }
        return nombreMayus;
    }
    
   public static HashSet<Estilo> sacarEstilos(String lineaFestival) {
       String[] dividido = lineaFestival.split(":");
       HashSet<Estilo> estilos = new HashSet<>();
       for (int i = 4; i < dividido.length; i++) {
           estilos.add(Estilo.valueOf(dividido[i].trim().toUpperCase()));
       }
       return estilos;
   }

    
    
}
