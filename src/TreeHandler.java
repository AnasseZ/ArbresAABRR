import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Service gérant la manipulation de nos arbres
 * @author anassezougarh
 *
 */
public class TreeHandler {

	private FileHandler fileHandler; // Notre service de gestion de fichier
	private String fileName;
	public final static String VALUE_SEPARATOR = ":"; // séparateur utlisé entre les valeurs
	public int parcoursIndex = 0; // indice courant dans notre parcours d'arbre ABRR
	public int parcoursIndexAABRR = 0;
	public AABRR currentWorkingAABRR = null;
	
	// séparateur utilisé entre le max et min des ABRR et les valeurs mêmes de l'ABRR
	public final static String TYPE_SEPARATOR = ";"; 
	
	public TreeHandler(String fileName) {
		this.fileHandler = new FileHandler(fileName);
		this.fileName = fileName;
	}

	public FileHandler getFileHandler() {
		return fileHandler;
	}

	public void setFileHandler(FileHandler fileHandler) {
		this.fileHandler = fileHandler;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public AABRR createAABRR(ArrayList<String> lines, int index) {
		int min;
		int max;
		
		ABRR aPrime = new ABRR();
		
		String[] contentExploded = lines.get(index).split(TreeHandler.TYPE_SEPARATOR);
		
		// contentExploded[0] stock le min et le max de l'AABRR
		int[] range = Arrays.stream(contentExploded[0].split(TreeHandler.VALUE_SEPARATOR))
				.mapToInt(Integer::parseInt)
				.toArray();
		
		min = range[0];
		max = range[1];
		
		// contentExploded[1] stock les values de l'ABRR
		int[] values = Arrays.stream(contentExploded[1].split(TreeHandler.VALUE_SEPARATOR))
			.mapToInt(Integer::parseInt)
			.toArray();
		
		// ICI IL FAUT charger l'ABRR Aprime
		aPrime = ABRRPrefixeCreation(0, values, values[0], Integer.MIN_VALUE, Integer.MAX_VALUE);
		
		
		return new AABRR(aPrime, min, max);
	}
	
	public void showAABRR() {
		// On récupère les lignes venant de notre fichier
		ArrayList<String> lines = fileHandler.getLines();
		AABRR a = new AABRR();
		
		int[] values = getABRRPrefixeFromFile(lines);
		
		a = AABRRPrefixeCreation(lines, values, values[0], Integer.MIN_VALUE, Integer.MAX_VALUE);
		currentWorkingAABRR = a;
		parcoursIndexAABRR = 0;
	}
	
	public void printAABRRonFile(Scanner reader) throws FileNotFoundException, UnsupportedEncodingException {
		if (currentWorkingAABRR == null) {
			System.out.println("Impossible. Veuillez charger l'AABRR en faisant 1) avant.");
		} else {
			System.out.println("Entrez le nom du fichier");
			String fileName = reader.nextLine();
			System.out.println("Entrez le chemin");
			String path = reader.nextLine();
			
			this.fileHandler.createFileFromAABRR(currentWorkingAABRR, path, fileName);
			//this.fileHandler.createFileFromAABRR(currentWorkingAABRR, "/Users/anassezougarh/Desktop/", "fichierdeSorti.txt");
		}
	}
	
	/**
     * Question 3
     * Affiche notre AABRR avec pour chaque AABRR sa range de valeurs et les valeurs de son ABRR
     * Complexité ?
     * 
     * @param root
     */
	public void showOneAABRR(AABRR a) {
		System.out.println(
				"Les valeurs de cet ABRR sont comprises entre " 
				+ a.getMin()
				+ " et "
				+ a.getMax()
				+ "."
				);
		
		System.out.print("Arbre en préfixe: ");
		showABRRContentPrefixe(a.getAprime());
		System.out.println("");
		System.out.println("**************************************");
	}
	
	public int[] getABRRPrefixeFromFile(ArrayList<String> lines) {
		
		int[] prefixeWithMin = new int[lines.size()];
		for (int i = 0; i < lines.size(); i++) {
			String[] contentExploded = lines.get(i).split(TreeHandler.TYPE_SEPARATOR);
			
			// contentExploded[0] stock le min et le max
			int[] range = Arrays.stream(contentExploded[0].split(TreeHandler.VALUE_SEPARATOR))
					.mapToInt(Integer::parseInt)
					.toArray();
			
			prefixeWithMin[i] = range[0];
		}
		
		return prefixeWithMin;
	}

    /**
	 * Question 1 première partie 
	 * Creer notre ABRR récursivement selon la suite de valeurs d'une ligne, en préfixe
	 * Complexité en 0(n)
	 * @param values tableau représentant notre suite de valeurs
	 * @param root la racine de l'ABRR
	 * @param index du tableau
	 * @return
	 */
    public ABRR ABRRPrefixeCreation(int pIndex, int[] values, int data, int min, int max) {
		if (pIndex < values.length) {
			if (values[pIndex] >= min && values[pIndex] <= max) {
				//System.out.println("test" + pIndex);
				ABRR root = new ABRR();
				root.setValue(data);
				pIndex++;
				if (pIndex < values.length) {
					//System.out.println("testnum2 " + pIndex);
					// On créé notre sag avec des valeurs entre celle courante et la maximum
					root.sag = ABRRPrefixeCreation(pIndex, values, values[pIndex], data, max);
					// On créé notre sad avec des valeurs entre le minimum et celle courante
					root.sad = ABRRPrefixeCreation(pIndex, values, values[pIndex], min, data);	
				}
				return root;
			}
		}
		
		return null;
	}
    
    public AABRR AABRRPrefixeCreation(ArrayList<String> lines, int[] values, int data, int min, int max) {
    		//parcoursIndex = 0;
    		if (parcoursIndexAABRR < values.length) {
			if (values[parcoursIndexAABRR] > min && values[parcoursIndexAABRR] < max) {
				AABRR root = this.createAABRR(lines, parcoursIndexAABRR);
				System.out.println(
						"index AA: "
						+ parcoursIndexAABRR 
						+ " / index A " 
						+ parcoursIndex
					);
				showOneAABRR(root);
				parcoursIndexAABRR++;
				System.out.println("");
				if (parcoursIndexAABRR < values.length) {

					// On créé notre sag avec des valeurs entre celle courante et la maximum
					root.sag = AABRRPrefixeCreation(lines, values, values[parcoursIndexAABRR], min, data);
					
					// On créé notre sad avec des valeurs entre le minimum et celle courante
					root.sad = AABRRPrefixeCreation(lines, values, values[parcoursIndexAABRR], data, max);	
				}
				
				return root;
			}
		}
		return null;
    }
    
    /**
     * Question 3 - sous partie 
     * Affiche notre arbre de manière prefixe 
     * Complexité 0(n)
     * 
     * @param root
     */
    public void showABRRContentPrefixe(ABRR root)
    {
        if (root != null) {
            System.out.print(root.getValue() + " ");
            /*
            System.out.println(
            		root.getValue()
            		+ " a pour fils gauche "
            		+ root.getSag().getValue()
            		+ " et a pour fils droit "
            		+ root.getSad().getValue()
            		); */
            showABRRContentPrefixe(root.sag);
            showABRRContentPrefixe(root.sad);
        }
    }
    
    /**
     * 
     * @param p le nombre d'aabrr choisis par l'utilisateur 
     * @param q la valeur max 
     */
    public void generateAABRRAleatoire(int p, int q) {
    	
    }
    
    public void AABRRAleatoire(int min, int p, int q, int abrrCreatead) {
	    	AABRRAleatoire(min,  p,  q, abrrCreatead + 1);
	    	AABRRAleatoire(min,  p,  q, abrrCreatead + 1);
    }
}
