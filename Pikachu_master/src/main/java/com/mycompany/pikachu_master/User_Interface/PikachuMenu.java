/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.pikachu_master.User_Interface;

/**
 *
 * @author laptop
 */
public class PikachuMenu extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(PikachuMenu.class.getName());

    /**
     * Creates new form PikachuMenu
     */
    public PikachuMenu() {

        setContentPane(new backGround_Menu());
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        maxButton = new javax.swing.JButton();
        settingButton = new javax.swing.JButton();
        playButton = new javax.swing.JButton();
        instructionutton = new javax.swing.JButton();
        exitButton = new javax.swing.JButton();
        levelButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(800, 600));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        maxButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        maxButton.setText("THÀNH TÍCH CAO NHẤT");
        maxButton.setPreferredSize(new java.awt.Dimension(220, 30));
        maxButton.addActionListener(this::maxButtonActionPerformed);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 296;
        gridBagConstraints.ipady = 6;
        gridBagConstraints.insets = new java.awt.Insets(6, 152, 0, 152);
        getContentPane().add(maxButton, gridBagConstraints);

        settingButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        settingButton.setText("CÀI ĐẶT");
        settingButton.setPreferredSize(new java.awt.Dimension(220, 30));
        settingButton.addActionListener(this::settingButtonActionPerformed);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 412;
        gridBagConstraints.ipady = 6;
        gridBagConstraints.insets = new java.awt.Insets(6, 152, 0, 152);
        getContentPane().add(settingButton, gridBagConstraints);

        playButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        playButton.setText("CHƠI");
        playButton.setPreferredSize(new java.awt.Dimension(220, 30));
        playButton.addActionListener(this::playButtonActionPerformed);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 424;
        gridBagConstraints.ipady = 6;
        gridBagConstraints.insets = new java.awt.Insets(206, 152, 0, 152);
        getContentPane().add(playButton, gridBagConstraints);

        instructionutton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        instructionutton.setText("HƯỚNG DẪN CHƠI");
        instructionutton.setPreferredSize(new java.awt.Dimension(220, 30));
        instructionutton.addActionListener(this::instructionuttonActionPerformed);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 335;
        gridBagConstraints.ipady = 6;
        gridBagConstraints.insets = new java.awt.Insets(6, 152, 0, 152);
        getContentPane().add(instructionutton, gridBagConstraints);

        exitButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        exitButton.setText("THOÁT");
        exitButton.setPreferredSize(new java.awt.Dimension(220, 30));
        exitButton.addActionListener(this::exitButtonActionPerformed);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 416;
        gridBagConstraints.ipady = 6;
        gridBagConstraints.insets = new java.awt.Insets(6, 152, 153, 152);
        getContentPane().add(exitButton, gridBagConstraints);

        levelButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        levelButton.setText("CẤP ĐỘ");
        levelButton.setPreferredSize(new java.awt.Dimension(220, 30));
        levelButton.addActionListener(this::levelButtonActionPerformed);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 421;
        gridBagConstraints.ipady = 7;
        gridBagConstraints.insets = new java.awt.Insets(6, 152, 0, 152);
        getContentPane().add(levelButton, gridBagConstraints);

        setBounds(0, 0, 814, 608);
    }// </editor-fold>//GEN-END:initComponents

    private void playButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_playButtonActionPerformed
        main Main = new main();
       Main.setVisible(true);
       this.dispose();
    }//GEN-LAST:event_playButtonActionPerformed

    private void maxButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_maxButtonActionPerformed
        // TODO add your handling code here:
        max Max = new max();
        Max.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_maxButtonActionPerformed

    private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitButtonActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_exitButtonActionPerformed

    private void settingButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_settingButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_settingButtonActionPerformed

    private void instructionuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_instructionuttonActionPerformed
        // TODO add your handling code here:
        instruction Instruct = new instruction();
        Instruct.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_instructionuttonActionPerformed

    private void levelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_levelButtonActionPerformed
        // TODO add your handling code here:
        Level lev = new Level();
        lev.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_levelButtonActionPerformed

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

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new PikachuMenu().setVisible(true));
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
