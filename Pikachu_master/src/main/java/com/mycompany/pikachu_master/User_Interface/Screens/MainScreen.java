/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.pikachu_master.User_Interface.Screens;

import com.mycompany.pikachu_master.Controller.GameConfig;
import com.mycompany.pikachu_master.Controller.PlayScreen;
import com.mycompany.pikachu_master.Data.gameDAO;
import com.mycompany.pikachu_master.Effect.PathOverlay;
import com.mycompany.pikachu_master.Model.LevelType;
import com.mycompany.pikachu_master.User_Interface.Components.BackgroundMain;
import com.mycompany.pikachu_master.User_Interface.Components.BackgroundStartScreen;
import com.mycompany.pikachu_master.Utils.SoundLoad;
import java.awt.event.ActionEvent;
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
    gameDAO DTB;

    int hintCount;
    int shuffleCount;
    private int displayedScore = 0;
    private int currentTotalScore = 0;
    private int displayedCoin = 0;
    private int currentTotalCoin = 0;

    private SoundLoad audioManager = new SoundLoad();
    
    public MainScreen(GameConfig config, LevelType level) {
        initComponents();
        this.setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        this.config = config;
        this.level = level;
        this.play = new PlayScreen(config, this);
        this.hintCount = 3;
        this.shuffleCount = 3;
        this.DTB = new gameDAO();

        // --- THÊM CỤM NÀY ĐỂ MÀN CHƠI TỰ CHỌN ĐÚNG NỀN ---
        javax.swing.JPanel dynamicBg = new javax.swing.JPanel() {
            @Override
            protected void paintComponent(java.awt.Graphics g) {
                super.paintComponent(g);
                // Tự động lấy đúng ảnh Ánh Sáng hoặc Bóng Tối
                java.awt.Image bg = com.mycompany.pikachu_master.Model.ThemeManager.getBackgroundImage(com.mycompany.pikachu_master.Model.ThemeManager.currentTheme);
                if (bg != null) {
                    g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        setContentPane(dynamicBg);
        this.setUndecorated(true);
        initComponents();
        this.setMinimumSize(new java.awt.Dimension(800, 600));
        play.initTimer(this.Timeline);

        // KÍCH HOẠT LỚP VẼ ĐƯỜNG ĐI
        PathOverlay pathOverlay = new PathOverlay();
        this.setGlassPane(pathOverlay);
        pathOverlay.setVisible(true);

        Timeline.setOpaque(false);
        Timeline.setEnabled(false);
        styleTopBar();
        this.getContentPane().setLayout(null);
        this.getContentPane().add(play);

        playMusicByLevel();
        resetScoreDisPlay();
        resetCoinDisplay(play.get_Totalcoin());

        this.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent evt) {
                updateGameLayout(level);
            }
        });
        // Lấy trước kích thước màn hình PC của bạn
        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(screenSize); // Ép kích thước ngay lập tức

        // Ra lệnh dàn layout TRƯỚC KHI màn hình được setVisible
        updateGameLayout(level);
    }
    
    
