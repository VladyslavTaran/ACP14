package model;

import javax.persistence.*;

/**
 * Created by Vlad on 04.10.2016.
 */

@MappedSuperclass
public class Inherit {
    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "active")
    private boolean active;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
