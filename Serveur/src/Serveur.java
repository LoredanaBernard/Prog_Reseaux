import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;



public class Serveur {
	public static void main(String argv[]) throws IOException
	{
		// Attente d'une connexion 
		ServerSocket welcomeSocket = new ServerSocket(Integer.parseInt(argv[0]));
		while(true)
		{
			final Socket connectionSocket = welcomeSocket.accept();
			ServeurThread serveur = new ServeurThread(connectionSocket);
			serveur.start();

		}

	}
}