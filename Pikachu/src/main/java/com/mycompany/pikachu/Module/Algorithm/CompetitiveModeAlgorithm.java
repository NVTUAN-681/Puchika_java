/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pikachu.Module.Algorithm;

import com.mycompany.pikachu.Module.Model.Board;
import com.mycompany.pikachu.Module.Model.Cell;
import com.mycompany.pikachu.Module.Model.CellPair;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Admin
 */
public class CompetitiveModeAlgorithm implements IAlgorithm{
    
    private Map<Integer, List<Cell>> map;
    private List<Point> list;

    public CompetitiveModeAlgorithm() {
        map = new HashMap<>();
        list = new ArrayList<>();
    }

    public CompetitiveModeAlgorithm(Map<Integer, List<Cell>> map, List<Point> list) {
        this.map = map;
        this.list = list;
    }
    
    public Map<Integer, List<Cell>> getMap() {
        return map;
    }
    
    
    
    // Kiem tra xem 2 diem co trung nhau khong, coincide(v) : trùng nhau
    private boolean checkCoincident (Cell c1, Cell c2) {
        if (c1.getX() == c2.getX() && c1.getY() == c2.getY()) {
            return true;
        }
        return false;
    }
    
    // Kiem tra xem co di duoc theo duong thang ngang khong
    private boolean checkLineX (Board board, Cell c1, Cell c2) {
        if (c1.getX() != c2.getX()) {
            return false;
        }
        int row = c1.getX();
	int left = c1.getY() < c2.getY() ? c1.getY() : c2.getY();
	int right = c1.getY() > c2.getY() ? c1.getY() : c2.getY();
        c1.setStatus(false);
        c2.setStatus(false);
	for (int i = left; i <= right; i++) {
 		if (board.getCell(row, i).isStatus() == true) {
                    c1.setStatus(true);
                    c2.setStatus(true);
                    return false;
                }
        }
        list.clear();
        list.add(new Point(c1.getX(), c1.getY()));
        list.add(new Point(c2.getX(), c2.getY()));
	return true;
    }
    
    // Kiem tra xem co di duoc theo duong thang dung khong
    private boolean checkLineY (Board board, Cell c1, Cell c2) {
	if (c1.getY() != c2.getY()) {
            return false;
        }
        int col = c1.getY();
	int above = c1.getX() < c2.getX() ? c1.getX() : c2.getX();
	int below = c1.getX() > c2.getX() ? c1.getX() : c2.getX();
        c1.setStatus(false);
        c2.setStatus(false);
	for(int i = above + 1; i < below; i++) {            
            if (board.getCell(i, col).isStatus() == true) {
                c1.setStatus(true);
                c2.setStatus(true);
		return false;
            }
	}
        list.clear();
        list.add(new Point(c1.getX(), c1.getY()));
        list.add(new Point(c2.getX(), c2.getY()));
	return true;
    }

    // Kiểm tra xem 2 điểm có thể nối theo hình chữ L không, xuất phát theo chiều ngang trước
    private boolean checkLX(Board board, Cell c1, Cell c2) {
        if (c1.getX() == c2.getX()) {
            return false;
        }
        if (checkLineX(board, c1, board.getCell(c1.getX(), c2.getY())) 
                && checkLineX(board, board.getCell(c1.getX(), c2.getY()), c2)) {
            return true;
        }
        return false;
    }    
    
    // Kiểm tra xem 2 điểm có thể nối theo hình chữ L không, xuất phát theo chiều dọc trước
    private boolean checkLY(Board board, Cell c1, Cell c2) {
        if (c1.getY() == c2.getY()) {
            return false;
        }
        if (checkLineY(board, c1, board.getCell(c2.getX(), c1.getY())) 
                && checkLineX(board, board.getCell(c2.getX(), c1.getY()), c2)) {
            return true;
        }
        return false;
    }
        
    // Kiểm tra xem 2 điểm có thể nối theo hình chữ Z không, xuất phát theo chiều ngang trước
    private boolean checkZX (Board board, Cell c1, Cell c2) {
        if (c1.getX() == c2.getX()) {
            return false;
        }
        for (int i = c1.getY(); i < c2.getY(); i++) {
            if (board.getCell(c1.getX(), i).isStatus() == true) {
                return false;
            }
            if (checkLY(board, c1, c2)) {
                return true;
            }
        }
        return false;
    }

    //  Kiểm tra xem 2 điểm có thể nối theo hình chữ Z không, xuất phát theo chiều dọc trước
    private boolean checkZY (Board board, Cell c1, Cell c2) {
        if (c1.getY() == c2.getY()) {
            return false;
        }
        for (int i = c1.getX(); i <= c2.getX(); i++) {
            if (board.getCell(i, c1.getY()).isStatus() == true) {
                return false;
            }
            if (checkLX(board, c1, c2)) {
            return true;
            }
        }
        return false;
    }
    
