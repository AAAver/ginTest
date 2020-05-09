package runner;

import org.testng.TestNG;
import tests.training.TestFake;

public class TestRunner {
	static TestNG test;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		test = new TestNG();
		test.setTestClasses(new Class[]{tests.spdDgi.ActMissAct.class});
		test.run();
		System.out.println("Hello World");
	}

}
