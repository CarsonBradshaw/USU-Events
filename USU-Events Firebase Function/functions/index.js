const functions = require('firebase-functions');
const admin = require('firebase-admin');
const firebase = require('firebase');
admin.initializeApp(functions.config().firebase);

var database = admin.database();
var eventsRef = database.ref('events').orderByChild('startDateTime').limitToFirst(5);



const timeOffSet = 6;

exports.updateUsers = functions.https.onRequest((request, response) =>{
    var ID;
    var eventTime;
    var notifiedBool;

    var dateObj = new Date();
    var curr_date = dateObj.getDate();
    var curr_month = dateObj.getMonth();
    curr_month = curr_month + 1;
    var curr_year = dateObj.getFullYear();
    var curr_min = dateObj.getMinutes();
    var curr_hr= dateObj.getHours();
    var curr_sc= dateObj.getSeconds();
    if(curr_month.toString().length == 1)
    curr_month = '0' + curr_month;      
    if(curr_date.toString().length == 1)
    curr_date = '0' + curr_date;
    if(curr_hr.toString().length == 1)
    curr_hr = '0' + curr_hr;
    if(curr_min.toString().length == 1)
    curr_min = '0' + curr_min;

    curr_hr -= timeOffSet;
    if(curr_hr<0){
        curr_date -= 1;
        if(curr_date<1){
            curr_month-=1;
            curr_date = 31;
        }
        curr_hr += 24;
    }

    if(curr_month.toString().length == 1)
    curr_month = '0' + curr_month;      
    if(curr_date.toString().length == 1)
    curr_date = '0' + curr_date;
    if(curr_hr.toString().length == 1)
    curr_hr = '0' + curr_hr;
    if(curr_min.toString().length == 1)
    curr_min = '0' + curr_min;

    var currentTime = curr_year + "-" + curr_month + "-" + curr_date + " " + curr_hr + ":" + curr_min + ":" + curr_sc;

    var displayedString = "";

    var eventsRes = eventsRef.once('value').then(function(snapshot){
            console.log("5 Most Recent Events from Query \n");
            console.log(snapshot.val());
            displayedString += "5 Most Recent Events from Query \n" + snapshot.val();
            snapshot.forEach(function(record){
                ID = record.key;
                eventTime = record.val().startDateTime;
                notifiedBool = record.val().notified;
                
                var eventTimeArray = eventTime.split('');
                var eventDay = (eventTimeArray[8] + eventTimeArray[9]).toString();
                var eventYear = (eventTimeArray[0] + eventTimeArray[1]+ eventTimeArray[2]+ eventTimeArray[3]).toString();
                var eventMonth = (eventTimeArray[5] + eventTimeArray[6]).toString();

                //delete event if expired
                if(eventTime<currentTime){
                    displayedString += deleteEvent(ID.toString());
                }

                //if within 1 day of the event, send notification and mark as notified 
                else if(notifiedBool.toString() == "false" && eventMonth == curr_month && eventYear == curr_year && (eventDay - curr_date) < 2){
                    displayedString += notifyUsers(record);
                }

//                console.log(notifiedBool);
//                console.log(eventTime);
//                console.log(ID);
            });
    });

    response.send("Firebase Function updateUsers successfully run. See Firebase Function Logs for routing details in the Firebase Console.");
});

//deletes expired events
function deleteEvent(eventID){
    console.log("Deleted Event with ID: " + eventID);
    var deleteEventRef = database.ref('events').child(eventID.toString());
    deleteEventRef.remove();
    return "Deleted Event with ID: " + eventID + "\n";
};

//notifies users of an event which takes place in one day and marks the event as notified
function notifyUsers(record){
    var topic = record.val().topic.toString();
    var payload={
        data:{
            description: record.val().description.toString(),
            lat: record.val().lat.toString(),
            lng: record.val().lng.toString(),
            notified: record.val().notified.toString(),
            startDateTime: record.val().startDateTime.toString(),
            title: record.val().title.toString(),
            topic: record.val().topic.toString(),
            voteCt: record.val().voteCt.toString()
        }
    };

    const options = {
        priority: "high",
    };


    admin.messaging().sendToTopic(topic, payload, options).then(function(response) {
        // See the MessagingTopicResponse reference documentation for the
        // contents of response.
        console.log("Successfully sent message to " + topic + " for event with title " + payload.data[title].toString());
        return "Succesfully sent message to topic: " + topic;
    }).catch(function(error) {
        console.log("Error sending message to topic: ", topic);
        return "Error sending message to topic: " + topic;
    });

    return "";
};


// // Start writing Firebase Functions
// // https://firebase.google.com/functions/write-firebase-functions
//
// exports.helloWorld = functions.https.onRequest((request, response) => {
//  response.send("Hello from Firebase!");
// })
