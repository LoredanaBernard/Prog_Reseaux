import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;

class SyncEsclaveTest {
	static Metadonnee m;
	static Metadonnee m2;
	static Metadonnee m3;
	static Metadonnee m4;
	ArrayList<Metadonnee> listeC;
	ArrayList<Metadonnee> listeS;
	
	@BeforeClass
	public static void initTests() {
		m = new Metadonnee("D/coucou", "doc1", "14092017","44000");
		m2 = new Metadonnee("D/coucou", "doc2", "14102017","44000");
		m3 = new Metadonnee("D/coucou", "doc1", "14102017","44000");
		m4 = new Metadonnee("D/coucou", "doc1", "14092017","44000");
	}

	// Test de 2 métadonnées identiques
	@Test
	void testComparerMetadonnee() {
		int result;
		result = comparerMetadonnee(m,m4);
	}

	@Test
	void testComparaisonMetadonneesListes() {
		fail("Not yet implemented");
	}

}
