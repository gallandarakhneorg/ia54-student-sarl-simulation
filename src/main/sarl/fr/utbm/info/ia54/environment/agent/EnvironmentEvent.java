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

import java.util.EventObject;
import java.util.Map;
import java.util.UUID;

import org.eclipse.xtext.xbase.lib.Pure;

import fr.utbm.info.ia54.environment.maze.PacmanObject;
import fr.utbm.info.ia54.math.Point2i;

/** Event that describes a change in the environment.
 *
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public class EnvironmentEvent extends EventObject {

	private static final long serialVersionUID = -8054100646829546393L;

	private final int time;
	
	private final int width;
	
	private final int height;

	private final Map<Point2i, PacmanObject> objects;

	public EnvironmentEvent(UUID source, int time, int width, int height, Map<Point2i, PacmanObject> objects) {
		super(source);
		this.time = time;
		this.width = width;
		this.height = height;
		this.objects = objects;
	}
	
	@Pure
	public int getTime() {
		return this.time;
	}
	
	@Pure
	public int getWidth() {
		return this.width;
	}
	
	@Pure
	public int getHeight() {
		return this.height;
	}

	@Pure
	public Map<Point2i, PacmanObject> getObjects() {
		return this.objects;
	}

}