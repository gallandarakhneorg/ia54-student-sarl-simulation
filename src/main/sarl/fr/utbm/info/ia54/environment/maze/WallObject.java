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

import org.eclipse.xtext.xbase.lib.Pure;

import fr.utbm.info.ia54.math.Point2i;

/**
 * Wall in the PacMan environment.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public class WallObject extends PacmanObject {

	private static final long serialVersionUID = -8482300397595682515L;

	WallObject(int x, int y, Maze maze) {
		super(x, y, maze);
	}

	WallObject(Point2i position, Maze maze) {
		super(position, maze);
	}

	@Override
	@Pure
	public final boolean isOccluder() {
		return true;
	}

	@Override
	@Pure
	public final boolean isPickable() {
		return false;
	}

}
