# Game Rental System

## Languages / Frameworks used

* PostgreSQL (PSQL)
* Java
* Bash

## Purpose / Goal

* To create a database management system for a Game Rental Store using PSQL, executable using bash, and interactable user interface with Java
* Complete with schema for users, catalog, rental orders, tracking information, and game rentals.

### Installation

* Mac OS X
* Ubuntu
* Windows (if applicable)
* PostgreSQL
* Text Editor (VS Code, emacs, etc.)

## Usage / Complete Functionalities

* **Create User**: Ask the user for details of a new user (login, password, role, phone number) and add it to the database. If the user already exists, prompt the user to re-enter the details.
* **Log In**: Check the login credentials provided by the user against the database and return the user login if the credentials are valid.
* **View Profile**: Display the profile details (login, role, favorite games, phone number, number of overdue games) of the logged-in user or another user if the logged-in user is a manager.
* **Update Profile**: Ask the user to provide new details for their profile (password, phone number). If the user leaves a prompt blank, the current information is retained.
* **View Catalog**: Display the catalog of games. Allow the user to filter the catalog by genre and/or maximum price.
* **Place Rental Order**: Allow the logged-in user to place a rental order for a specified number of games. Check if the entered game IDs exist and prompt the user to re-enter invalid game IDs. Calculate the total price and update the rental order in the database.
* **View Full Rental Order History**: Display the full rental order history for the logged-in user. If the logged-in user is a manager, they can view the order history for any user.
* **View Past 5 Rental Orders**: Display the most recent 5 rental orders for the logged-in user. If the logged-in user is a manager, they can view the recent orders for any user.
* **View Rental Order Information**: Display detailed information about a specific rental order, including the order details and the games included in the order.
* **View Tracking Information**: Display the tracking information for a specific rental order, including the tracking ID, status, current location, courier name, last update date, and additional comments.
* **Update Tracking Information**: Allow an employee or manager to update the tracking information for a specific rental order. If the user leaves a prompt blank, the current information is retained.
* **Update Catalog**: Allow a manager to update the catalog details for a specific game (game name, genre, price, description, image URL). If the user leaves a prompt blank, the current information is retained.
* **Update User**: Allow a manager to update the details (role, phone number, number of overdue games) of any user. If the user leaves a prompt blank, the current information is retained.
* **Physical DB Design**: Use indexes to improve database performance for frequent queries and join operations.
* **Physical DB Design** (DB performance tuning indexes)
* **Edge Case and Exception Handling**

## Explanation of Indexes and Stored Procedures

### Indexes

* **idx_users_role**: Speeds up queries filtering or sorting by user roles.
* **idx_catalog_genre**: Improves performance of queries filtering or sorting by game genres.
* **idx_catalog_price**: Enhances query performance for filtering or sorting by game prices.
* **idx_rentalorder_login**: Accelerates queries filtering or joining by user logins in rental orders.
* **idx_rentalorder_timestamp**: Speeds up queries filtering or sorting rental orders by timestamps.
* **idx_trackinginfo_rentalorder**: Improves performance of queries filtering or joining by rental order IDs in tracking information.
* **idx_gamesinorder_rentalorder**: Enhances the speed of queries filtering or joining by rental order IDs in games in orders.
* **idx_gamesinorder_gameid**: Improves query performance for filtering or joining by game IDs in games in orders.

### Stored Procedures

* **update_overdue_games**: Automatically updates the number of overdue games for a user when a rental order's due date passes.
* **log_user_update**: Logs changes to user information in a separate table for auditing purposes.
* **ensure_unique_game_names**: Ensures that each game name in the catalog is unique and raises an exception if a duplicate game name is detected.