private void styleTopBar() {
        // --- 1. KHỞI TẠO LẠI LABEL VỚI NỀN GIỐNG TOPBAR (MÀU ĐEN MỜ) ---
        topBarPanel.remove(coin);
        topBarPanel.remove(ScoreLabel);

        coin = new javax.swing.JLabel("VÀNG: 0") {
            @Override
            protected void paintComponent(java.awt.Graphics g) {
                java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();
                g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
                // Nền đen mờ (giống hệt nền của topBarPanel)
                g2.setColor(new java.awt.Color(0, 0, 0, 150)); 
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.dispose();
                super.paintComponent(g); 
            }
        };

        ScoreLabel = new javax.swing.JLabel("ĐIỂM: 0") {
            @Override
            protected void paintComponent(java.awt.Graphics g) {
                java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();
                g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
                // Nền đen mờ (giống hệt nền của topBarPanel)
                g2.setColor(new java.awt.Color(0, 0, 0, 150)); 
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.dispose();
                super.paintComponent(g);
            }
        };

        // --- 2. CHỈNH MÀU CHỮ VÀ ĐỘ ĐẬM ---
        levelLabel.setForeground(new java.awt.Color(255, 255, 255)); 
        
        // Chữ VÀNG: Màu vàng đồng, cho font BOLD đậm lên
        coin.setForeground(new java.awt.Color(255, 255, 0)); 
        coin.setFont(coin.getFont().deriveFont(java.awt.Font.BOLD));

        // Chữ ĐIỂM: Đổi thành màu ĐỎ ĐẬM
        ScoreLabel.setForeground(new java.awt.Color(255, 51, 51)); 
        ScoreLabel.setFont(ScoreLabel.getFont().deriveFont(java.awt.Font.BOLD));

        // --- 3. BO VIỀN MÀU VÀNG RÕ NÉT ---
        javax.swing.border.Border goldBorder = new javax.swing.border.AbstractBorder() {
            @Override
            public void paintBorder(java.awt.Component c, java.awt.Graphics g, int x, int y, int width, int height) {
                java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();
                g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
                // Màu vàng Gold rõ nét
                g2.setColor(new java.awt.Color(255, 215, 0)); 
                g2.setStroke(new java.awt.BasicStroke(2.5f)); // Viền dày 2.5 cho rõ
                g2.drawRoundRect(x, y, width - 1, height - 1, 20, 20);
                g2.dispose();
            }
            @Override
            public java.awt.Insets getBorderInsets(java.awt.Component c) {
                return new java.awt.Insets(4, 15, 4, 15);
            }
        };

        coin.setBorder(goldBorder);
        ScoreLabel.setBorder(goldBorder);
        coin.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ScoreLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        coin.setOpaque(false);
        ScoreLabel.setOpaque(false);

        // --- 4. NỀN KÍNH MỜ CHO TOPBAR (GIỮ NGUYÊN) ---
        topBarPanel.setOpaque(false);
        topBarPanel.setBorder(new javax.swing.border.AbstractBorder() {
            @Override
            public void paintBorder(java.awt.Component c, java.awt.Graphics g, int x, int y, int width, int height) {
                java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();
                g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new java.awt.Color(0, 0, 0, 150));
                g2.fillRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
                g2.setColor(java.awt.Color.WHITE);
                g2.setStroke(new java.awt.BasicStroke(1.5f));
                g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
                g2.dispose();
            }
        });

        // --- 5. TÙY CHỈNH THANH THỜI GIAN & NÚT (GIỮ NGUYÊN LAYOUT CŨ CỦA BẠN) ---
        Timeline.setOpaque(false);
        Timeline.setUI(new javax.swing.plaf.basic.BasicSliderUI(Timeline) {
            @Override
            public void paintTrack(java.awt.Graphics g) {
                java.awt.Graphics2D g2d = (java.awt.Graphics2D) g;
                g2d.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
                java.awt.Rectangle t = trackRect;
                g2d.setColor(new java.awt.Color(50, 50, 50, 200));
                int trackHeight = 12;
                g2d.fillRoundRect(t.x, t.y + (t.height - trackHeight) / 2, t.width, trackHeight, trackHeight, trackHeight);
                int fillWidth = (Timeline.getMaximum() > 0) ? (int) (t.width * ((double) Timeline.getValue() / Timeline.getMaximum())) : 0;
                double percent = (double) Timeline.getValue() / Timeline.getMaximum();
                if (percent < 0.25) g2d.setColor(new java.awt.Color(255, 60, 60));
                else if (percent < 0.5) g2d.setColor(new java.awt.Color(255, 165, 0));
                else g2d.setColor(new java.awt.Color(0, 220, 100));
                g2d.fillRoundRect(t.x, t.y + (t.height - trackHeight) / 2, fillWidth, trackHeight, trackHeight, trackHeight);
            }
            @Override public void paintThumb(java.awt.Graphics g) {}
        });

        javax.swing.AbstractButton[] topBtns = {settingmainButton, hintButton, swapButton, timeButton};
        String[] icons = {"⚙️", "💡", "🔄", "⏳"};
        for (int i = 0; i < topBtns.length; i++) {
            topBtns[i].setText(icons[i]);
            topBtns[i].setFont(new java.awt.Font("Segoe UI Emoji", java.awt.Font.PLAIN, 18));
            topBtns[i].setContentAreaFilled(false);
            topBtns[i].setFocusPainted(false);
            topBtns[i].setBorderPainted(false);
            topBtns[i].setOpaque(false);
            topBtns[i].setUI(new javax.swing.plaf.basic.BasicButtonUI() {
                @Override
                public void paint(java.awt.Graphics g, javax.swing.JComponent c) {
                    java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();
                    g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
                    javax.swing.AbstractButton b = (javax.swing.AbstractButton) c;
                    if (b.getModel().isPressed()) g2.setColor(new java.awt.Color(200, 200, 200, 255));
                    else if (b.getModel().isRollover()) g2.setColor(new java.awt.Color(255, 255, 255, 255));
                    else g2.setColor(new java.awt.Color(255, 255, 255, 200));
                    int size = Math.min(c.getWidth(), c.getHeight()) - 4;
                    g2.fillOval((c.getWidth() - size) / 2, (c.getHeight() - size) / 2, size, size);
                    g2.setColor(c == settingmainButton ? new java.awt.Color(255, 180, 0) : new java.awt.Color(255, 100, 80, 180));
                    g2.setStroke(new java.awt.BasicStroke(c == settingmainButton ? 2.0f : 1.5f));
                    g2.drawOval((c.getWidth() - size) / 2, (c.getHeight() - size) / 2, size, size);
                    g2.dispose();
                    super.paint(g, c);
                }
            });
        }

        // --- 6. SẮP XẾP LẠI LAYOUT TRONG PANEL ---
        java.awt.GridBagLayout topLayout = (java.awt.GridBagLayout) topBarPanel.getLayout();
        java.awt.GridBagConstraints gbc = new java.awt.GridBagConstraints();
        
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = java.awt.GridBagConstraints.WEST;
        gbc.insets = new java.awt.Insets(12, 20, 5, 10);
        topBarPanel.add(coin, gbc);

        gbc.gridy = 1; gbc.insets = new java.awt.Insets(5, 20, 12, 10);
        topBarPanel.add(ScoreLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1.0; gbc.anchor = java.awt.GridBagConstraints.CENTER;
        gbc.insets = new java.awt.Insets(10, 0, 5, 0);
        topBarPanel.add(levelLabel, gbc);

        gbc.gridy = 1; gbc.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gbc.insets = new java.awt.Insets(5, 40, 10, 40);
        topBarPanel.add(Timeline, gbc);

        javax.swing.AbstractButton[] tools = {timeButton, swapButton, hintButton, settingmainButton};
        for (int i = 0; i < tools.length; i++) {
            gbc = new java.awt.GridBagConstraints();
            gbc.gridx = 2 + i; gbc.gridy = 0; gbc.gridheight = 2;
            gbc.anchor = java.awt.GridBagConstraints.EAST;
            gbc.insets = new java.awt.Insets(10, 5, 10, (i == 3 ? 20 : 5));
            topBarPanel.add(tools[i], gbc);
        }
    }
    public void updateScore(int newScore) {
        this.currentTotalScore = newScore;
        // Tạo hiệu ứng nhảy số từ displayedScore đến currentTotalScore
        javax.swing.Timer ScoreTimer = new javax.swing.Timer(20, null);
        ScoreTimer.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                if (displayedScore < currentTotalScore) {
                    displayedScore += 5; // Tốc độ tăng điểm
                    if (displayedScore > currentTotalScore) {
                        displayedScore = currentTotalScore;
                    }
                    ScoreLabel.setText("ĐIỂM: " + displayedScore);
                } else {
                    ScoreTimer.stop();
                }
            }
        });
        ScoreTimer.start();
    }
    
    public void saveGame() {
        // Gọi sang PlayScreen để lấy dữ liệu hiện tại
        String matrix = play.getMatrixAsString();
        int score = play.get_TotalScore();
        int time = play.get_timeRemain();
        String level = play.get_Level();

        // Lưu vào Database qua DAO
        DTB.saveCurrentGame("tuan", level, score, time, hintCount, shuffleCount, matrix, 1);
        System.out.println("saved current game!");
    }

    public void updateCoin(int newCoin) {
        this.currentTotalCoin = newCoin;

        // Tạo hiệu ứng nhảy số từ displayedCoin đến currentTotalCoin
        javax.swing.Timer ScoreLabel = new javax.swing.Timer(20, null);
        ScoreLabel.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                if (displayedCoin < currentTotalCoin) {
                    displayedCoin += 5; // Tốc độ tăng điểm
                    if (displayedCoin > currentTotalCoin) {
                        displayedCoin = currentTotalCoin;
                    }
                    coin.setText("VÀNG: " + displayedCoin);
                } else {
                    ScoreLabel.stop();
                }
            }
        });
        ScoreLabel.start();
    }

    public void resetCoinDisplay(int currentTotalCoin) {
        this.currentTotalCoin = currentTotalCoin;
        this.displayedCoin = currentTotalCoin;
        coin.setText("VÀNG: " + displayedCoin);
    }

    public void resetScoreDisPlay() {
        this.currentTotalScore = 0;
        this.displayedScore = 0;
        ScoreLabel.setText("ĐIỂM: 0");
    }

