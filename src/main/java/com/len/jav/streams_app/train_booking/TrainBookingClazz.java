package com.len.jav.streams_app.train_booking;


import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TrainBookingClazz {

    private List<UserDetails> bookedList = new ArrayList<UserDetails>();
    private List<UserDetails> waitingList = new LinkedList<UserDetails>();

    public List<UserDetails> trainBooking(List<UserDetails> users){
        if (bookedList.size() < Consonats.TRAIN_SEATS ){
            int availableSeats = (Consonats.TRAIN_SEATS - 1)  - bookedList.size();
            if (availableSeats < users.size()){
                adjustedBooking( users, availableSeats);
            }
            markedAsBooked(users);
            bookedList.addAll(users);
        } else {
            markedAsWaitingList(users);
            waitingList.addAll(users);
        }
        return users;
    }

    private void markedAsBooked(List<UserDetails> users) { // , int seatNumber
        users.forEach(userDetails -> {
            userDetails.setStatus("booked");
            userDetails.setPnrNumber(generatPnr());
        });
    }

    private void markedAsWaitingList(List<UserDetails> users) {
        AtomicInteger waitingListNumber = new AtomicInteger(1 + waitingList.size());
        users.forEach(userDetails -> {
            userDetails.setStatus("waiting");
            userDetails.setWaitingList(waitingListNumber.get());
            waitingListNumber.getAndIncrement();
        });
    }

    private String generatPnr(){
        Random rand = new Random();
        rand.setSeed(System.currentTimeMillis());
        return String.valueOf(rand.nextInt(10));
    }

    private void adjustedBooking(List<UserDetails> users, int availableSeats) {

//        users.stream().

    }


    public void book(List<UserDetails> user) {

        int seatsAvailable = Consonats.TRAIN_SEATS - (bookedList.size() + 1);
        if (seatsAvailable >= user.size()){
            AtomicInteger seatNumber = new AtomicInteger(bookedList.size() + 1);
            user = user.stream().map(userDetails ->
                    this.markedAsBooking(userDetails, seatNumber.getAndIncrement())).collect(Collectors.toList());
            bookedList.addAll(user);
        } else if (seatsAvailable != 0){
            int needSeats = user.size() - seatsAvailable;
            if ( needSeats != 0) {
                System.out.println("Only "+needSeats+" seats available \n Please enter to confirm :- ");
                System.out.println("1. book 2. cancel ");
                int confirmation = MainClazz.getInput().nextInt();
                if (confirmation == 1){
                    List<UserDetails> finalUser = user;
                    List<UserDetails> confirmBookList = IntStream.range(0, needSeats)
                            .mapToObj(finalUser::get).toList();
                    book(confirmBookList);
                    List<UserDetails> waitingList = new ArrayList<>(IntStream.rangeClosed(needSeats, user.size()).
                            mapToObj(i -> {
                                UserDetails userDetails = finalUser.get(i);
                                userDetails.setStatus("waiting");
                                return userDetails;
                            }).toList());
                    waitingList.addAll(waitingList);
                }
                return;
            }
        }
    }

    private UserDetails markedAsBooking(UserDetails users, int seatNumber) {
        users.setStatus("booked");
        users.setPnrNumber(generatPnr());
        users.setSeatNo(seatNumber);
        return users;
    }

    private UserDetails markedAsWaiting(UserDetails users, int seatNumber) {
        users.setStatus("waiting");
        return users;
    }

}
