package db;

import java.io.IOException;

/**
 * Created by Vlad on 12.10.2016.
 */
public class RunApp {
    public static void main(String[] args) throws IOException {
        restoreDB.restoreDB();
    }
}

class restoreDB {
    public static final String PWD = "root";
    public static final String USER = "root";
    public static final String DB_TO_RESTORE = "homework";
    public static final String BKP_PATH = DB_TO_RESTORE + "_bkp.sql";

    public static void restoreDB(){
        try {
            String command = "mysqldump -u" + USER + " -p" + PWD + " " + DB_TO_RESTORE + " < " + BKP_PATH;
            Process runtimeProcess = Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}