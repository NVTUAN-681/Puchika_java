/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.pikachu_master.User_Interface.Screens;

import com.mycompany.pikachu_master.Utils.SoundLoad;
import com.mycompany.pikachu_master.Controller.PlayScreen;
import com.mycompany.pikachu_master.Data.gameDAO;
import com.mycompany.pikachu_master.Model.LevelType;
import java.awt.event.WindowEvent;
import java.awt.geom.RoundRectangle2D;

/**
 *
 * @author laptop
 */
public class HighScoreScreen extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(HighScoreScreen.class.getName());

    /**
     * Creates new form HighScoreScreen
     */
    StartScreen start;
    private SoundLoad audioManager = new SoundLoad();
    PlayScreen play;
    MainScreen main;
    LevelType level;
    gameDAO DTB = new gameDAO();

    public HighScoreScreen(StartScreen start, PlayScreen play, LevelType level) {
        this.setUndecorated(true);
        this.play = play;
        this.level = level;
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
        //this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
       // this.setMinimumSize(new java.awt.Dimension(300, 400));
        this.start = start;
        
        this.darkOverlay = new javax.swing.JWindow(start);
        this.darkOverlay.setBounds(start.getBounds()); 
        this.darkOverlay.setBackground(new java.awt.Color(0, 0, 0, 180)); 
        this.darkOverlay.addMouseListener(new java.awt.event.MouseAdapter() {}); 
        this.darkOverlay.setVisible(true); 
        this.setAlwaysOnTop(true);
        
         // --- BẮT ĐẦU: ĐỘ NÚT < THÀNH BO GÓC TRONG SUỐT ---
        exitButton5.setContentAreaFilled(false);
        exitButton5.setFocusPainted(false);
        exitButton5.setBorderPainted(false);
        exitButton5.setOpaque(false);
        exitButton5.setRolloverEnabled(true); // Bật nhận diện lướt chuột
        exitButton5.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 18)); 

        exitButton5.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
            @Override
            public void paint(java.awt.Graphics g, javax.swing.JComponent c) {
                java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();
                g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
                javax.swing.AbstractButton b = (javax.swing.AbstractButton) c;
                
                if (b.getModel().isPressed()) {
                    g2.setColor(new java.awt.Color(200, 200, 200, 255)); 
                } else if (b.getModel().isRollover()) {
                    g2.setColor(new java.awt.Color(255, 255, 255, 230)); // Nền trắng đặc để nổi chữ
                } else {
                    g2.setColor(new java.awt.Color(0, 0, 0, 150)); 
                }
                
                g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 20, 20);
                g2.setColor(java.awt.Color.WHITE);
                g2.setStroke(new java.awt.BasicStroke(1.5f));
                g2.drawRoundRect(0, 0, c.getWidth() - 1, c.getHeight() - 1, 20, 20);
                g2.dispose();
                super.paint(g, c); 
            }

            @Override
            protected void paintText(java.awt.Graphics g, javax.swing.JComponent c, java.awt.Rectangle textRect, String text) {
                javax.swing.AbstractButton b = (javax.swing.AbstractButton) c;
                if (b.getModel().isRollover() || b.getModel().isPressed()) {
                    g.setColor(new java.awt.Color(40, 40, 40)); 
                } else {
                    g.setColor(java.awt.Color.WHITE); 
                }
                java.awt.FontMetrics fm = g.getFontMetrics(c.getFont());
                int mnemonicIndex = b.getDisplayedMnemonicIndex();
                javax.swing.plaf.basic.BasicGraphicsUtils.drawStringUnderlineCharAt(g, text, mnemonicIndex, textRect.x, textRect.y + fm.getAscent());
            }
        });
        // --- KẾT THÚC ĐỘ NÚT ---
    }
    
    public void showHighScore(){
        HighScoreLabel.setText(String.valueOf(DTB.getHighScoreByLevel(this.level.getLevel())));
        System.out.println(this.level.getLevel());
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

        HighScoreLabel = new javax.swing.JLabel();
        exitButton5 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(300, 400));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        HighScoreLabel.setFont(new java.awt.Font("Segoe UI", 3, 48)); // NOI18N
        HighScoreLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        HighScoreLabel.setText("9999");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 28;
        gridBagConstraints.ipady = 46;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(214, 89, 290, 165);
        getContentPane().add(HighScoreLabel, gridBagConstraints);

        exitButton5.setText("<");
        exitButton5.setPreferredSize(new java.awt.Dimension(50, 30));
        exitButton5.addActionListener(this::exitButton5ActionPerformed);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 27;
        gridBagConstraints.ipady = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 0);
        getContentPane().add(exitButton5, gridBagConstraints);

        setSize(new java.awt.Dimension(464, 658));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void exitButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitButton5ActionPerformed
        // TODO add your handling code here:
        audioManager.playSoundEffect("/sound/SoundTap/tap.wav");
        start.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_exitButton5ActionPerformed

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
        //java.awt.EventQueue.invokeLater(() -> new HighScoreScreen().setVisible(true));
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
    private javax.swing.JLabel HighScoreLabel;
    private javax.swing.JButton exitButton5;
    // End of variables declaration//GEN-END:variables
}
