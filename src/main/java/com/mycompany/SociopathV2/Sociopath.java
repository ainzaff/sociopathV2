/*sociopath
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
        StudentMethods.initializeStudents();
        Menus.mainMenu();
        //studentMenu();
        //Events.eventSix();

    }

    public static enum Rels implements RelationshipType {
        IS_FRIENDS_WITH,
        LOVES,
        HATES,
        BULLIES,
        KNOWS_OF;

    }

    public static enum Labels implements Label {
        STUDENT;
    }

}
