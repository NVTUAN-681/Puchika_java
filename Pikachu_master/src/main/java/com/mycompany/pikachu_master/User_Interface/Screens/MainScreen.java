/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.pikachu_master.User_Interface.Screens;

import com.mycompany.pikachu_master.Controller.GameConfig;
import com.mycompany.pikachu_master.Controller.PlayScreen;
import com.mycompany.pikachu_master.User_Interface.Components.BackgroundMain;
//import com.mycompany.pikachu_master.User_Interface.Components.cus;
import com.mycompany.pikachu_master.User_Interface.Components.BackgroundStartScreen;
import java.awt.event.ComponentEvent;

/**
 *
 * @author laptop
 */
public class MainScreen extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(MainScreen.class.getName());

    /**
     * Creates new form MainScreen
     *
     * @param config
     */
    GameConfig config;
    PlayScreen gameBoard;
    
    int hintCount;
    int shuffleCount;

    public javax.swing.Timer countdownTimer;
    private int maxTime;
    private int currentTime;

    private void initTimer() {
        maxTime = config.GetTimeLimit();
        if (maxTime <= 0) {
            maxTime = 120; // 120 giây mặc định nếu timeLimit <= 0
        }
        currentTime = maxTime;
        Timeline.setMaximum(maxTime);
        Timeline.setMinimum(0);
        Timeline.setValue(maxTime);

        if (countdownTimer == null) {
            countdownTimer = new javax.swing.Timer(1000, new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    currentTime--;
                    Timeline.setValue(currentTime);

                    // In ra console để bạn dễ theo dõi thời gian thực
                    System.out.println("Thoi gian con: " + currentTime + " giay");

                    if (currentTime <= 0) {
                        countdownTimer.stop();
                        handleGameOver();
                    }
                }
            });
        }
    }

    public void stopTimer() {
        if (countdownTimer != null && countdownTimer.isRunning()) {
            countdownTimer.stop();
        }
    }
    
    public void resumeTimer() {
        if (countdownTimer != null && !countdownTimer.isRunning() && currentTime > 0 ) {
            countdownTimer.start();
        }
    }
    // ---> THÊM HÀM NÀY VÀO ĐỂ TRANG TRÍ THANH TOP BAR <---
    private void styleTopBar() {
        // 1. Chỉnh màu chữ: Tiêu đề màu Trắng, Chỉ số màu Vàng Gold
        java.awt.Color textColor = java.awt.Color.WHITE;
        java.awt.Color valueColor = new java.awt.Color(255, 215, 0); // Vàng Gold

        levelLabel.setForeground(textColor);
        PointLabel.setForeground(textColor);
        coin.setForeground(textColor);

        level_point.setForeground(valueColor);
        point.setForeground(valueColor);
        coinLabel.setForeground(valueColor);

        // 2. Vẽ nền "Chuẩn Game" cho cái khung topBarPanel (Nền đen trong suốt + Viền Vàng bo góc)
        topBarPanel.setBorder(new javax.swing.border.AbstractBorder() {
            @Override
            public void paintBorder(java.awt.Component c, java.awt.Graphics g, int x, int y, int width, int height) {
                java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();
                g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Vẽ nền đen trong suốt (Alpha = 180)
                g2.setColor(new java.awt.Color(20, 20, 20, 180)); 
                g2.fillRoundRect(x + 2, y + 2, width - 4, height - 8, 25, 25);
                
                // Vẽ viền Vàng
                g2.setColor(valueColor); 
                g2.setStroke(new java.awt.BasicStroke(2f));
                g2.drawRoundRect(x + 2, y + 2, width - 4, height - 8, 25, 25);
                g2.dispose();
            }

            @Override
            public java.awt.Insets getBorderInsets(java.awt.Component c) {
                // Đẩy các component bên trong lùi vào một chút để không đè lên viền
                return new java.awt.Insets(5, 15, 5, 15); 
            }
        });

        // 3. Làm đẹp các nút bấm Hỗ trợ (Hint, Swap, Setting...)
        javax.swing.JButton[] toolBtns = {swapButton, timeButton, hintButton, settingmainButton};
        for (javax.swing.JButton btn : toolBtns) {
            btn.setBackground(new java.awt.Color(40, 40, 40)); // Nền nút xám đậm
            btn.setForeground(java.awt.Color.WHITE); // Icon/Chữ trắng
            btn.setFocusPainted(false); // Xóa khung viền chấm chấm khi click
            // Tạo viền mỏng màu vàng cho từng nút
            btn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 215, 0), 1, true));
        }

        // 4. Ẩn cái đường kẻ ngang mặc định đi cho đỡ chướng mắt
        jSeparator1.setVisible(false);
    }
    // ---> KẾT THÚC HÀM TRANG TRÍ <---

    private void handleGameOver() {
        //javax.swing.JOptionPane.showMessageDialog(this, "Hết giờ! Bạn đã thua cuộc.", "Game Over", javax.swing.JOptionPane.INFORMATION_MESSAGE);
        this.setEnabled(false);
        LossScreen loss = new LossScreen(this, config);
        loss.setAlwaysOnTop(true);
        loss.setVisible(true);
    }

    public MainScreen(GameConfig config) {
        this.config = config;
        this.gameBoard = new PlayScreen(config);
        this.hintCount = 3;
        this.shuffleCount = 3;
        setContentPane(new BackgroundMain());
        initComponents();

        Timeline.setOpaque(false);
        //Timeline.setUI(new com.mycompany.pikachu_master.User_Interface.Components.CustomTimelineUI(Timeline));//Timeline.setUI(new com.mycompany.pikachu_master.User_Interface.Components.);
        Timeline.setEnabled(false);
        styleTopBar();
        initTimer();
        countdownTimer.start();
        this.getContentPane().add(gameBoard, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 80, 700, 450));
        //this.getContentPane().setLayout(new java.awt.GridBagLayout());
        //this.getContentPane().add(gameBoard);

        //căn chỉnh vị trí.
        this.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent evt) {
                int screenWidth = getContentPane().getWidth();
                int screenHeight = getContentPane().getHeight();

                int boardWidth = 1400;
                int boardHeight = 800;

                int newX = (screenWidth - boardWidth) / 2;

                int newY = ((screenHeight - 80) - boardHeight) / 2 + 80;

                if (newX < 0) {
                    newX = 0;
                }
                if (newY < 80) {
                    newY = 80;
                }

                getContentPane().add(gameBoard, new org.netbeans.lib.awtextra.AbsoluteConstraints(newX, newY, boardWidth, boardHeight));
                int topBarX = (screenWidth - topBarPanel.getWidth()) / 2;
                if (topBarX < 0) {
                    topBarX = 0;
                }
                getContentPane().add(topBarPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(topBarX, 10, topBarPanel.getWidth(), topBarPanel.getHeight()));
                // --------------------------------------------------

                getContentPane().revalidate();
            }
        });

        this.revalidate();
        this.repaint();
    }

    // xử lý resert lại game mới.
    public void resertGame(GameConfig newConfig) {
        if (countdownTimer != null) {
            countdownTimer.stop();
        }

        this.config = newConfig;

        int currentX = gameBoard.getX();
        int currentY = gameBoard.getY();

        //xóa bảng hiện tại.
        this.getContentPane().remove(gameBoard);
        // tạo bảng mới.
        gameBoard = new PlayScreen(config);

        initTimer();
        countdownTimer.start();

        //this.getContentPane().add(gameBoard);
        this.getContentPane().add(gameBoard, new org.netbeans.lib.awtextra.AbsoluteConstraints(currentX, currentY, 700, 450));
        this.revalidate();
        this.repaint();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        topBarPanel = new javax.swing.JPanel();
        coin = new javax.swing.JLabel();
        levelLabel = new javax.swing.JLabel();
        level_point = new javax.swing.JLabel();
        PointLabel = new javax.swing.JLabel();
        point = new javax.swing.JLabel();
        Timeline = new javax.swing.JSlider();
        swapButton = new javax.swing.JButton();
        timeButton = new javax.swing.JButton();
        hintButton = new javax.swing.JButton();
        coinLabel = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        settingmainButton = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        topBarPanel.setOpaque(false);
        topBarPanel.setPreferredSize(new java.awt.Dimension(800, 80));

        coin.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        coin.setText("VÀNG:");

        levelLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        levelLabel.setText("CẤP ĐỘ: ");

        level_point.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        level_point.setText("12");

        PointLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        PointLabel.setText("ĐIỂM:");

        point.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        point.setText("110");

        Timeline.setToolTipText("");
        Timeline.setEnabled(false);
        Timeline.setPreferredSize(new java.awt.Dimension(200, 30));

        swapButton.setFont(new java.awt.Font("Segoe UI Emoji", 0, 10)); // NOI18N
        swapButton.setText("🔁");
        swapButton.setPreferredSize(new java.awt.Dimension(30, 30));
        swapButton.addActionListener(this::swapButtonActionPerformed);

        timeButton.setFont(new java.awt.Font("Segoe UI Emoji", 0, 12)); // NOI18N
        timeButton.setText("kk");
        timeButton.setPreferredSize(new java.awt.Dimension(30, 30));
        timeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                timeButtonMouseClicked(evt);
            }
        });
        timeButton.addActionListener(this::timeButtonActionPerformed);

        hintButton.setFont(new java.awt.Font("Segoe UI Emoji", 0, 16)); // NOI18N
        hintButton.setText("🔍");
        hintButton.setPreferredSize(new java.awt.Dimension(30, 30));
        hintButton.addActionListener(this::hintButtonActionPerformed);

        coinLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        coinLabel.setText("9999");

        settingmainButton.setFont(new java.awt.Font("Segoe UI Emoji", 0, 20)); // NOI18N
        settingmainButton.setText("⚙");
        settingmainButton.setAlignmentY(0.0F);
        settingmainButton.setPreferredSize(new java.awt.Dimension(45, 45));
        settingmainButton.addActionListener(this::settingmainButtonActionPerformed);

        javax.swing.GroupLayout topBarPanelLayout = new javax.swing.GroupLayout(topBarPanel);
        topBarPanel.setLayout(topBarPanelLayout);
        topBarPanelLayout.setHorizontalGroup(
            topBarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(topBarPanelLayout.createSequentialGroup()
                .addComponent(levelLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(level_point, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(PointLabel)
                .addGap(9, 9, 9)
                .addComponent(point)
                .addGap(16, 16, 16)
                .addComponent(coin)
                .addGap(4, 4, 4)
                .addGroup(topBarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(topBarPanelLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(topBarPanelLayout.createSequentialGroup()
                        .addComponent(coinLabel)
                        .addGap(38, 38, 38)
                        .addComponent(Timeline, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(timeButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(swapButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(hintButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(settingmainButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        topBarPanelLayout.setVerticalGroup(
            topBarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(topBarPanelLayout.createSequentialGroup()
                .addGroup(topBarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(topBarPanelLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(levelLabel))
                    .addGroup(topBarPanelLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(level_point))
                    .addGroup(topBarPanelLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(PointLabel))
                    .addGroup(topBarPanelLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(point))
                    .addGroup(topBarPanelLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(coin))
                    .addGroup(topBarPanelLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(topBarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(coinLabel)
                            .addComponent(Timeline, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(topBarPanelLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(topBarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(swapButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(timeButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(hintButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(settingmainButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(37, 37, 37))
        );

        getContentPane().add(topBarPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jSeparator1.setPreferredSize(new java.awt.Dimension(9999, 3));
        getContentPane().add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 77, -1, -1));

        setSize(new java.awt.Dimension(814, 608));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void settingmainButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_settingmainButtonActionPerformed
        // TODO add your handling code here:
        //Pause Setting = new PauseScreen();
        this.stopTimer();
        PauseScreen pause = new PauseScreen(this, config);
        //this.setUndecorated(true);
        pause.setVisible(true);
        countdownTimer.stop();
        // this.dispose();
    }//GEN-LAST:event_settingmainButtonActionPerformed

    private void timeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_timeButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_timeButtonActionPerformed

    private void timeButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_timeButtonMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_timeButtonMouseClicked

    private void swapButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_swapButtonActionPerformed
        // TODO add your handling code here:
        if(this.shuffleCount>=0){
            this.shuffleCount --;
            gameBoard.shuffle();
        }
        else{
//          làm mình làm mẩy ở đây đi :))  
        }
    }//GEN-LAST:event_swapButtonActionPerformed

    private void hintButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hintButtonActionPerformed
        // TODO add your handling code here:
        if(this.hintCount >=0){
            this.hintCount --;
            gameBoard.findHint();
        }
        else{
//            ở đây thì làm trò mèo nhá 🐧
        }
    }//GEN-LAST:event_hintButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
//        GameConfig config = new GameConfig(6, 5, 12);

        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(() -> new MainScreen(config).setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel PointLabel;
    private javax.swing.JSlider Timeline;
    private javax.swing.JLabel coin;
    private javax.swing.JLabel coinLabel;
    private javax.swing.JButton hintButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel levelLabel;
    private javax.swing.JLabel level_point;
    private javax.swing.JLabel point;
    private javax.swing.JButton settingmainButton;
    private javax.swing.JButton swapButton;
    private javax.swing.JButton timeButton;
    private javax.swing.JPanel topBarPanel;
    // End of variables declaration//GEN-END:variables
}
