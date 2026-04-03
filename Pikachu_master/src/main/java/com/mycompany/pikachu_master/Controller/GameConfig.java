/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pikachu_master.Controller;

public class GameConfig {
    String Level;
    int rows, cols, currentCoin, shuffleCount, hintCount, timeLimit;

    public String getLevel() {
        return Level;
    }

    public void setLevel(String Level) {
        this.Level = Level;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getCols() {
        return cols;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }

    public int getCurrentCoin() {
        return currentCoin;
    }

    public void setCurrentCoin(int currentCoin) {
        this.currentCoin = currentCoin;
    }

    public int getShuffleCount() {
        return shuffleCount;
    }

    public void setShuffleCount(int shuffleCount) {
        this.shuffleCount = shuffleCount;
    }

    public int getHintCount() {
        return hintCount;
    }

    public void setHintCount(int hintCount) {
        this.hintCount = hintCount;
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }
        
    public String GetLevel(){
        return Level;
    }
    
    public GameConfig(String Level){
        this.Level = Level;
    }

    public GameConfig(String Level, int rows, int cols, int currentCoin, int shuffleCount, int hintCount, int timeLimit) {
        this.Level = Level;
        this.rows = rows;
        this.cols = cols;
        this.currentCoin = currentCoin;
        this.shuffleCount = shuffleCount;
        this.hintCount = hintCount;
        this.timeLimit = timeLimit;
    }
    
    
    
}
