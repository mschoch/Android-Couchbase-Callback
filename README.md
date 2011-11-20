## Android Couchbase Callback

This application provides the fastest way to deploy a <a href="http://couchapp.org/">CouchApp</a> to an Android device using <a href="http://couchbase.org/">Couchbase Mobile</a> and <a href="http://incubator.apache.org/projects/callback.html">Apache Callback (formerly PhoneGap)</a>.

## Getting Started

1.  Clone this repository
2.  Create a local.properties pointing to your Android SDK

    sdk.dir=...

3.  Build this application, either using eclipse or command line tools

    ant debug

4.  Install/Launch this application on your device/emulator

    adb install bin/AndroidCouchbaseCallback-debug.apk

    adb shell am start -n com.couchbase.callback/.AndroidCouchbaseCallback

5.  Couchbase Mobile is now running, you should see now see instructions on screen install your CouchApp.

6.  Forward the Couchbase Mobile from the device to your development machine (the Couchbase port is dynamic and is shown on the screen)

    adb forward tcp:8984 tcp:&gt;value displayed on your screen&lt;

7.  From within your CouchApp project directory, run the following command to install your couchapp on the device.

    couchapp push . http://localhost:8984/couchapp

8.  Compact your database

    curl -X POST -H "Content-Type: application/json"  http://localhost:8984/couchapp/_compact

9.  Copy the database off the device and into this Android application's assets directory:

    adb pull /mnt/sdcard/Android/data/com.couchbase.callback/db/couchapp.couch assets

10.  Repackage your application with the database file included

    ant debug

11.  Reinstall the application to launch the CouchApp

    adb uninstall com.couchbase.callback

    adb install bin/AndroidCouchbaseCallback-debug.apk

    adb shell am start -n com.couchbase.callback/.AndroidCouchbaseCallback

## Assumptions

A few assumptions are currently made to reduce the number of options that must be configured to get started.  Currently these can only be changed by modifying the code.

-  The name of the database can be anything (couchapp is used in the examples above).  BUT, the design document must have the same name.
    
## Further Customizations

-  Change the name and package of your application
-  Provide your own custom splash screen