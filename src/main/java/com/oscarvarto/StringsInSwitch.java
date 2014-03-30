package com.oscarvarto;

/**
 * Created by oscarvarto on 3/29/14.
 */
public class StringsInSwitch {
    public static String dayOfWeekInFrench(String dayOfWeek) {
        switch (dayOfWeek) {
            case "Sunday": return "Dimanche";
            case "Monday": return "Lundi";
            case "Tuesday": return "Mardi";
            case "Wednesday": return "Mercredi";
            case "Thursday": return "Jeudi";
            case "Friday": return "Vendredi";
            case "Saturday": return "Samedi";
            default: return "Error: '"+ dayOfWeek +"' is not a day of the week";
        }
    }
}
