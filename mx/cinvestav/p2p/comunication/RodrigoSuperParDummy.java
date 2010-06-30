package mx.cinvestav.p2p.comunication;

import java.net.*;
import java.io.*;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;

import mx.cinvestav.p2p.bellamFord.ShortestPath;
import mx.cinvestav.p2p.fileListSincro.ArchivosLocales;

public class RodrigoSuperParDummy {

	private ShortestPath bellman;
	ArchivosLocales archivos;
	private ServerSocket serverSocket;
        private final static int PORT=1234;
	
	/**
	 * Contructor con objetos necesarios
	 * @param archivosLocales Objeto de giron que debe decirte si un archivo esta en uno de los pares simples de este super par (si no continuar de acuerdo a lo que regrese ShortestPath)
	 * @param conexiones Tiene las ip de los nodos alcansables y su peso, sirve para ShortesPath
	 */
	public RodrigoSuperParDummy(ArchivosLocales archivosLocales,
			Hashtable<String, Integer> conexiones) {
		bellman = new ShortestPath(conexiones);
		archivos = archivosLocales;
	}
	
	

	/**
	 * Este metodo debe crear un hilo que atienda la peticion (correrlo) y terminar para que se regrese el contro al ciclo de atender peticiones
	 * @param client socket abierto con la peticion
	 */
	public void atenderPeticion() {
		// pseudo codigo de lo que debe hacer masomenos
		
		/*
		 * 1 leer una linea del socket para saber el nombre de archivo que se esta buscando
		 * 2 hacer archivos.isLocal(file);
		 * 		si es local usar un ServerTransfere para obtener el flujo del archivo y mandarlo por el socket
		 * 		sino hacer bellman.restartOrder() para reiniciar la secuencia de caminos
		 * 			mientras que no se acaben los caminos (null)
		 * 				hacer bellman.nextNode()
		 * 				mandar la peticion de busqueda al nodo con la ip regresada
		 * 				si se se encontro el archivo mandar el flujo de regreso por el socket
		 * 			fin mintras
		 * 		fin si
		 * 3 si no se encontro notificarlo por el socket
		 * */
		 try{
	              serverSocket=new ServerSocket(PORT);
	      	   
	         }catch(IOException ioe){
	               ioe.printStackTrace();
	         }
	         
               while(true){
	       	try{
	           Thread t=new Thread(new Handler(serverSocket.accept()));
	           t.start(); 
	       }catch(IOException ioe){
	          ioe.printStackTrace();
	       }
	    
	   }

		 
		 
	}
	
	class Handler implements Runnable{
	      private Socket socket;
	      private BufferedReader clientInput_;
              private OutputStream clientOutput_;
	      
	      Handler(Socket socket){
            try {
                this.socket = socket;
                clientInput_ = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                clientOutput_ = socket.getOutputStream();
            } catch (IOException ex) {
               ex.printStackTrace();
            }
	      } 
	      
	      public void run(){
	      
	          beginSearch();
	      
	      }
	      
	      
	      
	 private void beginSearch(){
             try {
       		String nombre=System.getProperty("user.name");
      		String line = clientInput_.readLine();
      		
                 
      		if((line = clientInput_.readLine()) != null) {
        		File f = new File("." + File.separator + line);
        		System.out.print("Client "+nombre+ "/" +socket.getInetAddress().getHostName()+ " request for file " + line + "...");
                        if(archivos.isLocal(f.toString())) {
                                PrintWriter srv = new PrintWriter(clientOutput_ , true);
          			srv.println("Archivo encontrado en: "+socket.getLocalSocketAddress());
          			//ServerTransferer srv=new ServerTransferer();
          			
          
        		}
                       /*else if(level > 0) {
                         System.out.println(" file is not here, lookup further...");
                         //boolean found = lookupFurther(level-1, line, clientOutput_);
                        }*/
                }  

                clientInput_.close();
                clientOutput_.close();

            }catch(Exception e) {e.printStackTrace(); }   
         } 
	      
	      
	      
	      
	      
	
	
	}
	
	public static void main(String args[]){
	    ArchivosLocales archivos=new ArchivosLocales();
	    Hashtable<String, Integer> conexiones=new Hashtable<String,Integer>();
            conexiones.put("192.168.100.165", 6);
            RodrigoSuperParDummy superParDummy=new RodrigoSuperParDummy(archivos,conexiones);
	    superParDummy.atenderPeticion();
	}

}
