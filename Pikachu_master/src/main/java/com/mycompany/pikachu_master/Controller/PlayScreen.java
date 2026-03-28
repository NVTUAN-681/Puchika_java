/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pikachu_master.Controller;

import com.mycompany.pikachu_master.Algorithm.ClassicAlgorithm;
import com.mycompany.pikachu_master.Algorithm.IAlgorithm;
import com.mycompany.pikachu_master.Algorithm.MediumModeAlgorithm;
import com.mycompany.pikachu_master.Effect.RocketAnimation;
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

    // gọi hàm asianmodel;
    private AsianModel asianModel;
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
            asianModel = new AsianModel(this);
            asianModel.start();
            
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

    public void updateAllButtons() {
        boolean isHiddenPhase = asianModel != null && asianModel.isHiddenPhase();
        
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
        if (config.GetLevel().equals("ASIAN") &&asianModel != null) {
           asianModel.reset();
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
        
        boolean isHiddenPhase = asianModel != null && asianModel.isHiddenPhase();
       
// lần click đầu tiên
        if (firstClick == null) {
            firstClick = currentCell;
            firstClickBtn = clickedBtn;
            clickedBtn.setSelectedState(true);
            //boolean isHiddenPhase;

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
                    RocketAnimation.triggerRocketEffect(this, firstBtn, clickedBtn);
                    // Nếu là Pikachu bình thường, chạy logic check thắng / xáo trộn như cũ
                    if (isBoardEmpty()) {
                        showHonorScreen();
                    } else {
                        if (algorithm.hasAnyMatch(board) == false) {
                            algorithm.shuffle(board);
                            if (config.GetLevel().equals("ASIAN") && asianModel != null) {
                               asianModel.reset();
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
    public boolean isBoardEmpty() {
        for (int i = 1; i <= this.rows; i++) {
            for (int j = 1; j <= this.cols; j++) {
                if (board.getCell(i, j).isStatus()) {
                    return false; // Vẫn còn ít nhất 1 ô sống
                }
            }
        }
        return true; // Bảng đã trống trơn 100%
    }

    public void showHonorScreen() {
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
    public void setProcessingMismatch(boolean b) { this.isProcessingMismatch = b; }
    public IAlgorithm getAlgorithm() { return this.algorithm; }
    public Board getBoard() { return this.board; }
    public RoundedIconButton[][] getBtnMatrix() { return this.btnMatrix; }

    public void pauseAsianTimer() {
        if (asianModel != null) asianModel.pause();
    }

    public void resumeAsianTimer() {
        if (asianModel != null && config.GetLevel().equals("ASIAN")) asianModel.resume();
    }

    public void stopAsianTimer() {
        if (asianModel != null) asianModel.stop();
    }
}
