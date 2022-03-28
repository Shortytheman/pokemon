-- Slet bruger: DROP USER IF EXISTS


-- Lav bruger og giv rettigheder
create user pokemon_master@localhost identified by "pokemon";
select user, host from mysql.user;

GRANT SELECT, INSERT, UPDATE, DELETE
    ON pokedex.*
    TO pokemon_master@localhost;

-- Vis rettigheder for user
SHOW GRANTs FOR pokemon_master@localhost;