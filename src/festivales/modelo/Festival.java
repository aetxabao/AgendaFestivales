package festivales.modelo;

import festivales.io.FestivalesIO;

import java.time.LocalDate;
import java.util.HashSet;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * Un objeto de esta clase almacena los datos de un
 * festival.
 * Todo festival tiene un nombre, se celebra en un lugar
 * en una determinada fecha, dura una serie de días y
 * se engloba en un conjunto determinado de estilos
 *
 * @author Sergio Cobos Lorca
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
        //DONE
        return Mes.values()[fechaInicio.getMonthValue() - 1];
    }

    /**
     *
     * @param otro
     * @return true si el festival actual empieza
     * en un fecha anterior a otro
     */
    public boolean empiezaAntesQue(Festival otro) {
        //DONE
        return fechaInicio.isBefore(otro.getFechaInicio());
    }

    /**
     *
     * @param otro
     * @return true si el festival actual empieza
     * en un fecha posteior a otro
     */
    public boolean empiezaDespuesQue(Festival otro) {
        //DONE
        return fechaInicio.isAfter(otro.getFechaInicio());
    }

    /**
     *
     * @return true si el festival ya ha concluido
     */
    public boolean haConcluido() {
        //DONE
        return fechaInicio.plusDays(duracion).isBefore(LocalDate.now());
    }

    /**
     * Representación textual del festival, exactamente
     * como se indica en el enunciado
     *
     */
    @Override
    public String toString() {
       //DONE
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-32s",nombre)).append(estilos.toString());
        sb.append("\n").append(lugar).append("\n");
        int dia = fechaInicio.getDayOfMonth();
        if (dia < 10) {
            sb.append(0);
        }
        sb.append(dia).append(" ");
        String mes = getMes().toString().toLowerCase().substring(0, 3).concat(".");
        sb.append(mes);
        if (duracion != 1) {
            sb.append(" - ").append(fechaInicio.plusDays(duracion).getDayOfMonth()).append(" ");
            sb.append(Mes.values()[fechaInicio.plusDays(duracion).getMonthValue() - 1].toString().toLowerCase().substring(0, 3).concat("."));
        }
        sb.append(" ").append(fechaInicio.getYear());
        if (haConcluido()) {
            sb.append(" (concluido)");
        } else if (fechaInicio.isAfter(LocalDate.now())) {
            long cuanto = DAYS.between(LocalDate.now(), fechaInicio);
            sb.append(" (quedan ").append(cuanto).append(" días)");
        } else {
            sb.append(" (ON)");
        }
        sb.append("\n----------------------------------------------------");
        return sb.toString();
    }

    /**
     * Código para probar la clase Festival
     *
     */
    public static void main(String[] args) {
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