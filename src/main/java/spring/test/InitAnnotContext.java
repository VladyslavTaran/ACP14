package spring.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.View;

/**
 * Created by Vlad on 27.10.2016.
 */
public class InitAnnotContext {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext("spring");
        View view = applicationContext.getBean(View.class);

        view.show();
    }
}
