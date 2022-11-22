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

import com.google.inject.Injector;

import io.sarl.core.OpenEventSpace;
import io.sarl.lang.core.EventListener;
import io.sarl.lang.core.SpaceID;
import io.sarl.lang.core.SpaceSpecification;
import io.sarl.sre.services.logging.LoggingService;
import io.sarl.sre.spaces.AbstractSreEventSpaceSpecification;
import io.sarl.sre.spaces.SpaceParticipantListener;
import io.sarl.sre.spaces.SpaceParticipantListenerFactory;

/** Space that is representing the Jaak environment.
 *
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
class MazeSpaceSpecification extends AbstractSreEventSpaceSpecification implements SpaceSpecification<MazeSpace> {

	private final SpaceParticipantListenerFactory spaceParticipantListenerFactory;

	private OpenEventSpace defaultSpace;

	private LoggingService logger;

	private Injector injector;
	
	public MazeSpaceSpecification (Injector injector,
			OpenEventSpace defaultSpace,
			SpaceParticipantListenerFactory spaceParticipantListenerFactory,
			LoggingService logger) {
		super(injector, defaultSpace, spaceParticipantListenerFactory, logger);
		this.spaceParticipantListenerFactory = spaceParticipantListenerFactory;
		this.defaultSpace = defaultSpace;
		this.logger = logger;
		this.injector = injector;
	}

	@Override
	public MazeSpace create(SpaceID id, Object... params) {
		return (MazeSpace) createMazeSpaceInstance(id, params);
	}

	protected MazeSpace createMazeSpaceInstance(SpaceID id, Object... params) {
		SpaceParticipantListener listener = this.spaceParticipantListenerFactory.create(this.defaultSpace, this.logger.getKernelLogger());
		DefaultMazeSpaceImpl space = new DefaultMazeSpaceImpl(id, listener, this.logger, (EventListener) params[0]);
		if (this.defaultSpace == null) {
			listener.setDefaultSpace(space);
		}
		this.injector.injectMembers(space);
		return space;
	}

}