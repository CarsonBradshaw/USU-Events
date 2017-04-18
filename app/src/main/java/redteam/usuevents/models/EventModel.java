package redteam.usuevents.models;
import java.io.Serializable;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EventModel implements Serializable {

    public String event_id;
    public String description;
    public String lat;
    public String lng;
    public Boolean notified;
    public String startDateTime;
    public String title;
    public String topic;
    public String voteCt;


    public EventModel(String description, String lat, String lng, Boolean notified, String startDateTime, String title, String topic, String voteCt) {
        this.description = description;
        this.lat = lat;
        this.lng = lng;
        this.notified = notified;
        this.startDateTime = startDateTime;
        this.title = title;
        this.topic = topic;
        this.voteCt = voteCt;
    }

    public EventModel() {

    }

    public String getEvent_id() {
        return event_id;
    }

    public void setEvent_id(String event_id) {
        this.event_id = event_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public Boolean getNotified() {
        return notified;
    }

    public void setNotified(Boolean notified) {
        this.notified = notified;
    }

    public String getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(String startDateTime) {
        this.startDateTime = startDateTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getVoteCt() {
        return voteCt;
    }

    public void setVoteCt(String voteCt) {
        this.voteCt = voteCt;
    }

    public String getStartDay(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        try {
            date = formatter.parse(startDateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //long startDateTimeMils = date.getTime();

        String finalDay = new SimpleDateFormat("EEEE").format(date);

        if(finalDay != null){
            return finalDay;
        }

        return "";

    }

    public String getStartTime12Hr(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        try {
            date = formatter.parse(startDateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //long startDateTimeMils = date.getTime();

        String finalTime = new SimpleDateFormat("hh:mm a").format(date);

        if(finalTime != null){
            if(finalTime.charAt(0)=='0'){
                finalTime = finalTime.substring(1);
            }
            return finalTime;
        }

        return "";
    }

    public String getStartDateMonthDayYearFormat(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        try {
            date = formatter.parse(startDateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String finalDateMonthDayYear = new SimpleDateFormat("MM-dd-yyyy").format(date);

        if(finalDateMonthDayYear != null){
            if(finalDateMonthDayYear.charAt(0)=='0'){
                finalDateMonthDayYear = finalDateMonthDayYear.substring(1);
            }
            return finalDateMonthDayYear;
        }

        return "";

    }


}
