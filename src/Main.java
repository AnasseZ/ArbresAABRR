import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {	
		TreeHandler treeHandler = new TreeHandler("contenu.txt");
		Main.showMenu();
		
		Scanner reader = new Scanner(System.in);
		
		boolean stop = false;
		while(!stop) {
			int choice = reader.nextInt();
			reader.nextLine(); // pour vider le scanner
			switch (choice) {
				case 1:
					treeHandler.showAABRR();
					break;
				case 2:
					treeHandler.printAABRRonFile(reader);
					break;
				case -1:
					stop = true;
				default:
					break;
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Main.showMenu();
		}		
		
		reader.close();
	}
	
	public static void showMenu() {
		System.out.println("");
		System.out.println("************************************************");
		System.out.println(" MENU - Entrez le numéro de la question voulue");
		System.out.println("************************************************");
		System.out.println("1 : Charger l'AABRR du fichier de base et l'afficher (Q1 + Q3)");
		System.out.println("2 : Charger un fichier depuis l'AABRR du fichier de base (Q2)");
	}
}
