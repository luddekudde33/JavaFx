package com.example.demo2;

public class Staff {
    private final int staffID;
    private final String fullName;
    private final String email;
    private final String phoneNr;

    public Staff(int staffID, String fullName, String email, String phoneNr) {
        this.staffID  = staffID;
        this.fullName = fullName;
        this.email    = email;
        this.phoneNr  = phoneNr;
    }

    public int    getStaffID()  { return staffID; }
    public String getFullName() { return fullName; }
    public String getEmail()    { return email; }
    public String getPhoneNr()  { return phoneNr; }
}
