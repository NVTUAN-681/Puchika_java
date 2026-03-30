/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pikachu_master.User_Interface.Components;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.BevelBorder;

/**
 *
 * @author laptop
 */
public class RoundedIconButton extends JButton {
    
   private final Color normalColor = new Color(220, 220, 220); // Màu gạch gốc
    private final Color hoverColor = new Color(245, 245, 245);  // Màu sáng khi di chuột
    private final Color selectedColor = new Color(255, 255, 100); // Màu vàng khi bấm chọn
    private boolean isSelectedState = false;

    public RoundedIconButton(ImageIcon icon, int radius) {
        super(icon);
        
        // Thiết lập viền 3D nổi lên và màu nền
        setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        setBackground(normalColor);
        setContentAreaFilled(true);
        setOpaque(true);
        setFocusPainted(false); // Bỏ viền chấm chấm

        // Xử lý sự kiện di chuột (Hover)
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (!isSelectedState && isVisible()) setBackground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (!isSelectedState && isVisible()) setBackground(normalColor);
            }
        });
    }

    public void setSelectedState(boolean selected) {
        this.isSelectedState = selected;
        setBackground(selected ? selectedColor : normalColor);
    }
}
