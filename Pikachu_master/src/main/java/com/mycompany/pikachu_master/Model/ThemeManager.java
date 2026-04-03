/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pikachu_master.Model;

import java.awt.Image;
import javax.swing.ImageIcon;
/**
 *
 * @author laptop
 */
public class ThemeManager {
// Khai báo hằng số cho các phe đỡ bị gõ sai chính tả
    public static final String SIDE_LIGHT = "LIGHT";
    public static final String SIDE_DARK = "DARK";
    public static final String SIDE_DEFAULT = "NONE";
    
    // THÊM DÒNG NÀY: Biến toàn cục lưu phe hiện tại
    public static String currentTheme = SIDE_DEFAULT;
    
    /**
     * Hàm lấy ảnh nền tổng theo phe
     */
    public static Image getBackgroundImage(String side) {
        String imagePath = "";
        
        switch (side) {
            case SIDE_LIGHT:
                imagePath = "/images/BackGround/BackgroundLight1.png";
                break;
            case SIDE_DARK:
                imagePath = "/images/BackGround/backGroundMain.jpg"; // Nhớ check lại tên file
                break;
            default:
                imagePath = "/images/BackGround/BackgroundStartScreen.jpg";
                break;
        }
        
        try {
            java.net.URL imgUrl = ThemeManager.class.getResource(imagePath);
            if (imgUrl != null) {
                return new ImageIcon(imgUrl).getImage();
            } else {
                System.out.println("Không tìm thấy ảnh tại: " + imagePath);
            }
        } catch (Exception e) {
            System.out.println("Lỗi load ảnh nền: " + e.getMessage());
        }
        return null;
    }
    
    /**
     * Hàm lấy màu viền JFrame theo phe
     */
    public static java.awt.Color getBorderColor(String side) {
        switch (side) {
            case SIDE_LIGHT: return new java.awt.Color(255, 215, 0);   // Vàng
            case SIDE_DARK:  return new java.awt.Color(180, 0, 255);   // Tím
            default:         return new java.awt.Color(255, 215, 0);   // Mặc định
        }
    }
    
    /**
     * Hàm lấy màu kính phủ (Hover Color) theo phe
     */
    public static java.awt.Color getOverlayColor(String side, boolean isHover) {
        if (side.equals(SIDE_LIGHT)) {
            return isHover ? new java.awt.Color(255, 255, 220, 220) : new java.awt.Color(255, 255, 220, 150);
        } else {
            return isHover ? new java.awt.Color(45, 45, 70, 220) : new java.awt.Color(20, 20, 40, 180);
        }
    }
}
