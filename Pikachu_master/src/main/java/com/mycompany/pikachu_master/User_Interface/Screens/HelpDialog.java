/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.mycompany.pikachu_master.User_Interface.Screens;

import com.mycompany.pikachu_master.User_Interface.Components.BackgroundHelp;
import com.mycompany.pikachu_master.User_Interface.Components.BackgroundPause;
import com.mycompany.pikachu_master.Utils.SoundLoad;
import java.awt.geom.RoundRectangle2D;

/**
 *
 * @author laptop
 */
public class HelpDialog extends javax.swing.JDialog {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(HelpDialog.class.getName());

    /**
     * Creates new form HelpDialog
     */
    StartScreen start;
    private SoundLoad audioManager = new SoundLoad();

    public HelpDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        this.setUndecorated(true);
        setContentPane(new BackgroundPause());
        initComponents();
        jScrollPane2.setOpaque(false);
        jScrollPane2.setBackground(new java.awt.Color(0, 0, 0, 0));
        jScrollPane2.getViewport().setOpaque(false);
        jScrollPane2.getViewport().setBackground(new java.awt.Color(0, 0, 0, 0));
        jTextPane1.setOpaque(false);
        jTextPane1.setBackground(new java.awt.Color(0, 0, 0, 0));
        jScrollPane2.setBorder(new javax.swing.border.AbstractBorder() {
            @Override
            public void paintBorder(java.awt.Component c, java.awt.Graphics g, int x, int y, int width, int height) {
                java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();
                g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);

                // Vẽ nền đen mờ (giống hệt nút bấm)
                g2.setColor(new java.awt.Color(0, 0, 0, 150));
                g2.fillRoundRect(x, y, width - 1, height - 1, 20, 20);

                // Vẽ viền trắng mỏng
                g2.setColor(java.awt.Color.WHITE);
                g2.setStroke(new java.awt.BasicStroke(1.5f));
                g2.drawRoundRect(x, y, width - 1, height - 1, 20, 20);

                g2.dispose();
            }

            // Ép lề (Padding) 20px thụt vào trong để chữ không bị dính sát mép viền
            @Override
            public java.awt.Insets getBorderInsets(java.awt.Component c) {
                return new java.awt.Insets(20, 20, 20, 20);
            }
        });

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
        this.start = start;
//          this.darkOverlay = new javax.swing.JWindow(start);
//        this.darkOverlay.setBounds(start.getBounds()); 
//        this.darkOverlay.setBackground(new java.awt.Color(0, 0, 0, 180)); 
//        this.darkOverlay.addMouseListener(new java.awt.event.MouseAdapter() {}); 
//        this.darkOverlay.setVisible(true); 
        this.setAlwaysOnTop(true);
        // --- BẮT ĐẦU: ĐỘ NÚT < THÀNH BO GÓC TRONG SUỐT ---
        exitButton.setContentAreaFilled(false);
        exitButton.setFocusPainted(false);
        exitButton.setBorderPainted(false);
        exitButton.setOpaque(false);
        exitButton.setRolloverEnabled(true);
        exitButton.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 18));

        exitButton.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
            @Override
            public void paint(java.awt.Graphics g, javax.swing.JComponent c) {
                java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();
                g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
                javax.swing.AbstractButton b = (javax.swing.AbstractButton) c;

                if (b.getModel().isPressed()) {
                    g2.setColor(new java.awt.Color(200, 200, 200, 255));
                } else if (b.getModel().isRollover()) {
                    g2.setColor(new java.awt.Color(255, 255, 255, 230));
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

        exitButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Hướng dẫn");
        getContentPane().setLayout(new java.awt.GridBagLayout());

        exitButton.setText("<");
        exitButton.setPreferredSize(new java.awt.Dimension(50, 30));
        exitButton.addActionListener(this::exitButtonActionPerformed);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 27;
        gridBagConstraints.ipady = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 0);
        getContentPane().add(exitButton, gridBagConstraints);

        jScrollPane2.setFocusable(false);
        jScrollPane2.setOpaque(false);

        jTextPane1.setContentType("text/html"); // NOI18N
        jTextPane1.setText("<div style=\"font-family: 'Segoe UI', Arial, sans-serif; padding: 15px; color: #FFFFFF; line-height: 1.6;\">\n    <h2 style=\"color: #FFB347; text-align: center; text-transform: uppercase; text-shadow: 1px 1px 2px #000;\">Hướng Dẫn Chơi Pikachu's Adventure</h2>\n\n    <h3 style=\"color: #48D1CC; border-bottom: 1px solid #48D1CC; padding-bottom: 5px;\">1. Mục Tiêu Trò Chơi</h3>\n    <p>Chào mừng bạn đến với thế giới của <b>Pikachu Adventure</b>! Nhiệm vụ của bạn là dọn sạch toàn bộ các hình Pokemon trên màn hình bằng cách tìm ra các cặp đôi giống nhau. Hãy thật nhanh tay và tinh mắt để hoàn thành màn chơi trước khi thanh thời gian đếm ngược cạn kiệt.</p>\n\n    <h3 style=\"color: #48D1CC; border-bottom: 1px solid #48D1CC; padding-bottom: 5px;\">2. Quy Tắc Kết Nối (Luật Chơi)</h3>\n    <p>Bạn chỉ có thể loại bỏ được <b style=\"color: #FFD700;\">2 thẻ Pokemon hoàn toàn giống nhau</b> nếu đường nối giữa chúng thỏa mãn các điều kiện:</p>\n    <ul style=\"color: #FFFFFF;\">\n        <li><b>Đường thẳng (Cạnh nhau):</b> Hai thẻ nằm sát nhau hoặc trên cùng một hàng ngang/dọc mà không có thẻ nào cản ở giữa.</li>\n        <li><b>Đường chữ L (1 lần gấp khúc):</b> Đường nối rẽ hướng 1 lần để chạm đến thẻ kia.</li>\n        <li><b>Đường chữ U hoặc Z (2 lần gấp khúc):</b> Đường nối rẽ hướng tối đa 2 lần (tạo thành tối đa 3 đoạn thẳng).</li>\n\n    <hr style=\"border: 0; border-top: 1px dashed #888888; margin: 5px 0 10px 0;\">\n\n    <div style=\"font-size: 0.9em; color: #CCCCCC; text-align: center;\">\n        <p style=\"margin-bottom: 5px; color: #FFB347;\">🎮 <b>Dự án Pikachu Adventure</b></p>\n        <p style=\"margin: 3px 0;\">Trưởng nhóm: <b>Nguyễn Việt Tuấn</b></p>\n        <p style=\"margin: 3px 0;\">Thành viên: <b>Từ Hoàng Thanh, Nguyễn Phương Thảo, Lò Hải Hoàng </b></p>\n</div>\n");
        jTextPane1.setFocusable(false);
        jTextPane1.setOpaque(false);
        jScrollPane2.setViewportView(jTextPane1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 422;
        gridBagConstraints.ipady = 570;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 6, 6, 6);
        getContentPane().add(jScrollPane2, gridBagConstraints);

        setBounds(0, 0, 464, 658);
    }// </editor-fold>//GEN-END:initComponents

    private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitButtonActionPerformed
        // TODO add your handling code here:
        audioManager.playSoundEffect("/sound/SoundTap/tap.wav"); // Kêu bíp một cái
        this.dispose();
    }//GEN-LAST:event_exitButtonActionPerformed

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

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                HelpDialog dialog = new HelpDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton exitButton;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextPane jTextPane1;
    // End of variables declaration//GEN-END:variables
}
