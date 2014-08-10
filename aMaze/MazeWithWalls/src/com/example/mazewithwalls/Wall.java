package com.example.mazewithwalls;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

// what randomly generated maze will be composed of
// marble cannot travel through walls, even if the walls are invisible 

public class Wall implements Item {
	protected int leftX;
	protected int topY;
	protected int rightX;
	protected int bottomY;
	protected Paint p;
	protected boolean marbleIsTouching;
	public void draw(Canvas c){
		c.drawRect(leftX, topY, rightX, bottomY, p);
	}
	
	//clone wall; useful for passing walls of certain types
	public Wall cloneWithNewCoordinates(int leftX, int rightX, int topY, int bottomY){
		return new Wall(leftX, rightX, topY, bottomY);
	}
	
	public int getWallWidth(){
		if (Math.abs(rightX-leftX)<Math.abs(topY-bottomY)){
			Log.d("maze", "wallThickness = "+Math.abs(rightX-leftX));
			return Math.abs(rightX-leftX);
		}
		else {
			Log.d("maze", "wallThickness = "+Math.abs(topY-bottomY));

			return Math.abs(topY-bottomY);
		}
	}
	public Wall(int leftX, int rightX, int topY, int bottomY){
		this.leftX= leftX;
		this.topY=topY;
		this.rightX=rightX;
		this.bottomY=bottomY;
		p = new Paint();
		p.setARGB(255, 255, 0, 0);
	}
	
	public void reactToPossibleCollision(Marble m){
		//finds out if there is a collision on the top left part of the wall
		//finds out if there is a collision on the right side of the wall
		if (m.getLeftmostPoint()<=this.rightX 
				&& this.leftX <= m.getLeftmostPoint()
				&& this.topY < m.getCenterY()
				&& m.getCenterY() < this.bottomY){
			onRightCollision(m);
			return;
		}
		//finds out if there is a collision on the left side of the wall
		else if (m.getRightmostPoint()<=this.rightX
				&& this.leftX <= m.getRightmostPoint()
				&& this.topY < m.getCenterY()
				&& m.getCenterY() < this.bottomY){
			onLeftCollision(m);
		}
		
		//finds out if there is a collision on the bottom side of the wall
		else if (this.topY<=m.getTopmostPoint()
				&& m.getTopmostPoint()<=this.bottomY
				&& this.leftX < m.getCenterX()
				&& m.getCenterX() < this.rightX){
			onBottomCollision(m);
		}
		//finds out if there is a collision on the top side of the wall
		else if (this.topY<=m.getBottommostPoint()
				&& m.getBottommostPoint()<=this.bottomY
				&& this.leftX < m.getCenterX()
				&& m.getCenterX() < this.rightX){
			onTopCollision(m);
		}
	     //finds out if the center of the circle is less than radius-length
        //from the top left corner using the Pythagorean Theorem
        else if (Math.sqrt(Math.pow(m.getCenterX()-this.leftX,2)+
                        Math.pow(m.getCenterY()-this.topY,2))<m.getRadius()){
            onTopLeftCornerCollision(m);
        }
        //finds out if the center of the circle is less than radius-length
        //from the top right corner using Pythagorean Theorem
        else if (Math.sqrt(Math.pow(m.getCenterX()-this.rightX,2)+
                Math.pow(m.getCenterY()-this.topY,2))<m.getRadius()){
            onTopRightCornerCollision(m);
        }
        //finds out if the center of the circle is less than radius-length
        //from the bottom left corner
        else if (Math.sqrt(Math.pow(m.getCenterX()-this.leftX,2)+
                Math.pow(m.getCenterY()-this.bottomY,2))<m.getRadius()){
            onBottomLeftCornerCollision(m);
        }
        //finds out if the center of the circle is less than radius-length
        //from the bottom right corner
        else if (Math.sqrt(Math.pow(m.getCenterX()-this.rightX,2)+
                Math.pow(m.getCenterY()-this.bottomY,2))<m.getRadius()){
            onBottomRightCornerCollision(m);
        }

		else{
			//if the marble was just in contact with the wall
			//but is no longer touching it
			//
			if(marbleIsTouching){
				marbleIsTouching=m.setIsTouching(null);
			} 
		}
	}
	
