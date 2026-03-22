    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pikachu_master.User_Interface.Components;
import javax.swing.JButton;

public class ButtonMain extends JButton {
    private int xCoord;
    private int yCoord;

    public ButtonMain(int x, int y, String value) {
        super(value);
        this.xCoord = x;
        this.yCoord = y;
    }

    public int getxCoord() { return xCoord; }
    public int getyCoord() { return yCoord; }
}