//âm thanh    
    void playMusicByLevel() {
        // Lấy tên của Level hiện tại và viết hoa hết để dễ so sánh
        String levelName = config.GetLevel().toUpperCase();

        // --- PHE TỐI ---
        if (levelName.equals("AFRICA")) {
            audioManager.playBGM("/sound/SoundBackgroundDark/SoundAfrica.wav");
        } else if (levelName.equals("ASIAN")) {
            audioManager.playBGM("/sound/SoundBackgroundDark/SoundAsian.wav");
        } else if (levelName.equals("EUROPE")) {
            audioManager.playBGM("/sound/SoundBackgroundDark/SoundEurope.wav");

            // --- PHE SÁNG ---
        } else if (levelName.equals("EASY")) {
            audioManager.playBGM("/sound/SoundBackgroundLight/SoundEasy.wav");
        } else if (levelName.equals("NORMAL")) {
            audioManager.playBGM("/sound/SoundBackgroundLight/SoundNormal.wav");
        } else if (levelName.equals("HARD")) {
            audioManager.playBGM("/sound/SoundBackgroundLight/SoundHard.wav");

            // --- MẶC ĐỊNH (PHÒNG HỜ) ---
        } else {
            audioManager.playBGM("/sound/SoundBackground/SoundStart.wav");
        }
    }

    public void playSoundEffect(String path) {
        System.out.println("MainScreen has signal! Audio is playing: " + path);

        if (audioManager != null) {
            audioManager.playSoundEffect(path);
        } else {
            System.out.println("Error: audioManager not have seen!");
        }
    }
