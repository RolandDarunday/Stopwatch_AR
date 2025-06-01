package com.example.stopwatchar;




import java.util.ArrayList;
import java.util.List;

public class Stopwatch {

    private long startTime = 0;
    private long pauseOffset = 0;
    private boolean isRunning = false;
    private List<String> laps = new ArrayList<>();

    public void start() {
        if (!isRunning) {
            startTime = System.currentTimeMillis() - pauseOffset;
            isRunning = true;
        }
    }

    public void pause() {
        if (isRunning) {
            pauseOffset = System.currentTimeMillis() - startTime;
            isRunning = false;
        }
    }

    public void reset() {
        startTime = 0;
        pauseOffset = 0;
        isRunning = false;
        laps.clear();
    }

    public long getCurrentTime() {
        return isRunning ? System.currentTimeMillis() - startTime : pauseOffset;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void lap(long currentTime) {
        String formatted = formatTime(currentTime);
        laps.add(formatted);
    }

    public List<String> getLaps() {
        return laps;
    }

    public static String formatTime(long millis) {
        int hours = (int) (millis / 3600000);
        int minutes = (int) ((millis % 3600000) / 60000);
        int seconds = (int) ((millis % 60000) / 1000);
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
