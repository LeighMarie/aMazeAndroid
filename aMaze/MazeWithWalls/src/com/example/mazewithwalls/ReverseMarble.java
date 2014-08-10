
package com.example.mazewithwalls;

//marble moves in opposite direction of tilt
//for use in level "Mind = Blown"

public class ReverseMarble extends Marble{
	public ReverseMarble(int centerX, int centerY, int r, int wallLength) {
		super(centerX, centerY, r, wallLength);
	}

	@Override
	public void updateMini (float xChange, float yChange){
		xChange = restrictToMax(xChange);
		yChange=restrictToMax(yChange);
    	centerX-=xChange;
    	centerY-=yChange;
	}
}
