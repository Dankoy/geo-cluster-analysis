package ru.dankoy.geoclusteranalysis.core;

import java.util.List;
import org.hibernate.SessionFactory;
import ru.dankoy.geoclusteranalysis.cluster.Cluster;
import ru.dankoy.geoclusteranalysis.clusteralgorithms.kmeans.KMeans;
import ru.dankoy.geoclusteranalysis.clusteralgorithms.kmeans.KMeansImpl;
import ru.dankoy.geoclusteranalysis.clusteralgorithms.kmeans.KMeansPlusPlusImpl;
import ru.dankoy.geoclusteranalysis.core.hibernate.dao.CrashDaoHibernateImpl;
import ru.dankoy.geoclusteranalysis.core.hibernate.sessionmanager.SessionManagerHibernate;
import ru.dankoy.geoclusteranalysis.core.hibernate.utils.HibernateUtils;
import ru.dankoy.geoclusteranalysis.core.model.Crash;
import ru.dankoy.geoclusteranalysis.core.service.crashservice.DBServiceCrashImpl;

public class Main {

    public static final int MAX_INACTIVE_INTERVAL = 10;
    private static final int WEB_SERVER_PORT = 8080;
    private static final String TEMPLATES_DIR = "/templates/";
    private static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";
    private static final String HASH_LOGIN_SERVICE_CONFIG_NAME = "realm.properties";
    private static final String REALM_NAME = "admin";

    public static void main(String[] args) throws Exception {

        SessionManagerHibernate sessionManagerHibernate = getSessionManager();

        var crashDaoHibernate = new CrashDaoHibernateImpl(sessionManagerHibernate);
        var dbServiceCrash = new DBServiceCrashImpl(crashDaoHibernate);

//        sessionManagerHibernate.beginSession();

//        System.out.println(dbServiceCrash.getAllCrashes().size());
//        System.out.println(dbServiceCrash.getCrashesWithNonMotorists().size());

//        sessionManagerHibernate.commitSession();

        List<Crash> crashes = dbServiceCrash.getCrashesWithNonMotorists();

//        List<Crash> crashes = dbServiceCrash
//                .getCrashesWithNonMotoristsInMapBounds(39.348624061555235, 39.0, -77.59848089307916,
//                        -76.65502996534478);
        System.out.println(crashes.size());

        KMeans kMeans = new KMeansPlusPlusImpl();
        List<Cluster> clusters = kMeans.cluster(crashes, 20);


//        System.out.println(clusters);
        clusters.forEach(cluster -> {
//            System.out.println(cluster.getPoints());
//            System.out.println(cluster.getLatitude());
//            System.out.println(cluster.getLongitude());

            cluster.getPoints().forEach(point -> {
//                System.out.println(point.getLatitude());
//                System.out.println(point.getLongitude());
                System.out.println(point.getGeolocation());
            });


        });

    }

    /**
     * Получение менеджера сессий hibernate
     *
     * @return
     */
    private static SessionManagerHibernate getSessionManager() {
        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory(HIBERNATE_CFG_FILE, Crash.class);
        return new SessionManagerHibernate(sessionFactory);
    }

}
