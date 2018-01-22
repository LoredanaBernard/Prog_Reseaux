package ensim.tp.Client_Reseaux;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import junit.framework.Assert;

public class SyncEsclaveTest {
	
	static Metadonnee m;
	static Metadonnee m2;
	static Metadonnee m3;
	static Metadonnee m4;
	static ArrayList<Metadonnee> listeC;
	static ArrayList<Metadonnee> listeS;
	static ArrayList<Metadonnee> fichiersARemplacer;
	static ArrayList<Metadonnee> fichiersNonExistants;
	static ArrayList<Metadonnee> existant;

	
	@BeforeClass
	public static void initTests() {
		m = new Metadonnee("D/coucou", "doc1", "14092017","44000");
		System.out.println("Metadonne 1 \n path : " +m.path + "\n nom " + m.name + " \ndate modif : " + m.lastModif + " \ntaille : " +m.size);
		m2 = new Metadonnee("D/coucou", "doc2", "14102017","44000");
		m3 = new Metadonnee("D/coucou", "doc1", "14102017","44000");
		m4 = new Metadonnee("D/coucou", "doc1", "14092017","44000");
		System.out.println("Metadonne 4 \n path : " +m4.path + "\n nom " + m4.name + " \ndate modif : " + m4.lastModif + " \ntaille : " +m4.size);
		
		fichiersARemplacer = new ArrayList<Metadonnee>();
		fichiersNonExistants = new ArrayList<Metadonnee>();
		existant = new ArrayList<Metadonnee>();
		listeS = new ArrayList<Metadonnee>();
		listeC = new ArrayList<Metadonnee>();
	
	}
	
	public int comparerMetadonnee(Metadonnee s, Metadonnee c) {
		int estEgal= -1;
		// Si les fichiers sont égaux
		if ( (s.name.equals(c.name)) && (s.path.equals(c.path)) && (s.lastModif == c.lastModif) && (s.size == c.size)) {			// estEgal passe à 1
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

	// Test de 2 métadonnées identiques
	@Test
	public void testComparerMetadonnee() {
		int result;
		result = comparerMetadonnee(m,m4);
		Assert.assertEquals(1, result);
	}

	// Test de 2 métadonnées dif
		@Test
		public void testComparerMetadonnee2() {
			int result;
			result = comparerMetadonnee(m,m2);
			Assert.assertEquals(0, result);
		}
	
		// Test de 2 métadonnées avec date dif
		@Test
		public void testComparerMetadonnee3() {
			int result;
			result = comparerMetadonnee(m3,m);
			Assert.assertEquals(2, result);
		}
		
		
		// Renvoi une ArrayList des métadonnées des fichiers à envoyer
		public void comparaisonMetadonneesListes(ArrayList<Metadonnee> metaClient, ArrayList<Metadonnee> metaServeur) {
			int estEgal=-1;
			boolean estExistant = true;
			boolean aModif = false;
			
			// Parcours des listes de métadonnées du serveur et client
			for ( Metadonnee s : metaServeur) {
				for ( Metadonnee c : metaClient) {
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
				
		//		System.out.println("******Boucle for 1 finie. \n **estExistant :" + estExistant + "\n **aModif :" + aModif);
				// Une fois qu'on a parcouru tous les fichiers du client
				// On ajoute aux listes 
				if ( aModif == true) {
					fichiersARemplacer.add(s);
					System.out.println("On doit le remplacer");
				}
				else {
					if (estExistant == false) {
						fichiersNonExistants.add(s);
						System.out.println("On doir le créer");
					}
					else {
						System.out.println("Le fichier existe deja");
						existant.add(s);
					}
					
				}
			}
		}	
		
		
	// test de 2 arrayList avec les mêmes données
	@Test
	public void testComparaisonMetadonneesListes() {
		int tailleFichiersARemplacer;
		int tailleFichiersNonExistants;
		
		listeS.add(m);
		listeC.add(m);
				
		comparaisonMetadonneesListes(listeC, listeS);
		tailleFichiersARemplacer = fichiersARemplacer.size();
		tailleFichiersNonExistants = fichiersNonExistants.size();
//		System.out.println("Taille fichiers à remplacer " + fichiersARemplacer.size());
//		System.out.println("Taille fichiers a créer " + fichiersNonExistants.size());
	
		Assert.assertEquals(0, tailleFichiersARemplacer);
		Assert.assertEquals(0, tailleFichiersNonExistants);
		
	}
	@Test
	public void testComparaisonMetadonneesListes2() {
		int tailleFichiersARemplacer;
		int tailleFichiersNonExistants;
	
		listeS.add(m);
		listeS.add(m2);
		listeC.add(m);
				
		comparaisonMetadonneesListes(listeC, listeS);
		tailleFichiersARemplacer = fichiersARemplacer.size();
		tailleFichiersNonExistants = fichiersNonExistants.size();
//		System.out.println("Taille fichiers à remplacer " + fichiersARemplacer.size());
//		System.out.println("Taille fichiers a créer " + fichiersNonExistants.size());
	
		Assert.assertEquals(0, tailleFichiersARemplacer);
		Assert.assertEquals(1, tailleFichiersNonExistants);
	
		for (Metadonnee l : fichiersNonExistants) {
			System.out.println("Liste : " + l.name);
		}
	}
	@Test
	public void testComparaisonMetadonneesListes3() {
		int tailleFichiersARemplacer;
		int tailleFichiersNonExistants;
	
		listeS.add(m);
		listeS.add(m3);
		listeC.add(m);
				
		comparaisonMetadonneesListes(listeC, listeS);
		tailleFichiersARemplacer = fichiersARemplacer.size();
		tailleFichiersNonExistants = fichiersNonExistants.size();
//		System.out.println("Taille fichiers à remplacer " + fichiersARemplacer.size());
//		System.out.println("Taille fichiers a créer " + fichiersNonExistants.size());
	
		Assert.assertEquals(1, tailleFichiersARemplacer);

	
		for (Metadonnee l : fichiersARemplacer) {
			System.out.println("Liste : " + l.name);
		}
	}
}
