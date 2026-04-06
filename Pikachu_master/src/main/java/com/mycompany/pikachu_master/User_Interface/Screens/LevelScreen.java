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
import com.mycompany.pikachu_master.Utils.SoundLoad;
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
    private SoundLoad audioManager = new SoundLoad();
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
        setupAllButtonIcons();

        // 1. TẢI TÀI NGUYÊN: Load 2 loại phôi nút khác nhau cho 2 phe
        ImageLoad.loadBg("LIGHT_SIDE", 3, 250, 70, 15);
        ImageLoad.loadBg("DARK_SIDE", 2, 250, 70, 15);

        // 2. TRẠNG THÁI BAN ĐẦU: Ẩn tất cả các nút Level, chỉ hiện 2 Panel chọn phe
        setButtonsVisibility(false);
        java.awt.Image imgLight = null;
        java.awt.Image imgDark = null;
        try {
            // Đã thêm dấu "/" ở đầu để đảm bảo path lấy từ thư mục gốc resources
            java.net.URL lightUrl = getClass().getResource("/images/BackGround/sunMom.png");
            if (lightUrl != null) {
                imgLight = new javax.swing.ImageIcon(lightUrl).getImage();
            } else {
                System.out.println("⚠️ LỖI: Không tìm thấy ảnh /images/BackGround/light.png");
            }

            java.net.URL darkUrl = getClass().getResource("/images/BackGround/moonMom.png");
            if (darkUrl != null) {
                imgDark = new javax.swing.ImageIcon(darkUrl).getImage();
            } else {
                System.out.println("⚠️ LỖI: Không tìm thấy ảnh /images/BackGround/moon.png");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // --- GỌI HIỆU ỨNG CHO 2 PANEL Ở ĐÂY ---
        applyPanelEffect(lightPanel, "LIGHT", imgLight);
        applyPanelEffect(darkPanel, "DARK", imgDark);
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
            //lightPanel.setBorder(null);    
            darkPanel.setVisible(false);

            // Áp Layout full màn hình cho Light Panel
            mainLayout.setConstraints(lightPanel, fullScreenGbc);
            lightPanel.setPreferredSize(new java.awt.Dimension(1200, 800)); // Nới trần Panel
            lightPanel.setMinimumSize(new java.awt.Dimension(1200, 800));

            // --- PHÓNG TO VÀ BẬT NÚT ---
            javax.swing.JButton[] lightBtns = {easyButton, normalButton, hardButton};
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
            //darkPanel.setBorder(null);     
            lightPanel.setVisible(false);

            // Áp Layout full màn hình cho Dark Panel
            mainLayout.setConstraints(darkPanel, fullScreenGbc);
            darkPanel.setPreferredSize(new java.awt.Dimension(1200, 800)); // Nới trần Panel
            darkPanel.setMinimumSize(new java.awt.Dimension(1200, 800));

            // --- PHÓNG TO VÀ BẬT NÚT ---
            javax.swing.JButton[] darkBtns = {africaButton, europeButton, asianButton};
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

    private void applyPanelEffect(javax.swing.JPanel panel, String sideType, java.awt.Image panelImage) {
        // 1. Kích thước ảo ép cứng để chống giật
        panel.setPreferredSize(new java.awt.Dimension(380, 580));
        panel.setMinimumSize(new java.awt.Dimension(380, 580));
        panel.setOpaque(false);

        // 2. Màu sắc
        java.awt.Color normalBg = sideType.equals("LIGHT") ? new java.awt.Color(255, 255, 220, 100) : new java.awt.Color(20, 20, 40, 180);
        java.awt.Color hoverBg = sideType.equals("LIGHT") ? new java.awt.Color(255, 255, 220, 180) : new java.awt.Color(45, 45, 70, 220);
        java.awt.Color hoverBorder = sideType.equals("LIGHT") ? new java.awt.Color(255, 255, 0) : new java.awt.Color(200, 50, 255);

        final int[] drawOffset = {15};
        final java.awt.Color[] drawColor = {normalBg};

        // Tự vẽ lớp kính thu/phóng và chèn ảnh lên trên
        panel.setBorder(new javax.swing.border.AbstractBorder() {
            @Override
            public void paintBorder(java.awt.Component c, java.awt.Graphics g, int x, int y, int width, int height) {
                if (!selectedSide.equals("NONE")) {
                    return;
                }
                java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();
                g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);

                int off = drawOffset[0];

                // Vẽ lớp phủ màu trong suốt
                g2.setColor(drawColor[0]);
                g2.fillRect(x + off, y + off, width - 2 * off, height - 2 * off);

                // Vẽ ảnh lên trên lớp phủ
                if (panelImage != null) {
                    int imgPadding = 40; // Độ thụt lề của ảnh so với viền (thay đổi để chỉnh độ to nhỏ của ảnh)

                    int imgX = x + off + imgPadding;
                    int imgY = y + off + imgPadding;
                    int imgW = width - 2 * off - 2 * imgPadding;
                    int imgH = height - 2 * off - 2 * imgPadding;

                    g2.drawImage(panelImage, imgX, imgY, imgW, imgH, null);
                }

                // Vẽ viền
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
                // CHÚ Ý: Đã sửa lại khớp với biến selectedSide của bạn
                if (!selectedSide.equals("NONE")) {
                    return;
                }

                panel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
                drawColor[0] = hoverBg;
                currentBorderColor = hoverBorder;

                currentBg = ThemeManager.getBackgroundImage(sideType);
                getContentPane().repaint();

                animate(0); // Phình to
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                // CHÚ Ý: Đã sửa lại khớp với biến selectedSide của bạn
                if (!selectedSide.equals("NONE")) {
                    return;
                }

                drawColor[0] = normalBg;
                currentBorderColor = new java.awt.Color(255, 215, 0);

                currentBg = ThemeManager.getBackgroundImage(ThemeManager.SIDE_DEFAULT);
                getContentPane().repaint();

                animate(15); // Thu nhỏ
            }

            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (selectedSide.equals("NONE")) { // Chỉ phát tiếng khi chưa chọn phe nào
                    SoundLoad audioManager = new SoundLoad();
                    audioManager.playTransitionSound("/Sound/SoundTap/NextScreen.wav"); // Đổi đường dẫn file nếu cần
                }
                applySideSelection(sideType);
            }

            private void animate(int targetOffset) {
                if (timer != null && timer.isRunning()) {
                    timer.stop();
                }
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
        String styleKey = selectedSide.equals("DARK") ? "DARK_SIDE" : "LIGHT_SIDE";
        java.awt.Color txtColor = selectedSide.equals("DARK") ? java.awt.Color.CYAN : java.awt.Color.DARK_GRAY;

        javax.swing.JButton[] allButtons = {africaButton, asianButton, europeButton, easyButton, normalButton, hardButton};

       for (javax.swing.JButton btn : allButtons) {
            // 1. LƯU LẠI TOÀN BỘ CHỮ VÀ FONT HIỆN TẠI (Size 36 bự chà bá)
            String textToSave = btn.getText();
            java.awt.Font fontToSave = btn.getFont(); // <--- Bắt quả tang và lưu lại font
            
            if (textToSave == null || textToSave.isEmpty() || textToSave.trim().isEmpty()) {
                if (btn == africaButton) textToSave = "AFRICAN";
                else if (btn == europeButton) textToSave = "EUROPEAN";
                else if (btn == asianButton) textToSave = "ASIAN";
                else if (btn == easyButton) textToSave = "EASY";
                else if (btn == normalButton) textToSave = "NORMAL";
                else if (btn == hardButton) textToSave = "HARD";
            }

            // 2. Gọi hàm tải ảnh (Hàm này sẽ lén reset font về 14)
            Button_Icon.applyCachedIcons(btn, "", styleKey);
            
            // 3. VẢ LẠI NGAY LẬP TỨC: Trả lại chữ, màu, và ép cứng lại cái FONT TO
            btn.setText(textToSave);
            btn.setForeground(txtColor);
            btn.setFont(fontToSave);
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
        javax.swing.AbstractButton[] btns = {africaButton, europeButton, asianButton, easyButton, normalButton, hardButton, exitButton3};

        for (javax.swing.AbstractButton btn : btns) {
            java.awt.GridBagConstraints gbc = layout.getConstraints(btn);
            gbc.fill = java.awt.GridBagConstraints.NONE; // Cấm tiệt việc tự kéo dãn nút
            gbc.ipadx = 0; // Xóa sạch cái lề ảo 150px mà NetBeans tự nhét vào
            layout.setConstraints(btn, gbc);
        }

        // BƯỚC 1: TRUYỀN CHUỖI RỖNG "" ĐỂ HÀM applyCachedIcons KHÔNG VẼ CHỮ LÊN ẢNH NỮA
        Button_Icon.applyCachedIcons(africaButton, "", "DARK_BTN");
        Button_Icon.applyCachedIcons(europeButton, "", "DARK_BTN");
        Button_Icon.applyCachedIcons(asianButton, "", "DARK_BTN");
        Button_Icon.applyCachedIcons(easyButton, "", "LIGHT_BTN");
        Button_Icon.applyCachedIcons(normalButton, "", "LIGHT_BTN");
        Button_Icon.applyCachedIcons(hardButton, "", "LIGHT_BTN");

        // BƯỚC 2: TÙY CHỈNH LẠI FONT CHỮ VÀ ÉP NÓ NẰM ĐÈ LÊN GIỮA ẢNH
        javax.swing.JButton[] levelBtns = {africaButton, europeButton, asianButton, easyButton, normalButton, hardButton};
        java.awt.Font levelFont = new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 24); // Đổi size 24 ở đây

        for (javax.swing.JButton btn : levelBtns) {
            btn.setFont(levelFont); // Set font to đùng
            
            // Ép chữ phải nằm chính giữa nút (đè ngay giữa bức ảnh)
            btn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
            btn.setVerticalTextPosition(javax.swing.SwingConstants.CENTER);
            
            // Xóa cái nền viền xám mặc định của nút Java để hiển thị ảnh cho đẹp
            btn.setContentAreaFilled(false);
            btn.setFocusPainted(false);
            btn.setBorderPainted(false);
            btn.setOpaque(false);
        }

        // BƯỚC 3: DÙNG setText() ĐỂ GÁN LẠI CHỮ. LÚC NÀY FONT VÀ MÀU SẼ CÓ TÁC DỤNG!
        africaButton.setText("AFRICAN");
        europeButton.setText("EUROPEAN");
        asianButton.setText("ASIAN");
        easyButton.setText("EASY");
        normalButton.setText("NORMAL");
        hardButton.setText("HARD");
        
        
        
       // ---ĐỘ NÚT < THÀNH BO GÓC TRONG SUỐT ---
        exitButton3.setContentAreaFilled(false);
        exitButton3.setFocusPainted(false);
        exitButton3.setBorderPainted(false);
        exitButton3.setOpaque(false);
        exitButton3.setRolloverEnabled(true); 
        exitButton3.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 18)); 

        exitButton3.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
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
        // --- KẾT THÚC ĐỘ NÚT ---
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
        easyButton = new javax.swing.JButton();
        normalButton = new javax.swing.JButton();
        hardButton = new javax.swing.JButton();
        darkPanel = new javax.swing.JPanel();
        africaButton = new javax.swing.JButton();
        europeButton = new javax.swing.JButton();
        asianButton = new javax.swing.JButton();

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

        easyButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        easyButton.setText("DỄ");
        easyButton.setPreferredSize(new java.awt.Dimension(250, 60));
        easyButton.addActionListener(this::easyButtonActionPerformed);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 232;
        gridBagConstraints.ipady = 33;
        gridBagConstraints.insets = new java.awt.Insets(100, 20, 0, 20);
        lightPanel.add(easyButton, gridBagConstraints);

        normalButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        normalButton.setText("THƯỜNG");
        normalButton.setPreferredSize(new java.awt.Dimension(250, 60));
        normalButton.addActionListener(this::normalButtonActionPerformed);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 210;
        gridBagConstraints.ipady = 33;
        gridBagConstraints.insets = new java.awt.Insets(60, 20, 0, 20);
        lightPanel.add(normalButton, gridBagConstraints);

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
        gridBagConstraints.insets = new java.awt.Insets(60, 20, 150, 20);
        lightPanel.add(hardButton, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(125, 213, 0, 0);
        getContentPane().add(lightPanel, gridBagConstraints);

        darkPanel.setMinimumSize(new java.awt.Dimension(350, 550));
        darkPanel.setPreferredSize(new java.awt.Dimension(350, 550));
        darkPanel.setLayout(new java.awt.GridBagLayout());

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
        gridBagConstraints.insets = new java.awt.Insets(100, 20, 0, 20);
        darkPanel.add(africaButton, gridBagConstraints);

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
        gridBagConstraints.insets = new java.awt.Insets(60, 20, 0, 20);
        darkPanel.add(europeButton, gridBagConstraints);

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
        gridBagConstraints.insets = new java.awt.Insets(60, 20, 150, 20);
        darkPanel.add(asianButton, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(125, 0, 0, 220);
        getContentPane().add(darkPanel, gridBagConstraints);

        setSize(new java.awt.Dimension(1214, 808));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void africaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_africaButtonActionPerformed
        // TODO add your handling code here:
        //SoundLoad audioManager = new SoundLoad();
        audioManager.playTransitionSound("/sound/SoundTap/NextScreen.wav");
        start.setLevel("AFRICAN");
        start.UpdateLevel("AFRICAN");
        start.setGameTheme(this.selectedSide);
        start.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_africaButtonActionPerformed

    private void asianButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_asianButtonActionPerformed
        // TODO add your handling code here:
        //SoundLoad audioManager = new SoundLoad();
        audioManager.playTransitionSound("/sound/SoundTap/NextScreen.wav");
        start.setLevel("ASIAN");
        start.UpdateLevel("ASIAN");
        start.setGameTheme(this.selectedSide);
        start.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_asianButtonActionPerformed

    private void europeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_europeButtonActionPerformed
        // TODO add your handling code here:
        //SoundLoad audioManager = new SoundLoad();
        audioManager.playTransitionSound("/sound/SoundTap/NextScreen.wav");
        start.setLevel("EUROPEAN");
        start.UpdateLevel("EUROPEAN");
        start.setGameTheme(this.selectedSide);
        start.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_europeButtonActionPerformed

    private void exitButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitButton3ActionPerformed
        // TODO add your handling code here:
        // 1. Thêm tiếng click chuột
        //SoundLoad audioManager = new SoundLoad();
        audioManager.playSoundEffect("/sound/SoundTap/tap.wav"); // <--- NHỚ THAY TÊN FILE

        // 2. Kiểm tra trạng thái hiện tại
        if (!this.selectedSide.equals("NONE")) {

            this.selectedSide = "NONE";

            // Hiện lại cả 2 Panel
            lightPanel.setVisible(true);
            darkPanel.setVisible(true);

            // Ẩn tất cả các nút Level
            setButtonsVisibility(false);

            // Trả lại ảnh nền mặc định
            currentBg = ThemeManager.getBackgroundImage(ThemeManager.SIDE_DEFAULT);

            // Trả lại màu viền vàng mặc định
            currentBorderColor = new java.awt.Color(255, 215, 0);

            // Reset Layout để 2 Panel quay về vị trí cũ (ở giữa, 2 bên)
            java.awt.GridBagLayout mainLayout = (java.awt.GridBagLayout) getContentPane().getLayout();

            // Cấu hình vị trí cho Light Panel (bên trái)
            java.awt.GridBagConstraints lightGbc = new java.awt.GridBagConstraints();
            lightGbc.gridx = 1;
            lightGbc.gridy = 0;
            lightGbc.insets = new java.awt.Insets(125, 213, 0, 0); // Lấy lại thông số insets cũ
            mainLayout.setConstraints(lightPanel, lightGbc);
            lightPanel.setPreferredSize(new java.awt.Dimension(350, 550)); // Kích thước ban đầu

            // Cấu hình vị trí cho Dark Panel (bên phải)
            java.awt.GridBagConstraints darkGbc = new java.awt.GridBagConstraints();
            darkGbc.gridx = 2;
            darkGbc.gridy = 0;
            darkGbc.insets = new java.awt.Insets(125, 0, 0, 220); // Lấy lại thông số insets cũ
            mainLayout.setConstraints(darkPanel, darkGbc);
            darkPanel.setPreferredSize(new java.awt.Dimension(350, 550)); // Kích thước ban đầu

            this.revalidate();
            this.repaint();

        } else {
            // NẾU CHƯA CHỌN PHE (ĐANG Ở MÀN HÌNH CHỌN PHE): Thoát hẳn về StartScreen
            this.dispose();
            if (start != null) {
                start.setVisible(true);
            }
        }
    }//GEN-LAST:event_exitButton3ActionPerformed

    private void easyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_easyButtonActionPerformed
        // TODO add your handling code here:
        //SoundLoad audioManager = new SoundLoad();
        audioManager.playTransitionSound("/sound/SoundTap/NextScreen.wav");
        start.setLevel("EASY");
        start.UpdateLevel("EASY");
        start.setGameTheme(this.selectedSide);
        start.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_easyButtonActionPerformed

    private void normalButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_normalButtonActionPerformed
        // TODO add your handling code here:
        //SoundLoad audioManager = new SoundLoad();
        audioManager.playTransitionSound("/sound/SoundTap/NextScreen.wav");
        start.setLevel("NORMAL");
        start.UpdateLevel("NORMAL");
        start.setGameTheme(this.selectedSide);
        start.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_normalButtonActionPerformed

    private void hardButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hardButtonActionPerformed
        // TODO add your handling code here:
        //SoundLoad audioManager = new SoundLoad();
        audioManager.playTransitionSound("/sound/SoundTap/NextScreen.wav");
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
