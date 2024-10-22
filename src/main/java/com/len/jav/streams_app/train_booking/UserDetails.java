package com.len.jav.streams_app.train_booking;

public class UserDetails {

    private String name;
    private int age;
    private String startingPoint;
    private String endingPoint ;

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

    public UserDetails(String name, int age, String startingPoint, String endingPoint) {
        this.name = name;
        this.age = age;
        this.startingPoint = startingPoint;
        this.endingPoint = endingPoint;
    }

    public UserDetails(String status, String pnrNumber) {
        this.status = status;
        this.pnrNumber = pnrNumber;
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
    public String getStartingPoint() {
        return startingPoint;
    }
    public void setStartingPoint(String startingPoint) {
        this.startingPoint = startingPoint;
    }
    public String getEndingPoint() {
        return endingPoint;
    }

    public void setEndingPoint(String endingPoint) {
        this.endingPoint = endingPoint;
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
}
