package festivales.modelo;

import festivales.io.FestivalesIO;

import java.time.LocalDate;
import java.util.HashSet;

/**
 * Un objeto de esta clase almacena los datos de un
 * festival.
 * Todo festival tiene un nombre, se celebra en un lugar
 * en una determinada fecha, dura una serie de días y
 * se engloba en un conjunto determinado de estilos
 *
 * @author Joan García Pedraza
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
     */
    public Mes getMes() {
        //DONE
        Mes mes = null;
        switch (fechaInicio.getMonth()) {
            case JANUARY -> mes = Mes.ENERO;
            case FEBRUARY -> mes = Mes.FEBRERO;
            case MARCH -> mes = Mes.MARZO;
            case APRIL -> mes = Mes.ABRIL;
            case MAY -> mes = Mes.MAYO;
            case JUNE -> mes = Mes.JUNIO;
            case JULY -> mes = Mes.JULIO;
            case AUGUST -> mes = Mes.AGOSTO;
            case SEPTEMBER -> mes = Mes.SEPTIEMBRE;
            case OCTOBER -> mes = Mes.OCTUBRE;
            case NOVEMBER -> mes = Mes.NOVIEMBRE;
            case DECEMBER -> mes = Mes.DICIEMBRE;
        }
        return mes;
    }

    /**
     * @param otro
     * @return true si el festival actual empieza
     * en un fecha anterior a otro
     */
    public boolean empiezaAntesQue(Festival otro) {
        //DONE
        return fechaInicio.isBefore(otro.fechaInicio);
    }

    /**
     * @param otro
     * @return true si el festival actual empieza
     * en un fecha posteior a otro
     */
    public boolean empiezaDespuesQue(Festival otro) {
        //DONE
        return fechaInicio.isAfter(otro.fechaInicio);
    }

    /**
     * @return true si el festival ya ha concluido
     */
    public boolean haConcluido() {
        //DONE
        return fechaInicio.isBefore(LocalDate.now());
    }

    /**
     * Representación textual del festival, exactamente
     * como se indica en el enunciado
     */
    @Override
    public String toString() {
        //DONE
        StringBuilder sb = new StringBuilder();
        sb.append(nombre).append(String.format("%-20s", " ")).append(estilos.toString()).append("\n").
                append(lugar).append("\n");
        if (duracion <= 1) {
            sb.append(fechaInicio.getDayOfMonth()).append(" ").
                    append(fechaInicio.getMonth().toString().toLowerCase().substring(0, 3)).append(".").
                    append(fechaInicio.getYear());
        }
        if (duracion > 1) {
            sb.append(fechaInicio.getDayOfMonth()).append(" ").
                    append(fechaInicio.getMonth().toString().toLowerCase().substring(0, 3)).append(".").append(" - ").append(fechaInicio.plusDays(duracion).getDayOfMonth()).append(" ").
                    append(fechaInicio.plusDays(duracion).getMonth().toString().toLowerCase().substring(0, 3)).append(".").
                    append(fechaInicio.plusDays(duracion).getYear());
        }
        if (fechaInicio.plusDays(duracion).isBefore(LocalDate.now())) {
            sb.append("(concluido)");
        }
        if (LocalDate.now().isBefore(fechaInicio.plusDays(duracion)) && LocalDate.now().isAfter(fechaInicio)) {
            sb.append("(ON)");
        }
        if (fechaInicio.isAfter(LocalDate.now())) {
            sb.append(" (quedan ").append(fechaInicio.getDayOfMonth() - LocalDate.now().getDayOfMonth()).append(" dias)");
        }

        sb.append("\n");

        sb.append("------------------------------------------------------------");


        return sb.toString();

    }

    /**
     * Código para probar la clase festivales.modelo.Festival
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
