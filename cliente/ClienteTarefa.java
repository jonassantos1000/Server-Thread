package cliente;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ClienteTarefa {
	public static void main(String[] args) throws Exception {
		Socket socket = new Socket ("localhost", 12345);
		System.out.println("Conexao estabelecida");
		PrintStream saida = new PrintStream(socket.getOutputStream());

		
		Scanner teclado = new Scanner(System.in);
		while(teclado.hasNextLine()) {
			String comando = teclado.nextLine();
			if(comando.trim().equals("")) {
				break;
			};
			saida.println(comando);
		}
		
		Scanner respostaServidor = new Scanner(socket.getInputStream());
		
		while(respostaServidor.hasNextLine()) {
			String resposta = respostaServidor.nextLine();
			System.out.println(resposta);
		}
		respostaServidor.close();
		
		saida.close();
		teclado.close();
		socket.close();
	}
}
