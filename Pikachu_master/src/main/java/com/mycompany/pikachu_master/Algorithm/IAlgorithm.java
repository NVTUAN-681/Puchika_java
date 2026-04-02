/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.pikachu_master.Algorithm;

import com.mycompany.pikachu_master.Model.Board;
import com.mycompany.pikachu_master.Model.Cell;
import com.mycompany.pikachu_master.Model.CellPair;
import java.awt.Point;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Admin
 */
public interface IAlgorithm {

    /**
     *
     * @param c1
     * @param c2
     * @return
     */
    Map<Integer, List<Cell>> getMap();
    public boolean checkPath (Board board, Cell c1, Cell c2);
    public List<Point> getPath ();
    public boolean hasAnyMatch (Board board);
    public CellPair findHint (Board board);
    public void shuffle(Board board);
    public CellPair removePair(Cell c1, Cell c2, Board board);
}
