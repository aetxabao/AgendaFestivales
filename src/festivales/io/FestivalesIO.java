package festivales.io;

import festivales.modelo.AgendaFestivales;
import festivales.modelo.Estilo;
import festivales.modelo.Festival;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Scanner;

/**
 * La clase contiene méodos estáticos que permiten
 * cargar la agenda de festivales leyendo los datos desde
 * un fichero
 *
 * @author Sergio Cobos Lorca
 */
public class FestivalesIO {


    public static void cargarFestivales(AgendaFestivales agenda) {
        Scanner sc = null;
        try {
            sc = new Scanner(FestivalesIO.class.
                    getResourceAsStream("/festivales/festivales.csv"));
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
     *
     * @param lineaFestival los datos de un festival
     * @return el festival creado
     */
    public static Festival parsearLinea(String lineaFestival) {
        //DONE
        String[] datos = lineaFestival.split(":");
        String nombre = "";
        String[] palabrasNombre = datos[0].trim().split(" ");
        for (int i = 0; i < palabrasNombre.length; i++) {
            String s = palabrasNombre[i];
            s = s.substring(0, 1).toUpperCase();
            palabrasNombre[i] = s.concat(palabrasNombre[i].substring(1));
            nombre = nombre.concat(palabrasNombre[i] + " ");
        }
        nombre = nombre.substring(0, nombre.length() - 1);
        String lugar = datos[1].toUpperCase().trim();
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate fecha = LocalDate.parse(datos[2].trim(), formateador);
        int duracion = Integer.valueOf(datos[3].trim());
        HashSet<Estilo> estilos = new HashSet<>();
        for (int i = 4; i < datos.length; i++) {
            String estilo = datos[i].toUpperCase().trim();
            estilos.add(Estilo.valueOf(estilo));
        }
        return new Festival(nombre, lugar, fecha, duracion, estilos);
    }
}
