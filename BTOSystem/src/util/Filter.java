package util;

import java.util.Comparator;
import entities.Project;

public interface Filter {
    String getFilterDescription();
    void setFilterDescription(String desc);

    Comparator<Project> getComparator();
    void setComparator(Comparator<Project> comp);

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
