/* 
 * $Id$
 * 
 * Copyright (c) 2008-15 Stephane GALLAND.
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * This program is free software; you can redistribute it and/or modify
 */
package fr.utbm.info.ia54.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.arakhne.afc.vmutil.Resources;
import org.arakhne.afc.vmutil.locale.Locale;

import fr.utbm.info.ia54.environment.agent.Controller;
import fr.utbm.info.ia54.environment.agent.EnvironmentEvent;
import fr.utbm.info.ia54.environment.agent.EnvironmentListener;
import fr.utbm.info.ia54.environment.agent.Player;
import fr.utbm.info.ia54.environment.maze.Direction;
import fr.utbm.info.ia54.environment.maze.GhostBody;
import fr.utbm.info.ia54.environment.maze.PacmanBody;
import fr.utbm.info.ia54.environment.maze.PacmanObject;
import fr.utbm.info.ia54.environment.maze.PillObject;
import fr.utbm.info.ia54.environment.maze.WallObject;
import fr.utbm.info.ia54.math.Point2i;

/**
 * Swing UI for the PacMan game. 
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public class PacManGUI extends JFrame implements KeyListener, EnvironmentListener {

	private static final long serialVersionUID = -3354644983761730596L;

	/** Width in pixels of a cell.
	 */
	public static final int CELL_WIDTH = 20;

	/** Height in pixels of a cell.
	 */
	public static final int CELL_HEIGHT = 20;

	/** Demi-width in pixels of a cell.
	 */
	public static final int DEMI_CELL_WIDTH = CELL_WIDTH/2;

	/** Demi-height in pixels of a cell.
	 */
	public static final int DEMI_CELL_HEIGHT = CELL_HEIGHT/2;

	private final AtomicBoolean isInit = new AtomicBoolean();

	private final GridPanel gridPanel;
	
	private final JButton startButton;
	
	private final long waitingDuration;
	
	private Player player;
	
	private Controller controller;

	/**
	 * @param waitingDuration - the duration of sleeping before giving the hand to the simulator back.
	 */
	public PacManGUI(long waitingDuration) throws Exception {
		this.waitingDuration = waitingDuration;
		setTitle(Locale.getString("FRAME_TITLE"));
		URL pacmanIcon = Resources.getResource(PacManGUI.class, "pacman.png");
		setIconImage(ImageIO.read(pacmanIcon));
		URL iconURL = Resources.getResource(getClass(), "play.png");
		this.startButton = new JButton(new ImageIcon(iconURL));
		this.startButton.setToolTipText(Locale.getString("START"));
		this.startButton.setEnabled(false);
		this.startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Controller ctrl = PacManGUI.this.controller;
				if (ctrl != null && !ctrl.isStarted()) {
					PacManGUI.this.startButton.setEnabled(false);
					ctrl.startSimulation();
				}
			}
		});
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
		topPanel.add(this.startButton);
		this.gridPanel = new GridPanel();
		this.gridPanel.setFocusable(true);
		this.gridPanel.addKeyListener(this);
		JScrollPane sc = new JScrollPane(this.gridPanel);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(BorderLayout.NORTH, topPanel);
		getContentPane().add(BorderLayout.CENTER, sc);
		setPreferredSize(new Dimension(600,600));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
	}

	@Override
	public void bindPlayer(Player player) {
		synchronized(this) {
			this.player = player;
		}
	}

	@Override
	public void unbindPlayer(Player player) {
		synchronized(this) {
			this.player = null;
		}
	}

	@Override
	public void bindController(Controller controller) {
		synchronized(this) {
			this.controller = controller;
			this.startButton.setEnabled(!this.controller.isStarted());
		}
	}

	@Override
	public void unbindController(Controller controller) {
		synchronized(this) {
			this.startButton.setEnabled(false);
			this.controller = null;
		}
	}
	
	@Override
	public void gameOver() {
		setVisible(false);
		JOptionPane.showMessageDialog(this,
				Locale.getString(PacManGUI.class, "GAME_OVER"),
				getTitle(),
				JOptionPane.INFORMATION_MESSAGE);
		dispose();
	}

	@Override
	public void environmentChanged(EnvironmentEvent event) {
		if (!isInit.get()) {
			this.isInit.set(true);
			this.gridPanel.setPreferredSize(new Dimension(
					CELL_WIDTH*event.getWidth(),
					CELL_HEIGHT*event.getHeight()));
			revalidate();
			pack();
			setVisible(true);
			this.gridPanel.requestFocus();
		}
		this.gridPanel.setObjects(event.getTime(), event.getObjects());
		if (this.waitingDuration > 0) {
			try {
				Thread.sleep(this.waitingDuration);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		Player player;
		synchronized(this) {
			player = this.player;
		}
		if (player != null) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				player.move(Direction.WEST);
				break;
			case KeyEvent.VK_RIGHT:
				player.move(Direction.EAST);
				break;
			case KeyEvent.VK_UP:
				player.move(Direction.NORTH);
				break;
			case KeyEvent.VK_DOWN:
				player.move(Direction.SOUTH);
				break;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		//
	}

	@Override
	public void keyTyped(KeyEvent e) {
		//
	}

	/**
	 * Swing panel that is displaying the environment state.
	 * 
	 * @author $Author: sgalland$
	 * @version $FullVersion$
	 * @mavengroupid $GroupId$
	 * @mavenartifactid $ArtifactId$
	 */
	private class GridPanel extends JPanel {

		private static final long serialVersionUID = -7163684947280907441L;

		private Map<Point2i, PacmanObject> objects;
		private AtomicInteger time = new AtomicInteger();

		public GridPanel() {
			setBackground(Color.BLACK);
		}

		public void setObjects(int time, Map<Point2i, PacmanObject> objects) {
			synchronized(getTreeLock()) {
				this.time.set(time);
				this.objects = objects;
				repaint();
			}
		}

		@SuppressWarnings("synthetic-access")
		@Override
		public void paint(Graphics g) {
			super.paint(g);

			int px, py;
			Graphics2D g2d = (Graphics2D)g;

			boolean isEvenTime = (this.time.get() % 2) == 0;

			if (this.objects != null) {
				for (Entry<Point2i, PacmanObject> entry : this.objects.entrySet()) {
					Point2i pos = entry.getKey();
					PacmanObject obj = entry.getValue();
					px = CELL_WIDTH * pos.getX();
					py = CELL_HEIGHT * pos.getY();
					if (obj instanceof WallObject) {
						g2d.setColor(Color.BLUE);
						g2d.fillRect(px, py, CELL_WIDTH, CELL_HEIGHT);
					}
					else if (obj instanceof PacmanBody) {
						PacmanBody pacman = (PacmanBody) obj;
						if (pacman.isSuperPacman()) {
							g2d.setColor(Color.MAGENTA);
						} else {
							g2d.setColor(Color.YELLOW);
						}
						if (isEvenTime) {
							g2d.fillArc(
									px+1, py+1, CELL_WIDTH-2, CELL_HEIGHT-2,
									45, 270);
						}
						else {
							g2d.fillArc(
									px+1, py+1, CELL_WIDTH-2, CELL_HEIGHT-2,
									5, 350);
						}
						g2d.setColor(getBackground());
						int eyex = px + DEMI_CELL_WIDTH - 2;
						int eyey = py + DEMI_CELL_HEIGHT / 2 - 2;
						if (!isEvenTime) eyex ++;
						g2d.fillOval(eyex, eyey, 4, 4);
					}
					else if (obj instanceof GhostBody) {
						g2d.setColor(Color.WHITE);

						g2d.fillArc(
								px+3, py+1, CELL_WIDTH-6, CELL_HEIGHT-2,
								0, 180);

						int up = py + DEMI_CELL_HEIGHT;
						int bottom1 = py + CELL_HEIGHT - 2;
						int bottom2 = bottom1-4;
						int lleft = px+3;
						int left = px + DEMI_CELL_WIDTH/2;
						int middle = px + DEMI_CELL_WIDTH;
						int right = px + DEMI_CELL_WIDTH + DEMI_CELL_WIDTH/2;
						int rright = px+CELL_WIDTH-3;
						if (!isEvenTime) {
							left --;
							middle -= 2;
							bottom2 += 2;
						}
						int[] xx = new int[] {
								lleft,
								lleft,
								left,
								middle,
								right,
								rright,
								rright
						};
						int[] yy = new int[] {
								up,
								bottom1,
								bottom2,
								bottom1,
								bottom2,
								bottom1,
								up
						};
						g2d.fillPolygon(xx,yy,Math.min(xx.length,yy.length));

						int eyex, eyey;

						int dx = 0;
						int dy = 0;

						eyex = px+DEMI_CELL_WIDTH - 5 + dx;
						eyey = py+DEMI_CELL_HEIGHT/2 + dy;
						g2d.setColor(getBackground());
						g2d.fillOval(eyex, eyey, 5,5);
						g2d.setColor(Color.WHITE);

						eyex = px+DEMI_CELL_WIDTH + 2 + dx;
						g2d.setColor(getBackground());
						g2d.fillOval(eyex, eyey, 5,5);

					}
					else if (obj instanceof PillObject) {
						PillObject pill = (PillObject) obj;
						if (pill.isSuperPill()) {
							g2d.setColor(Color.MAGENTA);
							if (isEvenTime) {
								g2d.fillOval(px+DEMI_CELL_WIDTH-4, py+DEMI_CELL_HEIGHT-4, 8, 8);
							}
							else {
								g2d.fillOval(px+DEMI_CELL_WIDTH-5, py+DEMI_CELL_HEIGHT-5, 10, 10);
							}
						} else {
							g2d.setColor(Color.WHITE);
							if (isEvenTime) {
								g2d.fillOval(px+DEMI_CELL_WIDTH-2, py+DEMI_CELL_HEIGHT-2, 4, 4);
							}
							else {
								g2d.fillOval(px+DEMI_CELL_WIDTH-3, py+DEMI_CELL_HEIGHT-3, 6, 6);
							}
						}
					}

				}
			}
		}

	}

}
