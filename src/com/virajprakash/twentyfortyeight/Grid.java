package com.virajprakash.twentyfortyeight;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Grid extends JPanel {

    int size;
    int[][] grid;
    boolean loss = false;
    Map<Integer, Color> colorMap = new HashMap<>();
    public Grid(int size, int[][] grid) {
        this.size = size;
        this.grid = grid;
        colorMap.put(4, Color.LIGHT_GRAY);
        colorMap.put(8, Color.PINK);
        colorMap.put(16, Color.YELLOW);
        colorMap.put(32, Color.RED);
        colorMap.put(64, Color.GREEN);
        colorMap.put(128, Color.BLUE);
        colorMap.put(256, Color.CYAN);
        colorMap.put(512, Color.MAGENTA);
        colorMap.put(1024, Color.getHSBColor(80, 80, 90));
        colorMap.put(2048, Color.getHSBColor(50, 20, 90));
        colorMap.put(4096, Color.getHSBColor(0.8f, 1, 1));
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int panelWidth = getWidth();
        int panelHeight = getHeight();

        // Calculate cell dimensions
        int cellWidth = panelWidth / size;
        int cellHeight = panelHeight / size;

        // Draw grid lines
        g.setColor(Color.BLACK);
        for (int i = 0; i <= size; i++) {
            g.drawLine(i * cellWidth, 0, i * cellWidth, panelHeight);
        }
        for (int i = 0; i <= size; i++) {
            g.drawLine(0, i * cellHeight, panelWidth, i * cellHeight);
        }
//        g.setColor(Color.ORANGE);
//        g.fillRoundRect(0, 0, 125 * 93 / 100, 125 * 93 / 100, 30, 30);
//        g.setColor(Color.BLACK);
        // Get the FontMetrics
        Font boldFont = new Font("Arial", Font.BOLD, cellWidth / 4);

        FontMetrics fm = g.getFontMetrics(boldFont);
        g.setFont(boldFont);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (colorMap.containsKey(grid[j][i])) {
                    g.setColor(colorMap.get(grid[j][i]));
                    g.fillRoundRect((i + 1) * cellWidth - cellWidth, (j + 1) * cellHeight - cellHeight, panelWidth / size , panelHeight / size , 30, 30);
                }
                String text = String.valueOf(grid[j][i] != 0 ? grid[j][i] : "");
//                int textX = ((i + 1) * (cellWidth)) - (cellWidth - fm.stringWidth(text)) / 2 ;
//                int textY = ((j + 1) * (cellHeight)) - ((cellHeight + fm.getHeight()) / 2) + fm.getAscent();
                g.setColor(Color.BLACK);
                g.drawString(text, (i + 1) * (cellWidth) - (cellWidth * 25 / 48) - fm.stringWidth(text) / 2, (j + 1) * (cellHeight) - (cellHeight * 25 / 48));
//                g.drawString("2048", textX, textY);

            }
        }
        if (loss) {
            Font bigFont = new Font("Arial", Font.BOLD, cellWidth / 2);
            g.setColor(Color.BLACK);
            g.setFont(bigFont);
            g.drawString("Game.java over!", panelWidth / 4, panelHeight / 2);
        }

    }

}
