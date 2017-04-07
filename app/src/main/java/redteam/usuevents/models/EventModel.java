package redteam.usuevents.models;
import java.io.Serializable;
import java.io.StringReader;

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


}
