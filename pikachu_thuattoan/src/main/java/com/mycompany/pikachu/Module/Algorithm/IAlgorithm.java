/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.pikachu.Module.Algorithm;

import com.mycompany.pikachu.Module.Model.Board;
import com.mycompany.pikachu.Module.Model.Cell;
import com.mycompany.pikachu.Module.Model.CellPair;
import java.awt.Point;
import java.util.List;

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
    public boolean checkPath (Board board, Cell c1, Cell c2);
    public List<Point> getPath ();
    public boolean hasAnyMatch (Board board);
    public CellPair findHint (Board board);
    public void shuffle(Board board);
    public void removePair(Cell c1, Cell c2, Board board);
}
