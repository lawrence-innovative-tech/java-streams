package com.len.jav.streams_app.train_booking;

import java.util.Scanner;

public class MainClazz {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {


        TrainBookingClazz trainBookingClazz = new TrainBookingClazz();
        boolean flag = true;

        while (flag) {
            System.out.println("Welcome to the TrainBooking System");
            System.out.println("Please enter which is u want to be perform");
            System.out.println("1. Book train \n2. Check status \n3. Exit");
            int choice = MainClazz.sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("case 1");
                    break;
                default:
                    System.out.println("exit");
                    System.exit(0);
            }

        }

    }

    public void getUserDetails() {

    }
}
