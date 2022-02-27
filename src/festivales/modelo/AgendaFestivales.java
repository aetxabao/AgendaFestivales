package festivales.modelo;

import festivales.modelo.Estilo;
import festivales.modelo.Festival;
import festivales.modelo.Mes;

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
 * @author Adrian Garcia Galera
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
        if (agenda.containsKey(festival.getMes())){

            int pos = obtenerPosicionDeInsercion(agenda.get(festival.getMes()), festival);
            agenda.get(festival.getMes()).add(pos, festival);
        }else{
            ArrayList<Festival> festNuevo = new ArrayList<>();
            festNuevo.add(festival);

            agenda.put(festival.getMes(), festNuevo);
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
        ArrayList<String> nombres = new ArrayList<>();
        nombres.add(festival.getNombre());

        for (Festival fest:
             festivales) {
            nombres.add(fest.getNombre());
        }

        Collections.sort(nombres);
        return nombres.indexOf(festival.getNombre());
        
    }

    /**
     * Representación textual del festival
     * De forma eficiente
     *  Usa el conjunto de entradas par recorrer el map
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        Set<Map.Entry<Mes, ArrayList<Festival>>> entradas = agenda.entrySet();
        Iterator<Map.Entry<Mes, ArrayList<Festival>>> it = entradas.iterator();

        while (it.hasNext()){
            Map.Entry<Mes, ArrayList<Festival>> entrada = it.next();
            sb.append("\n****************\n");
            sb.append(entrada.getKey()).append(" (" + festivalesEnMes(entrada.getKey()) + " festival/es)");
            sb.append("\n****************\n\n");
            sb.append(entrada.getValue());
            sb.append("\n");
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

        if (agenda.containsKey(mes)){
            return agenda.get(mes).size();
        }else{
            return 0;
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
    public  TreeMap<Estilo, TreeSet<String>>  festivalesPorEstilo() {

        TreeMap<Estilo, TreeSet<String>> entries = new TreeMap<>();

        for (Estilo estilo : Estilo.values()) {
            entries.put(estilo, new TreeSet<String>());
        }

        for (Mes mes : agenda.keySet()) {
            for (int i = 0; i < agenda.get(mes).size(); i++) {
                for (Estilo estilo : Estilo.values()) {
                    if (agenda.get(mes).get(i).getEstilos().contains(estilo)){
                        entries.get(estilo).add(agenda.get(mes).get(i).getNombre());
                    }
                }
            }
        }

        return entries;

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

        int contador = 0;

        if (agenda.keySet().contains(mes)){

            Iterator<Festival> it = agenda.get(mes).iterator();

            while (it.hasNext()){
                Festival fest = it.next();
                    if (lugares.contains(fest.getLugar()) && !(fest.haConcluido())){
                        it.remove();
                        contador++;
                    }
                }

            }else{
            return -1;
        }
        if (agenda.get(mes).size() == 0){
            agenda.remove(mes);
        }

        return contador;

        }


}
