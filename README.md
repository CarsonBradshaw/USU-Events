# Updated UI Demo - Test Data Included 8/22/2017
![Demo](https://user-images.githubusercontent.com/13813803/29599305-03ef5786-878c-11e7-80a2-4594c0a906e8.gif)

<br/>

# Sprint 2 Results
![Demo](https://user-images.githubusercontent.com/13813803/29651045-d35bf18a-885b-11e7-967f-b108bf001ecd.gif)

<br/>

# Sprint 1 Results
![Demo](https://user-images.githubusercontent.com/13813803/29648131-d5a06014-8849-11e7-9660-b46bfd73ba65.gif)

<br/>

# Connecting Students to Events

USU Events was created to solve one simple problem: getting students to events. 


On the surface this problem seems quite simple; in practice, not quite. In fact, to solve this problem USU itself has had to utilize 
several different social media outlets and means of advertisement and communication. Between Facebook, Twitter, posters, radio ads, word 
of mouth, and USU's own in-house mobile application, it's no wonder students struggle to keep up to date on upcoming events. 

#### At their core, these implementations suffer from two major flaws preventing user engagement:

### 1. Lack of Centralization
Due to source fragmentation, for a user to stay up to date on upcoming events they must actively check
multiple Facebook pages, Twitter accounts, etc. It has become increasingly hard to stay in the know.

### 2. Requirement of Constant User Interaction
By design, each of these sources require continuous interaction. For a user to be aware of an event, they themselves must frequently
visit each source.

<br/>

## The Solution - Centralization and... the Observer Pattern!
While centralization of data is fairly straightforward, this alone would not be enough. Even if all data sources were aggregated and
centralized, users would still have to continuously return to the source to find out about events. So how do we eliminate the users'
dependency on returning to the source for information?

### Notifications and Subscriptions
Thanks to the likes of Youtube and several other content providers, subscribing to a source (channel, topic, whatever) 
is a familiar concept. By applying this concept from the realm of content consumption to that of a separate domain (events), we are
able to provide users updates tailored exactly to their subscriptions. No more checking those 12 different Facebook pages!

<br/>

## From the User's Perspective

#### 1. Sign In
With support for Google and Facebook sign-in built in, users don't have to worry about remembering a username and password. Anonymous login and phone number authentication are in the pipeline based off of Google's Firebase Authentication system.
#### 2. Subscribe to Specific Channels or General Topics
Choose exactly which channels or topics you want to receive notifications for. Interested only in the College of Engineering events or Hackathons? Great! Subscribe to exactly what you want.
#### 3. Setup Notification Period
Now that you've chosen what you want to be notified of, now you can choose when exactly you'd like to be notified. 
#### 4. Stay Up to Date Without Ever Laying Another Finger
Manage subsciptions or view all upcoming events at anytime. Get notified without ever having to look again.

<br/>
