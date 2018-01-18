import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

public class SyncEsclave {

	String message;
	BufferedReader inFromUser;
	static BufferedReader inFromServer;
	DataOutputStream outToServer;
	Socket clientSocket;
	
	String serveurNom;
	String serveurPort;
	int numPort;
	String repertoireCible;
	static String repertoireSource;
	static ArrayList <Metadonnee> listeMetadonnees = new ArrayList<Metadonnee>();
	static ArrayList<Metadonnee> listeMetaClient = new ArrayList<Metadonnee>();
	static ArrayList<Metadonnee> liste = new ArrayList<Metadonnee>();
	static ArrayList<Metadonnee> fichiersNonExistants = new ArrayList<Metadonnee>();
	static ArrayList<Metadonnee> fichiersARemplacer = new ArrayList<Metadonnee>();


	
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
	
	//public boolean comparerMetadonnes() {
		
//	}
	
	public static void main(String[] args) throws IOException {
		
		SyncEsclave test = new SyncEsclave(args);
		test.send("D:/ENSIM/Projet");
		while(true) {
		//	System.out.println(test.inFromServer.readLine());
			listeMetadonnees = receptionMedadonnees();
		afficherMetaListe(listeMetadonnees);
		recListe("D:/ENSIM/Projet");
		System.out.println("Métadonnées des fichiers client");
		afficherMetaListe(listeMetaClient);
		}
		
	}
	



	// retourne une arrayList contenant des objets métadonnées pour chaque fichier
	public static ArrayList<Metadonnee> receptionMedadonnees() throws IOException {
		ArrayList<Metadonnee> listMeta = new ArrayList<Metadonnee>();
		String ligneMeta = new String();
		Metadonnee m ;
		
		ligneMeta = inFromServer.readLine();
		while (!ligneMeta.equals("STOP")) {
			String [] meta = ligneMeta.split(" ");
			
			//System.out.println(" meta[1] " + meta[1] + " meta[2] " + meta[2] + " meta[3] " + meta[3] + " meta[4] " + meta[4]);
			 m = new Metadonnee(meta[1], meta[2], meta[3], meta[4]);
			listMeta.add(m);
			ligneMeta = inFromServer.readLine();
		}
		
		return listMeta;
	}
	
	
	public static void afficherMetaListe(ArrayList<Metadonnee> liste) {
		System.out.println("Début de la liste des métadonnées");
		for ( Metadonnee m : liste) {
			System.out.println("Nom : " + m.name + " ; Chemin " + m.path + " ; Last Modif " + m.lastModif + " ; Taille " + m.size);
			
		}
		System.out.println("Fin des métadonnées");
	}
	
	
	public static void recListe(String path){
		File [] fichiers;
		ArrayList<File> listFichier = new ArrayList<File>();
		File currentFile = new File(path);
		fichiers = currentFile.listFiles();
		// Ajout à la liste de fichiers 
		for ( int i=0 ; i<fichiers.length ; i ++) {
			listFichier.add(fichiers[i]);
		}
		for(File f : listFichier){
			if ( f.isDirectory()){
				recListe(f.getPath());
			}
			else{
				// ajout métadonnées à la liste
				f.getPath();
				f.getName();
				String modif = String.valueOf(f.lastModified());
				String taille = String.valueOf(f.length());	
				System.out.println("AJOUT METADONNEE");
				listeMetaClient.add(new Metadonnee (f.getPath(), f.getName(), modif, taille));
			}
		}
	}
	
	// Comparaison des métadonnées 
	public int comparerMetadonnee(Metadonnee s, Metadonnee c) {
		int estEgal= -1;
		// Si les fichiers sont égaux
		if ( s.equals(c)) {
			// estEgal passe à 1
			estEgal = 1;
		}
		else {
			// Si le fichier venant du serveur est plus récent
			if ((s.name.equals(c.name)) && (s.path.equals(c.path)) && (s.lastModif>c.lastModif)) {
				estEgal = 2;
			}
			else {
				// Les fichiers n'ont rien en commun
				estEgal = 0;
			}
		}		
		return estEgal;
	}
	
	
	// Renvoi une ArrayList des métadonnées des fichiers à envoyer
	public void comparaisonMetadonneesListes(ArrayList<Metadonnee> metaClient, ArrayList<Metadonnee> metaServeur) {
		int estEgal=-1;
		boolean estExistant = true;
		boolean aModif = false;
		
		// Parcours des listes de métadonnées du serveur et client
		for ( Metadonnee s : metaClient) {
			for ( Metadonnee c : metaServeur) {
				// Si le nom est le même, on regarde si le fichier est une version antérieure ou non 
				 if ( s.name.equals(c.name)) {
					 // Comparaison des métadonnées entre elles 
					 estEgal = comparerMetadonnee(s,c);					 
					 switch (estEgal) {
					 case 0:
						 estExistant = false;
					 case 2 : 
						 aModif = true;
					 case 1 :
						 estExistant = true;
					 }
				 }
				 // Sinon le fichier n'existe pas et sera à créer 
				 else {
					 estExistant = false;
				 }
			}
			// Une fois qu'on a parcouru tous les fichiers du client
			// On ajoute aux listes 
			if ( aModif == true) {
				fichiersARemplacer.add(s);
			}
			else {
				if (estExistant == false) {
					fichiersNonExistants.add(s);
				}
			}
		}
	}

}