    //Kiểm tra xem 2 điểm có thể nối theo hình chữ U không, xuât phát theo chiểu ngang trước
    private boolean checkUX(Board board, Cell c1, Cell c2) {
        if (c1.getX() == c2.getX()) {
            return false;
        }
        int right = c1.getY() < c2.getY() ? c1.getY() : c2.getY();
        int left = c1.getY() > c2.getY() ? c1.getY() : c2.getY();
        for (int i = left; i <= board.getCols() + 1; i++) { // U ngang hở trái
            if (checkLineX(board, c1, board.getCell(c1.getX(), i))) {
                if (checkLineX(board, c2, board.getCell(c2.getX(), i))) {
                    if (checkLineY(board, board.getCell(c1.getX(), i), board.getCell(c2.getX(), i))) {
                        return true;
                    }
                } else {
                    break;
                }
            } else {
                break;
            }
        }
        
        for (int i = right; i >= 0; i--) { // U ngang hở phải
            if (checkLineX(board, c1, board.getCell(c1.getX(), i))) {
                if (checkLineX(board, c2, board.getCell(c2.getX(), i))) {
                    if (checkLineY(board, board.getCell(c1.getX(), i), board.getCell(c2.getX(), i))) {
                        return true;
                    }
                } else {
                    break;
                }
            } else {
                break;
            }
        }
        
        return false;
    }
    
    //Kiểm tra xem 2 điểm có thể nối theo hình chữ U không, xuât phát theo chiểu dọc trước
    private boolean checkUY(Board board, Cell c1, Cell c2) {
        
        if (c1.getY() == c2.getY()) {
            return false;
        }
        
        int above = c1.getX() < c2.getX() ? c1.getX() : c2.getX();
        int below = c1.getX() > c2.getX() ? c1.getX() : c2.getX();
        
        for (int i = below; i <= board.getRows()+ 1; i++) { // U đứng hở dưới
            if (checkLineY(board, c1, board.getCell(i, c1.getY()))) {
                if (checkLineY(board, c2, board.getCell(i, c2.getY()))) {
                    if (checkLineX(board, board.getCell(i, c1.getY()), board.getCell(i, c2.getY()))) {
                        return true;
                    }
                } else {
                    break;
                }
            } else {
                break;
            }
        }
        
        for (int i = above; i >= 0; i--) { // U đứng hở trên
            if (checkLineY(board, c1, board.getCell(i, c1.getY()))) {
                if (checkLineY(board, c2, board.getCell(i, c2.getY()))) {
                    if (checkLineX(board, board.getCell(i, c1.getY()), board.getCell(i, c2.getY()))) {
                        return true;
                    }
                } else {
                    break;
                }
            } else {
                break;
            }
        }
        
        return false;
    }
    
    @Override
    public boolean checkPath (Board board, Cell c1, Cell c2) {
        if (checkCoincident(c1, c2)) {            
            return false;
        }
        if (checkLineX(board, c1, c2)) {
            return true;
        }
        if (checkLineY(board, c1, c2)) {
            return true;
        }
        if (checkLX(board, c1, c2)) {
            return true;
        }
        if (checkLY(board, c1, c2)) {
            return true;
        }
        if (checkUX(board, c1, c2)) {
            return true;
        }
        if (checkUY(board, c1, c2)) {
            return true;
        }
        if (checkZX(board, c1, c2)) {
            return true;
        }
        if (checkZY(board, c1, c2)) {
            return true;
        }
        return false;
    }

    @Override
    public List<Point> getPath () {
        return list;
    }

    @Override
    public boolean hasAnyMatch (Board board) {
        for (Integer key : map.keySet()) {
            int size = map.get(key).size();
            for (int i = 0; i < size; i++) {
                if (map.get(key).get(i).isStatus() == true) {
                    for (int j = i + 1; j < size; j++) {
                        if (map.get(key).get(j).isStatus() == true) {
                            if (checkPath(board, map.get(key).get(i), map.get(key).get(j)) == true) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public CellPair findHint (Board board) {

        return null;
    }

    @Override
    public void shuffle(Board board) {
        ArrayList<Cell> allCells = new ArrayList<>();
        for(int i = 1; i <= board.getRows(); i++) {
            for(int j = 1; j <= board.getCols(); j++) {
                allCells.add(board.getCell(i, j));
            }
        }
        
        do {            
            Collections.shuffle(allCells);
            int index = 0;
            for (int i = 1; i <= board.getRows(); i++) {
                for (int j = 1; j <= board.getCols(); j++) {
                    board.setCell(i, j, allCells.get(index++));
                    board.getCell(i, j).setX(i);
                    board.getCell(i, j).setY(j);
                }
            }
            
            map.clear();
            for (int i = 1; i <= board.getRows(); i++) {
                for (int j = 1; j <= board.getCols(); j++) {
                    if (map.containsKey(board.getCell(i, j).getId()) == false) {
                        map.put(board.getCell(i, j).getId(), new ArrayList<>());
                    }
                    map.get(board.getCell(i, j).getId()).add(board.getCell(i, j));
                }
            }
            
        } while (hasAnyMatch(board));
    }
    
    @Override
    public void removePair(Cell c1, Cell c2, Board board) {
        list.clear();
        c1.setStatus(false);
        c2.setStatus(false);
        board.setTotalCells(board.getTotalCells() - 2);
    }    
    
}
