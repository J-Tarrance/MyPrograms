import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
/* Jayden Tarrance
   5/13/21 */
   
public class Maze {
	private String wall = "[]";
	private String path = "  ";
	private int xDim;
	private int yDim;
	private String[][] grid;
	//two lists to store the x and y cords of the walls added to the wall list 
	LinkedList<Integer> wallsx = new LinkedList<Integer>();
	LinkedList<Integer> wallsy = new LinkedList<Integer>();
	
	public Maze(int x, int y) {
		xDim = x - 1;
		yDim = y - 1;
		grid = new String[x][y];
		
		fillGrid();
	}
	
	//fills maze with walls
	private void fillGrid(){
		for (int i = xDim - 1; i >= 0; i--){
				for (int j = yDim - 1; j >= 0; j--){
					grid[j][i] = wall;
				}	
		}		
	}
	//creates the path in the maze 
	private void makePath() {
		
		Random num = new Random();
		int start = num.nextInt(xDim);
		int end = num.nextInt(xDim);
		while (start == 0 || start == xDim - 1){
			start = num.nextInt(xDim);
		}
		while (end == 0 || end == xDim - 1){
			end = num.nextInt(xDim);
		}
		//add the wall above the start to the wall list 
		wallsx.add(start);
		wallsy.add(1); 
		grid[0][start] = path;
		
		while (!wallsx.isEmpty()) {
			
			int temp = num.nextInt(wallsx.size());
			temp -= 1;
			if (temp < 0){
				temp = 0;
			}
			//checking to see if the temp wall is greater than or less than the array index 
			if (wallsx.getElement(temp) < 1 || wallsx.getElement(temp) >= xDim || wallsy.getElement(temp) < 1 || wallsy.getElement(temp) >= yDim){
				wallsx.remove(temp);
				wallsy.remove(temp);
				continue;
			}
			int tempx = wallsx.getElement(temp);
			int tempy = wallsy.getElement(temp);
			//if the position below the temp wall is a path then make sure the temp wall is surrounded by walls besides the path below 
			if (grid[tempy - 1][tempx] == path) {
				if (grid[tempy][tempx + 1] == wall && grid[tempy][tempx - 1] == wall && grid[tempy + 1][tempx] == wall) {
				//if the temp wall passes the checks then change the wall at the temp x and y cords to a path 
				//then add the walls surrounding the new path to the wall list 
				//the next 3 if statements do the same thing just from differnt directions 
					grid[tempy][tempx] = path;
					wallsx.add(tempx);
					wallsy.add(tempy + 1); 
					wallsx.add(tempx + 1); 
					wallsy.add(tempy); 
					wallsx.add(tempx - 1); 
					wallsy.add(tempy);
				}
			}
			//going down 
			if (grid[tempy + 1][tempx] == path) {
				if (grid[tempy][tempx + 1] == wall && grid[tempy][tempx - 1] == wall && grid[tempy - 1][tempx] == wall) {
					
					grid[tempy][tempx] = path;
					wallsx.add(tempx - 1);
					wallsy.add(tempy); 
					wallsx.add(tempx + 1); 
					wallsy.add(tempy); 
					wallsx.add(tempx); 
					wallsy.add(tempy - 1);
				}
			}
			//going left 
			if (grid[tempy][(tempx - 1)] == path) {
				if (grid[tempy + 1][tempx] == wall && grid[tempy - 1][tempx] == wall && grid[tempy][tempx + 1] == wall) {
					
					grid[tempy][tempx] = path;
					wallsx.add(tempx);
					wallsy.add(tempy + 1); 
					wallsx.add(tempx + 1); 
					wallsy.add(tempy); 
					wallsx.add(tempx); 
					wallsy.add(tempy - 1);
				}
			}
			//going right
			if (grid[tempy][tempx + 1] == path) {
				if (grid[tempy + 1][tempx] == wall && grid[tempy - 1][tempx] == wall && grid[tempy][tempx - 1] == wall) {
				
					grid[tempy][tempx] = path;
					wallsx.add(tempx - 1);
					wallsy.add(tempy); 
					wallsx.add(tempx); 
					wallsy.add(tempy + 1); 
					wallsx.add(tempx); 
					wallsy.add(tempy - 1);
				}
			}
			//remove the wall from the list 
			wallsx.remove(temp);
			wallsy.remove(temp);
				
		}
		grid[yDim - 1][end] = path;
		if (grid[yDim - 2][end] == wall){
			grid[yDim - 2][end] = path;
			
			if (grid[yDim - 3][end] == wall){
				grid[yDim - 3][end] = path;
			}
		}
		
	}
	
	public void print(){
		for (int i = xDim - 1; i >= 0; i--){
			System.out.print("\n");
			for (int j = yDim - 1; j >= 0; j--){
				System.out.print(grid[i][j]);
			}
		}
	}
	
	public void output(){
		try (PrintWriter txtfile = new PrintWriter(new FileWriter("maze.txt"))) {
			for (int i = xDim - 1; i >= 0; i--){
				txtfile.print("\n");
				for (int j = yDim - 1; j >= 0; j--){
					txtfile.print(grid[i][j]);
				}	
			}
			txtfile.flush();
			txtfile.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
			
		
		 
	}
	
	
	public static void main(String[] args){
		Scanner input = new Scanner(System.in);
		System.out.println("Too large of a maze will look broken in Powershell, but it will look correct in maze.txt");
		System.out.println("Enter one integer for Maze Dimensions. Example: 50 = 50x50");
		Integer Dimensions = input.nextInt();
		
		Maze maze = new Maze(Dimensions, Dimensions);
		maze.makePath();
		maze.print();
		maze.output();
		System.out.println("\nThe maze has also been stored in maze.txt");
		
	}
}