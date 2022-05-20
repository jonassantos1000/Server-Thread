package servidor;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServidorTarefas {
	public static void main(String[] args) throws Exception {
		System.out.println("--iniciando servidor --");
		ServerSocket servidor = new ServerSocket(12345);
		ExecutorService pool =  Executors.newCachedThreadPool();
		
		while(true) {
			Socket socket = servidor.accept();
			System.out.println("Aceitando novo cliente na porta " + socket.getPort());
			
			DistribuirTarefas distribuirTarefas = new DistribuirTarefas(socket);
			pool.execute(distribuirTarefas);
		}
	}	
}
