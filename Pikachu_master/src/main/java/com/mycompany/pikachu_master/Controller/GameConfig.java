/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pikachu_master.Controller;

public class GameConfig {
    public int rows, cols, timeLimit; // timeLimit = 0 là vô hạn
    
    public GameConfig(int rows, int cols, int timeLimit) {
        this.rows = rows;
        this.cols = cols;
        this.timeLimit = timeLimit;
    }
    
    public int GetRows(){
        return rows;
    }
    
    public int GetCols(){
        return cols;
    }
    
    public int GetTimeLimit(){
        return timeLimit;
    }
    
}
