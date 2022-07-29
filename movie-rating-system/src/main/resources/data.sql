INSERT INTO defaultdb.user_type (id,type) VALUES
(1,"admin"),
(2,"defaultUser"),
(3,"beginnerUser"),
(4,"advancedUser"),
(5,"gigaUser");


INSERT INTO defaultdb.user (name,surname,email,user_type_id) VALUES
('testName','testSurname',"testEmail",1),
('testName','testSurname',"testEmail",2),
('testName','testSurname',"testEmail",3),
('testName','testSurname',"testEmail",4),
('testName','testSurname',"testEmail",5);

