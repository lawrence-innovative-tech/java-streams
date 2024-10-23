package com.len.jav.streams_app.train_booking;

public class UserDetails {

    private String name;
    private int age;
    private char startingPoint;
    private char endingPoint ;
    private boolean splitedSeat;

    public int getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(int seatNo) {
        this.seatNo = seatNo;
    }

    private String status;
    private String pnrNumber;
    private int seatNo;
    private int waitingList;

    public int getWaitingList() {
        return waitingList;
    }

    public void setWaitingList(int waitingList) {
        this.waitingList = waitingList;
    }

    public UserDetails() {}

    public UserDetails(String name, int age, char startingPoint, char endingPoint) {
        this.name = name;
        this.age = age;
        this.startingPoint = startingPoint;
        this.endingPoint = endingPoint;
    }

    public UserDetails(String status, String pnrNumber) {
        this.status = status;
        this.pnrNumber = pnrNumber;
    }

    public char getEndingPoint() {
        return endingPoint;
    }

    public void setEndingPoint(char endingPoint) {
        this.endingPoint = endingPoint;
    }

    public char getStartingPoint() {
        return startingPoint;
    }

    public void setStartingPoint(char startingPoint) {
        this.startingPoint = startingPoint;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPnrNumber() {
        return pnrNumber;
    }

    public void setPnrNumber(String pnrNumber) {
        this.pnrNumber = pnrNumber;
    }

    public boolean isSplitedSeat() {
        return splitedSeat;
    }

    public void setSplitedSeat(boolean splitedSeat) {
        this.splitedSeat = splitedSeat;
    }
}
