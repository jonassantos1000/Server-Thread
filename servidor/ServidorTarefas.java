package servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class ServidorTarefas {
	
	ServerSocket servidor;
	ExecutorService pool;
	private AtomicBoolean estaRodando;
	
	public ServidorTarefas() throws IOException {
		System.out.println("--iniciando servidor --");
		this.servidor = new ServerSocket(12345);
		this.pool =  Executors.newCachedThreadPool();
		this.estaRodando = new AtomicBoolean(true);
	}
	
	public void parar() throws IOException {
		servidor.close();		
		pool.shutdown();
		this.estaRodando.set(false);
	}

	public void rodar() throws IOException {
		try {
			while(estaRodando.get()) {
				Socket socket = servidor.accept();
				System.out.println("Aceitando novo cliente na porta " + socket.getPort());
				
				DistribuirTarefas distribuirTarefas = new DistribuirTarefas(socket, this);
				pool.execute(distribuirTarefas);
			}
		}catch(SocketException e) {
			System.out.println("SocketException, Esta rodando ? "+ this.estaRodando);
		}
	}
	
	public static void main(String[] args) throws Exception {
		ServidorTarefas servidor = new ServidorTarefas();
		servidor.rodar();
	}	
}
