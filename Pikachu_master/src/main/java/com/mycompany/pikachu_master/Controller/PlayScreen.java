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

    public PlayScreen(GameConfig config) {
        this.config = config;
        this.rows = config.GetRows();
        this.cols = config.GetCols();
        board = new Board(this.rows, this.cols);
        this.timelimit = config.GetTimeLimit();
        this.tileSize = 55;
        this.remainingTiles = this.rows * this.cols;
        
        if(config.GetLevel().equals("AFICA")){
            this.algorithm = new ClassicAlgorithm();
            this.NoP = 12;
            board.initBoard(algorithm, NoP);
        }
        else if(config.GetLevel().equals("EUROPE")){
            this.algorithm = new ClassicAlgorithm();
            this.NoP = 16;
            board.initHardBoard(algorithm, NoP, true);
        }
        else if(config.GetLevel().equals("ASIAN")){
            this.algorithm = new MediumModeAlgorithm();
            this.NoP = 21;
            board.initHardBoard(algorithm, NoP, true);
        }
        else if(config.GetLevel().equals("Start")){
            this.algorithm = new ClassicAlgorithm();
            this.NoP = 6;
            board.initBoard(algorithm, NoP);
        }

        
        setLayout(new GridLayout(rows, cols, 0, 0)); 
        this.setPreferredSize(new Dimension(cols * tileSize, rows * tileSize));
        this.setMaximumSize(new Dimension(cols * tileSize, rows * tileSize));
        this.setMinimumSize(new Dimension(cols * tileSize, rows * tileSize));

        this.setOpaque(false); // Dòng này làm cho Panel không còn màu nền xám nữa
        this.setBackground(new Color(0, 0, 0, 0)); // Đảm bảo màu nền hoàn toàn trong suốt
//        board = new Board(config.GetRows(), config.GetCols()); 
//        board.initBoard(algorithm, 20); // Tạo số ngẫu nhiên từ thuật toán chính
        btnMatrix = new RoundedIconButton[rows + 2][cols + 2]; // Bao gồm cả viền trống nếu cần

        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= cols; j++) {
                int id = board.getCell(i, j).getId();
                ImageIcon icon = ImageLoad.getImage(id);
                btnMatrix[i][j] = new RoundedIconButton(icon, 20);
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
                // Cập nhật lại Icon vì sau khi dồn, ID tại vị trí (i,j) đã thay đổi
                btn.setIcon(ImageLoad.getImage(cell.getId()));
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
    
    public void shuffle(){
        algorithm.shuffle(board);
        updateAllButtons();
        firstClick = null;
        firstClickBtn = null;
    }
    
    public void findHint(){
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

        if (firstClick  == null) {
            firstClick  = currentCell;
            firstClickBtn = clickedBtn;
            clickedBtn.setSelectedState(true);
        } else {
            // Lấy nút đầu tiên để xử lý viền
            //RoundedIconButton firstBtn = btnMatrix[firstClick.getX()][firstClick.getY()];
            RoundedIconButton firstBtn = firstClickBtn;
            // Nếu click lại chính ô vừa chọn thì hủy chọn
            if (firstClick == currentCell) {
                 firstBtn.setSelectedState(false);
                 firstClick  = null;
                 firstClickBtn = null;
                 return;
            }
            if (firstClick.getId() == currentCell.getId() && algorithm.checkPath(board, firstClick, currentCell)) {
                // Ăn được: Ẩn cả 2 nút
                algorithm.removePair(firstClick, currentCell, board);
                firstBtn.setVisible(false);
                clickedBtn.setVisible(false);
                firstBtn.setSelectedState(false);
                updateAllButtons();
                remainingTiles -= 2;
                if (remainingTiles <= 0) {
                    showHonorScreen();
                }
                else{
                    if(algorithm.hasAnyMatch(board) == false){
                        algorithm.shuffle(board);
                        updateAllButtons();
                    }
                }
            } else {
                firstBtn.setSelectedState(false);
//                // Nếu em muốn báo hiệu chọn sai, có thể nháy đỏ ở đây
            }
            firstClick = null;
            firstClickBtn = null;
        }
    }

    private void showHonorScreen() {
        java.awt.Window windown = javax.swing.SwingUtilities.getWindowAncestor(this);
        if(windown instanceof MainScreen) {
            MainScreen main = (MainScreen) windown;
            main.stopTimer();
            main.setEnabled(false);
            HonorScreen honorScreen = new HonorScreen(main, config);
            honorScreen.setAlwaysOnTop(true);
            honorScreen.setVisible(true);           
        }
    }
}
