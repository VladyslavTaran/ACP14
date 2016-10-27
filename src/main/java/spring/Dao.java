package spring;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by Vlad on 27.10.2016.
 */

@Component
public class Dao implements IDao{

    @Value("some value")
    private String name;
    @Value("223")
    private int id;

    public Dao() {
    }

    @Override
    public String method(){
        return name + " " + id;
    }

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
}
