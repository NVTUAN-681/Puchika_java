/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pikachu_master.User_Interface;

import javax.swing.JPanel;
import java.awt.*;
import javax.swing.*;

/**
 *
 * @author laptop
 */

public class backGround_Menu extends JPanel{
    private final Image backgroundImage;
//    String dir = "C:\\Users\\DELL\\Documents\\BTL_java\\";
    public backGround_Menu() {
//        backgroundImage = new ImageIcon( dir +  "pikachu_UI\\src\\main\\java\\images\\backGround_MENU.jpg").getImage();
        backgroundImage = new ImageIcon("images/backGroundMain.jpg").getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(backgroundImage == null){
            System.out.println("khong co anh");
        }

        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
