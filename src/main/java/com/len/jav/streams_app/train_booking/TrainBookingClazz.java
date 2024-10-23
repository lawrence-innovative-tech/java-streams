package com.len.jav.streams_app.train_booking;


import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TrainBookingClazz {

    private List<UserDetails> bookedList = new ArrayList<UserDetails>();
    private List<UserDetails> waitingList = new LinkedList<UserDetails>();
    private int seatBooked = bookedList.size();


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

    public List<UserDetails> book(List<UserDetails> user) {

        int seatsAvailable = Consonats.TRAIN_SEATS - bookedList.size();
        if (seatsAvailable >= user.size()){
            AtomicInteger seatNumber = new AtomicInteger(bookedList.size() + 1);
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

                List<UserDetails> waitingLists = IntStream.range(seatsAvailable , finalUser.size()).
                        mapToObj(finalUser::get).toList();
                waitingLists = markedAsWaitingOrBooking(waitingLists);
                user.addAll(waitingLists);
                return user;
//            }
        } else {
//            return user.stream().map(userDetails -> this.markedAsWaiting(userDetails, wlNumber.getAndIncrement())).collect(Collectors.toList());
            return markedAsWaitingOrBooking(user);
        }
    }

    public void getBookedList() {
        bookedList.stream().forEach(this::printPassenger);
    }

    private void printPassenger(UserDetails userDetails){
        System.out.println("Name : "+userDetails.getName() );
        System.out.println("age : "+userDetails.getAge() );
        System.out.println("pnr : "+userDetails.getPnrNumber() );
        System.out.println("seatNumber : "+userDetails.getSeatNo() );
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

    public void cancelTickets(){
        System.out.print("Enter PNR Number :");
        String pnr = MainClazz.getInput().next();
        Optional<UserDetails> userDetails = bookedList.stream()
                .filter(passengers -> passengers.getPnrNumber().equals(pnr))
                .findFirst();
        UserDetails passenger = new UserDetails();
        if (userDetails.isEmpty()){
            userDetails = waitingList.stream()
                    .filter(passengers -> passengers.getPnrNumber().equals(pnr))
                    .findFirst();
            if (userDetails.isPresent()){
                passenger = chechAvailability(userDetails.get(), true, true);
            }
        }else
            passenger = chechAvailability(userDetails.get(), true, false);
        System.out.printf(pnr +" successfully cancelled");
        if (Objects.nonNull(passenger)){
            System.out.printf("Add waiting list passenger");
            printPassenger(passenger);
        }
    }

    private UserDetails markedAsBooking(UserDetails users, int seatNumber) {
        users.setStatus("booked");
        users.setPnrNumber(generatPnr());
        users.setSeatNo(seatNumber);
        return users;
    }

    private List<UserDetails> markedAsWaitingOrBooking(List<UserDetails> users){
        List<UserDetails> addBookingList = new ArrayList<>();
        List<UserDetails> addWaitingList = new ArrayList<>();
        int waitingListCount = waitingList.size();
        for (UserDetails userDetails: users){
            UserDetails passengers = chechAvailability(userDetails, false, false);
            if (Objects.nonNull(passengers)) {
                passengers.setStatus("booked");
                addBookingList.add(passengers);
            }
            else {
                userDetails.setWaitingList(++waitingListCount);
                addWaitingList.add(userDetails);
            }
        }
//        split and store the bookings
        addBookingList.addAll(addWaitingList);
        return addBookingList;
    }

    private UserDetails chechAvailability(UserDetails user, boolean cancelTickets, boolean wlPassenger) {

        int passengerBoardingPoint = user.getStartingPoint();
        int passengerDestination = user.getEndingPoint();
        if (cancelTickets){
            if (wlPassenger)
                removePassenger(user, wlPassenger);
            else {
                boolean splitSeat = user.isSplitedSeat();
                Optional<UserDetails> alternetWaitingPassenger =
                        cancelTicket(passengerBoardingPoint,passengerDestination, splitSeat);
                if (alternetWaitingPassenger.isPresent()){
                    UserDetails alternetPassenger = alternetWaitingPassenger.get();
                    alternetPassenger.setSeatNo(user.getSeatNo());
                    alternetPassenger.setPnrNumber(generatPnr());
                    alternetPassenger.setSplitedSeat(splitSeat);
                    removePassenger(user, wlPassenger);
                    return alternetPassenger;
                }
            }
        } else {
            Optional<UserDetails> assignedPassenger =
                    waitingList(passengerBoardingPoint,passengerDestination, false);
            if (assignedPassenger.isPresent()){
                assignedPassenger.get().setSplitedSeat(true);
                user.setPnrNumber(generatPnr());
                user.setSplitedSeat(true);
                user.setSeatNo(assignedPassenger.get().getSeatNo());
                return user;
            }
        }
//        Optional<UserDetails> cancelPassenger = bookedList.stream().filter(userDetails -> {
//            if (cancelTickets) {
//
//                return this.preCheckAvailability(userDetails.getStartingPoint(), userDetails.getEndingPoint(),
//                        passengerBoardingPoint, passengerDestination, userDetails.isSplitedSeat());
//            }else {
//                cancelPassengerSeatNo = userDetails.getSeatNo();
//                return postCheckAvailability(userDetails.getStartingPoint(), userDetails.getEndingPoint(),
//                        passengerBoardingPoint, passengerDestination, userDetails.isSplitedSeat());
//            }
//        }).findFirst();
//        if (cancelPassenger.isPresent()){
//             = cancelPassenger.get().getSeatNo();
//            List<UserDetails> passengersList = bookedList.stream()
//                    .filter(passenger -> passenger.getSeatNo() == cancelPassengerSeatNo)
//                    .collect(Collectors.toList());
//

//             = cancelPassenger.stream().map(seatExists -> bookedList
//                            .stream().
//                            filter(passengerList ->
//                    passengerList.getSeatNo() == seatExists.getSeatNo()).collect(Collectors.toList()))
//                    .collect(Collectors.toList());

//        }
//        user.setStatus("waiting");
//        user.setWaitingList(waitingListNumber);
        return null;
    }

    private Optional<UserDetails> cancelTicket(int passengerBoardingPoint,
                                               int passengerDestination, boolean splitedSeat){
        return waitingList.stream().filter(userDetails -> {
            return this.preCheckAvailability(userDetails.getStartingPoint(), userDetails.getEndingPoint(),
                    passengerBoardingPoint, passengerDestination, splitedSeat);
        }).findFirst();
    }

    private Optional<UserDetails> waitingList(int passengerBoardingPoint,
                                               int passengerDestination, boolean splitedSeat){
        return bookedList.stream().filter(userDetails -> {
            return this.postCheckAvailability(userDetails.getStartingPoint(), userDetails.getEndingPoint(),
                    passengerBoardingPoint, passengerDestination, splitedSeat);
        }).findFirst();
    }
    private boolean removePassenger(UserDetails userDetails, boolean wlPassenger){
        if (Objects.nonNull(userDetails.getPnrNumber())){
            return bookedList.remove(userDetails);
        }
        return waitingList.remove(userDetails);
    }


    private boolean postCheckAvailability(int boardingPoint, int destination,
                                          int passengerBoardingPoint, int passengerDestination, boolean splitedSeat) {

        if (!splitedSeat) {
            return (destination <= passengerBoardingPoint || passengerDestination <= boardingPoint);
        }
        return false;
    }

    private boolean preCheckAvailability(int boardingPoint, int destination,
                                         int passengerBoardingPoint, int passengerDestination, boolean splitedSeat) {
        if (splitedSeat)
            return boardingPoint <= passengerBoardingPoint && destination >= passengerDestination;
        return true;
    }

}
