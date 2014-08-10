package com.example.mazewithwalls;

import android.graphics.Canvas;
import android.graphics.Paint;

// space at end of maze that finishes MazeActivity when marble touches it
public class FinishLine implements Item {
	protected int leftX;
	protected int topY;
	protected int rightX;
	protected int bottomY;
	protected Paint p;
	public FinishLine(int leftX, int rightX, int topY, int bottomY){
		this.leftX= leftX;
		this.topY=topY;
		this.rightX=rightX;
		this.bottomY=bottomY;
		p = new Paint();
		p.setARGB(255, 255, 255, 255);
	}
	@Override
	public void draw(Canvas c) {
		c.drawRect(leftX, topY, rightX, bottomY, p);		
	}
	public boolean isFinished(Marble m){
		
		//finds out if there is a collision between the right side of the finish line and the marble
		if (m.getLeftmostPoint()<=this.rightX 
				&& this.leftX <= m.getLeftmostPoint()
				&& this.topY < m.getCenterY()
				&& m.getCenterY() < this.bottomY){
			return true;
		}
		
		//finds out if there is a collision between the left side of the finish line and the marble
		else if (m.getRightmostPoint()<=this.rightX
				&& this.leftX <= m.getRightmostPoint()
				&& this.topY < m.getCenterY()
				&& m.getCenterY() < this.bottomY){
			return true;
		}
		
		//finds out if there is a collision between the bottom of the finish line and the marble
		if (this.topY<=m.getTopmostPoint()
				&& m.getTopmostPoint()<=this.bottomY
				&& this.leftX < m.getCenterX()
				&& m.getCenterX() < this.rightX){
			return true;
		}
		
		//finds out if there is a collision between the top of the finish line and the marble
		else if (this.topY<=m.getBottommostPoint()
				&& m.getBottommostPoint()<=this.rightX
				&& this.leftX < m.getCenterX()
				&& m.getCenterX() < this.rightX){
			return true;
		}
		return false;
	}
	
}
