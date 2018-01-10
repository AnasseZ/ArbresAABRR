import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

public class Main {

	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {		
		TreeHandler treeHandler = new TreeHandler("contenu.txt");
		treeHandler.showAABRR();
	}
}
