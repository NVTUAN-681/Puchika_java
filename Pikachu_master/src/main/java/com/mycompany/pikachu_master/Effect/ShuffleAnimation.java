/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pikachu_master.Effect;

import com.mycompany.pikachu_master.Controller.PlayScreen;
import com.mycompany.pikachu_master.User_Interface.Components.RoundedIconButton;
import com.mycompany.pikachu_master.User_Interface.Screens.MainScreen;
import com.mycompany.pikachu_master.Utils.ImageLoad;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
/**
 *
 * @author laptop
 */
public class ShuffleAnimation {
    // Lớp hỗ trợ lưu trữ thông tin của từng icon đang bay
    private static class AnimNode {
        JLabel label;
        Point start;
        Point target;
        int r, c;

        AnimNode(JLabel l, Point s, Point t, int row, int col) {
            this.label = l;
            this.start = s;
            this.target = t;
            this.r = row;
            this.c = col;
        }
    }

    public static void trigger(PlayScreen playScreen) {
        Window window = SwingUtilities.getWindowAncestor(playScreen);
        if (!(window instanceof MainScreen)) {
            // Backup an toàn nếu lỗi giao diện: cứ đảo bình thường
            playScreen.getAlgorithm().shuffle(playScreen.getBoard());
            playScreen.onShuffleComplete();
            return;
        }
        
        MainScreen main = (MainScreen) window;
        JLayeredPane layeredPane = main.getLayeredPane();

        //  Khóa thao tác click chuột của người chơi trong lúc trộn
        playScreen.setProcessingMismatch(true); 

        int rows = playScreen.getRows();
        int cols = playScreen.getCols();
        RoundedIconButton[][] btnMatrix = playScreen.getBtnMatrix();

        List<AnimNode> nodes = new ArrayList<>();
        
        // Tính toán tọa độ tâm của bàn chơi để hút tất cả vào đó
        Point centerPlayScreen = new Point(playScreen.getWidth() / 2, playScreen.getHeight() / 2);
        Point center = SwingUtilities.convertPoint(playScreen, centerPlayScreen, main.getContentPane());

        // Tạo các JLabel giả (ảnh clone) cho tất cả các nút ĐANG HIỂN THỊ
        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= cols; j++) {
                if (playScreen.getBoard().getCell(i, j).isStatus()) {
                    RoundedIconButton btn = btnMatrix[i][j];
                    JLabel label = new JLabel(btn.getIcon()); // Lấy ảnh hiện tại
                    label.setSize(btn.getSize());
                    
                    // Lấy tọa độ thật của nút trên lưới
                    Point startLoc = SwingUtilities.convertPoint(playScreen, btn.getLocation(), main.getContentPane());
                    label.setLocation(startLoc);
                    
                    layeredPane.add(label, JLayeredPane.DRAG_LAYER);
                    nodes.add(new AnimNode(label, startLoc, center, i, j));
                    
                    // Giấu nút thật đi
                    btn.setVisible(false); 
                }
            }
        }

         main.playSoundEffect("/sound/SoundTap/NextScreen.wav");

        // --- GIAI ĐOẠN 1: HÚT VÀO TÂM ---
        int frames1 = 15; // Tốc độ hút (số khung hình)
        int delay = 15;   // Độ trễ mỗi khung hình
        Timer timer1 = new Timer(delay, null);
        timer1.addActionListener(new ActionListener() {
            int frame = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                frame++;
                float progress = (float) frame / frames1;

                for (AnimNode node : nodes) {
                    int x = (int) (node.start.x + (node.target.x - node.start.x) * progress);
                    int y = (int) (node.start.y + (node.target.y - node.start.y) * progress);
                    node.label.setLocation(x, y);
                }

                if (frame >= frames1) {
                    timer1.stop();
                    
                    // KHI ĐÃ HÚT VÀO GIỮA -> THỰC HIỆN TRỘN LOGIC BÊN DƯỚI NỀN
                    playScreen.getAlgorithm().shuffle(playScreen.getBoard());
                    
                    // Lấy data mới sau khi trộn để gắn ảnh mới cho các JLabel
                    for (AnimNode node : nodes) {
                        int r = node.r;
                        int c = node.c;
                        int newId = playScreen.getBoard().getCell(r, c).getId();
                        node.label.setIcon(ImageLoad.getImage(newId)); // Đổi ảnh mới
                        
                        // Chuẩn bị cho giai đoạn 2: Bay từ Tâm (start) ra Vị trí Lưới (target)
                        node.start = new Point(center);
                        node.target = SwingUtilities.convertPoint(playScreen, btnMatrix[r][c].getLocation(), main.getContentPane());
                    }

                    // --- CHẠY GIAI ĐOẠN 2: PHÓNG RA ---
                    startPhase2(playScreen, main, layeredPane, nodes);
                }
            }
        });
        timer1.start();
    }

    private static void startPhase2(PlayScreen playScreen, MainScreen main, JLayeredPane layeredPane, List<AnimNode> nodes) {
        int frames2 = 15; // Tốc độ phóng ra
        int delay = 15;
        Timer timer2 = new Timer(delay, null);
        timer2.addActionListener(new ActionListener() {
            int frame = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                frame++;
                float progress = (float) frame / frames2;
                
                for (AnimNode node : nodes) {
                    int x = (int) (node.start.x + (node.target.x - node.start.x) * progress);
                    int y = (int) (node.start.y + (node.target.y - node.start.y) * progress);
                    node.label.setLocation(x, y);
                }

                if (frame >= frames2) {
                    timer2.stop();
                    
                    // Dọn dẹp: Xóa các ảnh giả bay lượn trên lớp LayeredPane
                    for (AnimNode node : nodes) {
                        layeredPane.remove(node.label);
                    }
                    layeredPane.repaint();

                    // Báo cho PlayScreen biết Animation đã xong để cập nhật lại UI thật
                    playScreen.onShuffleComplete();
                }
            }
        });
        timer2.start();
    }
}