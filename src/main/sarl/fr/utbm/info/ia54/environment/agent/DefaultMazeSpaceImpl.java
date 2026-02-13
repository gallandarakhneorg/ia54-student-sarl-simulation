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

import java.util.UUID;

import fr.utbm.info.ia54.environment.maze.Direction;
import io.sarl.lang.core.Address;
import io.sarl.lang.core.Event;
import io.sarl.lang.core.EventListener;
import io.sarl.lang.core.SpaceID;
import io.sarl.sre.janus.services.logging.LoggingService;
import io.sarl.sre.janus.spaces.OpenLocalEventSpace;
import io.sarl.sre.janus.spaces.SpaceParticipantListener;

/** Abstract implementation of a distributed space.
 *
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
class DefaultMazeSpaceImpl extends OpenLocalEventSpace implements MazeSpace {

	private EventListener environmentAgent;
	
	/**
	 * @param id - the identifier of the space.
	 * @param environmentAgent - the reference to the agent listener which is managing the environment,
	 * or <code>null</code> if the current instance of the space is not directly linked to the
	 * environment agent.
	 */
	public DefaultMazeSpaceImpl(SpaceID spaceId, SpaceParticipantListener participantListener, LoggingService logger,
			EventListener environmentAgent) {
		super(spaceId, participantListener, logger);
		this.environmentAgent = environmentAgent;
	}

	@Override
	public void destroy() {
		if (this.environmentAgent != null) {
			this.environmentAgent = null;
		}
	}

	@Override
	public void spawnBody(EventListener binder) {
		registerToSpace(binder, false);
	}

	@Override
	public void killBody(EventListener binder) {
		unregisterFromSpace(binder);
	}

	@Override
	public void notifyPerception(Perception perception) {
		UUID id = perception.bodyId;
		emit(id, perception);
	}

	@Override
	public void influence(int influenceTime, UUID emitter, Direction influence) {
		Event event = new Action(influenceTime, influence);
		event.setSource(new Address(getSpaceID(), emitter));
		this.environmentAgent.receiveEvent(event);
	}
	
}