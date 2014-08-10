package com.example.mazewithwalls;

import android.graphics.Canvas;
import android.util.Log;

//walls inhibit marble movement; difficult for marble to leave walls and slow to travel on the walls
//used in level "Wet Paint"

public class NegativeEnergyMarble extends Marble {
	public NegativeEnergyMarble(int centerX, int centerY, int r, int wallLength) {
		super(centerX, centerY, r, wallLength);
	}
	
	@Override
	public void draw(Canvas c){
		if (isTouching==null){
			p.setARGB(255, 0, 0, 0);
		}
		else{
			p.setColor(isTouching.getARGB());
		}
		super.draw(c);
	}
	@Override
	public void updateMini (float xChange, float yChange){
		//if marble is touching a wall
		//it acts like opposite of speedy marble
		Log.d("maze", "isTouching = "+isTouching);
		if(isTouching!=null){
			xChange /=2;
			yChange/=2;
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

