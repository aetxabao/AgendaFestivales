

import java.time.LocalDate;
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
    public static Festival parsearLinea(String lineaFestival)
    {
       //TODO
        String nombre;
        String lugar;
        LocalDate fechaInicio = LocalDate.now();
        int duracion = 0;
        HashSet<Estilo> estilos = new HashSet<>();

        // Divido la linea de entrada separando por ':'
        String[] partes = lineaFestival.split(":");
        // Metodos privados:
        nombre = parsearNombre(partes[0]);
        lugar = parsearLugar(partes[1]);
        fechaInicio = parsearFecha(partes[2]);


        return new Festival(nombre,lugar,fechaInicio,duracion,estilos);
    }

    private static String parsearNombre(String lineaNombre)
    {
        String salida = "";
        // Elimino los posibles espacios iniciales y finales
        lineaNombre.trim();
        // Divido en array todas las posibles cadenas por " ".
        String[] partesNombre = lineaNombre.split(" ");

        // Para cada cadena, hago
        for (String parte : partesNombre)
        {
            // si una parte 'festivales.modelo.Festival', lo borra
            if(parte.equalsIgnoreCase("festivales.modelo.Festival"))
            {
                parte = "";
            }
            // si no, la capitalizo y añado a 'salida'
            else
            {
                parte = parte.substring(0,1).toUpperCase() + parte.substring(1);
                salida += parte + " ";
            }
        }


        return salida;
    }

    private static String parsearLugar(String lineaLugar)
    {

    }

    public static void main(String[] args)
    {
        String salida = FestivalesIO.parsearNombre("festivales.modelo.Festival blues de asturias");
        System.out.println(salida);
    }
}
