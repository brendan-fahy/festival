# Festival

## One Line
This app retrieves, caches, and displays data from a configurable RSS feed and Google Calendar.

## Slightly More Detail
This repo is intended more as a library than an app, providing several modules to allow easy
setting up of an app for a festival-type event, by providing out-of-the-box functionality for common/generic features.
In this case, the features are displaying news articles (pulled from an RSS feed), and displaying scheduled events (pulled from a Google Calendar API).

## Brief History
In early 2014 I wrote an app for a festival (the European Juggling Convention, or EJC) which was being run that year by some friends of mine.
The app was very simple (not much more than RSS feed and Google Calendar events), but it was made in a rush and wasn't very good.
 I had always intended to go back and rewrite it from scratch, in a generic way, allowing for as much of it as possible to re-use.
 This app is the product of me finally getting around to doing that.

## Architecture

### Multiple Sub-Modules
The modules are:

1. Data model layer

Holds dumb, no-logic POJO data model classes. There are classes to represent the network responses, and also the more useful `Event` and `Article` classes.

2. API layer

Handles connection to the RSS feed and Google Calendar. Uses Volley for the latter.

3. Presenter layer

This is where the real engine of the app is. The presenter layer provides the app/UI layer with the content to show.
It sits between the API layer and the UI layer, and has built-in storage for news `Article`s and `Event`s, using Jake Wharton's `DiskLruCache` library.
When the UI layer requests content from the `ContentPresenter`, the `ContentPresenter` checks in storage for content, and also requests the data from the network.
If content is found in storage, this is presented to the UI layer. When the network response is received, this is presented to the UI layer.
As the presenter layer is totally unaware of the App layer, delivery is achieved using the GreenRobot `EventBus` library.

4. App layer

The app layer is the actual app, not a library. It is made of core Android classes - Activities, Fragments, and Views, as well as XML layouts, values, etc.
There are no non-Android classes in the app layer, and no logic that is not directly tied to the UI.
The app initialises the Presenter layer, and requests content. All handling of storage, network calls, etc, is left up to the presenter.
Responses from the presenter layer are received via `EventBus` events.

### Build Flavours
The build flavours are set up to allow flavours to configure:

1. The RSS and Google Calendar endpoints.

2. String and colour resources.

At the moment, two flavours are provided: EJC and Wiremock. EJC is a fully-functioning rebuilding of the app that inspired this project.
It uses a real RSS feed and Google Calendar data.

Wiremock is intended to use the wiremock server included in this repo.

## Testing :(
Following the recent release of Android Studio 1.2, we now have full unit test support in the IDE.
However, after several hours work I was still unable to get my tests to run successfully, which is hugely disappointing.
They run, but don't appear to be able to access any of the main source code.
This is a pity, because one of the main reasons for, and benefits of, this module architecture is that it separates UI logic
from "business" logic, which is the stuff that is really key to test.
I have found Espresso to be the best of a bad lot in terms of UI testing, but ultimately too flaky and unreliable to be worth
the investment of time required, at least in short term.

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

 1. Get unit tests working.

 2. Extract News and Schedule Fragments from App layer - meaning an app consuming the library would not be forced to take the whole HomeActivity + Nav Drawer + Fragments structure, and could arrange them as desired.

 3. Better handling of the "before any content has been delivered" scenario - though this is more of a job for the consuming app rather than the library.

 4. Better handling of the updating of content. At the moment it's a rather "nuclear" kind of approach.

 5. Custom landscape layout on schedule view. Cards stretching to the width of the screen don't look great, a grid might be better.

 6. Add "Add to My Calendar" functionality, to either remind users of imminent events they've subscribed to, or add them into the user's own calendar.

 7. Add Twitter feed functionality.

