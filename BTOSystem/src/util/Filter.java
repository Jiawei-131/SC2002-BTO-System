package util;

import java.util.Comparator;
import entities.Project;

/**
 * Interface representing filter functionality for projects.
 * Allows setting of various filter criteria and sorting options for projects.
 */
public interface Filter {

    /**
     * Gets the current filter description.
     *
     * @return the filter description (e.g., "Alphabetical", "Neighbourhood", etc.).
     */
    String getFilterDescription();
    
    /**
     * Sets the filter description.
     *
     * @param desc the filter description to set (e.g., "Neighbourhood", "Opening Date", etc.).
     */
    void setFilterDescription(String desc);

    /**
     * Gets the current comparator used to sort projects.
     *
     * @return the comparator that defines how projects are sorted.
     */
    Comparator<Project> getComparator();
    
    /**
     * Sets the comparator used to sort projects.
     *
     * @param comp the comparator to set.
     */
    void setComparator(Comparator<Project> comp);

    /**
     * Sets the filter based on a user choice, adjusting the comparator and filter description.
     * The user can choose different sorting criteria for the projects.
     *
     * @param choice the choice number representing the filter criteria (1-5).
     *               1 - Neighbourhood,
     *               2 - Number of 2-Room Units,
     *               3 - Number of 3-Room Units,
     *               4 - Opening Date,
     *               5 - Closing Date.
     *               Any other value defaults to alphabetical sorting.
     */
    default void setFilter(int choice) {
        switch (choice) {
            case 1 -> {
                setComparator(Comparator.comparing(Project::getNeighbourhood));
                setFilterDescription("Neighbourhood");
            }
            case 2 -> {
                setComparator(Comparator.comparingInt(Project::getNumberOfType1Units));
                setFilterDescription("Number of 2-Room Units");
            }
            case 3 -> {
                setComparator(Comparator.comparingInt(Project::getNumberOfType2Units));
                setFilterDescription("Number of 3-Room Units");
            }
            case 4 -> {
                setComparator(Comparator.comparing(Project::getOpeningDate));
                setFilterDescription("Opening Date");
            }
            case 5 -> {
                setComparator(Comparator.comparing(Project::getClosingDate));
                setFilterDescription("Closing Date");
            }
            default -> {
                setComparator(Comparator.comparing(Project::getName));
                setFilterDescription("Alphabetical");
            }
        }
    }
}
