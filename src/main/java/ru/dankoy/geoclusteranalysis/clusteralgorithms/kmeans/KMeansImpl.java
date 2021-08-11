package ru.dankoy.geoclusteranalysis.clusteralgorithms.kmeans;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.dankoy.geoclusteranalysis.cluster.Cluster;
import ru.dankoy.geoclusteranalysis.cluster.ClusterImpl;
import ru.dankoy.geoclusteranalysis.core.model.Crash;

/**
 * @author ezelenin
 * <p>
 * Имплементация kmeans алгоритма.
 * <p>
 * Описание алгоритма:
 * Шаг 1 - первичная инициализация кластеров:
 * 1) Из существующих точек рандомно выбираются центры кластеров.
 * 2) Так же рандомно добавляются точки в кластера
 * <p>
 * Шаг 2 - поиск ближайших точек к центроиду
 * 1) Ищутся ближайшие точки к центру кластера. Рассточние от точки до центра кластера считается по формуле haversine.
 * 2) Центр кластеров пересчитывается
 * <p>
 * Шаг 2 повторяется до тех пор пока кластера из предыдущей итерации не будут отличатся от текущей итерации.
 * <p>
 * На больших данных работает достаточно медленно
 */
@Service
public class KMeansImpl implements KMeans {

    // Радиус земли в метрах
    public static final double EARTH_RADIUS = 6372.8 * 1000;
    private static final Logger logger = LoggerFactory.getLogger(KMeansImpl.class);

    /**
     * Главный метод класса.
     *
     * @param crashes          a список из аварий {@link Crash}
     * @param amountOfClusters количество кластеров
     * @return список кластеров
     */
    public List<Cluster> cluster(List<Crash> crashes, int amountOfClusters) throws IllegalArgumentException,
            CloneNotSupportedException {

        if (amountOfClusters <= 0)
            throw new IllegalArgumentException("Expected amount of clusters > 0, but got " + amountOfClusters);

        return cluster(crashes, amountOfClusters, buildRandomInitialClusters(crashes, amountOfClusters));
    }

    /**
     * Uses the KMeans algorithm to generate k clusters from the set of points using a predefined starting set of
     * {@link Cluster}
     *
     * @param crashes          a List of {@link Crash}
     * @param amountOfClusters количество кластеров
     * @param clusters         список сгенерированных кластеров с рандомными центрами
     * @return список кластеров {@link Cluster}
     */
    protected List<Cluster> cluster(List<Crash> crashes, int amountOfClusters,
        List<Cluster> clusters) throws
            CloneNotSupportedException {

        List<Cluster> oldClusters = new ArrayList<>(amountOfClusters);

        for (var clusterIndex = 1; clusterIndex <= amountOfClusters; clusterIndex++) {
            oldClusters.add(new ClusterImpl());
        }

        logger.info("oldClusters size {}", oldClusters.size());
        logger.info("clusters size {}", clusters.size());

        var clusterizationIteration = 0;
        while (!hasConverged(oldClusters, clusters)) {
            logger.info("On iteration {}", clusterizationIteration);

            for (var j = 0; j < amountOfClusters; j++) {

                Cluster clone = (Cluster) clusters.get(j).clone();
                oldClusters.set(j, clone);
            }
            clusterizationIteration++;

            assignPointsToClusters(crashes, clusters);
            adjustClusterCenters(clusters);
        }
        return clusters;
    }

    /**
     * Генерирует центры кластеров в рандомных местах, используя существующие координаты точек {@link Cluster}
     *
     * @param crashes          список аварий {@link Crash}
     * @param amountOfClusters количество кластеров
     * @return список кластеров {@link Cluster} содержащий рандомные центры с рандомно распределенные аварии
     * {@link Crash}
     */
    private List<Cluster> buildRandomInitialClusters(List<Crash> crashes, int amountOfClusters) {

        List<Cluster> clusters = new ArrayList<>();

        if (amountOfClusters > crashes.size()) {
            throw new IllegalArgumentException("Expected amount of clusters less or equals amount of accidents, but " +
                    "got " + amountOfClusters);
        }

        for (var i = 0; i < amountOfClusters; i++) {

            clusters.add(new ClusterImpl(crashes.get(i).getLatitude(), crashes.get(i).getLongitude()));

        }

        // Заполняет кластера авариями
        var i = 0;
        for (Crash crash : crashes) {
            clusters.get(i).getPoints().add(crash);
            i++;
            if (i == amountOfClusters) {
                i = 0;
            }
        }
        return clusters;
    }


    /**
     * Распределяет точки к наиболее подходящему кластеру {@link Cluster}, то есть находит ближайшую точку к кластеру
     * по формуле haversine.
     *
     * @param crashes  список аварий {@link Crash}
     * @param clusters список кластеров {@link Cluster}
     */
    private void assignPointsToClusters(List<Crash> crashes, List<Cluster> clusters) {
        // for each point, find the cluster with the closest center
        clusters.forEach(Cluster::clearPoints);

        for (Crash crash : crashes) {
            var current = clusters.get(0);
            for (Cluster cluster : clusters) {
                if (haversineDistance(crash, cluster) < haversineDistance(crash, current)) {
                    current = cluster;
                }
            }
            logger.debug("Adding {} to {}", crash, current);
            current.getPoints().add(crash);
        }
    }


    /**
     * Пересчитывает центр кластера для каждого кластера {@link Cluster}
     *
     * @param clusters список кластеров {@link Cluster}
     */
    private void adjustClusterCenters(List<Cluster> clusters) {
        for (Cluster cluster : clusters) {
            if (!cluster.getPoints().isEmpty()) {
                double newLatitude = cluster.getSumLatitude() / cluster.getPoints().size();
                double newLongitude = cluster.getSumLongitude() / cluster.getPoints().size();
                cluster.renewCoordinates(newLatitude, newLongitude);
            }
        }
    }

    /**
     * Определяет, если кластера {@link Cluster} сошлись, то есть сравнивается результат предыдущей итерации
     * кластеризации с текущей. Если списки точек {@link Crash} кластеров {@link Cluster} не отличаются друг от друга,
     * то, считаем, что распределили все точки по кластерам корректно.
     *
     * @param previous список кластеров из предыдущей операции
     * @param current  список кластеров из текущей итерации
     * @return true, если списки сошлись. false, если списки отличаются друг от друга.
     */
    private boolean hasConverged(List<Cluster> previous, List<Cluster> current) {
        logger.info("oldClusters size {}", previous.size());
        logger.info("clusters size {}", current.size());
        if (previous.size() != current.size()) {
            throw new IllegalArgumentException("Clusters must be the same size");

        }
        for (var i = 0; i < previous.size(); i++) {
            if (!previous.get(i).getPoints().equals(current.get(i).getPoints())) {
                return false;
            }
        }
        logger.debug("Converged!");
        return true;
    }


}
