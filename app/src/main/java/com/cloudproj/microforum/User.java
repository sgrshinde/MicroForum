package com.cloudproj.microforum;

/**
 * Created by Sagar Shinde on 12/4/2015.
 */
public class User {
    String fName;
    String lName;
    String email;
    String ID;

    public User(String fullname, String email, String ID){

        String[] names = fullname.split(" ",2);
        this.fName = names[0];
        this.lName = names[1];
        this.email = email;
        this.ID = ID;

    }

}
