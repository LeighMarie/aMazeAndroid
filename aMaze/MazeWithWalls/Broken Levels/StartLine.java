package com.example.mazewithwalls;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

public class StartLine implements Item{
	protected int leftX;
	protected int topY;
	protected int rightX;
	protected int bottomY;
	protected Paint p;
	
	public StartLine(Marble m){
		this.leftX= m.getLeftmostPoint();
		this.topY=m.getTopmostPoint();
		this.rightX=m.getRightmostPoint();
		this.bottomY=m.getBottommostPoint();
		p = new Paint();
		p.setARGB(255, 50, 40, 255);
		Log.d("maze", "StartLine");
	}
	@Override
	public void draw(Canvas c) {
		c.drawRect(leftX, topY, rightX, bottomY, p);
	}

}
