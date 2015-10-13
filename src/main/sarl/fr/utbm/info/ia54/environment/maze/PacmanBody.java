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

import java.util.UUID;

import org.eclipse.xtext.xbase.lib.Pure;

import fr.utbm.info.ia54.math.Point2i;

/**
 * The body of the pacman.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public class PacmanBody extends AgentBody {

	private static final long serialVersionUID = -6780162285591894489L;

	public static final int SUPER_POWER = 10;
	
	private int superPower;
	
	PacmanBody(int x, int y, Maze maze, UUID agentId, int perceptionDistance) {
		super(x, y, maze, agentId, perceptionDistance);
	}

	PacmanBody(Point2i position, Maze maze, UUID agentId, int perceptionDistance) {
		super(position, maze, agentId, perceptionDistance);
	}
	
	@Override
	@Pure
	public boolean isPickable() {
		return true;
	}

	/** Replies if the pacman body has super power.
	 */
	@Pure
	public boolean isSuperPacman() {
		return this.superPower > 0;
	}

	/** Set if the pacman body has super power.
	 */
	void resetSuperPower() {
		this.superPower = SUPER_POWER;
	}

	/** Decrease the super power.
	 */
	void decreaseSuperPower() {
		if (this.superPower > 0) {
			--this.superPower;
		}
	}

}
