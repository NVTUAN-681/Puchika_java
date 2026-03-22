/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pikachu_master.Algorithm;

import com.mycompany.pikachu_master.Model.Board;
import com.mycompany.pikachu_master.Model.Cell;
import com.mycompany.pikachu_master.Model.CellPair;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
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
    private boolean checkLineX (Board board, int x1, int y1, int x2, int y2, boolean left_right) {
        if (x1 != x2 || y1 == y2) {
            return false;
        }
        
        int row = x1;
        int left = y1 < y2 ? y1 : y2;
	int right = y1 > y2 ? y1 : y2;
        list.add(new Point(x1, y1));
        if (left_right == true) {
            for (int i = left + 1; i < right; i++) {
                    if (board.getCell(row, i).isStatus() == true) {
                        list.clear();
                        return false;
                    }
                    list.add(new Point(row, i));
            }
        } else {
            for (int i = right - 1; i > left; i--) {
                    if (board.getCell(row, i).isStatus() == true) {
                        list.clear();
                        return false;
                    }
                    list.add(new Point(row, i));
            }
        }
        return true;
    }
    
    // Kiem tra xem co di duoc theo duong thang dung khong
    private boolean checkLineY (Board board, int x1, int y1, int x2, int y2, boolean above_below) {
	if (y1 != y2 || x1 == x2) {
            return false;
        }
        
        int col = y1;
	int above = x1 < x2 ? x1 : x2;
	int below = x1 > x2 ? x1 : x2;
        list.add(new Point(x1, y1));
        if (above_below == true) {
            for(int i = above + 1; i < below; i++) {     
                if (board.getCell(i, col).isStatus() == true) {
                    list.clear();
                    return false;
                }
                list.add(new Point(i, col));
            }
        } else {
            for(int i = below - 1; i > above; i--) {            
                if (board.getCell(i, col).isStatus() == true) {
                    list.clear();
                    return false;
                }
                list.add(new Point(i, col));
            } 
        }
        return true;
    }

    // Kiểm tra xem 2 điểm có thể nối theo hình chữ L không, xuất phát theo chiều ngang trước
    private boolean checkLX(Board board, int x1, int y1, int x2, int y2) {
        if (x1 == x2) {
            return false;
        }
        
        if (checkLineX(board, x1, y1, x1, y2 + (y1 < y2 ? 1 : -1), y1 < y2)
                && checkLineY(board, x1, y2, x2, y2, x1 < x2)) {
            return true;
        }
        
        return false;
    }    
    
    // Kiểm tra xem 2 điểm có thể nối theo hình chữ L không, xuất phát theo chiều dọc trước
    private boolean checkLY(Board board, int x1, int y1, int x2, int y2) {
        if (y1 == y2) {
            return false;
        }
        
        if (checkLineY(board, x1, y1, x2 + (x1 < x2 ? 1 : -1), y1, x1 < x2) 
                && checkLineX(board, x2, y1, x2, y2, y1 < y2)) {
            return true;
        }
        
        return false;
    }
        
    // Kiểm tra xem 2 điểm có thể nối theo hình chữ Z không, xuất phát theo chiều ngang trước
    private boolean checkZX (Board board, int x1, int y1, int x2, int y2) {
        if (x1 == x2) {
            return false;
        }
        
        for (int i = y1 + 1; i < y2; i++) {
            if (board.getCell(x1, i).isStatus() == true) {
                return false;
            }
            if (checkLineX(board, x1, y1, x1, i + (y1 < y2 ? 1 : -1), y1 < y2) 
                    && checkLY(board, x1, i, x2, y2)) {
                return true;
            }
        }
        
        for (int i = y1 - 1; i > y2; i--) {
            if (board.getCell(x1, i).isStatus() == true) {
                return false;
            }
            if (checkLineX(board, x1, y1, x1, i + (y1 < y2 ? 1 : -1), y1 < y2) 
                    && checkLY(board, x1, i, x2, y2)) {
                return true;
            }
        }
        
        return false;
    }

    //  Kiểm tra xem 2 điểm có thể nối theo hình chữ Z không, xuất phát theo chiều dọc trước
    private boolean checkZY (Board board, int x1, int y1, int x2, int y2) {
        if (y1 == y2) {
            return false;
        }
        
        for (int i = x1 + 1; i < x2; i++) {
            if (board.getCell(i, y1).isStatus() == true) {
                return false;
            }
            if (checkLineY(board, x1, y1, i + (x1 < x2 ? 1 : -1), y1, x1 < x2)
                    && checkLX(board, i, y1, x2, y2)) {
                return true;
            }
        }

        for (int i = x1 - 1; i > x2; i--) {
            if (board.getCell(i, y1).isStatus() == true) {
                return false;
            }
            if (checkLineY(board, x1, y1, i + (x1 < x2 ? 1 : -1), y1, x1 < x2)
                    && checkLX(board, i, y1, x2, y2)) {
                return true;
            }
        }
        
        return false;
    }
    
    //Kiểm tra xem 2 điểm có thể nối theo hình chữ U không, xuât phát theo chiểu ngang trước
    private boolean checkUX(Board board, int x1, int y1, int x2, int y2) {
        
        if (x1 == x2) {
            return false;
        }
        
        int right = y1 < y2 ? y1 : y2;
        int left = y1 > y2 ? y1 : y2;
        
        for (int i = right + 1; i <= board.getCols() + 1; i++) { // U ngang hở trái
            if (checkLineX(board, x1, y1, x1, i + 1, true)) {
                if (checkLineY(board, x1, i, x2 + (x1 < x2 ? 1 : -1), i, x1 < x2)) {
                    if (checkLineX(board, x2, i, x2, y2, false)) {
                        return true;
                    } else {
                        break;
                    }
                }
            } else {
                break;
            }
        }
        
        for (int i = left - 1; i >= 0; i--) { // U ngang hở phải
            if (checkLineX(board, x1, y1, x1, i - 1, false)) {
                if (checkLineY(board, x1, i, x2 + (x1 < x2 ? 1 : -1), i, x1 < x2)) {
                    if (checkLineX(board, x2, i, x2, y2, true)) {
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
    private boolean checkUY(Board board, int x1, int y1, int x2, int y2) {
        
        if (y1 == y2) {
            return false;
        }
        
        int above = x1 < x2 ? x1 : x2;
        int below = x1 > x2 ? x1 : x2;
        
        for (int i = above - 1; i >= 0; i--) { // U đứng hở dưới
            if (checkLineY(board, x1, y1, i - 1, y1, false)) {
                if (checkLineX(board, i, y1, i, y2 + (y1 < y2 ? 1 : -1), y1 < y2)) {
                    if (checkLineY(board, i, y2, x2, y2, true)) {
                        return true;
                    } else {
                        break;
                    }
                }
            } else {
                break;
            }
        }
        
        for (int i = below + 1; i <= board.getRows() + 1; i++) { // U đứng hở trên
            if (checkLineY(board, x1, y1, i + 1, y1, true)) {
                if (checkLineX(board, i, y1, i, y2 + (y1 < y2 ? 1 : -1), y1 < y2)) {
                    if (checkLineY(board, i, y2, x2, y2, false)) {
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
        list.clear();
        if (c1.getId() != c2.getId()) { //System.out.println("false id");
            return false;
        }
        if (checkCoincident(c1, c2)) { //System.out.println("trung nhau");
            return false;
        }
        if (checkLineX(board, c1.getX(), c1.getY(), c2.getX(), c2.getY(), c1.getY() < c2.getY())) { //System.out.println("true lineX");
            list.add(new Point(c2.getX(), c2.getY()));
            return true;
        }
        if (checkLineY(board, c1.getX(), c1.getY(), c2.getX(), c2.getY(), c1.getX() < c2.getX())) { //System.out.println("true lineY");
            list.add(new Point(c2.getX(), c2.getY()));
            return true;
        }
        if (checkLX(board, c1.getX(), c1.getY(), c2.getX(), c2.getY())) { //System.out.println("true lx");
            list.add(new Point(c2.getX(), c2.getY()));
            return true;
        }
        if (checkLY(board, c1.getX(), c1.getY(), c2.getX(), c2.getY())) { //System.out.println("true ly");
            list.add(new Point(c2.getX(), c2.getY()));
            return true;
        }
        if (checkZX(board, c1.getX(), c1.getY(), c2.getX(), c2.getY())) { //System.out.println("true zx");
            list.add(new Point(c2.getX(), c2.getY()));
            return true;
        }
        if (checkZY(board, c1.getX(), c1.getY(), c2.getX(), c2.getY())) { //System.out.println("true zy");
            list.add(new Point(c2.getX(), c2.getY()));
            return true;
        }
        if (checkUX(board, c1.getX(), c1.getY(), c2.getX(), c2.getY())) { //System.out.println("true ux");
            list.add(new Point(c2.getX(), c2.getY()));
            return true;
        }
        if (checkUY(board, c1.getX(), c1.getY(), c2.getX(), c2.getY())) { //System.out.println("true uy");
            list.add(new Point(c2.getX(), c2.getY()));
            return true;
        }
        return false;
    }

    @Override
    public List<Point> getPath () {
        List<Point> uniqueList = new ArrayList<>(new LinkedHashSet(list));
        return uniqueList;
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

//public boolean hasAnyMatch(Board board) {
//    System.out.println("=== hasAnyMatch ===");
//    System.out.println("Map keys: " + map.keySet());
//    for (Integer key : map.keySet()) {
//        System.out.print("Key " + key + ": ");
//        for (Cell c : map.get(key)) {
//            System.out.print("(" + c.getX() + "," + c.getY() + " status=" + c.isStatus() + " id=" + c.getId() + ") ");
//        }
//        System.out.println();
//    }
//    
//    for (Integer key : map.keySet()) {
//        List<Cell> cells = map.get(key);
//        int size = map.get(key).size();
//        for (int i = 0; i < size; i++) {
//            if (cells.get(i).isStatus() == true) {
//                Cell cellI = cells.get(i);
//                for (int j = i + 1; j < size; j++) {
//                    if (cells.get(j).isStatus() == true) {
//                        Cell cellJ = cells.get(j);
//                        System.out.println("Checking: (" + cellI.getX() + "," + cellI.getY() + ") vs (" + cellJ.getX() + "," + cellJ.getY() + ")");
//                        if (checkPath(board, cellI, cellJ) == true) {
//                            System.out.println("Found match!");
//                            return true;
//                        }
//                    }
//                }
//            }
//        }
//    }
//    System.out.println("No match found!");
//    return false;
//}

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

//    public boolean checkPathfix(Board board, Cell c1, Cell c2) {
//        list.clear();
//        System.out.println("checkPath: c1=(" + c1.getX() + "," + c1.getY() + ") id=" + c1.getId() 
//                         + " c2=(" + c2.getX() + "," + c2.getY() + ") id=" + c2.getId());
//        if (c1.getId() != c2.getId()) {
//            System.out.println("false id: " + c1.getId() + " != " + c2.getId());
//            return false;
//        }
//        if (checkCoincident(c1, c2)) return false;
//
//        list.clear();
//        boolean lx = checkLineX(board, c1.getX(), c1.getY(), c2.getX(), c2.getY(), c1.getY() < c2.getY());
//        System.out.println("checkLineX: " + lx);
//
//        list.clear();
//        boolean ly = checkLineY(board, c1.getX(), c1.getY(), c2.getX(), c2.getY(), c1.getX() < c2.getX());
//        System.out.println("checkLineY: " + ly);
//
//        list.clear();
//        boolean lxCheck = checkLX(board, c1.getX(), c1.getY(), c2.getX(), c2.getY());
//        System.out.println("checkLX: " + lxCheck);
//
//        list.clear();
//        boolean lyCheck = checkLY(board, c1.getX(), c1.getY(), c2.getX(), c2.getY());
//        System.out.println("checkLY: " + lyCheck);
//
//        list.clear();
//        boolean zx = checkZX(board, c1.getX(), c1.getY(), c2.getX(), c2.getY());
//        System.out.println("checkZX: " + zx);
//
//        list.clear();
//        boolean zy = checkZY(board, c1.getX(), c1.getY(), c2.getX(), c2.getY());
//        System.out.println("checkZY: " + zy);
//
//        list.clear();
//        boolean ux = checkUX(board, c1.getX(), c1.getY(), c2.getX(), c2.getY());
//        System.out.println("checkUX: " + ux);
//
//        list.clear();
//        boolean uy = checkUY(board, c1.getX(), c1.getY(), c2.getX(), c2.getY());
//        System.out.println("checkUY: " + uy);
//
//        if (lx) { list.clear(); checkLineX(board, c1.getX(), c1.getY(), c2.getX(), c2.getY(), c1.getY() < c2.getY()); list.add(new Point(c2.getX(), c2.getY())); return true; }
//        if (ly) { list.clear(); checkLineY(board, c1.getX(), c1.getY(), c2.getX(), c2.getY(), c1.getX() < c2.getX()); list.add(new Point(c2.getX(), c2.getY())); return true; }
//        if (lxCheck) { list.clear(); checkLX(board, c1.getX(), c1.getY(), c2.getX(), c2.getY()); list.add(new Point(c2.getX(), c2.getY())); return true; }
//        if (lyCheck) { list.clear(); checkLY(board, c1.getX(), c1.getY(), c2.getX(), c2.getY()); list.add(new Point(c2.getX(), c2.getY())); return true; }
//        if (zx) { list.clear(); checkZX(board, c1.getX(), c1.getY(), c2.getX(), c2.getY()); list.add(new Point(c2.getX(), c2.getY())); return true; }
//        if (zy) { list.clear(); checkZY(board, c1.getX(), c1.getY(), c2.getX(), c2.getY()); list.add(new Point(c2.getX(), c2.getY())); return true; }
//        if (ux) { list.clear(); checkUX(board, c1.getX(), c1.getY(), c2.getX(), c2.getY()); list.add(new Point(c2.getX(), c2.getY())); return true; }
//        if (uy) { list.clear(); checkUY(board, c1.getX(), c1.getY(), c2.getX(), c2.getY()); list.add(new Point(c2.getX(), c2.getY())); return true; }
//
//        return false;
//    }
}
