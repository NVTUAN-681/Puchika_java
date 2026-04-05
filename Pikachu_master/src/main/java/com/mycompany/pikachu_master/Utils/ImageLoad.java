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
import java.awt.Graphics2D;

public class ImageLoad {
   // Map lưu trữ: Key là ID (1-21), Value là đối tượng Image tương ứng (CHẾ ĐỘ THƯỜNG)
    private static Map<Integer, ImageIcon> imageMap = new HashMap<>();
    
    // === [THÊM MỚI] 1. Khai báo Map và Cờ cho chế độ Asian ===
    private static Map<Integer, ImageIcon> asianImageMap = new HashMap<>(); // Chứa ảnh của thư mục 1life
    public static boolean isAsianMode = false; // Cờ báo hiệu đang ở chế độ Asian
    // ==========================================================

    private static Map<String, ImageIcon[]> buttonIconMap = new HashMap<>();
// ---> 1. GẮN CỨNG 2 ĐƯỜNG DẪN ẢNH VÀO ĐÂY <---
    public static final String PATH_1 = "/images/Picture_button/BackgroundButtonInMain.png";
    public static final String PATH_2 = "/images/Picture_button/BackgroundButtonMainGame.png";
    public static final String PATH_3 = "/images/Picture_button/BackgroundButtonLight.png";
    public static final String PATH_4 = "/images/Picture_button/BackgroundButtonLight_Dark.png";
    
    private static boolean isLoadedImage = false;
//    private static boolean isLoadedBg = false;
    // Hàm load toàn bộ 21 ảnh vào bộ nhớ khi bắt đầu game
    public static void loadAllImagesPika() {
        if(isLoadedImage == false){
            for (int i = 1; i <= 91; i++) {
                try {
                    // Đường dẫn tương đối trong project Maven
                    String path = "/images/Picture/" + i + ".png"; 
                    java.net.URL imgURL = ImageLoad.class.getResource(path);

                    if (imgURL != null) {
                        BufferedImage originalImage = ImageIO.read(imgURL);
                        Image smartScaledImg = trimAndFit(originalImage, 75); 
                        imageMap.put(i, new ImageIcon(smartScaledImg));
                    }
                    else{
                        System.err.println(" Path Not found: " + path);
                    }
                } catch (Exception e) {
                    System.out.println("ID not found: " + i);
                }
            }
            loadAsianImagesPika();
            isLoadedImage = true;
        }
    }
    
    public static void loadAsianImagesPika() {
        for (int i = 1; i <= 45; i++) { 
            try {
                String path = "/images/Picture_wood/" + i + ".png"; 
                java.net.URL imgURL = ImageLoad.class.getResource(path);
                
                if (imgURL != null) {
                    BufferedImage originalImage = ImageIO.read(imgURL);
                    Image smartScaledImg = trimAndFit(originalImage, 75); 
                    asianImageMap.put(i, new ImageIcon(smartScaledImg));                    
                } else {
                    System.err.println("not asian: " + path);
                }
            } catch (Exception e) {
                System.out.println("Asian ID not found: " + i);
            }
        }
    }
    
    //tách biệt không liên quan đến icon game
    
    public static Image trimAndFit(BufferedImage img, int targetSize) {
        try {
            int width = img.getWidth();
            int height = img.getHeight();
            int top = height, bottom = 0, left = width, right = 0;

            // 1. Quét tia X tìm ranh giới thực sự của con Pikachu
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int alpha = (img.getRGB(x, y) >> 24) & 0xff;
                    if (alpha > 10) { 
                        top = Math.min(top, y);
                        bottom = Math.max(bottom, y);
                        left = Math.min(left, x);
                        right = Math.max(right, x);
                    }
                }
            }

            if (top > bottom || left > right) {
                 return img.getScaledInstance(targetSize, targetSize, Image.SCALE_SMOOTH);
            }

            // 2. Cắt sát sạt lấy đúng phần "thịt" có chứa Pikachu
            BufferedImage cropped = img.getSubimage(left, top, right - left + 1, bottom - top + 1);

            // 3. Tạo một khung hình vuông để chống méo ảnh
            int maxDim = Math.max(cropped.getWidth(), cropped.getHeight());
            BufferedImage squareImg = new BufferedImage(maxDim, maxDim, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = squareImg.createGraphics();
            
            // Đặt Pikachu vào chính giữa hình vuông
            int x = (maxDim - cropped.getWidth()) / 2;
            int y = (maxDim - cropped.getHeight()) / 2;
            g2.drawImage(cropped, x, y, null);
            g2.dispose();

            // 4. QUAN TRỌNG: Cài đặt khoảng cách an toàn (Padding)
            int padding = 10; // Khoảng cách cách viền gỗ 10 pixel mỗi bên
            int innerSize = targetSize - (padding * 2); // Ví dụ target 85 -> ruột còn 65

            // 5. Thu nhỏ con Pikachu cho vừa phần ruột
            Image scaledInner = squareImg.getScaledInstance(innerSize, innerSize, Image.SCALE_SMOOTH);

            // 6. Tạo một tấm kính tàng hình kích thước chuẩn (85x85)
            BufferedImage finalImg = new BufferedImage(targetSize, targetSize, BufferedImage.TYPE_INT_ARGB);
            Graphics2D gFinal = finalImg.createGraphics();
            
            // Dán con Pikachu đã thu nhỏ vào giữa tấm kính (dịch vào 1 khoảng = padding)
            gFinal.drawImage(scaledInner, padding, padding, null);
            gFinal.dispose();

            return finalImg;
        } catch (Exception e) {
             return img.getScaledInstance(targetSize, targetSize, Image.SCALE_SMOOTH);
        }
    }
    
    public static ImageIcon getImage(int id) {
        // Nếu cờ Asian đang bật VÀ có ảnh trong thư mục 1life thì trả về ảnh Asian
        if (isAsianMode && asianImageMap.containsKey(id)) {
            return asianImageMap.get(id);
        }
        // Nếu không thì trả về ảnh mặc định
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
   // ---> 3. HÀM MỚI: TRUYỀN SỐ 1, 2, 3 HOẶC 4 <---
   public static void loadBg(String key, int loaiAnh, int w, int h, int corner) {
        // Phân tách rõ ràng 4 loại ảnh
        String path = PATH_1; // Mặc định là 1 (Main Screen)
        
        if (loaiAnh == 2) {
            path = PATH_2;       // Dark side
        } else if (loaiAnh == 3) {
            path = PATH_3;       // Light side
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
                System.err.println("❌ KHONG TIM THAY BACKGROUND NUT: " + path);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
