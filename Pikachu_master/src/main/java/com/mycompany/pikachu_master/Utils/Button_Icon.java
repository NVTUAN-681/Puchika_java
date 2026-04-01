/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pikachu_master.Utils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

public class Button_Icon {
public static void setupImageButton(JButton button, String frameImagePath, String buttonText) {
        URL imgURL = Button_Icon.class.getResource(frameImagePath);
        try {
            if (imgURL != null) {
                BufferedImage originalImage = ImageIO.read(imgURL);
                
                // Kích thước của cái khung ảnh
                int targetWidth = 650;
                int targetHeight = 70;
                
                // ĐỘ BO GÓC (Bạn có thể tăng giảm số này để góc bo nhiều hay ít)
                int cornerRadius = 25; 
                
                button.setPreferredSize(new Dimension(targetWidth, targetHeight));
                button.setMinimumSize(new Dimension(targetWidth, targetHeight));
                button.setMaximumSize(new Dimension(targetWidth, targetHeight));
                
                // Set cái khung làm background bình thường (Đã truyền thêm cornerRadius)
                button.setIcon(new ImageIcon(getHighQualityScaledImage(originalImage, targetWidth, targetHeight, cornerRadius)));
                
                // Set cái khung phóng to một chút khi di chuột vào (hiệu ứng nảy)
                int hoverWidth = (int) (targetWidth * 1.025);
                int hoverHeight = (int) (targetHeight * 1.025);
                // Vẫn giữ nguyên độ bo góc khi phóng to
                button.setRolloverIcon(new ImageIcon(getHighQualityScaledImage(originalImage, hoverWidth, hoverHeight, cornerRadius)));
                
            } else {
                System.err.println("Lỗi: Không tìm thấy ảnh khung tại " + frameImagePath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // --- ĐÂY LÀ PHÉP THUẬT ÉP CHỮ LÊN TRÊN ẢNH ---
        button.setText(buttonText);
        button.setHorizontalTextPosition(SwingConstants.CENTER); // Căn giữa theo chiều ngang
        button.setVerticalTextPosition(SwingConstants.CENTER);   // Căn giữa theo chiều dọc
        
        // --- TRANG TRÍ CHỮ CHO NGẦU ---
        button.setFont(new Font("Segoe UI", Font.BOLD, 20)); 
        button.setForeground(Color.BLACK); 

        // Xóa nền gốc của nút Java
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setOpaque(false);
        button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    }

    // Hàm giữ chất lượng ảnh khung luôn sắc nét VÀ CÓ BO GÓC
    private static Image getHighQualityScaledImage(Image srcImg, int w, int h, int cornerRadius) {
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();

        // Khử răng cưa giúp viền bo góc mượt hơn
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // --- PHẦN CẮT BO GÓC ---
        java.awt.geom.RoundRectangle2D roundedRectangle = new java.awt.geom.RoundRectangle2D.Double(0, 0, w, h, cornerRadius, cornerRadius);
        g2.setClip(roundedRectangle); // Chỉ vẽ phần ảnh nằm trong hình bo góc này

        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();
        return resizedImg;
    }
}
