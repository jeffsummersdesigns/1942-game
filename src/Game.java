/*Jeffrey Summers
 * 08/11/2015
 * Game.java
 * This program is a game to fly a WWII plane! 
 * This program will use event programming to move
 * the airplane left, right, up and down to position
 * the airplane to shoot and avoid the enemy airplanes
 * 
 */

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.net.URL;

import acm.graphics.GLabel;
import acm.graphics.GImage;
import acm.graphics.GObject;
import acm.graphics.GOval;
import acm.program.GraphicsProgram;
import acm.util.*;

public class Game extends GraphicsProgram {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// constants
	private static final int AW = 700;
	private static final int AH = 750;
	private static final int BULLET_SIZE = 15;
	private static final int BULLET_SPEED = -375;
	private final int WAIT = 50;

	// game objects!
	private GImage background, airplane, explosion, enemyExplosion, enemy1,
			enemy2, enemy3;
	private GOval[] bullets;
	private GLabel scoreLabel;
	private GObject obj, objEnemy1, objEnemy2, objEnemy3;

	// game sounds!
	private AudioClip gameMusic, airplaneSound, shootSound, explosionSound,
			enemyExplosionSound;

	// set the initial score to 0
	private int score = 0;

	// random generator
	private RandomGenerator rg = new RandomGenerator();

	public void init() {

		background = new GImage("images/clouds.jpg");
		add(background);

		setSize(AW, AH);
		setBackground(Color.BLUE);

		// sound of the airplane
		gameMusic = Applet.newAudioClip(Get_Location("/sound/gameMusic.wav"));
		gameMusic.loop();

		// use GImage to display png of airplane
		airplane = new GImage("images/airplane.fw.png");
		add(airplane);

		// get the reference to the airplane object
		obj = getElementAt(airplane.getX(), airplane.getY());

		// use GImage to display png of explosion
		explosion = new GImage("images/explosion.png");

		// use same GImage to display png for enemy explosion
		enemyExplosion = new GImage("images/enemyExplosion.png");

		// sound of the explosion
		explosionSound = Applet
				.newAudioClip(Get_Location("/sound/explosion.wav"));

		// sound of the enemy explosion
		enemyExplosionSound = Applet
				.newAudioClip(Get_Location("/sound/explosion.wav"));

		// get the reference to the airplane object
		obj = getElementAt(airplane.getX(), airplane.getY());

		// create 3 enemy planes
		enemy1 = Enemy();
		enemy2 = Enemy();
		enemy3 = Enemy();

		// get the reference to the enemy objects
		objEnemy1 = getElementAt(enemy1.getX(), enemy1.getY());
		objEnemy2 = getElementAt(enemy2.getX(), enemy2.getY());
		objEnemy3 = getElementAt(enemy3.getX(), enemy3.getY());

		// sound of the airplane
		airplaneSound = Applet
				.newAudioClip(Get_Location("/sound/biplaneSound.wav"));
		airplaneSound.loop();

		// create bullets
		bullets = new GOval[10];

		// sound of shooting
		shootSound = Applet.newAudioClip(Get_Location("/sound/gunFire.wav"));

		// add mouse listeners to animate airplane
		addMouseListeners();

		// add key listeners to add shooting function
		addKeyListeners();

	}// end init()

	// event handler for mouse event to animate airplane
	public void mouseMoved(MouseEvent e) {
		double x = e.getX();
		double y = e.getY();
		airplane.setLocation(x - 40, y); // center airplane under mouse
	} // mouseMoved

	// event handler for key event to shoot bullets from airplane
	public void keyPressed(KeyEvent e) {

		if (e.getKeyCode() == KeyEvent.VK_SPACE) {

			// create bullets
			for (int i = 0; i < bullets.length; i++) {
				bullets[i] = Bullet();
				shootSound.play();

				// bullet moves upward towards enemies
				for (int j = 0; j <= i; j++) {
					bullets[j].move(0, BULLET_SPEED);
					if (bullets[j].getBounds().intersects(enemy1.getBounds()) == true) {
						remove(enemy1);
						score++;
						//int x = (int) objEnemy1.getX();
						//int y = (int) objEnemy1.getY();
						//add(enemyExplosion, x, y);
						enemyExplosionSound.play();
						//remove(enemyExplosion);
						remove(scoreLabel);
						scoreLabel.setLabel("Score:   " + score);
						add(scoreLabel);
			}// end for
				
					if (bullets[j].getBounds().intersects(enemy2.getBounds()) == true) {
						remove(enemy2);
						score++;
						//int x = (int) objEnemy2.getX();
						//int y = (int) objEnemy2.getY();
						//add(enemyExplosion, x, y);
						enemyExplosionSound.play();
						//remove(enemyExplosion);
						remove(scoreLabel);
						scoreLabel.setLabel("Score:   " + score);
						add(scoreLabel);
						

					}
					if (bullets[j].getBounds().intersects(enemy3.getBounds()) == true) {
						remove(enemy3);
						score++;
						//int x = (int) objEnemy3.getX();
						//int y = (int) objEnemy3.getY();
						//add(enemyExplosion, x, y);
						enemyExplosionSound.play();
						//remove(enemyExplosion);
						remove(scoreLabel);
						scoreLabel.setLabel("Score:   " + score);
						add(scoreLabel);
						

					}// end if
				}// end for

			}// end for
		}//end while

		}// end if

