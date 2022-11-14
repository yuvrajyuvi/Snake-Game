import javax.swing.*;

public class SnakeGame {
    JFrame frame;
    SnakeGame(){
         frame=new JFrame();

         Board board=new Board();

         frame.add(board);
         frame.setVisible(true);
         frame.setTitle("Snake Game");
         frame.setResizable(false);
         frame.pack();
    }
    public static void main(String[] args) {
        SnakeGame snakeGame=new SnakeGame();
    }
}