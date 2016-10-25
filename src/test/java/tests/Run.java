package tests;

/**
 * Created by Vlad on 23.10.2016.
 */
public class Run {
    public static void main(String[] args) {
        ServiceTest tests = new ServiceTest();
        tests.init();
        tests.setUp();
        tests.testUpdateStudentInfo();
    }
}
