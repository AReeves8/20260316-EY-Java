/* SELECT * gives you all fields */
SELECT * FROM album;

/* can specify specific fields to query */
SELECT title, artist_id FROM album;

/* WHERE keyword to filter results */
SELECT * FROM album WHERE artist_id = 1;

/* comparison operators: =, <> (!= works as well), <, >, <=, >= */
SELECT * FROM track WHERE unit_price >= 0.99;
SELECT * FROM track WHERE unit_price < 0.99;

/* logical operators with AND, OR, NOT */
SELECT * FROM track WHERE unit_price >= 0.99 AND milliseconds > 100000;

/* 
	search text with LIKE 
		- wildcards: 
			% - match any number of characters
			_ - matches exactly one character
*/
SELECT * FROM artist WHERE name LIKE 'A%';	

/* IN, NOT IN - search through lists */
SELECT * FROM track WHERE genre_id IN (SELECT genre_id FROM genre WHERE name IN ('Rock', 'Jazz', 'Pop'));

/* LIMIT keyword to only return the first n results */
SELECT * FROM album WHERE artist_id = 21;			/* 4 records */
SELECT * FROM album WHERE artist_id = 21 LIMIT 3;	/* 3 records returned*/

/* pagination w/ LIMIT and OFFSET */
SELECT * FROM album LIMIT 10 OFFSET 30;

/* organize reults w/ ORDER BY */
SELECT * FROM album ORDER BY artist_id ASC;		/* ASC - smallest to largest, DESC - largest to smallest */




/*

	AGGREGATE FUNCTIONS
		- return information about the data, rather than the data itself
		
*/

/* returns just a number of the amount of tracks you have */
SELECT COUNT(*) FROM track;

/* returns the number of tracks with a value in the 'composer' column */
SELECT COUNT(composer) FROM track;

/* use AS to create aliases for data */
SELECT
	COUNT(*) AS total_tracks,
	AVG(milliseconds) AS average_track_length,
	MAX(milliseconds) AS longest_track_length,
	MIN(milliseconds) AS shortest_track_length
FROM 
	track;

/* finding the avarge track length of each album that has at least 3 tracks, sorted longest averge to shortest */
SELECT
	album_id,
	AVG(milliseconds) AS average_track_length	/* aggregating based on the group when used with GROUP BY */
FROM track
GROUP BY album_id
HAVING COUNT(*) > 3
ORDER BY average_track_length DESC;




/**

	JOINS
		- combine data from multiple RELATED tables

		- INNER JOIN: when a value is present in both tables
			- grabs only data that is in both tables
			- default for "JOIN"
		- LEFT and RIGHT JOIN: when a value is present in either the LEFT or RIGHT table
			- grab ALL data from only the LEFT or RIGHT table
		- FULL JOIN
			- grab ALL data from BOTH tables

**/

SELECT 
	a.title AS album_title, 
	ar.name AS artist_name
FROM album a
INNER JOIN artist ar 
ON a.artist_id = ar.artist_id;


SELECT 
	t.name AS track_name,
	t.milliseconds AS track_length,
	a.title AS album_title, 
	ar.name AS artist_name
FROM track t
JOIN album a 
	ON a.album_id = t.album_id
JOIN artist ar 
	ON a.artist_id = ar.artist_id
LIMIT 50;


/* modify to include album name, artist name */
SELECT
	track.album_id,
	album.title, 
	artist.name,
	AVG(milliseconds) AS average_track_length
FROM track
JOIN album
	ON track.album_id = album.album_id
JOIN artist 
	ON artist.artist_id = album.artist_id
GROUP BY track.album_id, album.title, artist.name
HAVING COUNT(*) > 3
ORDER BY average_track_length DESC;


SELECT
	album.title, 
	artist.name,
	AVG(milliseconds) AS average_track_length
FROM track
JOIN album
	ON track.album_id = album.album_id
JOIN artist 
	ON artist.artist_id = album.artist_id
GROUP BY artist.name, album.title
HAVING COUNT(*) > 3
ORDER BY average_track_length DESC;











