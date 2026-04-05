/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pikachu_master.Utils;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicButtonUI;

/**
 *
 * @author laptop
 */
public class WoodButtonUI extends BasicButtonUI{
    private boolean isDarkSide;
    private Color baseColor;
    private Color lightEdge;
    private Color darkEdge;
    private Color textColor;

    // Constructor nhận vào true/false để biết vẽ gỗ tối hay gỗ sáng
    public WoodButtonUI(boolean isDarkSide) {
        this.isDarkSide = isDarkSide;
        
        if (isDarkSide) {
            // Gỗ tối (Dark Oak)
            baseColor = new Color(48, 52, 70);     
            lightEdge = new Color(75, 85, 110);    
            darkEdge = new Color(20, 25, 35);      
            textColor = new Color(0, 255, 255);    // Chữ Vàng Gold
        } else {
            // Gỗ thông sáng (Pine Wood)
            baseColor = new Color(222, 184, 135);  
            lightEdge = new Color(255, 225, 180);  
            darkEdge = new Color(180, 140, 90);    
            textColor = new Color(60, 40, 20);     // Chữ Nâu đậm
        }
    }

    // Hàm này tự động chạy khi bạn gán UI này cho nút
    @Override
    public void installUI(JComponent c) {
        super.installUI(c);
        AbstractButton btn = (AbstractButton) c;
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(false);
        btn.setForeground(textColor);
    }

   @Override
    public void paint(Graphics g, JComponent c) {
        Graphics2D g2 = (Graphics2D) g.create();
        // Bật khử răng cưa cho nét vẽ mịn màng
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        AbstractButton b = (AbstractButton) c;

        int w = c.getWidth();
        int h = c.getHeight();
        int arc = 20; // Độ bo góc

        boolean isPressed = b.getModel().isPressed();
        boolean isHover = b.getModel().isRollover();
        
        // Nút sẽ lún xuống 3 pixel khi bấm
        int offset = isPressed ? 3 : 0; 

        // --- 1. VẼ ĐỔ BÓNG MỀM (Soft Drop Shadow) ---
        // Chỉ vẽ bóng khi nút không bị bấm (nổi lên)
        if (!isPressed) {
            for (int i = 1; i <= 4; i++) { 
                // Vẽ 4 lớp bóng, càng ra xa càng mờ dần
                g2.setColor(new Color(0, 0, 0, 50 / i)); 
                g2.fillRoundRect(offset + i, offset + i + 2, w - 4, h - 4, arc, arc);
            }
        }

        // Làm màu gỗ sáng hơn một chút khi lướt chuột qua
        Color drawBase = isHover && !isPressed ? baseColor.brighter() : baseColor;

        // --- 2. HIỆU ỨNG LỒI LÕM BẰNG GRADIENT ---
        // Không bấm -> Sáng ở trên, tối ở dưới (Cảm giác lồi)
        // Có bấm -> Tối ở trên, sáng ở dưới (Cảm giác lõm xuống)
        Color topColor = isPressed ? drawBase.darker() : drawBase.brighter();
        Color bottomColor = isPressed ? drawBase.brighter() : drawBase.darker();
        
        java.awt.GradientPaint gp = new java.awt.GradientPaint(0, 0, topColor, 0, h, bottomColor);
        g2.setPaint(gp);
        // Vẽ phần thịt gỗ
        g2.fillRoundRect(offset, offset, w - 4, h - 4, arc, arc);

        // --- 3. VIỀN SẮC NÉT (BEVEL EDGES) ---
        g2.setStroke(new BasicStroke(2.0f));
        
        // Đảo ngược vị trí vùng sáng/tối khi bấm
        Color topLeftEdge = isPressed ? darkEdge : lightEdge;
        Color bottomRightEdge = isPressed ? lightEdge : darkEdge;

        // Viền Cạnh Trên & Trái
        g2.setColor(topLeftEdge);
        g2.drawArc(offset, offset, arc, arc, 90, 90); 
        g2.drawLine(offset + arc/2, offset, w - 4 - arc/2, offset); 
        g2.drawLine(offset, offset + arc/2, offset, h - 4 - arc/2); 

        // Viền Cạnh Dưới & Phải
        g2.setColor(bottomRightEdge);
        g2.drawArc(w - 4 - arc, h - 4 - arc, arc, arc, 270, 90); 
        g2.drawLine(offset + arc/2, h - 4, w - 4 - arc/2, h - 4); 
        g2.drawLine(w - 4, offset + arc/2, w - 4, h - 4 - arc/2); 

        g2.dispose();
        
        // Gọi super.paint để Java vẽ đè con Pikachu (hoặc Text) lên cái nền gỗ vừa tạo
        super.paint(g, c); 
    }

    // Tùy chỉnh làm chữ/icon lún xuống khi bấm
    @Override
    protected void paintText(Graphics g, JComponent c, Rectangle textRect, String text) {
        AbstractButton b = (AbstractButton) c;
        int offset = b.getModel().isPressed() ? 2 : 0;
        textRect.y += offset; 
        textRect.x += offset;
        super.paintText(g, c, textRect, text);
    }
}
