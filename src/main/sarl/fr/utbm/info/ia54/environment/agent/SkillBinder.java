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

package fr.utbm.info.ia54.environment.agent;

import io.sarl.lang.core.Event;
import io.sarl.lang.core.EventListener;

import java.util.UUID;

/** Object that may be used to link a skill to a MazeSpace.
 *
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public class SkillBinder implements EventListener {

	private final EventListener owner;
	private int currentTime;
	private final UUID bodyId;

	/**
	 * @param owner - the owner of the skill.
	 * @param id - the identifier of the agent / body.
	 */
	public SkillBinder(EventListener owner, UUID id) {
		this.owner = owner;
		this.bodyId = id;
	}

	/** Replies the owner.
	 *
	 * @return the owner.
	 */
	public EventListener getOwner() {
		return this.owner;
	}

	/** Replies the current simulation time.
	 *
	 * @return the current time.
	 */
	public int getCurrentTime() {
		return this.currentTime;
	}

	@Override
	public UUID getID() {
		return this.bodyId;
	}

	@Override
	public void receiveEvent(Event event) {
		if (event instanceof Perception) {
			Perception perception = (Perception) event;
			this.currentTime = perception.time;
			//
			this.owner.receiveEvent(event);
		}
	}

}
