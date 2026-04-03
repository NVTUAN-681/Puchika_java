/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.pikachu_master.User_Interface.Screens;

import com.mycompany.pikachu_master.Controller.GameConfig;
import com.mycompany.pikachu_master.Model.ThemeManager;
import com.mycompany.pikachu_master.User_Interface.Components.BackgroundMain;
import com.mycompany.pikachu_master.User_Interface.Components.BackgroundStartScreen;
import com.mycompany.pikachu_master.Utils.Button_Icon;
import com.mycompany.pikachu_master.Utils.ImageLoad;
import java.awt.geom.RoundRectangle2D;

/**
 *
 * @author laptop
 */
public class LevelScreen extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(LevelScreen.class.getName());

    /**
     * Creates new form LevelScreen
     */
    StartScreen start;
    private String selectedSide = "NONE"; // Lưu phe đang chọn: LIGHT hoặc DARK
    private java.awt.Color currentBorderColor = new java.awt.Color(255, 215, 0); // Màu viền JFrame mặc định (Vàng)
    private java.awt.Image defaultBg; // Ảnh nền mặc định (ở giữa 2 phe)
    private java.awt.Image lightBg;   // Ảnh nền khi lướt vào phe Sáng
    private java.awt.Image darkBg;    // Ảnh nền khi lướt vào phe Tối
    private java.awt.Image currentBg; // Biến lưu ảnh đang được hiển thị

    public LevelScreen(StartScreen start) {
        this.setUndecorated(true);
// Khởi tạo nền mặc định bằng ThemeManager
        currentBg = ThemeManager.getBackgroundImage(ThemeManager.SIDE_DEFAULT);

        javax.swing.JPanel dynamicBackground = new javax.swing.JPanel() {
            @Override
            protected void paintComponent(java.awt.Graphics g) {
                super.paintComponent(g);
                if (currentBg != null) {
                    g.drawImage(currentBg, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        dynamicBackground.setLayout(new java.awt.GridBagLayout());
        setContentPane(dynamicBackground);
      
        initComponents();
        this.start = start;

        // 1. TẢI TÀI NGUYÊN: Load 2 loại phôi nút khác nhau cho 2 phe
        ImageLoad.loadBg("LIGHT_SIDE", 1, 250, 70, 15);
        ImageLoad.loadBg("DARK_SIDE", 2, 250, 70, 15);

        // 2. TRẠNG THÁI BAN ĐẦU: Ẩn tất cả các nút Level, chỉ hiện 2 Panel chọn phe
        setButtonsVisibility(false);
        // --- GỌI HIỆU ỨNG CHO 2 PANEL Ở ĐÂY ---
        applyPanelEffect(lightPanel, "LIGHT");
        applyPanelEffect(darkPanel, "DARK");
        setupFrameStyle();
        updateButtonSkins();
    }

    private void applySideSelection(String side) {
      // Ngăn chặn việc click chọn lại khi đã chọn phe rồi
        if (!this.selectedSide.equals("NONE")) {
            return;
        }

        this.selectedSide = side;

        // 1. Cấu hình nút SIÊU TO KHỔNG LỒ (Gần full màn hình 1214px)
        int btnWidth = 800; 
        int btnHeight = 120; // Nút cao hơn để cân đối với chiều dài
        java.awt.Dimension massiveBtnSize = new java.awt.Dimension(btnWidth, btnHeight);
        java.awt.Font massiveFont = new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 36); // Chữ bự chà bá

        // Load lại ảnh phôi siêu bự để không bị vỡ nét (bo góc 30 cho mềm mại)
        ImageLoad.loadBg("LIGHT_SIDE", 3, btnWidth, btnHeight, 30);
        ImageLoad.loadBg("DARK_SIDE", 2, btnWidth, btnHeight, 30);

        // 2. Ép Panel bung lụa ra toàn màn hình thay vì chỉ đứng ở giữa
        java.awt.GridBagLayout mainLayout = (java.awt.GridBagLayout) getContentPane().getLayout();
        java.awt.GridBagConstraints fullScreenGbc = new java.awt.GridBagConstraints();
        fullScreenGbc.gridx = 0; 
        fullScreenGbc.gridy = 0;
        fullScreenGbc.weightx = 1.0; 
        fullScreenGbc.weighty = 1.0; 
        fullScreenGbc.fill = java.awt.GridBagConstraints.BOTH; // <--- Điểm mấu chốt: Bung kín màn hình
        fullScreenGbc.anchor = java.awt.GridBagConstraints.CENTER;

        if (side.equals("LIGHT")) {
            // --- XỬ LÝ PANEL ---
            lightPanel.setOpaque(false);
            lightPanel.setBorder(null);    
            darkPanel.setVisible(false);   

            // Áp Layout full màn hình cho Light Panel
            mainLayout.setConstraints(lightPanel, fullScreenGbc);
            lightPanel.setPreferredSize(new java.awt.Dimension(1200, 800)); // Nới trần Panel
            lightPanel.setMinimumSize(new java.awt.Dimension(1200, 800));

            // --- PHÓNG TO VÀ BẬT NÚT ---
            javax.swing.JButton[] lightBtns = {africaButton, europeButton, asianButton};
            for (javax.swing.JButton btn : lightBtns) {
                btn.setVisible(true);
                btn.setPreferredSize(massiveBtnSize);
                btn.setMinimumSize(massiveBtnSize);
                btn.setMaximumSize(massiveBtnSize); // Ép cứng kích thước
                btn.setFont(massiveFont);
            }

            currentBorderColor = new java.awt.Color(255, 215, 0);

        } else if (side.equals("DARK")) {
            // --- XỬ LÝ PANEL ---
            darkPanel.setOpaque(false);
            darkPanel.setBorder(null);     
            lightPanel.setVisible(false);  

            // Áp Layout full màn hình cho Dark Panel
            mainLayout.setConstraints(darkPanel, fullScreenGbc);
            darkPanel.setPreferredSize(new java.awt.Dimension(1200, 800)); // Nới trần Panel
            darkPanel.setMinimumSize(new java.awt.Dimension(1200, 800));

            // --- PHÓNG TO VÀ BẬT NÚT ---
            javax.swing.JButton[] darkBtns = {easyButton, normalButton, hardButton};
            for (javax.swing.JButton btn : darkBtns) {
                btn.setVisible(true);
                btn.setPreferredSize(massiveBtnSize);
                btn.setMinimumSize(massiveBtnSize);
                btn.setMaximumSize(massiveBtnSize); // Ép cứng kích thước
                btn.setFont(massiveFont);
            }

            currentBorderColor = new java.awt.Color(180, 0, 255);
        }

        updateButtonSkins();

        this.revalidate();
        this.repaint();
    }

    private void applyPanelEffect(javax.swing.JPanel panel, String sideType) {
        // 1. Kích thước ảo ép cứng để chống giật (Jitter) của Swing
        panel.setPreferredSize(new java.awt.Dimension(380, 580));
        panel.setMinimumSize(new java.awt.Dimension(380, 580));
        panel.setOpaque(false);

        // 2. Màu sắc
        java.awt.Color normalBg = sideType.equals("LIGHT") ? new java.awt.Color(255, 255, 220, 150) : new java.awt.Color(20, 20, 40, 180);
        java.awt.Color hoverBg = sideType.equals("LIGHT") ? new java.awt.Color(255, 255, 220, 220) : new java.awt.Color(45, 45, 70, 220);
        java.awt.Color hoverBorder = sideType.equals("LIGHT") ? new java.awt.Color(255, 255, 0) : new java.awt.Color(200, 50, 255);

        final int[] drawOffset = {15}; 
        final java.awt.Color[] drawColor = {normalBg};

        // Tự vẽ lớp kính thu/phóng
        panel.setBorder(new javax.swing.border.AbstractBorder() {
            @Override
            public void paintBorder(java.awt.Component c, java.awt.Graphics g, int x, int y, int width, int height) {
                java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();
                g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
                
                int off = drawOffset[0];
                
                g2.setColor(drawColor[0]);
                g2.fillRect(x + off, y + off, width - 2 * off, height - 2 * off);
                
                if (off < 15) {
                    g2.setColor(currentBorderColor);
                    g2.setStroke(new java.awt.BasicStroke(2f));
                    g2.drawRect(x + off, y + off, width - 2 * off - 1, height - 2 * off - 1);
                }
                g2.dispose();
            }
        });

        // 3. Sự kiện chuột
        panel.addMouseListener(new java.awt.event.MouseAdapter() {
            private javax.swing.Timer timer;

            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (!selectedSide.equals(ThemeManager.SIDE_DEFAULT)) return;
                
                panel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
                drawColor[0] = hoverBg;
                currentBorderColor = hoverBorder;
                
                // ---> ĐÃ TRẢ LẠI: ĐỔI HÌNH NỀN KHI LƯỚT VÀO <---
                currentBg = ThemeManager.getBackgroundImage(sideType);
                getContentPane().repaint();

                animate(0); // Lớp kính phình to
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (!selectedSide.equals(ThemeManager.SIDE_DEFAULT)) return;
                
                drawColor[0] = normalBg;
                currentBorderColor = new java.awt.Color(255, 215, 0);
                
                // ---> ĐÃ TRẢ LẠI: QUAY VỀ HÌNH NỀN CŨ KHI RÚT RA <---
                currentBg = ThemeManager.getBackgroundImage(ThemeManager.SIDE_DEFAULT);
                getContentPane().repaint();

                animate(15); // Lớp kính thu nhỏ lại
            }

            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                applySideSelection(sideType);
            }

            private void animate(int targetOffset) {
                if (timer != null && timer.isRunning()) timer.stop();
                timer = new javax.swing.Timer(10, e -> {
                    if (drawOffset[0] == targetOffset) {
                        timer.stop();
                        return;
                    }
                    drawOffset[0] += (drawOffset[0] < targetOffset) ? 1 : -1;
                    panel.repaint(); 
                });
                timer.start();
            }
        });
    }

    private void updateButtonSkins() {
        // LIGHT -> LIGHT_SIDE (Loại 1) | DARK -> DARK_SIDE (Loại 2)
        String styleKey = selectedSide.equals("DARK") ? "DARK_SIDE" : "LIGHT_SIDE";
        java.awt.Color txtColor = selectedSide.equals("DARK") ? java.awt.Color.CYAN : java.awt.Color.BLACK;

        javax.swing.JButton[] allButtons = {africaButton, asianButton, europeButton, easyButton, normalButton, hardButton};

        for (javax.swing.JButton btn : allButtons) {
            // Dùng hàm applyCachedIcons từ Utils của bạn
            Button_Icon.applyCachedIcons(btn, btn.getText(), styleKey);
            btn.setForeground(txtColor);
        }
    }

    /**
     * Hàm ẩn/hiện hàng loạt các nút Level
     */
    private void setButtonsVisibility(boolean visible) {
        africaButton.setVisible(visible);
        europeButton.setVisible(visible);
        asianButton.setVisible(visible);
        easyButton.setVisible(visible);
        normalButton.setVisible(visible);
        hardButton.setVisible(visible);
    }

    private void setupFrameStyle() {
        // Nút thoát (LayeredPane để không bị che)
        this.getLayeredPane().add(exitButton3, javax.swing.JLayeredPane.PALETTE_LAYER);
        exitButton3.setBounds(20, 20, 50, 30);

        // FIX LỖI BỐ CỤC: Ẩn jPanel1 rỗng đi để không chiếm chỗ đẩy lệch 2 panel chính
        jPanel1.setVisible(false);

        // Bo góc JFrame
        this.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent evt) {
                setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 40, 40));
            }
        });

        // Vẽ viền JFrame động (Sửa JPanel thành JComponent cho an toàn)
        ((javax.swing.JComponent) getContentPane()).setBorder(new javax.swing.border.AbstractBorder() {
            @Override
            public void paintBorder(java.awt.Component c, java.awt.Graphics g, int x, int y, int width, int height) {
                java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();
                g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(currentBorderColor);
                g2.setStroke(new java.awt.BasicStroke(5f));
                g2.drawRoundRect(x + 2, y + 2, width - 4, height - 4, 40, 40);
                g2.dispose();
            }
        });

        // Khóa màn hình chính (darkOverlay)
        this.darkOverlay = new javax.swing.JWindow(start);
        this.darkOverlay.setBounds(start.getBounds());
        this.darkOverlay.setBackground(new java.awt.Color(0, 0, 0, 180));
        this.darkOverlay.setVisible(true);
    }
    
     private void setupAllButtonIcons() {
        // ---> 1. THUỐC ĐẶC TRỊ TẬT KÉO DÃN LỆCH CHỮ CỦA NETBEANS <---
        java.awt.GridBagLayout layout = (java.awt.GridBagLayout) getContentPane().getLayout();
        javax.swing.AbstractButton[] btns = {africaButton, europeButton, asianButton};
        
        for (javax.swing.AbstractButton btn : btns) {
            java.awt.GridBagConstraints gbc = layout.getConstraints(btn);
            gbc.fill = java.awt.GridBagConstraints.NONE; // Cấm tiệt việc tự kéo dãn nút
            gbc.ipadx = 0; // Xóa sạch cái lề ảo 150px mà NetBeans tự nhét vào
            layout.setConstraints(btn, gbc);
        }
        
        
       Button_Icon.applyCachedIcons(africaButton, "AFRICA", "PAUSE_BTN");
       Button_Icon.applyCachedIcons(europeButton, "EUROPE", "PAUSE_BTN");
       Button_Icon.applyCachedIcons(asianButton, "ASIAN", "PAUSE_BTN");
       
       africaButton.setForeground(java.awt.Color.WHITE);
       europeButton.setForeground(java.awt.Color.WHITE);
       asianButton.setForeground(java.awt.Color.WHITE);
        
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

        jPanel1 = new javax.swing.JPanel();
        exitButton3 = new javax.swing.JButton();
        lightPanel = new javax.swing.JPanel();
        africaButton = new javax.swing.JButton();
        europeButton = new javax.swing.JButton();
        asianButton = new javax.swing.JButton();
        darkPanel = new javax.swing.JPanel();
        normalButton = new javax.swing.JButton();
        hardButton = new javax.swing.JButton();
        easyButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel1.setPreferredSize(new java.awt.Dimension(50, 1200));

        exitButton3.setText("<");
        exitButton3.setPreferredSize(new java.awt.Dimension(50, 30));
        exitButton3.addActionListener(this::exitButton3ActionPerformed);
        jPanel1.add(exitButton3);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 17;
        gridBagConstraints.ipady = 738;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 17, 23, 0);
        getContentPane().add(jPanel1, gridBagConstraints);

        lightPanel.setMinimumSize(new java.awt.Dimension(350, 550));
        lightPanel.setPreferredSize(new java.awt.Dimension(350, 550));
        lightPanel.setLayout(new java.awt.GridBagLayout());

        africaButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        africaButton.setText("AFRICA");
        africaButton.setPreferredSize(new java.awt.Dimension(250, 60));
        africaButton.addActionListener(this::africaButtonActionPerformed);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 169;
        gridBagConstraints.ipady = 33;
        gridBagConstraints.insets = new java.awt.Insets(130, 80, 0, 80);
        lightPanel.add(africaButton, gridBagConstraints);

        europeButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        europeButton.setText("EUROPE  ");
        europeButton.setPreferredSize(new java.awt.Dimension(250, 60));
        europeButton.addActionListener(this::europeButtonActionPerformed);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 156;
        gridBagConstraints.ipady = 33;
        gridBagConstraints.insets = new java.awt.Insets(60, 80, 0, 80);
        lightPanel.add(europeButton, gridBagConstraints);

        asianButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        asianButton.setText("ASIAN ");
        asianButton.setPreferredSize(new java.awt.Dimension(250, 60));
        asianButton.addActionListener(this::asianButtonActionPerformed);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 171;
        gridBagConstraints.ipady = 33;
        gridBagConstraints.insets = new java.awt.Insets(60, 80, 180, 80);
        lightPanel.add(asianButton, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(125, 213, 125, 0);
        getContentPane().add(lightPanel, gridBagConstraints);

        darkPanel.setMinimumSize(new java.awt.Dimension(350, 550));
        darkPanel.setPreferredSize(new java.awt.Dimension(350, 550));
        darkPanel.setLayout(new java.awt.GridBagLayout());

        normalButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        normalButton.setText("THƯỜNG");
        normalButton.setPreferredSize(new java.awt.Dimension(250, 60));
        normalButton.addActionListener(this::normalButtonActionPerformed);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 156;
        gridBagConstraints.ipady = 33;
        gridBagConstraints.insets = new java.awt.Insets(60, 80, 0, 80);
        darkPanel.add(normalButton, gridBagConstraints);

        hardButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        hardButton.setText("KHÓ NHA BRO");
        hardButton.setPreferredSize(new java.awt.Dimension(250, 60));
        hardButton.addActionListener(this::hardButtonActionPerformed);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 171;
        gridBagConstraints.ipady = 33;
        gridBagConstraints.insets = new java.awt.Insets(60, 80, 180, 80);
        darkPanel.add(hardButton, gridBagConstraints);

        easyButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        easyButton.setText("DỄ");
        easyButton.setPreferredSize(new java.awt.Dimension(250, 60));
        easyButton.addActionListener(this::easyButtonActionPerformed);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 169;
        gridBagConstraints.ipady = 33;
        gridBagConstraints.insets = new java.awt.Insets(130, 80, 0, 80);
        darkPanel.add(easyButton, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(125, 0, 125, 220);
        getContentPane().add(darkPanel, gridBagConstraints);

        setSize(new java.awt.Dimension(1214, 808));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void africaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_africaButtonActionPerformed
        // TODO add your handling code here:
        start.setLevel("AFRICA");
        start.UpdateLevel("AFRICA");
        start.setGameTheme(this.selectedSide);
        start.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_africaButtonActionPerformed

    private void asianButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_asianButtonActionPerformed
        // TODO add your handling code here:
        start.setLevel("ASIAN");
        start.UpdateLevel("ASIAN");
        start.setGameTheme(this.selectedSide);
        start.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_asianButtonActionPerformed

    private void europeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_europeButtonActionPerformed
        // TODO add your handling code here:
        start.setLevel("EUROPE");
        start.UpdateLevel("EUROPE");
        start.setGameTheme(this.selectedSide);
        start.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_europeButtonActionPerformed

    private void exitButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitButton3ActionPerformed
        // TODO add your handling code here:
        this.dispose();
        if (start != null) {
            start.setVisible(true);
        }
    }//GEN-LAST:event_exitButton3ActionPerformed

    private void easyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_easyButtonActionPerformed
        // TODO add your handling code here:
        start.setLevel("EASY");
        start.UpdateLevel("EASY");
        start.setGameTheme(this.selectedSide);
        start.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_easyButtonActionPerformed

    private void normalButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_normalButtonActionPerformed
        // TODO add your handling code here:
        start.setLevel("NORMAL");
        start.UpdateLevel("NORMAL");
        start.setGameTheme(this.selectedSide);
        start.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_normalButtonActionPerformed

    private void hardButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hardButtonActionPerformed
        // TODO add your handling code here:
        start.setLevel("HARD");
        start.UpdateLevel("HARD");
        start.setGameTheme(this.selectedSide);
        start.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_hardButtonActionPerformed

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
        //java.awt.EventQueue.invokeLater(() -> new LevelScreen().setVisible(true));
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
    private javax.swing.JButton africaButton;
    private javax.swing.JButton asianButton;
    private javax.swing.JPanel darkPanel;
    private javax.swing.JButton easyButton;
    private javax.swing.JButton europeButton;
    private javax.swing.JButton exitButton3;
    private javax.swing.JButton hardButton;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel lightPanel;
    private javax.swing.JButton normalButton;
    // End of variables declaration//GEN-END:variables
}
