package cliente;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ClienteTarefa {
	public static void main(String[] args) throws Exception {
		Socket socket = new Socket ("localhost", 12345);
		System.out.println("Conexao estabelecida");
		
		Thread enviaComando = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					System.out.println("Enviar comando para o server");
					PrintStream saida = new PrintStream(socket.getOutputStream());
					Scanner teclado = new Scanner(System.in);
					while(teclado.hasNextLine()) {
						
						String comando = teclado.nextLine();
						
						if(comando.trim().equals("")) {
							break;
						}
						
						saida.println(comando);
					}
					
					saida.close();
					teclado.close();
					
				}catch(IOException e) {
					throw new RuntimeException(e);
				}
			}
		});
		
		Thread recebeComando = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					System.out.println("Recebendo info do server");
					Scanner respostaServidor = new Scanner(socket.getInputStream());
					while(respostaServidor.hasNextLine()) {
						String resposta = respostaServidor.nextLine();
						System.out.println(resposta);
					}
					respostaServidor.close();
					
				} catch(IOException e) {
					throw new RuntimeException(e);
				}
			}
		});
		
		recebeComando.start();
		enviaComando.start();
		enviaComando.join();
		
		socket.close();
	}
}
