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
import io.sarl.lang.core.EventListener;
import io.sarl.lang.core.Space;

import java.util.UUID;

/** Space that is representing the Jaak environment.
*
* @author $Author: sgalland$
* @version $FullVersion$
* @mavengroupid $GroupId$
* @mavenartifactid $ArtifactId$
*/
public interface MazeSpace extends Space {

	/** Replies the identifier of the creator of the space.
	 *
	 * @return the identifier of the space creator.
	 */
	UUID getCreatorID();

	/** Destroy the space.
	 */
	void destroy();

	/** Spawn the body with the given ID.
	 *
	 * @param binder - the binder between the skill and the space.
	 */
	void spawnBody(EventListener binder);

	/** Destroy the body with the given ID.
	 *
	 * @param binder - the binder between the skill and the space.
	 */
	void killBody(EventListener binder);

	/** Give the perceptions to the agents that is owning the given body.
	 *
	 * @param perception - the event to give to the agent.
	 */
	void notifyPerception(Perception perception);

	/** Emit an influence for the given agent.
	 *
	 * @param influenceTime - the time at which the influence is applied.
	 * @param emitter - the identifier of the emitter.
	 * @param influence - the influence to emit.
	 */
	void influence(int influenceTime, UUID emitter, Direction direction);

}