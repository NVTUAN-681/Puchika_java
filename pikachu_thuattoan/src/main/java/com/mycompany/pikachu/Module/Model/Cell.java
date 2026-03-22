/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pikachu.Module.Model;

/**
 *
 * @author Admin
 */
public class Cell {
    private int x, y, id;   //x, y là tọa độ; id là giá trị tại ô (x,y)
    private boolean status; //status là trạng thái ô đó (còn - true hay mất - false)

    public Cell() {
    }

    public Cell(int x, int y, int id, boolean status) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.status = status;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getId() {
        return id;
    }

    public boolean isStatus() {
        return status;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    
}
