package redteam.usuevents.model;

import android.net.Uri;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Admin on 6/21/2017.
 */

public class Event {

    private String beginDateTime;
    private String category;
    private String description;
    private String endDateTime;
    private String eventId;
    private String imageUri;
    private Double latitude;
    private String location;
    private Double longitude;
    private int numberInterested;
    private String topic;
    private String title;

    public Event(){

    }

    public Event(String beginDateTime, String category, String description, String endDateTime,
                 String eventId, String imageUri, Double latitude, String location, Double longitude,
                 int numberInterested, String topic, String title) {

        this.beginDateTime = beginDateTime;
        this.category = category;
        this.description = description;
        this.endDateTime = endDateTime;
        this.eventId = eventId;
        this.imageUri = imageUri;
        this.latitude = latitude;
        this.location = location;
        this.longitude = longitude;
        this.numberInterested = numberInterested;
        this.topic = topic;
        this.title = title;
    }

    public String getBeginDateTime() {
        return beginDateTime;
    }

    public void setBeginDateTime(String beginDateTime) {
        this.beginDateTime = beginDateTime;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(String endDateTime) {
        this.endDateTime = endDateTime;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public int getNumberInterested() {
        return numberInterested;
    }

    public void setNumberInterested(int numberInterested) {
        this.numberInterested = numberInterested;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
