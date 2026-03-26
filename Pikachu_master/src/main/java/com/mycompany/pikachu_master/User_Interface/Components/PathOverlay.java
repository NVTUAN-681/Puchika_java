/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pikachu_master.User_Interface.Components;

import javax.swing.JComponent;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.util.List;
import javax.swing.Timer;

/**
 *
 * @author laptop
 */
public class PathOverlay extends JComponent{
    private List<Point> pathGrid;
    private Rectangle boardBounds;
    private int rows, cols;
    private Timer clearTimer;

    public PathOverlay() {
        setOpaque(false); // Đảm bảo mặt kính hoàn toàn trong suốt
    }

    public void showPath(List<Point> pathGrid, Rectangle boardBounds, int rows, int cols) {
        this.pathGrid = pathGrid;
        this.boardBounds = boardBounds;
        this.rows = rows;
        this.cols = cols;
        repaint(); // Yêu cầu vẽ lại ngay lập tức

        // Xoá đường đi sau 0.4 giây
        if (clearTimer != null && clearTimer.isRunning()) {
            clearTimer.stop();
        }
        clearTimer = new Timer(400, e -> {
            this.pathGrid = null;
            repaint();
        });
        clearTimer.setRepeats(false);
        clearTimer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (pathGrid == null || pathGrid.size() < 2 || boardBounds == null) {
            return;
        }

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Cài đặt màu sắc và độ dày đường nét (Màu đỏ nét to)
        g2.setColor(new Color(255, 60, 60, 220));
        g2.setStroke(new BasicStroke(5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        // Tính toán kích thước 1 ô
        int tileW = boardBounds.width / cols;
        int tileH = boardBounds.height / rows;

        int[] xPoints = new int[pathGrid.size()];
        int[] yPoints = new int[pathGrid.size()];

        for (int i = 0; i < pathGrid.size(); i++) {
            Point p = pathGrid.get(i);
            int gridRow = p.x; 
            int gridCol = p.y; 

            // Tính toạ độ Pixel tâm của ô
            xPoints[i] = boardBounds.x + (gridCol - 1) * tileW + (tileW / 2);
            yPoints[i] = boardBounds.y + (gridRow - 1) * tileH + (tileH / 2);
        }

        // Vẽ đường nối các điểm
        g2.drawPolyline(xPoints, yPoints, pathGrid.size());

       // Vẽ các chấm tròn ở ĐÚNG 2 ĐẦU (Điểm bắt đầu và Điểm kết thúc)
        g2.setColor(new Color(255, 215, 0)); // Màu vàng
        if (pathGrid.size() > 0) {
            // Chấm ở con Pikachu thứ nhất (Điểm đầu)
            g2.fillOval(xPoints[0] - 6, yPoints[0] - 6, 10, 10);
            
            // Chấm ở con Pikachu thứ hai (Điểm cuối)
            int lastIndex = pathGrid.size() - 1;
            g2.fillOval(xPoints[lastIndex] - 6, yPoints[lastIndex] - 6, 10, 10);
        }
    }
}
