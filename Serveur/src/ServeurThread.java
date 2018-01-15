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
		// Ajout � la liste de fichiers 
		for ( int i=0 ; i<fichiers.length ; i ++) {
			listFichier.add(fichiers[i]);
		}
		for(File f : listFichier){
			if ( f.isDirectory()){
				recEnvoi(f.getPath());
			}
			else{
				// Envoi des M�tadonn�es de f
				// Envoi du nom, dateModif, chemin, et taille de chaque fichier   
				f.getPath();
				f.getName();
				f.lastModified();
				f.length();
				outToClient.writeBytes("METADONNEE : "+f.getPath()+" "+f.getName()+" "+f.lastModified()+" "+f.length()+"\n");
			}
		}
	}


	// Met les m�tadonn�es dans un fichier qui sera envoy� au client 
	public File Metadonnees(String repertoireSource) {
		String localPath="";  // Pour le test
		File metadonnees = new File(localPath);
		String pathName=repertoireSource;
		File [] fichiersRep;
		ArrayList<File> aAnalyser = new ArrayList<File>();
		ArrayList<File> dejaAnalyse = new ArrayList<File>();

		boolean resteFichier = true;

		String parent;
		File fichier = new File(pathName);
		String chemin = fichier.getPath();
		String name = fichier.getName();
		long lastModif =  fichier.lastModified();
		long sizeFile  = fichier.length();

		// Si le chemin d�signe un dossier 
		if (fichier.isDirectory()) {
			while (resteFichier) {
				// R�cup�re les fichiers contenus dans ce dossier
				fichiersRep = fichier.listFiles();
				// Ajout des fichiers du dossier dans une arrayList de fichiers � analyser 
				for ( int i=0 ; i<fichiersRep.length ; i ++) {
					aAnalyser.add(fichiersRep[i]);
				}
				for (File f : aAnalyser) {
					if (f.isDirectory()) {

					}
				}
				// S'il n'y a plus de dossier ou fichier � analyser dans le dossier
				parent = fichier.getParent();


				// S'il n'y a plus de dossiers et fichiers qui n'ont pas �t� analys�s 
				resteFichier = false;
			}		
		}

		else {
			// Envoi des m�tadonn�es du fichier 

		}

		return metadonnees;	
	}

	// Envoi les fichiers au client
	public void envoiFichiers(String repertoireSource) {

	}
}