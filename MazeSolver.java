/*
CSE 17
Tyler Monaghan
tfm219
Program #4 DEADLINE: November 14, 2017
Program Description: Maze Solver
*/ 

import java.util.*;
import java.lang.NumberFormatException;

/** This class takes a maze as an input from the maze class and solves it. */
public class MazeSolver {
  
  /** Starts the path of the maze */
  public static ArrayList<Cell> findPath(Maze currentMaze) {
    ArrayList<Cell> path = new ArrayList<>();
    findPath(currentMaze, currentMaze.getStartCell(), path);
    return path;
  }
  
  
  /** Finds the path of the maze */
  private static ArrayList<Cell> findPath(Maze currentMaze, Cell current, ArrayList<Cell> path) {
    path.add(current);
    ArrayList<Cell> neighbors = currentMaze.getNeighbors (current);
    for (int i = 0; i < neighbors.size(); i++) {
      if (neighbors.get(i).equals(currentMaze.getEndCell()) || current.equals(currentMaze.getEndCell())) {
        path.add(neighbors.get(i));
        return path;
      }
      else if (path.contains(neighbors.get(i))) {
        neighbors.remove(neighbors.indexOf(neighbors.get(i)));
        if(neighbors.size() == 0) 
          return null;
      }
    }
    for (int i = 0; i < neighbors.size(); i++) {
      if (findPath(currentMaze, neighbors.get(i), path) != null) {
        return path;
      }
      else {
        path.remove(neighbors.get(i));
        continue;
      }
      
    }
    return null;
  }
  
  
  /** Prints the solved maze */
  public static void printSolvedMaze(Maze m, ArrayList<Cell> solution) {
    char[][] solved = m.getMazeDisplay();
    for (int i = 0; i < solution.size(); i++) {
      solved[solution.get(i).getDisplayCol()][solution.get(i).getDisplayRow()] =  '.';
      if(i != 0) {
        if (solution.get(i).getCol() - solution.get(i - 1).getCol() > 0)
          solved[solution.get(i).getDisplayCol() - 1][solution.get(i).getDisplayRow()] = '.';
        else if (solution.get(i).getCol() - solution.get(i - 1).getCol() < 0)
          solved[solution.get(i).getDisplayCol() + 1][solution.get(i).getDisplayRow()] = '.';
        else if (solution.get(i).getRow() - solution.get(i - 1).getRow() > 0)
          solved[solution.get(i).getDisplayCol()][solution.get(i).getDisplayRow() - 1] = '.';
        else if (solution.get(i).getRow() - solution.get(i - 1).getRow() < 0)
          solved[solution.get(i).getDisplayCol()][solution.get(i).getDisplayRow() + 1] = '.';
      }
    }
    for(int y = 0; y < solved[0].length; y++) {
      for(int x = 0; x < solved.length; x++) {
        System.out.print(solved[x][y]);
      }
      System.out.println();
    }
  }
  
  public static void main(String[] args) {
    
    if (args.length > 1) {
      System.out.println("Please only enter one argument");
    }
    else {
      int mazeId = 1;
      try {
        if (args.length == 1) {
          mazeId = Integer.parseInt(args[0]);
        }
        Maze currentMaze = new Maze(20, 6, mazeId);
        currentMaze.printMaze();
        ArrayList<Cell> solution = findPath(currentMaze);
        System.out.println();
        printSolvedMaze(currentMaze, solution);
      }catch (NumberFormatException ex) {
        System.out.println("You must enter an integer");
      }
    }
  }
}