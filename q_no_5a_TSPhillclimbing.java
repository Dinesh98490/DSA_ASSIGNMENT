
// Implement travelling a salesman problem using hill climbing algorithm

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class q_no_5a_TSPhillclimbing {
    // Class to represent a town with coordinates
    static class Town {
        int x, y;

        Town(int x, int y) {
            this.x = x;
            this.y = y;
        }

        // Calculate the Euclidean distance between this town and another town
        double distanceTo(Town other) {
            int deltaX = this.x - other.x;
            int deltaY = this.y - other.y;
            return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
        }
    }

    // Calculate the total distance of a given tour
    static double calculateTourDistance(List<Town> towns, List<Integer> tour) {
        double totalDistance = 0;
        int numTowns = tour.size();
        
        for (int i = 0; i < numTowns; i++) {
            Town fromTown = towns.get(tour.get(i));
            Town toTown = towns.get(tour.get((i + 1) % numTowns));
            totalDistance += fromTown.distanceTo(toTown);
        }
        return totalDistance;
    }

    // Generate a random initial tour
    static List<Integer> createRandomTour(int numTowns) {
        List<Integer> tour = new ArrayList<>();
        for (int i = 0; i < numTowns; i++) {
            tour.add(i);
        }
        Collections.shuffle(tour);
        return tour;
    }

    // Perform the hill climbing algorithm to find the shortest tour
    static List<Integer> performHillClimbing(List<Town> towns, int maxIterations) {
        List<Integer> currentTour = createRandomTour(towns.size());
        double currentDistance = calculateTourDistance(towns, currentTour);

        Random random = new Random();

        for (int i = 0; i < maxIterations; i++) {
            // Create a new tour by swapping two towns
            List<Integer> newTour = new ArrayList<>(currentTour);
            int index1 = random.nextInt(towns.size());
            int index2 = random.nextInt(towns.size());

            // Swap two towns
            Collections.swap(newTour, index1, index2);

            double newDistance = calculateTourDistance(towns, newTour);

            // Accept the new tour if it has a shorter distance
            if (newDistance < currentDistance) {
                currentTour = newTour;
                currentDistance = newDistance;
            }
        }

        return currentTour;
    }

    public static void main(String[] args) {
        // Define example towns
        List<Town> towns = List.of(
            new Town(60, 200),
            new Town(180, 200),
            new Town(80, 180),
            new Town(140, 180),
            new Town(20, 160),
            new Town(100, 160),
            new Town(200, 160),
            new Town(140, 140),
            new Town(40, 120),
            new Town(100, 120)
        );

        // Define parameters
        int maxIterations = 10000;

        // Execute hill climbing algorithm
        List<Integer> bestTour = performHillClimbing(towns, maxIterations);

        // Print the best tour found
        System.out.println("Best tour found:");
        for (int townIndex : bestTour) {
            Town town = towns.get(townIndex);
            System.out.printf("Town %d: (%d, %d)%n", townIndex, town.x, town.y);
        }

        // Print the total distance of the best tour
        double bestDistance = calculateTourDistance(towns, bestTour);
        System.out.printf("Total distance: %.2f%n", bestDistance);
    }
}
