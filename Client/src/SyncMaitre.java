
public class SyncMaitre {
	String serveurNom;
	String serveurPort;
	int numPort;
	String repertoireRacine;
	String repertoireSource;

	public void recuperationArg(String[] args) {
		serveurNom = args[0];
		serveurPort = args[1];
		numPort = Integer.parseInt(serveurPort);
		repertoireSource = args[2];
		repertoireRacine = args[3];				
	}

}
