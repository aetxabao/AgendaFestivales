package festivales.modelo;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

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
 * Las claves se recuperan en orden alfabético
 *
 */
public class AgendaFestivales {
    private final TreeMap<Mes, ArrayList<Festival>> agenda;
    
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
        Mes mes = festival.getMes();
        if (agenda.containsKey(mes)) {
            ArrayList<Festival> festivales = agenda.get(mes);
            int p = obtenerPosicionDeInsercion(festivales, festival);
            festivales.add(p, festival);
            
        } else {
            ArrayList<Festival> festivales = new ArrayList<>();
            festivales.add(festival);
            agenda.put(mes, festivales);
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
        int i = 0;
        while (i < festivales.size() && festival.getNombre().compareToIgnoreCase(festivales.get(i).getNombre()) > 0) {
            i++;
        }
        return i;
        
    }

    /**
     * Representación textual del festival
     * De forma eficiente
     *  Usa el conjunto de entradas par recorrer el map
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("\nFestivales\n");
        Set<Map.Entry<Mes, ArrayList<Festival>>> entradas = agenda.entrySet();
        for (Map.Entry<Mes, ArrayList<Festival>> entrada : entradas) {
            Mes mes = entrada.getKey();
            sb.append("\n" + mes + "  (" + festivalesEnMes(mes) + " festival" +
                    "/es)\n");
            for (Festival festival : entrada.getValue()) {
                sb.append(festival + "\n");
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
        if (agenda.containsKey(mes)) {
            return agenda.get(mes).size();
        }
        return 0;
    }

    /**
     * Se trata de agrupar todos los festivales de la agenda
     * por estilo.
     * Cada estilo que aparece en la agenda tiene asociada una colección
     * que es el conjunto de nombres de festivales que pertenecen a ese estilo
     * Importa el orden de los nombres en el conjunto
     */
    public TreeMap<Estilo, TreeSet<String>> festivalesPorEstilo() {
        TreeMap<Estilo, TreeSet<String>> porEstilo = new TreeMap<>();
        Set<Mes> meses = agenda.keySet();
        for (Mes mes : meses) {
            ArrayList<Festival> festivalesEnMes = agenda.get(mes);
            for (Festival festival : festivalesEnMes) {
                for (Estilo estilo : festival.getEstilos()) {
                    TreeSet<String> nombres = porEstilo.get(estilo);
                    if (nombres == null) {
                        nombres = new TreeSet<>();
                        porEstilo.put(estilo, nombres);
                    }
                    porEstilo.get(estilo).add(festival.getNombre());
                }
            }

        }

        return porEstilo;
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
        int borrados = 0;
        if (!agenda.containsKey(mes)) {
            return -1;
        }
        ArrayList<Festival> festivalesEnMes = agenda.get(mes);
        Iterator<Festival> it = festivalesEnMes.iterator();
        while (it.hasNext()) {
            Festival festival = it.next();
            if (lugares.contains(festival.getLugar()) && !festival.haConcluido()) {
                it.remove();
                borrados++;
            }
            
        }
        if (festivalesEnMes.isEmpty()) {
            agenda.remove(mes);
        }
        return borrados;
        
    }
}
