/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pikachu_master.Utils;
import javax.sound.sampled.*;
import java.io.File;
import java.net.URL;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
/**
 *
 * @author DELL
 */
public class SoundLoad {
    private Clip bgmClip;
    
    // Phát nhạc nền lặp vô tận
    public void playBGM(String resourcePath) {
        try {
            // Dùng URL thay vì File để đọc từ thư mục resources/source của Project
            URL url = getClass().getResource(resourcePath);
            if (url != null) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(url);
                bgmClip = AudioSystem.getClip();
                bgmClip.open(audioInput);
                bgmClip.loop(Clip.LOOP_CONTINUOUSLY); 
                bgmClip.start();
                System.out.println("Đã tìm thấy và đang phát nhạc nền!");
            } else {
                System.err.println("LỖI: Không tìm thấy file nhạc nền tại: " + resourcePath);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Dừng nhạc nền
    public void stopBGM() {
        if (bgmClip != null && bgmClip.isRunning()) {
            bgmClip.stop();
        }
    }

 // Phát âm thanh 1 lần (click chuột, ăn điểm...)
    public void playSoundEffect(String resourcePath) {
        try {
            URL url = getClass().getResource(resourcePath);
            if (url != null) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(url);
                Clip effectClip = AudioSystem.getClip();
                effectClip.open(audioInput);
                effectClip.start();
            } else {
                System.err.println("LỖI: Không tìm thấy file âm thanh tại: " + resourcePath);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
