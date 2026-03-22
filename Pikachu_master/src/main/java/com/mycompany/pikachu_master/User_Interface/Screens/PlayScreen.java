/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pikachu_master.User_Interface.Screens;

import com.mycompany.pikachu_master.Algorithm.ClassicAlgorithm;
import com.mycompany.pikachu_master.Model.Board;
import com.mycompany.pikachu_master.Model.Cell;
import com.mycompany.pikachu_master.User_Interface.Components.ButtonMain;
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

    public PlayScreen() {
        // Khởi tạo thuật toán và bảng 6x5
        algorithm = new ClassicAlgorithm();
        board = new Board(6, 5); 
        board.initBoard(algorithm); // Tạo số ngẫu nhiên từ thuật toán chính

        setLayout(new GridLayout(6, 5, 5, 5)); // Ma trận 6 dòng, 5 cột
        btnMatrix = new ButtonMain[7][6]; // Bao gồm cả viền trống nếu cần

        for (int i = 1; i <= 6; i++) {
            for (int j = 1; j <= 5; j++) {
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
                
                System.out.println("Đã ăn cặp: " + firstClick.getId());
            } else {
                // Không ăn được thì bỏ chọn
                btnMatrix[firstClick.getX()][firstClick.getY()].setBackground(null);
            }
            firstClick = null;
        }
    }
}
