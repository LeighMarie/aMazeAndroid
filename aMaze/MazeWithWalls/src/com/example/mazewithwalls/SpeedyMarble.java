//working
package com.example.mazewithwalls;

import android.util.Log;

// marble moves at faster speed throughout maze
// for use in level "Minimum Speed Limit?"

public class SpeedyMarble extends Marble{

	public SpeedyMarble(int centerX, int centerY, int r, int wallLength) {
		super(centerX, centerY, r, wallLength);
	}

	@Override
	public void updateMini (float xChange, float yChange){

		//Log.d("maze", "xSpeed = "+xSpeed + " ySpeed = "+ySpeed+" centerX = "+centerX+ " centerY = "+centerY);
		xChange *=3;
		yChange*=3;
    	xChange = restrictToMax(xChange);
		yChange=restrictToMax(yChange);

    	centerX+=xChange;
    	centerY+=yChange;
	}
}

