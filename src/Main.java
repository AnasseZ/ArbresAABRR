
public class Main {

	public static void main(String[] args) {
		
		ABRR aPrime = new ABRR();
		AABRR a = new AABRR(aPrime, 50, 75);
		System.out.println(a.getMax());
		
		FileHandler fileHander = new FileHandler("contenu");
	}

}
