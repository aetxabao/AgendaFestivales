package festivales.modelo;

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
        if(agenda.containsKey(festival.getMes())){
            ArrayList<Festival> festivales = agenda.get(festival.getMes());
            festivales.add(obtenerPosicionDeInsercion(festivales,festival),festival);
        }else{
            ArrayList<Festival> festivales = new ArrayList<>();
            festivales.add(festival);
            agenda.put(festival.getMes(),festivales);
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
                                           Festival festival){
        int pos =0;
        for (Festival festivale : festivales) {
            if(festival.getNombre().compareToIgnoreCase(festivale.getNombre())<0){
                return pos;
            }
            pos++;
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
        StringBuilder sb = new StringBuilder();
        sb.append("Festivales\n\n");
        Set<Map.Entry<Mes, ArrayList<Festival>>> entradas = agenda.entrySet();
        Iterator<Map.Entry<Mes, ArrayList<Festival>>> it = entradas.iterator();
        while (it.hasNext())
        {
            Map.Entry<Mes, ArrayList<Festival>> entrada = it.next();
            Mes mes = entrada.getKey();
            ArrayList<Festival> festivales = entrada.getValue();
            sb.append(mes.toString()).append(" (").append(festivales.size()).append(" festival/es)\n");
            for (Festival festival : festivales) {
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
     * Si el mes no existe se devuelve -1
     */
    public int festivalesEnMes(Mes mes) {
        int cuantos = 0;
        ArrayList<Festival> lista = agenda.get(mes);
        if(lista == null){
            return 0;
        }
        cuantos = lista.size();
        return cuantos;
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
    public  Map<Estilo, TreeSet<String>>   festivalesPorEstilo() {
       Map<Estilo, TreeSet<String>> estiloFestival = new TreeMap<>();
        Set<Map.Entry<Mes, ArrayList<Festival>>> entradas = agenda.entrySet();
        Iterator<Map.Entry<Mes, ArrayList<Festival>>> it = entradas.iterator();
        while (it.hasNext()) {
            Map.Entry<Mes, ArrayList<Festival>> entrada = it.next();
            ArrayList<Festival> lista = entrada.getValue();
            for (Festival festival : lista) {
                for (Estilo  estilo: festival.getEstilos()) {
                        if(estiloFestival.containsKey(estilo)){
                            TreeSet<String> nombres = estiloFestival.get(estilo);
                            nombres.add(festival.getNombre());
                            estiloFestival.put(estilo,nombres);
                        }else{
                            TreeSet<String> nombres = new TreeSet<>();
                            nombres.add(festival.getNombre());
                            estiloFestival.put(estilo,nombres);
                        }
                }
            }
        }
       return estiloFestival;
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
       if(!agenda.containsKey(mes)){
           return -1;
       }
       ArrayList<Festival> festivales = agenda.get(mes);
       int cuantos = 0;
       for (int i = festivales.size()-1; i >=0; i--) {
            if(!festivales.get(i).haConcluido()){
                if(lugares.contains(festivales.get(i).getLugar())){
                    festivales.remove(festivales.get(i));
                    cuantos++;
                }
            }
        }
        if(festivales.size()!=0){
            agenda.put(mes,festivales);
        }else{
            agenda.remove(mes);
        }
       return cuantos;
    }
}
