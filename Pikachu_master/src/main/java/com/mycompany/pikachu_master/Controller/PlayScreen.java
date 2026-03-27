/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pikachu_master.Controller;

import com.mycompany.pikachu_master.Algorithm.ClassicAlgorithm;
import com.mycompany.pikachu_master.Algorithm.IAlgorithm;
import com.mycompany.pikachu_master.Algorithm.MediumModeAlgorithm;
import com.mycompany.pikachu_master.Model.Board;
import com.mycompany.pikachu_master.Model.Cell;
import com.mycompany.pikachu_master.Model.CellPair;

import com.mycompany.pikachu_master.User_Interface.Components.RoundedIconButton;
import com.mycompany.pikachu_master.User_Interface.Screens.HonorScreen;
import com.mycompany.pikachu_master.User_Interface.Screens.MainScreen;
import com.mycompany.pikachu_master.Utils.ImageLoad;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Currency;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.BorderFactory;

/**
 *
 * @author DELL
 */
public class PlayScreen extends JPanel implements ActionListener {

    private final Board board;
    private IAlgorithm algorithm;
    private final RoundedIconButton[][] btnMatrix;
    private Cell firstClick = null;
    private GameConfig config;
    private int rows, cols, timelimit, tileSize, NoP;
    private int remainingTiles;
    private RoundedIconButton firstClickBtn;

    // --- BIẾN CHO CHẾ ĐỘ ASIAN (TÀNG HÌNH) ---
    private javax.swing.Timer asianTimer;
    private int asianTick = 0;
    private boolean isHiddenPhase = false;

    // --- BIẾN KHOÁ CHUỘT KHI ĐANG HIỆN HÌNH SAI ---
    private boolean isProcessingMismatch = false;

    public PlayScreen(GameConfig config) {
        this.config = config;
        this.rows = config.GetRows();
        this.cols = config.GetCols();
        board = new Board(this.rows, this.cols, true);
        this.timelimit = config.GetTimeLimit();
        this.tileSize = 55;
        //this.remainingTiles = this.rows * this.cols;
        // Đếm chính xác số lượng Pikachu thực tế xuất hiện trên bảng

        if (config.GetLevel().equals("AFICA")) {
            this.algorithm = new ClassicAlgorithm();
            this.NoP = 12;
            board.initBoard(algorithm, NoP);
        } else if (config.GetLevel().equals("EUROPE")) {
            this.algorithm = new ClassicAlgorithm();
            this.NoP = 16;
            board.initHardBoard(algorithm, NoP, true);
        } else if (config.GetLevel().equals("ASIAN")) {
            this.algorithm = new MediumModeAlgorithm();
            this.NoP = 18;
            board.initHardBoard(algorithm, NoP, true);

            // --- KHỞI TẠO ĐỒNG HỒ TÀNG HÌNH CHO ASIAN ---
            asianTimer = new javax.swing.Timer(1000, new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    asianTick++;
                    if (!isHiddenPhase && asianTick >= 10) {
                        isHiddenPhase = true;
                        asianTick = 0;
                        updateAllButtons();
                    } else if (isHiddenPhase && asianTick >= 5) {
                        isHiddenPhase = false;
                        asianTick = 0;
                        updateAllButtons();
                    }
                }
            });
            asianTimer.start();
        } else if (config.GetLevel().equals("Start")) {
            this.algorithm = new ClassicAlgorithm();
            this.NoP = 6;
            board.initBoard(algorithm, NoP);
        }

        this.remainingTiles = 0;
        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= cols; j++) {
                if (board.getCell(i, j).isStatus()) {
                    this.remainingTiles++;
                }
            }
        }

        setLayout(new GridLayout(rows, cols, 0, 0));
//        this.setPreferredSize(new Dimension(cols * tileSize, rows * tileSize));
//        this.setMaximumSize(new Dimension(cols * tileSize, rows * tileSize));
//        this.setMinimumSize(new Dimension(cols * tileSize, rows * tileSize));

        this.setOpaque(false); // Dòng này làm cho Panel không còn màu nền xám nữa
        this.setBackground(new Color(0, 0, 0, 0)); // Đảm bảo màu nền hoàn toàn trong suốt
