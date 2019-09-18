package mx.com.dgom.hm.wourmeetz_comensal.utils;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils {
    public static final Locale LOCALE_MX = new Locale("es", "MX");

    public static String convertDateToFullString(String str) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", LOCALE_MX);
        try {
            return new SimpleDateFormat("EEE d MMM", LOCALE_MX).format(simpleDateFormat.parse(str));
        } catch (ParseException str2) {
            str2.printStackTrace();
            return "";
        }
    }

    public static String convertDateToShortString(String str) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd", LOCALE_MX).format(new SimpleDateFormat("EEEE, MMM dd", LOCALE_MX).parse(str));
        } catch (ParseException str2) {
            str2.printStackTrace();
            return "";
        }
    }

    public static boolean compareFullDate(String fecha1, String fecha2){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss",LOCALE_MX);
        try {
            Date fch1 = sdf.parse(fecha1);
            Date fch2 = sdf.parse(fecha1);

            Calendar cal1 = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();
            cal1.setTime(fch1);
            cal2.setTime(fch2);

            boolean sameDay = cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR) && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)
                    &&
                    cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);

            return sameDay;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }

    }


    public static boolean compareWithToday(String str, String str2) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", LOCALE_MX);
        Calendar instance = Calendar.getInstance();
        //TODO
        return false;
    }
}

