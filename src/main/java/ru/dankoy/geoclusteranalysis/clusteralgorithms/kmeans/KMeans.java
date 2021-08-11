package ru.dankoy.geoclusteranalysis.clusteralgorithms.kmeans;

import ru.dankoy.geoclusteranalysis.cluster.Cluster;
import ru.dankoy.geoclusteranalysis.core.model.Crash;

public interface KMeans {

  // Радиус земли в метрах
  double EARTH_RADIUS = 6372.8 * 1000;

  /**
   * Считает дистанцию между двумя точками на земле. Так как земля - не сфера, то используется
   * формула haversine. http://rosettacode.org/wiki/Haversine_formula#Java
   *
   * @param lat1 широта первой точки
   * @param lon1 долгота первой точки
   * @param lat2 широта второй точки
   * @param lon2 долгота второй точки
   * @return дистанция между двумя точками на земле
   */
  default double haversineDistance(double lat1, double lon1, double lat2, double lon2) {
    double dLat = Math.toRadians(lat2 - lat1);
    double dLon = Math.toRadians(lon2 - lon1);
    lat1 = Math.toRadians(lat1);
    lat2 = Math.toRadians(lat2);

    double a =
        Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math
            .cos(lat1) * Math.cos(lat2);
    double c = 2 * Math.asin(Math.sqrt(a));
    return EARTH_RADIUS * c;
  }

  /**
   * Метод обертка над методом {@link KMeansImpl#haversineDistance(double, double, double, double)}
   *
   * @param crash    объект аварии {@link Crash}
   * @param centroid кластер с центром которого происходит расчет расстояния
   * @return дистанция между двумя точками на земле
   */
  default double haversineDistance(Crash crash, Cluster centroid) {
    return haversineDistance(crash.getLatitude(), crash.getLongitude(), centroid.getLatitude(),
        centroid.getLongitude());
  }

}
