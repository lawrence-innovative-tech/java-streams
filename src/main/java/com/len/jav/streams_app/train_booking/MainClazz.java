package com.len.jav.streams_app.train_booking;

import org.apache.catalina.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class MainClazz {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {


        TrainBookingClazz trainBookingClazz = new TrainBookingClazz();

        while (true) {
            System.out.println("Welcome to the TrainBooking System");
            System.out.println("Please enter which is u want to be perform");
            System.out.println("1. Book train \n2. Check status \n3. Exit");
            System.out.print("Enter your choice -> " );
            int choice = MainClazz.sc.nextInt();

            switch (choice) {
                case 1:
                    List<UserDetails> userDetails = getUserDetails();
                    if (Objects.nonNull(userDetails)) {
                        System.out.println("Your TrainBooking processing...");
                        trainBookingClazz.trainBooking(userDetails);

                    }
                    break;
                case 2:
                    System.out.println("case 2");
                    break;
                default:
                    System.out.println("exit");
                    System.exit(0);

            }

        }

    }

    public static List<UserDetails> getUserDetails() {
        System.out.print("Please enter your seats you want to book ? -> " );
        int seats = sc.nextInt() ;
        if (seats >= Consonats.USER_DETAILS_COUNT ) {
            System.out.print("Maximum booking allowed " + Consonats.USER_DETAILS_COUNT );
            return null;
        }
        List<UserDetails> users = new ArrayList<UserDetails>();
        for (int i = 1; i <= seats; i++) {
            System.out.println("Please enter " +i +"'s person details :-");
            UserDetails user = new UserDetails();
            System.out.print("Please enter your name : ");
            user.setName(sc.next());
            System.out.print("Please enter your age : ");
            user.setAge(sc.nextInt());
            System.out.print("Please enter starting point : ");
            user.setStartingPoint(sc.next());
            System.out.print("Please enter ending point : ");
            user.setEndingPoint(sc.next());
            users.add(user);
        }
        return users;
    }

    public static Scanner getInput() {
        return sc;
    }
}
