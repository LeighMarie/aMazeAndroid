//broken
package com.example.mazewithwalls;

import android.util.Log;

public class BouncyWall extends Wall{

	public BouncyWall(int leftX, int rightX, int topY, int bottomY) {
		super(leftX, rightX, topY, bottomY);
	}
	public void reactToPossibleBouncyCollision(BouncyMarble m){
		//finds out if there is a collision on the right side of the wall
		if (m.getLeftmostPoint()<=this.rightX 
				&& this.leftX <= m.getLeftmostPoint()
				&& this.topY < m.getCenterY()
				&& m.getCenterY() < this.bottomY){
			onRightBouncyCollision(m);
		}
		//finds out if there is a collision on the left side of the wall
		else if (m.getRightmostPoint()<=this.rightX
				&& this.leftX <= m.getRightmostPoint()
				&& this.topY < m.getCenterY()
				&& m.getCenterY() < this.bottomY){
			onLeftBouncyCollision(m);
		}
		
		//finds out if there is a collision on the bottom side of the wall
		if (this.topY<=m.getTopmostPoint()
				&& m.getTopmostPoint()<=this.bottomY
				&& this.leftX < m.getCenterX()
				&& m.getCenterX() < this.rightX){
			onBottomBouncyCollision(m);
		}
		//finds out if there is a collision on the top side of the wall
		else if (this.topY<=m.getBottommostPoint()
				&& m.getBottommostPoint()<=this.bottomY
				&& this.leftX < m.getCenterX()
				&& m.getCenterX() < this.rightX){
			onTopBouncyCollision(m);
		}
	}
	
	public void onRightBouncyCollision(BouncyMarble m){
		Log.d("maze","bounce right side before "+m.getXSpeed());
		m.setXSpeed(-m.getXSpeed());
		Log.d("maze","bounce right side now "+m.getXSpeed());
		m.setCenterX(this.rightX+m.getRadius()+1);
		m.startBouncing();
	}
	public void onLeftBouncyCollision(BouncyMarble m){
		Log.d("maze","bouncy left side "+m.getXSpeed());
		m.setCenterX(this.leftX-m.getRadius()-1);
		m.setXSpeed(-m.getXSpeed());
		m.startBouncing();
	}
	public void onBottomBouncyCollision(BouncyMarble m){
		Log.d("maze","bouncy bottom side");
		m.setCenterY(this.bottomY+m.getRadius()+1);
		m.setYSpeed(-m.getYSpeed());
		m.startBouncing();

	} 
	public void onTopBouncyCollision(BouncyMarble m){
		Log.d("maze","bouncy top side");
		m.setCenterY(this.topY-m.getRadius()-1);
		m.setYSpeed(-m.getYSpeed());
		m.startBouncing();

	}
}
