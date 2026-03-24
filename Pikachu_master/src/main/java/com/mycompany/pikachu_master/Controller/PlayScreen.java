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
import com.mycompany.pikachu_master.User_Interface.Components.ButtonMain;

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
    private int rows, cols, timelimit, tileSize;
    private int remainingTiles;
    private RoundedIconButton firstClickBtn;

    public PlayScreen(GameConfig config) {
        this.config = config;
        this.rows = config.GetRows();
        this.cols = config.GetCols();
        this.timelimit = config.GetTimeLimit();
        this.tileSize = 55;
        this.remainingTiles = this.rows * this.cols;
        if(config.GetLevel().equals("AFICA")){
            this.algorithm = new ClassicAlgorithm();
        }
        else if(config.GetLevel().equals("ASIAN")){
            this.algorithm = new MediumModeAlgorithm();
        }

        
        setLayout(new GridLayout(rows, cols, 0, 0)); 
        this.setPreferredSize(new Dimension(cols * tileSize, rows * tileSize));
        this.setMaximumSize(new Dimension(cols * tileSize, rows * tileSize));
        this.setMinimumSize(new Dimension(cols * tileSize, rows * tileSize));

        this.setOpaque(false); // Dòng này làm cho Panel không còn màu nền xám nữa
        this.setBackground(new Color(0, 0, 0, 0)); // Đảm bảo màu nền hoàn toàn trong suốt
        board = new Board(config.GetRows(), config.GetCols()); 
        board.initBoard(algorithm); // Tạo số ngẫu nhiên từ thuật toán chính
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
    
//    private void updateAllButtons() {
//    for (int i = 1; i <= this.rows; i++) {
//        for (int j = 1; j <= this.cols; j++) {
//            Cell cell = board.getCell(i, j);
//            if (cell.isStatus()) {
//                btnMatrix[i][j].setIcon(ImageLoad.getImage(cell.getId()));
//                btnMatrix[i][j].setVisible(true);
//            } else {
//                btnMatrix[i][j].setVisible(false);
//            }
//            // Reset lại viền nếu có
//            btnMatrix[i][j].setBorderPainted(false);
//        }
//    }
//}

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
//        r = clickedBtn.getX();
//        c = clickedBtn.getY();
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

            HonorScreen honorScreen = new HonorScreen(main, config);
            honorScreen.setVisible(true);
        }
    }
}
