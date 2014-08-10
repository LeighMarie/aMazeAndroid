package com.example.mazewithwalls;

import java.util.Random;

import android.graphics.Canvas;

//Wall takes on random color 
//for use with ChameleonMarble
//in level "What Happens in Vegas . . ."

public class RandomColorWall extends Wall{
	Random r;
	public RandomColorWall(int leftX, int rightX, int topY, int bottomY) {
		super(leftX, rightX, topY, bottomY);
		r = new Random(System.nanoTime());
	}
	@Override
	public RandomColorWall cloneWithNewCoordinates(int leftX, int rightX, int topY, int bottomY){
		return new RandomColorWall(leftX, rightX, topY, bottomY);
	}
	
	public void draw(Canvas c){
		if (r.nextInt(200)==0){
			p.setARGB(255, 25+r.nextInt(220), 25+r.nextInt(220), 25+r.nextInt(220));
		}
		super.draw(c);
	}
}
