package zagar.view;

import org.jetbrains.annotations.NotNull;
import zagar.Game;

import java.awt.*;

/**
 * Created by roman on 28.11.16.
 */
public class Food {
    public double x, y;
    public final float size = 25.0f;
    private int r, g, b;

    public Food(double x, double y) {
        this.x = x;
        this.y = y;
        r = 255;
        g = 255;
        b = 0;
    }

    public void render(@NotNull Graphics2D g, float scale) {
        if (Game.food.length > 0) {
            g.setColor(new Color(this.r, this.g, this.b));
            int size = (int) ((this.size * 2f * scale) * Game.zoom);

            float avgX = 0;
            float avgY = 0;

            for (Cell c : Game.player) {
                if (c != null) {
                    avgX += c.xRender;
                    avgY += c.yRender;
                }
            }

            avgX /= Game.player.size();
            avgY /= Game.player.size();

            int x = (int) ((this.x - avgX) * Game.zoom) + GameFrame.size.width / 2 - size / 2;
            int y = (int) ((this.y - avgY) * Game.zoom) + GameFrame.size.height / 2 - size / 2;

            if (x < -size - 30 || x > GameFrame.size.width + 30 || y < -size - 30 || y > GameFrame.size.height + 30) {
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
