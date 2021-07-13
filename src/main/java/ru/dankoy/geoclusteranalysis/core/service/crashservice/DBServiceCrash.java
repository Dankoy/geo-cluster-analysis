package ru.dankoy.geoclusteranalysis.core.service.crashservice;

import java.util.List;
import ru.dankoy.geoclusteranalysis.core.model.Crash;

public interface DBServiceCrash {

    List<Crash> getAllCrashes();


    List<Crash> getCrashesWithNonMotorists();

    List<Crash> getCrashesWithNonMotoristsInMapBounds(double north, double south, double west, double east);

    List<Crash> getAllCrashesInMapBounds(double north, double south, double west, double east);
}
