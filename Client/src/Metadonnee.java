
public class Metadonnee {

	public String name;
	public String path;
	public long size;
	public long lastModif;
	
	public Metadonnee(String p, String n, String modif, String size) {
		this.name = n;
		this.path = p;
		this.size = Long.parseLong(size);
		this.lastModif = Long.parseLong(modif);
	}
}
