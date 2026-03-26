/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.pikachu_master;

import com.mycompany.pikachu_master.Algorithm.ClassicAlgorithm;
import com.mycompany.pikachu_master.Algorithm.IAlgorithm;
import com.mycompany.pikachu_master.Algorithm.MediumModeAlgorithm;

import com.mycompany.pikachu_master.Model.Board;
import com.mycompany.pikachu_master.Model.Cell;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Admin
 */
public class Pikachu {

    public static void main(String[] args) {
        //System.out.println("Hello World!");
        Board b = new Board(6, 6, true);
        IAlgorithm a = new MediumModeAlgorithm();
        System.out.println("Hello world!");
        b.initBoard(a, 9);
//        int[][] values = {
//            {1, 1, 1, 1, 1, 1},
//            {1, 1, 1, 1, 1, 1},
//            {1, 1, 1, 1, 1, 1},
//            {1, 1, 1, 1, 1, 1},
//            {1, 1, 1, 1, 1, 1},
//            {1, 1, 1, 1, 1, 1}
//        };
//        b.initBoardFixed(a, values);
        System.out.println("Hello world!");
        //System.out.println("Hello World!");
        Scanner sc = new Scanner(System.in);
        int x1, y1, x2, y2;
        
        do {
            b.printFull();
            //System.out.println("");
            //b.printStatus();
            //b.printCoordinates();
            //System.out.println(((ClassicAlgorithm) a).getMap());
            x1 = sc.nextInt();
            y1 = sc.nextInt();
            x2 = sc.nextInt();
            y2 = sc.nextInt();
            if (a.checkPath(b, b.getCell(x1, y1), b.getCell(x2, y2))) {
                System.out.println(a.getPath());
                a.removePair(b.getCell(x1, y1), b.getCell(x2, y2), b);
                // Nếu ăn trúng 2 ô tên lửa
                if (b.getCell(x1, y1).getId() == 1) {
                    for (Integer key : a.getMap().keySet()) {
                        if (key > 1) {
                            List<Cell> cells = a.getMap().get(key);
                            int size = a.getMap().get(key).size();
                            for (int i = 0; i < size; i++) {
                                if (cells.get(i).isStatus() == true) {
                                    Cell cellI = cells.get(i);
                                    for (int j = i + 1; j < size; j++) {
                                        if (cells.get(j).isStatus() == true) {
                                            Cell cellJ = cells.get(j);
                                            a.removePair(cellI, cellJ, b);
                                        }
                                    }
                                }
                            }                 
                        }

                    }
                }
                if (b.getTotalCells() > 0 && a.hasAnyMatch(b) == false) {
                    a.shuffle(b);
                }                
            }

        } while (b.getTotalCells() > 0);
        System.out.println("The end!");
    }
}