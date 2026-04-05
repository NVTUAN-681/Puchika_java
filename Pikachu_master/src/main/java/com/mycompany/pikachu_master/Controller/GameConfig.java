/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pikachu_master.Controller;

import com.mycompany.pikachu_master.Data.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GameConfig {
    String Level, matrix_data;
    int rows, cols, score, currentCoin, shuffleCount, hintCount, timeLimit, Resume = 0;

    public int GetResume() {
        return Resume;
    }

    public void setResume(int resume) {
        this.Resume = resume;
    }
    
    public String getMatrix_data() {
        return matrix_data;
    }

    public void setMatrix_data(String matrix_data) {
        this.matrix_data = matrix_data;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

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
        this.Resume = 0;
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
