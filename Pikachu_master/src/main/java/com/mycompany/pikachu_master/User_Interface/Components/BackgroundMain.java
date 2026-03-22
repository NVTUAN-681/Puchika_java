/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pikachu_master.User_Interface.Components;

import javax.swing.JPanel;
import java.awt.*;
import javax.swing.*;


/**
 *
 * @author laptop
 */
public class BackgroundMain extends JPanel {
  private final Image backgroundImage;
//    String dir = "C:\\Users\\DELL\\Documents\\BTL_java\\";
    public BackgroundMain() {
//        backgroundImage = new ImageIcon( "C:\\Users\\DELL\\Documents\\BTL-JAVA\\pikachu_UI\\src\\main\\java\\images\\backGroundMain.jpg").getImage();
        backgroundImage = new ImageIcon("images/backGround_MENU.jpg").getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
