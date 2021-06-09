/*events
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.SociopathV2;

import java.util.*;

import org.neo4j.graphdb.*;

/**
 * @author lenovo
 */
public class Events {

    public static Scanner input = new Scanner(System.in);

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

    public static void eventThree() {
        Sociopath.input.nextLine();
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
//        // User input
//        System.out.println("\nHow many friendships do you want to examine?");
//        int n = Sociopath.input.nextInt();
//        Graph graph = new Graph(n*2);
//        ArrayList<Integer> list = new ArrayList<>();
//
//        // New addition
//        // Creates nodes and forms a relationships
//        System.out.println("Enter TWO space-separated integers. Example: 1 2)");
//        for (int i = 0; i < n; i++) {
//            int node1 = input.nextInt();
//            if (!list.contains(node1)){
//                list.add(node1);
//            }
//            int node2 = input.nextInt();
//            if (!list.contains(node2)){
//                list.add(node2);
//            }
//            graph.addEdge(node1,node2);
//
//        }
//        
//        // return all paths from each source node to each end node
//        for (int i : list){
//            for (int k : list){
//                if (i==k){
//                    continue;
//                }
//                graph.printAllPaths(i, k);
//            }
//        }
//        DataManipulation dm = new DataManipulation();
//        ArrayList<Node> nodesList = new ArrayList<Node>();

//        // User input
//        System.out.println("\nHow many friendships do you want to examine?");
//        int n = Sociopath.input.nextInt();
//
//        // New addition
//        // Creates nodes and forms a relationships
//        System.out.println("Enter TWO space-separated integers. Example: 1 2)");
//        for (int i = 0; i < n; i++) {
//            String str1 = input.next();
//            String str2 = input.next();
//
//            // Filter node1 duplicates
//            // Filters duplicate str1
//           Sociopath.graphDb.schema()
//                    .constraintFor(Label.label("STUDENT"))
//                    .assertPropertyIsUnique("name")
///                   .create();
//            for (Node node : nodesList) {
//                if (node.getProperty("name").equals(str1) || node == null) {
//                    continue;
//                } else if (!node.getProperty("name").equals(str1)) {
//                    Node node1 = Sociopath.graphDb.createNode(Sociopath.Labels.STUDENT);
//                    node1.setProperty("name", str1);
//                    nodesList.add(node1);
//                }
//            }
//
//            // Filter node2 duplicates
//            for (Node node : nodesList) {
//                if (node.getProperty("name").equals(str2) || node == null) {
//                    continue;
//                } else if (!node.getProperty("name").equals(str2)) {
//                    Node node2 = Sociopath.graphDb.createNode(Sociopath.Labels.STUDENT);
//                    node2.setProperty("name", str2);
//                    nodesList.add(node2);
//                }
//            }
//
//
//           Filters duplicate str2
//            Sociopath.graphDb.schema()
//                    .constraintFor(Label.label("STUDENT"))
//                    .assertPropertyIsUnique(str2)
//                   .create();
//
//            dm.friendTo(str1, str2);
//        }
//
//        // Uses allSimplePaths
//        PathFinder<Path> pathFinder = GraphAlgoFactory.allSimplePaths(
//                PathExpanders.forDirection(Direction.OUTGOING), n + 5);
//
//        // TODO Print all paths
//        List<Iterable<Node>> list = new ArrayList<>();
//        for (Path path : pathFinder.findAllPaths(node1, node2)) {
//            list.add(path.nodes());
//        }
//        for(Iterable<Node> x : list) {
//            for(Node node : x) {
//                System.out.print(node.getProperty("name") + " ");
//            }
//        }
        DataManipulation dm = new DataManipulation();
        ArrayList<Node> nodesList = new ArrayList<>();
        ArrayList<String> relationships = new ArrayList<>();
        ArrayList<String> takenInts = new ArrayList<>();

        System.out.print("\nHow many friendships do you want to examine? --> ");
        int n = Sociopath.input.nextInt();

        System.out.println("\nEnter TWO space-separated integers. (Example: 1 2)");
        for (int i = 0; i < n; i++) {
            // Takes input
            String str1 = input.next();
            String str2 = input.next();

            String relay = str1 + str2;
            relationships.add(relay);

            // Tracks similar inputs to filter duplicates
            if (!takenInts.contains(str1))
                takenInts.add(str1);
            if (!takenInts.contains(str2))
                takenInts.add(str2);
        }
        System.out.println();
        displayPathsE6(dm, nodesList, relationships, takenInts, n);
    }