public void updateGameLayout(LevelType level) {
        int screenWidth = getContentPane().getWidth();
        int screenHeight = getContentPane().getHeight();
        if (screenWidth == 0 || screenHeight == 0) return;

        double scaleRatio = Math.max(1.0, (double) screenWidth / 800.0);
        int newTopBarHeight = (int) (80 * scaleRatio);

        // --- CẬP NHẬT FONT ---
        int newFontSize = (int) (15 * scaleRatio);
        java.awt.Font newFont = new java.awt.Font("Segoe UI", java.awt.Font.BOLD, newFontSize);
        ScoreLabel.setFont(newFont);
        coin.setFont(newFont);

        // --- CỐ ĐỊNH KÍCH THƯỚC Ô (CHỨA ĐỦ 7 CHỮ SỐ) ---
        int badgeWidth = (int) (220 * scaleRatio); // Đủ rộng cho "VÀNG: 9.999.999"
        int badgeHeight = (int) (35 * scaleRatio);
        java.awt.Dimension badgeSize = new java.awt.Dimension(badgeWidth, badgeHeight);
        
        coin.setPreferredSize(badgeSize);
        coin.setMinimumSize(badgeSize);
        coin.setMaximumSize(badgeSize); // Ép cứng kích thước không cho co lại
        
        ScoreLabel.setPreferredSize(badgeSize);
        ScoreLabel.setMinimumSize(badgeSize);
        ScoreLabel.setMaximumSize(badgeSize);

        // --- CẬP NHẬT CẤP ĐỘ & TIMELINE ---
        levelLabel.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, (int) (24 * scaleRatio)));
        Timeline.setPreferredSize(new java.awt.Dimension((int) (380 * scaleRatio), (int) (30 * scaleRatio)));

        // --- ÉP KÍCH THƯỚC NÚT ---
        int newBtnSize = (int) (40 * scaleRatio);
        java.awt.Dimension btnDim = new java.awt.Dimension(newBtnSize, newBtnSize);
        javax.swing.JButton[] btns = {timeButton, swapButton, hintButton, settingmainButton};
        for (javax.swing.JButton btn : btns) {
            btn.setPreferredSize(btnDim);
            btn.setMinimumSize(btnDim);
            btn.setFont(btn.getFont().deriveFont((float) (18 * scaleRatio)));
        }

        // --- CĂN CHỈNH TOPBAR ---
        int topBarWidth = Math.min(screenWidth, (int) (800 * scaleRatio));
        topBarPanel.setBounds((screenWidth - topBarWidth) / 2, 10, topBarWidth, newTopBarHeight);

        // --- SCALE BẢNG GAME ---
        int padding = (int) (40 * scaleRatio);
        int maxBW = screenWidth - (padding * 2);
        int maxBH = screenHeight - newTopBarHeight - (padding * 2);
        int tileSize = Math.min(maxBW / level.getCols(), maxBH / level.getRows());
        int bw = Math.max(400, tileSize * level.getCols());
        int bh = Math.max(300, tileSize * level.getRows());
        play.setBounds((screenWidth - bw) / 2, newTopBarHeight + (screenHeight - newTopBarHeight - bh) / 2, bw, bh);

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
        audioManager.stopBGM();
        if (SoundLoad.isBgmOn) {
            playMusicByLevel();
        }
        resetCoinDisplay(play.get_Totalcoin());
        resetScoreDisPlay();
        //xóa bảng hiện tại.
        if (play != null) {
            this.getContentPane().remove(play);
        }
        // tạo bảng mới.
        play = new PlayScreen(config, this);
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
    
    public int getHintCount() {
        return hintCount;
    }

    public int getShuffleCount() {
        return shuffleCount;
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
        ScoreLabel = new javax.swing.JLabel();
        levelLabel = new javax.swing.JLabel();
        Timeline = new javax.swing.JSlider();
        settingmainButton = new javax.swing.JButton();
        hintButton = new javax.swing.JButton();
        swapButton = new javax.swing.JButton();
        timeButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(800, 600));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        topBarPanel.setOpaque(false);
        topBarPanel.setPreferredSize(new java.awt.Dimension(800, 80));
        topBarPanel.setLayout(new java.awt.GridBagLayout());

        coin.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        coin.setText("VÀNG:");
        coin.setPreferredSize(new java.awt.Dimension(100, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 48;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 10, 0, 0);
        topBarPanel.add(coin, gridBagConstraints);

        ScoreLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        ScoreLabel.setText("ĐIỂM:");
        ScoreLabel.setPreferredSize(new java.awt.Dimension(100, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 50;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(8, 10, 0, 0);
        topBarPanel.add(ScoreLabel, gridBagConstraints);

        levelLabel.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        levelLabel.setForeground(new java.awt.Color(255, 255, 51));
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
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 147;
        gridBagConstraints.insets = new java.awt.Insets(5, 191, 0, 0);
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

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 19;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 520, 0);
        getContentPane().add(topBarPanel, gridBagConstraints);

        setSize(new java.awt.Dimension(814, 608));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void settingmainButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_settingmainButtonActionPerformed
        // TODO add your handling code here:
        //Pause Setting = new PauseScreen();
        audioManager.playSoundEffect("/sound/SoundTap/tap.wav");
        play.stopTimer();
        if(config.GetResume() == 1){
            config.setLevel(level.getLevel());
            config.setResume(0);
        }
        PauseScreen pause = new PauseScreen(this, config, level, play);
        pause.setVisible(true);
        saveGame();
        play.countdownTimer.stop();
        // this.dispose();
    }//GEN-LAST:event_settingmainButtonActionPerformed

    private void timeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_timeButtonActionPerformed
//        // TODO add your handling code here:
        audioManager.playSoundEffect("/sound/SoundTap/tap.wav");
        if(level.getLevel().equals("ASIAN")){
        }
        else{
            play.BuyTime();
        }
        resetCoinDisplay(play.get_Totalcoin());
        System.out.println("cong them 5 giay");
    }//GEN-LAST:event_timeButtonActionPerformed

    private void timeButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_timeButtonMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_timeButtonMouseClicked

    private void hintButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hintButtonActionPerformed
        // TODO add your handling code here:
        audioManager.playSoundEffect("/sound/SoundTap/tap.wav");
        if (this.hintCount > 0) {
            this.hintCount--;
            play.findHint();
        } else {
//            ở đây thì làm trò mèo nhá 🐧
        }
    }//GEN-LAST:event_hintButtonActionPerformed

    private void swapButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_swapButtonActionPerformed
        // TODO add your handling code here:
        audioManager.playSoundEffect("/sound/SoundTap/tap.wav");
        if (this.shuffleCount > 0) {
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
    private javax.swing.JLabel ScoreLabel;
    private javax.swing.JSlider Timeline;
    private javax.swing.JLabel coin;
    private javax.swing.JButton hintButton;
    private javax.swing.JLabel levelLabel;
    private javax.swing.JButton settingmainButton;
    private javax.swing.JButton swapButton;
    private javax.swing.JButton timeButton;
    private javax.swing.JPanel topBarPanel;
    // End of variables declaration//GEN-END:variables

}
