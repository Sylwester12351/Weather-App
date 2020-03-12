package office;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class TimeUpdate {

    private static int timerUpdate = 1; // minutes
    String text;
    private String timeS;
    private int time;

    public TimeUpdate() {
    }

    public TimeUpdate updateConfTime() {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader("conf"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            assert bufferedReader != null;
            text = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!StringUtils.isBlank(text)) {
            int startIndex = text.indexOf(";TIME={") + 7;
            int endIndex = text.indexOf("};ENDCONF");
            timeS = text.substring(startIndex, endIndex);
            int time = Integer.parseInt(timeS);
            timerUpdate = time;
            System.out.println(time);
        }
        return null;
    }

    public int getTimerUpdate() {
        return timerUpdate;
    }

    public void setTimerUpdate(int timerUpdate) {
        TimeUpdate.timerUpdate = timerUpdate;
    }

    @Override
    public String toString() {
        return "" + timerUpdate;
    }

}
