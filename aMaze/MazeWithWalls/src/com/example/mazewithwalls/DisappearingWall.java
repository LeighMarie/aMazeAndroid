
//Wall becomes transparent after Marble touches it, but marble still cannot move through it.
//Used in level "Limited Visibility"
package com.example.mazewithwalls;

import android.graphics.Canvas;
import android.util.Log;

public class DisappearingWall extends Wall{
	protected boolean alreadyCollided = false;
	public DisappearingWall(int leftX, int rightX, int topY, int bottomY) {
		super(leftX, rightX, topY, bottomY);
	}
	
	@Override
	//check for collisions with marble 
	protected void onRightCollision(Marble m){
		super.onRightCollision(m);
		alreadyCollided=true;
	}
	@Override
	protected void onLeftCollision(Marble m){
		super.onLeftCollision(m);
		alreadyCollided=true;
	}
	@Override
	protected void onBottomCollision(Marble m){
		super.onBottomCollision(m);
		alreadyCollided=true;
	}
	@Override
	protected void onTopCollision(Marble m){
		super.onTopCollision(m);
		alreadyCollided=true;
	}
	
	//return a copy of the wall
	public DisappearingWall cloneWithNewCoordinates(int leftX, int rightX, int topY, int bottomY){
		return new DisappearingWall(leftX, rightX, topY, bottomY);
	}
	@Override
	//don't draw the wall if the marble has collided with it
	public void draw(Canvas c){
		if (!alreadyCollided){
			super.draw(c);
		}
	}
}
