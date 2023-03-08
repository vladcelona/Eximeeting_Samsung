package com.application.vladcelona.eximeeting_samsung.data_classes;

import java.util.Date;

public class Event {

    public String title;
    public Date fromDate; public Date toDate;
    public String location;
    public int eventStatus;

    public Event(String title, Date fromDate, Date toDate, String location, int eventStatus) {
        this.title = title;
        this.fromDate = fromDate; this.toDate = toDate;
        this.location = location;
        this.eventStatus = eventStatus;
    }
}
