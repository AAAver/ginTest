package runner;

import org.testng.TestNG;


import java.util.ArrayList;
import java.util.List;

public class TestThread extends Thread {

    private Class[] testId;
    private TestNG test;

    public boolean isWait() {
        return wait;
    }

    public void setWait(boolean wait) {
        this.wait = wait;
    }

    private boolean wait = false;

    public TestThread(Class[] testId){
        this.testId = testId;
    }



        @Override
        public void run() {
            test = new TestNG();
            test.setTestClasses(testId);
            test.run();

        }


}
