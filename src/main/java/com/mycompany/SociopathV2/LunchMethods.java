/*lunchmethods
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.SociopathV2;

import java.util.Random;
import java.util.Scanner;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.ResourceIterator;

/**
 *
 * @author lenovo
 */
public class LunchMethods {
    private static int dayCounter=1;
    public static Scanner input = new Scanner(System.in);
private static Random r = new Random();

    public static int getDayCounter() {
        return dayCounter;
    }
    
    public static int getNewLunchStart(Node s1){
        int rand = r.nextInt(3);
        int start=(int)s1.getProperty("lunchStart");
        switch(rand){
            case 0: start=startEarly(start);
                break;
            case 1: start = startLate(start);
                break;
            case 2: 
                break;
        }
        return changeToTime(start);
    }
    
    static public int getNewLunchPeriod(Node s1){
    int lunchPeriod = (int)s1.getProperty("lunchPeriod");
    int rand = r.nextInt(2);
    switch(rand){
        case 0: lunchPeriod= startEarly(lunchPeriod);
            break;
        case 1: lunchPeriod = startLate(lunchPeriod);
            break;
    }
    return lunchPeriod;
    }
    
    static public void setNewAvgLunchStart(Node s1){
        int avg=(int)Math.floor((((int)s1.getProperty("avgLunchStart")+getNewLunchStart(s1)))/2);
        s1.setProperty("avgLunchStart", changeToTime(avg));
    }
    
    static public int startEarly(int start){
        start-=(r.nextInt(15)+1);
        return start;
    }
    
    static public int startLate(int start){
        start+=(r.nextInt(15)+1);
        return start;
    }
    
    static public void setNewAvgLunchEnd(Node s1){
        int avg=(int)Math.floor((((int)s1.getProperty("avgLunchEnd")+getNewLunchEnd(s1)))/2);
        s1.setProperty("avgLunchEnd", changeToTime(avg));
    }
    
    static public int getAvgLunchStart(Node s1){
    return (int)s1.getProperty("avgLunchStart");
}
    static public int getAvgLunchEnd(Node s1){
    return (int)s1.getProperty("avgLunchEnd");
}
    
    static public int getNewLunchEnd(Node s1){
        int lunchStart = getNewLunchStart(s1);
        int lunchPeriod = getNewLunchPeriod(s1);
        int hour=0;
        int lunchEnd=lunchStart- lunchPeriod;
        

        return changeToTime(lunchEnd);
    }
    
        static public int getLunchEnd(Node s1){
        int lunchStart = (int)s1.getProperty("lunchStart");
        int lunchPeriod = (int)s1.getProperty("lunchPeriod");
        int hour=0;
        int lunchEnd=lunchStart- lunchPeriod;
        
        // if its starts at the exact hour and 60 minutes period
        if (lunchStart%100==0&&lunchPeriod>=60)
        {
            lunchEnd= lunchStart+100;
            lunchEnd+=lunchPeriod-60;
        }
        // if it starts at the exact hour but period is less than an hour
        else if (lunchStart%100==0){
            lunchEnd=lunchStart+lunchPeriod;
            
        }
        
        else{
            // get hour
        hour = lunchStart-(lunchStart%100);
        //get minutes
        int minutes = lunchStart%hour;
        //get lunchEnd's minutes
        int totalMinutes = minutes + lunchPeriod;
        // if its more than an hour
         if (totalMinutes >=60){
             //add an hour
        hour+=100;
        // reset minutes to less than an hour
        totalMinutes-=60;
}
         // add the remaining minutes to the hour
       lunchEnd = hour + totalMinutes;
        }
        return lunchEnd;
    }
        
    static public int changeToTime(int n){
        int min=n%100;
        int hour = n-min;
        while (min>=60){
            hour+=100;
            min-=60;
            
        }
        n= hour+min;
        return n;
    }
    
    static public void nextDay(){
        dayCounter++;
    }
    
    public static void lunchAlgo(String user, int userStart, int userEnd) {
        ResourceIterator<Node> list = DataManipulation.getAllNodes();
        while (list.hasNext()) {
            Node s = list.next();
            if(s.getProperty("name").equals(user)){
                continue;
            }
            int start = (int) s.getProperty("avgLunchStart");
            int end = (int) s.getProperty("avgLunchEnd");
            String name = (String) s.getProperty("name");
            // if start within range of user's period
            if (start >= userStart && start <= userEnd) {
                System.out.println(name + " at " + start);
                //if friends:
                if (DataManipulation.isFriendsWith(user, name)) {
                    Relationship relay = DataManipulation.getRelationship(name, user, Sociopath.Rels.IS_FRIENDS_WITH);
                    DataManipulation.incrementRep(relay, 1);
                }
                //if not friends: create new relationship
                if (!DataManipulation.isFriendsWith(user, name)) {
                    DataManipulation.friendTo(user, name, 0);
                    DataManipulation.friendTo(name, user, 1);
                    System.out.println(user+" now knows "+ name+"!");
                }
            }
            //if end within users period
            if (end >= userStart && end <= userEnd) {
                System.out.println(name + " at " + end);
                //if friends:
                if (DataManipulation.isFriendsWith(user, name)) {
                    Relationship relay = DataManipulation.getRelationship(name, user, Sociopath.Rels.IS_FRIENDS_WITH);
                    DataManipulation.incrementRep(relay, 1);
                }
                //if not friends: create new relationship
                if (!DataManipulation.isFriendsWith(user, name)) {
                    DataManipulation.friendTo(user, name, 0);
                    DataManipulation.friendTo(name, user, 1);
                    System.out.println(user+" now knows "+ name+"!");

                }
            }

        }
        System.out.println("\nEach person "+ user +" had lunch with has increased "+ user+ "'s reputation towards them by!");
    }
}
