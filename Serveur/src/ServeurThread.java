import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.Socket;

public class ServeurThread extends Thread{
	private Socket connectionSocket;


	public ServeurThread(Socket connectionSocket){
		this.connectionSocket=connectionSocket;
	}

	@Override
	public void run(){

		try {
		String clientSentence;
		BufferedReader inFromClient =
				new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));


		while(true){

			clientSentence = inFromClient.readLine();
			System.out.println(clientSentence);


		}}
		catch(Exception e) {
		}
	}
	

	public void envoiMetadonnees(String repertoireSource) {
		File fichier = new File();
		
	}
}