/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pikachu_master.Data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author DELL
 */
public class ScoreDAO {
    
    String name;
    String level;
    int score;
    int time;

    public ScoreDAO() {
    }

    public ScoreDAO(String name, String level, int score, int time) {
        this.name = name;
        this.level = level;
        this.score = score;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
    
    
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
    
    public void savePlayer(String username, int coins) {
    String sql = "INSERT OR REPLACE INTO User(UserName, TotalCoin) VALUES(?, ?)";
    try (Connection conn = DatabaseConnection.getConnection(); // Hàm kết nối DB của Tuấn
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setString(1, username);
        pstmt.setInt(2, coins);
        pstmt.executeUpdate();
    } catch (SQLException e) {
        System.out.println("error save Player: " + e.getMessage());
        }   
    }
    
    public void updateHighScore(String LevelName, int score) {
    // Chỉ cập nhật nếu điểm mới cao hơn điểm cũ
    String sql = "INSERT INTO HighScore(LevelName, MaxScore, DateAchieved) VALUES(?, ?, DATETIME('now')) " +
                 "ON CONFLICT(LevelName) DO UPDATE SET " +
                 "MaxScore = excluded.MaxScore " +
                 "WHERE excluded.MaxScore > HighScore.MaxScore";
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setString(1, LevelName);
        pstmt.setInt(2, score);
        pstmt.executeUpdate();
    } catch (SQLException e) {
        System.out.println("error save HighScore: " + e.getMessage());
        }
    }
    
    public int getHighScoreByLevel(String levelName) {
    String sql = "SELECT MaxScore FROM HighScore WHERE LevelName = ?";
    try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, levelName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("MaxScore");
            }
        } 
    catch (SQLException e) {
        System.out.println("error take HighScore: " + e.getMessage());
        }
    return 0; // Trả về 0 nếu chưa có kỷ lục
    }
    
    public int getTotalCoin(String username) {
    String sql = "SELECT total_coin FROM Players WHERE username = ?";
    try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("total_coin");
            }
        }
    catch (SQLException e) {
        System.out.println("Lỗi lấy Coin: " + e.getMessage());
    }
    return 0;
    }
}
