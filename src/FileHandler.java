import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class FileHandler {
	public String fileContent;


	public FileHandler(String fileName) {
		try {
			this.fileContent = this.getFileContent(fileName);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getFileContent(String fileName) throws FileNotFoundException {
		Scanner in = new Scanner(new FileReader(fileName));
		StringBuilder sb = new StringBuilder();
		while(in.hasNext()) {
		    sb.append(in.next());
		}
		in.close();
		
		return sb.toString();
	}
}