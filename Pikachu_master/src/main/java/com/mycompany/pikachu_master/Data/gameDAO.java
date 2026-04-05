/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pikachu_master.Data;

import com.mycompany.pikachu_master.Controller.GameConfig;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author DELL
 */
public class gameDAO {
    
    String name;
    String level;
    int score;
    int time;

    public gameDAO() {
    }

    public gameDAO(String name, String level, int score, int time) {
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
    
    public void updateCoin_player(String username, int coins) {
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
    
    public void updateHighScore(String LevelName, int score, int timeRemain) {
    // Chỉ cập nhật nếu điểm mới cao hơn điểm cũ
    String sql = "INSERT INTO HighScore(LevelName, MaxScore, TimeRemain) VALUES(?, ?, ?) " +
                 "ON CONFLICT(LevelName) DO UPDATE SET " +
                 "MaxScore = excluded.MaxScore " +
                 "WHERE excluded.MaxScore > HighScore.MaxScore";
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setString(1, LevelName);
        pstmt.setInt(2, score);
        pstmt.setInt(3, timeRemain);
        pstmt.executeUpdate();
    } catch (SQLException e) {
        System.out.println("error save HighScore: " + e.getMessage());
        }
    }
    
    public void saveCurrentGame(String username, String level, int score, int time, int hints, int shuffles, String matrix) {
        String sql = "INSERT OR REPLACE INTO CurrentGameSave(UserName, LevelName, CurrentScore, TimeRemain, HintCount, ShuffleCount, MatrixData) "
                   + "VALUES(?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, level);
            pstmt.setInt(3, score);
            pstmt.setInt(4, time);
            pstmt.setInt(5, hints);
            pstmt.setInt(6, shuffles);
            pstmt.setString(7, matrix);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error save game: " + e.getMessage());
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
    System.out.println("Điểm cao của " + levelName + " là: " + score);
    return 0; // Trả về 0 nếu chưa có kỷ lục
    }
    
    public int getTimeRemainHighScoreByLevel(String levelName) {
    String sql = "SELECT TimeRemain FROM HighScore WHERE LevelName = ?";
    try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, levelName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("TimeRemain");
            }
        } 
    catch (SQLException e) {
        System.out.println("error take HighScore: " + e.getMessage());
        }
    System.out.println("Điểm cao của " + levelName + " là: " + score);
    return 0; // Trả về 0 nếu chưa có kỷ lục
    }
    
    public int getTotalCoin(String username) {
    String sql = "SELECT TotalCoin FROM User WHERE username = ?";
    try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("TotalCoin");
            }
        }
    catch (SQLException e) {
        System.out.println("Lỗi lấy Coin: " + e.getMessage());
    }
    return 0;
    }
    
    public GameConfig getSavedGame(String LevelName) {
    String sql = "SELECT * FROM CurrentGameSave WHERE LevelName = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, LevelName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                GameConfig savedConfig = new GameConfig(rs.getString("LevelName"));
                savedConfig.setScore(rs.getInt("CurrentScore"));
                savedConfig.setTimeLimit(rs.getInt("TimeRemain"));
                savedConfig.setHintCount(rs.getInt("HintCount"));
                savedConfig.setShuffleCount(rs.getInt("ShuffleCount"));
                savedConfig.setMatrix_data(rs.getString("MatrixData"));
                savedConfig.setResume(true); // Đánh dấu đây là ván chơi tiếp
                return savedConfig;
            }
        } catch (SQLException e) {
            System.out.println("error load old match: " + e.getMessage());
        }
    return null;
}
    
    public void deleteSaveGame(String levelname) {
       String sql = "DELETE FROM CurrentGameSave WHERE LevelName = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, levelname);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error : delete gamesave " + e.getMessage());
        }
    }
    
public void clearAllGameSaves() {
    // 1. Tạo danh sách các bảng cần xóa (Thứ tự: bảng con trước, bảng cha sau)
    String[] tables = {"CurrentGameSave", "HighScore", "PlayerScore", "User"};

    try (Connection conn = DatabaseConnection.getConnection();
         Statement stmt = conn.createStatement()) {

        // 2. Tạm tắt kiểm tra khóa ngoại (SQLite) để xóa không bị lỗi ràng buộc
        stmt.execute("PRAGMA foreign_keys = OFF;");

        // 3. Sử dụng Batch để gom nhóm các lệnh lại
        for (String table : tables) {
            stmt.addBatch("DELETE FROM " + table);
            // Tùy chọn: Reset ID tự tăng về 1
            stmt.addBatch("DELETE FROM sqlite_sequence WHERE name='" + table + "'");
        }

        // 4. Thực thi toàn bộ các lệnh trong Batch
        int[] results = stmt.executeBatch();
        
        // 5. Bật lại kiểm tra khóa ngoại
        stmt.execute("PRAGMA foreign_keys = ON;");

        System.out.println("has delete all Database!");

    } catch (SQLException e) {
        System.out.println("Error delete Database: " + e.getMessage());
    }
}
}
