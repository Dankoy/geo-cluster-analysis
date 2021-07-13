package ru.dankoy.geoclusteranalysis.cluster;

import java.util.ArrayList;
import java.util.List;
import ru.dankoy.geoclusteranalysis.core.model.Crash;

public class ClusterImpl implements Cluster {

    private long id;
    private double latitude;
    private double longitude;

    private List<Crash> points = new ArrayList<>();

    public ClusterImpl() {
    }

    public ClusterImpl(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public double getLatitude() {
        return latitude;
    }

    @Override
    public double getLongitude() {
        return longitude;
    }

    @Override
    public List<Crash> getPoints() {
        return points;
    }

    @Override
    public void clearPoints() {
        this.points = new ArrayList<>();
    }

    @Override
    public void renewCoordinates(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public double getSumLatitude() {
        double sum = 0;
        for (Crash point : points) {
            sum = sum + point.getLatitude();
        }
        return sum;
    }

    @Override
    public double getSumLongitude() {
        double sum = 0;
        for (Crash point : points) {
            sum = sum + point.getLongitude();
        }
        return sum;
    }

    @Override
    public long getAmountOfPoints() {
        return points.size();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
