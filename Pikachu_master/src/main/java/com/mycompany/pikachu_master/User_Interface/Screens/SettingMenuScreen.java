/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.pikachu_master.User_Interface.Screens;

import com.mycompany.pikachu_master.Data.gameDAO;
import com.mycompany.pikachu_master.User_Interface.Components.BackgroundSettingStartScreen;
import com.mycompany.pikachu_master.Utils.Button_Icon;
import com.mycompany.pikachu_master.Utils.ImageLoad;
import com.mycompany.pikachu_master.User_Interface.Components.BackgroundSettingMenuScreen;
import com.mycompany.pikachu_master.Utils.SoundLoad;
import java.awt.geom.RoundRectangle2D;

/**
 *
 * @author laptop
 */
public class SettingMenuScreen extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(SettingMenuScreen.class.getName());
    private SoundLoad audioManager = new SoundLoad();
    /**
     * Creates new form SettingMenuScreen
     */
    StartScreen start;

    public SettingMenuScreen(StartScreen start) {
        this.setUndecorated(true);
        setContentPane(new BackgroundSettingStartScreen());
        initComponents();

        // ---> THÊM ĐOẠN CODE NÀY ĐỂ BO GÓC JFRAME <---
        this.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent evt) {
                // Cắt JFrame thành hình chữ nhật bo góc
                // Tham số 40, 40 là độ cong của góc (bạn có thể tăng giảm tùy ý)
                setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 40, 40));
            }
        });

        // ---> BẮT ĐẦU THÊM MỚI TỪ ĐÂY: VẼ ĐƯỜNG VIỀN MÀU (BORDER) BO TRÒN THEO KHUNG <---
        javax.swing.JPanel contentPane = (javax.swing.JPanel) this.getContentPane();
        contentPane.setBorder(new javax.swing.border.AbstractBorder() {
            @Override
            public void paintBorder(java.awt.Component c, java.awt.Graphics g, int x, int y, int width, int height) {
                java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();
                // Bật khử răng cưa cho viền mượt mà
                g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);

                // Chọn màu viền (Ví dụ: Màu Vàng Gold giống TopBar của bạn)
                g2.setColor(new java.awt.Color(255, 215, 0));
                // Chỉnh độ dày của đường viền (4f là 4 pixel)
                g2.setStroke(new java.awt.BasicStroke(4f));

                // Vẽ viền bo góc 40px (Khớp với thông số 40 của lệnh setShape ở trên)
                // Cộng trừ vài pixel (x+2, y+2, width-4, height-4) để viền không bị lẹm ra ngoài khung
                g2.drawRoundRect(x + 2, y + 2, width - 4, height - 4, 40, 40);
                g2.dispose();
            }
        });
        //this.setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH); 
