package ru.dankoy.geoclusteranalysis.flyway;

public interface MigrationsExecutor {

    void cleanDb();

    void executeMigrations();

}
