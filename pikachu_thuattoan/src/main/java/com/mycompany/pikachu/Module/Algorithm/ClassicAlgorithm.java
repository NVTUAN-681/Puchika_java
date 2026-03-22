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
public class ClassicAlgorithm implements IAlgorithm{
    
    private Map<Integer, List<Cell>> map;
    private List<Point> list;

    public ClassicAlgorithm() {
        map = new HashMap<>();
        list = new ArrayList<>();
    }

    public ClassicAlgorithm(Map<Integer, List<Cell>> map, List<Point> list) {
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
    private boolean checkLineX (Board board, Cell c1, Cell c2, boolean left_right) {
        if (c1.getX() != c2.getX()) {
            return false;
        }
        
        int row = c1.getX();
	int left = c1.getY() < c2.getY() ? c1.getY() : c2.getY();
	int right = c1.getY() > c2.getY() ? c1.getY() : c2.getY();
        boolean status1 = c1.isStatus(), status2 = c2.isStatus();
        c1.setStatus(false);
        c2.setStatus(false);
        if (left_right == true) {
            for (int i = left; i <= right; i++) {
                    if (board.getCell(row, i).isStatus() == true) {
                        if (status1) {
                            c1.setStatus(true);
                        }
                        if (status2) {
                            c2.setStatus(true);
                        }
                        list.clear();
                        return false;
                    }
                    list.add(new Point(row, i));
            }
            if (status1) {
                c1.setStatus(true);
            }
            if (status2) {
                c2.setStatus(true);
            }
            return true;
        } else {
            for (int i = right; i >= left; i--) {
                    if (board.getCell(row, i).isStatus() == true) {
                        if (status1) {
                            c1.setStatus(true);
                        }
                        if (status2) {
                            c2.setStatus(true);
                        }
                        list.clear();
                        return false;
                    }
                    list.add(new Point(row, i));
            }
            if (status1) {
                c1.setStatus(true);
            }
            if (status2) {
                c2.setStatus(true);
            }
            return true;             
        }
    }
    
    // Kiem tra xem co di duoc theo duong thang dung khong
    private boolean checkLineY (Board board, Cell c1, Cell c2, boolean above_below) {
	if (c1.getY() != c2.getY()) {
            return false;
        }
        
        int col = c1.getY();
	int above = c1.getX() < c2.getX() ? c1.getX() : c2.getX();
	int below = c1.getX() > c2.getX() ? c1.getX() : c2.getX();
        boolean status1 = c1.isStatus(), status2 = c2.isStatus();
        c1.setStatus(false);
        c2.setStatus(false);
        if (above_below == true) {
            for(int i = above; i <= below; i++) {            
                if (board.getCell(i, col).isStatus() == true) {
                    if (status1) {
                        c1.setStatus(true);
                    }
                    if (status2) {
                        c2.setStatus(true);
                    }
                    list.clear();
                    return false;
                }
                list.add(new Point(i, col));
            }
            if (status1) {
                c1.setStatus(true);
            }
            if (status2) {
                c2.setStatus(true);
            }          
            return true;            
        } else {
            for(int i = below; i >= above; i--) {            
                if (board.getCell(i, col).isStatus() == true) {
                    if (status1) {
                        c1.setStatus(true);
                    }
                    if (status2) {
                        c2.setStatus(true);
                    }
                    list.clear();
                    return false;
                }
                list.add(new Point(i, col));
            }
            if (status1) {
                c1.setStatus(true);
            }
            if (status2) {
                c2.setStatus(true);
            }           
            return true; 
        }
    }

    // Kiểm tra xem 2 điểm có thể nối theo hình chữ L không, xuất phát theo chiều ngang trước
    private boolean checkLX(Board board, Cell c1, Cell c2) {
        if (c1.getX() == c2.getX()) {
            return false;
        }
        
        if (checkLineX(board, c1, board.getCell(c1.getX(), c2.getY()), (c1.getY() < c2.getY())) 
                && checkLineY(board, board.getCell(c1.getX(), c2.getY()), c2, (c1.getX() < c2.getX()))) {
            return true;
        }
        
        return false;
    }    
    
    // Kiểm tra xem 2 điểm có thể nối theo hình chữ L không, xuất phát theo chiều dọc trước
    private boolean checkLY(Board board, Cell c1, Cell c2) {
        if (c1.getY() == c2.getY()) {
            return false;
        }
        
        if (checkLineY(board, c1, board.getCell(c2.getX(), c1.getY()), (c1.getX() < c2.getX())) 
                && checkLineX(board, board.getCell(c2.getX(), c1.getY()), c2, (c1.getY() < c2.getY()))) {
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
            if (checkLineX(board, c1, board.getCell(c1.getX(), i), (c1.getY() < c2.getY())) 
                    && checkLY(board, board.getCell(c1.getX(), i), c2)) {
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
            if (checkLineY(board, c1, board.getCell(i, c1.getY()), (c1.getX() < c2.getX())) 
                    && checkLX(board, board.getCell(i, c1.getY()), c2)) {
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
        for (int i = right + 1; i <= board.getCols() + 1; i++) { // U ngang hở trái
            if (checkLineX(board, c1, board.getCell(c1.getX(), i), true)) {
                if (checkLineY(board, board.getCell(c1.getX(), i), board.getCell(c2.getX(), i), (c1.getX() < c2.getX()))) {
                    if (checkLineX(board, c2, board.getCell(c2.getX(), i), false)) {
                        return true;
                    } else {
                        break;
                    }
                }
            } else {
                break;
            }
        }
        
        for (int i = right; i >= 0; i--) { // U ngang hở phải
            if (checkLineX(board, c1, board.getCell(c1.getX(), i), false)) {
                if (checkLineY(board, board.getCell(c1.getX(), i), board.getCell(c2.getX(), i), (c1.getX() < c2.getX()))) {
                    if (checkLineX(board, c2, board.getCell(c2.getX(), i), true)) {
                        return true;
                    } else {
                        break;
                    }
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
            if (checkLineY(board, c1, board.getCell(i, c1.getY()), false)) {
                if (checkLineX(board, board.getCell(i, c1.getY()), board.getCell(i, c2.getY()), (c1.getY() < c2.getY()))) {
                    if (checkLineY(board, c2, board.getCell(i, c2.getY()), true)) {
                        return true;
                    } else {
                        break;
                    }
                }
            } else {
                break;
            }
        }
        
        for (int i = above; i >= 0; i--) { // U đứng hở trên
            if (checkLineY(board, c1, board.getCell(i, c1.getY()), true)) {
                if (checkLineX(board, board.getCell(i, c1.getY()), board.getCell(i, c2.getY()), (c1.getY() < c2.getY()))) {
                    if (checkLineY(board, c2, board.getCell(i, c2.getY()), false)) {
                        return true;
                    } else {
                        break;
                    }
                }
            } else {
                break;
            }
        }
        
        return false;
    }
    
    @Override
    public boolean checkPath (Board board, Cell c1, Cell c2) {
        if (c1.getId() != c2.getId()) {
            return false;
        }
        if (checkCoincident(c1, c2)) {            
            return false;
        }
        if (checkLineX(board, c1, c2, true)) {
            return true;
        }
        if (checkLineY(board, c1, c2, true)) {
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
            List<Cell> cells = map.get(key);
            int size = map.get(key).size();
            for (int i = 0; i < size; i++) {
                if (cells.get(i).isStatus() == true) {
                    Cell cellI = cells.get(i);
                    for (int j = i + 1; j < size; j++) {
                        if (cells.get(j).isStatus() == true) {
                            Cell cellJ = cells.get(j);
                            if (checkPath(board, cellI, cellJ) == true) {
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
        return new CellPair(board.getCell(list.getFirst().x, list.getFirst().y), 
                board.getCell(list.getLast().x, list.getLast().y));
    }

    @Override
    public void shuffle(Board board) {
        List<Integer> allCellsAvalible = new ArrayList<>();
        for(int i = 1; i <= board.getRows(); i++) {
            for(int j = 1; j <= board.getCols(); j++) {
                if (board.getCell(i, j).isStatus()) {
                    allCellsAvalible.add(board.getCell(i, j).getId());
                }                
            }
        }
//            int count = 0;
//            for (int i = 1; i <= board.getRows(); i++) {
//                for (int j = 1; j <= board.getCols(); j++) {
//                    if (board.getCell(i, j).isStatus()) {
//                        count++;
//                    }
//                }
//            }
//            System.out.println("Count = " + count);
//            System.out.println("allCellsAvalible = " + allCellsAvalible.size());
        
        do {            
            Collections.shuffle(allCellsAvalible);
            int index = 0;
            for (int i = 1; i <= board.getRows(); i++) {
                for (int j = 1; j <= board.getCols(); j++) {
                    if (board.getCell(i, j).isStatus()) {
                        board.getCell(i, j).setId(allCellsAvalible.get(index++));
                    }
                }
            }
            
            map.clear();
            for (int i = 1; i <= board.getRows(); i++) {
                for (int j = 1; j <= board.getCols(); j++) {
                    if (board.getCell(i, j).isStatus() && map.containsKey(board.getCell(i, j).getId()) == false) {
                        map.put(board.getCell(i, j).getId(), new ArrayList<>());
                    }
                    if (board.getCell(i, j).isStatus()) {
                        map.get(board.getCell(i, j).getId()).add(board.getCell(i, j));
                    }
                }
            }
            
        } while (hasAnyMatch(board) == false);
    }
    
    @Override
    public void removePair(Cell c1, Cell c2, Board board) {
        list.clear();
        c1.setStatus(false);
        c2.setStatus(false);
        board.setTotalCells(board.getTotalCells() - 2);
    }    
    
}
