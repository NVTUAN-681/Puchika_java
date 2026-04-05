/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pikachu_master.Model;

/**
 *
 * @author DELL
 */
public class LevelType {
    private String Level;
    private int rows, cols, timeLimit, NoP;
    private boolean isHardMode, isRocket;
    
    public LevelType(String level, int rows, int cols, int timeLimit, int NoP, boolean  isHardMode, boolean  isRocket){
        this.Level = level;
        this.rows = rows;
        this.cols = cols;
        this.timeLimit = timeLimit;
        this.NoP = NoP;
        this.isHardMode = isHardMode;
        this.isRocket = isRocket;
    }
    
    public LevelType(){
        
    }

    public static final LevelType START = new LevelType("Start", 3, 6, 20, 10, false, false);
    public static final LevelType AFRICA = new LevelType("AFRICA", 8, 12, 300, 18, true, true);
    public static final LevelType EUROPE = new LevelType("EUROPE", 10, 14, 240, 36, true, true);
    public static final LevelType ASIAN = new LevelType("ASIAN", 10, 20, 180, 45, true, false);
    public static final LevelType EASY = new LevelType("EASY", 8, 10, 240, 36, true, false);
    public static final LevelType NORMAL = new LevelType("NORMAL", 10, 12, 240, 72, false, false);
    public static final LevelType HARD = new LevelType("HARD", 10, 20, 180, 91, true, false);

    public static LevelType getByName(String levelName){
        switch (levelName.trim().toUpperCase()) {
            case "AFRICA":
                return AFRICA;
            case "EUROPE":
                return EUROPE;
            case "ASIAN":
                return ASIAN;
            case "EASY":
                return EASY;
            case "NORMAL":
                return NORMAL;
            case "HARD":
                return HARD;
            default:
                return START;
        }
    }
    
    public String getLevel() {
        return Level;
    }
    
    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public int getNoP() {
        return NoP;
    }

    public boolean isIsHardMode() {
        return isHardMode;
    }

    public boolean isIsRocket() {
        return isRocket;
    }
    
    
}
