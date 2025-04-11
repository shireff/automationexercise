package listeners.testng;


import driverFactory.Driver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.IExecutionListener;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utils.AllureReportHelper;
import utils.ScreenshotManager;

import java.io.IOException;
import java.lang.reflect.Field;

import static utils.PropertiesManager.initializeProperties;
import static utils.PropertiesManager.webConfig;


public class TestNGListener implements IExecutionListener, ITestListener {
    private static final Logger logger = LoggerFactory.getLogger(TestNGListener.class);
    private static final String RESET = "\u001B[0m";
    private static final String GREEN = "\u001B[32m";
    private static final String RED = "\u001B[31m";
    private static final String YELLOW = "\u001B[33m";
    private static final String CYAN = "\u001B[36m";
    private static final String BLUE = "\u001B[34m";


    @Override
    public void onExecutionStart() {
        logger.info(CYAN + "🚀 TestNG is starting the execution" + RESET);
        initializeProperties();
       AllureReportHelper.cleanAllureReport();
    }

    @Override
    public void onExecutionFinish() {
        logger.info(CYAN + "📊 Generating Allure Report..." + RESET);

        if (webConfig.getProperty("openAllureReportAfterExecution").equalsIgnoreCase("true")) {
            try {
                logger.info(BLUE + "🔍 Opening Allure Report..." + RESET);
                Runtime.getRuntime().exec("reportGeneration.bat");
            } catch (IOException e) {
                logger.error(RED + "⚠️ Unable to open Allure Report. Please check the batch file or commands." + RESET);
            }
        } else {
            logger.info(YELLOW + "🛑 Allure Report not opened (check 'openAllureReportAfterExecution' config)." + RESET);
        }

        logger.info(GREEN + "✅ TestNG has finished the execution." + RESET);
    }


    @Override
    public void onTestStart(ITestResult result) {
        logger.info(BLUE + "🟢 TestNG is starting the test: " + result.getName() + RESET);
    }

    public void onTestSuccess(ITestResult result) {
        logger.info(GREEN + "✅ TestNG has finished the test successfully: " + result.getName() + RESET);
    }

    public void onTestFailure(ITestResult result) {
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
            logger.error(YELLOW + "⚠️ Failed to get field: " + e.getMessage() + RESET);
        }

        if (driver == null) {
            logger.error(RED + "❌ Driver is not initialized. Cannot take screenshot." + RESET);
            return;
        }

        try {
            ScreenshotManager.takeScreenshot(driver.get(), result.getName());
        } catch (Exception e) {
            logger.error(RED + "⚠️ Failed to take screenshot: " + e.getMessage() + RESET);
        }
    }

    public void onTestSkipped(ITestResult result) {
        logger.info(YELLOW + "⏭️ TestNG has skipped the test: ", result.getName());
    }
}
