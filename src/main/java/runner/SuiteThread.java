package runner;

import org.testng.TestNG;


import java.util.ArrayList;
import java.util.List;

public class SuiteThread extends Thread{
    TestNG test;
    String suite;

    public SuiteThread(String suite){
        this.suite = suite;
    }

    @Override
    public void run() {
        test = new TestNG();
        List<String> suites = new ArrayList<>();
        suites.add(suite);
        test.setTestSuites(suites);
        test.run();
    }


}
