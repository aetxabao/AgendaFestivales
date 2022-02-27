
import com.sun.jdi.event.StepEvent;

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
        if(agenda.containsKey(festival.getMes())){
            agenda.get(festival.getMes()).add(obtenerPosicionDeInsercion(agenda.get(festival.getMes()), festival), festival);
        } else {
          agenda.put(festival.getMes(), new ArrayList<>());
          agenda.get(festival.getMes()).add(festival);
        }
    }

    /**
     *
     * @param festivales una lista de festivales
     * @param festival
     * @return la posición en la que debería ir el nuevo festival
     * de forma que la lista quedase ordenada por nombre
     */
    private int obtenerPosicionDeInsercion(ArrayList<Festival> festivales, Festival festival) {
        int aux = 0;

        for (Festival festivale : festivales) {
            if (festival.getNombre().compareTo(festivales.get(aux).getNombre()) <= 0){
                return aux;
            }
            aux++;
        }
        return aux;
    }

    /**
     * Representación textual del festival
     * De forma eficiente
     *  Usa el conjunto de entradas par recorrer el map
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        return sb.toString();
    }

    /**
     *
     * @param mes el mes a considerar
     * @return la cantidad de festivales que hay en ese mes
     * Si el mes no existe se devuelve -1
     */
    public int festivalesEnMes(Mes mes) {
       if(agenda.containsKey(mes)){
            return agenda.get(mes).size();
       }else{
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
        TreeMap<Estilo, TreeSet<String>> si = new TreeMap<>();
        Set<Mes> mes = agenda.keySet();

        for (Festival festival : agenda.get(mes)) {
            for( Estilo estilos : festival.getEstilos()) {
                TreeSet<String> nombres = new TreeSet<>();
                nombres.add(festival.getNombre());
                si.put(estilos, nombres);
            }
        }
        
        return si;
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
       int aux = 0;
        if (!agenda.containsKey(mes)) {
            return -1;
        }
        ArrayList<Festival> arrayFestivales = agenda.get(mes);
        for (int i = 0; i < arrayFestivales.size(); i++) {
            if(lugares.contains(arrayFestivales.get(i).getLugar())){
                arrayFestivales.remove(i);
                aux++;
            }
        }
        if(arrayFestivales.size() == 0){
            agenda.remove(mes);
        }
        return aux;
    }
}
