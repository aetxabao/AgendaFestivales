package festivales.io;

import festivales.modelo.AgendaFestivales;
import festivales.modelo.Estilo;
import festivales.modelo.Festival;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Scanner;

/**
 * La clase contiene méodos estáticos que permiten
 * cargar la agenda de festivales leyendo los datos desde
 * un fichero
 *
 * @author Joan García Pedraza
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
       //DONE
        String[] a = lineaFestival.split(":");
        String nombre = nombre(a[0]);
        String lugar = lugar(a[1]);
        LocalDate fecha = fecha(a[2]);
        int duracion = duracion(a[3]);
        HashSet<Estilo> estilos = estilos(lineaFestival);
        Festival festival = new Festival(nombre, lugar, fecha, duracion, estilos);
        return festival;
    }

    private static String nombre(String nombre){
        String p = "";
        nombre = nombre.trim();
        String[] s = nombre.split("//s+");
        for (int i = 0; i < s.length; i++) {
            String primera = s[i].substring(0, 1);
            String resto = s[i].substring(1,s[i].length());
            p = primera.toUpperCase() + resto;
        }
        return p;
    }
    private static String lugar(String lugar){
        String l = lugar.trim();
        l = l.toUpperCase();
        return l;
    }
    private static LocalDate fecha (String fecha){
        String f = fecha.trim();
        String[] fech = f.split("-");
        int dia = Integer.parseInt(fech[0]);
        int mes = Integer.parseInt(fech[1]);
        int anyo = Integer.parseInt(fech[2]);
        LocalDate fecha1 = LocalDate.of(anyo, mes, dia);
        return fecha1;
    }
    private static int duracion(String duracion){
        String d = duracion.trim();
        return Integer.parseInt(d);
    }
    private static HashSet<Estilo> estilos(String linea){
        HashSet<Estilo> estilos = new HashSet<>();
        String[] a = linea.split(":");
        Estilo estilo = null;
        for (int i = 4; i < a.length; i++) {
            a[i] = a[i].trim();
            if(a[i].equalsIgnoreCase("hiphop")){
                estilo = Estilo.HIPHOP;
            } else if(a[i].equalsIgnoreCase("indie")){
                estilo = Estilo.INDIE;
            } else if(a[i].equalsIgnoreCase("pop")) {
                estilo = Estilo.POP;
            }else if(a[i].equalsIgnoreCase("rock")){
                estilo = Estilo.ROCK;
            }else if(a[i].equalsIgnoreCase("fusion")){
                estilo = Estilo.FUSION;
            }else if(a[i].equalsIgnoreCase("rap")){
                estilo = Estilo.RAP;
            }else if(a[i].equalsIgnoreCase("electronica")){
                estilo = Estilo.ELECTRONICA;
            }else if(a[i].equalsIgnoreCase("punk")){
                estilo = Estilo.PUNK;
            }else if(a[i].equalsIgnoreCase("blues")){
                estilo = Estilo.BLUES;
            }
            estilos.add(estilo);
        }
        return estilos;
    }
    
   
    
    
}
