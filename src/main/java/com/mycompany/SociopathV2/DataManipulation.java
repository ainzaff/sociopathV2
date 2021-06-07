/*datamanipulation
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.SociopathV2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import org.neo4j.graphalgo.GraphAlgoFactory;
import org.neo4j.graphalgo.PathFinder;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Path;
import org.neo4j.graphdb.PathExpander;
import org.neo4j.graphdb.PathExpanders;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.ResourceIterator;

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
        Node src = Sociopath.graphDb.findNode(Sociopath.Labels.STUDENT, "name", s1.toUpperCase());
        Node target = Sociopath.graphDb.findNode(Sociopath.Labels.STUDENT, "name", s2.toUpperCase());
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
        Node s1 = Sociopath.graphDb.findNode(Sociopath.Labels.STUDENT, "name", name1.toUpperCase());
        Node s2 = Sociopath.graphDb.findNode(Sociopath.Labels.STUDENT, "name", name2.toUpperCase());
        Relationship relationship = s1.createRelationshipTo(s2, Sociopath.Rels.IS_FRIENDS_WITH);
        relationship.setProperty("rep", repTo);
        return relationship;
    }

    // Overload
    public static Relationship friendTo(String name1, String name2) {
        // Find node
        Node s1 = Sociopath.graphDb.findNode(Sociopath.Labels.STUDENT, "name", name1.toUpperCase());
        Node s2 = Sociopath.graphDb.findNode(Sociopath.Labels.STUDENT, "name", name2.toUpperCase());
        Relationship relationship = s1.createRelationshipTo(s2, Sociopath.Rels.IS_FRIENDS_WITH);

        return relationship;
    }

    public static void incrementRep(Relationship r, int change) {
        r.setProperty("rep", (int) r.getProperty("rep") + change);
    }

    public static Relationship knowsOf(String name1, String name2, int repTo) {
        Node s1 = Sociopath.graphDb.findNode(Sociopath.Labels.STUDENT, "name", name1.toUpperCase());
        Node s2 = Sociopath.graphDb.findNode(Sociopath.Labels.STUDENT, "name", name2.toUpperCase());
        Relationship relationship = s1.createRelationshipTo(s2, Sociopath.Rels.KNOWS_OF);
        relationship.setProperty("rep", repTo);
        return relationship;
    }

    public static boolean isFriendsWith(String s1, String s2) {
        Node src = Sociopath.graphDb.findNode(Sociopath.Labels.STUDENT, "name", s1.toUpperCase());
        Node target = Sociopath.graphDb.findNode(Sociopath.Labels.STUDENT, "name", s2.toUpperCase());
        Iterable<Relationship> relationships = src.getRelationships(Direction.OUTGOING, Sociopath.Rels.IS_FRIENDS_WITH);
        for (Relationship relationship : relationships) {
            if (relationship.getEndNode().equals(target)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isLoversWith(String s1, String s2) {
        Node src = Sociopath.graphDb.findNode(Sociopath.Labels.STUDENT, "name", s1.toUpperCase());
        Node target = Sociopath.graphDb.findNode(Sociopath.Labels.STUDENT, "name", s2.toUpperCase());
        Iterable<Relationship> relationships = src.getRelationships(Direction.OUTGOING, Sociopath.Rels.LOVES);
        for (Relationship relationship : relationships) {
            if (relationship.getEndNode().equals(target)) {
                return true;
            }
        }
        return false;
    }

    public static Relationship bullies(String name1, String name2, int repTo) {
        Node s1 = Sociopath.graphDb.findNode(Sociopath.Labels.STUDENT, "name", name1.toUpperCase());
        Node s2 = Sociopath.graphDb.findNode(Sociopath.Labels.STUDENT, "name", name2.toUpperCase());
        Relationship relationship = s1.createRelationshipTo(s2, Sociopath.Rels.BULLIES);
        relationship.setProperty("rep", repTo);
        return relationship;
    }

    public static Relationship hates(String name1, String name2, int repTo) {
        Node s1 = Sociopath.graphDb.findNode(Sociopath.Labels.STUDENT, "name", name1.toUpperCase());
        Node s2 = Sociopath.graphDb.findNode(Sociopath.Labels.STUDENT, "name", name2.toUpperCase());
        Relationship relationship = s1.createRelationshipTo(s2, Sociopath.Rels.HATES);
        relationship.setProperty("rep", repTo);
        return relationship;
    }

    public static Relationship loves(String name1, String name2, int repTo) {
        Node s1 = Sociopath.graphDb.findNode(Sociopath.Labels.STUDENT, "name", name1.toUpperCase());
        Node s2 = Sociopath.graphDb.findNode(Sociopath.Labels.STUDENT, "name", name2.toUpperCase());
        Relationship relationship = s1.createRelationshipTo(s2, Sociopath.Rels.LOVES);
        relationship.setProperty("rep", repTo);
        return relationship;
    }

    public static Node getNode(String name) {
        return Sociopath.graphDb.findNode(Sociopath.Labels.STUDENT, "name", name.toUpperCase());
    }

    // Method for expanding path during traversal
    public static Iterable<Path> getAllPaths(Node src, Node target) {
        PathExpander expander = PathExpanders.forType(Sociopath.Rels.IS_FRIENDS_WITH);
        //PathFinder<Path> allPath = GraphAlgoFactory.allPaths(expander, 10);
        PathFinder<Path> allPath = GraphAlgoFactory.allSimplePaths(expander, 50);
        return allPath.findAllPaths(src, target);
    }

    public static ResourceIterator<Node> getAllNodes() {
        return Sociopath.graphDb.findNodes(Sociopath.Labels.STUDENT);
    }

    /**
     * ******************Event 5 METHODS******************
     */
    public static void displayPath(ArrayList<ArrayList<Node>> list) {
        System.out.println("The following is the path from the rumour to your crush(left to right):");
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
        ArrayList<Node> isRemoved = new ArrayList<>();
        int cost = 1;
        while(!list.isEmpty()) {
            int currPathIndex = 0;
            int currPathSize = list.get(currPathIndex).size();
            for (int i = 0; i < list.size(); i++) {
                
                //Go for shortest paths first
                if(list.get(i).size()<currPathSize) {
                    currPathIndex = i;
                    currPathSize = list.get(i).size();
                }
                
                //Check whether current node in the depth is crush
                if(list.get(i).get(cost).equals(crush)) {
                    System.out.println("There is no way you can stop the rumour from reaching your crush");
                    return;
                }
            }
            
            //Check for removed node and add it to the removed list
            if(!isRemoved.contains(list.get(currPathIndex).get(cost))) {
                isRemoved.add(list.get(currPathIndex).get(cost));
            }
            
            //Remove paths that contains previously removed nodes
            for (int i = 0; i < list.size(); i++) {
                if(isRemoved.contains(list.get(i).get(cost))) {
                    list.remove(i);
                    i--;
                }
            }
            cost++;
        }
        
        //Last output
        System.out.println("The way to convince are as below");
        for (int i = 0; i < isRemoved.size(); i++) {
            System.out.println("Day "+(i+1)+": Convince "+isRemoved.get(i).getProperty("name"));
        }
        System.out.println("You're safe!\n");
    }

    // Event 6
    public static PathFinder<Path> instantiatePathFinder(int maxDepth) {
        return GraphAlgoFactory.allSimplePaths(PathExpanders.allTypesAndDirections(), maxDepth);
    }

    // Event 6
    public static String[] numWords = new String[]{"Zero", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine",
        "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen",
        "Eighteen", "Nineteen"};

}
