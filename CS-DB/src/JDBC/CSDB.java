package JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Scanner;

/*
	Run with:
	java -cp postgresql-42.2.1.jar:. DBTest
 */
public class CSDB {

    private Scanner scanner;
    private int inputValue;
    private int option4Value;
    private static String url;
    private static String username;
    private static String password;

    public CSDB() {
        url = "jdbc:postgresql://horton.elephantsql.com:5432/dluumnvl";
        username = "dluumnvl";
        password = "QmaWW2yvG8QXAgH6o2WdsbzCkIxLfkBk";
        initSQLDB();
        printWelcome();
    }

    public void printWelcome() {
        System.out.println("Welcome to the Counter Strike tournament Database");
        printOptions();
    }

    /**
     * prints the options
     */
    public void printOptions() {
        System.out.println("Press \"1\" to see a all names of coaches and their team");
        System.out.println("Press \"2\" to see the names of all people who are on a team, which has won at least one tournament");
        System.out.println("Press \"3\" to see all names of teams and the number of players on that team");
        System.out.println("Press \"4\" to obtain tournaments with x amount of participating teams");
    }

    /**
     * takes input from the user and selects the option method equal to the
     * input number
     */
    public void inputFromPlayer() {
        scanner = new Scanner(System.in);
        System.out.println("Input a number and press enter (or 0 to quit)");
        if (scanner.hasNextInt()) {
            inputValue = scanner.nextInt();
            switch (inputValue) {
                case 0:
                    wantToQuit();
                    break;
                case 1:
                    option1();
                    break;
                case 2:
                    option2();
                    break;
                case 3:
                    option3();
                    break;
                case 4:
                    option4();
                    break;
                default:
                    wrongInput();
            }
        } else {
            wrongInput();
        }

    }

    /**
     * method to handle the query for the first option
     */
    public void option1() {
        System.out.println("\nCoaches and their team:");
        //Insert SQL query here
        try {
            Connection db = DriverManager.getConnection(url, username, password);

            java.sql.Statement st = db.createStatement();
            ResultSet rs = st.executeQuery("SELECT People.name, Coaches.teamName\n"
                    + "FROM Coaches\n"
                    + "INNER JOIN People ON Coaches.coachName = People.nickname;");
            System.out.println(String.format("%-20s %-20s", "Name", "Team name"));
            System.out.println("-----------------------------------------");
            while (rs.next()) {
                System.out.println(String.format("%-20s %-20s", rs.getString(1), rs.getString(2)));
            }
            System.out.println("-----------------------------------------");
            rs.close();
            st.close();
            db.close();

        } catch (Exception e) {
            System.out.println(e);
        }
        wantToQuit();
    }

    /**
     * method to handle the query for the second option
     */
    public void option2() {
        System.out.println("\nNames of winners:");
        //Insert SQL query here
        try {
            Connection db = DriverManager.getConnection(url, username, password);

            java.sql.Statement st = db.createStatement();
            ResultSet rs = st.executeQuery("SELECT name\n"
                    + "FROM People\n"
                    + "WHERE nickname IN (SELECT playerName\n"
                    + "                   FROM PlaysFor\n"
                    + "                   WHERE teamName IN (SELECT teamName\n"
                    + "                                      FROM TournamentWinners))\n"
                    + "OR  nickname IN (SELECT coachName\n"
                    + "                 FROM coaches\n"
                    + "                 WHERE teamName IN (SELECT teamName\n"
                    + "                                    FROM TournamentWinners))");
            System.out.println(String.format("%-20s", "Name"));
            System.out.println("-----------------------------------------");
            while (rs.next()) {
                System.out.println(String.format("%-20s", rs.getString(1)));
            }
            System.out.println("-----------------------------------------");
            rs.close();
            st.close();
            db.close();

        } catch (Exception e) {
            System.out.println(e);
        }
        wantToQuit();
    }

    /**
     * method to handle the query for the third option
     */
    public void option3() {
        System.out.println("\nTeamnames and amount of players");
        //Insert SQL query here
        try {
            Connection db = DriverManager.getConnection(url, username, password);

            java.sql.Statement st = db.createStatement();
            ResultSet rs = st.executeQuery("SELECT teamName, COUNT(playerName)\n"
                    + "FROM PlaysFor\n"
                    + "GROUP BY teamName");
            System.out.println(String.format("%-20s %-20s", "Name", "Amount"));
            System.out.println("-----------------------------------------");
            while (rs.next()) {
                System.out.println(String.format("%-20s %-20s", rs.getString(1), rs.getString(2)));
            }
            System.out.println("-----------------------------------------");
            rs.close();
            st.close();
            db.close();

        } catch (Exception e) {
            System.out.println(e);
        }
        wantToQuit();
    }

    /**
     * method to handle the query for the fourth option
     */
    public void option4() {
        System.out.println("\nTurnaments with x amount of teams");
        System.out.println("Enter a number of teams");
        scanner = new Scanner(System.in);
        System.out.println("Input a number and press enter");
        if (scanner.hasNextInt()) {
            option4Value = scanner.nextInt();
            System.out.println("Your input: " + option4Value);
            try {
                Connection db = DriverManager.getConnection(url, username, password);

                java.sql.Statement st = db.createStatement();
                ResultSet rs = st.executeQuery("SELECT tournamentName\n"
                        + "FROM PlaysIn\n"
                        + "GROUP BY tournamentName\n" +
                "HAVING COUNT(teamName) = "+option4Value);
                System.out.println(String.format("%-20s %-20s", "Name", "Amount"));
                System.out.println("-----------------------------------------");
                while (rs.next()) {
                    System.out.println(String.format("%-20s", rs.getString(1)));
                }
                System.out.println("-----------------------------------------");
                rs.close();
                st.close();
                db.close();

            } catch (Exception e) {
                System.out.println(e);
            }
        } else {
            System.out.println("Wrong input. Tray again");
            option4();
        }
        wantToQuit();
    }

    public void wrongInput() {
        System.out.println("Wrong input. Try again");
        newInput();
    }

    public void initSQLDB() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (java.lang.ClassNotFoundException e) {
            System.out.println(e);
        }
    }

    public void wantToQuit() {
        scanner = new Scanner(System.in);
        System.out.println("\nDo you wish to quit?");
        System.out.println("press \"y\" to quit or \"n\" to make a new selection");
        if (scanner.hasNext()) {
            String quit = scanner.next();
            switch (quit.toLowerCase()) {
                case "y":
                    System.exit(0);
                    break;
                case "n":
                    newInput();
                    break;
                default:
                    System.out.println("wrong input");
                    wantToQuit();
            }
        } else {
            wantToQuit();
        }
    }

    public void newInput() {
        printOptions();
        inputFromPlayer();
    }

    public static void main(String[] args) {
        CSDB csdb = new CSDB();
        csdb.inputFromPlayer();

    }

}
