package com.len.jav.streams_app.train_booking;

public class UserDetails {

    private String name;
    private int age;
    private String startingPoint;
    private String endingPoint ;
    private String status;
    private String pnrNumber;

    public UserDetails() {}

    public UserDetails(String name, int age, String startingPoint, String endingPoint) {
        this.name = name;
        this.age = age;
        this.startingPoint = startingPoint;
        this.endingPoint = endingPoint;
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

}