//        board = new Board(config.GetRows(), config.GetCols()); 
//        board.initBoard(algorithm, 20); // Tạo số ngẫu nhiên từ thuật toán chính
        btnMatrix = new RoundedIconButton[rows + 2][cols + 2]; // Bao gồm cả viền trống nếu cần

        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= cols; j++) {
                int id = board.getCell(i, j).getId();
                ImageIcon icon = ImageLoad.getImage(id);
                btnMatrix[i][j] = new RoundedIconButton(icon, 0);
                // btnMatrix[i][j].setBorder(BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));                   
                btnMatrix[i][j].addActionListener(this);

                add(btnMatrix[i][j]);
            }
        }
    }

    private void updateAllButtons() {
        for (int i = 1; i <= this.rows; i++) {
            for (int j = 1; j <= this.cols; j++) {
                Cell cell = board.getCell(i, j);
                RoundedIconButton btn = btnMatrix[i][j];

                if (cell.isStatus()) {
                    //tàng hình nè 
                    if (isHiddenPhase) {
                        btn.setIcon(null);
                    } else {
                        btn.setIcon(ImageLoad.getImage(cell.getId()));
                    }
                    // Cập nhật lại Icon vì sau khi dồn, ID tại vị trí (i,j) đã thay đổi
                    // btn.setIcon(ImageLoad.getImage(cell.getId()));
                    btn.setVisible(true);
                } else {
                    // Ô trống thì ẩn đi
                    btn.setVisible(false);
                }

                // Quan trọng: Reset trạng thái chọn để tránh lỗi hiển thị viền vàng cũ
                btn.setSelectedState(false);
            }
        }
        // Vẽ lại giao diện sau khi thay đổi hàng loạt
        this.revalidate();
        this.repaint();
    }

    public void shuffle() {
        algorithm.shuffle(board);

        // --- ÉP HIỆN HÌNH LẠI KHI ĐẢO MAP ---
        if (config.GetLevel().equals("ASIAN")) {
            isHiddenPhase = false;
            asianTick = 0;
        }

        updateAllButtons();
        firstClick = null;
        firstClickBtn = null;
    }

    public void findHint() {
        CellPair hintPair = algorithm.findHint(board);

        if (hintPair != null) {
            Cell c1 = hintPair.getCell1();
            Cell c2 = hintPair.getCell2();

            RoundedIconButton btn1 = btnMatrix[c1.getX()][c1.getY()];
            RoundedIconButton btn2 = btnMatrix[c2.getX()][c2.getY()];

            // Gọi hàm nhấp nháy
            blinkHint(btn1, btn2);
        } else {
            System.out.println("Khong con cap nao de an.");
            // shuffle(); // Mở comment này nếu muốn hết đường là tự động đảo
        }
    }

    // Hiệu ứng nhấp nháy 2 ô
    private void blinkHint(RoundedIconButton b1, RoundedIconButton b2) {
        javax.swing.Timer blinkTimer = new javax.swing.Timer(300, new java.awt.event.ActionListener() {
            int count = 0;
            boolean isOn = false;

            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                isOn = !isOn; // Đảo trạng thái (Bật/Tắt)
                b1.setSelectedState(isOn);
                b2.setSelectedState(isOn);
                count++;

                // Nháy 6 nhịp (tầm gần 2 giây) thì dừng hẳn
                if (count >= 6) {
                    ((javax.swing.Timer) e.getSource()).stop();
                    b1.setSelectedState(false);
                    b2.setSelectedState(false);
                }
            }
        });
        blinkTimer.start();
    }
    // ---> KẾT THÚC SỬA <---

    @Override
    public void actionPerformed(ActionEvent e) {
        //Lúc đang chờ giấu hình thì không cho bấm
        if (isProcessingMismatch) {
            return;
        }

        RoundedIconButton clickedBtn = (RoundedIconButton) e.getSource();
//        int r = clickedBtn.getxCoord();
//        int c = clickedBtn.getyCoord();
        int r = -1, c = -1;
        for (int i = 1; i <= this.rows; i++) {
            for (int j = 1; j <= this.cols; j++) {
                if (btnMatrix[i][j] == clickedBtn) {
                    r = i;
                    c = j;
                    break;
                }
            }
        }
        if (r == -1 || c == -1) {
            return;
        }
        Cell currentCell = board.getCell(r, c);
        if (!currentCell.isStatus()) {
            return;
        }
       
// lần click đầu tiên
        if (firstClick == null) {
            firstClick = currentCell;
            firstClickBtn = clickedBtn;
            clickedBtn.setSelectedState(true);

            // --- CHO XEM LÉN KHI CLICK (Đang tàng hình) ---
            if (isHiddenPhase) {
                clickedBtn.setIcon(ImageLoad.getImage(currentCell.getId()));
            }
        } else {
            //lần click thứ 2
            //RoundedIconButton firstBtn = btnMatrix[firstClick.getX()][firstClick.getY()];
            RoundedIconButton firstBtn = firstClickBtn;
            if (isHiddenPhase) {
                clickedBtn.setIcon(ImageLoad.getImage(currentCell.getId()));
            }
            // Nếu click lại chính ô vừa chọn thì hủy chọn
            if (firstClick == currentCell) {
                firstBtn.setSelectedState(false);
                if (isHiddenPhase) {
                    firstBtn.setIcon(null);
                }
                firstClick = null;
                firstClickBtn = null;
                return;
            }
            if (firstClick.getId() == currentCell.getId() && algorithm.checkPath(board, firstClick, currentCell)) {
                // Ăn được: Ẩn cả 2 nút
                java.awt.Window window = javax.swing.SwingUtilities.getWindowAncestor(this);
                if (window instanceof MainScreen) {
                    MainScreen main = (MainScreen) window;
                    java.awt.Component glassPane = main.getGlassPane();
                    if (glassPane instanceof com.mycompany.pikachu_master.User_Interface.Components.PathOverlay) {
                        com.mycompany.pikachu_master.User_Interface.Components.PathOverlay overlay = 
                            (com.mycompany.pikachu_master.User_Interface.Components.PathOverlay) glassPane;
                        
                        // Truyền đường đi vào để vẽ
                        overlay.showPath(algorithm.getPath(), this.getBounds(), this.rows, this.cols);
                    }
                }

                // Ghi nhớ ID của cặp vừa ăn trước khi xóa nó
                int matchedId = firstClick.getId();

                // Xóa cặp hình vừa ăn (dù là Pikachu thường hay Tên lửa)
                algorithm.removePair(firstClick, currentCell, board);
                firstBtn.setVisible(false);
                clickedBtn.setVisible(false);
                firstBtn.setSelectedState(false);
                updateAllButtons();

                // ---> KIỂM TRA: CÓ PHẢI VỪA ĂN TÊN LỬA KHÔNG? <---
                int ROCKET_ITEM_ID = 1; // LƯU Ý: THAY SỐ NÀY BẰNG ID THỰC TẾ CỦA ITEM TÊN LỬA TRONG GAME CỦA BẠN
                
                if (matchedId == ROCKET_ITEM_ID) {
                    // Nếu là tên lửa, gọi hàm hoạt họa bay tới đập cặp khác
                    triggerRocketEffect(firstBtn, clickedBtn);
                } else {
                    // Nếu là Pikachu bình thường, chạy logic check thắng / xáo trộn như cũ
                    if (isBoardEmpty()) {
                        showHonorScreen();
                    } else {
                        if (algorithm.hasAnyMatch(board) == false) {
                            algorithm.shuffle(board);
                            if (config.GetLevel().equals("ASIAN")) {
                                isHiddenPhase = false;
                                asianTick = 0;
                            }
                            updateAllButtons();
                        }
                    }
                }

                firstClick = null;
                firstClickBtn = null;
             // ... (phần code xử lý chọn sai - isProcessingMismatch của bạn giữ nguyên không sửa gì ở đây) ...
            } else {

                isProcessingMismatch = true;
                // Tạo một Timer đếm ngược 0.75 giây (750 mili giây)
                javax.swing.Timer delayTimer = new javax.swing.Timer(750, new java.awt.event.ActionListener() {
                    @Override
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        firstBtn.setSelectedState(false); // Tắt viền vàng
                        clickedBtn.setSelectedState(false);

                        // Nếu đang tàng hình thì giấu ảnh đi
                        if (isHiddenPhase) {
                            firstBtn.setIcon(null);
                            clickedBtn.setIcon(null);
                        }

                        isProcessingMismatch = false; // Mở khoá chuột lại
                        ((javax.swing.Timer) evt.getSource()).stop(); // Chạy 1 lần rồi tắt timer
                    }
                });
                delayTimer.setRepeats(false);
                delayTimer.start();
                firstClick = null;
                firstClickBtn = null;
            }
        }
    }

    // Hàm quét thực tế: Còn ô nào sống không?
    private boolean isBoardEmpty() {
        for (int i = 1; i <= this.rows; i++) {
            for (int j = 1; j <= this.cols; j++) {
                if (board.getCell(i, j).isStatus()) {
                    return false; // Vẫn còn ít nhất 1 ô sống
                }
            }
        }
        return true; // Bảng đã trống trơn 100%
    }

    private void showHonorScreen() {
        java.awt.Window windown = javax.swing.SwingUtilities.getWindowAncestor(this);
        if (windown instanceof MainScreen) {
            MainScreen main = (MainScreen) windown;
            main.stopTimer();
            main.setEnabled(false);
            HonorScreen honorScreen = new HonorScreen(main, config);
            honorScreen.setAlwaysOnTop(true);
            honorScreen.setVisible(true);
        }
    }

    // --- 3 HÀM ĐỂ MAIN SCREEN ĐIỀU KHIỂN TÀNG HÌNH KHI PAUSE/RESTART ---
    public void pauseAsianTimer() {
        if (asianTimer != null && asianTimer.isRunning()) {
            asianTimer.stop();
        }
    }

    public void resumeAsianTimer() {
        if (asianTimer != null && !asianTimer.isRunning() && config.GetLevel().equals("ASIAN")) {
            asianTimer.start();
        }
    }

    public void stopAsianTimer() {
        if (asianTimer != null) {
            asianTimer.stop();
        }
    }
