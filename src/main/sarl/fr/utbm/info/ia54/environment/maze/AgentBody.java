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
 * Abstract element for all the agent bodies.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public abstract class AgentBody extends PacmanObject implements Comparable<AgentBody> {

	private static final long serialVersionUID = 153923572475856880L;

	private final UUID agentId;
	private final int perceptionDistance;
	
	AgentBody(int x, int y, Maze maze, UUID agentId, int perceptionDistance) {
		super(x, y, maze);
		this.agentId = agentId;
		this.perceptionDistance = perceptionDistance;
	}

	AgentBody(Point2i position, Maze maze, UUID agentId, int perceptionDistance) {
		super(position, maze);
		this.agentId = agentId;
		this.perceptionDistance = perceptionDistance;
	}
	
	/** Replies the distance of perception of the body.
	 *
	 * @return the distance.
	 */
	@Pure
	public int getPerceptionDistance() {
		return this.perceptionDistance;
	}
	
	@Pure
	public final UUID getAgentId() {
		return this.agentId;
	}

	@Override
	@Pure
	public final boolean isOccluder() {
		return false;
	}
	
	@Override
	public int compareTo(AgentBody o) {
		if (o == this) {
			return 0; 
		}
		if (o == null) {
			return Integer.MAX_VALUE;
		}
		return getAgentId().compareTo(o.getAgentId());
	}

}