    // TODO Add try-catch when user inputs more than n number of nodes
    // TODO Sort output by length
    // Computes the paths
    private static void displayPathsE6(DataManipulation dm, ArrayList<Node> inputNodesList, ArrayList<String> relationships, ArrayList<String> takenInts, int n) {
        // Creates n nodes labeled by user inputs
        for (int i = 0; i < n; i++) {
            Node node = Sociopath.graphDb.createNode(Sociopath.Labels.STUDENT);
            node.setProperty("name", takenInts.get(i));
            inputNodesList.add(node);
        }

        // Forms friendships between nodes
        for (int i = 0; i < n; i++) {
            String[] relationshipSplit = relationships.get(i).split("");
            String first = relationshipSplit[0];
            String second = relationshipSplit[1];
            dm.friendTo(first, second);
        }

        ArrayList<Iterable<Path>> pathsIterablesList = new ArrayList<>();

        // Retrieves all pathsList between all nodes
        // add to pathsIterablesList
        for (int i = 0; i < takenInts.size(); i++) {
            for (int j = i + 1; j < takenInts.size(); j++) {
                pathsIterablesList.add(dm.getAllPaths(inputNodesList.get(i), inputNodesList.get(j)));
            }
        }

        // Retrieves all pathsList Iterable from pathsIterablesList
        // add to pathsList
        ArrayList<Path> pathsList = new ArrayList<>();
        pathsList = new ArrayList<>();
        for (Iterable<Path> pathsIterable : pathsIterablesList) {
            // Retrieves all paths from pathsIterable
            // add to pathsList
            for (Path tempPaths : pathsIterable) {
                pathsList.add(tempPaths);
            }
        }

        // TODO Debug checkpoint
        // Retrieves all nodesList (paths) from pathsList
        // add to nodesIterable
        // !--> path == nodesList
        // index tracks number of paths
        ArrayList<ArrayList<Node>> nodesListsList = new ArrayList<>();
        int index = 0;
        for (Path nodesList : pathsList) {
            // nodes() returns nodesIterable
            Iterable<Node> nodesIterable = nodesList.nodes();

            // Add a new nodesList in nodesListsList
            nodesListsList.add(new ArrayList<Node>());

            // Retrieves all nodes from nodesIterable
            // add to the newly created nodesList in nodesListList
            for (Node node : nodesIterable) {
                // nodesListsList.get(i) returns a nodeList
                nodesListsList.get(index).add(node);
            }
            index++;
        }
        nodesListsList = dm.removeDuplicates(nodesListsList);
        displayPathE6Util(nodesListsList);
    }

    // To actually display the paths
    private static void displayPathE6Util(ArrayList<ArrayList<Node>> nodesListsList) {
        for (ArrayList<Node> nodesList : nodesListsList) {
            for (int i = 0; i < nodesList.size(); i++) {
                // If first node
                if (i == 0) {
                    System.out.print("[" + nodesList.get(i).getProperty("name") + ", ");
                }
                // If last node
                else if (i == nodesList.size() - 1) {
                    System.out.print(nodesList.get(i).getProperty("name") + "]");
                    System.out.println();
                } else {
                    System.out.print(nodesList.get(i).getProperty("name") + ", ");
                }
            }
        }


//        // For every nodesList in nodesListsList
//        for (int i = 0; i < nodesListsList.size(); i++) {
//            ArrayList<Node> nodesList = nodesListsList.get(i);
//            // For every nodes in nodesList
//            for (int j = 0; j < nodesList.size(); j++) {
//                // If first node
//                if (j == 0)
//                    System.out.print("[" + nodesList.get(j).getProperty("name") + ", ");
//                    // If last node
//                else if (j == nodesList.size() - 1)
//                    System.out.print(nodesList.get(j).getProperty("name") + "]");
//                else
//                    System.out.print(nodesList.get(j).getProperty("name") + ", ");
//            }
//            System.out.println("");
//        }
//        System.out.println("");
    }

    public static void eventSeven() {
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
        input.nextLine();
        System.out.println("Who was the bully?");
        String bully = input.nextLine();
        Node bullyNode = DataManipulation.getNode(bully);
        System.out.println("Which person was being bullied?");
        String bullied = input.nextLine();
        System.out.println("The small and weak " + bullied + " was preyed upon by the big bad bully , " + bully + "!");
        DataManipulation.bullies(bully, bullied, -1);
        System.out.println(bullied + " now hates " + bully);
        DataManipulation.hates(bullied, bully, -1);
        System.out.println("\nThe voices of " + bullied + " echoed through the school halls.");
        System.out.println("Everyone now knows that " + bully + " is bullying " + bullied + "\n");
        Iterable<Relationship> list = bullyNode.getRelationships();
        for (Relationship r : list) {
            DataManipulation.incrementRep(r, -1);
        }

        System.out.println("-1 Reputation for every relationship that " + bully + " has");
    }

    public static void loveProposal() {
        input.nextLine();
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
        if (DataManipulation.isLoversWith(loved, lover)) {
            System.out.println("\nThey are already lovers. \nReturning to main menu . . .");
            return;
        }

        System.out.println("\nWell, stop being dramatic! Did they accept the proposal? (YES/NO)");
        String choice = input.nextLine();
        if (choice.equalsIgnoreCase("yes")) {
            //if already have a crush
            if (DataManipulation.isLoversWith(lover, loved)) {
                DataManipulation.incrementRep(DataManipulation.getRelationship(lover, loved, Sociopath.Rels.LOVES), 10);
            }
            if (!DataManipulation.isLoversWith(lover, loved)) {
                DataManipulation.loves(lover, loved, 20);
            }
            DataManipulation.loves(loved, lover, 20);
            System.out.println("\n" + lover + " and " + loved + " are now lovers!\n");
            return;
        }
        if (choice.equalsIgnoreCase("no")) {
            System.out.println("\nThat's too bad. . .");
            if (DataManipulation.isLoversWith(lover, loved)) {
                DataManipulation.incrementRep(DataManipulation.getRelationship(lover, loved, Sociopath.Rels.LOVES), 1);
                System.out.println("Through every rejection, " + lover + "'s crush on " + loved + " grows biggers and bigger!");
            }
            DataManipulation.loves(lover, loved, 10);
            System.out.println(lover + " still has a crush on " + loved + ", but " + loved + " does not feel the same way . . .");
            return;
        }
        System.out.println("I don't wanna waste anymore time with you. Try putting in the correct input next time. Returning to main menu . . .");
    }

}
