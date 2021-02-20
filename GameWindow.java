import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class GameWindow extends JFrame {

    GameWindow() throws IOException { createWindow(); }

    void createWindow() throws IOException {
        setTitle("Snowflakes Game");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize (1023, 683);
        setLocationRelativeTo(null);
        setResizable(false);

        GameField game_field = new GameField();
        game_field.setLast_frame_time(System.nanoTime());
        game_field.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();

                float drop_right = game_field.getDrop_left() +
                                    game_field.getSnowflakes().getWidth(null);

                float drop_bottom = game_field.getDrop_top() +
                                    game_field.getSnowflakes().getHeight(null);

                boolean is_drop = x >= game_field.getDrop_left() && x <= drop_right &&
                                    y >= game_field.getDrop_top() && y <= drop_bottom;

                if (is_drop){
                    game_field.setDrop_top(-100);
                    game_field.setDrop_left((int) (Math.random() * (game_field.getWidth() -
                                            game_field.getSnowflakes().getWidth(null))));

                    game_field.setDrop_v(game_field.getDrop_v() + 10);

                    game_field.setScores(game_field.getScores() + 1);

                    setTitle("Snowflakes Game. Scores: " + game_field.getScores());
                }

            }
        });

        add(game_field);

        setVisible(true);
    }
}
