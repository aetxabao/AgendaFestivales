package festivales.modelo;

import java.time.LocalDate;
import java.util.*;


/**
 * Esta clase guarda una agenda con los festivales programados
 * en una serie de meses
 *
 * La agenda guardalos festivales en una colección map
 * La clave del map es el mes (un enumerado festivales.modelo.festivales.modelo.Mes)
 * Cada mes tiene asociados en una colección ArrayList
 * los festivales  de ese mes
 *
 * Solo aparecen los meses que incluyen algún festival
 *
 * Las claves se recuperan en orden alfabéico
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
            if(agenda.containsKey(festival.getMes())){
            ArrayList<Festival> a = agenda.get(festival.getMes());
            a.add(obtenerPosicionDeInsercion(a, festival),festival);
        } else {
            ArrayList<Festival> a = new ArrayList<>();
            a.add(festival);
            agenda.put(festival.getMes(),a);
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
        int posicion = 0;
        for (int i = 0; i < festivales.size(); i++) {
            Festival mayor = festivales.get(0);
            if(festivales.get(i).getNombre().compareTo(mayor.getNombre()) > 0){
                mayor = festivales.get(i);
            }
        }
        return posicion;
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
        sb.append("\n").append("Festivales").append("\n\n");
        for (Map.Entry<Mes, ArrayList<Festival>> entry : entries) {
            sb.append(entry.getKey()).append(" (").append(entry.getValue().size()).append("festival/es)").append("\n");
            for ( Festival e: entry.getValue()) {
                sb.append(e.toString()).append("\n");
            }
            sb.append("\n\n");
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
        int total = 0;
        Set<Map.Entry<Mes, ArrayList<Festival>>> entries = agenda.entrySet();
        for (Map.Entry<Mes, ArrayList<Festival>> entry : entries) {
            if (entry.getKey().equals(mes)){
                total += entry.getValue().size();
            }
        }
        return total;
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
    public  TreeMap<Estilo, TreeSet<String>>   festivalesPorEstilo() {
       //DONE
        TreeMap<Estilo, TreeSet<String>> e = new TreeMap<>();
        for (Mes m : agenda.keySet()) {
            for (int i = 0; i < agenda.get(m).size(); i++) {
                HashSet<Estilo> estilo = agenda.get(m).get(i).getEstilos();
                for (Estilo es : estilo) {
                    if(e.containsKey(es)){
                        TreeSet<String> n = e.get(es);
                        n.add(agenda.get(m).get(i).getNombre());
                    } else {
                        TreeSet<String> n = new TreeSet<>();
                        n.add(agenda.get(m).get(i).getNombre());
                        e.put(es,n);
                    }
                }
            }
        }
        return e;
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
       //TODO
        int cancelados = 0;
        if (!agenda.containsKey(mes)){
            return -1;
        } else {
            Set<Map.Entry<Mes, ArrayList<Festival>>> entries = agenda.entrySet();
            for (Map.Entry<Mes, ArrayList<Festival>> entry : entries) {
                for (int i = 0; i < entry.getValue().size(); i++) {
                    if(lugares.contains(entry.getValue().get(i).getLugar()) &&
                            entry.getValue().get(i).getMes().equals(mes)){
                        entry.getValue().remove(i);
                        cancelados++;
                    }
                }
            }
        }
        return cancelados;
    }
}
