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
    private String name;
    private int rows, cols, timeLimit, NoP;
    private boolean isHardMode, isRocket;
    
    public LevelType(String name, int rows, int cols, int timeLimit, int NoP, boolean  isHardMode, boolean  isRocket){
        this.name = name;
        this.rows = rows;
        this.cols = cols;
        this.timeLimit = timeLimit;
        this.NoP = NoP;
        this.isHardMode = isHardMode;
        this.isRocket = isRocket;
    }
    
    public static final LevelType START = new LevelType("Start", 3, 6, 20, 6, false, true);
    public static final LevelType AFRICA = new LevelType("AFRICA", 6, 8, 300, 12, false, false);
    public static final LevelType EUROPE = new LevelType("EUROPE", 8, 10, 240, 16, true, false);
    public static final LevelType ASIAN = new LevelType("ASIAN", 8, 12, 180, 18, true, true);

    public static LevelType getByName(String levelName){
        switch (levelName) {
            case "AFRICA":
                return AFRICA;
            case "EUROPE":
                return EUROPE;
            case "ASIAN":
                return ASIAN;
            default:
                return START;
        }
    }
    
    public String getName() {
        return name;
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
