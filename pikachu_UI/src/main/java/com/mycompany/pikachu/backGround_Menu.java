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
<<<<<<< HEAD

public class backGround_Menu extends JPanel{
    private final Image backgroundImage;
//    String dir = "C:\\Users\\DELL\\Documents\\BTL_java\\";
    public backGround_Menu() {
//        backgroundImage = new ImageIcon( dir +  "pikachu_UI\\src\\main\\java\\images\\backGround_MENU.jpg").getImage();
        backgroundImage = new ImageIcon("images/backGroundMain.jpg").getImage();
=======
public class backGround_Menu extends JPanel{
    private final Image backgroundImage;

    public backGround_Menu() {
        backgroundImage = new ImageIcon("C:\\BTL_java\\pikachu_UI\\src\\main\\java\\images\\backGround_MENU.jpg").getImage();
>>>>>>> origin/hai
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
<<<<<<< HEAD
        if(backgroundImage == null){
            System.out.println("khong co anh");
        }
=======
>>>>>>> origin/hai

        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
