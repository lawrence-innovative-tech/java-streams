package com.len.jav.streams_app.train_booking;

import org.apache.catalina.User;

import java.util.*;

public class MainClazz {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {


        TrainBookingClazz trainBookingClazz = new TrainBookingClazz();

        while (true) {
            System.out.println("Welcome to the TrainBooking System");
            System.out.println("Please enter which is u want to be perform");
            System.out.println("1. Book Ticket \n2. Confirm List \n3. Waiting List \n4. Book Ticket \n5. exit");
            System.out.print("Enter your choice -> " );
            int choice = MainClazz.sc.nextInt();

            switch (choice) {
                case 1:
                    List<UserDetails> userDetails = getUserDetails();
                    if (Objects.nonNull(userDetails)) {
                        trainBookingClazz.book(userDetails);
                        printingBookingList(userDetails);
                        System.out.println("Booking confirmed ..... ");
                    }
                    printLine();
                    break;
                case 2:
                    trainBookingClazz.getBookedList();
                    printLine();
                    break;
                case 3:
                    trainBookingClazz.getWaitingList();
                    printLine();
                    break;
                case 4:
                    trainBookingClazz.cancelTickets();
                    printLine();
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
        if (seats > Consonats.USER_DETAILS_COUNT ) {
            System.out.println("Maximum booking allowed " + Consonats.USER_DETAILS_COUNT );
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

            int boardingPoint = sc.next().charAt(0);
            if (!(boardingPoint <= 65 || boardingPoint >= 70)) {
                throw new RuntimeException("Invalid boarding point " + (char) boardingPoint);
            }
            user.setStartingPoint((char) boardingPoint);
            int destination = sc.next().charAt(0);
            System.out.print("Please enter ending point : ");
            if (!(destination <= 66 || destination >= 70)) {
                throw new RuntimeException("Invalid destination point " + (char)  destination);
            }
            user.setStartingPoint((char) destination);
            users.add(user);
        }
        return users;
    }

    public static Scanner getInput() {
        return sc;
    }

    private static void printingBookingList(List<UserDetails> userDetails) {
        for (UserDetails user : userDetails) {
            if (Objects.nonNull(user)) {
                System.out.println("Name: " + user.getName());
                System.out.println("Age: " + user.getAge());
                System.out.println("Starting point: " + user.getStartingPoint());
                System.out.println("Ending point: " + user.getEndingPoint());
                if (Objects.nonNull(user.getPnrNumber())) {
                    System.out.println("PNR Number: " + user.getPnrNumber());
                }
                if (user.getSeatNo() != 0) {
                    System.out.println("Seat Number: " + user.getSeatNo());
                }
                if (user.getWaitingList() != 0){
                    System.out.println("Waiting list number :"+user.getWaitingList());
                }
            }
            System.out.println("________________________________________________________");
        }
    }
    public static void printLine(){
        System.out.println();
        System.out.println("---------------------------------------------------------------");
        System.out.println();
    }
}
