
import java.util.*;


/**
 * Esta clase guarda una agenda con los festivales programados
 * en una serie de meses
 *
 * La agenda guardalos festivales en una colección map
 * La clave del map es el mes (un enumerado festivales.modelo.Mes)
 * Cada mes tiene asociados en una colección ArrayList
 * los festivales  de ese mes
 *
 * Solo aparecen los meses que incluyen algún festival
 *
 * Las claves se recuperan en orden alfabéico
 *
 * @Autor Juan Garbayo
 *
 */



public class AgendaFestivales {
    private TreeMap<Mes, ArrayList<Festival>> agenda;
    
    public AgendaFestivales() {
        this.agenda = new TreeMap<>();
    }

    /**
     * añade un nuevo festival a la agenda
     *
     * Si la clave (el mes en el que se celebra el festival)
     * no existe en la agenda se creará una nueva entrada
     * con dicha clave y la colección formada por ese único festival
     *
     * Si la clave (el mes) ya existe se añade el nuevo festival
     * a la lista de festivales que ya existe ese ms
     * insertándolo de forma que quede ordenado por nombre de festival.
     * Para este segundo caso usa el método de ayuda
     * obtenerPosicionDeInsercion()
     *
     */
    public void addFestival(Festival festival) {
        //DONE
        if (agenda.containsKey(festival.getMes())) {
            agenda.get(festival.getMes()).add(obtenerPosicionDeInsercion(agenda.get(festival.getMes()), festival), festival);
        } else {
            ArrayList<Festival> festivales = new ArrayList<>();
            festivales.add(festival);
            agenda.put(festival.getMes(), festivales);
        }
    }

    /**
     *
     * @param festivales una lista de festivales
     * @param festival
     * @return la posición en la que debería ir el nuevo festival
     * de forma que la lista quedase ordenada por nombre
     */
    private int obtenerPosicionDeInsercion(ArrayList<Festival> festivales,
                                           Festival festival) {
       //DONE
        int pos = 0;
        for (Festival festivale : festivales) {
            if (festival.getNombre().compareTo(festivale.getNombre()) > 0) {
                pos++;
            }
        }
        return pos;
        
    }

    /**
     * Representación textual del festival
     * De forma eficiente
     *  Usa el conjunto de entradas par recorrer el map
     */
    @Override
    public String toString() {
        //DONE
        StringBuilder sb = new StringBuilder();
        Set<Map.Entry<Mes, ArrayList<Festival>>> entries = agenda.entrySet();
        for (Map.Entry<Mes, ArrayList<Festival>> entry : entries) {
            sb.append("Festivales\n\n").append(entry.getKey()).append(" ").append("(").append(festivalesEnMes(entry.getKey())).append("festival/es").append(")\n");
            ArrayList<Festival> festivales = entry.getValue();
            for (Festival festivale : festivales) {
                sb.append(festivale).append("\n");
            }
        }
        return sb.toString();
    }

    /**
     *
     * @param mes el mes a considerar
     * @return la cantidad de festivales que hay en ese mes
     * Si el mes no existe se devuelve -1
     */
    public int festivalesEnMes(Mes mes) {
       //DONE
        if (agenda.containsKey(mes)) {
            return agenda.get(mes).size();
        } else {
            return -1;
        }
    }

    /**
     * Se trata de agrupar todos los festivales de la agenda
     * por estilo.
     * Cada estilo que aparece en la agenda tiene asociada una colección
     * que es el conjunto de nombres de festivales que pertenecen a ese estilo
     * Importa el orden de los nombres en el conjunto
     *
     * Identifica el tipo exacto del valor de retorno
     */
    public TreeMap<Estilo, TreeSet<String>> festivalesPorEstilo() {
       //DONE
        TreeMap<Estilo, TreeSet<String>> mapaPorEstilos = new TreeMap<>();
        Set<Mes> meses = agenda.keySet();
        for (Mes mes : meses) {
            ArrayList<Festival> festivales = agenda.get(mes);
            for (Festival festival : festivales) {
                HashSet<Estilo> estilos = festival.getEstilos();
                for (Estilo estilo : estilos) {
                    if (mapaPorEstilos.containsKey(estilo)) {
                        mapaPorEstilos.get(estilo).add((festival.getNombre()));
                    } else {
                        TreeSet<String> nombres = new TreeSet<>();
                        nombres.add(festival.getNombre());
                        mapaPorEstilos.put(estilo, nombres);
                    }
                }
            }
        }
        return mapaPorEstilos;
    }

    /**
     * Se cancelan todos los festivales organizados en alguno de los
     * lugares que indica el conjunto en el mes indicado. Los festivales
     * concluidos o que no empezados no se tienen en cuenta
     * Hay que borrarlos de la agenda
     * Si el mes no existe se devuelve -1
     *
     * Si al borrar de un mes los festivales el mes queda con 0 festivales
     * se borra la entrada completa del map
     */
    public int cancelarFestivales(HashSet<String> lugares, Mes mes) {
       //DONE
        int contador = 0;
        if (!agenda.containsKey(mes)) {
            return -1;
        }
        ArrayList<Festival> festivales = new ArrayList<>();
        for (Festival festival : festivales) {
            if (lugares.contains(festival.getLugar())) {
                //No se como borrar un festival
                contador++;
            }
        }
        return contador;
    }
}
