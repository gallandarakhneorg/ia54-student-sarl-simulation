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

import io.janusproject.kernel.repository.UniqueAddressParticipantRepository;
import io.janusproject.kernel.space.SpaceBase;
import io.janusproject.services.distributeddata.DMap;
import io.janusproject.services.distributeddata.DistributedDataStructureService;
import io.janusproject.services.executor.ExecutorService;
import io.janusproject.services.logging.LogService;
import io.janusproject.services.network.NetworkService;
import io.sarl.lang.core.Event;
import io.sarl.lang.core.EventListener;
import io.sarl.lang.core.Scope;
import io.sarl.lang.core.SpaceID;
import io.sarl.lang.util.SynchronizedSet;
import io.sarl.util.concurrent.Collections3;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.UUID;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.logging.Level;

import com.google.inject.Inject;
import com.google.inject.Provider;

/** Abstract implementation of a distributed space.
 *
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
abstract class AbstractDistributedSpace extends SpaceBase {

	/** Name of the shared attribute that is the ID of the creator of the space.
	 */
	public static final String KEY_CREATORID = "creatorID"; //$NON-NLS-1$

	/** Repository of the agents in the space.
	 */
	protected final UniqueAddressParticipantRepository<UUID> agents;

	/** Shared attributes in the space.
	 */
	protected final DMap<String, Serializable> sharedAttributes;

	/** The logging service.
	 */
	@Inject
	protected LogService logger;

	@Inject
	private ExecutorService executorService;

	@Inject
	private NetworkService network;

	/**
	 * @param id - the identifier of the space.
	 * @param factory - the factory to be used for creating distributed data structures.
	 * @param lockProvider - the provider of locks.
	 */
	public AbstractDistributedSpace(SpaceID id, DistributedDataStructureService factory,
			Provider<ReadWriteLock> lockProvider) {
		super(id);
		assert (id != null);
		this.agents = new UniqueAddressParticipantRepository<>(
				getSpaceID().getID().toString() + "-mazespace-agents", //$NON-NLS-1$
				factory, lockProvider);
		this.sharedAttributes = factory.getMap(getSpaceID().getID().toString() + "-mazespace-attributes"); //$NON-NLS-1$
	}

	@Override
	public SynchronizedSet<UUID> getParticipants() {
		synchronized (this.agents) {
			return Collections3.unmodifiableSynchronizedSet(this.agents.getParticipantIDs());
		}
	}

	public UUID getCreatorID() {
		return (UUID) this.sharedAttributes.get(KEY_CREATORID);
	}

	/** Do the emission of the event on the local event bus.
	 *
	 * @param event - the event to emit.
	 * @param scope - the scope.
	 * @return <code>true</code> if the event was dispatched on the local bus.
	 */
	protected boolean putOnEventBus(Event event, UUID scope) {
		synchronized (this.agents) {
			for (EventListener agent : this.agents.getListeners()) {
				if (scope.equals(agent.getID())) {
					fireAsync(agent, event);
					return true;
				}
			}
			return false;
		}
	}

	/** Do the emission of the event over the network.
	 *
	 * @param event - the event to emit.
	 * @param scope - the scope.
	 */
	protected void putOnNetwork(Event event, UUID scope) {
		try {
			this.network.publish(new UUIDScope(scope), event);
		} catch (Exception e) {
			this.logger.getKernelLogger().log(
					Level.SEVERE,
					MessageFormat.format("Cannot notify over the network; scope={0}, event={1}, exception={2}",
					scope, event, e)); //$NON-NLS-1$
		}
	}

	/** Send the event to the given listener asyncronously.
	 *
	 * @param agent - the listener to notify.
	 * @param event - the event to send.
	 */
	protected void fireAsync(EventListener agent, Event event) {
		this.executorService.submit(new AsyncRunner(agent, event));
	}

	/** Implement a scope matching a single UUID.
	 *
	 * @author $Author: sgalland$
	 * @version $FullVersion$
	 * @mavengroupid $GroupId$
	 * @mavenartifactid $ArtifactId$
	 */
	protected static class UUIDScope implements Scope<UUID> {

		private static final long serialVersionUID = -2678036964365090540L;

		private final UUID id;

		/**
		 * @param id - the identifier.
		 */
		public UUIDScope(UUID id) {
			this.id = id;
		}

		/** Replies the identifier that is matched by this scope.
		 *
		 * @return the identifier.
		 */
		public UUID getID() {
			return this.id;
		}

		@Override
		public boolean matches(UUID element) {
			return this.id.equals(element);
		}

	}

	/**
	 * @author $Author: sgalland$
	 * @version $FullVersion$
	 * @mavengroupid $GroupId$
	 * @mavenartifactid $ArtifactId$
	 */
	private static class AsyncRunner implements Runnable {

		private final EventListener agent;
		private final Event event;

		/**
		 * @param agent
		 * @param event
		 */
		public AsyncRunner(EventListener agent, Event event) {
			this.agent = agent;
			this.event = event;
		}

		@Override
		public void run() {
			this.agent.receiveEvent(this.event);
		}

		@Override
		public String toString() {
			return "[agent=" + this.agent + "; event=" + this.event + "]"; //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
		}

	}

}