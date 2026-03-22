/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.pikachu_master;

import com.mycompany.pikachu_master.Algorithm.ClassicAlgorithm;
import com.mycompany.pikachu_master.Algorithm.IAlgorithm;

import com.mycompany.pikachu_master.Model.Board;
import com.mycompany.pikachu_master.User_Interface.Screens.MainScreen;
import java.util.Scanner;

/**
 *
 * @author Admin
 */
public class Pikachu {

    public static void main(String[] args) {
        Board b = new Board(100, 100);
        IAlgorithm a = new ClassicAlgorithm();
        System.out.println("Hello world!");
        b.initBoard(a);
        Scanner sc = new Scanner(System.in);
        int x1, y1, x2, y2;
        
        do {
            b.print();
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
//        System.out.println("The end!");
//            java.awt.EventQueue.invokeLater(() -> {
//                    new MainScreen().setVisible(true);
//                });
    }
}
