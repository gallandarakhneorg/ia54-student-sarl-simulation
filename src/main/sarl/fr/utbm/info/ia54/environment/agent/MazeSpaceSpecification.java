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

import java.util.concurrent.locks.ReadWriteLock;

import org.arakhne.afc.vmutil.locale.Locale;

import io.janusproject.services.distributeddata.DistributedDataStructureService;
import io.sarl.lang.core.EventListener;
import io.sarl.lang.core.Space;
import io.sarl.lang.core.SpaceID;
import io.sarl.lang.core.SpaceSpecification;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;

/** Space that is representing the Jaak environment.
 *
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
class MazeSpaceSpecification implements SpaceSpecification<MazeSpace> {

	@Inject
	private DistributedDataStructureService dataStructureService;

	@Inject
	private Injector injector;

	@Inject
	private Provider<ReadWriteLock> lockProvider;
	
	/**
	 */
	public MazeSpaceSpecification() {
		//
	}

	@Override
	public MazeSpace create(SpaceID id, Object... params) {
		if (params.length >= 1 && params[0] instanceof EventListener) {
			return createSpace(id, this.dataStructureService, (EventListener) params[0]);
		}
		throw new IllegalArgumentException(Locale.getString(MazeSpaceSpecification.class, "NO_EVENT_LISTENER"));
	}

	/**
	 * Creates a {@link Space} that respects this specification.
	 *
	 * @param spaceId - the {@link SpaceID} for the newly created {@link Space}
	 * @param factory - the factory to be used for creating distributed data structures.
	 * @param environmentAgent - the reference to the agent listener which is managing the environment,
	 * or <code>null</code> if the current instance of the space is not directly linked to the
	 * environment agent.
	 * @return an instance of {@link Space}
	 */
	public MazeSpace createSpace(SpaceID spaceId, DistributedDataStructureService factory,
			EventListener environmentAgent) {
		MazeSpace space = new DefaultMazeSpaceImpl(spaceId, factory, this.lockProvider, environmentAgent);
		this.injector.injectMembers(space);
		return space;
	}

}