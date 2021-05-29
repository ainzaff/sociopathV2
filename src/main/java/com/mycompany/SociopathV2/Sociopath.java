/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.SociopathV2;

import java.io.*;
import java.lang.*;
import java.util.*;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Result;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

public class Sociopath {

    public static GraphDatabaseFactory dbFactory = new GraphDatabaseFactory();
    public static GraphDatabaseService graphDb = dbFactory.newEmbeddedDatabase(
            new File("data/students"));
    public static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        graphDb.beginTx();
        initializeStudents();
        mainMenu();
        //studentMenu();

    }

    public static void initializeStudents() {
        Node Alice = graphDb.createNode(Label.label("Student"));
        Node Bob = graphDb.createNode(Label.label("Student"));
        Node Charlie = graphDb.createNode(Label.label("Student"));
        Node Daniel = graphDb.createNode(Label.label("Student"));
        Node Ethan = graphDb.createNode(Label.label("Student"));
        Node Finn = graphDb.createNode(Label.label("Student"));
        Node Guy = graphDb.createNode(Label.label("Student"));
        Node Holly = graphDb.createNode(Label.label("Student"));
        Node Ian = graphDb.createNode(Label.label("Student"));
        Node Joe = graphDb.createNode(Label.label("Student"));

        Relationship AliceBob = Alice.createRelationshipTo(Bob, RelationshipType.withName("IS_FRIENDS_WITH"));
        AliceBob.setProperty("rep", 5);
        Relationship BobAlice = Bob.createRelationshipTo(Alice, RelationshipType.withName("IS_FRIENDS_WITH"));
        BobAlice.setProperty("rep", 8);

        Relationship AliceGuy = Alice.createRelationshipTo(Guy, RelationshipType.withName("IS_FRIENDS_WITH"));
        AliceGuy.setProperty("rep", 4);
        Relationship GuyAlice = Guy.createRelationshipTo(Alice, RelationshipType.withName("IS_FRIENDS_WITH"));
        GuyAlice.setProperty("rep", 3);

        Relationship BobFinn = Bob.createRelationshipTo(Finn, RelationshipType.withName("IS_FRIENDS_WITH"));
        BobFinn.setProperty("rep", 9);
        Relationship FinnBob = Finn.createRelationshipTo(Bob, RelationshipType.withName("IS_FRIENDS_WITH"));
        FinnBob.setProperty("rep", 7);

        Relationship BobCharlie = Bob.createRelationshipTo(Charlie, RelationshipType.withName("IS_FRIENDS_WITH"));
        BobCharlie.setProperty("rep", 5);
        Relationship CharlieBob = Charlie.createRelationshipTo(Bob, RelationshipType.withName("IS_FRIENDS_WITH"));
        CharlieBob.setProperty("rep", 4);

        Relationship BobEthan = Bob.createRelationshipTo(Ethan, RelationshipType.withName("IS_FRIENDS_WITH"));
        BobEthan.setProperty("rep", 6);
        Relationship EthanBob = Ethan.createRelationshipTo(Bob, RelationshipType.withName("IS_FRIENDS_WITH"));
        EthanBob.setProperty("rep", 2);

        Relationship DanielHolly = Daniel.createRelationshipTo(Holly, RelationshipType.withName("IS_FRIENDS_WITH"));
        DanielHolly.setProperty("rep", 7);
        Relationship HollyDaniel = Holly.createRelationshipTo(Daniel, RelationshipType.withName("IS_FRIENDS_WITH"));
        HollyDaniel.setProperty("rep", 10);

        Relationship DanielJoe = Daniel.createRelationshipTo(Joe, RelationshipType.withName("IS_FRIENDS_WITH"));
        DanielJoe.setProperty("rep", 7);
        Relationship JoeDaniel = Joe.createRelationshipTo(Daniel, RelationshipType.withName("IS_FRIENDS_WITH"));
        JoeDaniel.setProperty("rep", 7);

        Relationship JoeIan = Joe.createRelationshipTo(Ian, RelationshipType.withName("IS_FRIENDS_WITH"));
        JoeIan.setProperty("rep", 4);
        Relationship IanJoe = Ian.createRelationshipTo(Joe, RelationshipType.withName("IS_FRIENDS_WITH"));
        IanJoe.setProperty("rep", 3);

        Alice.setProperty("name", "ALICE");
        Bob.setProperty("name", "BOB");
        Charlie.setProperty("name", "CHARLIE");
        Daniel.setProperty("name", "DANIEL");
        Ethan.setProperty("name", "ETHAN");
        Finn.setProperty("name", "FINN");
        Guy.setProperty("name", "GUY");
        Holly.setProperty("name", "HOLLY");
        Ian.setProperty("name", "IAN");
        Joe.setProperty("name", "JOE");

        Alice.setProperty("dive", 92);
        Bob.setProperty("dive", 88);
        Charlie.setProperty("dive", 7);
        Daniel.setProperty("dive", 1);
        Ethan.setProperty("dive", 5);
        Finn.setProperty("dive", 10);
        Guy.setProperty("dive", 30);
        Holly.setProperty("dive", 50);
        Ian.setProperty("dive", 20);
        Joe.setProperty("dive", 70);

        Alice.setProperty("lunchStart", 1200);
        Bob.setProperty("lunchStart", 1300);
        Charlie.setProperty("lunchStart", 1330);
        Daniel.setProperty("lunchStart", 1120);
        Ethan.setProperty("lunchStart", 1100);
        Finn.setProperty("lunchStart", 1400);
        Guy.setProperty("lunchStart", 1240);
        Holly.setProperty("lunchStart", 1350);
        Ian.setProperty("lunchStart", 1110);
        Joe.setProperty("lunchStart", 1310);

        Alice.setProperty("lunchStart", 1200);
        Bob.setProperty("lunchStart", 1300);
        Charlie.setProperty("lunchStart", 1330);
        Daniel.setProperty("lunchStart", 1120);
        Ethan.setProperty("lunchStart", 1100);
        Finn.setProperty("lunchStart", 1400);
        Guy.setProperty("lunchStart", 1240);
        Holly.setProperty("lunchStart", 1350);
        Ian.setProperty("lunchStart", 1110);
        Joe.setProperty("lunchStart", 1310);

        Alice.setProperty("lunchPeriod", 30);
        Bob.setProperty("lunchPeriod", 40);
        Charlie.setProperty("lunchPeriod", 20);
        Daniel.setProperty("lunchPeriod", 10);
        Ethan.setProperty("lunchPeriod", 40);
        Finn.setProperty("lunchPeriod", 60);
        Guy.setProperty("lunchPeriod", 20);
        Holly.setProperty("lunchPeriod", 10);
        Ian.setProperty("lunchPeriod", 50);
        Joe.setProperty("lunchPeriod", 10);

    }

    public static void mainMenu() {
        int choice=0;
        while(choice !=1 || choice != 2 || choice != 3){
            System.out.println("\nWELCOME TO SOCIOPATH!");
            System.out.println("What do you want to do?\n");
            System.out.println("1. Check Students");
            System.out.println("2. Events");
            System.out.println("3. Exit\n");
            choice = input.nextInt();
            switch (choice) {
                case 1:
                    studentMenu();
                    break;
                case 2:
                    eventMenu();
                    break;
                case 3:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Please type in a valid number option");
            }
        }
    }

    public static void studentMenu() {
        System.out.println("\nDisplaying students . . .\n");
        displayStudents();
        System.out.println("\nWhich student's friends would you like to check?");
        System.out.println("Type exit to go back to main menu\n");
        input.nextLine();
        String name = input.nextLine();
        displayFriends(name);

    }

    public static void eventMenu() {
        int choice;
        System.out.println("\nWhich event happened? \n");
        System.out.println("1. Teaching a stranger to solve lab questions ");
        System.out.println("2. Chit-chat ");
        System.out.println("3. Your road to glory ");
        System.out.println("4. Arranging books ");
        System.out.println("5. Meet your crush ");
        System.out.println("6. Friendship \n");
        choice = input.nextInt();
        eventSelector(choice);
    }

    public static void displayStudents() {
        Result result = graphDb.execute(
                "MATCH (s:Student)"
                + "RETURN s.name as name,s.dive as diving_rate, s.lunchStart as lunch_starts_at,s.lunchPeriod as lunch_period");

        System.out.println(result.resultAsString());

    }

    public static void displayFriends(String name) {
        if (name.compareToIgnoreCase("exit") == 0) {
            mainMenu();
            return;
        }
        name = name.toUpperCase();
        Result result = graphDb.execute(
                "MATCH (s:Student) -[r:IS_FRIENDS_WITH]-> (other:Student) "
                + "WHERE  s.name= '" + name + "'"
                + "RETURN other.name as friend_name, r.rep as reputation_with");

        System.out.println(result.resultAsString());

        mainMenu();
    }

    public static void eventSelector(int choice) {
        switch (choice) {

            case 1: {
                eventOne();
                break;
            }
            case 2: {
                eventTwo();
                break;
            }
            case 3: {
                eventThree();
                break;
            }
            case 4: 
                eventFour();
                break;
            
            case 5: {
                eventFive();
                break;
            }
            case 6: {
                eventSix();
                break;
            }
            default: {
                System.out.println("Please type in a valid number option\nGoing back to main menu");
                mainMenu();
            }
        }
        //mainMenu();
    }

    public static void eventOne() {
        System.out.println("\nThis is a placeholder because the event has not currently been implemented yet <3\n");
    }

    public static void eventTwo() {
        System.out.println("\nThis is a placeholder because the event has not currently been implemented yet <3\n");
    }

    public static void eventThree() {
        System.out.println("\nThis is a placeholder because the event has not currently been implemented yet <3\n");
    }

    public static void eventFour() {
        int counter = 0;
        LinkedList<Integer> bookList = new LinkedList<>();
        System.out.println("Enter number of books: ");
        int numOfBooks = input.nextInt();
        input.nextLine();
        System.out.println("Enter books' heights: ");
        String bookHeightsInput = input.nextLine();
        //split space 
        String[] bookHeights = bookHeightsInput.split(" ");
        //push book height elements into a list while parse into integer
        for (int i = 0; i < numOfBooks; i++) {
            bookList.push(Integer.parseInt(bookHeights[i]));
        }
        for (int i = 0; i < bookList.size(); i++) {
            for (int j = 0; j < bookList.size(); j++) {
                if (j == bookList.size() - 1) {
                    break;
                } else if (bookList.get(j) > bookList.get(j + 1)) {
                    bookList.remove(j + 1);
                }
            }
            counter = i;
        }
        System.out.println("Rounds needed: " + counter);
    }

    public static void eventFive() {
        System.out.println("\nThis is a placeholder because the event has not currently been implemented yet <3\n");
    }

    public static void eventSix() {
        System.out.println("\nThis is a placeholder because the event has not currently been implemented yet <3\n");
    }
}
