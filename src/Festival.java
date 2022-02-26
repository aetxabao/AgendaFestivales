
import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Locale;

/**
 * Un objeto de esta clase almacena los datos de un
 * festival.
 * Todo festival tiene un nombre, se celebra en un lugar
 * en una determinada fecha, dura una serie de días y
 * se engloba en un conjunto determinado de estilos
 *
 */
public class Festival
{
    private final String nombre;
    private final String lugar;
    private final LocalDate fechaInicio;
    private final int duracion;
    private final HashSet<Estilo> estilos;
    
    
    public Festival(String nombre, String lugar, LocalDate fechaInicio,
                    int duracion, HashSet<Estilo> estilos)
    {
        this.nombre = nombre;
        this.lugar = lugar;
        this.fechaInicio = fechaInicio;
        this.duracion = duracion;
        this.estilos = estilos;
    }
    
    public String getNombre()
    {
        return nombre;
    }
    
    public String getLugar()
    {
        return lugar;
    }
    
    public LocalDate getFechaInicio()
    {
        return fechaInicio;
    }
    
    public int getDuracion()
    {
        return duracion;
    }
    
    public HashSet<Estilo> getEstilos()
    {
        return estilos;
    }
    
    public void addEstilo(Estilo estilo)
    {
        this.estilos.add(estilo);
    }

    /**
     * devuelve el mes de celebración del festival, como
     * valor enumerado
     */
    public Mes getMes()
    {
        int numMes = fechaInicio.getMonthValue();

        return Mes.values()[numMes - 1];
    }

    /**
     * @return true si el festival actual empieza
     * en un fecha anterior a otro
     * @param otro
     */
    public boolean empiezaAntesQue(Festival otro)
    {
        return fechaInicio.isBefore(otro.getFechaInicio());
    }

    /**
     * @param otro
     * @return true si el festival actual empieza
     * en un fecha posteior a otro
     */
    public boolean empiezaDespuesQue(Festival otro)
    {
        return this.getFechaInicio().isAfter(otro.getFechaInicio());
    }

    /**
     * @return true si el festival ya ha concluido
     */
    public boolean haConcluido()
    {
        LocalDate fechaFin = this.getFechaInicio().plusDays(this.duracion);
        return fechaFin.isBefore(LocalDate.now());
    }

    /**
     * Representación textual del festival, exactamente
     * como se indica en el enunciado
     */
    @Override
    public String toString()
    {
        String salida = "\n";

        salida += this.getNombre() + "      ";
        salida += this.getEstilos();
        salida += "\n" + this.getLugar();
        salida += "\n" + lineaFecha() + "\n";

        return salida;
    }

    /**
     * Construlle la linea de las fechas
     */
    private String lineaFecha()
    {
        String lineaFecha;
        LocalDate fechaInicio = getFechaInicio();
        LocalDate fechaFin = fechaInicio.plusDays(duracion);
        LocalDate ahora = LocalDate.now();
        // si solo dura un dia, la fecha de fin es la misma que la de inicio)

        // Escribo la fecha de inicio
        lineaFecha = "" + getFechaInicio().getDayOfMonth();
        lineaFecha += " " + iniciales(fechaInicio);

        // Si dura mas de un dia
        if(duracion > 1)
        {
            // Escribo la fecha de fin
            lineaFecha += " - " + fechaFin.getDayOfMonth();
            lineaFecha += " " + iniciales(fechaFin);
        }

        // Escribo el año
        lineaFecha += " " + fechaFin.getYear();

        // si ahora es despues de inicio
        if(ahora.isAfter(fechaInicio))
        {
            // o bien aun no ha acabado, sigue en curso
            if(ahora.isBefore(fechaFin))
            {
                lineaFecha += " (ON)";
            }
            // o ya ha concluido
            else
            {
                lineaFecha += " (concluido)";
            }
        }
        else
        {
            lineaFecha += " (quedan " + ChronoUnit.DAYS.between(ahora,fechaInicio) + " dias)";
        }

        return lineaFecha;
    }

    /**
     * Devuelve las tres primeras iniciales del mes en 'fecha'
     */
    private String iniciales(LocalDate fecha)
    {
        String nombreMes = Mes.values()[ fecha.getMonthValue() - 1].toString();

        return nombreMes.substring(0,3).toLowerCase() + ".";
    }

    /**
     * Código para probar la clase Festival
     *
     */
    public static void main(String[] args)
    {
        System.out.println("Probando clase Festival");
        String datosFestival = "Gazpatxo Rock : " +
                "valencia: 28-02-2022  :1  :rock" +
                ":punk " +
                ": hiphop ";
        Festival f1 = FestivalesIO.parsearLinea(datosFestival);
        System.out.println(f1);
        
        datosFestival = "black sound fest:badajoz:05-02-2022:  21" +
                ":rock" + ":  blues";
        Festival f2 = FestivalesIO.parsearLinea(datosFestival);
        System.out.println(f2);
    
        datosFestival = "guitar bcn:barcelona: 28-01-2022 :  170" +
                ":indie" + ":pop:fusion";
        Festival f3 = FestivalesIO.parsearLinea(datosFestival);
        System.out.println(f3);
    
        datosFestival = "  benidorm fest:benidorm:26-01-2022:3" +
                ":indie" + ": pop  :rock";
        Festival f4 = FestivalesIO.parsearLinea(datosFestival);
        System.out.println(f4);
      
        
        System.out.println("\nProbando empiezaAntesQue() empiezaDespuesQue()" +
                "\n");
        if (f1.empiezaAntesQue(f2)) {
            System.out.println(f1.getNombre() + " empieza antes que " + f2.getNombre());
        } else if (f1.empiezaDespuesQue(f2)) {
            System.out.println(f1.getNombre() + " empieza después que " + f2.getNombre());
        } else {
            System.out.println(f1.getNombre() + " empieza el mismo día que " + f2.getNombre());
        }

        System.out.println("\nProbando haConcluido()\n");
        System.out.println(f4);
        System.out.println(f4.getNombre() + " ha concluido? " + f4.haConcluido());
        System.out.println(f1);
        System.out.println(f1.getNombre() + " ha concluido? " + f1.haConcluido());
    }
}
