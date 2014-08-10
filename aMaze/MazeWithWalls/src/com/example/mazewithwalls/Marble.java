package com.example.mazewithwalls;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.hardware.SensorEvent;
import android.util.Log;
// what user will move through maze of walls by tilting device
// variable "ship" in other code
public class Marble implements Item{
	
	protected int centerX;
	protected int centerY;
	protected int radius;
	protected Paint p;
	protected float maxSpeed;
	//holds one of the Wall objects which the marble is touching
	protected Wall isTouching;
	public static Paint getDefaultPaint(){
		Paint rtn = new Paint();
		rtn.setARGB(255, 255, 255, 255);
		return rtn;
	}
	public void draw(Canvas c){
			c.drawCircle(centerX, centerY, radius, p);
		}	
	public int getLeftmostPoint(){
		return centerX-radius;
	}
	public int getRightmostPoint(){
		return centerX+radius;
	}
	public int getTopmostPoint(){
		return centerY-radius;
	}
	public int getBottommostPoint(){
		return centerY+radius;
	}
	public int getRadius(){
		return radius;
	}
	public void setCenterX(int x){
		centerX=x;
	}
	public void setCenterY(int y){
		centerY=y;
	}
	public void setRadius(int r){
		radius = r;
	}
	public int getCenterX(){
		return centerX;
		}
	public int getCenterY(){
		return centerY;
	}
	
	//called on updateGame(SensorEvent event) in Level class
	public void updateMini (float xChange, float yChange){
		xChange = restrictToMax(xChange);
		yChange= restrictToMax(yChange);
    	centerX += xChange;
    	centerY += yChange;
	}
	
	//make sure marble speed doesn't get too fast
	//the maxSpeed  is the max change in x or y that can occur without the risk of the marble
	//going through more than half of the wall in one updateMini()
	//and resulting in the wrong on ___Collision() method being called within Wall
	public float restrictToMax(float speed){
		if (speed>maxSpeed){Log.d("maze", "hitMax"); return maxSpeed; }
		else if (speed<-maxSpeed){Log.d("maze", "hitMax"); return -maxSpeed;}
		return speed;
		
	}
		
	
	public Marble(int centerX, int centerY, int r, int wallLength){
		this.centerX=centerX;
		this.centerY=centerY;
		radius = r; 
		maxSpeed=wallLength/(4);
		Log.d("maze", "maxSpeed = "+maxSpeed);
		p = Marble.getDefaultPaint();
	}
	public boolean setIsTouching (Wall isTouching){
		this.isTouching=isTouching;
		if (isTouching ==null){return false;}
		else{return true;}
	}
}

