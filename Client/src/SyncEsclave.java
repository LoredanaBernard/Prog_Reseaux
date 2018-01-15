import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class SyncEsclave {

	String message;
	BufferedReader inFromUser,inFromServer;
	DataOutputStream outToServer;
	Socket clientSocket;
	
	String serveurNom;
	String serveurPort;
	int numPort;
	String repertoireCible;
	String repertoireSource;
	
	// Récupère les données en argument
	public void recuperationArg(String[] args) {
		serveurNom = args[0];
		serveurPort = args[1];
		numPort = Integer.parseInt(serveurPort);
		repertoireCible = args[2];
		repertoireSource = args[3];				
	}
	
	
	public SyncEsclave(String[] args){
		recuperationArg(args);
		initialize();
	}

	public void stopClient() throws IOException{
		clientSocket.close();
	}

	// Envoi de requete au serveur
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

	// Initialisation du client
	public void initialize(){
		try{
			inFromUser = new BufferedReader( new InputStreamReader(System.in));
			System.out.println(numPort);
			clientSocket = new Socket("localhost", numPort);
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
	
	public boolean comparerMetadonnes(String metaDonnee) {
		//File file = new file(arg[3]);
		String filepath  metadonnee;
		FileReader donnee = new FileReader(filepath);
		BufferedReader stock = new BufferedReader(donnee);
		
		String line = br.readLine();
		while(line != null ) {
			System.out.println(line);
		}
		String chemin = donnees.getPath();
		String name = donnees.getName();
		long lastModif =  donnees.lastModified();
		long sizeFile  = donnees.length();
	}
	
	public static void main(String[] args) throws IOException {
		
		SyncEsclave test = new SyncEsclave(args);
		test.send("D:/ENSIM/Projet");
		while(true) {
			System.out.println(test.inFromServer.readLine());
		}
	}
	



	/*public void receptionMedadonnees() throws IOException {
		String ligneMeta = new String();
		
		do {
			ligneMeta = inFromServer.readLine();
			String [] metadonnes = ligneMeta.split(" ");
		}while (!ligneMeta.equals("stop"));
		
		
	}

	*/
	


}