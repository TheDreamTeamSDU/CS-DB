package JDBC;

import java.util.Scanner;

/*
	Run with:
	java -cp postgresql-42.2.1.jar:. DBTest
 */
public class CSDB {

    private Scanner scanner;
    private int inputValue;
    private int option4Value;

    public CSDB() {
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
        System.out.println("Press \"4\" to see info about participating teams in a tournament");
    }

    /**
     * takes input from the user and selects the option method equal to the
     * input number
     */
    public void inputFromPlayer() {
        scanner = new Scanner(System.in);
        System.out.println("Input a number and press enter (or -1 to quit)");
        if (scanner.hasNextInt()) {
            inputValue = scanner.nextInt();
            switch (inputValue) {
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
                case -1:
                    wantToQuit();
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
        System.out.println("option1");
        //Insert SQL query here
        wantToQuit();
    }

    /**
     * method to handle the query for the second option
     */
    public void option2() {
        System.out.println("option2");
        //Insert SQL query here
        wantToQuit();
    }

    /**
     * method to handle the query for the third option
     */
    public void option3() {
        System.out.println("option3");
        //Insert SQL query here
        wantToQuit();
    }

    /**
     * method to handle the query for the fourth option
     */
    public void option4() {
        System.out.println("option4");
        scanner = new Scanner(System.in);
        System.out.println("Input a number and press enter");
        if (scanner.hasNextInt()) {
            option4Value = scanner.nextInt();
            //Insert SQL query here with option4Value

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

        String url = "jdbc:postgresql://horton.elephantsql.com:5432/dluumnvl";
        String username = "dluumnvl";
        String password = "QmaWW2yvG8QXAgH6o2WdsbzCkIxLfkBk";
    }

    public void wantToQuit() {
        scanner = new Scanner(System.in);
        System.out.println("Do you wish to quit?");
        System.out.println("press \"y\" to quit or \"n\" to make a new selection");
        if (scanner.hasNext("y") || scanner.hasNext("n")) {
            String quit = scanner.next();
            switch (quit) {
                case "y":
                    System.exit(0);
                    break;
                case "n":
                    newInput();
                    break;
                case "Y":
                    System.exit(0);
                    break;
                case "N":
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

//        try {
//            Connection db = DriverManager.getConnection(url, username, password);
//
//            Statement st = db.createStatement();
//            ResultSet rs = st.executeQuery("select * from test3");
//            while (rs.next()) {
//
//                System.out.print(rs.getString(1) + " ");
//                System.out.println(rs.getString(2) + " ");
//            }
//            rs.close();
//            st.close();
//
//
//        } catch (Exception e) {
//            System.out.println(e);
//        }
    }

}
