package vsp25.pa.spaceship;

import vsp25.pa.controllers.GathererStage;
import vsp25.pa.controllers.HunterStage;
import vsp25.pa.controllers.Spaceship;
import vsp25.pa.graph.GraphInterface;
import vsp25.pa.models.Link;
import vsp25.pa.models.Planet;
import vsp25.pa.models.PlanetStatus;
import vsp25.pa.util.Heap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * An instance implements the methods needed to complete the mission.
 * 
 * TASK 5 REQUIREMENTS:
 * - Implement hunt(HunterStage state): Navigate to find Kamino
 * - Implement gather(GathererStage state): Collect spice and return to Earth
 * 
 * GAME MECHANICS:
 * - Hunt Phase: Use signal strength to navigate toward Kamino
 * - Gather Phase: Collect spice while managing fuel consumption
 * - Scoring: Higher scores for faster completion and more spice collected
 */
public class MillenniumFalcon implements Spaceship {
    long startTime = System.nanoTime(); // start time of rescue phase

    @Override
    public void hunt(HunterStage state) {
        // TODO: Implement this method
        
        // HUNT PHASE STRATEGY:
        // - Use state.currentID() to get current planet ID
        // - Use state.neighbors() to get neighboring planet IDs
        // - Use state.signal() to get signal strength from Kamino at each planet
        // - Use state.onKamino() to check if you've reached Kamino
        // - Use state.moveTo(int id) to move to a neighboring planet
        
        // IMPLEMENTATION TIPS:
        // - Higher signal strength means closer to Kamino
        // - Move toward planets with higher signal strength
        // - Consider using your graph algorithms from Tasks 3-4
        // - You can use breadth-first search or greedy approach
        // - No fuel constraints in hunt phase
        
        // EXAMPLE APPROACH:
        // 1. Get current planet and its neighbors
        // 2. Find neighbor with highest signal strength
        // 3. Move to that neighbor
        // 4. Repeat until onKamino() returns true
        // 5. Return from method
    }

    @Override
    public void gather(GathererStage state) {
        // TODO: Implement this method
        
        // GATHER PHASE STRATEGY:
        // - Use state.currentNode() to get current planet
        // - Use state.earth() to get Earth planet
        // - Use state.planets() to get all planets in the graph
        // - Use state.fuelRemaining() to check fuel
        // - Use link.fuelNeeded() to check fuel cost for travel
        // - Use state.moveTo(Planet p) to move to adjacent planet
        // - Each planet has spice that's automatically collected
        
        // IMPLEMENTATION TIPS:
        // - You have access to the full graph via state
        // - Use your shortestPath algorithm from Task 3
        // - Consider fuel efficiency when planning routes
        // - Balance spice collection with fuel consumption
        // - Must return to Earth before running out of fuel
        
        // EXAMPLE APPROACH:
        // 1. Use shortestPath to find route to Earth
        // 2. Identify high-spice planets along the way
        // 3. Plan route that maximizes spice while ensuring fuel sufficiency
        // 4. Execute the route, collecting spice at each planet
        // 5. Return to Earth before fuel runs out
    }
}
