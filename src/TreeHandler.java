import java.util.ArrayList;
import java.util.Iterator;

public class TreeHandler {

	private FileHandler fileHandler;
	private String fileName;
	public final static String VALUE_SEPARATOR = ":";
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
	
	public AABRR createAABRR() {
		int min;
		int max;
		
		ABRR aPrime = new ABRR();
		ArrayList<String> lines = fileHandler.getLines();
	
		String[] contentExploded = lines.get(0).split(this.TYPE_SEPARATOR);
		
		String[] range = contentExploded[0].split(this.VALUE_SEPARATOR);
		min = Integer.parseInt(range[0]);
		max = Integer.parseInt(range[1]);
	
		return new AABRR(aPrime, min, max);
	}
	
	public void showAABRR() {
		AABRR a = this.createAABRR();
		System.out.println(a.getMin());
		System.out.println(a.getMax());
	}
}
