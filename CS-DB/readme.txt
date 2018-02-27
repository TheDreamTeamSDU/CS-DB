Getting started with JDBC

1)
Go to elephantsql.com, sign up and make a database 
(you made also use another database provider or host your own, but this guide is based on elephantsql).

2)
The program will asume that you have a table called test3 with two colums. This could be created by
inputting the following in the elephantsql browser

create table test3(
id int,
val int
);

insert into test3 values
(1,2),
(2,3),
(2,4)


2)
Put the following files in the same directory on your machine:

DBTest.java
postgresql-42.2.1.jar

The first goal is to get the file DBTest.java to connect to your database, send a query and print the response.


3)
The lines:

        String url = "jdbc:postgresql://horton.elephantsql.com:5432/jpjkxtkp";
        String username = "jpjkxtkp";
        String password = "xxxx";

in DBTest.java should be replaced with your own information. 
You can find that info under 'details' on your ElephantSQL.
The syntax is:
jdbc:postgresql://<server>:<port>/<database>

<server> should be replaced by the server you find on the details page, for example horton.elephantsql.com
<port> should be the port you see in the url on your details page. It is probably 5432.
<database> is whatever is written under 'User & Default database'.

You should now compile your java file. You should be able to run it from the terminal with

Linux (and probably also mac):
java -cp postgresql-42.2.1.jar:. DBTest

Windows:
java -cp postgresql-42.2.1.jar;. DBTest

If you are not running from the terminal, but instead using an editor to do so,
you need to load the driver postgresql-42.2.1.jar.


If everything went well, the program should output the content of table 3.


4) Play with the program! Try making your program insert data, delete data or even delete tables.


5) We now want to play with injection attacks. Put the file Injection.java where you placed the other files.

The program Injection.java assumes the existance of a table 'datastore' with three columns.
To make this table, you can copy the statements from injection_data.sql into your elephantsql browser and run it.


Now run the program Injection.java
This program allows a user to input a username and a password. If they match an entry in the database,
the data of that user will be revealed. Try with username hans and password kanelgiffel



6) Now we should try to compromise the security of the program. See if by inputting something clever
in username or password, you can reveal the data of hans without knowing his password.
If you need a hint, check out https://xkcd.com/327/

7) Now, see if you can reveal the whole data of the table, without knowing the names of all the users.

Note: Exercise 6 and 7 concern what is known as an Injection Attack and shows a possition weakness
in sql applications. You should NOT try and use this technique to compromise other databases than your own
as doing so is illegal.

8) Good job, you have found a security hole in the application. Now it is time to fix it.
Use google to find out how we can avoid sql injection attacks in java and fix the program Injection.java
to patch this weakness.
