/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.pikachu_master.User_Interface.Screens;

import com.mycompany.pikachu_master.Controller.GameConfig;
import com.mycompany.pikachu_master.Controller.PlayScreen;
import com.mycompany.pikachu_master.Effect.PathOverlay;
import com.mycompany.pikachu_master.Model.LevelType;
import com.mycompany.pikachu_master.User_Interface.Components.BackgroundMain;
//import com.mycompany.pikachu_master.User_Interface.Components.cus;
import com.mycompany.pikachu_master.User_Interface.Components.BackgroundStartScreen;
import com.mycompany.pikachu_master.Utils.SoundLoad;
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
    PlayScreen play;
    LevelType level;

    int hintCount;
    int shuffleCount;
    
    private SoundLoad audioManager = new SoundLoad();

    // ---> THÊM HÀM NÀY VÀO ĐỂ TRANG TRÍ THANH TOP BAR <---
    private void styleTopBar() {
        // 1. Chỉnh màu chữ: Tiêu đề màu Trắng, Chỉ số màu Vàng Gold
        java.awt.Color textColor = java.awt.Color.WHITE;
        java.awt.Color valueColor = new java.awt.Color(255, 215, 0); // Vàng Gold

        levelLabel.setForeground(textColor);
        PointLabel.setForeground(textColor);
        coin.setForeground(textColor);

        //level_point.setForeground(valueColor);
        //point.setForeground(valueColor);
        //coinLabel.setForeground(valueColor);

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


        // 4. Ẩn cái đường kẻ ngang mặc định đi cho đỡ chướng mắt
        jSeparator1.setVisible(false);

        // 5. "Độ" lại giao diện cho cái JSlider (Thanh thời gian)
        Timeline.setUI(new javax.swing.plaf.basic.BasicSliderUI(Timeline) {
            @Override
            public void paintTrack(java.awt.Graphics g) {
                java.awt.Graphics2D g2d = (java.awt.Graphics2D) g;
                g2d.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);

                java.awt.Rectangle t = trackRect;

                // Vẽ cái nền của thanh thời gian (Màu xám đen)
                g2d.setColor(new java.awt.Color(50, 50, 50, 200));
                int trackHeight = 16; // Độ dày của thanh
                g2d.fillRoundRect(t.x, t.y + (t.height - trackHeight) / 2, t.width, trackHeight, trackHeight, trackHeight);

                // Tính toán độ dài thời gian còn lại
                int fillWidth = 0;
                if (Timeline.getMaximum() > 0) {
                    fillWidth = (int) (t.width * ((double) Timeline.getValue() / Timeline.getMaximum()));
                }

                // Đổi màu cảnh báo nếu sắp hết giờ (dưới 20%)
                if (((double) Timeline.getValue() / Timeline.getMaximum()) < 0.2) {
                    g2d.setColor(new java.awt.Color(255, 50, 50)); // Đỏ lè
                } else {
                    g2d.setColor(new java.awt.Color(0, 200, 100)); // Xanh lá cây mát mắt
                }

                // Vẽ phần thời gian còn lại
                g2d.fillRoundRect(t.x, t.y + (t.height - trackHeight) / 2, fillWidth, trackHeight, trackHeight, trackHeight);
            }

            @Override
            public void paintThumb(java.awt.Graphics g) {
                // Giấu luôn cái cục kéo tròn tròn đi, vì đây là thanh thời gian chứ không phải chỗ chỉnh âm lượng
            }
        });

    }
    // ---> KẾT THÚC HÀM TRANG TRÍ <---
