/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pikachu_master.Data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author DELL
 */
public class ScoreDAO {
    public void insertScore(String name, String level, int score, int time) {
    String sql = "INSERT INTO PlayerScore(playerName, levelName, Score, timePlayed, playDate) VALUES(?,?,?,?,DATETIME('now'))";
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setString(1, name);
        pstmt.setString(2, level);
        pstmt.setInt(3, score);
        pstmt.setInt(4, time);
        pstmt.executeUpdate();
        System.out.println("success update");
    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }
}
}
