/*events
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.SociopathV2;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Path;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.ResourceIterable;
import org.neo4j.graphdb.ResourceIterator;
import org.neo4j.graphdb.Result;

/**
 *
 * @author lenovo
 */
public class Events {
public static Scanner input = new Scanner(System.in);
    public static void eventThree() {
        Sociopath.input.nextLine();
        System.out.println("\nWho is going to use this unrivaled algorithm to obtain maximum reputation by having lunch?");
        String user = input.nextLine().toUpperCase();
        Node usernode = DataManipulation.getNode(user);
        System.out.println("\nVery well, " + user + " , proceed with caution.");
        LunchMethods.nextDay();
        System.out.println("Through meticulous calculation , Sociopath has decided who to have lunch with to gain the most rep\n");
        System.out.println("Day : "+(LunchMethods.getDayCounter()-1));
        //set averages first before getting them
        ResourceIterator<Node> list = DataManipulation.getAllNodes();
        while (list.hasNext()) {
            Node s = list.next();
            LunchMethods.setNewAvgLunchStart(s);
            LunchMethods.setNewAvgLunchEnd(s);
        }
        Result result = Sociopath.graphDb.execute("MATCH (s:STUDENT)" + "RETURN s.name as name, s.avgLunchStart as average_lunch_start,s.avgLunchEnd as average_lunch_end");
        System.out.println(result.resultAsString());
        System.out.println("\nUser : " + user);
        System.out.println("User's average lunchStart : " + LunchMethods.getAvgLunchStart(usernode));
        System.out.println("User's average lunchEnd : " + LunchMethods.getAvgLunchEnd(usernode));
        System.out.println("\n"+user+" should have lunch with the following :\n");
        LunchMethods.lunchAlgo(user, LunchMethods.getAvgLunchStart(usernode), LunchMethods.getAvgLunchEnd(usernode));
    }

    public static void eventFour() {
        int counter = 0;
        LinkedList<Integer> bookList = new LinkedList<>();
        System.out.println("\nOn a lazy Sunday afternoon, you decide to volunteer at the library. The librarian, Jabbar, has asked you to arrange some books.");
        System.out.println("Enter number of books: ");
        int numOfBooks = Sociopath.input.nextInt();
        Sociopath.input.nextLine();
        System.out.println("Enter books' heights: ");
        String bookHeightsInput = Sociopath.input.nextLine();
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
        Menus.mainMenu();
    }

