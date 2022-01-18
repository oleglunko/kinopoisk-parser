CREATE TABLE IF NOT EXISTS film
(
    id           BIGINT PRIMARY KEY,
    name         VARCHAR NOT NULL,
    position     INT     NOT NULL,
    release_year INT     NOT NULL,
    rating       NUMERIC NOT NULL,
    votes_count  INT     NOT NULL
);

