package zagar.view;

import org.jetbrains.annotations.NotNull;
import zagar.Game;

import java.awt.*;

/**
 * Created by roman on 28.11.16.
 */
public class Food {
    public double x, y;
    public final int size = 25;
    private int r, g, b;

    public Food(double x, double y) {
        this.x = x;
        this.y = y;
        r = 255;
        g = 255;
        b = 0;
    }

    public void render(@NotNull Graphics2D g) {
        if (Game.food.length > 0) {
            g.setColor(new Color(this.r, this.g, this.b));

            float avgX = 0, avgY = 0, playerSize = 0;
            for (Cell c : Game.player) {
                if (c != null && c.kind != 1) {
                    avgX += c.x;
                    avgY += c.y;
                    playerSize++;
                }
            }
            avgX /= playerSize;
            avgY /= playerSize;

            int x = (int) ((this.x - avgX) * Game.zoom) + GameFrame.frame_size.width / 2;
            int y = (int) ((this.y - avgY) * Game.zoom) + GameFrame.frame_size.height / 2;

            if (x < -size - 30 || x > GameFrame.frame_size.width + 30 || y < -size - 30 || y > GameFrame.frame_size.height + 30) {
                return;
            }
            g.fillOval(x, y, size, size);

        }
    }

    @Override
    public String toString() {
        return "Food{" +
                "x=" + x +
                ", y=" + y +
                ", size=" + size +
                '}';
    }
}
