package redteam.usuevents.models;

public class EventModel {

    private String category_id;
    private String event_id;
    private String title;
    private String description;
    private String date_time;
    private String address;
    private String lat;
    private String lng;
    private String voteCt;

    public EventModel(String category_id, String event_id, String title, String description, String date_time, String address, String lat, String lng, String voteCt) {
        this.setCategory_id(category_id);
        this.setEvent_id(event_id);
        this.setTitle(title);
        this.setDescription(description);
        this.setDate_time(date_time);
        this.setAddress(address);
        this.setLat(lat);
        this.setLng(lng);
        this.setVoteCt(voteCt);
    }

    public String getVoteCt() {
        return voteCt;
    }

    public void setVoteCt(String voteCt) {
        this.voteCt = voteCt;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getEvent_id() {
        return event_id;
    }

    public void setEvent_id(String event_id) {
        this.event_id = event_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
}
