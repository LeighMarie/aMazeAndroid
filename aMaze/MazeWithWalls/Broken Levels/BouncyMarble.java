//broken
package com.example.mazewithwalls;

import android.util.Log;

public class BouncyMarble extends Marble{
	private int isBouncing;
	public void startBouncing(){
		isBouncing=1;
	}
	@Override
	public void update (float xChange, float yChange){
		if (isBouncing==0){
			super.update(xChange, yChange);
		}
		else{
			super.update(0, 0);
			isBouncing++;
			isBouncing=isBouncing%100;
			Log.d("maze", "in the else of update");
		}
	}
	public BouncyMarble(int centerX, int centerY, int r) {
		super(centerX, centerY, r);
		isBouncing=0;
	}

}
