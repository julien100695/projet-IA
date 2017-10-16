package vacuum;

import app.Case;

public class CaseState {
	
	public int posCaseX;
	public int posCaseY;
	public app.Case state;
	
	
	public CaseState(int posCaseX, int posCaseY, Case state) {
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


	public app.Case getState() {
		return state;
	}


	public void setState(app.Case state) {
		this.state = state;
	}

}
