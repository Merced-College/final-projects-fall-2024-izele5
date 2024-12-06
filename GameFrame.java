import javax.swing.JFrame;
// game frame class extends JFrame to create the main game window
public class GameFrame extends JFrame{
    //constructor that sets up the game frame
    GameFrame(){

        this.add(new GamePanel());
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);


    }
}
