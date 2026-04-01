/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pikachu_master.Utils;

/**
 *
 * @author DELL
 */

import javax.swing.ImageIcon;
import java.awt.Image;
import java.util.HashMap;
import java.util.Map;

public class ImageLoad {
    // Map lưu trữ: Key là ID (1-21), Value là đối tượng Image tương ứng
    private static Map<Integer, ImageIcon> imageMap = new HashMap<>();

    // Hàm load toàn bộ 21 ảnh vào bộ nhớ khi bắt đầu game
    public static void loadAllImagesPika() {
        
        for (int i = 1; i <= 21; i++) {
            try {
                // Đường dẫn tương đối trong project Maven
                String path = "/images/Picture/" + i + ".jpg"; 
                java.net.URL imgURL = ImageLoad.class.getResource(path);
                
                if (imgURL != null) {
                    ImageIcon icon = new ImageIcon(imgURL);
                    // Có thể resize ảnh tại đây để khớp với kích thước nút bấm
                    Image scaledImg = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                    imageMap.put(i, new ImageIcon(scaledImg));
                }
                else{
                    System.err.println("❌ KHÔNG TÌM THẤY: " + path);
                }
            } catch (Exception e) {
                System.out.println("Không tìm thấy ảnh ID: " + i);
            }
        }
    }

    public static ImageIcon getImage(int id) {
        return imageMap.get(id);
    }
    
    public static void loadBackgroundButton(){
        try{
            String path = "/images/Picture_button/BackgroundButtonInMain.png";
            java.net.URL imgURL = ImageLoad.class.getResource(path);
            
            if(imgURL != null){
                ImageIcon icon = new ImageIcon(imgURL);
 
            }
        } catch(Exception e){
            System.out.println("Không tìm thấy ảnh");
        }
    } 
    
    
}
