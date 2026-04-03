/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pikachu_master.Controller;

import com.mycompany.pikachu_master.Algorithm.ClassicAlgorithm;
import com.mycompany.pikachu_master.Algorithm.IAlgorithm;
import com.mycompany.pikachu_master.Algorithm.MediumModeAlgorithm;
import com.mycompany.pikachu_master.Effect.RocketAnimation;
import com.mycompany.pikachu_master.Data.ScoreDAO;
import com.mycompany.pikachu_master.Effect.PathOverlay;
import com.mycompany.pikachu_master.Model.AsianModel;
import com.mycompany.pikachu_master.Model.Board;
import com.mycompany.pikachu_master.Model.Cell;
import com.mycompany.pikachu_master.Model.CellPair;
import com.mycompany.pikachu_master.Model.LevelType;
import com.mycompany.pikachu_master.User_Interface.Components.RoundedIconButton;
import com.mycompany.pikachu_master.User_Interface.Screens.HonorScreen;
import com.mycompany.pikachu_master.User_Interface.Screens.LossScreen;
import com.mycompany.pikachu_master.User_Interface.Screens.MainScreen;
import com.mycompany.pikachu_master.Utils.ImageLoad;
import com.mycompany.pikachu_master.Utils.SoundLoad;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author DELL
 */
public class PlayScreen extends JPanel implements ActionListener {

    private final Board board;
    private IAlgorithm algorithm;
    private final RoundedIconButton[][] btnMatrix;
    private Cell firstClick = null;
    private final GameConfig config;
    private LevelType level;
    private MainScreen main;
    private HonorScreen Honor;
    private RoundedIconButton firstClickBtn;
    private final ScoreDAO DTB;
    private AsianModel asianModel;
    private boolean isProcessingMismatch = false;
    private int maxTime;
    private int currentTime;
    private int RewardCount = 0;
    private int TileCount;
    public javax.swing.Timer countdownTimer;
    private javax.swing.JSlider timeline;
    private int score;
    private int TotalScore;
    private int reward;
    private int totalCoin;

    
    

    
    public PlayScreen(GameConfig config, MainScreen main ) {
        this.config = config;
        this.DTB = new ScoreDAO();
        this.main = main;
        this.score = 0;
        this.TotalScore = 0;
        this.reward = 0;
        
//thiết lập thuật toán và cố định level;        
        switch(config.GetLevel()){
            case "AFRICA" -> {
                this.level = LevelType.AFRICA;
                this.algorithm = new ClassicAlgorithm();
            }
            case "EUROPE" -> {
                this.level = LevelType.EUROPE;
                this.algorithm = new ClassicAlgorithm();
            }
            case "ASIAN" -> {
                this.level = LevelType.ASIAN;
                this.algorithm = new MediumModeAlgorithm();
                // --- KHỞI TẠO ĐỒNG HỒ TÀNG HÌNH CHO ASIAN ---
                asianModel = new AsianModel(this);
                asianModel.start();
            }
            case "Start" -> {
                this.level = LevelType.START;
                this.algorithm = new ClassicAlgorithm();
            }
            case "EASY" -> {
                this.level = LevelType.EASY;
                this.algorithm = new ClassicAlgorithm();
            }
            case "MEDIUM" ->{
                this.level = LevelType.MEDIUM;
                this.algorithm = new ClassicAlgorithm();
            }
            case "HARD" ->{
                this.level = LevelType.HARD;
                this.algorithm = new ClassicAlgorithm();
            }
            default -> {
                this.level = LevelType.START;
                this.algorithm = new ClassicAlgorithm();
            }
        }
        
//Thiết lập bảng
//        System.out.println("HardMode: " + this.level.isIsHardMode());
//        System.out.println("rocket: " + this.level.isIsRocket());
//        System.out.println("Algorithm: " + this.algorithm);
//        System.out.println("level: " + this.level.getName());
        this.board = new Board(level.getRows(), level.getCols(),true);
        if(level.isIsHardMode() == true){
            board.initHardBoard(algorithm, level.getNoP(), level.isIsRocket());
        }
        else{
            board.initBoard(algorithm, level.getNoP());
        }
        
        setLayout(new GridLayout(level.getRows(), level.getCols(), 0, 0));
        this.setOpaque(false); // Dòng này làm cho Panel không còn màu nền xám nữa
        this.setBackground(new Color(0, 0, 0, 0)); // Đảm bảo màu nền hoàn toàn trong suốt
        btnMatrix = new RoundedIconButton[level.getRows() + 2][level.getCols() + 2]; // Bao gồm cả viền trống nếu cần
        
        this.TileCount = (level.getRows() * level.getCols())/2;
        
        for (int i = 1; i <= level.getRows(); i++) {
            for (int j = 1; j <= level.getCols(); j++) {
                int id = board.getCell(i, j).getId();
                ImageIcon icon = ImageLoad.getImage(id);
                btnMatrix[i][j] = new RoundedIconButton(icon, 0, i, j);
                btnMatrix[i][j].addActionListener(this);
                add(btnMatrix[i][j]);
            }
        }
    }

