
//Marble takes on the color of one of the walls it is touching or one of the walls it last touched
//for use with RandomColorWall 
//level "What Happens in Vegas . . ."
package com.example.mazewithwalls;

import android.graphics.Canvas;
import android.util.Log;

public class ChameleonMarble extends Marble{

	public ChameleonMarble(int centerX, int centerY, int r, int wallLength) {
		super(centerX, centerY, r, wallLength);
	}
	@Override
	public void draw(Canvas c){
		if (isTouching!=null){
			p.setColor(isTouching.getARGB());
		}
		super.draw(c);
	}
}