//working in a limited context
//concept: marbles that are touching the wall move very slowly

package com.example.mazewithwalls;

import android.util.Log;

public class NegativeEnergyWall extends Wall{
//USE CAREFULLY! WILL NOT WORK WITH ALL TYPES OF MARBLES!
	public NegativeEnergyWall(int leftX, int rightX, int topY, int bottomY) {
		super(leftX, rightX, topY, bottomY);
	}
	protected void onRightCollision(Marble m){
		Log.d("maze","right side of block");
		m.setCenterX(this.rightX+m.getRadius());
		m.setXSpeed(0);
		m.setYSpeed(-m.getYSpeed()*9/5);

	}
	protected void onLeftCollision(Marble m){
		Log.d("maze","left side of block");

		m.setCenterX(this.leftX-m.getRadius());
		m.setXSpeed(0);
		m.setYSpeed(-m.getYSpeed()*9/5);

	}
	protected void onBottomCollision(Marble m){
		Log.d("maze","bottom side of block xSpeed = "+m.getXSpeed());

		m.setCenterY(this.bottomY+m.getRadius());
		m.setYSpeed(0);
		m.setXSpeed(-m.getXSpeed()*9/5);

	}
	protected void onTopCollision(Marble m){
		Log.d("maze","top side of block");

		m.setCenterY(this.topY-m.getRadius());
		m.setYSpeed(0);
		//every time this can be called from LevelOne.updateGame()
		//Marble.update() will also be called, halving the xSpeed of the marble
		//by adding almost 2x the negative speed, 
		//we almost negate the current speed and whatever addition it will receive from the xChange parameter
		m.setXSpeed(-m.getXSpeed()*9/5);

	}

}
