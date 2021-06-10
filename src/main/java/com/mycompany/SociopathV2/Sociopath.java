/*sociopath
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.SociopathV2;

import java.io.*;
import java.util.*;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

public class Sociopath {

    public static GraphDatabaseFactory dbFactory = new GraphDatabaseFactory();
    public static GraphDatabaseService graphDb = dbFactory.newEmbeddedDatabase(
            new File("data/students"));
    public static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        graphDb.beginTx();
        StudentMethods.initializeStudents();
        Menus.mainMenu();
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
