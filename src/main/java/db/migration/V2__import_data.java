package db.migration;


import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.math.NumberUtils;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import java.io.FileReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class V2__import_data extends BaseJavaMigration {

    private static final DateTimeFormatter dateformat = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm:ss a");

    @Override
    public void migrate(Context context) throws Exception {

        JdbcTemplate jdbcTemplate =
                new JdbcTemplate(new SingleConnectionDataSource(context.getConnection(), true));

        FileReader dataFile = new FileReader(getClass().getClassLoader().getResource("crashes.csv").getPath());

        Iterable<CSVRecord> csvRecords = CSVFormat.EXCEL.withHeader().parse(dataFile);

        for (CSVRecord record : csvRecords) {

            String reportNumber = record.get("Report Number");
            String localCaseNumber = record.get("Local Case Number");
            String agencyName = record.get("Agency Name");
            String acsr = record.get("ACRS Report Type");
            LocalDateTime crashDate = LocalDateTime.parse(record.get("Crash Date/Time"), dateformat);
            String hitRun = record.get("Hit/Run");
            String routeType = record.get("Route Type");
            double milePoint = record.get("Mile Point").isEmpty() || record.get("Mile Point").isBlank() ?
                    0D : NumberUtils.createDouble(record.get("Mile Point"));
            String milePointDirection = record.get("Mile Point Direction");
            String laneDirection = record.get("Lane Direction");
            int laneNumber = record.get("Lane Number").isEmpty() || record.get("Lane Number").isBlank() ?
                    0 : NumberUtils.createInteger(record.get("Lane Number"));
            String laneType = record.get("Lane Type");
            int numberOfLanes = record.get("Number of Lanes").isEmpty() || record.get("Number of Lanes").isBlank() ?
                    0 : NumberUtils.createInteger(record.get("Number of Lanes"));
            String direction = record.get("Direction");
            double distance = record.get("Distance").isBlank() || record.get("Distance").isEmpty() ?
                    0D : NumberUtils.createDouble(record.get("Distance"));
            String distanceUnit = record.get("Distance Unit");
            String roadGrade = record.get("Road Grade");
            String nonTraffic = record.get("NonTraffic");
            String roadName = record.get("Road Name");
            String crossStreetType = record.get("Cross-Street Type");
            String crossStreetName = record.get("Cross-Street Name");
            String offRoadDescription = record.get("Off-Road Description");
            String municipality = record.get("Municipality");
            String relatedNonMotorist = record.get("Related Non-Motorist");
            String atFault = record.get("At Fault");
            String collisionType = record.get("Collision Type");
            String weather = record.get("Weather");
            String surfaceCondition = record.get("Surface Condition");
            String light = record.get("Light");
            String trafficControl = record.get("Traffic Control");
            String driverSubstanceAbuse = record.get("Driver Substance Abuse");
            String nonMotoristSubstanceAbuse = record.get("Non-Motorist Substance Abuse");
            String firstHarmfulEvent = record.get("First Harmful Event");
            String secondHarmfulEvent = record.get("Second Harmful Event");
            String fixedOjectStruck = record.get("Fixed Oject Struck");
            String junction = record.get("Junction");
            String intersectionType = record.get("Intersection Type");
            String intersectionArea = record.get("Intersection Area");
            String roadAlignment = record.get("Road Alignment");
            String roadCondition = record.get("Road Condition");
            String roadDivision = record.get("Road Division");
            double latitude = record.get("Latitude").isEmpty() || record.get("Latitude").isBlank() ?
                    0D : NumberUtils.createDouble(record.get("Latitude"));
            double longitude = record.get("Longitude").isEmpty() || record.get("Longitude").isBlank() ?
                    0D : NumberUtils.createDouble(record.get("Longitude"));
            String location = record.get("Location");

            jdbcTemplate.update(
                    "insert into crashes(report_number," +
                            "                    local_case_number," +
                            "                    agency_name," +
                            "                    acrs_report_type," +
                            "                    crash_date_time," +
                            "                    hit_run," +
                            "                    route_type," +
                            "                    mile_point," +
                            "            mile_point_direction," +
                            "                    lane_direction," +
                            "                    lane_number," +
                            "                    lane_type," +
                            "                    number_of_lanes," +
                            "                    direction," +
                            "                    distance," +
                            "            distance_unit," +
                            "                    road_grade," +
                            "                    nontraffic," +
                            "                    road_name," +
                            "                    cross_street_type," +
                            "                    cross_street_name," +
                            "                    off_road_description," +
                            "                    municipality," +
                            "                    related_non_motorist," +
                            "                    at_fault," +
                            "                    collision_type," +
                            "                    weather," +
                            "                    surface_condition," +
                            "                    light," +
                            "                    traffic_control," +
                            "                    driver_substance_abuse," +
                            "                    non_motorist_substance_abuse," +
                            "                    first_harmful_event," +
                            "                    second_harmful_event," +
                            "                    fixed_object_struck," +
                            "                    junction," +
                            "                    intersection_type," +
                            "                    intersection_area," +
                            "                    road_alignment," +
                            "                    road_condition," +
                            "                    road_division," +
                            "                    latitude," +
                            "            longitude," +
                            "                    geolocation) " +
                            "values (?,?,?,?,?,?,?,?,?,?," +
                            "?,?,?,?,?,?,?,?,?,?," +
                            "?,?,?,?,?,?,?,?,?,?," +
                            "?,?,?,?,?,?,?,?,?,?," +
                            "?,?,?,?)",
                    reportNumber,
                    localCaseNumber,
                    agencyName,
                    acsr,
                    crashDate,
                    hitRun,
                    routeType,
                    milePoint,
                    milePointDirection,
                    laneDirection,
                    laneNumber,
                    laneType,
                    numberOfLanes,
                    direction,
                    distance,
                    distanceUnit,
                    roadGrade,
                    nonTraffic,
                    roadName,
                    crossStreetType,
                    crossStreetName,
                    offRoadDescription,
                    municipality,
                    relatedNonMotorist,
                    atFault,
                    collisionType,
                    weather,
                    surfaceCondition,
                    light,
                    trafficControl,
                    driverSubstanceAbuse,
                    nonMotoristSubstanceAbuse,
                    firstHarmfulEvent,
                    secondHarmfulEvent,
                    fixedOjectStruck,
                    junction,
                    intersectionType,
                    intersectionArea,
                    roadAlignment,
                    roadCondition,
                    roadDivision,
                    latitude,
                    longitude,
                    location
            );

        }

    }
}