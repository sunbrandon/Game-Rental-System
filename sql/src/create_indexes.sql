-- Brandon Sun
CREATE INDEX idx_users_role ON Users(role);

CREATE INDEX idx_catalog_genre ON Catalog(genre);
CREATE INDEX idx_catalog_price ON Catalog(price);

CREATE INDEX idx_rentalorder_login ON RentalOrder(login);
CREATE INDEX idx_rentalorder_timestamp ON RentalOrder(orderTimestamp);

CREATE INDEX idx_trackinginfo_rentalorder ON TrackingInfo(rentalOrderID);

CREATE INDEX idx_gamesinorder_rentalorder ON GamesInOrder(rentalOrderID);
CREATE INDEX idx_gamesinorder_gameid ON GamesInOrder(gameID);