	  //resetting the marble to be on the same line
    //on which it hit the corner in the first place
    protected void onTopLeftCornerCollision (Marble m){
        int currentXDistance = this.leftX-m.getCenterX();
        int currentYDistance= this.topY-m.getCenterY();
        //factor by which currentYDistance and currentXDistance must be multiplied
        //to get the marble exactly radius-length away from the corner
        double ratioOfRadiusToCurrent=m.getRadius()/Math.sqrt(Math.pow(currentXDistance,2)+
                Math.pow(currentYDistance,2));
        int newXDistance= (int)(currentXDistance*ratioOfRadiusToCurrent);
        int newYDistance=(int)(currentYDistance*ratioOfRadiusToCurrent);
        m.setCenterX(this.leftX-newXDistance);
        m.setCenterY(this.topY-newYDistance);
        marbleIsTouching=m.setIsTouching(this);
        Log.d("maze", "onTopleftCornerCollision new position"+m.getCenterX()+" "+m.getCenterY());
    }
    protected void onTopRightCornerCollision (Marble m){
        int currentXDistance = m.getCenterX()-this.rightX;
        int currentYDistance= this.topY-m.getCenterY();
        //factor by which currentYDistance and currentXDistance must be multiplied
        //to get the marble exactly radius-length away from the corner
        double ratioOfRadiusToCurrent=m.getRadius()/Math.sqrt(Math.pow(currentXDistance,2)+
                Math.pow(currentYDistance,2));
        int newXDistance= (int)(currentXDistance*ratioOfRadiusToCurrent);
        int newYDistance=(int)(currentYDistance*ratioOfRadiusToCurrent);
        m.setCenterX(this.rightX+newXDistance);
        m.setCenterY(this.topY-newYDistance);
        marbleIsTouching=m.setIsTouching(this);

    }
    protected void onBottomLeftCornerCollision (Marble m){
        int currentXDistance = this.leftX-m.getCenterX();
        int currentYDistance= m.getCenterY()-this.bottomY;
        //factor by which currentYDistance and currentXDistance must be multiplied
        //to get the marble exactly radius-length away from the corner
        double ratioOfRadiusToCurrent=m.getRadius()/Math.sqrt(Math.pow(currentXDistance,2)+
                Math.pow(currentYDistance,2));
        int newXDistance= (int)(currentXDistance*ratioOfRadiusToCurrent);
        int newYDistance=(int)(currentYDistance*ratioOfRadiusToCurrent);
        m.setCenterX(this.leftX-newXDistance);
        m.setCenterY(this.bottomY+newYDistance);
        marbleIsTouching=m.setIsTouching(this);
        Log.d("maze", "onTopleftCornerCollision new position"+m.getCenterX()+" "+m.getCenterY());
    }
    protected void onBottomRightCornerCollision (Marble m){
        int currentXDistance = m.getCenterX()-this.rightX;
        int currentYDistance= m.getCenterY()-this.bottomY;
        //factor by which currentYDistance and currentXDistance must be multiplied
        //to get the marble exactly radius-length away from the corner
        double ratioOfRadiusToCurrent=m.getRadius()/Math.sqrt(Math.pow(currentXDistance,2)+
                Math.pow(currentYDistance,2));
        int newXDistance= (int)(currentXDistance*ratioOfRadiusToCurrent);
        int newYDistance=(int)(currentYDistance*ratioOfRadiusToCurrent);
        m.setCenterX(this.rightX+newXDistance);
        m.setCenterY(this.bottomY+newYDistance);
        Log.d("maze", "onBottomRightCornerCollision");

    }
	protected void onRightCollision(Marble m){
		//Log.d("maze","right side of block");
		m.setCenterX(this.rightX+m.getRadius());
		marbleIsTouching=m.setIsTouching(this);
	}
	protected void onLeftCollision(Marble m){
		//Log.d("maze","left side of block");
		marbleIsTouching=m.setIsTouching(this);
		m.setCenterX(this.leftX-m.getRadius());
	}
	protected void onBottomCollision(Marble m){
		//Log.d("maze","bottom side of block");
		marbleIsTouching=m.setIsTouching(this);
		m.setCenterY(this.bottomY+m.getRadius());
	}
	protected void onTopCollision(Marble m){
		//Log.d("maze","top side of block");
		marbleIsTouching=m.setIsTouching(this);
		m.setCenterY(this.topY-m.getRadius());
	}
	public int getARGB(){
		return p.getColor();
	}
	
}
