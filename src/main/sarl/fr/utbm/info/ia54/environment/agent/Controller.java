/* 
 * $Id$
 * 
 * Copyright (c) 2014-15 Stephane GALLAND <stephane.galland@utbm.fr>.
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

import io.sarl.lang.core.Address;
import io.sarl.lang.core.EventSpace;
import io.sarl.util.Scopes;

import java.util.concurrent.atomic.AtomicBoolean;


/**
 * Control the simulation.
 * 
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
public final class Controller {
	
	private final AtomicBoolean started = new AtomicBoolean();
	
	private final EventSpace space;
	
	private final Address address;
	
	Controller(EventSpace space, Address emitter) {
		this.space = space;
		this.address = emitter;
	}
	
	/** Start the simulation.
	 */
	public void startSimulation() {
		if (!this.started.getAndSet(true)) {
			RunBeginingOfStep event = new RunBeginingOfStep();
			event.setSource(this.address);
			this.space.emit(event, Scopes.addresses(this.address));
		}
	}
	
	/** Replies if the simulation was started.
	 */
	public boolean isStarted() {
		return this.started.get();
	}
	
}