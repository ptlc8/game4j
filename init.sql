create table history
(
	date DATE, 
	game TEXT,
	id INT PRIMARY KEY NOT NULL AUTO_INCREMENT
);
create table saves
(
	date DATE, 
	game TEXT,
	name VARCHAR(20) KEY NOT NULL 
);