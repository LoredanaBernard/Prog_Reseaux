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



	// POUR VENDREDI : FINIR RECUPERATION METADONNEES ET ENVOI + ENVOI FICHIERS	fonction rec(Dossier Source){
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

	// Envoi les fichiers au client
	public void envoiFichiers(String repertoireSource) {

	}
}