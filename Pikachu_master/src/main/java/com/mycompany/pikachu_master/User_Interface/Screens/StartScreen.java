/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.pikachu_master.User_Interface.Screens;

import com.mycompany.pikachu_master.Controller.GameConfig;
import com.mycompany.pikachu_master.Model.LevelType;
import com.mycompany.pikachu_master.User_Interface.Components.BackgroundMain;
//import com.mycompany.pikachu_master.User_Interface.Components.BackgroundMain;
import com.mycompany.pikachu_master.User_Interface.Components.BackgroundStartScreen;
import com.mycompany.pikachu_master.Utils.ImageLoad;
import com.mycompany.pikachu_master.Utils.Button_Icon;

/**
 *
 * @author laptop
 */
public class StartScreen extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(StartScreen.class.getName());

    /**
     * Creates new form StartScreen
     */
    private GameConfig config;
    private LevelType level;
    public StartScreen(GameConfig config,LevelType level) {
        //this.setUndecorated(true); // Xóa thanh tiêu đề (chơi tràn viền)
        this.setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        this.config = config;
        this.level = level;
        setContentPane(new BackgroundStartScreen());
        ImageLoad.loadAllImagesPika(); // tải ảnh trước khi bắt đầu trò chơi
        ImageLoad.loadBackgroundButton();
        initComponents();
        this.setMinimumSize(new java.awt.Dimension(800, 600));
        setupAllButtonIcons();

    }
    
    public void setLevel(String Level){
        levelButton.setText("CẤP ĐỘ: " + Level);
    }
    
       
    public void UpdateLevel(String Level){
        this.config = new GameConfig(Level);
        this.level = LevelType.getByName(Level);
      setupAllButtonIcons();
    }
    
    private void setupAllButtonIcons() {
        // Giả sử bạn có 1 cái khung tên là "KhungGo.png" (hoặc cứ dùng lại ảnh cũ tạm cũng được)
        String frameImage = "/images/Picture_button/Back_Ground.png"; 
        //this.setLayout(new java.awt.GridBagLayout()); 
        //this.add(myButton, new java.awt.GridBagConstraints());
        // Gọi hàm: truyền nút, truyền ảnh khung, và TRUYỀN CHỮ
        Button_Icon.setupImageButton(playButton, frameImage, "CHƠI NGAY");
        Button_Icon.setupImageButton(levelButton, frameImage, "CẤP ĐỘ");
        Button_Icon.setupImageButton(maxButton, frameImage, "THÀNH TÍCH CAO NHẤT");
        Button_Icon.setupImageButton(settingButton, frameImage, "CÀI ĐẶT");
        Button_Icon.setupImageButton(instructionutton, frameImage, "HƯỚNG DẪN");
        Button_Icon.setupImageButton(exitButton, frameImage, "THOÁT");
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        playButton = new javax.swing.JButton();
        levelButton = new javax.swing.JButton();
        maxButton = new javax.swing.JButton();
        settingButton = new javax.swing.JButton();
        instructionutton = new javax.swing.JButton();
        exitButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        playButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        playButton.setMaximumSize(new java.awt.Dimension(220, 30));
        playButton.setMinimumSize(new java.awt.Dimension(220, 30));
        playButton.setOpaque(true);
        playButton.setPreferredSize(new java.awt.Dimension(220, 30));
        playButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                playButtonMouseEntered(evt);
            }
        });
        playButton.addActionListener(this::playButtonActionPerformed);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 424;
        gridBagConstraints.ipady = 6;
        gridBagConstraints.insets = new java.awt.Insets(100, 80, 0, 80);
        getContentPane().add(playButton, gridBagConstraints);

        levelButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        levelButton.setMaximumSize(new java.awt.Dimension(220, 30));
        levelButton.setMinimumSize(new java.awt.Dimension(220, 30));
        levelButton.setOpaque(true);
        levelButton.setPreferredSize(new java.awt.Dimension(220, 30));
        levelButton.addActionListener(this::levelButtonActionPerformed);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 424;
        gridBagConstraints.ipady = 7;
        gridBagConstraints.insets = new java.awt.Insets(14, 80, 0, 80);
        getContentPane().add(levelButton, gridBagConstraints);

        maxButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        maxButton.setMaximumSize(new java.awt.Dimension(220, 30));
        maxButton.setMinimumSize(new java.awt.Dimension(220, 30));
        maxButton.setOpaque(true);
        maxButton.setPreferredSize(new java.awt.Dimension(220, 30));
        maxButton.addActionListener(this::maxButtonActionPerformed);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 424;
        gridBagConstraints.ipady = 6;
        gridBagConstraints.insets = new java.awt.Insets(14, 80, 0, 80);
        getContentPane().add(maxButton, gridBagConstraints);

        settingButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        settingButton.setMaximumSize(new java.awt.Dimension(220, 30));
        settingButton.setMinimumSize(new java.awt.Dimension(220, 30));
        settingButton.setOpaque(true);
        settingButton.setPreferredSize(new java.awt.Dimension(220, 30));
        settingButton.addActionListener(this::settingButtonActionPerformed);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 424;
        gridBagConstraints.ipady = 6;
        gridBagConstraints.insets = new java.awt.Insets(14, 80, 0, 80);
        getContentPane().add(settingButton, gridBagConstraints);

        instructionutton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        instructionutton.setMaximumSize(new java.awt.Dimension(220, 30));
        instructionutton.setMinimumSize(new java.awt.Dimension(220, 30));
        instructionutton.setOpaque(true);
        instructionutton.setPreferredSize(new java.awt.Dimension(220, 30));
        instructionutton.addActionListener(this::instructionuttonActionPerformed);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 424;
        gridBagConstraints.ipady = 6;
        gridBagConstraints.insets = new java.awt.Insets(14, 80, 0, 80);
        getContentPane().add(instructionutton, gridBagConstraints);

        exitButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        exitButton.setMaximumSize(new java.awt.Dimension(220, 30));
        exitButton.setMinimumSize(new java.awt.Dimension(220, 30));
        exitButton.setOpaque(true);
        exitButton.setPreferredSize(new java.awt.Dimension(220, 30));
        exitButton.addActionListener(this::exitButtonActionPerformed);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 424;
        gridBagConstraints.ipady = 6;
        gridBagConstraints.insets = new java.awt.Insets(14, 80, 44, 80);
        getContentPane().add(exitButton, gridBagConstraints);

        setSize(new java.awt.Dimension(814, 608));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void playButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_playButtonActionPerformed

        MainScreen Main = new MainScreen(config, this.level);
        Main.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_playButtonActionPerformed

    private void maxButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_maxButtonActionPerformed
        // TODO add your handling code here:
        HighScoreScreen Max = new HighScoreScreen(this);
        Max.setVisible(true);
    }//GEN-LAST:event_maxButtonActionPerformed

    private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitButtonActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_exitButtonActionPerformed

    private void settingButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_settingButtonActionPerformed
        // TODO add your handling code here:
        SettingMenuScreen set = new SettingMenuScreen(this);
        set.setVisible(true);
        //this.setVisible(false);
    }//GEN-LAST:event_settingButtonActionPerformed

    private void instructionuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_instructionuttonActionPerformed
        // TODO add your handling code here:
        HelpScreen Instruct = new HelpScreen(this);
        Instruct.setVisible(true);
        //this.setVisible(false);
    }//GEN-LAST:event_instructionuttonActionPerformed

    private void levelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_levelButtonActionPerformed
        // TODO add your handling code here:
        LevelScreen lev = new LevelScreen(this);
        lev.setVisible(true);
        //this.setVisible(false);
    }//GEN-LAST:event_levelButtonActionPerformed

    private void playButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_playButtonMouseEntered
        // TODO add your handling code here:
        
    }//GEN-LAST:event_playButtonMouseEntered

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
        GameConfig config = new GameConfig("Start");
        LevelType level = LevelType.START;
    /* Create and display the form */
    java.awt.EventQueue.invokeLater (() -> new StartScreen(config, level).setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton exitButton;
    private javax.swing.JButton instructionutton;
    private javax.swing.JButton levelButton;
    private javax.swing.JButton maxButton;
    private javax.swing.JButton playButton;
    private javax.swing.JButton settingButton;
    // End of variables declaration//GEN-END:variables
}
