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
			// Lorsqu'un client souhaite se connecter
			final Socket connectionSocket = welcomeSocket.accept();
			// Creation d'un ServeurThread pour ce client
			ServeurThread serveur = new ServeurThread(connectionSocket);
			serveur.start();

		}

	}
}