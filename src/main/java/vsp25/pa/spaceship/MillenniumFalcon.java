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
import java.util.Set;

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
        // Hunt Phase: Navigate toward Kamino using signal strength
        // Higher signal strength means closer to Kamino
        
        while (!state.onKamino()) {
            // Get current planet and its neighbors
            int currentID = state.currentID();
            PlanetStatus[] neighbors = state.neighbors();
            
            // Find neighbor with highest signal strength
            PlanetStatus bestNeighbor = null;
            double maxSignal = -1;
            
            for (PlanetStatus neighbor : neighbors) {
                if (neighbor.signal() > maxSignal) {
                    maxSignal = neighbor.signal();
                    bestNeighbor = neighbor;
                }
            }
            
            // Move to the neighbor with highest signal strength
            if (bestNeighbor != null) {
                state.moveTo(bestNeighbor.id());
            } else {
                // If no neighbors found, this shouldn't happen but handle gracefully
                break;
            }
        }
    }

    @Override
    public void gather(GathererStage state) {
        // Gather Phase: Collect spice while managing fuel and returning to Earth
        
        Planet currentPlanet = state.currentPlanet();
        Planet earth = state.earth();
        GraphInterface<Planet, Link> graph = state.planetGraph();
        
        // Strategy: Find shortest path to Earth, but also collect spice along the way
        // We'll use a greedy approach that balances spice collection with fuel efficiency
        
        while (state.fuelRemaining() > 0 && !currentPlanet.equals(earth)) {
            // Get current planet's neighbors
            Set<Planet> neighbors = graph.getNeighbors(currentPlanet).keySet();
            
            // Calculate shortest path to Earth
            List<Planet> pathToEarth = graph.shortestPath(currentPlanet, earth);
            
            if (pathToEarth.isEmpty()) {
                // No path to Earth exists, this shouldn't happen
                break;
            }
            
            // Find the next planet on the path to Earth
            Planet nextOnPath = null;
            if (pathToEarth.size() > 1) {
                nextOnPath = pathToEarth.get(1); // First element is current planet
            }
            
            // Look for high-spice planets among neighbors
            Planet bestNeighbor = null;
            double bestScore = -1;
            
            for (Planet neighbor : neighbors) {
                // Calculate a score that balances spice and distance to Earth
                int spice = neighbor.spice();
                int distanceToEarth = graph.pathLength(graph.shortestPath(neighbor, earth));
                int fuelCost = graph.getEdge(currentPlanet, neighbor).fuelNeeded();
                
                // Score = spice / (distance to Earth + fuel cost)
                // This prioritizes planets with high spice that don't take us too far from Earth
                double score = spice > 0 ? (double) spice / (distanceToEarth + fuelCost + 1) : 0;
                
                // Bonus for planets that are on the path to Earth
                if (nextOnPath != null && neighbor.equals(nextOnPath)) {
                    score += 10; // Strong preference for staying on path to Earth
                }
                
                if (score > bestScore) {
                    bestScore = score;
                    bestNeighbor = neighbor;
                }
            }
            
            // Move to the best neighbor
            if (bestNeighbor != null) {
                state.moveTo(bestNeighbor);
                currentPlanet = bestNeighbor;
            } else {
                // No valid neighbors, this shouldn't happen
                break;
            }
        }
        
        // If we have fuel left and we're not on Earth, try to get to Earth
        if (state.fuelRemaining() > 0 && !currentPlanet.equals(earth)) {
            List<Planet> finalPath = graph.shortestPath(currentPlanet, earth);
            for (int i = 1; i < finalPath.size() && state.fuelRemaining() > 0; i++) {
                Planet nextPlanet = finalPath.get(i);
                if (graph.getNeighbors(currentPlanet).keySet().contains(nextPlanet)) {
                    state.moveTo(nextPlanet);
                    currentPlanet = nextPlanet;
                } else {
                    break;
                }
            }
        }
    }
}
