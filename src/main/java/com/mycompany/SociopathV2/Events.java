/*events
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.SociopathV2;

import java.util.*;

import org.neo4j.graphdb.*;
import org.neo4j.unsafe.impl.batchimport.input.Input;

/**
 * @author lenovo
 */
public class Events {

    public static void eventOne() {
        Scanner input = new Scanner(System.in);
        try {
            System.out.println("\nWho was helping the stranger with their lab questions?");
            String helper = input.nextLine().toUpperCase();
            System.out.println("\nWho was the stranger?");
            String helped = input.nextLine().toUpperCase();
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
                    Relationship lab = DataManipulation.friendTo(helped, helper, 10);
                    DataManipulation.friendTo(helper, helped, 0);
                    lab.setProperty("goodLab", "true");
                }
                if ((Integer) helpernode.getProperty("prog") < 50) {
                    System.out.println("\n" + helper + " isn't very good at programming, so " + helper + " probably could not have finished those questions . . .");
                    System.out.println(helper + " and " + helped + " have now become friends!");
                    System.out.println(helper + "'s reputation in the eyes of " + helped + " has increased by 2!");
                    Relationship lab = DataManipulation.friendTo(helped, helper, 2);
                    DataManipulation.friendTo(helper, helped, 0);
                    lab.setProperty("goodLab", "false");
                }
            } else {
                System.out.println("\nThey are not strangers. Are you lying to yourself?");
            }
        } catch (Exception e) {
            System.out.println("Please enter a valid student's name.");
        }
    }

    public static void eventTwo() {
        Scanner input = new Scanner(System.in);
        try {
            System.out.println("\nWho was the first person chit-chatting?");
            String person1 = input.nextLine().toUpperCase();
            System.out.println("\nWho was the second person chit-chatting?");
            String person2 = input.nextLine().toUpperCase();
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
                String talked = input.nextLine().toUpperCase();
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
                Iterable<Relationship> relationships = talkednode.getRelationships(Direction.INCOMING, Sociopath.Rels.IS_FRIENDS_WITH);
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
                        DataManipulation.knowsOf(talked, person2, (int) Math.floor(0.5 * (int) relay.getProperty("rep")));
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
                        DataManipulation.knowsOf(talked, person2, (int) Math.floor(-1 * (int) relay.getProperty("rep")));
                        System.out.println(person2 + " now knows of " + talked + " !");
                        System.out.println(talked + "'s reputation in the eyes (or in this case, ears) of " + person2 + " has decreased by " + (int) Math.floor(-1 * (int) relay.getProperty("rep")) + "!");
                        return;
                    }
                }
                System.out.println("Lies! " + talked + " did not help " + person1 + " with their lab questions!");
            }
        } catch (Exception e) {
            System.out.println("Please enter a valid student's name.");
        }
    }

    public static void eventThree() {
        Scanner input = new Scanner(System.in);
        try {
            System.out.println("\nWho is going to use this unrivaled algorithm to obtain maximum reputation by having lunch?");
            String user = input.nextLine().toUpperCase();
            Node usernode = DataManipulation.getNode(user);
            System.out.println("\nVery well, " + user + " , proceed with caution.");
            LunchMethods.nextDay();

            System.out.println("Through meticulous calculation , Sociopath has decided who to have lunch with to gain the most rep\n");
            System.out.println("Day : " + (LunchMethods.getDayCounter() - 1));
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
            System.out.println("\n" + user + " should have lunch with the following :\n");
            LunchMethods.lunchAlgo(user, LunchMethods.getAvgLunchStart(usernode), LunchMethods.getAvgLunchEnd(usernode));
        } catch (Exception e) {
            System.out.println("Event was not executed properly.Please enter a valid student's name.");
            eventThree();
        }
    }

    public static void eventFour() {
        //hhee
        Scanner input = new Scanner(System.in);
        try {
            int counter = 0;
            LinkedList<Integer> bookList = new LinkedList<>();
            System.out.println("\nOn a lazy Sunday afternoon, you decided to volunteer at the library. The librarian, Jabbar, has asked you to arrange some books.");
            System.out.println("Enter number of books: ");
            int numOfBooks = Sociopath.input.nextInt();
            Sociopath.input.nextLine();
            System.out.println("Enter books' heights: ");
            String bookHeightsInput = Sociopath.input.nextLine();
            //split space
            String[] bookHeights = bookHeightsInput.split(" ");
            //push book height elements into a list while parse into integer
            if(bookHeights.length != numOfBooks){
                System.out.println("Heights inserted is not the same as input");
            }
            if(bookHeights.length == numOfBooks){
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
        } catch (Exception e) {
            System.out.println("Please enter the number of books and the book's heights in a String with blank spaces in between.");
        }
    }

    public static void eventFive() {
        //Get all nodes and randomize crush and rumour
        ResourceIterator<Node> allStudents = DataManipulation.getAllNodes();
        ArrayList<Node> studentlist = new ArrayList<>();
        while (allStudents.hasNext()) {
            Node student = allStudents.next();
            studentlist.add(student);
        }
        Random r = new Random();
        Node rumor = studentlist.get(r.nextInt(studentlist.size()));
        Node crush = studentlist.get(r.nextInt(studentlist.size()));
        while (rumor.equals(crush)) {
            rumor = studentlist.get(r.nextInt(studentlist.size()));
        }

        //Text
        System.out.println("You have a crush on " + crush.getProperty("name") + " but there is someone who has a rumour about you and he/she is " + rumor.getProperty("name"));
        System.out.println("");

        //Traverse and return the path
        Iterable<Path> all = DataManipulation.getAllPaths(rumor, crush);//Returns all paths found
        try {
            //To collect paths found into an ArrayList from Iterable above
            ArrayList<Path> pathlist = new ArrayList<>();
            for (Path curr : all) {
                pathlist.add(curr);
            }

            //To create a list that holds lists of paths which contains lists of nodes
            ArrayList<ArrayList<Node>> list = new ArrayList<>();
            int index = 0;
            for (Path curr : pathlist) {
                list.add(new ArrayList<Node>());
                Iterable<Node> nodesList = curr.nodes();
                for (Node node : nodesList) {
                    list.get(index).add(node);
                }
                index++;
            }

            //Filter duplicate paths obtained by the path finder framework
            list = DataManipulation.removeDuplicates(list);

            //Filter for empty paths
            if (list.isEmpty()) {
                System.out.println("Luckily, it is impossible for the rumour to reach your crush");
                return;
            }
            DataManipulation.displayPath(list);
            DataManipulation.convincer(list, crush);

        } catch (NoSuchElementException ex) {
            System.out.println("Luckily, it is impossible for the rumour to reach your crush");
        }
    }

    public static void eventSix() {
        Scanner input = new Scanner(System.in);
        DataManipulation dm = new DataManipulation();
        ArrayList<Node> nodesList = new ArrayList<>();
        ArrayList<String> relationships = new ArrayList<>();
        ArrayList<String> taken = new ArrayList<>();

        // Input
        System.out.println("\nHow many friendships do you want to examine? --> ");
        int n = input.nextInt();

        System.out.println("\nEnter " + n + " friendships between " + n + " people. (Example: 1 2 or A B)\n");
        for (int i = 0; i < n; i++) {
            try {
                // Takes input
                String friend1 = input.next();
                String friend2 = input.next();

                /**
                 * Filtering conditions to re-prompt inputs
                 */
                // If same characters are entered
                if (friend1 == friend2) {
                    System.out.println("You entered the same character twice. Try again.");
                    friend1 = input.next();
                    friend2 = input.next();
                    System.out.println();
                }
                // If user enters more than n nodes
                if (taken.size() == n) {
                    while (!taken.contains(friend1) || !taken.contains(friend2)) {
                        System.out.println("You entered more than " + n + " characters. Try again.\n");
                        friend1 = input.next();
                        friend2 = input.next();
                        System.out.println();
                    }
                }
                // Filters duplicate nodes
                if (!taken.contains(friend1)) {
                    taken.add(friend1);
                }
                if (!taken.contains(friend2)) {
                    taken.add(friend2);
                }

                // Concatenates friend1 and friend2
                // add into relationships
                String relay = friend1 + friend2;
                relationships.add(relay);
            } // TODO Fix try-catch
            catch (Exception e) {
                System.out.println("You entered an unsupported character. Please try again.");
                eventSix();
            }
        }
        System.out.println();
        dm.displayPathsE6(dm, nodesList, relationships, taken, n);

    }

    public static void eventSeven() {
        Scanner input = new Scanner(System.in);
        try {
            System.out.println("Select event :");
            System.out.println("1. Bully");
            System.out.println("2. Love Proposal");
            int choice = input.nextInt();
            switch (choice) {
                case 1: {
                    bully();
                    break;
                }
                case 2: {
                    loveProposal();
                    break;
                }
                default: {
                    System.out.println("Please type in a valid number option. Returning to main menu . . .");
                    Menus.mainMenu();
                }
            }
        } catch (Exception e) {
            System.out.println("Please enter an integer!");
            eventSeven();
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

    public static void bully() {
        Scanner input = new Scanner(System.in);
        try {
            System.out.println("Who was the bully?");
            String bully = input.nextLine().toUpperCase();
            Node bullyNode = DataManipulation.getNode(bully);
            System.out.println("Which person was being bullied?");
            String bullied = input.nextLine().toUpperCase();
            System.out.println("The small and weak " + bullied + " was preyed upon by the big bad bully , " + bully + "!");
            DataManipulation.bullies(bullied, bully, -1);
            System.out.println(bullied + " now hates " + bully);
            DataManipulation.hates(bully, bullied, -1);
            System.out.println("\nThe voices of " + bullied + " echoed through the school halls.");
            System.out.println("Everyone now knows that " + bully + " is bullying " + bullied + "\n");
            Iterable<Relationship> list = bullyNode.getRelationships();
            for (Relationship r : list) {
                DataManipulation.incrementRep(r, -1);
            }

            System.out.println("-1 Reputation for every relationship that " + bully + " has");
        } catch (Exception e) {
            System.out.println("\nEvent was not executed properly.Please enter a valid student's name.\n");
            bully();
        }
    }

    public static void loveProposal() {
        Scanner input = new Scanner(System.in);
        try {
            System.out.println("\nYou were walking home from school when you saw two shady figures on your way out.");
            System.out.println("Using your ultimate stealth, you creep behind them unnoticed to hear that one of the shady figures had confessed their love to the other!");
            System.out.println("Creeping closer, you realize who those two shady figures were. . .");
            System.out.println("\nWho was the one confessing their love?");
            String lover = input.nextLine().toUpperCase();
            Node loverNode = DataManipulation.getNode(lover);
            System.out.println("\nWho was the one being confessed to?");
            String loved = input.nextLine().toUpperCase();
            if (lover.equals(loved)) {
                System.out.println("\n Now you're not even making sense . . .");
                return;
            }
            if (DataManipulation.isLoversWith(lover, loved)) {
                System.out.println("\nThey are already lovers. \nReturning to main menu . . .");
                return;
            }

            System.out.println("\nWell, stop being dramatic! Did they accept the proposal? (YES/NO)");
            String choice = input.nextLine();
            if (choice.equalsIgnoreCase("yes")) {
                //if already have a crush
                if (DataManipulation.isLoversWith(loved, lover)) {
                    DataManipulation.incrementRep(DataManipulation.getRelationship(loved, lover, Sociopath.Rels.LOVES), 10);
                }
                if (!DataManipulation.isLoversWith(loved, lover)) {
                    DataManipulation.loves(loved, lover, 20);
                }
                DataManipulation.loves(lover, loved, 20);
                System.out.println("\n" + lover + " and " + loved + " are now lovers!\n");
                return;
            }
            if (choice.equalsIgnoreCase("no")) {
                System.out.println("\nThat's too bad. . .");
                if (DataManipulation.isLoversWith(loved, lover)) {
                    DataManipulation.incrementRep(DataManipulation.getRelationship(loved, lover, Sociopath.Rels.LOVES), 1);
                    System.out.println("Through every rejection, " + lover + "'s crush on " + loved + " grows bigger and bigger!");
                    return;
                }
                DataManipulation.loves(loved, lover, 10);
                System.out.println(lover + " still has a crush on " + loved + ", but " + loved + " does not feel the same way . . .");
                return;
            }
            System.out.println("I don't wanna waste anymore time with you. Try putting in the correct input next time. Returning to main menu . . .");
        } catch (Exception e) {
            System.out.println("Event was not executed properly.Please enter a valid student's name.");
            loveProposal();
        }
    }

}
