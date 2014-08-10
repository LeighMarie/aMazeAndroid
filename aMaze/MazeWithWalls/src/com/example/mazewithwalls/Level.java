package com.example.mazewithwalls;

import java.util.ArrayList;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

// view of the maze that will be changed slightly for different levels 
// takes a context, specific type of marble, and specific type of wall 

public class Level extends View {
	private Marble ship;
	private ArrayList<Wall>walls;
	private FinishLine finish;
	private int width;
	private int height;
	private int side;

	public Level(Context context, Marble ship, Wall example) {
		super(context); 
		// adjusts size of view to fit device screen
		width= context.getResources().getDisplayMetrics().widthPixels;
		height= context.getResources().getDisplayMetrics().heightPixels;
		side = Math.min(width,height)-100;
		NewMazeGenerator MG = new NewMazeGenerator();
		int numBlock = 15;
		int blockWidth = side/numBlock;
		Log.d("maze", "generated");
		//generate random maze numBlock by numBlock with blocks of width blockWidth and with example type Walls 
		walls = MG.generateMaze(blockWidth, numBlock, example);
		this.ship = ship;
		//set placement of marble ("ship") and finishLine ("finish")
		ship.setCenterX((int)(9*blockWidth/20) + 16);
		ship.setCenterY((int)(9*blockWidth/20) + 16);
		ship.setRadius((int)(blockWidth/4));
		finish = new FinishLine ((numBlock-1)*(blockWidth) + walls.get(0).getWallWidth(), blockWidth*numBlock, (numBlock-1)*(blockWidth) + walls.get(0).getWallWidth(), blockWidth*numBlock);
	}
	
	public void updateGame(SensorEvent event){
		//when user tilts device, update marble movement in increments to more frequently check for collisions with Wall
		int iter =4;
		for (int i=0;i<iter; i++){
			ship.updateMini(-event.values[0]/iter, event.values[1]/iter);
			for (Wall w:walls){
				w.reactToPossibleCollision(ship);
			}
		}
		if (finish.isFinished(ship)){
			Log.d("maze", "is finished");
		}
	}
	public Marble getMarble(){
		return ship;
	}
	
	public FinishLine getFinishLine(){
		return finish;
	}
	
	// draw finishLine, walls, and marble ("ship")
	public void onDraw (Canvas canvas){
		finish.draw(canvas);
		for (Wall w:walls){
			w.draw(canvas);
		}
		ship.draw(canvas);
		invalidate();
	}
}


