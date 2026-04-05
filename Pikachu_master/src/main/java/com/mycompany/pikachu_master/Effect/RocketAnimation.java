/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pikachu_master.Effect;
import com.mycompany.pikachu_master.Controller.PlayScreen;
import com.mycompany.pikachu_master.Model.Cell;
import com.mycompany.pikachu_master.Model.CellPair;
import com.mycompany.pikachu_master.User_Interface.Components.RoundedIconButton;
import com.mycompany.pikachu_master.User_Interface.Screens.MainScreen;
import com.mycompany.pikachu_master.Utils.ImageLoad;
import java.awt.Image;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
/**
 *
 * @author laptop
 */
public class RocketAnimation {
    public static void triggerRocketEffect(PlayScreen playScreen, RoundedIconButton startBtn1, RoundedIconButton startBtn2, CellPair targetPair) {
        
//        CellPair targetPair = playScreen.getAlgorithm().findHint(playScreen.getBoard());
        if (targetPair == null) {
            if (playScreen.isBoardEmpty())
                playScreen.showHonorScreen();
            return; 
        }

        Cell tCell1 = targetPair.getCell1();
        Cell tCell2 = targetPair.getCell2();
        RoundedIconButton targetBtn1 = playScreen.getBtnMatrix()[tCell1.getX()][tCell1.getY()];
        RoundedIconButton targetBtn2 = playScreen.getBtnMatrix()[tCell2.getX()][tCell2.getY()];

        Window window = SwingUtilities.getWindowAncestor(playScreen);
        if (!(window instanceof MainScreen)) return;
        MainScreen main = (MainScreen) window;

        int ROCKET_ID = 1; 
        ImageIcon originalIcon = ImageLoad.getImage(ROCKET_ID); 
        Image img = originalIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon rocketIcon = new ImageIcon(img);

        JLabel rocket1 = new JLabel(rocketIcon);
        JLabel rocket2 = new JLabel(rocketIcon);
        rocket1.setSize(40, 40);
        rocket2.setSize(40, 40);

        Point s1 = SwingUtilities.convertPoint(playScreen, startBtn1.getLocation(), main.getContentPane());
        Point s2 = SwingUtilities.convertPoint(playScreen, startBtn2.getLocation(), main.getContentPane());
        Point t1 = SwingUtilities.convertPoint(playScreen, targetBtn1.getLocation(), main.getContentPane());
        Point t2 = SwingUtilities.convertPoint(playScreen, targetBtn2.getLocation(), main.getContentPane());

        rocket1.setLocation(s1.x, s1.y);
        rocket2.setLocation(s2.x, s2.y);

        main.getLayeredPane().add(rocket1, JLayeredPane.DRAG_LAYER);
        main.getLayeredPane().add(rocket2, JLayeredPane.DRAG_LAYER);
        
        // ---> THÊM HIỆU ỨNG: Phát âm thanh khi tên lửa đang BAY
        main.playSoundEffect("/sound/SoundRocket/RocketFly.wav");
        
        playScreen.setProcessingMismatch(true); 

        int totalFrames = 35; 
        int delayPerFrame = 15; 

        Timer animTimer = new Timer(delayPerFrame, null);
        animTimer.addActionListener(new ActionListener() {
            int currentFrame = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                currentFrame++;
                float progress = (float) currentFrame / totalFrames;

                rocket1.setLocation((int) (s1.x + (t1.x - s1.x) * progress), (int) (s1.y + (t1.y - s1.y) * progress));
                rocket2.setLocation((int) (s2.x + (t2.x - s2.x) * progress), (int) (s2.y + (t2.y - s2.y) * progress));
                //Ten lửa đến đích
                if (currentFrame >= totalFrames) {
                    animTimer.stop();
                    
                    main.playSoundEffect("/sound/SoundRocket/RocketBoom.wav");
                    try {
                        // Load ảnh vụ nổ (Đảm bảo bạn có file explosion.png trong folder)
                        ImageIcon expOriginal = new ImageIcon(RocketAnimation.class.getResource("/images/Picture_Rocket/Boom.png"));
                        Image expImg = expOriginal.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                        ImageIcon explosionIcon = new ImageIcon(expImg);
                        
                        // Đổi icon của tên lửa thành vụ nổ
                        rocket1.setIcon(explosionIcon);
                        rocket2.setIcon(explosionIcon);
                        rocket1.setSize(50, 50);
                        rocket2.setSize(50, 50);
                    } catch (Exception ex) {
                        System.out.println("Loi Boom: " + ex.getMessage());
                    }

                    // Tạo một timer chờ 300ms (0.3s) để người chơi kịp nhìn thấy hiệu ứng nổ rồi mới xóa ảnh đi
                    Timer explosionTimer = new Timer(300, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent evt) {
                            main.getLayeredPane().remove(rocket1);
                            main.getLayeredPane().remove(rocket2);
                            main.getLayeredPane().repaint();
                        }
                    });
                    explosionTimer.setRepeats(false);
                    explosionTimer.start();

                    playScreen.getAlgorithm().removePair(tCell1, tCell2, playScreen.getBoard());
                    targetBtn1.setVisible(false);
                    targetBtn2.setVisible(false);
                    targetBtn1.setSelectedState(false);
                    targetBtn2.setSelectedState(false);
                    playScreen.updateAllButtons();

                    playScreen.setProcessingMismatch(false); 

                    if (playScreen.isBoardEmpty()) {
                        playScreen.showHonorScreen();
                    } else if (!playScreen.getAlgorithm().hasAnyMatch(playScreen.getBoard())) {
                        playScreen.shuffle();
                        playScreen.updateAllButtons();
                    }
                }
            }
        });
        animTimer.start();
    }
}
