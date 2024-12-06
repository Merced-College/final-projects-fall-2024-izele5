import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class GamePanel extends JPanel implements ActionListener {

    // the games dimension's and speed
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
    static final int DELAY = 75;
    // Arrays for x and y coordinates
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];

    int bodyParts = 6; // snakes size
    int mouseEaten; // number of mice caught
    int mouseX;
    int mouseY;
    char direction = 'R';
    boolean running = false;
    Timer timer; // timer to control game speed
    Random random;

    // history of the snake's head positions
    List<Point> headPositionHistory = new ArrayList<>();
//sets up the game panel to start playing
    GamePanel() {
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.green);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }

    public void startGame() {
        newMouse();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }
    // sets the snake, mouse, and score
    public void draw(Graphics g) {
//sets the mouse
        if (running) {
            g.setColor(Color.black);
            g.fillOval(mouseX, mouseY, UNIT_SIZE, UNIT_SIZE);
//sets the snake
            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    g.setColor(new Color(45, 180, 0));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
//sets the score
            }
            g.setColor(Color.pink);
            g.setFont(new Font("Ink Free", Font.BOLD, 40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: " + mouseEaten,
                    (SCREEN_WIDTH - metrics.stringWidth("Score: " + mouseEaten)) / 2,
                    g.getFont().getSize());

        } else {
            gameOver(g); // shows game over screen
        }
    }
// adds a new mouse on the panel
    public void newMouse() {
        mouseX = random.nextInt((int) (SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
        mouseY = random.nextInt((int) (SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
    }

    public void move() {
        for (int i = bodyParts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        // moving directions
        switch (direction) {
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }

        // Add the new head position to the history
        headPositionHistory.add(new Point(x[0], y[0]));
    }

    public void checkMouse() {
        if ((x[0] == mouseX) && (y[0] == mouseY)) {
            bodyParts++;
            mouseEaten++;
            newMouse();
        }
    }
    // check if the snake hit its body
    public void checkCollisions() {
        for (int i = bodyParts; i > 0; i--) {
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                running = false;
            }
// check if the snake hit the wall
        }
        if (x[0] < 0 || x[0] > SCREEN_WIDTH || y[0] < 0 || y[0] > SCREEN_HEIGHT) {
            running = false;
        }
// stops the game if not running anymore
        if (!running) {
            timer.stop();
        }
    }
    // shows "Game Over" text
    public void gameOver(Graphics g) {
        g.setColor(Color.red);
        g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
        g.setFont(new Font("Ink Free", Font.BOLD, 40));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score: " + mouseEaten,
                (SCREEN_WIDTH - metrics1.stringWidth("Score: " + mouseEaten)) / 2,
                g.getFont().getSize());

        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Game Over",
                (SCREEN_WIDTH - metrics2.stringWidth("Game Over")) / 2,
                SCREEN_HEIGHT / 2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move(); //moves the snake
            checkMouse(); //checks if the snake has caught any mice
            checkCollisions(); //checks if snake has collided
        }
        repaint(); //resets the game
    }
//the directions and handles for the input
    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L') {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (direction != 'D') {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != 'U') {
                        direction = 'D';
                    }
                    break;
            }
        }
    }
}