	// end keyPressed

	// method to make file location a string for parameter work for AudioClip
	public URL Get_Location(String filename) {

		URL url = null;
		try {
			url = this.getClass().getResource(filename);
		} catch (Exception e) { /* STUFF */
		}
		return url;
	}

	public void run() {

		scoreLabel = new GLabel("Score:   " + score);
		scoreLabel.setFont("SansSerif-BOLD-35");
		scoreLabel.setColor(Color.BLACK);
		add(scoreLabel, 2, AH - 700);

		waitForClick();

		// move background down to create appearance of
		// forward movement
		int bgMoveY = -1;
		// pick a random direction for the enemy plane
		int dx1 = rg.nextInt(-6, 6);
		int dx2 = rg.nextInt(-3, 3);
		int dx3 = rg.nextInt(-3, 3);
		int dy1 = rg.nextInt(5, 10);
		int dy2 = rg.nextInt(5, 10);
		int dy3 = rg.nextInt(5, 10);

		// game loop
		while (true) {

			pause(WAIT);

			// move the background to give appearance of forward movement
			background.move(0, bgMoveY);

			// animate the enemy planes
			enemy1.move(dx1, dy1);
			enemy2.move(dx2, dy2);
			enemy3.move(dx3, dy3);

			/*
			 * if enemy plane touch bottom of the applet, remove the plane and
			 * start the loop over with 3 new enemy planes starting from the top
			 * of the applet
			 */

			if (enemy1.getY() + enemy1.getHeight() >= AH) {
				remove(enemy1);
				// create new enemy plane
				enemy1 = Enemy();
				score--; // decrement score by 1 each time enemy plane gets by
				remove(scoreLabel);
				scoreLabel.setLabel("Score:   " + score);
				add(scoreLabel);
			}
			if (enemy2.getY() + enemy2.getHeight() >= AH) {
				remove(enemy2);
				// create new enemy plane
				enemy2 = Enemy();
				score--; // decrement score by 1 each time enemy plane gets by
				remove(scoreLabel);
				scoreLabel.setLabel("Score:   " + score);
				add(scoreLabel);
			}
			if (enemy3.getY() + enemy3.getHeight() >= AH) {
				remove(enemy3);
				// create new enemy plane
				enemy3 = Enemy();
				score--; // decrement score by 1 each time enemy plane gets by
				remove(scoreLabel);
				scoreLabel.setLabel("Score:   " + score);
				add(scoreLabel);
			} // at left or right side? Enemy plane changes horizontal move
			if ((enemy1.getX() + enemy1.getWidth() <= 0)
					|| (enemy1.getX() + enemy1.getWidth() >= AW)) {
				dx1 = -dx1;
			}
			if ((enemy2.getX() + enemy2.getWidth() <= 0)
					|| (enemy2.getX() + enemy2.getWidth() >= AW)) {
				dx2 = -dx2;
			}
			if ((enemy3.getX() + enemy3.getWidth() <= 0)
					|| (enemy3.getX() + enemy3.getWidth() >= AW)) {
				dx3 = -dx3;
			} // if enemy planes crash into airplane the airplane is removed
			  // and an explosion replaces the airplane
			if (enemy1.getBounds().intersects(airplane.getBounds()) == true) {
				remove(airplane);
				int x = (int) obj.getX();
				int y = (int) obj.getY();
				add(explosion, x, y);
				explosionSound.play();
				gameMusic.stop();
				airplaneSound.stop();
				pause(250);
				remove(explosion);
				displayFinalScore();
				break;
			}
			if (enemy2.getBounds().intersects(airplane.getBounds()) == true) {
				remove(airplane);
				int x = (int) obj.getX();
				int y = (int) obj.getY();
				add(explosion, x, y);
				explosionSound.play();
				gameMusic.stop();
				airplaneSound.stop();
				pause(250);
				remove(explosion);
				displayFinalScore();
				break;
			}
			if (enemy3.getBounds().intersects(airplane.getBounds()) == true) {
				remove(airplane);
				int x = (int) obj.getX();
				int y = (int) obj.getY();
				add(explosion, x, y);
				explosionSound.play();
				gameMusic.stop();
				airplaneSound.stop();
				pause(250);
				remove(explosion);
				displayFinalScore();
				break;
			}

		}// end while

	}// end run

	public GLabel displayFinalScore() {

		GLabel finalLabel = new GLabel("Game Over!");
		finalLabel.setFont("SansSerif-BOLD-50");
		finalLabel.setColor(Color.RED);
		add(finalLabel, AW - 500, AH - 500);
		return finalLabel;
	}

	// create bullets
	public GOval Bullet() {
		GOval bullet = new GOval(BULLET_SIZE, BULLET_SIZE);
		bullet.setLocation(airplane.getX(), airplane.getY());
		bullet.setFilled(true);
		bullet.setColor(Color.ORANGE);
		add(bullet);
		return bullet;
	}// end Bullet()

	// method to create enemy planes
	public GImage Enemy() {
		// set enemy plane at a random location at top of screen
		int randomX = rg.nextInt(25, AW - 50);
		int y = 0;
		GImage enemyPlane = new GImage("images/enemyPlane.fw.png", randomX, y);
		add(enemyPlane);
		return enemyPlane;
	} // Enemy

}// end Game