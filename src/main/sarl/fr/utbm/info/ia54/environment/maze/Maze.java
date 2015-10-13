/* 
 * $Id$
 * 
 * Copyright (c) 2015 Stephane GALLAND.
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
package fr.utbm.info.ia54.environment.maze;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.UUID;

import org.eclipse.xtext.xbase.lib.Pure;

import fr.utbm.info.ia54.math.Point2i;
import fr.utbm.info.ia54.math.Vector2i;

/**
 * Define the maze.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public class Maze {

	/** Max number of super pills in the world.
	 */
	public static final int MAX_SUPER_PILLS = 5;

	/** Max number of ghosts in the world.
	 */
	public static final int MAX_GHOSTS = 5;

	/** Matrix of the objects.
	 */
	private final PacmanObject[][] grid;

	private final Map<UUID, AgentBody> bodies = new TreeMap<>();

	/** Width of the world.
	 */
	private final int width;

	/** Height of the world.
	 */
	private final int height;

	/** Random generator.
	 */
	private final Random random = new Random();

	/**
	 * @param width is the width of the world.
	 * @param height is the height of the world.
	 */
	public Maze(int width, int height) {
		this.width = width;
		this.height = height;
		this.grid = new PacmanObject[width][height];

		buildWorld(this.width, this.height);
	}

	/** Build the maze and fill the spaces with pills.
	 * 
	 * @param width width of the maze.
	 * @param height height of the maze.
	 */
	private void buildWorld(int width, int height) {
		//buildMaze_recursiveAlgorithm(0, width, 0, height);
		buildMaze();

		for (int i = 0; i < width; ++i) {
			for (int j = 0; j < height; ++j) {
				if (this.grid[i][j] == null) {
					this.grid[i][j] = new PillObject(i, j, this, false);
				}
			}
		}

		for (int i = 0; i < MAX_SUPER_PILLS; ++i) {
			int x = this.random.nextInt(width);
			int y = this.random.nextInt(height);
			while (!canMoveInside(x, y)) {
				x = this.random.nextInt(width);
				y = this.random.nextInt(height);
			}
			this.grid[x][y] = new PillObject(x, y, this, true);
		}
	}

	/** Build the maze with the Prim's algorithm.
	 */
	private void buildMaze() {
		// Fill the maze with walls.
		for (int i = 0; i < this.width; ++i) {
			for (int j = 0; j < this.height; ++j) {
				this.grid[i][j] = new WallObject(i, j, this);
			}
		}
		
		// Declare the list of walls from which it could be possible to dig.
		List<PrimWall> walls = new ArrayList<>();
		
		// Select a random cell and prepare to dig.
		int x = this.random.nextInt(this.width);
		int y = this.random.nextInt(this.height);
		this.grid[x][y] = null;
		walls.add(new PrimWall(x, y - 1, x, y - 2, x - 1, y - 2, x, y - 3, x + 1, y - 2));
		walls.add(new PrimWall(x, y + 1, x, y + 2, x - 1, y + 2, x, y + 3, x + 1, y + 2));
		walls.add(new PrimWall(x - 1, y, x - 2,  y, x - 2, y + 1, x - 3, y, x - 2, y - 1));
		walls.add(new PrimWall(x + 1, y, x + 2, y, x + 2, y - 1, x + 3, y, x + 2, y + 1));
		
		// Loop 'til no more place for digging
		while (!walls.isEmpty()) {
			// Select a wall to dig inside
			PrimWall diggeableWall = walls.remove(this.random.nextInt(walls.size()));
			// Check if the wall could be digged
			if (diggeableWall.corridor.getX() >= 0 && diggeableWall.corridor.getX() < this.width
				&& diggeableWall.corridor.getY() >= 0 && diggeableWall.corridor.getY() < this.height
				&& this.grid[diggeableWall.corridor.getX()][diggeableWall.corridor.getY()] instanceof WallObject
				&& this.grid[diggeableWall.passage.getX()][diggeableWall.passage.getY()] instanceof WallObject) {
				// Dig the walls
				this.grid[diggeableWall.passage.getX()][diggeableWall.passage.getY()] = null;
				this.grid[diggeableWall.corridor.getX()][diggeableWall.corridor.getY()] = null;
				// Add the candidates
				addWallCandidate(walls, diggeableWall.corridor, diggeableWall.passageCandidate1);
				addWallCandidate(walls, diggeableWall.corridor, diggeableWall.passageCandidate2);
				addWallCandidate(walls, diggeableWall.corridor, diggeableWall.passageCandidate3);
			}
		}
	}
	
	private void addWallCandidate(List<PrimWall> walls, Point2i corridor, Point2i candidate) {
		final Vector2i v = new Vector2i();
		v.sub(candidate, corridor);

		final Vector2i r = new Vector2i();
		r.set(v);
		r.perpendicularize();
		
		PrimWall pw = new PrimWall(
				// new passage
				corridor.getX() + v.getX(),
				corridor.getY() + v.getY(),
				// new corridor
				corridor.getX() + 2 * v.getX(),
				corridor.getY() + 2 * v.getY(),
				// new candidate 1
				corridor.getX() + 3 * v.getX(),
				corridor.getY() + 3 * v.getY(),
				// new candidate 2
				corridor.getX() + 2 * v.getX() + r.getX(),
				corridor.getY() + 2 * v.getY() + r.getY(),
				// new candidate 3
				corridor.getX() + 2 * v.getX() - r.getX(),
				corridor.getY() + 2 * v.getY() - r.getY());
		
		walls.add(pw);
	}

	/** Replies if the cell at the given coordinates can receive a body.
	 * 
	 * @param x
	 * @param y
	 * @return <code>true</code> if the cell is empty or has a pickable object; <code>false</code>
	 * otherwise.
	 */
	@Pure
	public synchronized boolean canMoveInside(int x, int y) {
		return (x>=0 && y>=0 && x<this.width && y<this.height
				&& (this.grid[x][y]==null || this.grid[x][y].isPickable()));
	}

	/** Create a body of the given type.
	 *
	 * @param bodyType the type of the body.
	 * @param agentId the identifier of the agent that will be linked to the body.
	 * @param perceptionDistance the distance of perception.
	 * @return the body.
	 * @throws Exception if it is impossible to retrieve the body constructor or to create the instance. 
	 */
	public <T extends AgentBody> T createBody(Class<T> bodyType, UUID agentId, int perceptionDistance) throws Exception {
		int x = this.random.nextInt(width);
		int y = this.random.nextInt(height);
		while (!canMoveInside(x, y)) {
			x = this.random.nextInt(width);
			y = this.random.nextInt(height);
		}

		UUID id = agentId;
		if (id == null) {
			id = UUID.randomUUID();
		}

		Constructor<T> cons = bodyType.getDeclaredConstructor(int.class, int.class, Maze.class, UUID.class, int.class);
		T body = cons.newInstance(x, y, this, id, perceptionDistance);

		this.grid[x][y] = body;
		this.bodies.put(id, body);

		return body;
	}

	/** Replies the number of bodies in the maze.
	 * 
	 * @return the number of bodies.
	 */
	@Pure
	public int getBodyCount() {
		return this.bodies.size();
	}

	/** Replies the object at the given position.
	 * 
	 * @param x
	 * @param y
	 * @return the object or <code>null</code>.
	 */
	@Pure
	public PacmanObject getObjectAt(int x, int y) {
		if (x >= 0 && x < this.width && y >= 0 && y < this.height) {
			return this.grid[x][y];
		}
		return new WallObject(x, y, this);
	}

	/** Set the object at the given position.
	 * 
	 * @param x
	 * @param y
	 * @param obj the object to put at the position
	 * @return the object in the cell before the change.
	 */
	public PacmanObject setObjectAt(int x, int y, PacmanObject obj) {
		if (x >= 0 && x < this.width && y >= 0 && y < this.height) {
			PacmanObject old = this.grid[x][y];
			this.grid[x][y] = obj;
			if (obj != null) {
				obj.setPosition(x, y);
			}
			return old;
		}
		return null;
	}

	/** Set the object at the given position.
	 * 
	 * @param position
	 * @param obj the object to put at the position
	 * @return the object in the cell before the change.
	 */
	public PacmanObject setObjectAt(Point2i position, PacmanObject obj) {
		return setObjectAt(position.getX(), position.getY(), obj);
	}

	/** Replies the agent bodies.
	 *
	 * @return the agent bodies.
	 */
	public Collection<AgentBody> getAgentBodies() {
		return Collections.unmodifiableMap(this.bodies).values();
	}

	/** Replies the agent body.
	 *
	 * @param id
	 * @return the body.
	 */
	@Pure
	public AgentBody getAgentBody(UUID id) {
		return this.bodies.get(id);
	}
	
	/** Replies the accessors for managing the super power of Pacman.
	 * 
	 * @param id the id of Pacman.
	 * @return the accessor.
	 */
	public SuperPowerAccessor getSuperPowerAccessorFor(UUID id) {
		AgentBody body = this.bodies.get(id);
		if (body instanceof PacmanBody) {
			final PacmanBody pacman = (PacmanBody) body;
			return new SuperPowerAccessor() {
				@Override
				public void resetSuperPower() {
					pacman.resetSuperPower();
				}
				@Override
				public void decreaseSuperPower() {
					pacman.decreaseSuperPower();
				}
			};
		}
		return new SuperPowerAccessor();
	}

	/**
	 * @author $Author: sgalland$
	 * @version $FullVersion$
	 * @mavengroupid $GroupId$
	 * @mavenartifactid $ArtifactId$
	 */
	private static class PrimWall {

		public final Point2i passage;
		public final Point2i corridor;
		public final Point2i passageCandidate1;
		public final Point2i passageCandidate2;
		public final Point2i passageCandidate3;
		
		public PrimWall(int passageX, int passageY, int corridorX, int corridorY,
				int passageCandidateX1, int passageCandidateY1,
				int passageCandidateX2, int passageCandidateY2,
				int passageCandidateX3, int passageCandidateY3) {
			this.passage = new Point2i(passageX, passageY);
			this.corridor = new Point2i(corridorX, corridorY);
			this.passageCandidate1 = new Point2i(passageCandidateX1, passageCandidateY1);
			this.passageCandidate2 = new Point2i(passageCandidateX2, passageCandidateY2);
			this.passageCandidate3 = new Point2i(passageCandidateX3, passageCandidateY3);
		}
		
		@Override
		public String toString() {
			return "passage=" + this.passage + "|corridor=" + this.corridor;
		}
		
	}

}
