package brick_broker;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;

import javax.swing.JPanel;

public class GamePlay extends JPanel implements KeyListener,ActionListener {
	private boolean play=false;
	private int score=0;
	private int totalBrick=21;
	private Timer timer;
	private int delay =8;
	
	private int playerX=310;
	private int ballposX=120;
	private int ballposY=350;
	private int ballXDir=-1;
	private int ballYDir=-2;
	private MapGenerartor map;
	public GamePlay() {
		map=new MapGenerartor(3,7);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer=new Timer(delay,this);
		timer.start();
	}
	public void paint(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(1,1,692,592 );
		
		map.draw((Graphics2D) g);
		
		g.setColor(Color.YELLOW);
		g.fillRect(0,0,3,592 );
		g.fillRect(0,0, 692,3 );
		g.fillRect(691, 0, 3,592 );
		
		//score
		g.setColor(Color.white);
		g.setFont(new Font("serif",Font.BOLD,25));
		g.drawString("Score: "+score,540,30);
		g.setColor(Color.green);
		g.fillRect(playerX, 550, 100, 8);
		
		g.setColor(Color.YELLOW);
		g.fillOval(ballposX, ballposY, 20, 20);
		
		if(totalBrick<=0 ) {
			play =false;
			ballXDir=0;
			ballYDir=0;
			g.setColor(Color.red);
			g.setFont(new Font("serif",Font.BOLD,30));
			g.drawString("You WON!!",260,300);
		    
		    
		    g.setFont(new Font("serif",Font.BOLD,20));
			g.drawString("Press ENTER to Restart",190,350);
		}
		
		if(ballposY>570) {
			play=false;
			ballXDir=0;
			ballYDir=0;
			g.setColor(Color.red);
			g.setFont(new Font("serif",Font.BOLD,30));
			g.drawString("GAME OVER!!",190,280);
		    g.drawString( "Score :" + score, 190, 310);
		    
		    g.setFont(new Font("serif",Font.BOLD,20));
			g.drawString("Press ENTER to Restart",190,350);
		}
		g.dispose();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		timer.start();
		if(play) {
			if(new Rectangle(ballposX,ballposY,20,20).intersects(new Rectangle(playerX,550,100,8))) {
				ballYDir=-ballYDir;
			}
			A: for(int i=0;i<map.map.length;i++) {
				for(int j=0;j<map.map[0].length;j++) {
					if(map.map[i][j]>0){
						int brickX=j*map.brickWidth+80;
						int brickY=i*map.brickHeight+50;
						int brickWidth=map.brickWidth;
						int brickHeight=map.brickHeight;
						Rectangle rect=new Rectangle(brickX,brickY,brickWidth,brickHeight);
						Rectangle ballRect=new Rectangle(ballposX,ballposY,20,20);
						Rectangle brickRect=rect;
						if(ballRect.intersects(brickRect)) {
							map.setBrickValue(0, i,j);
							totalBrick--;
							score+=5;
						
							if(ballposX+19<=brickRect.x || ballposX+1 >=brickRect.x + brickRect.width) {
								ballXDir=-ballXDir;
							}else {
								ballYDir=-ballYDir;
							}
							break A;
						}
					}
				}
			}
		}
		if(play==true) {
			ballposX+=ballXDir;
			ballposY+=ballYDir;
			if(ballposX<0) {
				ballXDir=-ballXDir;
			}
			if(ballposY<0) {
				ballYDir=-ballYDir;
			}
			if(ballposX>670) {
				ballXDir=-ballXDir;
			}
		}
		repaint();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode()==KeyEvent.VK_RIGHT) {
			if(playerX >=600) {
				playerX=600;
			}else {
				moveLeft();
			}
		}
		if(e.getKeyCode()==KeyEvent.VK_LEFT) {
			if(playerX <10) {
				playerX=10;
			}else {
				moveRight();
			}
		}
		
		if(e.getKeyCode()==KeyEvent.VK_ENTER) {
			if(!play) {
				play=true;
				playerX=310;
				ballposX=120;
				ballposY=350;
				ballXDir=-1;
				ballYDir=-2;
				score=0;
				totalBrick=21;
				map=new MapGenerartor(3,7);
				repaint();
			}
		}
	}
	public void moveLeft() {
		play =true;
		playerX+=20;
	}
	public void moveRight() {
		play =true;
		playerX-=20;
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
