package mx.cinvestav.p2p.comunication;

import java.net.*; 
import java.io.*;


public class RodrigoParSimpleDummy {

    private String ip;


    public RodrigoParSimpleDummy(String ip){
        this.ip=ip;
    }

/**
 * Este metodo debe regresar true si se encontro el archivo false en los demas casos
 * */
	public boolean solicitar(String file) {
             
             
              try{
                 
                
                 Socket s = new Socket(ip, 1234);
                 PrintWriter srv = new PrintWriter(s.getOutputStream(), true);
                 System.out.println(3 + "\n" + file);
                 srv.println(file);

      
                 BufferedReader br=new BufferedReader(new InputStreamReader(s.getInputStream()));
                 //System.out.println("buffer: "+br.readLine());
    
                 if(br.readLine()!=null){
                    System.out.println("entra");
                    return true;
         
                  }  
                }            
               
              catch(ConnectException ce){
                    System.out.println("Could not connect: ");
                    ce.printStackTrace();
              }catch(IOException ioe){
                  ioe.printStackTrace();  
              }
              return false;
		
	}
	
	
	public static void main(String args[]){
	    RodrigoParSimpleDummy simpleDummy=new RodrigoParSimpleDummy("127.0.0.1");
	    System.out.println(simpleDummy.solicitar("servers.lst"));
	  
	}

}
