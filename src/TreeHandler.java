import java.util.ArrayList;
import java.util.Arrays;

/**
 * Service gérant la manipulation de nos arbres
 * @author anassezougarh
 *
 */
public class TreeHandler {

	private FileHandler fileHandler; // Notre service de gestion de fichier
	private String fileName;
	public final static String VALUE_SEPARATOR = ":"; // séparateur utlisé entre les valeurs
	public int parcoursIndex = 0; // indice courant dans notre parcours d'arbre
	
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
	
		// BOUCLER AVEC l'indice de lines pour créer tout les ABRR
		
		String[] contentExploded = lines.get(index).split(TreeHandler.TYPE_SEPARATOR);
		
		// contentExploded[0] stock le min et le max
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
		aPrime = ABRRPrefixeCreation(values, values[0], Integer.MIN_VALUE, Integer.MAX_VALUE);
		return new AABRR(aPrime, min, max);
	}
	
	public void showAABRR() {
		
		// On récupère les lignes venant notre fichier
		ArrayList<String> lines = fileHandler.getLines();
		
		AABRR a = this.createAABRR(lines, 0);
		System.out.println(
				"Les valeurs de cet ABRR sont comprises entre " 
				+ a.getMin()
				+ " et "
				+ a.getMax()
				+ "."
				);
		
		System.out.print("Arbre en préfixe: ");
		showABRRContentPrefixe(a.getAprime());
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
    public ABRR ABRRPrefixeCreation(int[] values, int data, int min, int max) {
		if (parcoursIndex < values.length) {
			if (values[parcoursIndex] > min && values[parcoursIndex] < max) {
				ABRR root = new ABRR();
				root.setValue(data);
				parcoursIndex++;
				if (parcoursIndex < values.length) {
					// On créé notre sag avec des valeurs entre celle courante et la maximum
					root.sag = ABRRPrefixeCreation(values, values[parcoursIndex], data, max);
					// On créé notre sad avec des valeurs entre le minimum et celle courante
					root.sad = ABRRPrefixeCreation(values, values[parcoursIndex], min, data);	
				}
				return root;
			}
		}
		return null;
	}
    
    /**
     * Question 3
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
