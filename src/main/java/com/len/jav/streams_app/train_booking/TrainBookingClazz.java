package com.len.jav.streams_app.train_booking;


import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TrainBookingClazz {

    private List<UserDetails> bookedList = new ArrayList<UserDetails>();
    private List<UserDetails> waitingList = new LinkedList<UserDetails>();
    private int seatBooked = bookedList.size();

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
        return String.valueOf(rand.nextInt(500));
    }

    private void adjustedBooking(List<UserDetails> users, int availableSeats) {

//        users.stream().

    }


    public List<UserDetails> book(List<UserDetails> user) {

        int seatsAvailable = Consonats.TRAIN_SEATS - bookedList.size();
        AtomicInteger seatNumber = new AtomicInteger(bookedList.size() + 1);
        AtomicInteger wlNumber = new AtomicInteger(waitingList.size() + 1);
        if (seatsAvailable >= user.size()){

            user = user.stream().map(userDetails ->
                    this.markedAsBooking(userDetails, seatNumber.getAndIncrement())).collect(Collectors.toList());
            bookedList.addAll(user);
            return user;
        } else if (seatsAvailable != 0){
//            System.out.println("Only "+seatsAvailable+" seats available \n Please enter to confirm :- ");
//            System.out.println("1. book 2. cancel ");
//            int confirmation = MainClazz.getInput().nextInt();
//            if (confirmation == 1){
                List<UserDetails> finalUser = user;
                List<UserDetails> confirmBookList = IntStream.range(0, seatsAvailable )
                        .mapToObj(finalUser::get).toList();
                user = book(confirmBookList);

                List<UserDetails> waitingLists = new ArrayList<>(IntStream.range(seatsAvailable , finalUser.size()).
                        mapToObj(i -> {
                            return markedAsWaiting(finalUser.get(i), wlNumber.getAndIncrement());
                        }).toList());
                waitingList.addAll(waitingLists);
                user.addAll(waitingList);
                return user;
//            }
        } else {
            return user.stream().map(userDetails -> this.markedAsWaiting(userDetails, wlNumber.getAndIncrement())).collect(Collectors.toList());
        }
    }

    public void getBookedList() {
        bookedList.stream().forEach( userDetails -> {
            System.out.println("Name : "+userDetails.getName() );
            System.out.println("age : "+userDetails.getAge() );
            System.out.println("pnr : "+userDetails.getPnrNumber() );
            System.out.println("seatNumber : "+userDetails.getSeatNo() );
        });
    }

    public void getWaitingList() {
        waitingList.stream().forEach( userDetails -> {
            System.out.println("Name : "+userDetails.getName() );
            System.out.println("age : "+userDetails.getAge() );
            System.out.println("waitingList : "+userDetails.getWaitingList());
        });
    }

    public void cancelTicket(String pnr){
        Optional<UserDetails> cancelPassenger = bookedList.stream().filter(userDetails -> userDetails.getPnrNumber().equals(pnr)).findFirst();
        if (cancelPassenger.isPresent()){

        } else {
            cancelPassenger = waitingList.stream().filter(userDetails -> userDetails.getPnrNumber().equals(pnr)).findFirst();
        }
    }

    private UserDetails markedAsBooking(UserDetails users, int seatNumber) {
        users.setStatus("booked");
        users.setPnrNumber(generatPnr());
        users.setSeatNo(seatNumber);
        return users;
    }

    private UserDetails markedAsWaiting(UserDetails user, int waitingListNumber, boolean cancelTickets) {

        int passengerBoardingPoint = user.getStartingPoint();
        int passengerDestination = user.getEndingPoint();
        Optional<UserDetails> cancelPassenger = bookedList.stream().filter(userDetails -> {
            if (cancelTickets) {
                return this.preCheckAvailability(userDetails.getStartingPoint(), userDetails.getEndingPoint(),
                        passengerBoardingPoint, passengerDestination, userDetails.isSplitedSeat());
            }else
                return postCheckAvailability(userDetails.getStartingPoint(), userDetails.getEndingPoint(),
                        passengerBoardingPoint, passengerDestination, userDetails.isSplitedSeat());
        }).findFirst();
        if (cancelPassenger.isPresent()){
            cancelPassenger.filter(seatExists -> bookedList.);
        }
        user.setStatus("waiting");
        user.setWaitingList(waitingListNumber);
        return user;
    }

    private
    private boolean postCheckAvailability(int boardingPoint, int destination,
                                          int passengerBoardingPoint, int passengerDestination, boolean splitedSeat) {

        if (!splitedSeat) {
            return (destination <= passengerBoardingPoint || passengerDestination <= boardingPoint);
        }
        return false;
    }

    private boolean preCheckAvailability(int boardingPoint, int destination,
                                         int passengerBoardingPoint, int passengerDestination, boolean splitedSeat) {

        if (splitedSeat) {
            return boardingPoint <= passengerBoardingPoint && destination >= passengerDestination;
        } else
            return true;
    }

}
