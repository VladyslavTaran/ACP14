package spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Vlad on 27.10.2016.
 */

@Component
public class View {

    @Autowired
    private IService service;

    public View() {
    }

    public IService getService() {
        return service;
    }

    public void setService(IService service) {
        this.service = service;
    }

    public void show(){
        System.out.println(service.doService());
    }

}
