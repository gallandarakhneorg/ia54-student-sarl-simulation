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

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.Objects;

import org.eclipse.xtext.xbase.lib.Pure;

import fr.utbm.info.ia54.math.Point2i;

/**
 * Object in the PacMan environment.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public abstract class PacmanObject implements Serializable {

	private static final long serialVersionUID = -2687900803025474730L;

	private final Point2i position;
	
	private transient final WeakReference<Maze> maze;
	
	PacmanObject(int x, int y, Maze maze) {
		this.position = new Point2i(x, y);
		this.maze = new WeakReference<Maze>(maze);
	}

	PacmanObject(Point2i position, Maze maze) {
		this(position.getX(), position.getY(), maze);
	}
	
	@Override
	@Pure
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj != null && getClass().equals(obj.getClass())) {
			PacmanObject o = (PacmanObject) obj;
			return o.getPosition().equals(getPosition());
		}
		return false;
	}
	
	@Override
	@Pure
	public int hashCode() {
		return Objects.hash(position);
	}
	
	/** Replies the position of the object.
	 * 
	 * @return the position.
	 */
	@Pure
	public Point2i getPosition() {
		return this.position.clone();
	}
	
	/** Change the position of the object.
	 */
	void setPosition(int x, int y) {
		this.position.set(x, y);
	}

	/** Replies the maze in which this object is located.
	 * 
	 * @return the maze.
	 */
	@Pure
	Maze getMaze() {
		return (this.maze == null) ? null : this.maze.get();
	}

	/** Replies if this object occludes other objects during the agent's perception.
	 */
	@Pure
	public abstract boolean isOccluder();
	
	/** Replies if this object could be pick by the agents.
	 */
	@Pure
	public abstract boolean isPickable();

}
