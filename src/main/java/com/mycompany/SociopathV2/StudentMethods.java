/*studentmethods
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.SociopathV2;

import java.util.Scanner;
import javax.swing.JFrame;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.ResourceIterator;

/**
 *
 * @author lenovo
 */
public class StudentMethods {

    public static Scanner input = new Scanner(System.in);

    public static void initializeStudents() {
        Node Alice = Sociopath.graphDb.createNode(Sociopath.Labels.STUDENT);
        Node Bob = Sociopath.graphDb.createNode(Sociopath.Labels.STUDENT);
        Node Charlie = Sociopath.graphDb.createNode(Sociopath.Labels.STUDENT);
        Node Daniel = Sociopath.graphDb.createNode(Sociopath.Labels.STUDENT);
        Node Ethan = Sociopath.graphDb.createNode(Sociopath.Labels.STUDENT);
        Node Finn = Sociopath.graphDb.createNode(Sociopath.Labels.STUDENT);
        Node Guy = Sociopath.graphDb.createNode(Sociopath.Labels.STUDENT);
        Node Holly = Sociopath.graphDb.createNode(Sociopath.Labels.STUDENT);
        Node Ian = Sociopath.graphDb.createNode(Sociopath.Labels.STUDENT);
        Node Joe = Sociopath.graphDb.createNode(Sociopath.Labels.STUDENT);

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

        Alice.setProperty("avgLunchStart", 1200);
        Bob.setProperty("avgLunchStart", 1300);
        Charlie.setProperty("avgLunchStart", 1330);
        Daniel.setProperty("avgLunchStart", 1120);
        Ethan.setProperty("avgLunchStart", 1100);
        Finn.setProperty("avgLunchStart", 1400);
        Guy.setProperty("avgLunchStart", 1240);
        Holly.setProperty("avgLunchStart", 1350);
        Ian.setProperty("avgLunchStart", 1110);
        Joe.setProperty("avgLunchStart", 1310);

        Alice.setProperty("lunchPeriod", 30);
        Bob.setProperty("lunchPeriod", 40);
        Charlie.setProperty("lunchPeriod", 20);
        Daniel.setProperty("lunchPeriod", 30);
        Ethan.setProperty("lunchPeriod", 40);
        Finn.setProperty("lunchPeriod", 60);
        Guy.setProperty("lunchPeriod", 20);
        Holly.setProperty("lunchPeriod", 45);
        Ian.setProperty("lunchPeriod", 50);
        Joe.setProperty("lunchPeriod", 35);

        Alice.setProperty("avgLunchEnd", LunchMethods.getLunchEnd(Alice));
        Bob.setProperty("avgLunchEnd", LunchMethods.getLunchEnd(Bob));
        Charlie.setProperty("avgLunchEnd", LunchMethods.getLunchEnd(Charlie));
        Daniel.setProperty("avgLunchEnd", LunchMethods.getLunchEnd(Daniel));
        Ethan.setProperty("avgLunchEnd", LunchMethods.getLunchEnd(Ethan));
        Finn.setProperty("avgLunchEnd", LunchMethods.getLunchEnd(Finn));
        Guy.setProperty("avgLunchEnd", LunchMethods.getLunchEnd(Guy));
        Holly.setProperty("avgLunchEnd", LunchMethods.getLunchEnd(Holly));
        Ian.setProperty("avgLunchEnd", LunchMethods.getLunchEnd(Ian));
        Joe.setProperty("avgLunchEnd", LunchMethods.getLunchEnd(Joe));

        DataManipulation.friendTo("Alice", "Bob", 5);
        DataManipulation.friendTo("Bob", "Alice", 8);
        DataManipulation.friendTo("Alice", "Guy", 4);
        DataManipulation.friendTo("Guy", "Alice", 3);
        DataManipulation.friendTo("Bob", "Finn", 9);
        DataManipulation.friendTo("Finn", "Bob", 7);
        DataManipulation.friendTo("Bob", "Charlie", 5);
        DataManipulation.friendTo("Charlie", "Bob", 4);
        DataManipulation.friendTo("Bob", "Ethan", 6);
        DataManipulation.friendTo("Ethan", "Bob", 2);
        DataManipulation.friendTo("Daniel", "Holly", 7);
        DataManipulation.friendTo("Holly", "Daniel", 10);
        DataManipulation.friendTo("Daniel", "Joe", 7);
        DataManipulation.friendTo("Joe", "Daniel", 7);
        DataManipulation.friendTo("Joe", "Ian", 4);
        DataManipulation.friendTo("Ian", "Joe", 3);
    }

    public static void displayStudents() {
        jFrameDisplayStudents ds = new jFrameDisplayStudents();
        ds.setVisible(true);
        ds.pack();
        ds.setLocationRelativeTo(null);
        ds.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ds.setResizable(false);
    }
    
    public static void displayProfile(String name) {
        if (name.compareToIgnoreCase("exit") == 0) {
            Menus.mainMenu();
            return;
        }
        jFrameDisplayProfile dp = new jFrameDisplayProfile(name);
        dp.setVisible(true);
        dp.pack();
        dp.setLocationRelativeTo(null);
        dp.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dp.setResizable(false);
        Menus.mainMenu();
    }
    
    public static Object[][] getAllStudents() {
        Object[][] res = new Object[10][6];
        int colcounter = 0;
        int rowcounter = 0;
        ResourceIterator<Node> list = DataManipulation.getAllNodes();

        while (list.hasNext()) {
            colcounter = 0;
            Node s = list.next();
            res[rowcounter][colcounter] = s.getProperty("name");
            colcounter++;
            res[rowcounter][colcounter] = s.getProperty("dive");
            colcounter++;
            res[rowcounter][colcounter] = s.getProperty("lunchStart");
            colcounter++;
            res[rowcounter][colcounter] = s.getProperty("lunchPeriod");
            colcounter++;
            res[rowcounter][colcounter] = s.getProperty("prog");
            colcounter++;
            rowcounter++;
        }

        return res;
    }

    public static Object[][] getProfile(Node s1) {
        Object[][] res = new Object[100][4];
        int rowcounter = 0;
        Iterable<Relationship> list = s1.getRelationships(Direction.INCOMING);

        for (Relationship r : list) {
            int colcounter = 0;
            res[rowcounter][colcounter] = (Object) r.getType().toString();
            colcounter++;
            res[rowcounter][colcounter] = r.getStartNode().getProperty("name");
            colcounter++;
            res[rowcounter][colcounter] = r.getProperty("rep");
            rowcounter++;
        }

        return res;
    }

}