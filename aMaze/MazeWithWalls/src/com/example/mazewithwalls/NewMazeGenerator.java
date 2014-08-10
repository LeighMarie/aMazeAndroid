
package com.example.mazewithwalls;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;
import android.util.Log;

//Generates random maze of wall type wall example, dimensions length by length, and cell size cellWidth
//http://www.mazeworks.com/mazegen/mazetut/index.htm for algorithm idea

public class NewMazeGenerator {
	private Random r;
	public ArrayList<Wall>generateMaze(int cellWidth, int length, Wall example){
		r=new Random(System.nanoTime());
		int wallThickness=cellWidth/5;
		CellGrid cells=new CellGrid(length,length);
		Wall [][] leftWalls=new Wall[length][length];
		Wall [][] topWalls=new Wall [length][length]; 
		for (int r = 0; r<length; r++){
			for (int c=0; c<length; c++){

				//in this design, Walls will overlap at the top left corner of each cell
				Wall topCellEdge= example.cloneWithNewCoordinates(c*cellWidth, c*cellWidth+cellWidth+wallThickness, r*cellWidth, r*cellWidth+wallThickness);
				topWalls[r][c]=topCellEdge;
				Wall leftCellEdge=example.cloneWithNewCoordinates(c*cellWidth, c*cellWidth+wallThickness, r*cellWidth, r*cellWidth+cellWidth);
				leftWalls[r][c]=leftCellEdge;
			}
		}
		//we will now create the maze, deleting walls from leftWalls
		//and topWalls
		int currRow=r.nextInt(length);
		int currColumn=r.nextInt(length);
		cells.setAsTrue(currRow, currColumn);
		Stack<Integer>rowStack=new Stack<Integer>();
		Stack<Integer>colStack=new Stack<Integer>();
		//while we haven't visited every cell in the maze
		while (!cells.allCellsTrue()){ 
			int [] nextCellCoordinates=cells.getRandomFalseNeighbor(currRow, currColumn);
			//if there are no false neighbors, go back to the last cell visited
			if (nextCellCoordinates==null){
				currRow=rowStack.pop();
				currColumn=colStack.pop();
			}
			else{
				//push current position coordinates on stacks in case we need to back track later
				rowStack.push(currRow);
				colStack.push(currColumn);
				//break down the wall between the current cell and the next neighbor we will visit
				//moving up a row
				if (nextCellCoordinates[0]<currRow){
					topWalls[currRow][currColumn]=null;
				}
				//moving down a row
				else if (currRow<nextCellCoordinates[0]){
					topWalls[nextCellCoordinates[0]][nextCellCoordinates[1]]=null;
				} 
				//moving one column left
				else if(nextCellCoordinates[1]<currColumn){
					leftWalls[currRow][currColumn]=null;

				}
				//moving one column right
				else{
					leftWalls[nextCellCoordinates[0]][nextCellCoordinates[1]]=null;

				}
				currRow=nextCellCoordinates[0];
				currColumn=nextCellCoordinates[1];
				//set the cell we have just moved to as visited
				cells.setAsTrue(currRow, currColumn);
			

			}
		} 
		ArrayList<Wall> walls=new ArrayList<Wall>(); 
		//add whatever is left in leftWalls and topWalls to ArrayList walls
		for (int i=0; i<leftWalls.length;i++){
			for (int j=0; j<leftWalls[0].length;j++){
				if (leftWalls[i][j]!=null){
					walls.add(leftWalls[i][j]);
				} 
			} 
		}
		
		for (int i=0; i<topWalls.length;i++){
			for (int j=0; j<topWalls[0].length;j++){
				if (topWalls[i][j]!=null){
					walls.add(topWalls[i][j]);
				}
			} 
		}
		//add the maze edges to the array 
		Wall bottomMazeEdge=example.cloneWithNewCoordinates(0, length*cellWidth+wallThickness, length* cellWidth, length* cellWidth + wallThickness);
		walls.add(bottomMazeEdge);
		Wall rightMazeEdge = example.cloneWithNewCoordinates(length*cellWidth, length* cellWidth+wallThickness, 0, length*cellWidth);
		walls.add(rightMazeEdge); 
		Wall topMazeEdge= example.cloneWithNewCoordinates(0, length*cellWidth+wallThickness, 0, wallThickness);
		walls.add(topMazeEdge);
		Wall leftMazeEdge = example.cloneWithNewCoordinates(0, wallThickness, 0, length*cellWidth);
		walls.add(leftMazeEdge);
		return walls;
		
		
}
	
	class CellGrid{
		boolean[][]cells;
		public boolean allCellsTrue(){
			for (int i=0;i<cells.length;i++){
				for (int j=0;j<cells[0].length; j++){
					if (!cells[i][j]){return false;}
				}
			}
			return true;
		}
		public CellGrid(int length, int numColumns){
			cells=new boolean [length][numColumns];
		}
		public void setAsTrue(int row, int column){
			if (0<=row && 0<=column && row<=cells.length && column<=cells[0].length){
				cells[row][column]=true;
			}
		}
		//returns two-item int array containing row and column
		//coordinates for an unvisited random neighbor
		public int[] getRandomFalseNeighbor(int row, int column){

			//0 = north, 1 = east, 2 = south, 3 = west 
			int startDirection =r.nextInt(4);
			for (int iteration=0; iteration<4; iteration++){

				//looking at the neighbor above
				//make sure that the cell described by the coordinates array
				//is within the grid, but not in the top row
				if (startDirection==0){

					if (1<=row
							&& 0<=column
							&& row<cells.length 
							&& column<cells[0].length){
						//check that the cell above is unvisited (i.e.- equals false)
						if (!cells[row-1][column]){
							int[]rtn = {row-1, column};
							return rtn;
						}
					}
				}
				else if (startDirection==1){
					if (0<=row
							&& 0<=column
							&& row<cells.length 
							&& column<cells[0].length-1){
						//check that the cell above is unvisited (i.e.- equals false)
						if (!cells[row][column+1]){
							int[]rtn = {row, column+1};

							return rtn;
						}
					}
				}
				else if (startDirection==2){
					if (0<=row
							&& 0<=column
							&& row<cells.length-1
							&& column<cells[0].length){
						//check that the cell above is unvisited (i.e.- equals false)
						if (!cells[row+1][column]){
							int[]rtn = {row+1, column};

							return rtn;
						}
					}
				}
				else if (startDirection==3){
					if (0<=row
							&& 1<=column
							&& row<cells.length 
							&& column<cells[0].length){
						//check that the cell above is unvisited (i.e.- equals false)
						if (!cells[row][column-1]){
							int[]rtn = {row, column-1};

							return rtn;
						}
					}
				}
				//tries a different direction, moving clockwise
				startDirection=(startDirection+1)%4;
			}
			//no unvisited neighbors could be found in any direction
			return null;

		}
	}
}