-- Brandon Sun

CREATE OR REPLACE LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS trg_update_overdue_games ON RentalOrder;
DROP TRIGGER IF EXISTS trg_log_user_update ON Users;
DROP TRIGGER IF EXISTS trg_ensure_unique_game_names ON Catalog;

-- procedure updates number of overdue games in users table based on overdue rental orders
CREATE OR REPLACE FUNCTION update_overdue_games() 
RETURNS TRIGGER AS 
$$
BEGIN
    IF NEW.dueDate < CURRENT_TIMESTAMP THEN
        UPDATE Users
        SET numOverDueGames = numOverDueGames + 1
        WHERE login = NEW.login;
    END IF;
    RETURN NEW;
END;
$$ 
LANGUAGE plpgsql;

-- trigger calls update_overdue_games procedure after an insert or update on rental orders
CREATE TRIGGER trg_update_overdue_games
AFTER INSERT OR UPDATE ON RentalOrder
FOR EACH ROW
EXECUTE PROCEDURE update_overdue_games();

-- procedure logs updates on user table
CREATE OR REPLACE FUNCTION log_user_update() 
RETURNS TRIGGER AS 
$$
BEGIN
    INSERT INTO UserUpdateLogs (login, old_password, new_password, old_phoneNum, new_phoneNum, update_time)
    VALUES (OLD.login, OLD.password, NEW.password, OLD.phoneNum, NEW.phoneNum, CURRENT_TIMESTAMP);
    RETURN NEW;
END;
$$ 
LANGUAGE plpgsql;

-- new table created to store user update logs
CREATE TABLE IF NOT EXISTS UserUpdateLogs (
    id SERIAL PRIMARY KEY,
    login VARCHAR(50),
    old_password VARCHAR(30),
    new_password VARCHAR(30),
    old_phoneNum VARCHAR(20),
    new_phoneNum VARCHAR(20),
    update_time TIMESTAMP
);

-- if there is an update on user trigger calls update user logs
CREATE TRIGGER trg_log_user_update
AFTER UPDATE ON Users
FOR EACH ROW
EXECUTE PROCEDURE log_user_update();

-- procedure makes sure there are unique game names in catalog table
CREATE OR REPLACE FUNCTION ensure_unique_game_names() 
RETURNS TRIGGER AS $$
BEGIN
    IF (SELECT COUNT(*) FROM Catalog WHERE gameName = NEW.gameName) > 0 THEN
        RAISE EXCEPTION 'A game with this name already exists in the catalog.';
    END IF;
    RETURN NEW;
END;
$$ 
LANGUAGE plpgsql;

-- trigger calls procedure to ensure unique game names in catalog
CREATE TRIGGER trg_ensure_unique_game_names
BEFORE INSERT ON Catalog
FOR EACH ROW
EXECUTE PROCEDURE ensure_unique_game_names();
