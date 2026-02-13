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

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

import io.bootique.di.Injector;
import io.sarl.api.core.OpenEventSpace;
import io.sarl.lang.core.SpaceSpecification;
import io.sarl.sre.janus.KernelScope;
import io.sarl.sre.janus.services.logging.LoggingService;
import io.sarl.sre.janus.spaces.SpaceParticipantListenerFactory;
import io.sarl.sre.janus.spaces.SreSpaceSpecificationFactory;

/** Space that is representing the Jaak environment.
 *
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
@Singleton
class MazeSpaceSpecificationFactory extends SreSpaceSpecificationFactory {

	@Inject
	private Injector injector;

	@Inject
	@KernelScope
	Provider<SpaceParticipantListenerFactory> spaceParticipantListenerFactory;

	@Inject
	private Provider<LoggingService> logger;

	@Override
	protected SpaceSpecification<?> newSpaceSpecification(Class<? extends SpaceSpecification<?>> type, OpenEventSpace defaultSpace) {
		if (type.equals(MazeSpaceSpecification.class)) {
			return new MazeSpaceSpecification(this.injector, defaultSpace,
				this.spaceParticipantListenerFactory.get(), this.logger.get());
		}
		return super.newSpaceSpecification(type, defaultSpace);
	}

}