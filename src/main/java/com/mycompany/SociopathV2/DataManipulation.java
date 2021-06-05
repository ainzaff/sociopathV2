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
 *
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

    // Overloaded friendTo() for eventSix()
    public static Relationship friendTo(String name1, String name2) {
        // Creates two new students
        Node student1 = Sociopath.graphDb.createNode(Sociopath.Labels.STUDENT);
        Node student2 = Sociopath.graphDb.createNode(Sociopath.Labels.STUDENT);

        // Forms a friendship
        Relationship relationship = student1.createRelationshipTo(student2, Sociopath.Rels.IS_FRIENDS_WITH);
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

    //Method for expanding path during traversal
    public static Iterable<Path> getAllPaths(Node src, Node target) {
        PathExpander expander = PathExpanders.forType(Sociopath.Rels.IS_FRIENDS_WITH);
        //PathFinder<Path> allPath = GraphAlgoFactory.allPaths(expander, 10);
        PathFinder<Path> allPath = GraphAlgoFactory.allSimplePaths(expander, 50);
        return allPath.findAllPaths(src, target);
    }

    public static ResourceIterator<Node> getAllNodes() {
        return Sociopath.graphDb.findNodes(Sociopath.Labels.STUDENT);
    }

    public static void displayPath(LinkedList<LinkedList<Node>> list) {
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
        System.out.println("");
    }

    public static void convince(LinkedList<LinkedList<Node>> list, Node crush) {
        LinkedList<Node> isRemoved = new LinkedList<>();
        //CHECKING STARTS
        int freq = 0;
        for (int i = 0; i < list.size(); i++) {
            LinkedList<Node> path = list.get(i);
            if(path.size()==3) freq++;
        }
        if(freq>1) {
            System.out.println("There is no way you can stop the rumour from reaching your crush");
            return;
        }
        
        for (int cost = 1; cost < 20; cost++) {
            
            //Check previously removed node in path to prevent multiple convincing
            try {
                ArrayList<Integer> saveIndex = new ArrayList<>();
                for (int i = 0; i < list.size(); i++) {
                    LinkedList<Node> checkpath = list.get(i);
                    for (int j = 0; j < isRemoved.size(); j++) {
                        if (checkpath.contains(isRemoved.get(j))) {
                            saveIndex.add(i);
                        }
                    }
                }
                
                for (int i = 0; i < saveIndex.size(); i++) {
                    int j = saveIndex.get(i);
                    list.remove(j);
                    for (int k = 0; k < saveIndex.size(); k++) {
                        saveIndex.set(k, saveIndex.get(k)-1);
                    }
                }
            } catch (IndexOutOfBoundsException ex) {}
            
            //Check whether crush exists in current level
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).get(cost).equals(crush)) {
                    System.out.println("OH WAIT!!! After calculating, apparently you can't stop the rumour from reaching your crush");
                    return;
                }
            }

            boolean hasRemoved = false;
            //Check for similar nodes
            int similaritycount = 0;
            LinkedList<Integer> similarid = new LinkedList<>();
            LinkedList<String> getSimilar = new LinkedList<>();
            for (int i = 0; i < list.size(); i++) {
                getSimilar.add(list.get(i).get(cost).getProperty("name").toString());
            }
            for (int i = 0; i < getSimilar.size() - 1; i++) {
                similarid.add(0);
                if (getSimilar.get(i).equalsIgnoreCase(getSimilar.get(i + 1))) {
                    similaritycount++;
                    similarid.set(i, 1);
                    similarid.add(0);
                    similarid.set(i + 1, 1);
                }
            }

            //Priority for people directly connected to crush
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).get(cost + 1).equals(crush)) {
                    //Check if same person
                    if (similaritycount != 0 && similarid.get(i) == 1) {
                        for (int j = 0; j < list.size() - 1; j++) {
                            if (list.get(j).get(cost).equals(list.get(j + 1).get(cost))) {
                                System.out.println("Day " + cost + ": Convince " + list.get(i).get(cost).getProperty("name"));
                                if (similaritycount == list.size() - 1) { //This means all same node, so clear all
                                    list.clear();
                                    hasRemoved = true;
                                    break;
                                } else {
                                    isRemoved.add(list.get(i).get(cost));
                                    list.remove(j);
                                    list.remove(j + 1);
                                    hasRemoved = true;
                                    break;
                                }
                            }
                        }
                    } else {
                        System.out.println("Day " + cost + ": Convince " + list.get(i).get(cost).getProperty("name"));
                        isRemoved.add(list.get(i).get(cost));
                        list.remove(i);
                        hasRemoved = true;
                        break;
                    }
                }
            }

            if (!hasRemoved) {
                //Check if same person
                if (similaritycount != 0 && similarid.getFirst() == 1) {
                    for (int j = 0; j < list.size() - 1; j++) {
                        if (list.get(j).get(cost).equals(list.get(j + 1).get(cost))) {
                            System.out.println("Day " + cost + ": Convince " + list.get(j).get(cost).getProperty("name"));
                            if (similaritycount == list.size() - 1) {
                                list.clear();
                                break;
                            } else {
                                isRemoved.add(list.get(j).get(cost));
                                list.remove(j);
                                list.remove(j + 1);
                                break;
                            }
                        }
                    }
                } else {
                    isRemoved.add(list.remove().get(cost));
                    System.out.println("Day " + cost + ": Convince " + isRemoved.get(isRemoved.size()-1).getProperty("name"));
                }
            }
            if (list.isEmpty()) {
                System.out.println("You're safe!");
                return;
            }
        }
    }

}
