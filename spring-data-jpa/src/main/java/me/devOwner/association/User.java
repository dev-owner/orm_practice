package me.devOwner.association;

import java.util.List;

public class User {
    private List<Study> myStudy;

    public List<Study> getMyStudy() {
        return myStudy;
    }

    public void setMyStudy(List<Study> myStudy) {
        this.myStudy = myStudy;
    }
}
