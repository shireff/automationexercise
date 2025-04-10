package listeners.testng;

import driverFactory.Driver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.IExecutionListener;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utils.ScreenshotManager;

import java.lang.reflect.Field;

import static utils.PropertiesManager.initializeProperties;

public class TestNGListener implements IExecutionListener, ITestListener {
    private static final Logger logger = LoggerFactory.getLogger(TestNGListener.class); // Create logger instance
    private static final String RESET = "\u001B[0m";
    private static final String GREEN = "\u001B[32m";
    private static final String RED = "\u001B[31m";
    private static final String YELLOW = "\u001B[33m";
    private static final String CYAN = "\u001B[36m";
    private static final String BLUE = "\u001B[34m";


    @Override
    public void onExecutionStart() {
        // System.out.println("TestNG is starting the execution");
        logger.info(CYAN + "🚀 TestNG is starting the execution" + RESET);

        initializeProperties();
    }

    @Override
    public void onExecutionFinish() {
        //  System.out.println("TestNG has finished the execution");
        logger.info(GREEN + "🎉 TestNG has finished the execution" + RESET);

    }

    public void onTestStart(ITestResult result) {
        //  System.out.println("TestNG is starting the test" + result.getName());
        logger.info(BLUE + "🟢 TestNG is starting the test: " + result.getName() + RESET);

    }

    public void onTestSuccess(ITestResult result) {
        // System.out.println("TestNG has finished the test successfully" + result.getName());
        logger.info(GREEN + "✅ TestNG has finished the test successfully: " + result.getName() + RESET);

    }

    public void onTestFailure(ITestResult result) {
//        System.out.println("Test Failed...........");
//        System.out.println("Taking Screenshot...........");
        logger.error(RED + "❌ Test Failed..........." + RESET);
        logger.error(RED + "📸 Taking Screenshot..........." + RESET);

        Driver driver = null;
        ThreadLocal<Driver> driverThreadLocal;
        Object currentClass = result.getInstance();
        Field[] fields = result.getTestClass().getRealClass().getDeclaredFields();

        try {
            for (Field field : fields) {
                if (field.getType() == Driver.class) {
                    driver = (Driver) field.get(currentClass);
                }

                if (field.getType() == ThreadLocal.class) {
                    driverThreadLocal = (ThreadLocal<Driver>) field.get(currentClass);
                }
            }
        } catch (IllegalAccessException e) {
            //  System.out.println("Failed to get field: " + e.getMessage());
            logger.error(YELLOW + "⚠️ Failed to get field: " + e.getMessage() + RESET);

        }

        assert driver != null;
        ScreenshotManager.takeScreenshot(driver.get(), result.getName());

    }

    public void onTestSkipped(ITestResult result) {
        //  System.out.println("TestNG has skipped the test" + result.getName());
        logger.info(YELLOW + "⏭️ TestNG has skipped the test: ", result.getName());

    }
}
