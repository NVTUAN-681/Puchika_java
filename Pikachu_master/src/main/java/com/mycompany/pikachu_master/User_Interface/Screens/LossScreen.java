/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.pikachu_master.User_Interface.Screens;

import com.mycompany.pikachu_master.Controller.GameConfig;
import com.mycompany.pikachu_master.Controller.PlayScreen;
import com.mycompany.pikachu_master.Model.LevelType;
import com.mycompany.pikachu_master.User_Interface.Components.BackgroundLossScreen;
import com.mycompany.pikachu_master.Utils.Button_Icon;
import com.mycompany.pikachu_master.Utils.ImageLoad;
import com.mycompany.pikachu_master.Utils.SoundLoad;
import com.mycompany.pikachu_master.User_Interface.Components.BackgroundLossScreen;
import com.mycompany.pikachu_master.Model.LevelType;
import com.mycompany.pikachu_master.User_Interface.Components.BackgroundLossScreen;
import com.mycompany.pikachu_master.Utils.Button_Icon;
import com.mycompany.pikachu_master.Utils.ImageLoad;
import com.mycompany.pikachu_master.Utils.SoundLoad;
import com.mycompany.pikachu_master.User_Interface.Components.BackgroundLossScreen;
import com.mycompany.pikachu_master.Model.LevelType;
import java.awt.geom.RoundRectangle2D;

/**
 *
 * @author laptop
 */
public class LossScreen extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(LossScreen.class.getName());

    /**
     * Creates new form LossScreen
     */
    
    MainScreen main;
    GameConfig config;
    LevelType level;
    PlayScreen play;
    
    
    
    private SoundLoad audioManager = new SoundLoad();    
    private int displayedScore = 0;
    private int currentTotalScore = 0;
    
    public LossScreen(MainScreen main, GameConfig config , LevelType level, PlayScreen play) {
        this.setUndecorated(true);
        setContentPane(new BackgroundLossScreen());
        initComponents();
        
                 // ---> THÊM ĐOẠN CODE NÀY ĐỂ BO GÓC JFRAME <---
        this.addComponentListener(new java.awt.event.ComponentAdapter() {
                 public void componentResized(java.awt.event.ComponentEvent evt) {
            // Cắt JFrame thành hình chữ nhật bo góc
            // Tham số 40, 40 là độ cong của góc (bạn có thể tăng giảm tùy ý)
            setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 40, 40));
        }
        });
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
        ImageLoad.loadBg("PAUSE_BTN", 2, 250, 40, 10);
        setupAllButtonIcons();
        
        this.setMinimumSize(new java.awt.Dimension(300, 400));
        this.main = main;
        this.config = config;
        this.level = level;
        this.play = play;
        
        // ---> TẠO LỚP PHỦ ĐEN MỜ KHÓA MÀN HÌNH CHÍNH TẠI ĐÂY <---
        this.darkOverlay = new javax.swing.JWindow(main);
        this.darkOverlay.setBounds(main.getBounds()); 
        this.darkOverlay.setBackground(new java.awt.Color(0, 0, 0, 180)); 
        this.darkOverlay.addMouseListener(new java.awt.event.MouseAdapter() {}); 
        this.darkOverlay.setVisible(true); 
        this.setAlwaysOnTop(true);
        
        //this.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        
        this.main.stopMusic();
        audioManager.playBGM("/images/Sound/Loss.wav");
        this.updateScore(play.get_TotalScore());
        
        // Bắt sự kiện bấm nút X
