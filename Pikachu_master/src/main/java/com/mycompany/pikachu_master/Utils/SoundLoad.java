/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pikachu_master.Utils;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
/**
 *
 * @author DELL
 */
public class SoundLoad {
    // Biến trạng thái bật/tắt (mặc định là bật)
    public static boolean isSfxOn = true;
    public static boolean isBgmOn = true;
    
    // Biến lưu trữ nhạc nền để có thể dừng nó lại
    private static Clip bgmClip;

    // Hàm phát nhạc nền (Lặp đi lặp lại)
    public static void playBGM(String filepath) {
        if (!isBgmOn) return; // Nếu đang tắt thì không phát
        
        try {
            // Dừng nhạc cũ nếu đang phát
            if (bgmClip != null && bgmClip.isRunning()) {
                bgmClip.stop();
            }
            
            File musicPath = new File(filepath);
            if (musicPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                bgmClip = AudioSystem.getClip();
                bgmClip.open(audioInput);
                bgmClip.loop(Clip.LOOP_CONTINUOUSLY); // Lệnh lặp vô hạn
                bgmClip.start();
            } else {
                System.out.println("Không tìm thấy file nhạc: " + filepath);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Hàm dừng nhạc nền
    public static void stopBGM() {
        if (bgmClip != null && bgmClip.isRunning()) {
            bgmClip.stop();
        }
    }

    // Hàm phát tiếng thao tác (Chỉ phát 1 lần)
    public static void playSFX(String filepath) {
        if (!isSfxOn) return; // Nếu đang tắt SFX thì không phát
        
        try {
            File soundPath = new File(filepath);
            if (soundPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(soundPath);
                Clip sfxClip = AudioSystem.getClip();
                sfxClip.open(audioInput);
                sfxClip.start(); // Chỉ phát 1 lần
            } else {
                System.out.println("Không tìm thấy file âm thanh: " + filepath);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
