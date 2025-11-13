package dev.puzzleshq.solarflare.carutil.utils;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

import java.io.File;


@ExtendWith(TestMain.MyTestWatcher.class)
public class TestMain {

    private String name;

    public final File testDir;
    public final File outDir;

    public TestMain(String name) {
        this.name = name;
        testDir = new File("test/" + this.name);
        outDir = new File(testDir, "out");
    }

    @BeforeEach
    void setUp() {
        testDir.mkdirs();
        outDir.mkdirs();
    }

    @AfterAll
    static void deleteAll(){
        TestUtils.deleteDir(new File("test"));
    }

    static class MyTestWatcher implements TestWatcher, AfterTestExecutionCallback {

        private boolean testFailed = false;

        @Override
        public void testFailed(ExtensionContext context, Throwable cause) {
            testFailed = true;
        }

        @Override
        public void afterTestExecution(ExtensionContext context) {
            Object testInstance = context.getRequiredTestInstance();

            if (testInstance instanceof TestMain) {
                TestMain tm = (TestMain) testInstance;
                File testDir = tm.testDir;
                File outDir = tm.outDir;

                if (!testFailed) {
                    TestUtils.deleteDir(outDir);
                    TestUtils.deleteDir(testDir);
                } else {
                    System.out.println("Skipping cleanup because test failed");
                }
            }

            testFailed = false;
        }
    }
}
