package ru.dankoy.geoclusteranalysis.core.dao;

import java.util.List;
import ru.dankoy.geoclusteranalysis.core.model.Crash;
import ru.dankoy.geoclusteranalysis.core.sessionmanager.SessionManager;

public interface CrashDao {

    List<Crash> getAllCrashes();

    List<Crash> getAllCrashesWithNonMotorists();

    List<Crash> getCrashesWithNonMotoristsInMapBounds(double north, double south, double west, double east);

    List<Crash> getAllCrashesInMapBounds(double north, double south, double west, double east);

    SessionManager getSessionManager();
}