//        this.addWindowListener(new java.awt.event.WindowAdapter() {
//            @Override
//            public void windowClosing(java.awt.event.WindowEvent e) {
//               main.setEnabled(true);
//                main.dispose(); 
//                StartScreen pika = new StartScreen(config);
//                pika.setLevel(config.GetLevel());
//                pika.setVisible(true); // Báo màn hình chính đếm thời gian tiếp
//            }
//        });
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                main.setEnabled(true);
                main.dispose(); 
                StartScreen pika = new StartScreen(config, level);
                pika.setLevel(config.GetLevel());
                pika.setVisible(true); // Báo màn hình chính đếm thời gian tiếp
            }
        });
        
    }
    
    public void updateScore(int newScore) {
    // 1. Tính tổng điểm cuối cùng
    this.currentTotalScore = newScore;
    this.displayedScore = 0;

    // 2. Thiết lập cấu hình thời gian
    final int TotalAnimationTime = 2000;
    final int TimeDelay = 20;            
    
    // 3. Tính toán số bước nhảy và giá trị mỗi bước
    int totalSteps = TotalAnimationTime / TimeDelay; // Tổng số lần Timer sẽ chạy
    // Mỗi bước cộng bao nhiêu điểm (dùng số thực để tránh mất mát dữ liệu khi chia)
    final double incrementPerStep = (double) currentTotalScore / totalSteps;

    javax.swing.Timer scoreTimer = new javax.swing.Timer(TimeDelay, null);
    scoreTimer.addActionListener(new java.awt.event.ActionListener() {
        private int currentStep = 0;

        @Override
        public void actionPerformed(java.awt.event.ActionEvent e) {
            currentStep++;
            
            if (currentStep <= totalSteps) {
                displayedScore = (int) (currentStep * incrementPerStep);
                if (displayedScore > currentTotalScore) 
                    displayedScore = currentTotalScore;
                
                scoreLabel.setText(String.valueOf(displayedScore));
            } else {
                // Bước cuối cùng: Chốt hạ con số chính xác nhất
                scoreLabel.setText(String.valueOf(currentTotalScore));
                ((javax.swing.Timer) e.getSource()).stop();
            }
        }
    });
    
    scoreTimer.start();
}
    
     private void setupAllButtonIcons() {
        // ---> 1. THUỐC ĐẶC TRỊ TẬT KÉO DÃN LỆCH CHỮ CỦA NETBEANS <---
        java.awt.GridBagLayout layout = (java.awt.GridBagLayout) getContentPane().getLayout();
        javax.swing.AbstractButton[] btns = {exitButton, retryButton};
        
        for (javax.swing.AbstractButton btn : btns) {
            java.awt.GridBagConstraints gbc = layout.getConstraints(btn);
            gbc.fill = java.awt.GridBagConstraints.NONE; // Cấm tiệt việc tự kéo dãn nút
            gbc.ipadx = 0; // Xóa sạch cái lề ảo 150px mà NetBeans tự nhét vào
            layout.setConstraints(btn, gbc);
        }
        
        
       Button_Icon.applyCachedIcons(exitButton, "THOÁT", "PAUSE_BTN");
       Button_Icon.applyCachedIcons(retryButton, "VÁN MỚI", "PAUSE_BTN");
       
       exitButton.setForeground(java.awt.Color.WHITE);
        retryButton.setForeground(java.awt.Color.WHITE);
        
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

        retryButton = new javax.swing.JButton();
        exitButton = new javax.swing.JButton();
        scoreLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        retryButton.setText("VÁN MỚI");
        retryButton.setPreferredSize(new java.awt.Dimension(150, 30));
        retryButton.addActionListener(this::retryButtonActionPerformed);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.ipady = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(18, 34, 0, 36);
        getContentPane().add(retryButton, gridBagConstraints);

        exitButton.setText("THOÁT");
        exitButton.setPreferredSize(new java.awt.Dimension(150, 30));
        exitButton.addActionListener(this::exitButtonActionPerformed);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 158;
        gridBagConstraints.ipady = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 34, 161, 36);
        getContentPane().add(exitButton, gridBagConstraints);

        scoreLabel.setFont(new java.awt.Font("Segoe UI", 3, 48)); // NOI18N
        scoreLabel.setForeground(new java.awt.Color(255, 255, 0));
        scoreLabel.setText("9999");
        getContentPane().add(scoreLabel, new java.awt.GridBagConstraints());

        setSize(new java.awt.Dimension(464, 658));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void retryButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_retryButtonActionPerformed
        // TODO add your handling code here:
       // config = new GameConfig(6, 5,150, "");
       //this.main.playBGM();
       audioManager.stopBGM();
       String levelName = config.GetLevel(); 
        
        if (levelName.equalsIgnoreCase("AFRICA")) {
            audioManager.playBGM("/Sound/SoundAfrica_Europe.wav");
        } else if (levelName.equalsIgnoreCase("ASIAN")) {
            audioManager.playBGM("/Sound/SoundAsian.wav");
        } else if (levelName.equalsIgnoreCase("EUROPE")) {
            audioManager.playBGM("/Sound/SoundAfrica_Europe.wav");
        } else {
            // Nhạc mặc định nếu người chơi không chọn màn mà bấm Play ngay từ đầu
            audioManager.playBGM("/Sound/SoundAfrica_Europe.wav");
        }

       //main.playBGM();
       main.setEnabled(true);
       main.resertGame(config, level);
       this.dispose();
    }//GEN-LAST:event_retryButtonActionPerformed

    private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitButtonActionPerformed
        // TODO add your handling code here:
        audioManager.stopBGM();
        
        StartScreen pika = new StartScreen(config, level);
        pika.setLevel(config.GetLevel());
        pika.setVisible(true);
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

        /* Create and display the form */
        //java.awt.EventQueue.invokeLater(() -> new LossScreen().setVisible(true));
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
    private javax.swing.JButton exitButton;
    private javax.swing.JButton retryButton;
    private javax.swing.JLabel scoreLabel;
    // End of variables declaration//GEN-END:variables
}
