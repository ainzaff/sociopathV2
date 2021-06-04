/*datamanipulation
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.SociopathV2;

import java.util.ArrayList;
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

   public static ResourceIterator<Node> getAllNodes (){
       return Sociopath.graphDb.findNodes(Sociopath.Labels.STUDENT);
   }
}
