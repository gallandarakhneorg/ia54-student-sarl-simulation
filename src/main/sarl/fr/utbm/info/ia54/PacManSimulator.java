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
package fr.utbm.info.ia54;

import fr.utbm.info.ia54.environment.agent.Environment;
import fr.utbm.info.ia54.ui.PacManGUI;
import io.janusproject.Boot;
import io.janusproject.util.LoggerCreator;

import java.util.logging.Level;

/**
 * Launcher of the simulation framework.
 *
 * This launcher needs the {@link http://www.janusproject.io Janus platform}.
 * 
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
public class PacManSimulator {
	
	/** Width of the world (in number of cells).
	 */
	public static int WIDTH = 25;

	/** Height of the world (in number of cells).
	 */
	public static int HEIGHT = 25;

	/** Number of ghosts at the start-up.
	 */
	public static int NB_GHOSTS = 3;
	
	/** Percpetion distance for the agents (usually the ghosts).
	 */
	public static int PERCEPTION_DISTANCE = 5;
	
	/** The UI will force the environment agent to wait for it.
	 */
	public static int WAITING_DURATION = 500;
	
	public static void main(String[] args) throws Exception {
		Boot.setOffline(true);
		Boot.setVerboseLevel(LoggerCreator.toInt(Level.INFO));
		Boot.showJanusLogo();
		
		PacManGUI ui = new PacManGUI(WAITING_DURATION);
		
		Boot.startJanus(
				(Class) null,
				Environment.class,
				WIDTH,
				HEIGHT,
				NB_GHOSTS,
				PERCEPTION_DISTANCE,
				ui);
	}
	
}