    public static void eventTwo() {
        Sociopath.input.nextLine();
        System.out.println("\nWho was the first person chit-chatting?");
        String person1 = Sociopath.input.nextLine().toUpperCase();
        System.out.println("\nWho was the second person chit-chatting?");
        String person2 = Sociopath.input.nextLine().toUpperCase();
        if (person1.equals(person2)) {
            System.out.println("\n Now you're not even making sense . . .");
            return;
        }
        if (!DataManipulation.isFriendsWith(person1, person2)) {
            System.out.println("\nImpossible. They do not know each other!");
            return;
        }
        if (DataManipulation.isFriendsWith(person1, person2)) {
            System.out.println("Which student were they talking about?");
            String talked = Sociopath.input.nextLine().toUpperCase();
            if (talked.equals(person2) || talked.equals(person1)) {
                System.out.println("\n Now you're not even making sense . . .");
                return;
            }
            if (!DataManipulation.isFriendsWith(person1, talked)) {
                System.out.println("\nThat's a lie! " + person1 + " and " + talked + " haven't even met!");
                return;
            }
            Node talkednode = DataManipulation.getNode(talked);
            Node person1node = DataManipulation.getNode(person1);
            Iterable<Relationship> relationships = talkednode.getRelationships(Direction.OUTGOING, Sociopath.Rels.IS_FRIENDS_WITH);
            for (Relationship relay : relationships) {
                if (!relay.hasProperty("goodLab")) {
                    continue;
                }
                if (relay.getProperty("goodLab").equals("true")) {
                    System.out.println("\nLuckily, " + talked + " did a good job helping " + person1 + " with the lab questions . . .");
                    if (DataManipulation.isFriendsWith(person2, talked)) {
                        DataManipulation.incrementRep(DataManipulation.getRelationship(person2, talked, Sociopath.Rels.IS_FRIENDS_WITH), (int) Math.floor(0.5 * (int) relay.getProperty("rep")));
                        System.out.println(person2 + " is already friends with " + talked + "\nThis incident has caused " + talked + "'s reputation from " + person2 + "'s perspective to increase by " + (int) Math.floor(0.5 * (int) relay.getProperty("rep")) + "!");
                        return;
                    }
                    DataManipulation.knowsOf(person2, talked, (int) Math.floor(0.5 * (int) relay.getProperty("rep")));
                    System.out.println(person2 + " now knows of " + talked + " !");
                    System.out.println(talked + "'s reputation in the eyes (or in this case, ears) of " + person2 + " has increased by " + (int) Math.floor(0.5 * (int) relay.getProperty("rep")) + "!");
                    return;
                }
                if (relay.getProperty("goodLab").equals("false")) {
                    System.out.println("\n" + talked + " must have messed up when helping " + person1 + " with the lab questions . . .");
                    if (DataManipulation.isFriendsWith(person2, talked)) {
                        DataManipulation.incrementRep(DataManipulation.getRelationship(person2, talked, Sociopath.Rels.IS_FRIENDS_WITH), (int) Math.floor(-1 * (int) relay.getProperty("rep")));
                        System.out.println(person2 + " is already friends with " + talked + "\nThis incident has caused " + talked + "'s reputation from " + person2 + "'s perspective to decrease by " + (int) Math.floor(1 * (int) relay.getProperty("rep")) + "!");
                        return;
                    }
                    DataManipulation.knowsOf(person2, talked, (int) Math.floor(-1 * (int) relay.getProperty("rep")));
                    System.out.println(person2 + " now knows of " + talked + " !");
                    System.out.println(talked + "'s reputation in the eyes (or in this case, ears) of " + person2 + " has decreased by " + (int) Math.floor(-1 * (int) relay.getProperty("rep")) + "!");
                    return;
                }
            }
        }
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
        Node src = Sociopath.graphDb.findNode(Sociopath.Labels.STUDENT, "name", rumor);
        Node target = Sociopath.graphDb.findNode(Sociopath.Labels.STUDENT, "name", crush);
        //Traverse and return the path
        Iterable<Path> all = DataManipulation.getAllPaths(src, target);
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
            list = DataManipulation.removeDuplicates(list);
            //Output
            if (list.size() == 0) {
                System.out.println("Luckily, it is impossible for the rumour to reach your crush");
                return;
            }
            System.out.println("The following is the path from the rumour to your crush(left to right):");
            for (int i = 0; i < list.size(); i++) {
                for (int j = 0; j < list.get(i).size(); j++) {
                    if (j == list.get(i).size() - 1) {
                        System.out.print(list.get(i).get(j).getProperty("name"));
                    } else {
                        System.out.print(list.get(i).get(j).getProperty("name") + ">");
                    }
                }
                System.out.println("");
            }
            System.out.println("\n");
        } catch (NoSuchElementException ex) {
            System.out.println("Luckily, it is impossible for the rumour to reach your crush");
        }
    }

    public static void eventOne() {
        Sociopath.input.nextLine();
        System.out.println("\nWho was helping the stranger with their lab questions?");
        String helper = Sociopath.input.nextLine().toUpperCase();
        System.out.println("\nWho was the stranger?");
        String helped = Sociopath.input.nextLine().toUpperCase();
        if (helper.equals(helped)) {
            System.out.println("\n Now you're not even making sense . . .");
            return;
        }
        Node helpernode = DataManipulation.getNode(helper);
        Node helpednode = DataManipulation.getNode(helped);
        if (!DataManipulation.isFriendsWith(helper, helped)) {
            if ((Integer) helpernode.getProperty("prog") >= 50) {
                System.out.println("\nAhh, I see. " + helper + " is quite good at programming . . .");
                System.out.println(helper + " and " + helped + " have now become friends!");
                System.out.println(helper + "'s reputation in the eyes of " + helped + " has increased by 10!");
                Relationship lab = DataManipulation.friendTo(helper, helped, 10);
                lab.setProperty("goodLab", "true");
                DataManipulation.friendTo(helped, helper, 0);
            }
            if ((Integer) helpernode.getProperty("prog") < 50) {
                System.out.println("\n" + helper + " isn't very good at programming, so " + helper + " probably could not have finished those questions . . .");
                System.out.println(helper + " and " + helped + " have now become friends!");
                System.out.println(helper + "'s reputation in the eyes of " + helped + " has increased by 2!");
                Relationship lab = DataManipulation.friendTo(helper, helped, 2);
                lab.setProperty("goodLab", "false");
                DataManipulation.friendTo(helped, helper, 0);
            }
        } else {
            System.out.println("\nThey are not strangers. Are you lying to yourself?");
        }
    }

    public static void eventSix() {
        System.out.println("\nThis is a placeholder because the event has not currently been implemented yet <3\n");
        // How to check for duplicate nodes?
        System.out.println("TEMP: How many friendships do you want to examine?\n");
//        int n = Sociopath.input.nextInt();
        //        for(int i = 1; i <= n; i++) {
        //            System.out.println("Friendship #" + i + " (enter TWO integers)");
        //            int node1 = input.nextInt();
        //            int node2 = input.nextInt();
        //            // checks for duplicate inputs
        //            while (node1 == node2) {
        //                node2 = input.nextInt();
        //            }
        // For testing purposes. Manually initialized three nodes.
        Node nodeOne = Sociopath.graphDb.createNode(Sociopath.Labels.STUDENT);
        Node nodeTwo = Sociopath.graphDb.createNode(Sociopath.Labels.STUDENT);
        Node nodeThree = Sociopath.graphDb.createNode(Sociopath.Labels.STUDENT);

//        nodeOne.setProperty("name", "nodeOne");
//        nodeTwo.setProperty("name", "nodeTwo");

        // ISSUE: Error "s1" is null in friendTo()
        // REASON: Because s1 is not an existing node in the original initialized Students graph
        // SOLUTION: Overloaded friendTo() method in DataManipulation
        DataManipulation.friendTo("nodeOne", "nodeTwo");
        DataManipulation.friendTo("nodeTwo", "nodeThree");
//        DataManipulation.friendTo("nodeOne", "nodeThree");


        // ISSUE: Cannot retrieve paths
//        Iterable<Path> path1 = DataManipulation.getAllPaths(nodeOne, nodeTwo);
//        Iterable<Path> path2 = DataManipulation.getAllPaths(nodeTwo, nodeThree);
        Iterable<Path> path3 = DataManipulation.getAllPaths(nodeOne, nodeThree);


        // Testing path1
        try {
            // Adds all paths in path1 -> pathList
            ArrayList<Path> pathList = new ArrayList<>();
            for (Path path : path3) {
                pathList.add(path);
            }

            // Adds all nodesList in pathList -> nodesListsList
            ArrayList<ArrayList<Node>> nodesListsList = new ArrayList<>();
            int index = 0;
            for (Path path : pathList) {
                nodesListsList.add(new ArrayList<Node>());
                Iterable<Node> nodesList = path.nodes();
            }

            // Removes duplicate nodesList from nodesListsList
            nodesListsList = DataManipulation.removeDuplicates(nodesListsList);


            // Output
            // Traverses through nodesListsList and displays the nodes in each paths
            int numFriendships = nodesListsList.size();
            System.out.println("You can form " + numFriendships + " friendship(s):");
            for (int i = 1; i <= numFriendships; i++) {
                System.out.println(i + ". " + nodesListsList.get(i).toString());
            }
            return;

        } catch (NoSuchElementException ex) {
            System.out.println("No paths available!");
        }

    }

    public static void eventSelector(int choice) {
        switch (choice) {
            case 1: {
                Events.eventOne();
                break;
            }
            case 2: {
                Events.eventTwo();
                break;
            }
            case 3: {
                Events.eventThree();
                break;
            }
            case 4:
                Events.eventFour();
                break;
            case 5: {
                Events.eventFive();
                break;
            }
            case 6: {
                Events.eventSix();
                break;
            }
            case 7: {
                Events.eventSeven();

                break;
            }
            default: {
                System.out.println("Please type in a valid number option\nGoing back to main menu");
                Menus.mainMenu();
            }
        }
        //mainMenu();
    }
    
    public static void eventSeven(){
        System.out.println("Select event :");
        System.out.println("1. Bully");
        System.out.println("2. Love Proposal");
        int choice = input.nextInt();
        switch(choice){
            case 1:{
                Bully();
                break;
            }
            case 2: {
                loveProposal();
                break;
            }
            default:{System.out.println("Please type in a valid number option. Returning to main menu . . .");
            Menus.mainMenu();
                
            }
        }
    }
    
    public static void Bully(){
        input.nextLine();
        System.out.println("Who was the bully?");
        String bully = input.nextLine();
        Node bullynode = DataManipulation.getNode(bully);
        System.out.println("Which person was being bullied?");
        String bullied = input.nextLine();
        System.out.println("The small and weak "+ bullied+" was preyed upon by the big bad bully , "+bully+"!" );
        DataManipulation.bullies(bully, bullied, -1);
        System.out.println(bullied + " now hates "+ bully);
        DataManipulation.hates(bullied, bully, -1);
        System.out.println("\nThe voices of "+ bullied+" echoed through the school halls.");
        System.out.println("Everyone now knows that "+bully+ " is bullying "+ bullied+"\n");
        Iterable<Relationship> list = bullynode.getRelationships();
        for (Relationship r : list){
            DataManipulation.incrementRep(r, -1);
        }

        System.out.println("-1 Reputation for every relationship that "+ bully +" has");
    }
    
    public static void loveProposal(){
        input.nextLine();
        System.out.println("\nYou were walking home from school when you saw two shady figures on your way out.");
        System.out.println("Using your ultimate stealth, you creep behind them unnoticed to hear that one of the shady figures had confessed their love to the other!");
        System.out.println("Creeping closer, you realize who those two shady figures were. . .");
        System.out.println("\nWho was the one confessing their love?");
        String lover = input.nextLine().toUpperCase();
        Node lovernode = DataManipulation.getNode(lover);
        System.out.println("\nWho was the one being confessed to?");
        String loved = input.nextLine().toUpperCase();
        if (lover.equals(loved)){
            System.out.println("\n Now you're not even making sense . . .");
            return;
        }
        if (DataManipulation.isLoversWith(loved, lover)){
            System.out.println("\nThey are already lovers. \nReturning to main menu . . .");
            return;
        }
        
        
        System.out.println("\nWell, stop being dramatic! Did they accept the proposal? (YES/NO)");
        String choice = input.nextLine();
        if (choice.equalsIgnoreCase("yes")){
            //if already have a crush
            if (DataManipulation.isLoversWith(lover, loved)){
                                DataManipulation.incrementRep(DataManipulation.getRelationship(lover, loved, Sociopath.Rels.LOVES), 10);
            }
           if (!DataManipulation.isLoversWith(lover, loved)){
                                DataManipulation.loves(lover,loved,20);
            }
            DataManipulation.loves(loved, lover, 20);
            System.out.println("\n"+lover+ " and "+ loved +" are now lovers!\n");
            return;
        }
        if (choice.equalsIgnoreCase("no")){
            System.out.println("\nThat's too bad. . .");
            if (DataManipulation.isLoversWith(lover, loved)){
                DataManipulation.incrementRep(DataManipulation.getRelationship(lover, loved, Sociopath.Rels.LOVES), 1);
                System.out.println("Through every rejection, "+ lover+"'s crush on "+loved+" grows biggers and bigger!");
            }
            DataManipulation.loves(lover, loved, 10);
            System.out.println(lover+" still has a crush on "+ loved+", but "+ loved+" does not feel the same way . . .");
            return;
        }
        System.out.println("I don't wanna waste anymore time with you. Try putting in the correct input next time. Returning to main menu . . .");
        
        
    }
    
    
    
}
