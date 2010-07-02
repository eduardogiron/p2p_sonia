package mx.cinvestav.p2p.bellamFord;

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
	@SuppressWarnings("unchecked")
    public void initTable(){
        String ip_nodo;
        Enumeration key;
        nodo= new ArrayList();
        key=actual.keys();
        ip_nodo=(String) key.nextElement();
        nodo.add(ip_nodo);
        nodo.add(((Integer)actual.get(ip_nodo)));
        System.out.println("Inicialización de la tabla interna");
        System.out.println("Añadido_actual ip "+ip_nodo+" next cost "+nodo);
        tabla_propia.put(ip_nodo,nodo);
        while(key.hasMoreElements())
        {
			nodo=new ArrayList();
			ip_nodo=(String) key.nextElement();
			nodo.add(ip_nodo);
			nodo.add(((Integer)actual.get(ip_nodo)));
			tabla_propia.put(ip_nodo,nodo);
			System.out.println("Añadido_actual ip "+ip_nodo+" next cost "+nodo);	
        }
    }
    public boolean cambiovector(GironVectores conexiones)
    {
        if ((tabla_propia.equals(conexiones.tabla_propia)==false)&& (this.flag==0)){
			this.tabla_externa= conexiones.tabla_propia;
			this.ip_nodof= conexiones.ip_nodo;
			this.actualizar();
            return false;
        }
        else
            return true;
    }
    @SuppressWarnings("unchecked")
    public void actualizar(){
        System.out.println("Actualizando ruta");
        propio= new ArrayList();
        foraneo= new ArrayList();
        aux= new ArrayList();
        aux2= new ArrayList();
        aux=aux2=null;
        boolean flag=false;
        Enumeration keys1,keys2;
        String au,k1="",k2="";
        keys2=tabla_externa.keys();
        keys1=this.tabla_propia.keys();
        aux= tabla_externa.get((String)ip_nodo);
        System.out.println(aux+ip_nodo);
        /*while(keys1.hasMoreElements()){
		 k1=(String)keys1.nextElement();
		 System.out.println("k1C k1 "+ tabla_propia.get(k1));
		 }*/
        while(keys2.hasMoreElements())
        {
            k2 =(String) keys2.nextElement();
            System.out.println("k2 "+ tabla_externa.get(k2));
            keys1=this.tabla_propia.keys();
            flag=false;
            while(keys1.hasMoreElements()){
				k1=(String)keys1.nextElement();
				if(k1.equals(k2))
				{
					System.out.println("Iguales k1 "+tabla_propia.get(k1)+" y K2 " + tabla_propia.get(k2)+" iniciando comparación... " ); 
					flag=true;  
					propio= tabla_propia.get(k1);
					System.out.println(propio);
					foraneo = tabla_externa.get(k2);
					System.out.println(foraneo+k2);
					System.out.println(aux+ip_nodo);
					if (((Integer)propio.get(1))<(((Integer)aux.get(1))+((Integer)foraneo.get(1)))){
						//System.out.println("va"+aux.get(0));
						aux2= new ArrayList();
						aux2.add((String)aux.get(0)); 
						aux2.add((Integer)(((Integer)aux.get(1))+((Integer)foraneo.get(1)))); 
						tabla_propia.put(k1,aux2);
						this.flag=1;
                    }
					propio.clear();
					foraneo.clear();
					//aux.clear();
					aux2.clear();
                }
				
            }
            if(flag==false)
            {
				if(k2.equals(this.ip_nodo)==false)
				{
					ArrayList a_ux= new ArrayList();
					System.out.println("Existe un nuevo nodo:");
					a_ux.add((String) this.ip_nodof);
					a_ux.add((Integer)tabla_externa.get(k2).get(1));
					tabla_propia.put(k2,a_ux); 
					System.out.println("Valor añadido : "+k2+ " "+ a_ux);
					this.flag=1;
				}
            }
        }
        
        
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
