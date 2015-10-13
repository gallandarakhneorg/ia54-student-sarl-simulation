# Lecture: IA54 - Multiagent Systems (Chapter: Agent-based Simulation)

This lecture aims to provide the background for creating agent-based simulators.

This repository contains the source code that is used by the students during the lab works associated to the lecture.
This lecture has the goal to create a simulator based on the Pacman game.

Author: Stéphane Galland.
License: Apache License 2

## Content of this repository
 
* `src/main/sarl/fr/utbm/info/ia54/environment` contains the definition of the environment (including the maze) based on the [SARL agent-oriented language](http://www.sarl.io) and the [Janus platform](http://www.janusproject.io).
* `src/main/java/fr/utbm/info/ia54/players` contains the different agents that are used in the Pacman game.
* `src/main/java/fr/utbm/info/ia54/PacManSimulator` contains the main program.

## Installation

* Download the [version 2.1.0.0](http://www.janusproject.io/#download) (or higher) of the Janus platform.
* Download the [Eclipse product 1.0.0](http://www.sarl.io/download/) for the SARL agent programming language.
* Launch the SARL Eclipse product.
* Create a SARL project.
* In the buildpath of the project: remove the "SARL libraries".
* In the buildpath of the project: add the external Jar of the Janus platform.
* Copy the source code from this repository in the project.

## Launching

* Create a launch configuration for a Java application.
* Select the main function in the `PacManSimulator` class.
