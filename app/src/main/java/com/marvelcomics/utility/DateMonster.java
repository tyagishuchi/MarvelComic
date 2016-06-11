package com.marvelcomics.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by SHUCHI on 6/11/2016.
 */
public class DateMonster {
    private static final Map<String, String> DATE_FORMAT_REGEXPS = new HashMap<String, String>() {{
        put("^\\d{4}-\\d{1,2}-\\d{1,2}\\s\\w{1}\\s\\d{1,2}:\\d{2}:\\d{2}\\w{1}$", "yyyy-MM-dd'T'HH:mm:ss");
        put("^\\d{4}/\\d{1,2}/\\d{1,2}\\s\\w{1}\\s\\d{1,2}:\\d{2}:\\d{2}.\\d{3}\\w{1}$", "yyyy-MM-dd'T'HH:mm:ss.SSS");
    }};
    public String getDate(String dateString) throws ParseException {
        String pattern ="yyyy-MM-dd'T'HH:mm:ss.SSSZ";
        SimpleDateFormat format=new SimpleDateFormat(pattern);
        Date date = format.parse(dateString);
        String time = new SimpleDateFormat("MMM d y").format(date);
        return time;
    }
    public String getDate(String dateString,String pattern) throws ParseException {
        SimpleDateFormat format=new SimpleDateFormat(pattern);
        Date date = format.parse(dateString);
        String time = new SimpleDateFormat("MMM d").format(date);
        return time;
    }
}
