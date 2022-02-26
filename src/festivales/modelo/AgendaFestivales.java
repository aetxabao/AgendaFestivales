package festivales.modelo;

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
 * @author Sergio Cobos Lorca
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
        if (!agenda.containsKey(festival.getMes())) {
            ArrayList<Festival> fest = new ArrayList<>();
            fest.add(festival);
            agenda.put(festival.getMes(), fest);
        } else {
            int n = obtenerPosicionDeInsercion(agenda.get(festival.getMes()), festival);
            agenda.get(festival.getMes()).add(n, festival);
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
        int n = 0;
        for (Festival f : festivales) {
            if ((f.getNombre().compareTo(festival.getNombre()) < 0)) {
                n++;
            }
        }
        return n;
        
    }

    /**
     * Representación textual del festival
     * De forma eficiente
     *  Usa el conjunto de entradas par recorrer el map
     */
    @Override
    public String toString() {
        //DONE
        StringBuilder sb = new StringBuilder("Festivales\n");
        Set<Map.Entry<Mes, ArrayList<Festival>>> entries = agenda.entrySet();
        for (Map.Entry<Mes, ArrayList<Festival>> entry : entries) {
            ArrayList<Festival> f = entry.getValue();
            sb.append("\n").append(entry.getKey()).append(" (").append(f.size()).append(" festival/es)\n");
            for (Festival festival : f) {
                sb.append(festival).append("\n");
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
        if (!agenda.containsKey(mes)) {
            return 0;
        } else {
            return agenda.get(mes).size();
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
        TreeMap<Estilo, TreeSet<String>> fest = new TreeMap<>();
        Set<Mes> claves = agenda.keySet();
        for (Mes clave : claves) {
            ArrayList<Festival> f = agenda.get(clave);
            for (Festival festival : f) {
                HashSet<Estilo> estilos = festival.getEstilos();
                for (Estilo estilo : estilos) {
                    if (fest.containsKey(estilo)) {
                        fest.get(estilo).add(festival.getNombre());
                    } else {
                        TreeSet<String> festivals = new TreeSet<>();
                        festivals.add(festival.getNombre());
                        fest.put(estilo, festivals);
                    }
                }
            }
        }
        return fest;
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
        if (!agenda.containsKey(mes)) {
            return -1;
        }
        int n = 0;
        ArrayList<Festival> festivals = agenda.get(mes);
        Iterator<Festival> it = festivals.iterator();
        while (it.hasNext()) {
            Festival f = it.next();
            if (lugares.contains(f.getLugar()) && !f.haConcluido()) {
                it.remove();
                n++;
            }
        }
        if (festivals.isEmpty()) {
            agenda.remove(mes);
        }
        return n;
    }
}
