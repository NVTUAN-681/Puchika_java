/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pikachu_master.Controller;

import com.mycompany.pikachu_master.Algorithm.ClassicAlgorithm;
import com.mycompany.pikachu_master.Model.Board;
import com.mycompany.pikachu_master.Model.Cell;
import com.mycompany.pikachu_master.User_Interface.Components.ButtonMain;
import com.mycompany.pikachu_master.User_Interface.Screens.LevelScreen;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;

/**
 *
 * @author DELL
 */
public class PlayScreen extends JPanel implements ActionListener {
    private Board board;
    private ClassicAlgorithm algorithm;
    private ButtonMain[][] btnMatrix;
    private Cell firstClick = null;
    private GameConfig config;
    

    public PlayScreen(GameConfig config ) {
        // Khởi tạo thuật toán và bảng 6x5
        
        algorithm = new ClassicAlgorithm();
        board = new Board(config.GetCols(), config.GetRows()); 
        board.initBoard(algorithm); // Tạo số ngẫu nhiên từ thuật toán chính
        setLayout(new GridLayout(config.GetRows(), config.GetCols(), config.GetCols(), config.GetCols())); // Ma trận 6 dòng, 5 cột
        btnMatrix = new ButtonMain[config.GetRows() + 1][config.GetCols() + 1]; // Bao gồm cả viền trống nếu cần

        for (int i = 1; i <= config.GetRows(); i++) {
            for (int j = 1; j <=  config.GetCols(); j++) {
                Cell cell = board.getCell(i, j);
                // Hiển thị con số ngẫu nhiên từ Board
                btnMatrix[i][j] = new ButtonMain(i, j, String.valueOf(cell.getId()));
                btnMatrix[i][j].addActionListener(this);
                add(btnMatrix[i][j]);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ButtonMain clickedBtn = (ButtonMain) e.getSource();
        int r = clickedBtn.getxCoord();
        int c = clickedBtn.getyCoord();
        Cell currentCell = board.getCell(r, c);

        if (!currentCell.isStatus()) return; // Ô đã biến mất (>0 là có, 0 là trống)

        if (firstClick == null) {
            firstClick = currentCell;
            clickedBtn.setBackground(Color.YELLOW); // Đánh dấu ô thứ nhất
        } else {
            // Gửi tọa độ về thuật toán xử lý
            if (algorithm.checkPath(board, firstClick, currentCell)) {
                // Xử lý logic khi ăn được
                algorithm.removePair(firstClick, currentCell, board);
                
                // Cập nhật giao diện (ẩn các ô = 0)
                btnMatrix[firstClick.getX()][firstClick.getY()].setVisible(false);
                btnMatrix[currentCell.getX()][currentCell.getY()].setVisible(false);
                
                System.out.println("delete: [" + firstClick.getX() + " " + firstClick.getY() + "] ["+ currentCell.getX() + " " + currentCell.getY() +"]");
            } else {
                // Không ăn được thì bỏ chọn
                btnMatrix[firstClick.getX()][firstClick.getY()].setBackground(null);
            }
            firstClick = null;
        }
    }
}
