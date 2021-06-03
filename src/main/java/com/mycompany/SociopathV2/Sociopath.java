/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.SociopathV2;

import java.io.*;
import java.lang.*;
import java.util.*;
import org.neo4j.graphalgo.GraphAlgoFactory;
import org.neo4j.graphalgo.PathFinder;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Result;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.Path;
import org.neo4j.graphdb.PathExpander;
import org.neo4j.graphdb.PathExpanders;
import org.neo4j.graphdb.Direction;

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
        Node Alice = graphDb.createNode(Labels.STUDENT);
        Node Bob = graphDb.createNode(Labels.STUDENT);
        Node Charlie = graphDb.createNode(Labels.STUDENT);
        Node Daniel = graphDb.createNode(Labels.STUDENT);
        Node Ethan = graphDb.createNode(Labels.STUDENT);
        Node Finn = graphDb.createNode(Labels.STUDENT);
        Node Guy = graphDb.createNode(Labels.STUDENT);
        Node Holly = graphDb.createNode(Labels.STUDENT);
        Node Ian = graphDb.createNode(Labels.STUDENT);
        Node Joe = graphDb.createNode(Labels.STUDENT);

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

        Alice.setProperty("prog", 92);
        Bob.setProperty("prog", 88);
        Charlie.setProperty("prog", 10);
        Daniel.setProperty("prog", 40);
        Ethan.setProperty("prog", 55);
        Finn.setProperty("prog", 10);
        Guy.setProperty("prog", 30);
        Holly.setProperty("prog", 60);
        Ian.setProperty("prog", 70);
        Joe.setProperty("prog", 30);

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

        friendTo("Alice", "Bob", 5);
        friendTo("Bob", "Alice", 8);

        friendTo("Alice", "Guy", 4);
        friendTo("Guy", "Alice", 3);

        friendTo("Bob", "Finn", 9);
        friendTo("Finn", "Bob", 7);

        friendTo("Bob", "Charlie", 5);
        friendTo("Charlie", "Bob", 4);

        friendTo("Bob", "Ethan", 6);
        friendTo("Ethan", "Bob", 2);

        friendTo("Daniel", "Holly", 7);
        friendTo("Holly", "Daniel", 10);

        friendTo("Daniel", "Joe", 7);
        friendTo("Joe", "Daniel", 7);

        friendTo("Joe", "Ian", 4);
        friendTo("Ian", "Joe", 3);
        
    }

    public static void mainMenu() {
        int choice = 0;
        while (choice != 1 || choice != 2 || choice != 3) {
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
        System.out.println("\nWhich student's profile would you like to check?");
        System.out.println("Type exit to go back to main menu\n");
        input.nextLine();
        String name = input.nextLine();
        displayProfile(name);

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
                "MATCH (s:STUDENT)"
                + "RETURN s.name as name,s.dive as diving_rate, s.lunchStart as lunch_starts_at,s.lunchPeriod as lunch_period");

        System.out.println(result.resultAsString());

    }

    public static void displayProfile(String name) {
        if (name.compareToIgnoreCase("exit") == 0) {
            mainMenu();
            return;
        }
        name = name.toUpperCase();
        Result result = graphDb.execute(
                "MATCH (s:STUDENT) -[r]-> (other:STUDENT) "
                + "WHERE  s.name= '" + name + "'"
                + "RETURN type(r)as relationship,other.name as name, r.rep as reputation_with");

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
        input.nextLine();
        System.out.println("\nWho was helping the stranger with their lab questions?");
        String helper = input.nextLine().toUpperCase();
        System.out.println("\nWho was the stranger?");
        String helped = input.nextLine().toUpperCase();
        if (helper.equals(helped)) {
            System.out.println("\n Now you're not even making sense . . .");
            return;
        }
        Node helpernode = getNode(helper);
        Node helpednode = getNode(helped);
        if (!isFriendsWith(helper, helped)) {
            if ((Integer) helpernode.getProperty("prog") >= 50) {
                System.out.println("\nAhh, I see. " + helper + " is quite good at programming . . .");
                System.out.println(helper + " and " + helped + " have now become friends!");
                System.out.println(helper + "'s reputation in the eyes of " + helped + " has increased by 10!");
                Relationship lab = friendTo(helper, helped, 10);
                lab.setProperty("goodLab", "true");
                friendTo(helped, helper, 0);
            }
            if ((Integer) helpernode.getProperty("prog") < 50) {
                System.out.println("\n" + helper + " isn't very good at programming, so " + helper + " probably could not have finished those questions . . .");
                System.out.println(helper + " and " + helped + " have now become friends!");
                System.out.println(helper + "'s reputation in the eyes of " + helped + " has increased by 2!");
                Relationship lab = friendTo(helper, helped, 2);
                lab.setProperty("goodLab", "false");
                friendTo(helped, helper, 0);
            }
        } else {
            System.out.println("\nThey are not strangers. Are you lying to yourself?");
        }

    }

    public static void eventTwo() {
        input.nextLine();
        System.out.println("\nWho was the first person chit-chatting?");
        String person1 = input.nextLine().toUpperCase();
        System.out.println("\nWho was the second person chit-chatting?");
        String person2 = input.nextLine().toUpperCase();
        if (person1.equals(person2)) {
            System.out.println("\n Now you're not even making sense . . .");
            return;
        }
        if (!isFriendsWith(person1, person2)) {
            System.out.println("\nImpossible. They do not know each other!");
            return;
        }
        if (isFriendsWith(person1, person2)) {
            System.out.println("Which student were they talking about?");
            String talked = input.nextLine().toUpperCase();
            if (talked.equals(person2) || talked.equals(person1)) {
                System.out.println("\n Now you're not even making sense . . .");
                return;
            }
            if (!isFriendsWith(person1, talked)) {
                System.out.println("\nThat's a lie! " + person1 + " and " + talked + " haven't even met!");
                return;
            }
            Node talkednode = getNode(talked);
            Node person1node = getNode(person1);
            Iterable<Relationship> relationships = talkednode.getRelationships(Direction.OUTGOING, Rels.IS_FRIENDS_WITH);
            for (Relationship relay : relationships) {
                if (!relay.hasProperty("goodLab")) {
                    continue;
                }
                if (relay.getProperty("goodLab").equals("true")) {
                    System.out.println("\nLuckily, " + talked + " did a good job helping " + person1 + " with the lab questions . . .");
                    if (isFriendsWith(person2, talked)) {
                        incrementRep(getRelationship(person2, talked, Rels.IS_FRIENDS_WITH), (int) Math.floor(0.5 * (int) relay.getProperty("rep")));
                        System.out.println(person2 + " is already friends with " + talked + "\nThis incident has caused " + talked + "'s reputation from " + person2 + "'s perspective to increase by " + (int) Math.floor(0.5 * (int) relay.getProperty("rep")) + "!");
                        return;
                    }
                    knowsOf(person2, talked, (int) Math.floor(0.5 * (int) relay.getProperty("rep")));
                    System.out.println(person2 + " now knows of " + talked + " !");
                    System.out.println(talked + "'s reputation in the eyes (or in this case, ears) of " + person2 + " has increased by " + (int) Math.floor(0.5 * (int) relay.getProperty("rep")) + "!");
                    return;
                }
                if (relay.getProperty("goodLab").equals("false")) {
                    System.out.println("\n" + talked + " must have messed up when helping " + person1 + " with the lab questions . . .");
                    if (isFriendsWith(person2, talked)) {
                        incrementRep(getRelationship(person2, talked, Rels.IS_FRIENDS_WITH), (int) Math.floor(-1 * (int) relay.getProperty("rep")));
                        System.out.println(person2 + " is already friends with " + talked + "\nThis incident has caused " + talked + "'s reputation from " + person2 + "'s perspective to decrease by " + (int) Math.floor(1 * (int) relay.getProperty("rep")) + "!");
                        return;
                    }
                    knowsOf(person2, talked, (int) Math.floor(-1 * (int) relay.getProperty("rep")));
                    System.out.println(person2 + " now knows of " + talked + " !");
                    System.out.println(talked + "'s reputation in the eyes (or in this case, ears) of " + person2 + " has decreased by " + (int) Math.floor(-1 * (int) relay.getProperty("rep")) + "!");
                    return;
                }
            }
        }
    }

    public static void eventThree() {
        input.nextLine();
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
        mainMenu();
    }

    public static void eventFive() {
        //Randomizer
        String[] name = {"ALICE", "BOB", "CHARLIE", "DANIEL", "ETHAN", "FINN", "GUY", "HOLLY", "IAN", "JOE"};
        Random r = new Random();
        String rumor = name[r.nextInt(10)];
        String crush = name[r.nextInt(10)];
        while (crush == rumor) {
            rumor = name[r.nextInt(10)];
        }

        //Text
        System.out.println("You have a crush who is " + crush + " but there is someone who has a rumour about you and he/she is " + rumor);
        System.out.println("");

        //Initialize source and target nodes
        Node src = graphDb.findNode(Labels.STUDENT, "name", rumor);
        Node target = graphDb.findNode(Labels.STUDENT, "name", crush);

        //Traverse and return the path
        Iterable<Path> all = allPaths(src, target);
        try {
            ArrayList<Path> pathlist = new ArrayList<>();
            for (Path curr : all) {
                pathlist.add(curr);
            }

            //To collect the nodes in the path
            ArrayList<ArrayList<Node>> list = new ArrayList<>();
            int index = 0;
            for (Path curr : pathlist) {
                list.add(new ArrayList<Node>());
                Iterable<Node> nodeslist = curr.nodes();
                for (Node node : nodeslist) {
                        list.get(index).add(node);
                    }
                index++;
            }
            
            //Filter duplicate paths
            list = removeDuplicates(list);

            //Output
            if(list.size()==0) {
                System.out.println("Luckily, it is impossible for the rumour to reach your crush");
                return;
            }
            System.out.println("The following is the path from the rumour to your crush(left to right):");
            for (int i = 0; i < list.size(); i++) {
                for (int j = 0; j < list.get(i).size(); j++) {
                    if(j==list.get(i).size()-1) System.out.print(list.get(i).get(j).getProperty("name"));
                    else System.out.print(list.get(i).get(j).getProperty("name") + ">");
                }
                System.out.println("");
            }
            System.out.println("\n");
        } catch (NoSuchElementException ex) {
            System.out.println("Luckily, it is impossible for the rumour to reach your crush");
        }
    }

    public static void eventSix() {
        System.out.println("\nThis is a placeholder because the event has not currently been implemented yet <3\n");
        // How to check for duplicate nodes?

        System.out.println("How many friendships do you want to examine?");
        int n = input.nextInt();
//        for(int i = 1; i <= n; i++) {
//            System.out.println("Friendship #" + i + " (enter TWO integers)");
//            int node1 = input.nextInt();
//            int node2 = input.nextInt();
//            // checks for duplicate inputs
//            while (node1 == node2) {
//                node2 = input.nextInt();
//            }

        // For testing purposes. Manually initialized three nodes.
        Node nodeOne = graphDb.createNode(Labels.STUDENT);
        Node nodeTwo = graphDb.createNode(Labels.STUDENT);
        Node nodeThree = graphDb.createNode(Labels.STUDENT);

        Relationship oneTwo = nodeOne.createRelationshipTo(nodeTwo, RelationshipType.withName("IS_FRIENDS_WITH"));
        Relationship twoThree = nodeOne.createRelationshipTo(nodeTwo, RelationshipType.withName("IS_FRIENDS_WITH"));
        Relationship oneThree = nodeOne.createRelationshipTo(nodeTwo, RelationshipType.withName("IS_FRIENDS_WITH"));

    }

    private static enum Rels implements RelationshipType {
        IS_FRIENDS_WITH,
        KNOWS_OF;

    }

    public static enum Labels implements Label {
        STUDENT;
    }

    //Method for expanding path during traversal
    public static Iterable<Path> allPaths(Node src, Node target) {
        PathExpander expander = PathExpanders.forType(Rels.IS_FRIENDS_WITH);
        //PathFinder<Path> allPath = GraphAlgoFactory.allPaths(expander, 10);
        PathFinder<Path> allPath = GraphAlgoFactory.allSimplePaths(expander,50);
        return allPath.findAllPaths(src, target);
    }

    public static boolean isFriendsWith(String s1, String s2) {
        Node src = graphDb.findNode(Labels.STUDENT, "name", s1.toUpperCase());
        Node target = graphDb.findNode(Labels.STUDENT, "name", s2.toUpperCase());
        Iterable<Relationship> relationships = src.getRelationships(Direction.OUTGOING, Rels.IS_FRIENDS_WITH);
        for (Relationship relationship : relationships) {
            if (relationship.getEndNode().equals(target)) {
                return true;
            }
        }

        return false;
    }

    public static Relationship friendTo(String name1, String name2, int repTo) {
        Node s1 = graphDb.findNode(Labels.STUDENT, "name", name1.toUpperCase());
        Node s2 = graphDb.findNode(Labels.STUDENT, "name", name2.toUpperCase());
        Relationship relationship = s1.createRelationshipTo(s2, Rels.IS_FRIENDS_WITH);
        relationship.setProperty("rep", repTo);
        return relationship;
    }

    public static Node getNode(String name) {
        return graphDb.findNode(Labels.STUDENT, "name", name.toUpperCase());
    }

    public static Relationship knowsOf(String name1, String name2, int repTo) {
        Node s1 = graphDb.findNode(Labels.STUDENT, "name", name1.toUpperCase());
        Node s2 = graphDb.findNode(Labels.STUDENT, "name", name2.toUpperCase());
        Relationship relationship = s1.createRelationshipTo(s2, Rels.KNOWS_OF);
        relationship.setProperty("rep", repTo);
        return relationship;

    }

    public static Relationship getRelationship(String s1, String s2, RelationshipType rt) {
        Node src = graphDb.findNode(Labels.STUDENT, "name", s1.toUpperCase());
        Node target = graphDb.findNode(Labels.STUDENT, "name", s2.toUpperCase());
        Relationship relay;
        Iterable<Relationship> relationships = src.getRelationships(Direction.OUTGOING, rt);
        for (Relationship relationship : relationships) {
            if (relationship.getEndNode().equals(target)) {
                relay = relationship;
                return relay;
            }
        }
        return null;
    }

    public static void incrementRep(Relationship r, int change) {
        r.setProperty("rep", (int) r.getProperty("rep") + change);
    }
    
    //Remove duplicate elements in an arraylist method for Event 5
    public static <T> ArrayList<T> removeDuplicates(ArrayList<T> list) {
        Set<T> set = new LinkedHashSet<>();
        set.addAll(list);
        list.clear();
        list.addAll(set);
        return list;
    }
}
