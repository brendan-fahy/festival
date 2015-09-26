# Festival

## One Line
This app retrieves, caches, and displays data from a configurable RSS feed and Google Calendar.

## Slightly More Detail
This repo is intended as a testbed for me to play with new stuff. Originally it was meant to function 
more as a library than an app, providing several modules to allow easy setting up of an app for a 
festival-type event, by providing out-of-the-box functionality for common/generic features.
In this case, the features are displaying news articles (pulled from an RSS feed), and displaying 
scheduled events (pulled from a Google Calendar API).
Now I'm more interested in using it to try out some of the Android technology I don't get to use 
at work. 

## Brief History
In early 2014 I wrote an app for a festival (the European Juggling Convention, or EJC) which was being run that year by some friends of mine.
The app was very simple (not much more than RSS feed and Google Calendar events), but it was made in a rush and wasn't very good.
 I had always intended to go back and rewrite it from scratch, in a generic way, allowing for as much of it as possible to re-use.
 This app is the product of me finally getting around to doing that. And then several subsequent 
 refactors.

## Architecture
My first refactor of this was an attempt to use a sort of MVP-by-module, with separate modules
for the Model, View, and Presenter. It wasn't real MVP by-the-book, though I'm not sure that 
actually exists. I have subsequently found that, at scale, that approach doesn't work very well. 
Changes cross modules quite frequently, as code for any given feature can easily span all modules.
I've since gone back to more of an MVP-by-feature sort of approach, wherein the MVP pattern exists 
within a package. As I write this, I'm planning more massive refactoring, including the use of 
Dagger and RxJava, so there's probably not too much point writing more about the current state of 
affairs. Maybe I should sum it up with "in flux". 

### Build Flavours
The build flavours are set up to allow flavours to configure:

1. The RSS and Google Calendar endpoints.

2. String and colour resources.

At the moment, two flavours are provided: EJC and Wiremock. EJC is a fully-functioning rebuilding of the app that inspired this project.
It uses a real RSS feed and Google Calendar data.

Wiremock is intended to use the wiremock server included in this repo.

## Testing 
There are some basic unit tests in place, mostly to establish the framework in place so I can 
build on that. They're not testing anything significant at the moment.
Integration of Dagger 2 will be one of the next improvements to be put in place. The use of custom/
overridden Dagger modules will facilitate testing in a big way, particularly with the RxJava 
 scheduling stuff.
  
I've added some Instrumentation tests (again quite basic for the moment), but it's occurred to me 
that in an MVP world, Instrumentation tests don't do much that couldn't/shouldn't/can't already be 
handled by unit tests and Espresso tests. Speaking of which...  

`//TODO - add Espresso tests.`


## Wiremock Server
This is the simplest possible mock server - one response for each of the two endpoints, no variation.
 However it allows for testing of the app without needing to pull data from any third-party/external API, which is handy.
 The Wiremock flavour presumes that it's being run on a Genymotion emulator, so it uses the IP address `10.0.3.2`, which on Genymotion points to the host machine.
  On a regular Android emulator this would need to be changed to `10.0.2.2`.
  And obviously on a real device, neither of those would work. Change as required.
  A simple `wiremock.sh` script is included, to start the server on port 9999. Execute `./wiremock.sh` to run it.


## Design
I am not a designer. However, with the release of the material design guidelines, as well as the `AppCompat` libraries, you don't need to be a designer
to make a basic Android app that doesn't look too bad. I opted for two of the most common design patterns here: lists of cards, and a navigation drawer.
I used www.materialpalette.com to generate a colour scheme.

## Future Enhancements/Room For Improvements
This library has plenty of room for additional development, in terms of both more features, and improving what's already here.
 Some areas that spring to mind are:

### Code Improvements
 
 1. Dagger
 
 2. RecyclerView instead of ListView. Shouldn't take long.
 
 3. More/better RxJava.
 
 4. Use SyncAdapter for Calendar/RSS data.
 
 5. More unit tests.

 6. More instrumentation/Espresso tests.

### Feature Enhancements
 1. Better handling of the "before any content has been delivered" scenario

 2. Better handling of the updating of content. At the moment it's a rather "nuclear" kind of approach.

 3. Custom landscape layout on schedule view. Cards stretching to the width of the screen don't look great, a grid might be better.

 4. Add "Add to My Calendar" functionality, to either remind users of imminent events they've subscribed to, or add them into the user's own calendar.

 5. Add Twitter feed functionality.
 

