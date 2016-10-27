package tests;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Vlad on 28.10.2016.
 */
public class TestAnnotXmlInitContext {

    @Test
    public void initContext(){
        ApplicationContext context = new ClassPathXmlApplicationContext("app-xml-annot-context.xml");
    }

}