    public void updateAllButtons() {
        boolean isHiddenPhase = asianModel != null && asianModel.isHiddenPhase();
        
        for (int i = 1; i <= this.level.getRows(); i++) {
            for (int j = 1; j <= this.level.getCols(); j++) {
                Cell cell = board.getCell(i, j);
                RoundedIconButton btn = btnMatrix[i][j];

                if (cell.isStatus()) {
                    //tàng hình nè 
                    if (isHiddenPhase) {
                        btn.setIcon(null);
                    } else {
                        btn.setIcon(ImageLoad.getImage(cell.getId()));
                    }
                    btn.setVisible(true);
                } else {
                    // Ô trống thì ẩn đi
                    btn.setVisible(false);
                }
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
    
    public boolean isBoardEmpty() {
        for (int i = 1; i <= this.level.getRows(); i++) {
            for (int j = 1; j <= this.level.getCols(); j++) {
                if (board.getCell(i, j).isStatus()) {
                    return false;
                }
            }
        }
        return true; // Bảng đã trống trơn 100%
    }
 
 //hàm hiện thị bảng vinh danh khi dành chiến thắng   
    public void showHonorScreen() {
        java.awt.Window windown = javax.swing.SwingUtilities.getWindowAncestor(this);
        if (windown instanceof MainScreen) {
            MainScreen main = (MainScreen) windown;
            main.setEnabled(false);
            HonorScreen honorScreen = new HonorScreen(main, config, level, this);
            honorScreen.setAlwaysOnTop(true);
            honorScreen.setVisible(true);
        }
    } 
    
    public void showlossScreen(){
        java.awt.Window windown = javax.swing.SwingUtilities.getWindowAncestor(this);
            if (windown instanceof MainScreen) {
                MainScreen main = (MainScreen) windown;
                main.setEnabled(false);
                LossScreen lossScreen = new LossScreen(main, config, level, this);
                lossScreen.setAlwaysOnTop(true);
                lossScreen.setVisible(true);
            }
    }
    
    public void addScore(int point, int reward){
        this.TotalScore += point;
        this.TotalScore += reward;
        if(main != null){
            main.updateScore(this.TotalScore);
        }
    }
    
    public void addcoin(int coin){
        this.totalCoin += coin;
        if(main != null){
            main.updateCoin(totalCoin);
        }
    }

// hàm vẽ 
    private void drawPathOnOverlay() {
        java.awt.Window window = javax.swing.SwingUtilities.getWindowAncestor(this);
        if (window instanceof MainScreen) {
            MainScreen main = (MainScreen) window;
            java.awt.Component glassPane = main.getGlassPane();

            if (glassPane instanceof PathOverlay) {
                PathOverlay overlay = (PathOverlay) glassPane;
                // algorithm.getPath() lấy danh sách các điểm nối từ thuật toán vừa check
                overlay.showPath(algorithm.getPath(), this.getBounds(), level.getRows(), level.getCols());
            }
        }
    }
   
// xử lý khi chọn sai    
    private void processMismatch(RoundedIconButton clickedBtn, boolean isHidden) {       
        isProcessingMismatch = true; // Khóa không cho bấm lung tung khi đang chờ

        // Tạo bản sao của firstBtn để dùng trong Timer (vì firstClickBtn sẽ bị reset về null sớm)
        final RoundedIconButton firstBtn = firstClickBtn;
        final RoundedIconButton secondBtn = clickedBtn;

        javax.swing.Timer delayTimer = new javax.swing.Timer(100, new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                // Tắt viền vàng của cả 2 nút
                firstBtn.setSelectedState(false);
                secondBtn.setSelectedState(false);

                // Nếu đang ở chế độ tàng hình (Asian), giấu ảnh đi lại
                if (isHidden) {
                    firstBtn.setIcon(null);
                    secondBtn.setIcon(null);
                }

                isProcessingMismatch = false; // Mở khóa chuột
                ((javax.swing.Timer) evt.getSource()).stop();
            }
        });
    
        delayTimer.setRepeats(false);
        delayTimer.start();

        // Reset các biến tạm để chuẩn bị cho lượt chọn mới
        firstClick = null;
        firstClickBtn = null;
    }
    
    private void handleFirstClick(Cell cell, RoundedIconButton btn, boolean isHidden) {
        firstClick = cell;
        firstClickBtn = btn;
        btn.setSelectedState(true);
        if (isHidden) btn.setIcon(ImageLoad.getImage(cell.getId()));
    }

    private void handleSecondClick(Cell currentCell, RoundedIconButton clickedBtn, boolean isHidden) {
        if (isHidden) clickedBtn.setIcon(ImageLoad.getImage(currentCell.getId()));

        // Hủy chọn nếu click lại ô cũ
        if (firstClick == currentCell) {
            resetSelection(isHidden);
            return;
        }

        // Kiểm tra khớp cặp
        if (firstClick.getId() == currentCell.getId() && algorithm.checkPath(board, firstClick, currentCell)) {
            processMatch(currentCell, clickedBtn);
            this.RewardCount++;
        } else {
            processMismatch(clickedBtn, isHidden);
            this.RewardCount = 0;
        }
        Reward(15);
    }
//sự kiện nếu chạy đúng
    private void processMatch(Cell currentCell, RoundedIconButton clickedBtn) {
        //audioManager.playSoundEffect("/images/Sound/eated.wav");
        // ---> SỬA CÁCH GỌI ÂM THANH ĂN Ô TẠI ĐÂY <---
        java.awt.Window window = javax.swing.SwingUtilities.getWindowAncestor(this);
        if (window instanceof MainScreen) {
            MainScreen main = (MainScreen) window;
            main.playSoundEffect("/Sound/eated.wav"); 
        }
//Cộng diểm 
        addScore(100, reward);
        addcoin(TotalScore/10);
// Vẽ đường đi
        drawPathOnOverlay();

        int matchedId = firstClick.getId();
        RoundedIconButton firstBtn = firstClickBtn;

        // Xóa dữ liệu & ẩn UI
        CellPair RockketTarget = algorithm.removePair(firstClick, currentCell, board);
        if(RockketTarget != null){
            addScore(150, 0);// Cộng điểm nếu là tên lửa
            addcoin(TotalScore/10);
        }
        
        firstBtn.setVisible(false);
        clickedBtn.setVisible(false);
        
        updateAllButtons();

        //Hiệu ứng tên lửa
        if (matchedId == 1) { // ROCKET_ITEM_ID
            RocketAnimation.triggerRocketEffect(this, firstBtn, clickedBtn, RockketTarget);
                addScore(50, 0);// Cộng điểm tên lửa sau khi bắn trúng mục tiêu
                addcoin(TotalScore/10);
        }
        checkGameState();
        
        firstClick = null;
        firstClickBtn = null;
    }

// Hàm kiểm tra thắng thua    
    private void checkGameState() {
        if (isBoardEmpty()) {
            stopTimer();
            stopAsianTimer();
            DTB.savePlayer("tuan", totalCoin);
            DTB.updateHighScore(level.getLevel(), TotalScore);
            showHonorScreen();
            
        } else if (!algorithm.hasAnyMatch(board)) {
            shuffle();
        }
    }

    private void resetSelection(boolean isHidden) {
        firstClickBtn.setSelectedState(false);
        if (isHidden) firstClickBtn.setIcon(null);
        firstClick = null;
        firstClickBtn = null;
    }
   
    public void Reward(int point){
        if(this.RewardCount != 0){
            this.reward+= point;
        }
        else{
            this.reward = 0;
        }
    }
    
    public void stopTimer() {
        if (countdownTimer != null && countdownTimer.isRunning()) {
            countdownTimer.stop();
        }
    }
    public void resumeTimer() {
        if (countdownTimer != null && !countdownTimer.isRunning() && currentTime > 0) {
            countdownTimer.start();
        }
    }  
    public void addTimer(int time){
        currentTime = currentTime + time;
    }
    
    public int get_timeRemain(){
        return currentTime;
    }
    public int get_TotalScore(){
        return TotalScore;
    }
    public String get_Level(){
        return level.getLevel();
    }
    
public void initTimer(javax.swing.JSlider externalTimeline) {
    this.timeline = externalTimeline;
    this.maxTime = level.getTimeLimit();
    
    if (maxTime <= 0) maxTime = 120;
    
    this.currentTime = maxTime;
    
    if (timeline != null) {
        timeline.setMaximum(maxTime);
        timeline.setMinimum(0);
        timeline.setValue(maxTime);
    }

    if (countdownTimer != null) {
        countdownTimer.stop(); // Dừng cái cũ nếu có (tránh chạy chồng chéo)
    }

    countdownTimer = new javax.swing.Timer(1000, new java.awt.event.ActionListener() {
        @Override
        public void actionPerformed(java.awt.event.ActionEvent e) {
            currentTime--;
            
            if (timeline != null) {
                timeline.setValue(currentTime);
                timeline.repaint(); // <--- Thêm dòng này để ép UI vẽ lại giá trị mới
            }

            System.out.println("Thoi gian con: " + currentTime + " giay");
            if(currentTime > maxTime){
                currentTime = maxTime;
            }
            if (currentTime <= 0) {
                countdownTimer.stop();
                showlossScreen();
           }
        }
    });
    countdownTimer.start();
}
    @Override
    public void actionPerformed(ActionEvent e) {
        if (isProcessingMismatch) return;

        RoundedIconButton clickedBtn = (RoundedIconButton) e.getSource();

        // TỐI ƯU 1: Lấy tọa độ trực tiếp (Giả định em đã thêm getPosX/Y vào RoundedIconButton)
        int r = clickedBtn.getRows();
        int c = clickedBtn.getCols();
//        int[] coords = findCoords(clickedBtn);
//        int r = coords[0], c = coords[1];
        
        if (r == -1 || !board.getCell(r, c).isStatus()) return;

        Cell currentCell = board.getCell(r, c);
        boolean isHidden = asianModel != null && asianModel.isHiddenPhase();

        if (firstClick == null) {
            handleFirstClick(currentCell, clickedBtn, isHidden);
        } else {
            handleSecondClick(currentCell, clickedBtn, isHidden);
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
        if (asianModel != null)
            asianModel.stop();
    }
}
