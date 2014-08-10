//working
//concept: static is messing up communications with the ship, so its responsiveness is deterred
package com.example.mazewithwalls;

import android.util.Log;

public class InterferenceMarble extends Marble{

	public InterferenceMarble(int centerX, int centerY, int r) {
		super(centerX, centerY, r);
	}
	public void update (float xChange, float yChange){
		//Log.d("maze", "xSpeed = "+xSpeed + " ySpeed = "+ySpeed+" centerX = "+centerX+ " centerY = "+centerY);
		xSpeed+= xChange/20;
    	ySpeed+= yChange/20; 
    	centerX+=xSpeed;
    	centerY+=ySpeed;
	}
}
