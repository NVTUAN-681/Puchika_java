/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pikachu.Module.Model;

import com.mycompany.pikachu.Module.Algorithm.IAlgorithm;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 *
 * @author Admin
 */
public class Board {
    private int rows, cols, totalCells;
    private Cell[][] matrix;
    

    public Board() {
        do {            
            Random rand = new Random();
            rows = rand.nextInt(5, 10);
            cols = rand.nextInt(5, 10);
        } while (rows * cols % 2 != 0);
        this.matrix = new Cell[rows+ 2][cols + 2];
        this.totalCells = rows * cols;        
    }
    
    public Board(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.matrix = new Cell[rows+ 2][cols + 2];
        this.totalCells = rows * cols;
    }
    
    public void initBoard(IAlgorithm a) {
        Random rand = new Random();
        ArrayList<Integer> list = new ArrayList<>();
        int halfElems = totalCells / 2;
        for (int i = 0; i < halfElems; i++) {
            int randomNumber = rand.nextInt(1, 20) + 1;
            list.add(randomNumber);
            list.add(randomNumber);
        }
        
        do {
            Collections.shuffle(list);
            int index = 0;
            for (int i = 0; i <= this.rows + 1; i++) {
                for (int j = 0; j <= this.cols + 1; j++) {
                    if (i == 0 || j == 0 || i == this.rows + 1 || j == this.cols + 1) {
                        matrix[i][j] = new Cell(i, j, 0, false);
                    } else {
                        matrix[i][j] = new Cell(i, j, list.get(index++), true);
                    }
                }
            }
            
            a.getMap().clear();
            for (int i = 1; i <= this.rows; i++) {
                for (int j = 1; j <= this.cols; j++) {
                    if (a.getMap().containsKey(matrix[i][j].getId()) == false) {
                        a.getMap().put(matrix[i][j].getId(), new ArrayList<>());
                    }
                    a.getMap().get(matrix[i][j].getId()).add(matrix[i][j]);
                }
            }

        } while(a.hasAnyMatch(this) == false);
        //System.out.println(a.getMap());
    }
    
    public void initHardBoard(IAlgorithm a, int NoP) {
        
        ArrayList<Integer> list = new ArrayList<>();
        int halfElems = totalCells / 2;
        for (int i = 1; i < halfElems; i++) {
            list.add(i % NoP);
            list.add(i % NoP);
        }        
        
        do {
            Collections.shuffle(list);
            int index = 0;
            for (int i = 0; i <= this.rows + 1; i++) {
                for (int j = 0; j <= this.cols + 1; j++) {
                    if (i == 0 || j == 0 || i == this.rows + 1 || j == this.cols + 1) {
                        matrix[i][j] = new Cell(i, j, 0, false);
                    } else {
                        matrix[i][j] = new Cell(i, j, list.get(index++), true);
                    }
                }
            }
            
            a.getMap().clear();
            for (int i = 1; i <= this.rows; i++) {
                for (int j = 1; j <= this.cols; j++) {
                    if (a.getMap().containsKey(matrix[i][j].getId()) == false) {
                        a.getMap().put(matrix[i][j].getId(), new ArrayList<>());
                    }
                    a.getMap().get(matrix[i][j].getId()).add(matrix[i][j]);
                }
            }

        } while(a.hasAnyMatch(this) == false);
        //System.out.println(a.getMap());
    }
    
    public Cell getCell(int r, int c) {
        return matrix[r][c];
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }
    
    public int getTotalCells() {
        return totalCells;
    }

    public void setCell(int i, int j, Cell cell) {
        matrix[i][j] = cell;
    }

    public void setTotalCells(int totalCells) {
        this.totalCells = totalCells;
    }

    public void print() {
        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= cols; j++) {
                if (matrix[i][j].isStatus()) {
                    System.out.printf("%-5d", matrix[i][j].getId());
                } else {
                    System.out.printf("%-5c", ' ');
                }
            }
            System.out.println();
        }
    }

    
// Fixed
    
    public void printFull() {
        for (int i = 0; i <= rows; i++) {
            for (int j = 0; j <= cols; j++) {
                if (matrix[i][j].isStatus()) {
                    System.out.printf("%-5d", matrix[i][j].getId());
                } else if (i == 0){
                    System.out.printf("%-5d", j);
                } else if (j == 0){
                    System.out.printf("%-5d", i);
                } else {
                    System.out.printf("%-5c", ' ');
                }
            }
            System.out.println();
        }
    }
    
    public void printStatus() {
        for (int i = 0; i <= rows + 1; i++) {
            for (int j = 0; j <= cols + 1; j++) {
                System.out.printf("%-5d", matrix[i][j].isStatus() ? 1 : 0);
            }
            System.out.println();
        }
    }

    public void printCoordinates() {
        for (int i = 0; i <= rows + 1; i++) {
            for (int j = 0; j <= cols + 1; j++) {
                System.out.printf("%-12s", String.format("(%d, %d)", matrix[i][j].getX(), matrix[i][j].getY()));
            }
            System.out.println();
        }
    }
    
    public void initBoardFixed(IAlgorithm a, int[][] values) {
        for (int i = 0; i <= this.rows + 1; i++) {
            for (int j = 0; j <= this.cols + 1; j++) {
                if (i == 0 || j == 0 || i == this.rows + 1 || j == this.cols + 1) {
                    matrix[i][j] = new Cell(i, j, 0, false);
                } else {
                    matrix[i][j] = new Cell(i, j, values[i-1][j-1], true);
                }
            }
        }

        a.getMap().clear();
        for (int i = 1; i <= this.rows; i++) {
            for (int j = 1; j <= this.cols; j++) {
                if (a.getMap().containsKey(matrix[i][j].getId()) == false) {
                    a.getMap().put(matrix[i][j].getId(), new ArrayList<>());
                }
                a.getMap().get(matrix[i][j].getId()).add(matrix[i][j]);
            }
        }
    }
}
