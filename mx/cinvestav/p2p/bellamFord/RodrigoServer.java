package mx.cinvestav.p2p.bellamFord;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Hashtable;

public class RodrigoServer implements Runnable{


        private GironVectores giron;
        private ClienteBellman clienteBell;
        private ServerSocket serverSocket;
        private final static int PORT=5432;

	public RodrigoServer(GironVectores giron, ClienteBellman clienteBell) {
		this.giron=giron;
		this.clienteBell=clienteBell;
	}
        
        public RodrigoServer(){
        }
	
	public void run(){
	   try{
	      serverSocket=new ServerSocket(PORT);
	      clienteBell.mandarVectorAVecinos(PORT);
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
      
        
           Handler(Socket socket){
              this.socket=socket;
            }
      
            public void run(){
               GironVectores vectors=null;
               ObjectInputStream ois=null;
               String ip;
               try{
                   vectors= new GironVectores();
                   InputStream is = socket.getInputStream(); 
                   ois = new ObjectInputStream(is);
                   vectors.tabla_propia = (Hashtable<String, ArrayList>) ois.readObject();
                   ip = socket.getInetAddress().toString();
                   ip = ip.substring(1);
                   System.out.println("d:ip llegante:"+ip);
                   vectors.setIP(ip);
                   ip = socket.getLocalSocketAddress().toString();
                   ip = ip.substring(1);
                   ip = ip.split(":")[0];
                   System.out.println("d:ip propia:"+ip);
                   giron.setIP(ip);
                   //vectors.setIp(ip);
                }catch(UnknownHostException e){
                     e.printStackTrace();
                }catch(IOException ioe){
                     ioe.printStackTrace();
                }catch(ClassNotFoundException cnfe) {
                    cnfe.printStackTrace();
                }finally{

                }
                if(giron.cambiovector(vectors)){
                       clienteBell.mandarVectorAVecinos(PORT);
                       
                }
           
            }

}

	

}




