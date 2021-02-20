import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class GameField extends JPanel {

    Image background = ImageIO.read(GameWindow.class.getResourceAsStream("background.png"));
    Image game_over = ImageIO.read(GameWindow.class.getResourceAsStream("game_over.png"));
    Image snowflakes = ImageIO.read(GameWindow.class.getResourceAsStream("snowflakes.png"));

    private float drop_left = 200;
    private float drop_top = -100;
    private float drop_v = 200;

    private int scores;

    private long last_frame_time;

    public GameField() throws IOException {
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        onRepaint(g);
        repaint();
    }

    void onRepaint (Graphics g)
    {
        long current_time = System.nanoTime();
        float delta_time = ((current_time - last_frame_time) * 0.000000001f);
        last_frame_time = current_time;
        drop_top = drop_top + drop_v * delta_time;
        //drop_left = drop_left + drop_v * delta_time;

        g.drawImage(background,0,0,null);
        g.drawImage(snowflakes,(int)drop_left,(int)drop_top,null);

        if (drop_top > getHeight()) g.drawImage(game_over,280,120,null);
    }

    public void setLast_frame_time(long last_frame_time) {
        this.last_frame_time = last_frame_time;
    }

    public float getDrop_left() {
        return drop_left;
    }

    public Image getSnowflakes() {
        return snowflakes;
    }

    public float getDrop_top() {
        return drop_top;
    }

    public void setDrop_top(float drop_top) {
        this.drop_top = drop_top;
    }

    public void setDrop_left(float drop_left) {
        this.drop_left = drop_left;
    }

    public void setDrop_v(float drop_v) {
        this.drop_v = drop_v;
    }

    public float getDrop_v() {
        return drop_v;
    }

    public void setScores(int scores) {
        this.scores = scores;
    }

    public int getScores() {
        return scores;
    }
}
