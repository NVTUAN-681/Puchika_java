/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pikachu_master.Algorithm;

import com.mycompany.pikachu_master.Model.Board;
import com.mycompany.pikachu_master.Model.Cell;
import com.mycompany.pikachu_master.Model.CellPair;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class MediumModeAlgorithm extends ClassicAlgorithm {

@Override
public CellPair removePair(Cell c1, Cell c2, Board board) {
    CellPair rocketTarget = super.removePair(c1, c2, board);
    int centerRow = board.getRows() / 2;
    int centerCol = board.getCols() / 2;
    
    if (c1.getY() == c2.getY()) {
        // Cùng cột → xa tâm trước, gần tâm sau
        int x1 = c1.getX();
        int x2 = c2.getX();
        if (Math.abs(x1 - centerRow) < Math.abs(x2 - centerRow)) {
            int tmp = x1; x1 = x2; x2 = tmp;
        }
        applyVerticalGravitySingle(board, x1, c1.getY());
        applyVerticalAndHorizontal(board, x2, c1.getY(), centerRow, centerCol);
    } else {
        // Cùng hàng hoặc khác hàng khác cột → dồn dọc cả 2 trước
        applyVerticalGravitySingle(board, c1.getX(), c1.getY());
        applyVerticalGravitySingle(board, c2.getX(), c2.getY());
        // Tính range hàng bị ảnh hưởng, mỗi hàng chỉ dồn ngang 1 lần
        int minRow, maxRow;
        if (c1.getX() <= centerRow && c2.getX() <= centerRow) {
            minRow = 1;
            maxRow = Math.max(c1.getX(), c2.getX());
        } else if (c1.getX() > centerRow && c2.getX() > centerRow) {
            minRow = Math.min(c1.getX(), c2.getX());
            maxRow = board.getRows();
        } else {
            minRow = 1;
            maxRow = board.getRows();
        }
        for (int i = minRow; i <= maxRow; i++) {
            applyHorizontalGravityRow(board, i, centerCol);
        }
    }
    
    rebuildMap(board);
    return rocketTarget;
}

private void applyVerticalAndHorizontal(Board board, int x, int y, int centerRow, int centerCol) {
    applyVerticalGravitySingle(board, x, y);
    if (x <= centerRow) {
        // Nửa trên → các hàng từ 1 đến x bị ảnh hưởng
        for (int i = 1; i <= x; i++) {
            applyHorizontalGravityRow(board, i, centerCol);
        }
    } else {
        // Nửa dưới → các hàng từ x đến cuối bị ảnh hưởng
        for (int i = x; i <= board.getRows(); i++) {
            applyHorizontalGravityRow(board, i, centerCol);
        }
    }
}

private void applyVerticalGravitySingle(Board board, int x, int y) {
    int centerRow = board.getRows() / 2;
    if (x <= centerRow) {
        for (int i = x; i > 1; i--) {
            board.getCell(i, y).setId(board.getCell(i - 1, y).getId());
            board.getCell(i, y).setStatus(board.getCell(i - 1, y).isStatus());
        }
        board.getCell(1, y).setId(0);
        board.getCell(1, y).setStatus(false);
    } else {
        for (int i = x; i < board.getRows(); i++) {
            board.getCell(i, y).setId(board.getCell(i + 1, y).getId());
            board.getCell(i, y).setStatus(board.getCell(i + 1, y).isStatus());
        }
        board.getCell(board.getRows(), y).setId(0);
        board.getCell(board.getRows(), y).setStatus(false);
    }
}

private void applyHorizontalGravityRow(Board board, int x, int centerCol) {
    // Phần phải: collect từ centerCol+1 đến cuối, dồn về tâm
    List<Integer> rightIds = new ArrayList<>();
    for (int j = centerCol + 1; j <= board.getCols(); j++) {
        if (board.getCell(x, j).isStatus()) {
            rightIds.add(board.getCell(x, j).getId());
        }
    }
    int idx = 0;
    for (int j = centerCol + 1; j <= board.getCols(); j++) {
        if (idx < rightIds.size()) {
            board.getCell(x, j).setId(rightIds.get(idx++));
            board.getCell(x, j).setStatus(true);
        } else {
            board.getCell(x, j).setId(0);
            board.getCell(x, j).setStatus(false);
        }
    }
    // Phần trái: collect từ centerCol về 1, dồn về tâm
    List<Integer> leftIds = new ArrayList<>();
    for (int j = centerCol; j >= 1; j--) {
        if (board.getCell(x, j).isStatus()) {
            leftIds.add(board.getCell(x, j).getId());
        }
    }
    idx = 0;
    for (int j = centerCol; j >= 1; j--) {
        if (idx < leftIds.size()) {
            board.getCell(x, j).setId(leftIds.get(idx++));
            board.getCell(x, j).setStatus(true);
        } else {
            board.getCell(x, j).setId(0);
            board.getCell(x, j).setStatus(false);
        }
    }
}

private void rebuildMap(Board board) {
    getMap().clear();
    for (int i = 1; i <= board.getRows(); i++) {
        for (int j = 1; j <= board.getCols(); j++) {
            if (board.getCell(i, j).isStatus()) {
                if (!getMap().containsKey(board.getCell(i, j).getId())) {
                    getMap().put(board.getCell(i, j).getId(), new ArrayList<>());
                }
                getMap().get(board.getCell(i, j).getId()).add(board.getCell(i, j));
            }
        }
    }
}
}