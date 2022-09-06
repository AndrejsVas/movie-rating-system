INSERT INTO defaultdb.user_type (id,type) VALUES
(1,"admin"),
(2,"defaultUser"),
(3,"beginnerUser"),
(4,"advancedUser"),
(5,"gigaUser");


INSERT INTO defaultdb.users (name,surname,email,user_type_id) VALUES
('Alexis','Merceris',"testEmail@cool.com",1),
('Gordon','Freedomman',"hf3@never.com",2),
('Big','Lebowski',"money@ishere.com",3),
('Adam','Sander',"click@magic.com",4),
('Britain','Pears',"notoxicity@again.oops",5),
('Pressiks','Tojason',"find@shawn.com",5);

INSERT INTO defaultdb.movie_type (type) VALUES
("horror"),("fantasy"),("sci-fi"),("action"),("comedy");

INSERT INTO defaultdb.movie (title,description,movie_type) VALUES
("CyborgNinja", "This is a test description lorem ipsum all that kind of stuff", 3),
("Lord Of The Rings", "This is a test description lorem ipsum all that kind of stuff", 2),
("Another Adam Sandler Movie", "This is a test description lorem ipsum all that kind of stuff", 5),
("Game of sinke", "This is a test description lorem ipsum all that kind of stuff", 3),
("House of sinke", "This is a test description lorem ipsum all that kind of stuff", 2),
("Americans", "This is a test description lorem ipsum all that kind of stuff", 5),
("Creator", "This is a test description lorem ipsum all that kind of stuff", 3),
("Disturbed", "This is a test description lorem ipsum all that kind of stuff", 2),
("down with the sickness", "This is a test description lorem ipsum all that kind of stuff", 5),
("LP tribute", "This is a test description lorem ipsum all that kind of stuff", 3),
("Fantasy movie 2", "This is a test description lorem ipsum all that kind of stuff", 2),
("Fantasy movie 3", "This is a test description lorem ipsum all that kind of stuff", 5);

INSERT INTO defaultdb.review (review_date,score,text_review,movie_id,user_id) VALUES
('2010-03-21', 5 , 'Still better than Robocop',3, 1),
('2010-03-21', 10 , "N3ver fails to impress, another masterpiece",3, 4),
('2010-03-21', 1 , "Boooooring",2, 4),
('2010-03-21', 10 , "Best piece of cinematography",2, 1),
('2010-03-21', 10 , "...", 1, 2),
('2010-03-21', 1 , "...",3, 2),
('2010-03-21', 10 , "DUUUUDE , Best piece of cinematography ",2, 3),
('2010-03-21', 10 , "B3st p13c3 0f kin3m@tOgr@phy EVERRRR",2, 5);

INSERT INTO defaultdb.user_likes (review_id, user_id) VALUES
(1,1),(1,2),(1,3),(1,4),(1,5),(1,6),
(2,1),(2,3),(2,5),(2,6),
(3,2),(3,4),(3,6),
(4,1),
(5,1),(5,6),
(6,4),(6,3),
(8,5);

INSERT INTO defaultdb.movie_likes (movie_id, user_id) VALUES
(1,1),(1,2),(1,3),(1,4),(1,5),(1,6),
(2,1),(2,3),(2,5),(2,6),
(3,1),(3,2),(3,3),(3,4),(3,5),(3,6),
(4,1),(4,3),(4,5),(4,6),
(5,5),(5,2),(5,3),(5,4),(5,5),(5,6),
(6,1),
(7,1),
(8,2),
(9,3),
(10,1),(10,3);