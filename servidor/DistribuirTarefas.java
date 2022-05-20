package servidor;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class DistribuirTarefas implements Runnable {

	private Socket socket;
	private ServidorTarefas servidor;
	
	public DistribuirTarefas(Socket socket, ServidorTarefas servidor) {
		this.socket = socket;
		this.servidor = servidor;
	}
	
	@Override
	public void run() {
		try {
			System.out.println("Distribuindo tarefa para porta " + socket + Thread.currentThread().getId());
		
			Scanner entrada = new Scanner(socket.getInputStream());
			PrintStream saida = new PrintStream(socket.getOutputStream());
			
			while(entrada.hasNext()) {
				String comando = entrada.nextLine();
				
				switch(comando) {
					
					case "c1" : {
						saida.println("Confirmção comando 1");
						break;
					}
					case "c2" : {
						saida.println("Confirmção comando 2");
						break;
					}
					case "fim" : {
						saida.println("Desligando servidor");
						servidor.parar();
						break;
					}
					default : {
						System.out.println("Comando não encontrado");
					}
				
				}
				
				System.out.println(comando);
			}
			
			entrada.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
