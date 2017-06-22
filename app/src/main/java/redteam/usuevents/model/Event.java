package redteam.usuevents.model;

import android.net.Uri;

import java.util.UUID;

/**
 * Created by Admin on 6/21/2017.
 */

public class Event {
    private UUID id;
    private String title;
    private String description;
    private String latVal;
    private String longVal;
    private int voteCt;
    private Uri imageUri;
    private String topic;
    private long startTime;
}
