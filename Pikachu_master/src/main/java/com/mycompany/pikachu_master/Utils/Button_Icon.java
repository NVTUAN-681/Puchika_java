/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pikachu_master.Utils;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

public class Button_Icon {    
    
    // Mặc định nó sẽ tự chèn Key là "MENU_FRAME"
    public static void applyCachedIcons(javax.swing.AbstractButton button, String text) {
        applyCachedIcons(button, text, "MENU_FRAME");
    }
    
    public static void applyCachedIcons(javax.swing.AbstractButton button, String text, String iconKey) {
        ImageIcon[] icons = ImageLoad.getButtonIcons(iconKey);
        if (icons != null) {
            button.setIcon(icons[0]);      // Normal
            button.setRolloverIcon(icons[1]); // Hover
            //Dimension size = new Dimension(650, 60);
            // CẬP NHẬT: Tự động lấy kích thước từ ảnh luôn, không fix cứng 650x60 nữa
            // Như vậy nút to nút nhỏ đều dùng chung hàm này được
            Dimension size = new Dimension(icons[0].getIconWidth(), icons[0].getIconHeight());
            button.setPreferredSize(size);
            button.setMinimumSize(size);
            button.setMaximumSize(size);
        }

        button.setText(text);
        
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setVerticalTextPosition(SwingConstants.CENTER);
        // ---> 2. THÊM 3 DÒNG NÀY ĐỂ TRỊ TẬT LỆCH CHỮ <---
        button.setHorizontalAlignment(SwingConstants.CENTER); // Ép TẤT CẢ (Ảnh + Chữ) vào giữa nút
        button.setMargin(new java.awt.Insets(0, 0, 0, 0));    // Xóa lề tàng hình mặc định của NetBeans
        button.setIconTextGap(0);        
        // Xóa khoảng cách thừa giữa viền ảnh và chữ
        button.setFont(new Font("Segoe UI", Font.BOLD, 24)); 
        button.setForeground(Color.BLACK); 

        // Xóa nền mặc định
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setOpaque(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    // Hàm giữ chất lượng ảnh khung luôn sắc nét VÀ CÓ BO GÓC
public static Image getHighQualityScaledImage(Image srcImg, int w, int h, int cornerRadius) {
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);

        java.awt.geom.RoundRectangle2D roundedRectangle = new java.awt.geom.RoundRectangle2D.Double(0, 0, w, h, cornerRadius, cornerRadius);
        g2.setClip(roundedRectangle);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();
        return resizedImg;
    }
}
