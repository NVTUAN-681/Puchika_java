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
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

public class ImageLoad {
    // Map lưu trữ: Key là ID (1-21), Value là đối tượng Image tương ứng
    private static Map<Integer, ImageIcon> imageMap = new HashMap<>();
    private static Map<String, ImageIcon[]> buttonIconMap = new HashMap<>();

    // ---> 1. GẮN CỨNG 2 ĐƯỜNG DẪN ẢNH VÀO ĐÂY <---
    public static final String PATH_1 = "/images/Picture_button/BackgroundButtonInMain.png";
    public static final String PATH_2 = "/images/Picture_button/BackgroundButtonMainGame.png";
    public static final String PATH_3 = "/images/Picture_button/BackgroundButtonLight.png";
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
                    System.err.println("❌ Not found: " + path);
                }
            } catch (Exception e) {
                System.out.println("ID not found: " + i);
            }
        }
    }
    
    //tách biệt không liên quan đến icon game
    

    public static ImageIcon getImage(int id) {
        return imageMap.get(id);
    }
    
    // 1. Thêm hàm mới: Trả về mảng Icon dựa trên Key
    public static ImageIcon[] getButtonIcons(String key){
        return buttonIconMap.get(key); 
    }
    
   // ---> 2. HÀM CŨ CHO STARTSCREEN <---
    // Vẫn giữ để file StartScreen không bị lỗi, tự động gọi ảnh số 1
    public static void BackgroundButtonsLoad() {
        loadBg("MENU_FRAME", 1, 650, 60, 25);
    }
    
    // ---> 3. HÀM MỚI: CHỈ CẦN TRUYỀN SỐ 1 HOẶC 2 <---
   public static void loadBg(String key, int loaiAnh, int w, int h, int corner) {
        // Phân tách rõ ràng 3 loại ảnh
        String path = PATH_1; // Mặc định là 1 (Main Screen)
        if (loaiAnh == 2) {
            path = PATH_2;    // Dark side
        } else if (loaiAnh == 3) {
            path = PATH_3;    // Light side
        }
        
        URL imgURL = ImageLoad.class.getResource(path);
        try {
            if (imgURL != null) {
                BufferedImage originalImage = ImageIO.read(imgURL);

                ImageIcon normalIcon = new ImageIcon(Button_Icon.getHighQualityScaledImage(originalImage, w, h, corner));
                ImageIcon hoverIcon = new ImageIcon(Button_Icon.getHighQualityScaledImage(originalImage, (int)(w*1.025), (int)(h*1.025), corner));
                
                // Lưu ảnh vào map với cái tên (key) mày truyền vào
                buttonIconMap.put(key, new ImageIcon[]{normalIcon, hoverIcon});
            } else {
                System.err.println("❌ KHÔNG TÌM THẤY ẢNH BACKGROUND NÚT: " + path);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
