package com.example.mazewithwalls;

import android.graphics.Canvas;

//other maze objects (Wall, Marble, FinishLine)  will implement this 
public interface Item {
	public void draw (Canvas c);
}
