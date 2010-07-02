package mx.cinvestav.p2p.bellamFord;
// All is complete
import java.io.Serializable;
import java.util.Hashtable;
import java.util.*; 
import java.io.*;
public class GironVectores implements Serializable {
    public Hashtable<String, Integer> actual;
    public Hashtable<String, ArrayList> tabla_propia, tabla_externa;
    // Array List:  Destino Next Peso
    private ArrayList nodo,propio,foraneo,aux,aux2;
    public String ip_nodo="",ip_nodof="";
	public int flag=0;
    @SuppressWarnings("unchecked")
    public GironVectores(Hashtable<String, Integer> conexiones) {
        // TODO Auto-generated constructor stub
        this.actual= conexiones;
        this.tabla_propia= new Hashtable();
        this.tabla_externa = new Hashtable();
        this.initTable();
    }
    public GironVectores() {
		// TODO Auto-generated constructor stub
	}

    public void initTable(){
        String ip_nodo;
        Enumeration<String> key;
        System.out.println("Inicialización de la tabla interna");

        key=actual.keys();
        while(key.hasMoreElements())
        {
        	nodo=new ArrayList();
            ip_nodo=(String) key.nextElement();
            nodo.add(ip_nodo);//ruta
            nodo.add(actual.get(ip_nodo));//peso camino
            tabla_propia.put(ip_nodo,nodo);
			System.out.println("Añadido_actual ip "+ip_nodo+" next cost "+nodo);	
        }
    }
    
    public boolean cambiovector(GironVectores conexiones)
    {
		boolean fla = false;
    	
    	this.tabla_externa= conexiones.tabla_propia;
		this.ip_nodof= conexiones.ip_nodo;
		
		Set<String> llaves = tabla_externa.keySet();//destinos
		ArrayList<Object> tuplaExterna;
		ArrayList<Object> tuplaLocal;
		System.out.println("d:Comprobando tabla del vecino "+ ip_nodof);
		String llaveForanea;
		Integer caminoActual;
		Integer caminoNuevo;

		for (String siguiente : llaves) {
			if(!siguiente.equals(ip_nodo))
			{
				tuplaExterna = (ArrayList<Object>) tabla_externa.get(siguiente);
				tuplaLocal = (ArrayList) tabla_propia.get(siguiente);
				if(tuplaLocal != null)//si se tiene localmente
				{
					System.out.println("d:Se compara un camino existente: " + siguiente);
					caminoActual = (Integer) tuplaLocal.get(1);
					caminoNuevo = (Integer) tuplaExterna.get(1) + actual.get(ip_nodof);
					if(caminoNuevo < caminoActual)
					{
						fla = true;
						System.out.println("d:Se encontro un camino mas corto, actualizando...");
						tuplaLocal = new ArrayList<Object>();
						tuplaLocal.add(ip_nodof);
						tuplaLocal.add(caminoNuevo);
						tabla_propia.remove(siguiente);
						tabla_propia.put(siguiente, tuplaLocal);
						System.out.println("d:destino\tcamino\tpeso camino");
						System.out.println("d:["+siguiente+","+ip_nodof+","+caminoNuevo+"]");
					}
				}
				else//no se tiene localmente
				{
					System.out.println("d:Se encontro un nuevo nodo" + siguiente + "-" + tuplaExterna.get(1).toString());
					fla = true;
					caminoNuevo = actual.get(ip_nodof) + (Integer)tuplaExterna.get(1);
					tuplaLocal = new ArrayList<Object>();
					tuplaLocal.add(ip_nodof);
					tuplaLocal.add(caminoNuevo);
					tabla_propia.put(siguiente, tuplaLocal);
					System.out.println("d:destino\tcamino\tpeso camino");
					System.out.println("d:["+siguiente+","+ip_nodof+","+caminoNuevo+"]");
				}
			}
			else
			{
				System.out.println("d:encontro ip propia en tabla del vecino " + ip_nodof);
			}
		}

		return fla;
    }
    
    public void setIP(String ip)
    {
        this.ip_nodo=ip;
    }
    private void writeObject(java.io.ObjectOutputStream out) throws IOException{
        out.writeObject(tabla_propia);
        out.close();
    }
    @SuppressWarnings("unchecked")
    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException{
        actual= (Hashtable)in.readObject();
        in.close();
    }
    
      @Override
    public boolean equals(Object o){
        if ((o instanceof GironVectores) && (((GironVectores)o).getActual()== this.actual)
                &&  (((GironVectores)o).getTabla_externa()== this.tabla_externa)
                && (((GironVectores)o).getTabla_propia()== this.tabla_propia) ) {
            return true;
          } else {
            return false;
          }

        
    }

    /**
     * @return the actual
     */
    public Hashtable<String, Integer> getActual() {
        return actual;
    }

    /**
     * @return the tabla_propia
     */
    public Hashtable<String, ArrayList> getTabla_propia() {
        return tabla_propia;
    }

    /**
     * @return the tabla_externa
     */
    public Hashtable<String, ArrayList> getTabla_externa() {
        return tabla_externa;
    }
    
    
    
}
