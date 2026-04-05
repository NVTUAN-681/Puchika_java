/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pikachu_master.Controller;

import com.mycompany.pikachu_master.Algorithm.ClassicAlgorithm;
import com.mycompany.pikachu_master.Algorithm.IAlgorithm;
import com.mycompany.pikachu_master.Algorithm.MediumModeAlgorithm;
import com.mycompany.pikachu_master.Effect.RocketAnimation;
import com.mycompany.pikachu_master.Data.gameDAO;
import com.mycompany.pikachu_master.Effect.PathOverlay;
import com.mycompany.pikachu_master.Effect.ShuffleAnimation;
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
import com.mycompany.pikachu_master.Utils.WoodButtonUI;
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
    private final gameDAO DTB;
    private AsianModel asianModel;
    private boolean isProcessingMismatch = false;
    private int maxTime;
    private int currentTime;
    private int RewardCount = 0;
    public javax.swing.Timer countdownTimer;
    private javax.swing.JSlider timeline;
    private int RewardScore;
    private int RewardCoin;
    private int TotalScore;
    private int reward;
    private int totalCoin;
    private int CostBuyTime;
    private int TimeBought;

    public PlayScreen(GameConfig config, MainScreen main) {
        this.config = config;
        this.DTB = new gameDAO();
        this.main = main;
        this.RewardScore = 100;
        this.RewardCoin = RewardScore / 10;
        this.TotalScore = 0;
        this.totalCoin = DTB.getTotalCoin("tuan");
        this.reward = 0;
        this.CostBuyTime = 500;
        this.TimeBought = (int) (CostBuyTime / 100);
        boolean isDark = false;

//thiết lập thuật toán và cố định level;
        switch (config.GetLevel()) {
            case "AFRICA" -> {
                ImageLoad.isAsianMode = false; // === [THÊM MỚI] Tắt cờ ===
                this.level = LevelType.AFRICA;
                this.algorithm = new ClassicAlgorithm();
                isDark = true;
            }
            case "EUROPE" -> {
                ImageLoad.isAsianMode = false; // === [THÊM MỚI] Tắt cờ ===
                this.level = LevelType.EUROPE;
                this.algorithm = new ClassicAlgorithm();
                isDark = true;
            }
            case "ASIAN" -> {
                ImageLoad.isAsianMode = true; // === [THÊM MỚI] BẬT CỜ CHO CHẾ ĐỘ NÀY ===
                this.level = LevelType.ASIAN;
                this.algorithm = new MediumModeAlgorithm();
                isDark = true;
                // --- KHỞI TẠO ĐỒNG HỒ TÀNG HÌNH CHO ASIAN ---
                asianModel = new AsianModel(this);
                asianModel.start();
            }
            case "Start" -> {
                ImageLoad.isAsianMode = false; // === [THÊM MỚI] Tắt cờ ===
                this.level = LevelType.START;
                this.algorithm = new ClassicAlgorithm();
                isDark = false;
            }
            case "EASY" -> {
                ImageLoad.isAsianMode = false; // === [THÊM MỚI] Tắt cờ ===
                this.level = LevelType.EASY;
                this.algorithm = new ClassicAlgorithm();
                isDark = false;
            }
            case "NORMAL" -> {
                ImageLoad.isAsianMode = false; // === [THÊM MỚI] Tắt cờ ===
                this.level = LevelType.NORMAL;
                this.algorithm = new ClassicAlgorithm();
                isDark = false;
            }
            case "HARD" -> {
                ImageLoad.isAsianMode = false; // === [THÊM MỚI] Tắt cờ ===
                this.level = LevelType.HARD;
                this.algorithm = new ClassicAlgorithm();
                isDark = false;
            }
            default -> {
                ImageLoad.isAsianMode = false; // === [THÊM MỚI] Tắt cờ ===
                this.level = LevelType.START;
                this.algorithm = new ClassicAlgorithm();
            }
        }

        this.board = new Board(level.getRows(), level.getCols(), true);

        if (config.GetResume() == 1) {
            // KỊCH BẢN VÁN DỞ: Lấy ma trận từ chuỗi lưu trữ
            int[][] savedData = convertStringToMatrix(config.getMatrix_data(), level.getRows(), level.getCols());
            applySavedMatrix(savedData);
            this.TotalScore = config.getScore(); // Khôi phục điểm cũ
        } else {
            // KỊCH BẢN VÁN MỚI: Khởi tạo ngẫu nhiên như cũ
            if (level.isIsHardMode()) {
                board.initHardBoard(algorithm, level.getNoP(), level.isIsRocket());
            } else {
                board.initBoard(algorithm, level.getNoP());
            }
        }

        setLayout(new GridLayout(level.getRows(), level.getCols(), 0, 0));
        this.setOpaque(false); // Dòng này làm cho Panel không còn màu nền xám nữa
        this.setBackground(new Color(0, 0, 0, 0)); // Đảm bảo màu nền hoàn toàn trong suốt

        btnMatrix = new RoundedIconButton[level.getRows() + 2][level.getCols() + 2]; // Bao gồm cả viền trống nếu cần

        for (int i = 1; i <= level.getRows(); i++) {
            for (int j = 1; j <= level.getCols(); j++) {
                Cell cell = board.getCell(i, j);
                int id = cell.getId();
                ImageIcon icon = ImageLoad.getImage(id);

                btnMatrix[i][j] = new RoundedIconButton(icon, 0, i, j);
                // mày xóa 2 dòng này đi tao đập chết mày

                btnMatrix[i][j].setUI(new WoodButtonUI(isDark));
                btnMatrix[i][j].addActionListener(this);

                // QUAN TRỌNG: Nếu ván dở mà ô đã ăn (status = false) thì phải ẩn nút ngay
                if (!cell.isStatus()) {
                    btnMatrix[i][j].setVisible(false);
                }
                add(btnMatrix[i][j]);
            }
        }
    }
    
