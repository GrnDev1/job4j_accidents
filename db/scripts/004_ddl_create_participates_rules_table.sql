CREATE TABLE participates_rules
(
    id          SERIAL PRIMARY KEY,
    accident_id int not null REFERENCES accidents (id),
    rule_id     int not null REFERENCES rules (id),
    UNIQUE (accident_id, rule_id)
);