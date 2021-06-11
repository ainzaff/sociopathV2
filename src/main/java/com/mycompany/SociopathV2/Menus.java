/*menus
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.SociopathV2;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author lenovo
 */
public class Menus {

    public static Scanner input = new Scanner(System.in);
    
    public static void mainMenu() {
        Scanner in = new Scanner(System.in);
        try {
            int choice = 0;
            while (choice != 1 || choice != 2 || choice != 3) {
                System.out.println("\nWELCOME TO SOCIOPATH!");
                System.out.println("What do you want to do?\n");
                System.out.println("1. Check Students");
                System.out.println("2. Events");
                System.out.println("3. Exit\n");
                choice = in.nextInt();
                switch (choice) {
                    case 1:
                        studentMenu();
                        break;
                    case 2:
                        eventMenu();
                        break;
                    case 3:
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Please type in a valid number option");
                }
            }
        } catch (InputMismatchException ex) {
            System.out.println("Please enter an integer!");
            mainMenu();
        }
    }

    public static void studentMenu() {
        System.out.println("\nDisplaying students . . .\n");
        StudentMethods.displayStudents();
        System.out.println("\nWhich student's profile would you like to check?");
        System.out.println("Type exit to go back to main menu\n");
        Sociopath.input.nextLine();
        String name = Sociopath.input.nextLine();
        StudentMethods.displayProfile(name);
    }

    public static void eventMenu() {
        int choice;
        System.out.println("\nWhich event happened? \n");
        System.out.println("1. Teaching a stranger to solve lab questions ");
        System.out.println("2. Chit-chat ");
        System.out.println("3. Your road to glory ");
        System.out.println("4. Arranging books ");
        System.out.println("5. Meet your crush ");
        System.out.println("6. Friendship ");
        System.out.println("7. Social Dynamics Events\n");
        choice = Sociopath.input.nextInt();
        Events.eventSelector(choice);
    }

}
