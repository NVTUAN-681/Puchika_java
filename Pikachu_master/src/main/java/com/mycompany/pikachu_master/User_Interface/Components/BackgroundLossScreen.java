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
public class BackgroundLossScreen extends JPanel{
     private Image backgroundImage;

    public BackgroundLossScreen() {
        String path = "/images/LossScreen.png";
        // Lấy ảnh từ resources thông qua ImageIcon để đảm bảo ảnh tải xong
        ImageIcon icon = new ImageIcon(getClass().getResource(path));
        this.backgroundImage = icon.getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Vẽ ảnh phủ kín toàn bộ diện tích của Panel
        // g.drawImage(image, x, y, width, height, observer)
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
