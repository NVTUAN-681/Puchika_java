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
        //b.initBoard(a, 9);
//        int[][] values = {
//            {1, 1, 2, 1, 1, 1},
//            {1, 1, 1, 1, 1, 1},
//            {1, 1, 3, 1, 3, 1},
//            {1, 1, 1, 2, 1, 1},
//            {1, 1, 1, 1, 1, 1},
//            {1, 1, 1, 1, 1, 1}
//        };
        b.initHardBoard(a, 20, true);
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
                if (b.getTotalCells() > 0 && a.hasAnyMatch(b) == false) {
                    a.shuffle(b);
                }                
            }

        } while (b.getTotalCells() > 0);
        System.out.println("The end!");
    }
}