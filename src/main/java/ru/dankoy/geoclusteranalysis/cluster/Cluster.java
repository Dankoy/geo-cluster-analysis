package ru.dankoy.geoclusteranalysis.cluster;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import ru.dankoy.geoclusteranalysis.core.model.Crash;

public interface Cluster extends Cloneable {
    double getLatitude();

    double getLongitude();

    List<Crash> getPoints();

    void clearPoints();

    void renewCoordinates(double latitude, double longitude);

    @JsonIgnore
    double getSumLatitude();

    @JsonIgnore
    double getSumLongitude();

    long getAmountOfPoints();

    Object clone() throws CloneNotSupportedException;
}
