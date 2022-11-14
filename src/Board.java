import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


// Creating the Board

public class Board extends JPanel implements ActionListener{
    int height = 400;
    int width = 400;

    int dot;
    int dotSize = 10;
    int allDots = 40 * 40;
    int DELAY = 150;

    int x[] = new int[allDots];
    int y[] = new int[allDots];

    int apple_x;
    int apple_y;

    boolean leftDirection = true;
    boolean rightDirection = false;
    boolean upDirection = false;
    boolean downDirection = false;

    boolean inGame=true;


    Timer timer;


    Image head;
    Image apple;
    Image body;

    //Initializing the panel attributes
    Board(){
        addKeyListener(new TAdapter());
        setBackground(Color.BLACK);
        setFocusable(true);
        setPreferredSize(new Dimension(height,width));
        loadImages();
        initGame();

    }

    //Initialize the game
    public void initGame(){
        locateApple();
        dot=3;
        for(int z=0;z<dot;z++){
            y[z]=350;
            x[z]=350+(z*dotSize);
        }
        timer=new Timer(DELAY,this);
        timer.start();


    }
    public void loadImages(){
        ImageIcon a = new ImageIcon("src/resources/apple.png");
        apple = a.getImage();
        ImageIcon b = new ImageIcon("src/resources/head.png");
        head = b.getImage();
        ImageIcon c = new ImageIcon("src/resources/dot.png");
        body = c.getImage();
    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        doDrawings(g);
    }
    public void doDrawings(Graphics g){
        if(inGame){
            g.drawImage(apple,apple_x,apple_y,this);
            g.drawImage(head,x[0],y[0],this);
            for(int z =1;z<dot;z++){
                g.drawImage(body,x[z],y[z],this);
            }
            Toolkit.getDefaultToolkit().sync();
        }
        else{
            gameOver(g);
        }
    }
    public void checkCollision(){
        for(int z=0;z<dot;z++){
            if((z>3)&&(x[0]==x[z])&&(y[0]==y[z])){
                inGame=false;
            }
        }
        if(x[0]<0){
            inGame=false;
        }
        if(x[0]>=width){
            inGame=false;
        }
        if(y[0]<0){
            inGame=false;
        }
        if(y[0]>=height){
            inGame=false;
        }
        if(!inGame){
            timer.stop();
        }
    }
    public void gameOver(Graphics g){
        String msg="GAME OVER";
        Font font=new Font("Helvetica",Font.BOLD,20);
        FontMetrics fontMetrics=getFontMetrics(font);

        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString(msg,(width-fontMetrics.stringWidth(msg))/2,height/2);
    }
    public void checkApple(){
        if(x[0]==apple_x&&y[0]==apple_y){
            dot++;
            locateApple();
        }
    }
    public void locateApple(){
        int x= (int)(Math.random()*39);
        apple_x=x*10;
        int y= (int)(Math.random()*39);
        apple_y=y*10;
    }
    public void move(){
        for(int z=dot-1;z>0;z--){
            x[z]=x[z-1];
            y[z]=y[z-1];
        }
        if(leftDirection){
            x[0]-=dotSize;
        }
        if(rightDirection){
            x[0]+=dotSize;
        }
        if(upDirection){
            y[0]-=dotSize;
        }
        if(downDirection){
            y[0]+=dotSize;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            move();
            checkApple();
            checkCollision();
        }
        repaint();
    }

    public class TAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            int key= e.getKeyCode();

            if(key==KeyEvent.VK_LEFT && !rightDirection){
                leftDirection=true;
                upDirection=false;
                downDirection=false;
            }
            if(key==KeyEvent.VK_RIGHT && !leftDirection){
                rightDirection=true;
                upDirection=false;
                downDirection=false;
            }
            if(key==KeyEvent.VK_UP && !downDirection){
                upDirection=true;
                leftDirection=false;
                rightDirection=false;
            }
            if(key==KeyEvent.VK_DOWN && !upDirection){
                downDirection=true;
                rightDirection=false;
                leftDirection=false;
            }
        }
    }
}


