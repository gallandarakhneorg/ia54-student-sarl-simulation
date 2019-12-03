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

import fr.utbm.info.ia54.environment.maze.Direction;
import io.janusproject.services.distributeddata.DistributedDataStructureService;
import io.sarl.lang.core.Address;
import io.sarl.lang.core.Event;
import io.sarl.lang.core.EventListener;
import io.sarl.lang.core.Scope;
import io.sarl.lang.core.SpaceID;

import java.text.MessageFormat;
import java.util.UUID;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.logging.Level;

import com.google.inject.Provider;

/** Abstract implementation of a distributed space.
 *
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
class DefaultMazeSpaceImpl extends AbstractDistributedSpace implements MazeSpace {

	private EventListener environmentAgent;
	
	/**
	 * @param id - the identifier of the space.
	 * @param factory - the factory to be used for creating distributed data structures.
	 * @param lockProvider - the provider of locks.
	 * @param environmentAgent - the reference to the agent listener which is managing the environment,
	 * or <code>null</code> if the current instance of the space is not directly linked to the
	 * environment agent.
	 */
	public DefaultMazeSpaceImpl(SpaceID id, DistributedDataStructureService factory,
			Provider<ReadWriteLock> lockProvider, EventListener environmentAgent) {
		super(id, factory, lockProvider);
		this.environmentAgent = environmentAgent;
	}

	@Override
	public void destroy() {
		if (this.environmentAgent != null) {
			this.environmentAgent = null;
			this.sharedAttributes.remove(KEY_CREATORID);
		}
	}

	@Override
	public void spawnBody(EventListener binder) {
		synchronized (this.agents) {
			this.agents.registerParticipant(binder.getID(), binder);
		}
	}

	@Override
	public void killBody(EventListener binder) {
		synchronized (this.agents) {
			this.agents.unregisterParticipant(binder);
		}
	}

	@Override
	public void notifyPerception(Perception perception) {
		UUID id = perception.bodyId;
		if (!putOnEventBus(perception, id)) {
			putOnNetwork(perception, id);
		}
	}

	@Override
	public void influence(int influenceTime, UUID emitter, Direction influence) {
		Event event = new Action(influenceTime, influence);
		event.setSource(new Address(getSpaceID(), emitter));
		if (this.environmentAgent != null) {
			fireAsync(this.environmentAgent, event);
		} else {
			putOnNetwork(event, getCreatorID());
		}
	}

	@Override
	public void eventReceived(SpaceID space, Scope<?> scope, Event event) {
		if (scope instanceof UUIDScope) {
			UUID id = ((UUIDScope) scope).getID();
			putOnEventBus(event, id);
		} else {
			this.logger.getKernelLogger().log(
					Level.SEVERE,
					MessageFormat.format("Invalid scope {0} for event {1}", scope, event)); //$NON-NLS-1$
		}
	}
	
}