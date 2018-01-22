import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

public class ServeurThread extends Thread{
	private Socket connectionSocket;
	DataOutputStream outToClient; 
	BufferedReader inFromClient;

	public ServeurThread(Socket connectionSocket) throws IOException{
		this.connectionSocket=connectionSocket;
		outToClient=new DataOutputStream(connectionSocket.getOutputStream());
	}

	@Override
	public void run(){

		try {
			String clientSentence;
			BufferedReader inFromClient =
					new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			while(true){
				clientSentence = inFromClient.readLine();
				recEnvoi(clientSentence);
				outToClient.writeBytes("STOP\n");
			}
		}
		catch(Exception e) {
		}
	}


	// Envoi des métadonnées au client 
	public void recEnvoi	(String dossierSource) throws IOException {
		File [] fichiers;
		ArrayList<File> listFichier = new ArrayList<File>();
		File currentFile = new File(dossierSource);
		fichiers = currentFile.listFiles();
		// Ajout à la liste de fichiers 
		for ( int i=0 ; i<fichiers.length ; i ++) {
			listFichier.add(fichiers[i]);
		}
		for(File f : listFichier){
			if ( f.isDirectory()){
				recEnvoi(f.getPath());
			}
			else{
				// Envoi des Métadonnées de f
				// Envoi du nom, dateModif, chemin, et taille de chaque fichier   
				f.getPath();
				f.getName();
				f.lastModified();
				f.length();
				outToClient.writeBytes("METADONNEE "+f.getPath()+" "+f.getName()+" "+f.lastModified()+" "+f.length()+"\n");
			}
		}
	}

	// Reception des noms des fichier à envoyer
	// Retourne une arrayList avec les noms 
	public ArrayList<String> receptionNom (String dossierSource) throws IOException {
		ArrayList<String> listNom = new ArrayList<String>();
		String noms = new String();
		
		noms = inFromClient.readLine();		
		while (!noms.equals("STOP")) {
			// Ajout du nom à la liste
			listNom.add(noms);
		}		
		return listNom;
	}
	
	
	public void rec( String chemin, ArrayList<String> listNom, ArrayList<File> listFichierAEnvoyer) {
		ArrayList<File> listFile = new ArrayList<File>();
		File currentFile = new File(chemin);
		File [] fichiers;
		fichiers = currentFile.listFiles();
		
		for ( int i=0 ; i<fichiers.length ; i ++) {
			listFile.add(fichiers[i]);
		}
		for(File f : listFile){
			if ( f.isDirectory()){
				rec(f.getPath(), listNom, listFichierAEnvoyer);
			}
			else{
				for ( String nom : listNom) {
					// Si le nom du fichier correspond à un nom de la liste en param
					if (f.getName().equals(nom)) {
						listFichierAEnvoyer.add(f);
					}
				}
			
			}
			}
	}
	// TO DO VERIFICATIONS
	// Parcours des fichiers à partir du dossier source et liste des noms 
	// Ajout des fichiers dont le nom est dans la listeNom à la liste de fichiers
	public ArrayList<File> fichiersAEnvoyer (String dossierSource, ArrayList<String> listNom) throws IOException{
		ArrayList<File> listFichierAEnvoyer = new ArrayList<File>();
		
		rec(dossierSource, listNom, listFichierAEnvoyer);
		// Verif que listFichierAEnvoyer a bien changé
		// Sinon le passer en paramètre 
		
		return listFichierAEnvoyer;
	}
	
	// TO DO ENVOI FICHIERS
	// Envoi les fichiers de la liste fichiers au client
	public void envoiFichiers(String repertoireSource, ArrayList<File> listFile) {
		
		for ( File f : listFile) {
			
		}
	}
}