package com.example.mazewithwalls;

import android.graphics.Canvas;
import android.util.Log;

// Marble that speeds up when it travels along the walls (opposite of NegativeEnergyMarble)
// used in Level "Caffeine Boost"

public class PositiveEnergyMarble extends Marble {
	public PositiveEnergyMarble(int centerX, int centerY, int r, int wallLength) {
		super(centerX, centerY, r, wallLength);
	}
	
	@Override
	public void draw(Canvas c){
		if (isTouching==null){
			p=Marble.getDefaultPaint();
		}
		else{
			p.setColor(isTouching.getARGB());
		}
		super.draw(c);
	}
	@Override
	public void updateMini (float xChange, float yChange){
		//if it is touching a wall
		//it acts rather like a speedy marble
		Log.d("maze", "isTouching = "+isTouching);
		if(isTouching!=null){
			xChange *=4;
			yChange*=4;
	    	xChange = restrictToMax(xChange);
			yChange=restrictToMax(yChange);
	    	centerX+=xChange;
	    	centerY+=yChange;

		}
		else{
			super.updateMini(xChange, yChange);
		}		
	}
	
}
