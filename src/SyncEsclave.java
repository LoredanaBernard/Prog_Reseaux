import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class SyncEsclave {

	String message;
	BufferedReader inFromUser,inFromServer;
	DataOutputStream outToServer;
	Socket clientSocket;
	
	public SyncEsclave(int port){
		initialize(port);
	}

	public void stopClient() throws IOException{
		clientSocket.close();
	}

	public String envoyerRequete(String requete){
		
		String recieved="null";
		try{
			send(requete);
			recieved = inFromServer.readLine();
		}
		catch(IOException e){
		}
		return recieved;
	}

	public void initialize(int port){
		try{
			inFromUser = new BufferedReader( new InputStreamReader(System.in));
			clientSocket = new Socket("localhost", port);
			outToServer = new DataOutputStream(clientSocket.getOutputStream());
			inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		}
		catch(Exception e){	
			System.out.println("Serveur Indisponible");
		}
	}

	public void send(String message){
		try{
			outToServer.writeBytes(message + '\n');
		}
		catch(IOException e){
		}
	}


}