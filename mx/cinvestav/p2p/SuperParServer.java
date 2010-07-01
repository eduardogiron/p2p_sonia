package mx.cinvestav.p2p;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Hashtable;

import mx.cinvestav.p2p.comunication.RodrigoSuperParDummy;
import mx.cinvestav.p2p.fileListSincro.ArchivosLocales;

/**
 * Clase con un ciclo que atiende las peticiones creando hilos para cada una
 * @author absol
 */
public class SuperParServer {
	private static final int puertoTransferencias = 1234;
	private RodrigoSuperParDummy rodrigo;
	
	public SuperParServer(ArchivosLocales archivos,
			Hashtable<String, Integer> conexiones) {
		rodrigo = new RodrigoSuperParDummy(archivos, conexiones);
	}

	public void start() {
		System.out.println("Atendiendo peticiones");
		
		//debe regresar lo antes posible para poder atender mas peticiones
		rodrigo.atenderPeticion();
			
		
	}

}
