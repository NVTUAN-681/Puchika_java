/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pikachu_master.Effect;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.Timer;
/**
 *
 * @author laptop
 */
public class FireWorks extends JPanel{
   private ArrayList<Particle> particles = new ArrayList<>();
    private Random rand = new Random();
    private Timer timer;

    // ---> THÔNG SỐ VẬT LÝ SIÊU THỰC TẾ <---
    private final double GRAVITY = 0.18;      // Trọng lực kéo hạt rơi xuống
    private final double AIR_FRICTION = 0.94; // Lực cản không khí (càng nhỏ hạt càng nhanh dừng lại)

    public FireWorks() {
        setOpaque(false); // Kính trong suốt
        // Chạy 30ms/frame (~33 FPS) để chuyển động mượt mà
        timer = new Timer(30, e -> {
            updateParticles();
            repaint();
        });
    }

    public void start() {
        timer.start();
    }

    public void stop() {
        timer.stop();
        particles.clear();
    }

    @Override
    public boolean contains(int x, int y) {
        return false; // Hút click cho phép bấm xuyên qua
    }

    private void updateParticles() {
        // Lâu lâu bắn một quả lên (Tỉ lệ 5%, vừa đủ ngắm, không bị rác màn hình)
        if (rand.nextInt(100) < 5) {
            // Quả pháo xuất phát từ cạnh dưới màn hình
            particles.add(new Particle(rand.nextInt(getWidth()), getHeight(), true, null, 0, 0));
        }

        ArrayList<Particle> newParticles = new ArrayList<>();
        Iterator<Particle> it = particles.iterator();

        while (it.hasNext()) {
            Particle p = it.next();
            p.update();

            // Nổ pháo khi bay đến đỉnh (vận tốc dọc vy bắt đầu rơi xuống)
            if (p.isRocket && p.vy >= -0.5 || p.y < getHeight()* 0.2) {
                p.life = 0; // Kích hoạt tự hủy
            }

            // Xử lý khi hạt chết
            if (p.life <= 0) {
                it.remove();
                if (p.isRocket) {
                    // ---> BÙM! VỤ NỔ ĐẦY MÀU SẮC <---
                    Color burstColor = getRandomBrightColor();
                    Color secondaryColor = getRandomBrightColor(); // Trộn thêm màu thứ 2 cho sinh động

                    // Sinh ra 150-200 tia lửa
                    int sparkCount = rand.nextInt(50) + 150;
                    for (int i = 0; i < sparkCount; i++) {
                        // Tỉ lệ 70% tia lửa mang màu chính, 30% mang màu phụ
                        Color sparkColor = (rand.nextInt(10) < 7) ? burstColor : secondaryColor;
                        
                        // Truyền cả vận tốc cũ của quả pháo (p.vx, p.vy) để tia lửa kế thừa gia tốc
                        newParticles.add(new Particle(p.x, p.y, false, sparkColor, p.vx, p.vy));
                    }
                }
            }
        }
        
        // Đổ rổ tạm vào danh sách chính
        particles.addAll(newParticles);
    }

    private Color getRandomBrightColor() {
        float hue = rand.nextFloat();
        // Giữ độ bão hòa và độ sáng cao để màu luôn rực rỡ như Neon
        float saturation = 0.6f + rand.nextFloat() * 0.4f; 
        float brightness = 0.8f + rand.nextFloat() * 0.2f; 
        return Color.getHSBColor(hue, saturation, brightness);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        // Bật khử răng cưa cho nét vẽ mượt mà
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (Particle p : particles) {
            // Tính toán độ mờ (Alpha) từ 1.0 (rõ) giảm dần về 0.0 (biến mất)
            float alpha = Math.max(0, Math.min(1.0f, (float) p.life / p.maxLife));
            g2d.setColor(new Color(p.color.getRed(), p.color.getGreen(), p.color.getBlue(), (int) (255 * alpha)));

            if (p.isRocket) {
                // Quả pháo bay lên là một đốm sáng tròn
                g2d.fillOval((int) p.x, (int) p.y, 4, 4);
            } else {
                // ---> ĐỈNH CAO Ở ĐÂY: VẼ VỆT SÁNG (MOTION BLUR) <---
                // Nét vẽ mỏng dần khi tia lửa mờ đi
                g2d.setStroke(new BasicStroke(2.5f * alpha + 0.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                
                // Vẽ một đường thẳng từ vị trí "trước đó một chút" đến vị trí "hiện tại"
                // Tạo cảm giác tia lửa xé gió bay đi
                int oldX = (int) (p.x - p.vx * 1.5);
                int oldY = (int) (p.y - p.vy * 1.5);
                g2d.drawLine(oldX, oldY, (int) p.x, (int) p.y);
            }
        }
        g2d.dispose();
    }

    // Lớp chứa thông số của từng hạt
    private class Particle {
        double x, y, vx, vy;
        int life, maxLife;
        boolean isRocket;
        Color color;

        // Constructor nhận thêm baseVx, baseVy (vận tốc đà cũ)
        Particle(double x, double y, boolean isRocket, Color c, double baseVx, double baseVy) {
            this.x = x;
            this.y = y;
            this.isRocket = isRocket;

            if (isRocket) {
                // Quả pháo lúc bắn lên
                this.vx = (rand.nextDouble() - 0.5) * 2;   // Lắc ngang nhẹ
                this.vy = -(rand.nextDouble() * 3 + 10);   // Lực bắn vút lên trên (mạnh)
                this.maxLife = 100; // Không xài, vì nổ theo vật lý đỉnh điểm
                this.color = new Color(255, 200, 100); // Lửa đuôi màu vàng cam sáng
            } else {
                // Các tia lửa lúc nổ
                double angle = rand.nextDouble() * 2 * Math.PI;
                double speed = rand.nextDouble() * 10 + 2; // Lực văng ngẫu nhiên

                // Cộng gộp lực văng vụ nổ + 40% đà bay cũ của quả pháo
                this.vx = (Math.cos(angle) * speed) + (baseVx * 0.4);
                this.vy = (Math.sin(angle) * speed) + (baseVy * 0.4);
                
                this.maxLife = rand.nextInt(40) + 30; // Tuổi thọ tia lửa dài ngắn khác nhau
                this.color = c;

                // Để màu trắng sáng ở những frame đầu tiên cho giống lõi pháo hoa thật
                if (rand.nextBoolean()) {
                    this.color = Color.WHITE;
                }
            }
            this.life = this.maxLife;
        }

        void update() {
            x += vx;
            y += vy;
            life--;

            if (isRocket) {
                // Trọng lực kéo quả pháo chậm lại lúc lên tới đỉnh
                vy += GRAVITY * 0.8; 
            } else {
                // Tia lửa thì chuyển màu từ Trắng -> Màu chính của nó
                if (life == maxLife - 5 && color.equals(Color.WHITE)) {
                    // Đổi lại màu thật sau vụ nổ lóe sáng 5 frames đầu
                    // *Hack nhẹ: bỏ qua, tự nó sẽ hòa quyện nhờ mờ dần*
                }
                
                // Vật lý môi trường tác động lên tia lửa
                vy += GRAVITY;       // Rơi lả tả xuống
                vx *= AIR_FRICTION;  // Phanh ngang lại
                vy *= AIR_FRICTION;  // Phanh dọc lại (chống rơi quá nhanh)
            }
        }
    }
}
