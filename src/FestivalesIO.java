

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

/**
 * La clase contiene méodos estáticos que permiten
 * cargar la agenda de festivales leyendo los datos desde
 * un fichero
 * @author Iñigo Gutierrez
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
        String[] lineas = lineaFestival.split(":");
        lineas[0] = lineas[0].trim();
        String[] nombres = lineas[0].split(" ");
        String output = "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < nombres.length; i++) {
            output = nombres[i].substring(0, 1).toUpperCase() + nombres[i].substring(1);
            if(i == nombres.length -1){
                sb.append(output);
            }else{
                sb.append(output).append(" ");
            }
        }
        String nombre = sb.toString();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse(lineas[2].trim(),formatter);
        HashSet<Estilo> estilos = new HashSet<>();
        for (int i = 3; i < lineas.length; i++) {
            Estilo[] lista = Estilo.values();
            for (int j = 0; j < lista.length; j++) {
                if(lista[j].toString().compareToIgnoreCase(lineas[i].trim()) == 0){
                    estilos.add(lista[j]);
                }
            }
        }
        Festival festival = new Festival(nombre,lineas[1].toUpperCase().trim(),localDate,Integer.valueOf(lineas[3].trim()),estilos);
        return festival;
    }

    
   
    
    
}
