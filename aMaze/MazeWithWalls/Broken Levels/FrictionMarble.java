//broken
package com.example.mazewithwalls;

import android.util.Log;

public class FrictionMarble extends Marble{
	public FrictionMarble(int centerX, int centerY, int r) {
		super(centerX, centerY, r);
	} 
	@Override
	public void update (float xChange, float yChange){

		//Log.d("maze", "xSpeed = "+xSpeed + " ySpeed = "+ySpeed+" centerX = "+centerX+ " centerY = "+centerY);
		xSpeed+= (4*xChange);
    	ySpeed+= (4*yChange);
    	xSpeed=xSpeed*1/4; 
    	ySpeed=ySpeed*1/4;
    	Log.d("maze", "xSpeed = "+xSpeed);
    	Log.d("maze", "ySpeed = "+ySpeed);

    	centerX+=xSpeed;
    	centerY+=ySpeed;
	}
}
