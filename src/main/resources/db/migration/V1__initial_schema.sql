create database accidents;

create table crashes
(
    report_number                VARCHAR(100),
    local_case_number            VARCHAR(255),
    agency_name                  VARCHAR(255),
    acrs_report_type             VARCHAR(255),
    crash_date_time              DATE,
    hit_run                      VARCHAR(5),
    route_type                   VARCHAR(255),
    mile_point                   DOUBLE PRECISION,
    mile_point_direction         VARCHAR(10),
    lane_direction               VARCHAR(10),
    lane_number                  INTEGER,
    lane_type                    VARCHAR(255),
    number_of_lanes              INTEGER,
    direction                    VARCHAR(10),
    distance                     DOUBLE PRECISION,
    distance_unit                VARCHAR(10),
    road_grade                   VARCHAR(255),
    nontraffic                   VARCHAR(5),
    road_name                    VARCHAR(255),
    cross_street_type            VARCHAR(100),
    cross_street_name            VARCHAR(255),
    off_road_description         VARCHAR(255),
    municipality                 VARCHAR(100),
    related_non_motorist         VARCHAR(50),
    at_fault                     VARCHAR(20),
    collision_type               VARCHAR(100),
    weather                      VARCHAR(30),
    surface_condition            VARCHAR(50),
    light                        VARCHAR(30),
    traffic_control              VARCHAR(50),
    driver_substance_abuse       VARCHAR(150),
    non_motorist_substance_abuse VARCHAR(150),
    first_harmful_event          VARCHAR(100),
    second_harmful_event         VARCHAR(100),
    fixed_object_struck          VARCHAR(100),
    junction                     VARCHAR(50),
    intersection_type            VARCHAR(50),
    intersection_area            VARCHAR(50),
    road_alignment               VARCHAR(30),
    road_condition               VARCHAR(30),
    road_division                VARCHAR(150),
    latitude                     DOUBLE PRECISION,
    longitude                    DOUBLE PRECISION,
    geolocation                  VARCHAR(150)
)


