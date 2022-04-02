package util;

public final class TimeUtil {

    private TimeUtil(){

    }

    public static String timeParse(long duration) {
        String time = "";

        long minute = duration / 60000;
        long seconds = duration % 60000;

        long second = Math.round((float) seconds / 1000);

        if (minute < 10) {
            time += "0";
        }
        time += minute + "分";

        if (second < 10) {
            time += "0";
        }
        time += second + "秒";

        return time;
    }

}
