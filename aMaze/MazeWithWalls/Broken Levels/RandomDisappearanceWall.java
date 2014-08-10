package com.example.mazewithwalls;

import java.util.Random;

import android.graphics.Canvas;

public class RandomDisappearanceWall extends Wall{

	Random r;
	boolean isPresent;
	public RandomDisappearanceWall(int leftX, int rightX, int topY, int bottomY) {
		super(leftX, rightX, topY, bottomY);
		isPresent=true;
		r=new Random(System.nanoTime());
	}
	@Override
	public void reactToPossibleCollision(Marble m){
		if (isPresent){
			super.reactToPossibleCollision(m);
		}
	}
	
	@Override
	public void draw(Canvas c){
		if (isPresent){super.draw(c);}	
		//if a wall has disappeared
		//there is a 1/500 chance every time this method is called
		//that it will return
		if(!isPresent){
			if (r.nextInt(500)==0){
				isPresent=true;
			}
		}
		//every time this method is called
		//there is a 1/1000 chance the wall will disappear
		if (r.nextInt(10000)==0){
			isPresent=false;
		}
		
	}
}
