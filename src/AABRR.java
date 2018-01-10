
public class AABRR {
	public AABRR sag;
	public AABRR sad;
	private ABRR Aprime;
	private int min;
	private int max;
	
	public AABRR(ABRR aprime, int min, int max) {
		super();
		Aprime = aprime;
		this.min = min;
		this.max = max;
	}
	
	public AABRR() {
		
	}
	
	public AABRR getSag() {
		return sag;
	}
	public void setSag(AABRR sag) {
		this.sag = sag;
	}
	public AABRR getSad() {
		return sad;
	}
	public void setSad(AABRR sad) {
		this.sad = sad;
	}
	public ABRR getAprime() {
		return Aprime;
	}
	public void setAprime(ABRR aprime) {
		Aprime = aprime;
	}
	
	public int getMin() {
		return min;
	}
	public void setMin(int min) {
		this.min = min;
	}
	public int getMax() {
		return max;
	}
	public void setMax(int max) {
		this.max = max;
	}
}