////XU LY BAN TEN LUA HOAT HOA////
    
    public void triggerRocketEffect(RoundedIconButton startBtn1, RoundedIconButton startBtn2) {
        // 1. Tìm 1 cặp Pikachu ngẫu nhiên trên bàn để làm mục tiêu
        CellPair targetPair = algorithm.findHint(board);
        if (targetPair == null) {
            if (isBoardEmpty()) showHonorScreen();
            return; 
        }

        Cell tCell1 = targetPair.getCell1();
        Cell tCell2 = targetPair.getCell2();
        RoundedIconButton targetBtn1 = btnMatrix[tCell1.getX()][tCell1.getY()];
        RoundedIconButton targetBtn2 = btnMatrix[tCell2.getX()][tCell2.getY()];

        java.awt.Window window = javax.swing.SwingUtilities.getWindowAncestor(this);
        if (!(window instanceof MainScreen)) return;
        MainScreen main = (MainScreen) window;

        
        int ROCKET_ID = 1; // ID của item tên lửa
        javax.swing.ImageIcon originalIcon = ImageLoad.getImage(ROCKET_ID); 
        java.awt.Image img = originalIcon.getImage().getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
        javax.swing.ImageIcon rocketIcon = new javax.swing.ImageIcon(img);

        javax.swing.JLabel rocket1 = new javax.swing.JLabel(rocketIcon);
        javax.swing.JLabel rocket2 = new javax.swing.JLabel(rocketIcon);
        rocket1.setSize(40, 40);
        rocket2.setSize(40, 40);

        // 3. Tính toán tọa độ xuất phát (Từ 2 ô tên lửa vừa ăn)
        java.awt.Point s1 = javax.swing.SwingUtilities.convertPoint(this, startBtn1.getLocation(), main.getContentPane());
        java.awt.Point s2 = javax.swing.SwingUtilities.convertPoint(this, startBtn2.getLocation(), main.getContentPane());
        
        // Tính toán tọa độ đích (Đến 2 ô mục tiêu)
        java.awt.Point t1 = javax.swing.SwingUtilities.convertPoint(this, targetBtn1.getLocation(), main.getContentPane());
        java.awt.Point t2 = javax.swing.SwingUtilities.convertPoint(this, targetBtn2.getLocation(), main.getContentPane());

        rocket1.setLocation(s1.x, s1.y);
        rocket2.setLocation(s2.x, s2.y);

        main.getLayeredPane().add(rocket1, javax.swing.JLayeredPane.DRAG_LAYER);
        main.getLayeredPane().add(rocket2, javax.swing.JLayeredPane.DRAG_LAYER);

        // 4. Bật Timer để tạo hoạt họa bay
        isProcessingMismatch = true; // Khóa không cho bấm bậy bạ lúc tên lửa đang bay
        int totalFrames = 35; // khung hình bay
        int delayPerFrame = 15; // Mỗi khung 15 mili-giây

        javax.swing.Timer animTimer = new javax.swing.Timer(delayPerFrame, null);
        animTimer.addActionListener(new java.awt.event.ActionListener() {
            int currentFrame = 0;

            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                currentFrame++;
                float progress = (float) currentFrame / totalFrames;

                // Cập nhật vị trí tên lửa bay dần đến mục tiêu
                rocket1.setLocation((int) (s1.x + (t1.x - s1.x) * progress), (int) (s1.y + (t1.y - s1.y) * progress));
                rocket2.setLocation((int) (s2.x + (t2.x - s2.x) * progress), (int) (s2.y + (t2.y - s2.y) * progress));

                // Khi tên lửa chạm đích
                if (currentFrame >= totalFrames) {
                    animTimer.stop();
                    
                    // Xóa ảnh tên lửa khỏi màn hình
                    main.getLayeredPane().remove(rocket1);
                    main.getLayeredPane().remove(rocket2);
                    main.getLayeredPane().repaint();

                    // Tiêu diệt cặp mục tiêu
                    algorithm.removePair(tCell1, tCell2, board);
                    targetBtn1.setVisible(false);
                    targetBtn2.setVisible(false);
                    targetBtn1.setSelectedState(false);
                    targetBtn2.setSelectedState(false);
                    updateAllButtons();

                    isProcessingMismatch = false; // Mở khóa chuột

                    // Kiểm tra thắng hoặc hết đường đi
                    if (isBoardEmpty()) {
                        showHonorScreen();
                    } else if (!algorithm.hasAnyMatch(board)) {
                        shuffle();
                        updateAllButtons();
                    }
                }
            }
        });
        animTimer.start();
    }
}