//         Rút nút Back ra khỏi lưới GridBagLayout
        this.getContentPane().remove(exitButton6);

        // Ném nó lên tầng LayeredPane (tầng cao nhất, cho phép đặt vị trí tự do)
        this.getLayeredPane().add(exitButton6, javax.swing.JLayeredPane.PALETTE_LAYER);

        // Đóng đinh tọa độ tuyệt đối: Cách mép trái 20px, mép trên 20px, kích thước 60x40
        exitButton6.setBounds(10, 10, 50, 30);

        //ImageLoad.loadBg("PAUSE_BTN", 4, 280, 60, 10);
        //ImageLoad.loadBg("MINI_BTN", 4, 50, 30, 10);
        setupAllButtonIcons();
        
        if (SoundLoad.isBgmOn) {
            volumnButton.setSelected(false);
            volumnButton.setText("🎧");
        } else {
            volumnButton.setSelected(true);
            volumnButton.setText("🚫");
        }

        if (SoundLoad.isSfxOn) {
            soundButton.setSelected(false);
            soundButton.setText("🔊");
        } else {
            soundButton.setSelected(true);
            soundButton.setText("🔇");
        }

        // ---> ÉP CỨNG FONT EMOJI SAU KHI GẮN PHÔI <---
        java.awt.Font emojiFont = new java.awt.Font("Segoe UI Emoji", java.awt.Font.PLAIN, 22);
        volumnButton.setFont(emojiFont);
        soundButton.setFont(emojiFont);
      
        java.awt.Color blueColor = new java.awt.Color(255, 255, 255); // Màu xanh dương
        authorButton.setForeground(blueColor);
        canncelButton.setForeground(blueColor);
        soundButton.setForeground(blueColor);
        volumnButton.setForeground(blueColor);
        this.start = start;

        this.darkOverlay = new javax.swing.JWindow(start);
        this.darkOverlay.setBounds(start.getBounds());
        this.darkOverlay.setBackground(new java.awt.Color(0, 0, 0, 180));
        this.darkOverlay.addMouseListener(new java.awt.event.MouseAdapter() {
        });
        this.darkOverlay.setVisible(true);
        this.setAlwaysOnTop(true);
    }

    private void setupAllButtonIcons() {
        java.awt.GridBagLayout layout = (java.awt.GridBagLayout) getContentPane().getLayout();
        
        javax.swing.AbstractButton[] btns = {soundButton, volumnButton, authorButton, canncelButton, exitButton6};

        for (javax.swing.AbstractButton btn : btns) {
            // ---> TRỊ TẬN GỐC CÁI TẬT KÉO DÃN CỦA NETBEANS <---
            java.awt.GridBagConstraints gbc = layout.getConstraints(btn);
            gbc.fill = java.awt.GridBagConstraints.NONE; // Cấm tự kéo dãn
            gbc.ipadx = 0; // Xóa đệm ngang
            gbc.ipady = 0; // Xóa luôn đệm dọc (Lúc nãy sót thằng này)
            layout.setConstraints(btn, gbc);
            
            // ---> ÉP CHUẨN TỶ LỆ 250x40 TUYỆT ĐỐI <---
            if (btn != exitButton6) {
                java.awt.Dimension fixedSize = new java.awt.Dimension(300, 60);
                btn.setPreferredSize(fixedSize);
                btn.setMinimumSize(fixedSize); // Khóa cứng size tối thiểu
                btn.setMaximumSize(fixedSize); // Khóa cứng size tối đa
            }
            
            // Xóa background mặc định của Java Swing
            btn.setContentAreaFilled(false);
            btn.setFocusPainted(false);
            btn.setBorderPainted(false);
            btn.setOpaque(false);
            
            // Màu chữ mặc định lúc bình thường là TRẮNG
            btn.setForeground(new java.awt.Color(255, 255, 255)); 

            // ---> BẮT SỰ KIỆN CHUỘT ĐỂ ĐỔI MÀU CHỮ <---
            btn.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent e) {
                    // Khi chuột lướt vào -> Đổi chữ sang màu xám đen
                    btn.setForeground(new java.awt.Color(40, 40, 40)); 
                }
                @Override
                public void mouseExited(java.awt.event.MouseEvent e) {
                    // Khi chuột rời đi -> Trả lại chữ màu trắng
                    btn.setForeground(new java.awt.Color(255, 255, 255)); 
                }
            });

            // ---> VẼ GIAO DIỆN NỀN BO GÓC <---
            btn.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
                @Override
                public void paint(java.awt.Graphics g, javax.swing.JComponent c) {
                    java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();
                    g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
                    javax.swing.AbstractButton b = (javax.swing.AbstractButton) c;

                    // Xử lý màu nền
                    if (b.getModel().isPressed()) {
                        g2.setColor(new java.awt.Color(200, 200, 200, 255)); // Bấm vào: Nền xám nhạt
                    } else if (b.getModel().isRollover()) {
                        g2.setColor(new java.awt.Color(255, 255, 255, 230)); // Di chuột vào: Nền trắng đặc
                    } else {
                        g2.setColor(new java.awt.Color(0, 0, 0, 150)); // Bình thường: Nền đen mờ
                    }

                    // Vẽ nền bo góc 20px
                    g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 20, 20);

                    // Vẽ viền trắng mỏng bọc ngoài
                    g2.setColor(java.awt.Color.WHITE);
                    g2.setStroke(new java.awt.BasicStroke(1.5f));
                    g2.drawRoundRect(0, 0, c.getWidth() - 1, c.getHeight() - 1, 20, 20);

                    g2.dispose();
                    super.paint(g, c); 
                }
            });
        }

        // Đặt lại chữ
        authorButton.setText("NHÀ SẢN XUẤT");
        canncelButton.setText("XÓA DỮ LIỆU");
        
        // Căn giữa nội dung
        soundButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        volumnButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        // Chỉnh riêng font cho nút Back
        exitButton6.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 18)); 
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

        exitButton6 = new javax.swing.JButton();
        soundButton = new javax.swing.JToggleButton();
        volumnButton = new javax.swing.JToggleButton();
        authorButton = new javax.swing.JButton();
        canncelButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        exitButton6.setText("<");
        exitButton6.setPreferredSize(new java.awt.Dimension(50, 30));
        exitButton6.addActionListener(this::exitButton6ActionPerformed);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 27;
        gridBagConstraints.ipady = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        getContentPane().add(exitButton6, gridBagConstraints);

        soundButton.setFont(new java.awt.Font("Segoe UI Emoji", 0, 18)); // NOI18N
        soundButton.setText("🔊");
        soundButton.setPreferredSize(new java.awt.Dimension(250, 40));
        soundButton.addActionListener(this::soundButtonActionPerformed);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 233;
        gridBagConstraints.ipady = 17;
        gridBagConstraints.insets = new java.awt.Insets(275, 50, 0, 60);
        getContentPane().add(soundButton, gridBagConstraints);

        volumnButton.setFont(new java.awt.Font("Segoe UI Emoji", 0, 18)); // NOI18N
        volumnButton.setText("🎧");
        volumnButton.setAutoscrolls(true);
        volumnButton.setPreferredSize(new java.awt.Dimension(250, 40));
        volumnButton.addActionListener(this::volumnButtonActionPerformed);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 233;
        gridBagConstraints.ipady = 17;
        gridBagConstraints.insets = new java.awt.Insets(6, 50, 0, 60);
        getContentPane().add(volumnButton, gridBagConstraints);

        authorButton.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        authorButton.setText("ĐỘI NGŨ SẢN XUẤT");
        authorButton.setPreferredSize(new java.awt.Dimension(250, 40));
        authorButton.addActionListener(this::authorButtonActionPerformed);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 112;
        gridBagConstraints.ipady = 17;
        gridBagConstraints.insets = new java.awt.Insets(6, 50, 0, 60);
        getContentPane().add(authorButton, gridBagConstraints);

        canncelButton.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        canncelButton.setText("XÓA DỮ LIỆU");
        canncelButton.setPreferredSize(new java.awt.Dimension(250, 40));
        canncelButton.addActionListener(this::canncelButtonActionPerformed);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 137;
        gridBagConstraints.ipady = 13;
        gridBagConstraints.insets = new java.awt.Insets(6, 50, 153, 60);
        getContentPane().add(canncelButton, gridBagConstraints);

        setSize(new java.awt.Dimension(464, 658));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void exitButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitButton6ActionPerformed
        // TODO add your handling code here:
        audioManager.playSoundEffect("/sound/SoundTap/tap.wav");

        start.setVisible(true);
        this.dispose();

    }//GEN-LAST:event_exitButton6ActionPerformed
