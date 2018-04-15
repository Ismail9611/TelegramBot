package logging;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;


public class LoggingUtil {

    public static void logToFile(String str){
        try {
            str = str + "\n";
            File filename = new File("resources/logs.txt");
            FileUtils.writeStringToFile(filename, str, true);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
