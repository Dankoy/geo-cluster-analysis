package ru.dankoy.geoclusteranalysis.clusteralgorithms.kmeans;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import ru.dankoy.geoclusteranalysis.cluster.Cluster;
import ru.dankoy.geoclusteranalysis.cluster.ClusterImpl;
import ru.dankoy.geoclusteranalysis.core.model.Crash;

public class KMeansPlusPlusImpl extends KMeansImpl {


  @Override
  public List<Cluster> cluster(List<Crash> crashes, int amountOfClusters)
      throws CloneNotSupportedException {

    if (amountOfClusters <= 0) {
      throw new IllegalArgumentException(
          "Expected amount of clusters > 0, but got " + amountOfClusters);
    }

    return cluster(crashes, amountOfClusters, getPerfectClusterCentroids(crashes,
        amountOfClusters));

  }

  /**
   * Рассчитывает и получает оптимальные центроиды при старте алгоритма
   *
   * @param crashes a список из аварий {@link Crash}
   * @return список центроидов с оптимальным центром
   */
  private List<Cluster> getPerfectClusterCentroids(List<Crash> crashes, int amountOfClusters) {

    var sum = 0;

    var clusters = new ArrayList<>();
    Random rand = new Random();

    // nextInt is normally exclusive of the top value,
    // so add 1 to make it inclusive
    int randomNum = rand.nextInt((crashes.size() - 1) + 1) + 1;

    for (var i = 1; i <= amountOfClusters; i++) {

      // Первый центроид выбирается рандомно
      Cluster firstCentroid = new ClusterImpl(crashes.get(randomNum).getLatitude(),
          crashes.get(randomNum).getLatitude());

      clusters.add(firstCentroid);

      // Считается дистанция от аварии до центроида
      sum += crashes.stream().mapToDouble(crash -> haversineDistance(crash, firstCentroid))
          .map(distance -> distance * distance).mapToInt(distance -> (int) distance).sum();


    }

    return null;
  }

}
