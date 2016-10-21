package model;

import javax.persistence.*;

/**
 * Created by Vlad on 04.10.2016.
 */

@MappedSuperclass
public class Inherit {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "active", columnDefinition = "INT(1)")
    private boolean active;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
