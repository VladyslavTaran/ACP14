package spring.test;

import javafx.application.Application;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import spring.IService;
import spring.View;

/**
 * Created by Vlad on 27.10.2016.
 */
public class InitContext {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("app-context.xml");
        View view = (View)applicationContext.getBean("view");
        IService service = applicationContext.getBean(IService.class);

        view.show();
    }
}
