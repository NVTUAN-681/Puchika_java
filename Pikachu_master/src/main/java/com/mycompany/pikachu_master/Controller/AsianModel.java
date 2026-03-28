/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pikachu_master.Controller;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 *
 * @author laptop
 */
public class AsianModel {
    private Timer asianTimer;
    private int asianTick = 0;
    private boolean isHiddenPhase = false;
    private PlayScreen playScreen;

    public AsianModel(PlayScreen playScreen) {
        this.playScreen = playScreen;
        
        asianTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                asianTick++;
                if (!isHiddenPhase && asianTick >= 10) {
                    isHiddenPhase = true;
                    asianTick = 0;
                    playScreen.updateAllButtons();
                } else if (isHiddenPhase && asianTick >= 5) {
                    isHiddenPhase = false;
                    asianTick = 0;
                    playScreen.updateAllButtons();
                }
            }
        });
    }

    public void start() {
        if (asianTimer != null) asianTimer.start();
    }

    public void stop() {
        if (asianTimer != null) asianTimer.stop();
    }

    public void pause() {
        if (asianTimer != null && asianTimer.isRunning()) asianTimer.stop();
    }

    public void resume() {
        if (asianTimer != null && !asianTimer.isRunning()) asianTimer.start();
    }

    public void reset() {
        isHiddenPhase = false;
        asianTick = 0;
    }

    public boolean isHiddenPhase() {
        return isHiddenPhase;
    }
}