// này là tiếng thao tác nhé, như kiểu chọn hay ăn á
    private void soundButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_soundButtonActionPerformed
        // TODO add your handling code here:
       if (soundButton.isSelected()) {
            soundButton.setText("🔇");
            SoundLoad.isSfxOn = false; // TẮT
        } else {
            soundButton.setText("🔊");
            SoundLoad.isSfxOn = true;  // BẬT
            audioManager.playSoundEffect("/sound/SoundTap/tap.wav");
        }
    }//GEN-LAST:event_soundButtonActionPerformed

    private void volumnButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_volumnButtonActionPerformed
        // TODO add your handling code here:
        if (volumnButton.isSelected()) {
            volumnButton.setText("🚫");
            SoundLoad.isBgmOn = false; // TẮT
            audioManager.stopBGM();
        } else {
            volumnButton.setText("🎧");
            SoundLoad.isBgmOn = true;  // BẬT
            audioManager.playBGM("/sound/SoundBackground/SoundStart.wav");
        }
    }//GEN-LAST:event_volumnButtonActionPerformed

    private void authorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_authorButtonActionPerformed
        // TODO add your handling code here:
        audioManager.playSoundEffect("/sound/SoundTap/tap.wav");
        AuthorDialog dialog = new AuthorDialog(this, true);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }//GEN-LAST:event_authorButtonActionPerformed

    private void canncelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_canncelButtonActionPerformed
         // TODO add your handling code here:
         new gameDAO().clearAllGameSaves();
        audioManager.playSoundEffect("/sound/SoundTap/tap.wav");
    }//GEN-LAST:event_canncelButtonActionPerformed

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
        //java.awt.EventQueue.invokeLater(() -> new SettingMenuScreen().setVisible(true));
    }

    private javax.swing.JWindow darkOverlay;

    @Override
    public void dispose() {
        if (darkOverlay != null) {
            darkOverlay.dispose();
        }
        super.dispose();
    }

    @Override
    public void setVisible(boolean b) {
        super.setVisible(b);
        if (!b && darkOverlay != null) {
            darkOverlay.setVisible(false);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton authorButton;
    private javax.swing.JButton canncelButton;
    private javax.swing.JButton exitButton6;
    private javax.swing.JToggleButton soundButton;
    private javax.swing.JToggleButton volumnButton;
    // End of variables declaration//GEN-END:variables
}
