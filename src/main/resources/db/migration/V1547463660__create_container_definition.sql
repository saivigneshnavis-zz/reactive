CREATE TABLE container (
uuid CHARACTER VARYING (60) PRIMARY KEY,
container_name CHARACTER VARYING (12),
visit_state CHARACTER VARYING (10),
freight_kind CHARACTER VARYING (4),
category CHARACTER VARYING (13),
gross_weight INTEGER
);
