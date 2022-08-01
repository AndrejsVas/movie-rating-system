INSERT INTO defaultdb.user_type (id,type) VALUES
(1,"admin"),
(2,"defaultUser"),
(3,"beginnerUser"),
(4,"advancedUser"),
(5,"gigaUser");


INSERT INTO defaultdb.user (name,surname,email,user_type_id) VALUES
('Alexis','Merceris',"testEmail@cool.com",1),
('Gordon','Freedomman',"hf3@never.com",2),
('Big','Lebowski',"money@ishere.com",3),
('Adam','Sander',"click@magic.com",4),
('Britney','Pears',"notoxicity@again.oops",5);

INSERT INTO defaultdb.movie_type (type) VALUES
("horror"),("fantasy"),("sci-fi"),("action"),("comedy");

INSERT INTO defaultdb.movie (title,description,movie_type) VALUES
("CyborgNinja", "This is a test description lorem ipsum all that kind of stuff", 3),
("Lord Of The Rings", "This is a test description lorem ipsum all that kind of stuff", 2),
("Another Adam Sandler Movie", "This is a test description lorem ipsum all that kind of stuff", 5);

INSERT INTO defaultdb.review (review_date,score,text_review,movie_id,user_id) VALUES
('2010-03-21', 5 , 'Still better than Robocop',3, 1),
('2010-03-21', 10 , "N3ver fails to impress, another masterpiece",3, 4),
('2010-03-21', 1 , "Boooooring",2, 4),
('2010-03-21', 10 , "Best piece of cinematography",2, 1),
('2010-03-21', 10 , "...", 1, 2),
('2010-03-21', 1 , "...",3, 2),
('2010-03-21', 10 , "DUUUUDE , Best piece of cinematography ",2, 3),
('2010-03-21', 10 , "B3st p13c3 0f kin3m@tOgr@phy EVERRRR",2, 5);
