import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Service permettant de manipuler nos fichiers
 * càd récuperer nos arbres ainsi que les écrire dans un fichier
 * @author anassezougarh
 *
 */
public class FileHandler {
	public String fileContent;
	public ArrayList<String> lines; /* Liste de nos lignes où chaque ligne est un ABR*/
	public List<Map<String,String>> linesSplited;

	public FileHandler(String fileName) {
		try {
			this.lines = new ArrayList<>();
			this.fileContent = this.loadFileContent(fileName);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param fileName
	 * @return contenu du fichier dans une chaîne de caractères
	 * @throws FileNotFoundException
	 */
	public String loadFileContent(String fileName) throws FileNotFoundException {
		try {
			File file = new File(fileName);
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			StringBuffer stringBuffer = new StringBuffer();
			String line;
			
			while ((line = bufferedReader.readLine()) != null) {
				stringBuffer.append(line);
				this.lines.add(line);
				stringBuffer.append("\n");
			}
			fileReader.close();
			System.out.println("Contenu du fichier de base:");
			System.out.println("");
			System.out.println(stringBuffer.toString());
			return stringBuffer.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return "";
	}

	public ArrayList<String> getLines() {
		return lines;
	}

	public String getFileContent() {
		return fileContent;
	}

	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}
	
	/**
	 * Question 2 : Création d'un fichier représentant un AABRR
	 * @param root la racine de notre arbre
	 * @param fileName nom du fichier entré par l'utilisateur
	 * @throws UnsupportedEncodingException 
	 * @throws FileNotFoundException 
	 */
	public void createFileFromAABRR(
			AABRR root,
			String path,
			String fileName
	) throws FileNotFoundException, UnsupportedEncodingException 
	{
		// Comme pour notre fichier de base on stock ses lignes dans une arraylist
		ArrayList<String> lines = new ArrayList<String>();
		
		traversalAABRRPrefixe(root, lines);
		
		File file = new File(path + fileName);
		file.getParentFile().mkdirs();
		
		PrintWriter writer = new PrintWriter(file);
		
		for (Iterator<String> iterator = lines.iterator(); iterator.hasNext();) {
			String string = (String) iterator.next();
			// On écrit dans le fichier
			writer.println(string);
		}
		
		System.out.println("Fichier créé !");
		writer.close();
		
	}
	
	/**
	 * Renvoi une chaine de caractères représentant l'AABRR complet
	 * @param root
	 * @return
	 */
	public String getOneAABRRLine(AABRR root) {
		String content = root.getMin() + ":" + root.getMax() + ";";
		
		return content + getABRRLine(root.getAprime());
	}
	
	/**
	 * Renvoi une chaine de caractères représentant uniquement le sous arbre A' d'un AABRR
	 * @param root
	 * @return
	 */
	public String getABRRLine(ABRR root) {
		ArrayList<Integer> lines = new ArrayList<Integer>();
		traversalABRRPrefixe(root,lines);
		
		String content = "";
		
		for (int i = 0; i < lines.size(); i++) {
			content += lines.get(i);
			
			if(i != (lines.size() - 1)) {
				content += ":";
			}
		}
		
		return content;
	}
	
	/**
	 * Parcours prefixe de l'AABRR
	 * 
	 * @param root racine de notre AABRR
	 * @param lines on stock dans notre liste de lines  une ligne par AABRR
	 * @return
	 */
	public ArrayList<String> traversalAABRRPrefixe(AABRR root, ArrayList<String> lines) {
		lines.add((getOneAABRRLine(root)));
		
		if(root.getSag() != null) {
			traversalAABRRPrefixe(root.getSag(), lines);
		}
		if(root.getSad() != null) {
			traversalAABRRPrefixe(root.getSad(), lines);
		}
		
		return lines;
	}
	
	/**
	 *  Parcours prefixe du sous arbre A' 
	 *  
	 * @param root racine du sous arbre A'
	 * @param lines on stock les valeurs de notre sous arbre
	 * @return
	 */
	public ArrayList<Integer> traversalABRRPrefixe(ABRR root, ArrayList<Integer> lines) {
		lines.add((root.getValue()));
		
		if(root.getSag() != null) {
			traversalABRRPrefixe(root.getSag(), lines);
		}
		if(root.getSad() != null) {
			traversalABRRPrefixe(root.getSad(), lines);
		}
		
		return lines;
	}
}