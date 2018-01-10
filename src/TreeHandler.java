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
				/*System.out.println(
						"index AA: "
						+ parcoursIndexAABRR 
						+ " / index A " 
						+ parcoursIndex
					); */
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
     *  Question 4 Générer un AABRR aléatoirement
     * @param p le nombre d'aabrr choisis par l'utilisateur 
     * @param q la valeur max  de tout l'arbre
     */
    public void generateAABRRAleatoire(int p, int q) {
    	
    }
    
    public void AABRRAleatoire(int min, int p, int q, int abrrCreatead) {
	    	AABRRAleatoire(min,  p,  q, abrrCreatead + 1);
	    	AABRRAleatoire(min,  p,  q, abrrCreatead + 1);
    }
    
    /**
     * Question 5 - Vérification de la validité d'un AABRR
     * On descend jusqu'aux feuilles en testant la validité
     * Notre expression est un suite de && et non || car on pourrait
     * Avoir une trace jusqu'à une feuille valide mais tout le reste faux
     * 
     * 
     * @param root la racine de notre AABRR
     * @return 
     */
    public boolean AABRRVerification(AABRR root) {
    		
    		// Condition d'arrêt
    		if (root == null) {
    			return true;
    		}
    		
    		// 1 ère Vérification du caractère disjoints de nos ABR
    		if ((root.getSag() != null) && (root.getSag().getMax() >= root.getMin())) {
    			return false;
    		}
    			
    		if ((root.getSad() != null) && (root.getSad().getMin() <= root.getMax())) {
    			return false;
    		}
    		
    		// Vérifie la validité des valeurs des fils gauche et droit
    		if ((root.getSag() != null) && (root.getSag().getMin() > root.getMin())) {
    			return false;
    		}
    			
    		if ((root.getSad() != null) && (root.getSad().getMax() < root.getMax())) {
    			return false;
    		}
    			
    		
    		// Appel de la vérification de l'ABRR
    		if(!isABRRValide(root.getAprime(),root.getMin(), root.getMax())) {
    			return false;
    		}
    			
    		return (
    				AABRRVerification(root.getSag()) 
    				&& AABRRVerification(root.getSad())
    				);
    	
    }
    
    /**
     * Question 5 - partie 2
     * Vérification d'un ABRR
     * Même principe qu'au dessus sauf que nous travaillons sur un ABRR
     * Donc la valeur du fils de droite est inférieur et à gauche supérieur
     * 
     * @param root racine de notre ABRR
     * @param minimium obtenu par l'information détenu par son AABRR
     * @param maximum obtenu par l'information détenu par son AABRR
     * @return
     */
    public boolean ABRRVerification(ABRR root, int minimium, int maximum) {
    		// Condition d'arrêt
		if (root == null) {
			return true;
		}
		
		// On vérifie que les bornes soient respectées
		if (root.getValue() < minimium || root.getValue() > maximum) {
			return false;
		}
		
		// On vérifie que si il y'a un fils gauche renvoie false si il est strictement inférieur
		if ((root.sag != null) && (root.sag.getValue() < root.getValue())) {
			return false;
		}
			
		// Pareil pour le droit avec la négation de la véritable expression de validité 
		if ((root.sad != null) && (root.sad.getValue() > root.getValue())) {
			return false;
		}
		
		return (ABRRVerification(root.getSag(), minimium, maximum) 
				&& ABRRVerification(root.sad, minimium, maximum)
				);
    }
    
    /**
     * Appelle la vérification ABRR
     * @param root
     * @param minimium
     * @param maximum
     * @return vrai si valide, faux sinon
     */
    public boolean isABRRValide(ABRR root, int minimium, int maximum) {
    		return ABRRVerification(root, minimium, maximum);
    }
    
    /**
     * Appelle la vérification AABRR
     * @param 
     */
    public void isAABRRValide() {
    		if (currentWorkingAABRR == null) {
			System.out.println("Impossible. Veuillez charger l'AABRR en faisant 1) avant.");
		} else {
			 boolean res = AABRRVerification(currentWorkingAABRR);
			 
			 if(res == true) {
				 System.out.println("L'arbre est valide ! ");
			 } else {
				 System.out.println("L'abre est incorrecte.");
			 }
		}
    }
    
    /**
     * Question 6 : Rechercher une valeur dans l'AABRR
     *  Le but est d'afficher l'intervalle de valeur de celle qu'on recherche
     *  Si elle est bien évidemment disponible
     *  
     *  On se déplace dans l'AABRR en comparant les intervalles
     *  Si un intervalle est trouvé on rentre dans l'ABR A' et si la
     *  valeur est présente on affiche l'intervalle.
     * 
     * @param value
     */
    public void findValueinAABRR(int value) {
    		
    	if (currentWorkingAABRR == null) {
			System.out.println("Impossible. Veuillez charger l'AABRR en faisant 1) avant.");
		} else {
			AABRR aabrrFound = findInterval(currentWorkingAABRR, value);
    		
			if(aabrrFound == null) {
				System.out.println(value + " ne fait pas parti de l'AABRR !");
			} else {
				if(findValueinABRR(aabrrFound.getAprime(), value) == null) {
					System.out.println(
							"Intervalle " 
							+ aabrrFound.getMin() + " - " + aabrrFound.getMax()
							+ " a été trouvé mais la valeur "
							+ value
							+ " n'existe pas."
					); 
				} else {
					System.out.println(
							value 
							+" fait parti de notre intervalle "
							+ aabrrFound.getMin() + " - " + aabrrFound.getMax()
					); 
				}
			}
		}
    }
    
    /**
     * Question 6 - sous méthode
     * Recherche un intervalle valide
     * @param root
     * @param value
     * @return l'aabrr de l'intervalle si trouvé ou  null sinon
     */
    	public AABRR findInterval(AABRR root, int value) {
    		
    		if(root.getMin() <= value && root.getMax() >= value) {
    			// Intervalle trouvé, on renvoi l'arbre AABRR associé
    			return root;
    		} 
    		
    		if (root.getMax() < value) {
    			// La valeur est plus grande que l'intervalle actuel donc on se déplace chez le fils droit
    			if(root.getSad() != null) {
    				return findInterval(root.getSad(), value);
    			}
    			// Pas de sous arbre à visiter
    			return null;	
    		} 
    		
    		// On va au fils gauche si il y'en a un 
		if(root.getSag() != null) {
			return findInterval(root.getSag(), value);
		}
		
		// Pas de sous arbre à visiter on renvoi null donc
		return null;
    	}
    	
    	/**
    	 * Question 6 - sous méthode
    	 * L'intervalle existe on cherche l'existence de la valeur
    	 *  Méthode récursive donc on return des abrr pour le parcours
    	 * @param root
    	 * @param value
    	 * @return l'abrr si trouvé sinon null
    	 */
    	public ABRR findValueinABRR(ABRR root, int value) {
    		
    		// Condition d'arrêt
    		if(root == null) {
    			return null;
    		}
    		
    		if(root.getValue() == value) {		
			return root;
		} 
    		
    		if(root.getValue() > value) {
    			// La valeur recherchée est plus petite que celle actuelle, on se déplace à droite
			return findValueinABRR(root.getSad(), value);
		} else {
			return findValueinABRR(root.getSag(), value);
		}
    }
}
