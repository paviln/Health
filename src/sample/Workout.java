package sample;

import javafx.beans.property.SimpleStringProperty;

public class Workout
{
    private SimpleStringProperty week;
    private SimpleStringProperty hours;

    public Workout(String week, String hours) {
        this.week = new SimpleStringProperty(week);
        this.hours = new SimpleStringProperty(hours);
    }

    public String getWeek() {
        return week.get();
    }

    public void setWeek(String week) {
        this.week.set(week);
    }

    public String getHours() {
        return hours.get();
    }

    public void setHours(String hours) {
        this.hours.set(hours);
    }
}
