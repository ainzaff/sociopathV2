/*datamanipulation
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.SociopathV2;

import java.util.*;

import org.neo4j.graphalgo.GraphAlgoFactory;
import org.neo4j.graphalgo.PathFinder;
import org.neo4j.graphdb.*;

import static com.mycompany.SociopathV2.Sociopath.graphDb;

/**
 * @author lenovo
 */
public class DataManipulation {

    public static Scanner input = new Scanner(System.in);

    //Remove duplicate elements in an arraylist method for Event 5
    public static <T> LinkedList<T> removeDuplicates(LinkedList<T> list) {
        Set<T> set = new LinkedHashSet<>();
        set.addAll(list);
        list.clear();
        list.addAll(set);
        return list;
    }

    public static <T> ArrayList<T> removeDuplicates(ArrayList<T> list) {
        Set<T> set = new LinkedHashSet<>();
        set.addAll(list);
        list.clear();
        list.addAll(set);
        return list;
    }

    public static Relationship getRelationship(String s1, String s2, RelationshipType rt) {
        Node src = graphDb.findNode(Sociopath.Labels.STUDENT, "name", s1.toUpperCase());
        Node target = graphDb.findNode(Sociopath.Labels.STUDENT, "name", s2.toUpperCase());
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

    public static Relationship friendTo(String name1, String name2, int repTo) {
        Node s1 = graphDb.findNode(Sociopath.Labels.STUDENT, "name", name1.toUpperCase());
        Node s2 = graphDb.findNode(Sociopath.Labels.STUDENT, "name", name2.toUpperCase());
        Relationship relationship = s1.createRelationshipTo(s2, Sociopath.Rels.IS_FRIENDS_WITH);
        relationship.setProperty("rep", repTo);
        return relationship;
    }

    // Overload
    public static Relationship friendTo(String name1, String name2) {
        // Find node
        Node s1 = graphDb.findNode(Sociopath.Labels.STUDENT, "name", name1.toUpperCase());
        Node s2 = graphDb.findNode(Sociopath.Labels.STUDENT, "name", name2.toUpperCase());
        Relationship relationship = s1.createRelationshipTo(s2, Sociopath.Rels.IS_FRIENDS_WITH);

        return relationship;
    }

    public static void incrementRep(Relationship r, int change) {
        r.setProperty("rep", (int) r.getProperty("rep") + change);
    }

    public static Relationship knowsOf(String name1, String name2, int repTo) {
        Node s1 = graphDb.findNode(Sociopath.Labels.STUDENT, "name", name1.toUpperCase());
        Node s2 = graphDb.findNode(Sociopath.Labels.STUDENT, "name", name2.toUpperCase());
        Relationship relationship = s1.createRelationshipTo(s2, Sociopath.Rels.KNOWS_OF);
        relationship.setProperty("rep", repTo);
        return relationship;
    }

    public static boolean isFriendsWith(String s1, String s2) {
        Node src = graphDb.findNode(Sociopath.Labels.STUDENT, "name", s1.toUpperCase());
        Node target = graphDb.findNode(Sociopath.Labels.STUDENT, "name", s2.toUpperCase());
        Iterable<Relationship> relationships = src.getRelationships(Direction.OUTGOING, Sociopath.Rels.IS_FRIENDS_WITH);
        for (Relationship relationship : relationships) {
            if (relationship.getEndNode().equals(target)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isLoversWith(String s1, String s2) {
        Node src = graphDb.findNode(Sociopath.Labels.STUDENT, "name", s1.toUpperCase());
        Node target = graphDb.findNode(Sociopath.Labels.STUDENT, "name", s2.toUpperCase());
        Iterable<Relationship> relationships = src.getRelationships(Direction.OUTGOING, Sociopath.Rels.LOVES);
        for (Relationship relationship : relationships) {
            if (relationship.getEndNode().equals(target)) {
                return true;
            }
        }
        return false;
    }

    public static Relationship bullies(String name1, String name2, int repTo) {
        Node s1 = graphDb.findNode(Sociopath.Labels.STUDENT, "name", name1.toUpperCase());
        Node s2 = graphDb.findNode(Sociopath.Labels.STUDENT, "name", name2.toUpperCase());
        Relationship relationship = s1.createRelationshipTo(s2, Sociopath.Rels.BULLIES);
        relationship.setProperty("rep", repTo);
        return relationship;
    }

    public static Relationship hates(String name1, String name2, int repTo) {
        Node s1 = graphDb.findNode(Sociopath.Labels.STUDENT, "name", name1.toUpperCase());
        Node s2 = graphDb.findNode(Sociopath.Labels.STUDENT, "name", name2.toUpperCase());
        Relationship relationship = s1.createRelationshipTo(s2, Sociopath.Rels.HATES);
        relationship.setProperty("rep", repTo);
        return relationship;
    }

    public static Relationship loves(String name1, String name2, int repTo) {
        Node s1 = graphDb.findNode(Sociopath.Labels.STUDENT, "name", name1.toUpperCase());
        Node s2 = graphDb.findNode(Sociopath.Labels.STUDENT, "name", name2.toUpperCase());
        Relationship relationship = s1.createRelationshipTo(s2, Sociopath.Rels.LOVES);
        relationship.setProperty("rep", repTo);
        return relationship;
    }

    public static Node getNode(String name) {
        return graphDb.findNode(Sociopath.Labels.STUDENT, "name", name.toUpperCase());
    }

    // Method for expanding path during traversal
    public static Iterable<Path> getAllPaths(Node src, Node target) {
        PathExpander expander = PathExpanders.forType(Sociopath.Rels.IS_FRIENDS_WITH);
        PathFinder<Path> allPath = GraphAlgoFactory.allSimplePaths(expander, 50);
        return allPath.findAllPaths(src, target);
    }

    public static ResourceIterator<Node> getAllNodes() {
        return graphDb.findNodes(Sociopath.Labels.STUDENT);
    }

    /**
     * ******************EVENT 5 METHODS******************
     */
    public static void displayPath(ArrayList<ArrayList<Node>> list) {
        System.out.println("The following is the path from the rumour to your crush:");
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.get(i).size(); j++) {
                if (j == list.get(i).size() - 1) {
                    System.out.print(list.get(i).get(j).getProperty("name"));
                } else {
                    System.out.print(list.get(i).get(j).getProperty("name") + "-->");
                }
            }
            System.out.println("");
        }
        System.out.println("");
    }

    public static void convincer(ArrayList<ArrayList<Node>> list, Node crush) {
        ArrayList<Node> isRemoved = new ArrayList<>();  //List to hold removed/convinced person

        //Traverse through nodes and check paths according to depth/cost
        int cost = 1;
        while (!list.isEmpty()) {
            int currPathIndex = 0;
            int currPathSize = list.get(currPathIndex).size();
            for (int i = 0; i < list.size(); i++) {

                //Go for shortest paths first
                if (list.get(i).size() < currPathSize) {
                    currPathIndex = i;
                    currPathSize = list.get(i).size();
                }

                //Check whether current node in the depth is crush,if true then impossible
                if (list.get(i).get(cost).equals(crush)) {
                    System.out.println("There is no way you can stop the rumour from reaching your crush");
                    return;
                }
            }

            //Convince person that is not yet in isRemoved list
            if (!isRemoved.contains(list.get(currPathIndex).get(cost))) {
                isRemoved.add(list.get(currPathIndex).get(cost));
            }

            //Filter paths that contains previously removed nodes to avoid multiple convincing
            for (int i = 0; i < list.size(); i++) {
                if (isRemoved.contains(list.get(i).get(cost))) {
                    list.remove(i);
                    i--;
                }
            }
            cost++; //Iterate to enter the next depth
        }

        //Last output
        System.out.println("The way to convince are as below");
        for (int i = 0; i < isRemoved.size(); i++) {
            System.out.println("Day " + (i + 1) + ": Convince " + isRemoved.get(i).getProperty("name"));
        }
        System.out.println("You're safe!\n");
    }

    /**
     * ******************EVENT 6 METHODS******************
     */
    // Computes the paths
    public static void displayPathsE6(DataManipulation dm, ArrayList<Node> inputNodesList, ArrayList<String> relationships, ArrayList<String> taken, int n) {
        // Creates n nodes labeled by user inputs
        for (int i = 0; i < n; i++) {
            Node node = graphDb.createNode(Sociopath.Labels.STUDENT);
            node.setProperty("name", taken.get(i));
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
        for (int i = 0; i < taken.size(); i++) {
            for (int j = i + 1; j < taken.size(); j++) {
                pathsIterablesList.add(dm.getAllPaths(inputNodesList.get(i), inputNodesList.get(j)));
            }
        }

        // Retrieves all pathsList Iterable from pathsIterablesList
        // add to pathsList
        ArrayList<Path> pathsList = new ArrayList<>();
        for (Iterable<Path> pathsIterable : pathsIterablesList) {
            // Retrieves all paths from pathsIterable
            // add to pathsList
            for (Path tempPaths : pathsIterable) {
                pathsList.add(tempPaths);
            }
        }

        // Retrieves all nodesList (paths) from pathsList
        // add to nodesIterable
        // !--> path == nodesList
        // index tracks number of paths
        ArrayList<ArrayList<Node>> nodesListsList = new ArrayList<>();
        int index = 0;
        for (Path nodesList : pathsList) {
            // Adds all nodes in nodesList into nodesIterable
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
        nodesListsList = removeDuplicates(nodesListsList);
        nodesListsList.sort(Comparator.comparing(List::size));
        displayPathsE6Util(nodesListsList, index);
        clearAllE6(nodesListsList);
    }

    // To delete the created nodes to avoid future nodes duplication errors
    public static void clearAllE6(ArrayList<ArrayList<Node>> nodesListsList) {
        for (ArrayList<Node> nodesList : nodesListsList) {
            for (Node node : nodesList) {
                try (Transaction tx = graphDb.beginTx()) {
                    node.delete();
                    tx.success();
                } catch (Exception e) {
                }
            }
        }
    }

    // To actually display the paths
    public static void displayPathsE6Util(ArrayList<ArrayList<Node>> nodesListsList, int n) {
        System.out.println("You can form " + n + " friendships: ");
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
        System.out.println();
    }

    public static String[] numWords = new String[]{"Zero", "one", "two", "three", "four", "five", "six", "seven",
            "eight", "nine",
            "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen",
            "eighteen", "nineteen"};

}
