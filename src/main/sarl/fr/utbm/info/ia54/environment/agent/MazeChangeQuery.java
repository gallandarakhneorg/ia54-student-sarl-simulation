/* 
 * $Id$
 * 
 * Copyright (c) 2014-15 Stephane GALLAND <stephane.galland@utbm.fr>.
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
package fr.utbm.info.ia54.environment.agent;

import java.util.UUID;

import fr.utbm.info.ia54.environment.maze.Direction;


/**
 * Describes a query for the maze change.
 * 
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
final class MazeChangeQuery {
	
	private final UUID emitter;
	
	private final Direction direction;
	
	MazeChangeQuery(UUID emitter, Direction direction) {
		this.emitter = emitter;
		this.direction = direction;
	}

	/** Replies the emitter.
	 */
	public UUID getEmitter() {
		return this.emitter;
	}
	
	/** Replies the change.
	 */
	public Direction getChange() {
		return this.direction;
	}

}