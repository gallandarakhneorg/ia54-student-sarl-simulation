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

import java.util.Objects;

import org.eclipse.xtext.xbase.lib.Pure;

import fr.utbm.info.ia54.math.Point2i;

/**
 * Pill in the PacMan environment.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public class PillObject extends PacmanObject {

	private static final long serialVersionUID = 1985603764851937190L;
	
	private final boolean isSuperPill;
	
	PillObject(int x, int y, Maze maze, boolean isSuperPill) {
		super(x, y, maze);
		this.isSuperPill = isSuperPill;
	}

	PillObject(Point2i position, Maze maze, boolean isSuperPill) {
		super(position, maze);
		this.isSuperPill = isSuperPill;
	}
	
	@Override
	@Pure
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		PillObject o = (PillObject) obj;
		return o.isSuperPill() == isSuperPill();
	}
	
	@Override
	@Pure
	public int hashCode() {
		return Objects.hash(super.hashCode(), this.isSuperPill);
	}
	
	/** Replies the pill has the super power.
	 */
	@Pure
	public boolean isSuperPill() {
		return this.isSuperPill;
	}

	@Override
	@Pure
	public final boolean isOccluder() {
		return false;
	}

	@Override
	@Pure
	public boolean isPickable() {
		return true;
	}
	
}
