package com.shoppy.com.utils;

import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;

public class AllureReportHelper {

    private AllureReportHelper() {

    }

    public static void cleanAllureReport() {
        try {
            FileUtils.deleteDirectory(new File("target/allure-results"));
        } catch (IOException e) {
            System.out.println("Allure Report Already Cleaned");
        }
    }
}