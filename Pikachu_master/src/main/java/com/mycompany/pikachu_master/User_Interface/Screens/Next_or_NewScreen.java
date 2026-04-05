/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.pikachu_master.User_Interface.Screens;

//import static com.mycompany.pikachu_master.User_Interface.Screens.HighScoreScreen.main;
//import static com.mycompany.pikachu_master.User_Interface.Screens.MainScreen.main;
//import static com.mycompany.pikachu_master.User_Interface.Screens.StartScreen.main;
import com.mycompany.pikachu_master.Controller.GameConfig;
import com.mycompany.pikachu_master.Data.gameDAO;
import com.mycompany.pikachu_master.Model.LevelType;
import com.mycompany.pikachu_master.Utils.ImageLoad;
import com.mycompany.pikachu_master.Utils.SoundLoad;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JWindow;
import com.mycompany.pikachu_master.User_Interface.Components.BackgroundPause;

/**
 *
 * @author laptop
 */
public class Next_or_NewScreen extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Next_or_NewScreen.class.getName());

    /**
     * Creates new form Next_or_NewScreen
     */
    private SoundLoad audioManager = new SoundLoad();
    private final JWindow darkOverlay;
    private StartScreen start;
    private GameConfig configNew;
    private GameConfig configSaved;
    private LevelType level;
            
    public Next_or_NewScreen(StartScreen start, GameConfig configNew, GameConfig configSave) {                
        this.start = start;
        setUndecorated(true);
        setContentPane(new BackgroundPause());
        this.configNew = configNew;
        this.configSaved = configSave;
        this.level = new LevelType();
        initComponents();
        
        setupButtonStyles();
        setupLabelStyle();
        setLocationRelativeTo(start);
        this.addComponentListener(new java.awt.event.ComponentAdapter() {
        @Override
        public void componentResized(java.awt.event.ComponentEvent evt) {
            if(isUndecorated()){
                setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 40, 40));
            }
        }
    });
         javax.swing.JPanel contentPane = (javax.swing.JPanel) this.getContentPane();
        contentPane.setBorder(new javax.swing.border.AbstractBorder() {
            @Override
            public void paintBorder(java.awt.Component c, java.awt.Graphics g, int x, int y, int width, int height) {
                java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();
                
                g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
                
              
                g2.setColor(new java.awt.Color(255, 215, 0));
                
                g2.setStroke(new java.awt.BasicStroke(4f)); 
                
               
                g2.drawRoundRect(x + 2, y + 2, width - 4, height - 4, 40, 40);
                g2.dispose();
            }
        });
        ImageLoad.loadBg("PAUSE_BTN", 4, 100, 60, 10);
        
        
        // Lấy kích thước toàn màn hình
        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        this.darkOverlay = new javax.swing.JWindow(); // Không để owner là 'this' nếu muốn quản lý layer thủ công
        this.darkOverlay.setSize(screenSize);
        this.darkOverlay.setLocation(0, 0);
        this.darkOverlay.setBackground(new java.awt.Color(0, 0, 0, 180));
        this.darkOverlay.setVisible(true);

        // Đảm bảo Frame chính nằm trên cái lớp phủ đen
        this.toFront(); 
        this.setAlwaysOnTop(true);
    }
    
   private void setupButtonStyles() {
        javax.swing.JButton[] btns = {yes_I_doButton, No_I_dontButton};
        
        // Lấy Layout hiện tại của Frame để can thiệp
        java.awt.GridBagLayout layout = (java.awt.GridBagLayout) getContentPane().getLayout();

        for (javax.swing.JButton btn : btns) {
            
            // 1. ÉP CỨNG KÍCH THƯỚC NÚT (Tránh bị NetBeans kéo lệch)
            java.awt.Dimension fixedSize = new java.awt.Dimension(110, 40);
            btn.setPreferredSize(fixedSize);
            btn.setMinimumSize(fixedSize);
            btn.setMaximumSize(fixedSize);
            
            // 2. SỬA LỖI LỆCH TỈ LỆ CỦA GRIDBAGLAYOUT
            java.awt.GridBagConstraints gbc = layout.getConstraints(btn);
            gbc.fill = java.awt.GridBagConstraints.NONE; // Tắt chế độ tự động kéo dãn
            gbc.weightx = 0.5; // Bắt buộc chia đều 50% không gian cho mỗi cột
            gbc.ipadx = 0; // Xóa padding ảo của NetBeans
            gbc.ipady = 0; 
            layout.setConstraints(btn, gbc);

            // 3. SET STYLE NỀN KÍNH MỜ
            btn.setRolloverEnabled(true);
            btn.setContentAreaFilled(false);
            btn.setFocusPainted(false);
            btn.setBorderPainted(false);
            btn.setOpaque(false);
            
            btn.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 16));
            
            btn.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
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
    }
   
   private void setupLabelStyle() {
        // Tắt nền mặc định để lớp nền mờ không bị đè lên những vùng thừa ở góc
        jLabel1.setOpaque(false); 
        // Đổi màu chữ thành trắng
        jLabel1.setForeground(java.awt.Color.WHITE); 
        
        jLabel1.setUI(new javax.swing.plaf.basic.BasicLabelUI() {
            @Override
            public void paint(java.awt.Graphics g, javax.swing.JComponent c) {
                java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();
                g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);

                // 1. Vẽ nền đen mờ (giống hệt trạng thái bình thường của nút)
                g2.setColor(new java.awt.Color(0, 0, 0, 150)); 
                g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 20, 20);
                
                // 2. Vẽ viền trắng mỏng xung quanh
                g2.setColor(java.awt.Color.WHITE);
                g2.setStroke(new java.awt.BasicStroke(1.5f));
                g2.drawRoundRect(0, 0, c.getWidth() - 1, c.getHeight() - 1, 20, 20);

                g2.dispose();
                
                // 3. Gọi hàm gốc để vẽ phần chữ "Will you marry me?" lên trên cùng
                super.paint(g, c);
            }
        });
    }
        
        private void OpenMainScreen(GameConfig config){
//            config.setResume(0);
            MainScreen Main = new MainScreen(config, LevelType.getByName(config.GetLevel()));
            Main.setVisible(true);
            javax.swing.SwingUtilities.invokeLater(() -> {
                if (start != null) {
                start.dispose();
            }
                this.dispose();
            });
        }
        //ghi đè để tắt màn kính phủ
        @Override
    public void dispose() {
        if (darkOverlay != null) {
            darkOverlay.dispose(); 
        }
        super.dispose();
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

        yes_I_doButton = new javax.swing.JButton();
        No_I_dontButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        yes_I_doButton.setText("Có!");
        yes_I_doButton.setPreferredSize(new java.awt.Dimension(100, 30));
        yes_I_doButton.addActionListener(this::yes_I_doButtonActionPerformed);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 28;
        gridBagConstraints.ipady = 7;
        gridBagConstraints.insets = new java.awt.Insets(30, 30, 40, 15);
        getContentPane().add(yes_I_doButton, gridBagConstraints);

        No_I_dontButton.setText("Không!");
        No_I_dontButton.setToolTipText("");
        No_I_dontButton.setPreferredSize(new java.awt.Dimension(100, 30));
        No_I_dontButton.addActionListener(this::No_I_dontButtonActionPerformed);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 28;
        gridBagConstraints.ipady = 7;
        gridBagConstraints.insets = new java.awt.Insets(30, 15, 40, 30);
        getContentPane().add(No_I_dontButton, gridBagConstraints);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Tiếp tục ván chơi trước?\n");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 41;
        gridBagConstraints.ipady = 61;
        gridBagConstraints.insets = new java.awt.Insets(20, 30, 0, 30);
        getContentPane().add(jLabel1, gridBagConstraints);

        setBounds(0, 0, 314, 208);
    }// </editor-fold>//GEN-END:initComponents

    private void yes_I_doButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yes_I_doButtonActionPerformed
        // TODO add your handling code here:
        OpenMainScreen(configSaved);
        audioManager.playSoundEffect("/sound/SoundTap/tap.wav");
    }//GEN-LAST:event_yes_I_doButtonActionPerformed

    private void No_I_dontButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_No_I_dontButtonActionPerformed
        // TODO add your handling code here:
        new gameDAO().deleteSaveGame(level.getLevel());  
        OpenMainScreen(configNew);
        audioManager.playSoundEffect("/sound/SoundTap/tap.wav");
    }//GEN-LAST:event_No_I_dontButtonActionPerformed

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
        //java.awt.EventQueue.invokeLater(() -> new Next_or_NewScreen().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton No_I_dontButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JButton yes_I_doButton;
    // End of variables declaration//GEN-END:variables
}
