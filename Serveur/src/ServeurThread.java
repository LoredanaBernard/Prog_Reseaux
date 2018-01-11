import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

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
	
//	public void recupererMetadonnees (File fichier) {
//		String chemin = fichier.getPath();
//		String name = fichier.getName();
//		long lastModif =  fichier.lastModified();
//		long sizeFile  = fichier.length();
//	}
	


	// POUR VENDREDI : FINIR RECUPERATION METADONNEES ET ENVOI + ENVOI FICHIERS	fonction rec(Dossier Source){
   public void recEnvoi	(String dossierSource) {
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
	    	  
	    	  listFichier.remove(f);
	      }
	    }
	}
	
	// Met les métadonnées dans un fichier qui sera envoyé au client 
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
		
		// Si le chemin désigne un dossier 
		if (fichier.isDirectory()) {
			while (resteFichier) {
				// Récupère les fichiers contenus dans ce dossier
				fichiersRep = fichier.listFiles();
				// Ajout des fichiers du dossier dans une arrayList de fichiers à analyser 
				for ( int i=0 ; i<fichiersRep.length ; i ++) {
					aAnalyser.add(fichiersRep[i]);
				}
				for (File f : aAnalyser) {
					if (f.isDirectory()) {
						
					}
				}
				// S'il n'y a plus de dossier ou fichier à analyser dans le dossier
				parent = fichier.getParent();
				
				
				// S'il n'y a plus de dossiers et fichiers qui n'ont pas été analysés 
				resteFichier = false;
			}		
		}
		
		else {
			// Envoi des métadonnées du fichier 
			
		}
		
	return metadonnees;	
	}
	
	// Envoi les fichiers au client
	public void envoiFichiers(String repertoireSource) {
		
	}
}