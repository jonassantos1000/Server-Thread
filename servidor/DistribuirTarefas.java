package servidor;

import java.net.Socket;
import java.util.Scanner;

public class DistribuirTarefas implements Runnable {

	private Socket socket;
	public DistribuirTarefas(Socket socket) {
		this.socket = socket;
	}
	
	@Override
	public void run() {
		try {
			System.out.println("Distribuindo tarefa para porta " + socket + Thread.currentThread().getId());
		
			Scanner entrada = new Scanner(socket.getInputStream());
			
			while(entrada.hasNext()) {
				String comando = entrada.nextLine();
				System.out.println(comando);
			}
			
			entrada.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