//hàm hiện thị thua cuộc
    private void handleGameOver() {
        //javax.swing.JOptionPane.showMessageDialog(this, "Hết giờ! Bạn đã thua cuộc.", "Game Over", javax.swing.JOptionPane.INFORMATION_MESSAGE);
       audioManager.stopBGM();
        this.setEnabled(false);
        LossScreen loss = new LossScreen(this, config, level);
        loss.setAlwaysOnTop(true);
        loss.setVisible(true);
    }

    public MainScreen(GameConfig config, LevelType level) {
        initComponents();
        this.setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        this.config = config;
        this.level = level;
        this.play = new PlayScreen(config);
        this.hintCount = 3;
        this.shuffleCount = 3;
        setContentPane(new BackgroundMain());
        initComponents();
        this.setMinimumSize(new java.awt.Dimension(800, 600));
        play.initTimer(this.Timeline);
        
        // ---> THÊM 3 DÒNG NÀY ĐỂ KÍCH HOẠT LỚP VẼ ĐƯỜNG ĐI <---
        PathOverlay pathOverlay = new PathOverlay();
        this.setGlassPane(pathOverlay);
        pathOverlay.setVisible(true);
        
        Timeline.setOpaque(false);
        Timeline.setEnabled(false);
        styleTopBar();
        this.getContentPane().setLayout(null);
        this.getContentPane().add(play);
        
        playMusicByLevel();
        
        //căn chỉnh vị trí.
        // Căn chỉnh vị trí và kích thước linh động cho bảng Pokemon
        // Căn chỉnh vị trí và Kích thước (Scale) động cho TOÀN BỘ màn hình
        this.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent evt) {
                updateGameLayout(level);
            }
        });
        
        javax.swing.SwingUtilities.invokeLater(()->{
            updateGameLayout(level);
        });
    }
    
    private void playMusicByLevel() {
        // Lấy tên của Level hiện tại (AFRICA, ASIAN, EUROPE...)
        String levelName = config.GetLevel(); 
        
        if (levelName.equalsIgnoreCase("AFRICA")) {
            audioManager.playBGM("/images/Sound/SoundAfrica_Europe.wav");
        } else if (levelName.equalsIgnoreCase("ASIAN")) {
            audioManager.playBGM("/images/Sound/SoundAsian.wav");
        } else if (levelName.equalsIgnoreCase("EUROPE")) {
            audioManager.playBGM("/images/Sound/SoundAfrica_Europe.wav");
        } else {
            // Nhạc mặc định nếu người chơi không chọn màn mà bấm Play ngay từ đầu
            audioManager.playBGM("/images/Sound/SoundAfrica_Europe.wav");
        }
    }
    
    // Thêm hàm này vào MainScreen.java
    public void playSoundEffect(String path) {
        if (audioManager != null) {
            audioManager.playSoundEffect(path);
        }
    }

    // Hàm dùng chung để tự động tính toán và phóng to/thu nhỏ mọi thứ
    public void updateGameLayout(LevelType level) {
        int screenWidth = getContentPane().getWidth();
        int screenHeight = getContentPane().getHeight();

        if (screenWidth == 0 || screenHeight == 0) {
            return; // Tránh lỗi chia 0 lúc mới khởi tạo
        }
        double scaleRatio = (double) screenWidth / 800.0;
        if (scaleRatio < 1.0) {
            scaleRatio = 1.0;
        }

        int newTopBarHeight = (int) (80 * scaleRatio);

        int newFontSize = (int) (14 * scaleRatio);
        java.awt.Font newFont = new java.awt.Font("Segoe UI", java.awt.Font.BOLD, newFontSize);
        levelLabel.setFont(newFont);
        //level_point.setFont(newFont);
        PointLabel.setFont(newFont);
       // point.setFont(newFont);
        coin.setFont(newFont);
        //coinLabel.setFont(newFont);

        int newTimelineWidth = (int) (240 * scaleRatio);
        int newTimelineHeight = (int) (30 * scaleRatio);
        Timeline.setPreferredSize(new java.awt.Dimension(newTimelineWidth, newTimelineHeight));

        int newBtnSize = (int) (30 * scaleRatio);
        java.awt.Dimension newBtnDim = new java.awt.Dimension(newBtnSize, newBtnSize);
        swapButton.setPreferredSize(newBtnDim);
        timeButton.setPreferredSize(newBtnDim);
        hintButton.setPreferredSize(newBtnDim);

        int newSettingSize = (int) (45 * scaleRatio);
        settingmainButton.setPreferredSize(new java.awt.Dimension(newSettingSize, newSettingSize));

        swapButton.setFont(swapButton.getFont().deriveFont((float) (10f * scaleRatio)));
        timeButton.setFont(timeButton.getFont().deriveFont((float) (12f * scaleRatio)));
        hintButton.setFont(hintButton.getFont().deriveFont((float) (16f * scaleRatio)));
        settingmainButton.setFont(settingmainButton.getFont().deriveFont((float) (20f * scaleRatio)));

        int topBarWidth = (int) (800 * scaleRatio);
        if (topBarWidth > screenWidth) {
            topBarWidth = screenWidth;
        }
        int topBarX = (screenWidth - topBarWidth) / 2;
        //getContentPane().add(topBarPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(topBarX, 10, topBarWidth, newTopBarHeight));
        topBarPanel.setBounds(topBarX, 10, topBarWidth, newTopBarHeight);
        
        // ==========================================
        // PHẦN 2: SCALE BẢNG POKEMON (GAME BOARD)
        // ==========================================
        int padding = (int) (40 * scaleRatio); // Lề

        // Không gian khả dụng tối đa cho play (Cái hộp chứa)
        int maxBoardWidth = screenWidth - (padding * 2);
        int maxBoardHeight = screenHeight - newTopBarHeight - (padding * 2);

        int numRows = level.getRows();
        int numCols = level.getCols();

        // Tính toán kích thước lớn nhất có thể của MỘT ô Pikachu vuông
        // Nó phải vừa với cả chiều rộng tối đa chia cho số cột VÀ chiều cao tối đa chia cho số hàng.
        int maxTileSize_H = maxBoardWidth / numCols;
        int maxTileSize_V = maxBoardHeight / numRows;

        // Kích thước ô cuối cùng là giá trị nhỏ nhất trong 2 giá trị trên, để luôn đảm bảo là hình vuông
        int finalTileSize = Math.min(maxTileSize_H, maxTileSize_V);

        // Cập nhật kích thước thực tế của play theo tỷ lệ đan khít
        // Bảng không được phình to quá mức nữa, để không bị méo.
        int boardWidth = finalTileSize * numCols;
        int boardHeight = finalTileSize * numRows;

        // Đặt giới hạn tối thiểu (giữ nguyên logic cũ)
        if (boardWidth < 400) {
            boardWidth = 400;
        }
        if (boardHeight < 300) {
            boardHeight = 300;
        }

        // Tọa độ luôn nằm chính giữa khoảng trống còn lại
        int newX = (screenWidth - boardWidth) / 2;
        int newY = newTopBarHeight + (screenHeight - newTopBarHeight - boardHeight) / 2;

        //getContentPane().add(play, new org.netbeans.lib.awtextra.AbsoluteConstraints(newX, newY, boardWidth, boardHeight));
        play.setBounds(newX, newY, boardWidth, boardHeight);
        
        topBarPanel.revalidate();
        topBarPanel.repaint();
        getContentPane().revalidate();
        getContentPane().repaint();
    }

    // xử lý resert lại game mới.
    public void resertGame(GameConfig newConfig, LevelType level) {

        this.config = newConfig;
        this.hintCount = 3;
        this.shuffleCount = 3;

        //xóa bảng hiện tại.
        if(play != null){
        this.getContentPane().remove(play);
        }
        // tạo bảng mới.
        play = new PlayScreen(config);
        this.getContentPane().add(play);
        play.initTimer(Timeline);

        updateGameLayout(level);
    }
    
    // Thêm hàm này vào MainScreen.java
    public void stopMusic() {
        if (audioManager != null) {
            audioManager.stopBGM();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        topBarPanel = new javax.swing.JPanel();
        coin = new javax.swing.JLabel();
        PointLabel = new javax.swing.JLabel();
        levelLabel = new javax.swing.JLabel();
        Timeline = new javax.swing.JSlider();
        settingmainButton = new javax.swing.JButton();
        hintButton = new javax.swing.JButton();
        swapButton = new javax.swing.JButton();
        timeButton = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(800, 600));

        topBarPanel.setOpaque(false);
        topBarPanel.setPreferredSize(new java.awt.Dimension(800, 80));
        topBarPanel.setLayout(new java.awt.GridBagLayout());

        coin.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        coin.setText("VÀNG:");
        coin.setPreferredSize(new java.awt.Dimension(100, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 48;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 24, 0, 0);
        topBarPanel.add(coin, gridBagConstraints);

        PointLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        PointLabel.setText("ĐIỂM:");
        PointLabel.setPreferredSize(new java.awt.Dimension(100, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 50;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(8, 24, 0, 0);
        topBarPanel.add(PointLabel, gridBagConstraints);

        levelLabel.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        levelLabel.setForeground(new java.awt.Color(255, 0, 51));
        levelLabel.setText("CẤP ĐỘ: ");
        levelLabel.setPreferredSize(new java.awt.Dimension(250, 30));
        levelLabel.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                levelLabelAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 147;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 191, 0, 0);
        topBarPanel.add(levelLabel, gridBagConstraints);

        Timeline.setToolTipText("");
        Timeline.setEnabled(false);
        Timeline.setPreferredSize(new java.awt.Dimension(300, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 264;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 153, 0, 0);
        topBarPanel.add(Timeline, gridBagConstraints);

        settingmainButton.setFont(new java.awt.Font("Segoe UI Emoji", 0, 20)); // NOI18N
        settingmainButton.setText("⚙");
        settingmainButton.setAlignmentY(0.0F);
        settingmainButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        settingmainButton.setPreferredSize(new java.awt.Dimension(45, 45));
        settingmainButton.addActionListener(this::settingmainButtonActionPerformed);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 7;
        gridBagConstraints.ipady = 11;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        topBarPanel.add(settingmainButton, gridBagConstraints);

        hintButton.setFont(new java.awt.Font("Segoe UI Emoji", 0, 16)); // NOI18N
        hintButton.setText("🔍");
        hintButton.setPreferredSize(new java.awt.Dimension(30, 30));
        hintButton.addActionListener(this::hintButtonActionPerformed);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 2;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        topBarPanel.add(hintButton, gridBagConstraints);

        swapButton.setText("/");
        swapButton.setPreferredSize(new java.awt.Dimension(30, 30));
        swapButton.addActionListener(this::swapButtonActionPerformed);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 7;
        gridBagConstraints.ipady = 7;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        topBarPanel.add(swapButton, gridBagConstraints);

        timeButton.setFont(new java.awt.Font("Segoe UI Emoji", 0, 12)); // NOI18N
        timeButton.setText("t");
        timeButton.setToolTipText("");
        timeButton.setPreferredSize(new java.awt.Dimension(30, 30));
        timeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                timeButtonMouseClicked(evt);
            }
        });
        timeButton.addActionListener(this::timeButtonActionPerformed);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 9;
        gridBagConstraints.ipady = 9;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        topBarPanel.add(timeButton, gridBagConstraints);

        jSeparator1.setPreferredSize(new java.awt.Dimension(9999, 3));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(topBarPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(topBarPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(77, 77, 77)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(520, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(814, 608));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void settingmainButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_settingmainButtonActionPerformed
        // TODO add your handling code here:
        //Pause Setting = new PauseScreen();
        play.stopTimer();
        PauseScreen pause = new PauseScreen(this, config, level, play);
        pause.setVisible(true);
        play.countdownTimer.stop();
        // this.dispose();
    }//GEN-LAST:event_settingmainButtonActionPerformed

    private void timeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_timeButtonActionPerformed
//        // TODO add your handling code here:
        play.addTimer(15);
        System.out.println("cong them 15 giay");
    }//GEN-LAST:event_timeButtonActionPerformed

    private void timeButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_timeButtonMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_timeButtonMouseClicked

    private void hintButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hintButtonActionPerformed
        // TODO add your handling code here:
        if (this.hintCount >= 0) {
            this.hintCount--;
            play.findHint();
        } else {
//            ở đây thì làm trò mèo nhá 🐧
        }
    }//GEN-LAST:event_hintButtonActionPerformed

    private void swapButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_swapButtonActionPerformed
        // TODO add your handling code here:
        
        if (this.shuffleCount >= 0) {
            this.shuffleCount--;
            play.shuffle();
        } else {
//          làm mình làm mẩy ở đây đi :))  
        }
    }//GEN-LAST:event_swapButtonActionPerformed

    private void levelLabelAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_levelLabelAncestorAdded
        // TODO add your handling code here:
        levelLabel.setText("CẤP ĐỘ: " + config.GetLevel());
    }//GEN-LAST:event_levelLabelAncestorAdded

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
    private javax.swing.JButton hintButton;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel levelLabel;
    private javax.swing.JButton settingmainButton;
    private javax.swing.JButton swapButton;
    private javax.swing.JButton timeButton;
    private javax.swing.JPanel topBarPanel;
    // End of variables declaration//GEN-END:variables

    void playBGM() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
