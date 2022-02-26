

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
 */
public class FestivalesIO
{
    public static void cargarFestivales(AgendaFestivales agenda)
    {
        Scanner sc = null;
        try
        {
            sc = new Scanner(FestivalesIO.class.
                    getResourceAsStream("/festivales.csv"));
            while (sc.hasNextLine())
            {
                String lineaFestival = sc.nextLine();
                Festival festival = parsearLinea(lineaFestival);
                agenda.addFestival(festival);
            }
        }
        finally
        {
            if (sc != null)
            {
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
    public static Festival parsearLinea(String lineaFestival)
    {
        String nombre;
        String lugar;
        LocalDate fechaInicio;
        int duracion = 0;
        HashSet<Estilo> estilos = new HashSet<>();

        // Divido la linea de entrada separando por ':'
        String[] partes = lineaFestival.split(":");

        // Metodos privados:
        nombre = parsearNombre(partes[0]);
        lugar = parsearLugar(partes[1]);
        fechaInicio = parsearFecha(partes[2]);
        duracion = parsearDuracion(partes[3]);
        estilos = parsearEstilos(Arrays.copyOfRange(partes, 4, partes.length));

        return new Festival(nombre,lugar,fechaInicio,duracion,estilos);
    }

    //==============================================================================

    private static String parsearNombre(String lineaNombre)
    {
        String salida = "";

        // Elimino los posibles espacios iniciales y finales
        lineaNombre.trim();
        // Divido en array todas las posibles cadenas por " ".
        String[] partesNombre = lineaNombre.split(" ");

        // Para cada cadena,
        for (String parte : partesNombre)
        {
            // y siempre que no sea nula
            if(parte.length() != 0)
            {
                // si esa parte no es 'festivales.modelo.Festival', la capitalizo
                if ( ! parte.equalsIgnoreCase("festivales.modelo.Festival"))
                {
                    parte = parte.substring(0, 1).toUpperCase() + parte.substring(1);
                    salida += parte + " ";
                }
            }
        }


        return salida;
    }

    private static String parsearLugar(String lineaLugar)
    {
        return lineaLugar.trim().toUpperCase();
    }

    private static LocalDate parsearFecha(String lineaFecha)//  26-03-2021
    {
        LocalDate fechaLocal;
        lineaFecha = lineaFecha.trim();
        fechaLocal = LocalDate.parse(lineaFecha, DateTimeFormatter.ofPattern("dd-MM-yyy",Locale.ROOT));
        return fechaLocal;
    }

    private static int parsearDuracion(String lineaDuracion)
    {
        return Integer.valueOf(lineaDuracion.trim());
    }

    private static HashSet<Estilo> parsearEstilos(String[] arrayEstilos)// rock: punk:hiphop
    {
        HashSet<Estilo> estilos = new HashSet<>();

        for (String s : arrayEstilos)
        {
            estilos.add(Estilo.valueOf(s.trim().toUpperCase()));
        }

        return estilos;
    }

    // test propio
    public static void main(String[] args)
    {
        Festival unFestival = FestivalesIO.parsearLinea("Pueblos blancos Music festivales.modelo.Festival:  ronda:28-07-2022: 4: blues: rock");
        System.out.println(unFestival.toString());
    }
}
