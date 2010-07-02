package mx.cinvestav.p2p.bellamFord;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

/**
 * Implementa el algoritmo de Bellman-Ford
 * 
 * @author absol
 */
public class ShortestPath {
	private GironVectores giron;
	private Iterator<String> caminos;

	/**
	 * Constructor para crear un objeto inicializado
	 * 
	 * @param conexiones
	 *            tabla con las ips de nodos vecions y su peso
	 */
	public ShortestPath(Hashtable<String, Integer> conexiones) {
		RodrigoServer rodrigo;
		ClienteBellman clienteBell;

		giron = new GironVectores(conexiones);
		clienteBell = new ClienteBellman(giron);
		rodrigo = new RodrigoServer(giron, clienteBell);

		Thread t = new Thread(rodrigo);
		t.start();
	}

	/**
	 * Regresa la ip para seguir el camino mas corto, cada vez que se llama
	 * regresa otra ip con un camino igual o menos corto que el anterior y
	 * cuando no hay mas caminos regresa un null
	 * 
	 * @return regresa la ip del siguiente nodo a revisar con el orden de
	 *         caminos mas cortos a caminos mas largos, null si ya no sigue
	 *         ninguno
	 */
	public String nextNode()
	{
		if(caminos.hasNext())
		{
			return caminos.next();
		}
		else
			return null;
	}

	/**
	 * Reinicia la secuencia de caminos a seguir
	 */
	public void restart() {
		TreeMap<Integer, String> tablaCaminos = new TreeMap<Integer, String>();
		Set<String> llaves = giron.tabla_propia.keySet();
		ArrayList<Object> lista;
		String ant = "";

		for (String siguiente : llaves) {
			if (!ant.equals(siguiente)) {
				lista = giron.tabla_propia.get(siguiente);
				tablaCaminos.put((Integer) lista.get(1), (String) lista.get(0));
			}
			ant = siguiente;
		}

		caminos = tablaCaminos.values().iterator();
	}
}
