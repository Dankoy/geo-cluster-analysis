package ru.dankoy.geoclusteranalysis.clusteralgorithms.kmeans;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.springframework.stereotype.Service;
import ru.dankoy.geoclusteranalysis.cluster.Cluster;
import ru.dankoy.geoclusteranalysis.cluster.ClusterImpl;
import ru.dankoy.geoclusteranalysis.core.model.Crash;

@Service
public class KMeansPlusPlusImpl extends KMeansImpl {

  private final Random rand = new Random();

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
   * @param crashes список из аварий {@link Crash}
   * @return список центроидов с оптимальным центром
   */
  private List<Cluster> getPerfectClusterCentroids(List<Crash> crashes, int amountOfClusters) {

    var sum = 0D;

    List<Cluster> clusters = new ArrayList<>();

    // Первый центроид выбирается рандомно
    int randomNum = rand.nextInt((crashes.size() - 1) + 1) + 1;
    Cluster centroid = new ClusterImpl(crashes.get(randomNum).getLatitude(),
        crashes.get(randomNum).getLongitude());

    // Цикл по количеству кластеров ищет наиболее подходящие точки
    for (var i = 1; i <= amountOfClusters; i++) {

      clusters.add(centroid);

      for (Crash crash : crashes) {

        sum = addDistanceToSum(sum, crash, centroid);

      }

      // Получаем магическое число которое подскажет, где следующий центроид
      var magicNumber = getRandomSum(sum);

      // считается сумма расстояний пока эта сумма не станет больше чем магическое число
      sum = 0;
      for (Crash crash : crashes) {

        sum = addDistanceToSum(sum, crash, centroid);

        if (sum >= magicNumber) {
          centroid = new ClusterImpl(crash.getLatitude(), crash.getLongitude());
          break;
        }
      }

    }

    // Заполняет кластера авариями
    addCrashesToCluster(crashes, clusters);

    return clusters;
  }

  /**
   * Считает квадрат расстояния точки до центроида и суммирует это значение с переданной суммой
   *
   * @param sum      сумма
   * @param crash    авария
   * @param centroid центроид
   * @return сумму предыдущего значения суммы с квадратом расстояния
   */
  private double addDistanceToSum(double sum, Crash crash, Cluster centroid) {

    var distance = haversineDistance(crash, centroid);
    distance = distance * distance;

    sum = sum + distance;
    return sum;

  }

  /**
   * Возвращает магическое число, по которому можно определить координату следующего центроида
   *
   * @param sum высчитанная сумма квадратов расстояний от центроида до каждой точки
   * @return магическое число
   */
  private double getRandomSum(double sum) {

    return rand.nextDouble() * sum;

  }

}
