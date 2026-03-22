/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.pikachu;

import com.mycompany.pikachu.Module.Algorithm.ClassicAlgorithm;
import com.mycompany.pikachu.Module.Algorithm.IAlgorithm;

import com.mycompany.pikachu.Module.Model.Board;
import com.mycompany.pikachu.Module.Model.Cell;
import java.util.Scanner;

/**
 *
 * @author Admin
 */
public class Pikachu {

    public static void main(String[] args) {
        //System.out.println("Hello World!");
        Board b = new Board(2, 1);
        IAlgorithm a = new ClassicAlgorithm();
        b.initBoard(2, 1, a);
        //System.out.println("Hello World!");
        Scanner sc = new Scanner(System.in);
        int x1, y1, x2, y2;
        
        do {
            b.print();
            x1 = sc.nextInt();
            y1 = sc.nextInt();
            x2 = sc.nextInt();
            y2 = sc.nextInt();
            if (a.checkPath(b, b.getCell(x1, y1), b.getCell(x2, y2))) {
                a.removePair(b.getCell(x1, y1), b.getCell(x2, y2), b);
            }
            if (b.getTotalCells() > 0 && a.hasAnyMatch(b) == false) {
                a.shuffle(b);
            }
        } while (b.getTotalCells() > 0);
        System.out.println("The end!");
    }
}
