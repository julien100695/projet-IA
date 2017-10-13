package vacuum;

public class CaseState {
	
	public int posCaseX;
	public int posCaseY;
	public String state;
	
	
	public CaseState(int posCaseX, int posCaseY, String state) {
		super();
		this.posCaseX = posCaseX;
		this.posCaseY = posCaseY;
		this.state = state;
	}


	public int getPosCaseX() {
		return posCaseX;
	}


	public int getPosCaseY() {
		return posCaseY;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}

}
