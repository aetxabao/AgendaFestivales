package festivales.modelo;

import java.util.*;


/**
 * Esta clase guarda una agenda con los festivales programados
 * en una serie de meses
 *
 * La agenda guardalos festivales en una colecci�n map
 * La clave del map es el mes (un enumerado festivales.modelo.festivales.modelo.Mes)
 * Cada mes tiene asociados en una colecci�n ArrayList
 * los festivales  de ese mes
 *
 * Solo aparecen los meses que incluyen alg�n festival
 *
 * Las claves se recuperan en orden alfab�ico
 * @autor Evelin Virunurm
 */
public class AgendaFestivales {
    private TreeMap<Mes, ArrayList<Festival>> agenda;

    public AgendaFestivales() {
        this.agenda = new TreeMap<>();
    }

    /**
     * a�ade un nuevo festival a la agenda
     *
     * Si la clave (el mes en el que se celebra el festival)
     * no existe en la agenda se crear� una nueva entrada
     * con dicha clave y la colecci�n formada por ese �nico festival
     *
     * Si la clave (el mes) ya existe se a�ade el nuevo festival
     * a la lista de festivales que ya existe ese ms
     * insert�ndolo de forma que quede ordenado por nombre de festival.
     * Para este segundo caso usa el m�todo de ayuda
     * obtenerPosicionDeInsercion()
     *
     */
    public void addFestival(Festival festival) {
        if (!this.agenda.containsKey(festival.getMes())) {
            this.agenda.put(festival.getMes(), new ArrayList<>());
            this.agenda.get(festival.getMes()).add(festival);
            return;
        }
        this.agenda.get(festival.getMes()).add(obtenerPosicionDeInsercion(this.agenda.get(festival.getMes()), festival) ,festival);
    }

    /**
     *
     * @param festivales una lista de festivales
     * @param festival
     * @return la posici�n en la que deber�a ir el nuevo festival
     * de forma que la lista quedase ordenada por nombre
     */
    private int obtenerPosicionDeInsercion(ArrayList<Festival> festivales,
                                           Festival festival) {
        int i = 0;
        while (i < festivales.size()) {
            if (festival.getNombre().compareTo(festivales.get(i).getNombre()) < 0 || festival.getNombre().compareTo(festivales.get(i).getNombre()) == 0) {
                return i;
            }
            i++;
        }

        return i;
    }

    /**
     * Representaci�n textual del festival
     * De forma eficiente
     *  Usa el conjunto de entradas par recorrer el map
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Set<Map.Entry<Mes, ArrayList<Festival>>> entries = this.agenda.entrySet();
        Iterator<Map.Entry<Mes, ArrayList<Festival>>> it = entries.iterator();
        sb.append("Festivales\n\n");

        while (it.hasNext()) {
            Map.Entry<Mes, ArrayList<Festival>> entry = it.next();
            sb.append(entry.getKey()).append(" (").append(entry.getValue().size()).append(" festival/es)\n");
            for (Festival festival: entry.getValue()) {
                sb.append(festival).append("\n");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     *
     * @param mes el mes a considerar
     * @return la cantidad de festivales que hay en ese mes
     * Si el mes no existe se devuelve 0
     */
    public int festivalesEnMes(Mes mes) {
        if (!this.agenda.containsKey(mes)) {
            return 0;
        }
        return this.agenda.get(mes).size();
    }

    /**
     * Se trata de agrupar todos los festivales de la agenda
     * por estilo.
     * Cada estilo que aparece en la agenda tiene asociada una colecci�n
     * que es el conjunto de nombres de festivales que pertenecen a ese estilo
     * Importa el orden de los nombres en el conjunto
     *
     * Identifica el tipo exacto del valor de retorno
     */
    public TreeMap<Estilo, TreeSet<String>> festivalesPorEstilo() {
        TreeMap<Estilo, TreeSet<String>> festivalesMapeados = new TreeMap<>();
        Estilo[] estilos = Estilo.values();
        for (int i = 0; i < estilos.length; i++) {
            festivalesMapeados.put(estilos[i], new TreeSet<String>());
        }

        Set<Map.Entry<Mes, ArrayList<Festival>>> entries = this.agenda.entrySet();
        Iterator<Map.Entry<Mes, ArrayList<Festival>>> it = entries.iterator();

        while (it.hasNext()) {
            Map.Entry<Mes, ArrayList<Festival>> entry = it.next();
            for (Festival festival: entry.getValue()) {
                for (Estilo estilo: festival.getEstilos()) {
                    festivalesMapeados.get(estilo).add(festival.getNombre());
                }
            }
        }

        return festivalesMapeados;
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
        if (!this.agenda.containsKey(mes)) {
            return -1;
        }
        int count = 0;
        Iterator<Festival> it = this.agenda.get(mes).iterator();

        while (it.hasNext()) {
            Festival festival = it.next();
            if (!festival.haConcluido() && lugares.contains(festival.getLugar())) {
                it.remove();
                count++;
            }
        }

        if (this.agenda.get(mes).size() == 0) {
            this.agenda.remove(mes);
        }

        return count;
    }
}
