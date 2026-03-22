/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pikachu;

import javax.swing.JPanel;
import java.awt.*;
import javax.swing.*;


/**
 *
 * @author laptop
 */
public class backGround_main extends JPanel {
  private final Image backgroundImage;
<<<<<<< HEAD
//    String dir = "C:\\Users\\DELL\\Documents\\BTL_java\\";
    public backGround_main() {
//        backgroundImage = new ImageIcon( "C:\\Users\\DELL\\Documents\\BTL-JAVA\\pikachu_UI\\src\\main\\java\\images\\backGroundMain.jpg").getImage();
        backgroundImage = new ImageIcon("images/backGround_MENU.jpg").getImage();
=======

    public backGround_main() {
        backgroundImage = new ImageIcon("C:\\BTL_java\\pikachu_UI\\src\\main\\java\\images\\backGroundMain.jpg").getImage();
>>>>>>> origin/hai
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
