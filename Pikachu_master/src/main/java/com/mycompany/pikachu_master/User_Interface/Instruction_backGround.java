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
public class Instruction_backGround extends JPanel {

    private final Image backgroundImage;

    public Instruction_backGround() {
        backgroundImage = new ImageIcon("C:\\BTL_java\\pikachu_UI\\src\\main\\java\\images\\instruction.jpg").getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
