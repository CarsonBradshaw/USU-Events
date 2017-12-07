package redteam.usuevents.model;

/**
 * Created by Admin on 12/6/2017.
 */

public class Topic {
    private String topic;
    private int numActiveEvents;
    private int numSubscribers;
    private String imageUri;
    private boolean subscribed;

    public boolean isSubscribed() {
        return subscribed;
    }

    public void setSubscribed(boolean subscribed) {
        this.subscribed = subscribed;
    }

    public Topic(String topic, int numActiveEvents, int numSubscribers, String imageUri) {
        this.topic = topic;
        this.numActiveEvents = numActiveEvents;
        this.numSubscribers = numSubscribers;
        this.imageUri = imageUri;
    }

    public Topic(){

    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public int getNumActiveEvents() {
        return numActiveEvents;
    }

    public void setNumActiveEvents(int numActiveEvents) {
        this.numActiveEvents = numActiveEvents;
    }

    public int getNumSubscribers() {
        return numSubscribers;
    }

    public void setNumSubscribers(int numSubscribers) {
        this.numSubscribers = numSubscribers;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}
