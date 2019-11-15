CREATE DATABASE Health;
GO 
use Health;

CREATE TABLE workout (
	weekNumber int NOT NULL PRIMARY KEY,
	totalHours int NOT NULL,
)