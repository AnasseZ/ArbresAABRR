import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
			System.out.println("Contents of file:");
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
	
	public void showLines() {
		for (Iterator iterator = lines.iterator(); iterator.hasNext();) {
			String string = (String) iterator.next();
			System.out.println(string);
		}
	}
}