package me.devOwner.association;

import java.util.List;

public class Study {
    private User owner;
    private List<User> attenders;

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<User> getAttenders() {
        return attenders;
    }

    public void setAttenders(List<User> attenders) {
        this.attenders = attenders;
    }

    public void hello() {
        getOwner().getMyStudy().forEach(study -> study.getOwner());
    }
}
