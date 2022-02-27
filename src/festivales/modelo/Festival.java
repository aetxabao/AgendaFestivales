package festivales.modelo;

import festivales.io.FestivalesIO;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.HashSet;
import java.util.Locale;



/**
 * Un objeto de esta clase almacena los datos de un
 * festival.
 * Todo festival tiene un nombre, se celebra en un lugar
 * en una determinada fecha, dura una serie de días y
 * se engloba en un conjunto determinado de estilos
 *
 * @author Adrian Garcia Galera
 *
 */
public class Festival {
    private final String nombre;
    private final String lugar;
    private final LocalDate fechaInicio;
    private final int duracion;
    private final HashSet<Estilo> estilos;
    
    
    public Festival(String nombre, String lugar, LocalDate fechaInicio,
                    int duracion, HashSet<Estilo> estilos) {
        this.nombre = nombre;
        this.lugar = lugar;
        this.fechaInicio = fechaInicio;
        this.duracion = duracion;
        this.estilos = estilos;
        
    }

    public String getNombre() {
        return nombre;
    }
    
    public String getLugar() {
        return lugar;
    }
    
    public LocalDate getFechaInicio() {
        return fechaInicio;
    }
    
    public int getDuracion() {
        return duracion;
    }
    
    public HashSet<Estilo> getEstilos() {
        return estilos;
    }
    
    public void addEstilo(Estilo estilo) {
        this.estilos.add(estilo);
        
    }

    /**
     * devuelve el mes de celebración del festival, como
     * valor enumerado
     *
     */
    public Mes getMes() {
        Month mes = getFechaInicio().getMonth();
        String mesEsp = mes.getDisplayName(TextStyle.FULL, new Locale("es", "ES"));
        return Mes.valueOf(mesEsp.toUpperCase());
    }

    /**
     *
     * @param otro
     * @return true si el festival actual empieza
     * en un fecha anterior a otro
     */
    public boolean empiezaAntesQue(Festival otro) {

        return getFechaInicio().isBefore(otro.getFechaInicio());
        
    }

    /**
     *
     * @param otro
     * @return true si el festival actual empieza
     * en un fecha posteior a otro
     */
    public boolean empiezaDespuesQue(Festival otro) {
        
        return getFechaInicio().isAfter(otro.getFechaInicio());
        
    }

    /**
     *
     * @return true si el festival ya ha concluido
     */
    public boolean haConcluido() {
        LocalDate fechaFinal = getFechaInicio().plusDays(getDuracion());
        return fechaFinal.isBefore(LocalDate.now());

    }

    /**
     * Representación textual del festival, exactamente
     * como se indica en el enunciado
     *
     */
    @Override
    public String toString() {

        Festival festivalActual = new Festival("x", "xx", LocalDate.now(), 1, null);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        int diaInicio = getFechaInicio().getDayOfMonth();
        String mes = getMes().toString().substring(0,3).toLowerCase();
        int anio = getFechaInicio().getYear();

        String fechaFinalstr = (getFechaInicio().plusDays(getDuracion())).format(formatter);
        LocalDate fechaFinal = LocalDate.parse(fechaFinalstr, formatter);
        Month month = fechaFinal.getMonth();
        String mesFinal = month.getDisplayName(TextStyle.FULL, new Locale("es", "ES"));
        mesFinal = mesFinal.substring(0,3).toLowerCase();

        int diasRestantes = (getFechaInicio().getDayOfYear()) - (LocalDate.now().getDayOfYear());

        if (empiezaDespuesQue(festivalActual)){
            return "\n" + getNombre() + "     " + getEstilos() + "\n" +
                    getLugar() + "\n" +
                    String.valueOf(diaInicio) + " " + mes + ". " + String.valueOf(anio) +
                    "(quedan " + String.valueOf(diasRestantes) + " dias)\n----------------";
        }else if(haConcluido()){
            return "\n" + getNombre() + "     " + getEstilos() + "\n" +
                    getLugar() + "\n" +
                    String.valueOf(diaInicio) + " " + mes + ". -" + fechaFinal.getDayOfMonth() +
                    " " + mesFinal + ". " + fechaFinal.getYear() +
                    "(concluido)\n----------------";

        }else{
            return "\n" + getNombre() + "     " + getEstilos() + "\n" +
                    getLugar() + "\n" +
                    String.valueOf(diaInicio) + " " + mes + ". -" + fechaFinal.getDayOfMonth() +
                    " " + mesFinal + ". " + fechaFinal.getYear() +
                    "(ON)\n----------------";
        }

        
    }

    /**
     * Código para probar la clase festivales.modelo.Festival
     *
     */
    public static void main(String[] args) {
        System.out.println("Probando clase festivales.modelo.Festival");
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
