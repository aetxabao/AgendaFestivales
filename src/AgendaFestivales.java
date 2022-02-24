/**
 * @author jgrijalaz - Javier Grijalba
 * @version 24.02.22
 */

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
public class AgendaFestivales
{
    private TreeMap<Mes, ArrayList<Festival>> agenda;
    
    public AgendaFestivales()
    {
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
    public void addFestival(Festival festival)
    {
        Mes mesClave = festival.getMes();
        ArrayList<Festival> festivalesEnMes;
        int posInsercion;

        if(!agenda.containsKey(mesClave))
        {
            festivalesEnMes = new ArrayList<>();
            festivalesEnMes.add(festival);
            agenda.put(mesClave,festivalesEnMes);
        }
        else
        {
            festivalesEnMes = agenda.get(mesClave);
            posInsercion = obtenerPosicionDeInsercion(festivalesEnMes,festival);
            festivalesEnMes.add(posInsercion, festival);
        }
    }

    /**
     * @param festivalesEnMes una lista de festivales
     * @param festival
     * @return la posición en la que debería ir el nuevo festival
     * de forma que la lista quedase ordenada por nombre
     */
    private int obtenerPosicionDeInsercion(ArrayList<Festival> festivalesEnMes,
                                           Festival festival)
    {
        String nuevoF = festival.getNombre();
        String festivalEnI;

        for (int i = 0; i < festivalesEnMes.size(); i++)
        {
            festivalEnI = festivalesEnMes.get(i).getNombre();
            if( nuevoF.compareToIgnoreCase(festivalEnI) < 0 )
            {
                return i;
            }
        }

        return festivalesEnMes.size();
    }

    /**
     * Representación textual del festival
     * De forma eficiente
     * Usa el conjunto de entradas par recorrer el map
     */
    @Override
    public String toString()
    {
        StringBuilder salida = new StringBuilder();
        salida.append("\n");

        if(agenda.isEmpty())
        {
            return "vacio";
        }

        for (Map.Entry<Mes,ArrayList<Festival>> entradaMes : agenda.entrySet())
        {
            // ENERO  (2 festivales)
            salida.append(entradaMes.getKey().toString());
            salida.append(" (");
            salida.append(festivalesEnMes(entradaMes.getKey()));
            salida.append(" festivales) ").append("\n");

            for (Festival fest : entradaMes.getValue())
            {
                salida.append(fest.toString());
                salida.append("------------------------------------------------");
            }

            salida.append("\n\n");
        }

        return salida.toString();
    }

    /**
     * @param mes el mes a considerar
     * @return la cantidad de festivales que hay en ese mes
     * Si el mes no existe se devuelve -1
     */
    public int festivalesEnMes(Mes mes)
    {
        if(agenda.containsKey(mes))
        {
            return agenda.get(mes).size();
        }
        else
        {
            return 0; // En el enunciado, el resultado no aparece como -1.
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
    public HashMap<Estilo,TreeSet<String>>  festivalesPorEstilo()
    {
        HashMap<Estilo,TreeSet<String>> festivalesPorEstilo = new HashMap<Estilo,TreeSet<String>>();
        TreeSet<String> arbolFestivalesDeEstiloX;

        for (Map.Entry<Mes,ArrayList<Festival>> cadaMes : agenda.entrySet())
        {
            for (Festival cadaFestibal : cadaMes.getValue())
            {
                //festibalesDeEsteEstilo = new TreeSet<>();
                for (Estilo cadaEstilo  : cadaFestibal.getEstilos())
                {
                    if(!festivalesPorEstilo.containsKey(cadaEstilo))
                    {
                        festivalesPorEstilo.put(cadaEstilo, new TreeSet<>());
                    }
                    // saco el TreeSet de festivales correspondiente a 'cadaEstilo'
                    arbolFestivalesDeEstiloX = festivalesPorEstilo.get(cadaEstilo);
                    // y le añado el nombre del festival
                    arbolFestivalesDeEstiloX.add(cadaFestibal.getNombre());
                }
            }
        }

        return festivalesPorEstilo;
    }

    /**
     * Se cancelan todos los festivales organizados en alguno de los
     * lugares que indica el conjunto en el mes indicado. Los festivales
     * concluidos no se tienen en cuenta
     * Hay que borrarlos de la agenda
     * Si el mes no existe se devuelve -1
     *
     * Si al borrar de un mes los festivales el mes queda con 0 festivales
     * se borra la entrada completa del map
     */
    public int cancelarFestivales(HashSet<String> lugares, Mes mes)
    {
        int numCancelados = 0;
        ArrayList<Festival> festivales;
        Festival festi;

        if(!agenda.containsKey(mes))
            return -1;

        festivales = agenda.get(mes);

        // Para cada festival del mes ...
        for (int i = festivales.size() - 1; i >= 0 ; i--)
        {
            festi = festivales.get(i);
            // Si esta en 'lugares' y no ha concluido ...
            if(lugares.contains(festi.getLugar()) && !festi.haConcluido())
            {
                // lo borra.
                festivales.remove(i);
                numCancelados++;
            }
        }

        // Si el mes ha quedado a 0 festivales ...
        // if(agenda.get(mes).size() == 0)
        if(festivales.size() == 0)
        {
            agenda.remove(mes);
        }
        return numCancelados;
    }

}
