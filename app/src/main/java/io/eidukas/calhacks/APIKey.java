package io.eidukas.calhacks;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by solmoms on 11/12/2016.
 */

public class APIKey {
    public static final File API_FILE = new File("\\app\\libs\\APIKey");

    public static String getAPIKey() {
        Scanner scanner = null;
        try {
            scanner = new Scanner(API_FILE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        StringBuilder fileString = new StringBuilder();
        while (scanner.hasNext()) {
            fileString.append(scanner.nextLine() + "\n");
        }
        return fileString.toString();
    }
}
