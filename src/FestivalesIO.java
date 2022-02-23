

import java.util.Date;
import java.util.HashSet;
import java.util.Scanner;

/**
 * La clase contiene méodos estáticos que permiten
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
       //TODO
        HashSet<Estilo> estilo = getEstilo(lineaFestival);
        String nombre = getNombre(lineaFestival);
        String lugar = getLugar(lineaFestival);
        Date fecha = getFecha(lineaFestival);
        int tiempo = getTiempo(lineaFestival);

        Festival festival = new Festival(lugar, fecha, tiempo, estilo);
        return festival;
    }

    private static String getNombre(String nombre) {
        String[] n = nombre.split(":");
        String nom = "";


    }

    public static HashSet<Estilo> getEstilo(String estilos){
        HashSet<Estilo> estilo = new HashSet<>();
        String[] linea = estilos.split(":");
       for (int i = 0; i < linea.length; i++) {
           estilo.add(Estilo.valueOf(linea[i].trim()));
       }
       return estilo;
   }
    
    
}
