/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pikachu_master.User_Interface.Components;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 *
 * @author laptop
 */
public class RoundedIconButton extends JButton {
    
    private int cornerRadius = 20; // Độ bo tròn (bán kính góc)
    private Color normalColor = Color.WHITE; // Màu nền bình thường
    private Color clickedColor = Color.YELLOW; // Màu nền khi được chọn
    private Color hoverColor = new Color(245, 245, 245); // Màu khi di chuột qua
    private Color borderColor = Color.GRAY; // Màu viền

    private boolean selected = false;
    private Color selectBorderColor = Color.YELLOW;
    
    public RoundedIconButton(ImageIcon icon, int radius) {
        super(icon); // Đặt hình ảnh
        this.cornerRadius = radius;

        // BẮT BUỘC: Làm cho nút không trong suốt và không vẽ nội dung mặc định của Swing
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false); // Chúng ta tự vẽ viền
        setFocusPainted(false); // Loại bỏ viền chấm chấm khó chịu của Swing khi focus

        // Căn giữa hình ảnh
        setHorizontalTextPosition(JButton.CENTER);
        setVerticalTextPosition(JButton.CENTER);
    }

    // --> SỬA TẠI ĐÂY 2: Thêm phương thức để thiết lập trạng thái được chọn
    public void setSelectedState(boolean selected) {
        this.selected = selected;
        repaint(); // Yêu cầu vẽ lại nút để hiển thị trạng thái mới
    }
    // --> KẾT THÚC SỬA 2

    /**
     * Ghi đè phương thức paintComponent để tự vẽ nền và hình ảnh bo tròn
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        // Kích hoạt tính năng khử răng cưa (antialiasing) để góc bo trông mượt mà
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // --> SỬA TẠI ĐÂY 3: Sửa lại tọa độ hình dạng bo tròn để tránh bị cắt viền
        // shape = new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius); // <-- CŨ
        // FIXED shape (Subtract 1 pixel to make the border fully visible)
        int padding = 4;
        Shape shape = new RoundRectangle2D.Double(padding, padding, getWidth()-4.5, getHeight()-3.8, cornerRadius, cornerRadius);
        // --> KẾT THÚC SỬA 3
        g2.clip(shape);
        // Định nghĩa hình dạng bo tròn (RoundRectangle2D)
        // Shape shape = new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius);
        // THIẾT LẬP MÀU NỀN dựa trên trạng thái (nhấn, di chuột, bình thường)
//        if (getModel().isRollover()) {
//            g2.setColor(hoverColor);
//            //        } else if (getModel().isRollover()) {
//            //            g2.setColor(hoverColor);
//            //        } 
//        }else {
//            g2.setColor(normalColor);
        //       }
        g2.setColor(normalColor);

        // Vẽ nền bo tròn
        g2.fill(shape);
       // paintComponent(g2);

        // GIỚI HẠN VÙNG VẼ (Clipping): Bắt buộc để bo tròn cả hình ảnh bên trong
        g2.clip(shape);

        // Gọi phương thức paintComponent mặc định để vẽ Icon (bây giờ nó sẽ bị bo tròn theo clip)
        super.paintComponent(g2);

        // Vẽ viền bo tròn
        // g2.setColor(borderColor);
        //g2.draw(shape);
        // --- VẼ VIỀN (BORDER PAINTING) ---
        // --> SỬA TẠI ĐÂY 4: Loại bỏ clipping để viền được vẽ rõ ràng và thay đổi màu viền
        g2.setClip(null); // Bỏ clipping để vẽ viền không bị cắt

        // Thiết lập màu và độ dày viền
        if (selected) {
            g2.setColor(selectBorderColor); // Màu vàng khi được chọn
            g2.setStroke(new BasicStroke(3.0f)); // Viền dày 3 pixel
            g2.draw(shape);
        } else if (getModel().isPressed()) {
            g2.setColor(Color.RED); // Nổi bật viền khi nhấn (option)
            g2.setStroke(new BasicStroke(1.0f));
            g2.draw(shape);
        } 

        g2.dispose();
    }
}
