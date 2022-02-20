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
 * @author Sergio Cobos Lorca
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
        String lugar = datos[1].toUpperCase().trim();
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate fecha = LocalDate.parse(datos[2].trim(), formateador);
        int duracion = Integer.valueOf(datos[3].trim());
        HashSet<Estilo> estilos = new HashSet<>();
        for (int i = 4; i < datos.length; i++) {
            String estilo = datos[i].toUpperCase().trim();
            switch (estilo) {
                case "HIPHOP":
                    estilos.add(Estilo.HIPHOP);
                    break;
                case "INDIE":
                    estilos.add(Estilo.INDIE);
                    break;
                case "POP":
                    estilos.add(Estilo.POP);
                    break;
                case "ROCK":
                    estilos.add(Estilo.ROCK);
                    break;
                case "FUSION":
                    estilos.add(Estilo.FUSION);
                    break;
                case "RAP":
                    estilos.add(Estilo.RAP);
                    break;
                case "ELECTRONICA":
                    estilos.add(Estilo.ELECTRONICA);
                    break;
                case "PUNK":
                    estilos.add(Estilo.PUNK);
                    break;
                case "BLUES":
                    estilos.add(Estilo.BLUES);
                    break;
            }
        }
        return new Festival(nombre, lugar, fecha, duracion, estilos);
    }


}