public void applySavedMatrix(int[][] savedData) {
    // 1. Xóa sạch Map cũ của thuật toán để nạp lại từ đầu
    algorithm.getMap().clear();

    for (int i = 0; i < level.getRows(); i++) {
        for (int j = 0; j < level.getCols(); j++) {
            int id = savedData[i][j];
            Cell cell = board.getCell(i + 1, j + 1);
            
            if (id == 0) {
                cell.setStatus(false);
            } else {
                cell.setId(id);
                cell.setStatus(true);
                
                // 2. Nạp Cell vào Map của Algorithm để Hint/CheckPath hoạt động
                if (!algorithm.getMap().containsKey(id)) {
                    algorithm.getMap().put(id, new java.util.ArrayList<>());
                }
                algorithm.getMap().get(id).add(cell);
            }
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
                    //tàng hình 
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
        // Animation sẽ hút vào -> Đảo data -> Phóng ra
        ShuffleAnimation.trigger(this);
    }

    public void onShuffleComplete() {
        // --- ÉP HIỆN HÌNH LẠI KHI ĐẢO MAP Ở CHẾ ĐỘ ASIAN ---
        if (config.GetLevel().equals("ASIAN") && asianModel != null) {
            asianModel.reset();
        }

        // Hiện lại các nút thật trên lưới với data mới
        updateAllButtons();

        // Reset thao tác click dở dang (nếu có)
        firstClick = null;
        firstClickBtn = null;

        // Mở khóa chuột cho người chơi ấn tiếp
        this.setProcessingMismatch(false);
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
            System.out.println("have not CellPair for eat.");
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

    // kiểm tra bảng còn cặp nào không
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

            DTB.updateCoin_player("tuan", totalCoin);
            DTB.updateHighScore(level.getLevel(), TotalScore + (this.get_timeRemain() *10), level.getTimeLimit() - currentTime);
            DTB.deleteSaveGame("tuan");
            DTB.insertScore("tuan", level.getLevel(), TotalScore, level.getTimeLimit() - currentTime);
        }
    }

    //hàm hiện thị khi thua
    public void showlossScreen() {
        java.awt.Window windown = javax.swing.SwingUtilities.getWindowAncestor(this);
        if (windown instanceof MainScreen) {
            MainScreen main = (MainScreen) windown;
            main.setEnabled(false);
            LossScreen lossScreen = new LossScreen(main, config, level, this);
            lossScreen.setAlwaysOnTop(true);
            lossScreen.setVisible(true);

            DTB.updateCoin_player("tuan", totalCoin);
            DTB.updateHighScore(level.getLevel(), TotalScore + (this.get_timeRemain() * 10), level.getTimeLimit() - currentTime);
            DTB.deleteSaveGame(level.getLevel());
        }
    }

    //hàm cộng điểm
    public void addScore(int point, int reward) {
        this.TotalScore += point;
        this.TotalScore += reward;
        if (main != null) {
            main.updateScore(this.TotalScore);
        }
    }

    //hàm cộng coin
    public void addcoin(int coin, int reward) {
        this.totalCoin += (int) (coin / 10);
        this.totalCoin += (int) (reward / 10);
        if (main != null) {
            main.updateCoin(totalCoin);
        }
    }

    public void BuyTime() {
        if(totalCoin >= CostBuyTime){
        this.totalCoin -= CostBuyTime;
        this.currentTime += TimeBought;
        DTB.updateCoin_player("tuan", this.totalCoin);
        }
        else{
        }
    }

    public void Reward() {
        if (this.RewardCount != 0) {
            this.reward += (int) (RewardCount * 10);
        } else {
            this.reward = 0;
        }
    }

    public String getMatrixAsString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= level.getRows(); i++) {
            for (int j = 1; j <= level.getCols(); j++) {
                Cell cell = board.getCell(i, j);
                if (!cell.isStatus()) {
                    sb.append("0,"); // Ô đã biến mất
                } else {
                    sb.append(cell.getId()).append(","); // Ô còn hình
                }
            }
        }
        return sb.toString();
    }

    public int[][] convertStringToMatrix(String matrixData, int rows, int cols) {
        // 1. Khởi tạo mảng tạm để chứa dữ liệu
        int[][] tempMatrix = new int[rows][cols];

        // 2. Tách chuỗi
        String[] ids = matrixData.split(",");
        int index = 0;

        // 3. Đổ dữ liệu vào mảng (Giữ đúng thứ tự Duyệt hàng -> Duyệt cột)
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (index < ids.length) {
                    tempMatrix[i][j] = Integer.parseInt(ids[index++]);
                }
            }
        }
        return tempMatrix;
    }

    // hàm vẽ đường nối giữa hai ô khi chọn đúng
    private void drawPathOnOverlay(Runnable onComplete) {
        java.awt.Window window = javax.swing.SwingUtilities.getWindowAncestor(this);
        if (window instanceof MainScreen) {
            MainScreen main = (MainScreen) window;
            java.awt.Component glassPane = main.getGlassPane();

            if (glassPane instanceof PathOverlay) {
                PathOverlay overlay = (PathOverlay) glassPane;
                overlay.showPath(algorithm.getPath(), this.getBounds(), level.getRows(), level.getCols(),onComplete);
            } else {
                // Nếu vì lý do nào đó không có overlay, vẫn phải chạy lệnh xóa ô
                if (onComplete != null) onComplete.run();
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

    //sự kiện nếu chạy đúng
    private void processMatch(Cell currentCell, RoundedIconButton clickedBtn) {
        java.awt.Window window = javax.swing.SwingUtilities.getWindowAncestor(this);
        if (window instanceof MainScreen) {
            MainScreen main = (MainScreen) window;
            main.playSoundEffect("/sound/eated.wav");
        }
//Cộng diểm 
        addScore(RewardScore, reward);
        addcoin(RewardCoin, reward);
// Vẽ đường đi
        //drawPathOnOverlay();

        int matchedId = firstClick.getId();
        RoundedIconButton firstBtn = firstClickBtn;
        Cell savedFirstClick = firstClick;
        
        // Khóa không cho người chơi click lung tung trong lúc đợi 0.4s vẽ đường
        isProcessingMismatch = true; 

        // Truyền toàn bộ đoạn code "Xóa dữ liệu, hiệu ứng, check thắng thua" vào trong Runnable
        drawPathOnOverlay(() -> {
        // Xóa dữ liệu & ẩn UI
        //CellPair RockketTarget = algorithm.removePair(firstClick, currentCell, board);
       // [CHỈNH SỬA] - SỬA LẠI THÀNH:
        CellPair RockketTarget = algorithm.removePair(savedFirstClick, currentCell, board);
        if (RockketTarget != null) {
            addScore((int) (RewardScore * 1.5), 0);// Cộng điểm nếu là tên lửa
            addcoin(RewardScore, 0);
        }

        firstBtn.setVisible(false);
        clickedBtn.setVisible(false);

        updateAllButtons();

        //Hiệu ứng tên lửa
        if (matchedId == 1) {
            if (main != null) {
                main.playSoundEffect("/sound/SoundRocket/RocketStart.wav");
            }
            RocketAnimation.triggerRocketEffect(this, firstBtn, clickedBtn, RockketTarget);
            addScore((int) (RewardScore * 0.5), 0);// Cộng điểm tên lửa sau khi bắn trúng mục tiêu
            addcoin(RewardScore, 0);
        }
        checkGameState();
        // Vẽ xong, xóa xong rồi thì mở khóa chuột lại cho người chơi
            isProcessingMismatch = false;
        });
        firstClick = null;
        firstClickBtn = null;
    }

    private void handleFirstClick(Cell cell, RoundedIconButton btn, boolean isHidden) {
        if (main != null) {
            main.playSoundEffect("/sound/SoundTap/TapMain1.wav"); // Thay bằng tên file của bạn
        }

        firstClick = cell;
        firstClickBtn = btn;
        btn.setSelectedState(true);
        if (isHidden) {
            btn.setIcon(ImageLoad.getImage(cell.getId()));
        }
    }

    private void handleSecondClick(Cell currentCell, RoundedIconButton clickedBtn, boolean isHidden) {
        if (main != null) {
            main.playSoundEffect("/sound/SoundTap/TapMain2.wav"); // Thay bằng tên file của bạn
        }
        if (isHidden) {
            clickedBtn.setIcon(ImageLoad.getImage(currentCell.getId()));
        }

        // Hủy chọn nếu click lại ô cũ
        if (firstClick == currentCell) {
            resetSelection(isHidden);
            return;
        }

        // Kiểm tra khớp cặp
        if (firstClick.getId() == currentCell.getId() && algorithm.checkPath(board, firstClick, currentCell)) {
            processMatch(currentCell, clickedBtn);
            this.RewardCount++; // Cộng chuỗi
        } else {
            processMismatch(clickedBtn, isHidden);
            this.RewardCount = 0; // hủy chuỗi
        }
        // Tăng điểm cộng khi có chuỗi
        Reward();
    }

// Hàm kiểm tra thắng thua    
    private void checkGameState() {
        if (isBoardEmpty()) {
            stopTimer();
            stopAsianTimer();

            // chèn dữ liệu vào database
            showHonorScreen();

        } else if (!algorithm.hasAnyMatch(board)) {
            shuffle();
        }
    }

    private void resetSelection(boolean isHidden) {
        firstClickBtn.setSelectedState(false);
        if (isHidden) {
            firstClickBtn.setIcon(null);
        }
        firstClick = null;
        firstClickBtn = null;
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

    public void addTimer(int time) {
        currentTime = currentTime + time;
    }

    public int get_timeRemain() {
        return currentTime;
    }

    public int get_TotalScore() {
        return TotalScore;
    }

    public int get_Totalcoin() {
        return totalCoin;
    }

    public String get_Level() {
        return level.getLevel();
    }

    public void initTimer(javax.swing.JSlider externalTimeline) {
        this.timeline = externalTimeline;
        this.maxTime = level.getTimeLimit();

        if (maxTime <= 0) {
            maxTime = 120;
        }

//        this.currentTime = maxTime;
        if (config.GetResume() == 1 && config.getTimeLimit() > 0) {
            this.currentTime = config.getTimeLimit();
        } else {
            this.currentTime = maxTime;
        }

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
                    timeline.repaint();
                }

                System.out.println("Thoi gian con: " + currentTime + " giay");
                if (currentTime > maxTime) {
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
        if (isProcessingMismatch) {
            return;
        }

        RoundedIconButton clickedBtn = (RoundedIconButton) e.getSource();

        //Lấy tọa độ trực tiếp
        int r = clickedBtn.getRows();
        int c = clickedBtn.getCols();

        if (r == -1 || !board.getCell(r, c).isStatus()) {
            return;
        }

        Cell currentCell = board.getCell(r, c);
        boolean isHidden = asianModel != null && asianModel.isHiddenPhase();

        if (firstClick == null) {
            handleFirstClick(currentCell, clickedBtn, isHidden);
        } else {
            handleSecondClick(currentCell, clickedBtn, isHidden);
        }
    }

    // --- 3 HÀM ĐỂ MAIN SCREEN ĐIỀU KHIỂN TÀNG HÌNH KHI PAUSE/RESTART ---
    public void setProcessingMismatch(boolean b) {
        this.isProcessingMismatch = b;
    }

    public IAlgorithm getAlgorithm() {
        return this.algorithm;
    }

    public Board getBoard() {
        return this.board;
    }

    public RoundedIconButton[][] getBtnMatrix() {
        return this.btnMatrix;
    }

    public void pauseAsianTimer() {
        if (asianModel != null) {
            asianModel.pause();
        }
    }

    public void resumeAsianTimer() {
        if (asianModel != null && config.GetLevel().equals("ASIAN")) {
            asianModel.resume();
        }
    }

    public void stopAsianTimer() {
        if (asianModel != null) {
            asianModel.stop();
        }
    }

    public int getRows() {
        return level.getRows();
    }

    public int getCols() {
        return level.getCols();
    }
}
