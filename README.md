## Android Couchbase Callback

This application provides the fastest way to deploy a <a href="http://couchapp.org/">CouchApp</a> to an Android device using <a href="http://couchbase.org/">Couchbase Mobile</a> and <a href="http://incubator.apache.org/projects/callback.html">Apache Callback (formerly PhoneGap)</a>.

## Getting Started

1.  Clone this repository
2.  Build this application, either using eclipse or command line tools

    ant debug
    
3.  Install/Launch this application on your device/emulator

    adb install bin/AndroidCouchbaseCallback-debug.apk
    adb shell am start -n com.couchbase.callback/.AndroidCouchbaseCallback
    
4.  Couchbase Mobile is now running, you should see now see instructions on screen install your CouchApp.

5.  Forward the Couchbase Mobile from the device to your development machine (the Couchbase port is dynamic and is shown on the screen)

    adb forward tcp:8984 tcp:<value displayed on your screen>
    
6.  From within your CouchApp project directory, run the following command to install your couchapp on the device.

    couchapp push . http://localhost:8984/couchapp
    
7.  Compact your database

    curl -X POST -H "Content-Type: application/json"  http://localhost:8984/couchapp/_compact
    
8.  Copy the database off the device and into this Android application's assets directory:

    adb pull /mnt/sdcard/Android/data/com.couchbase.callback/db/couchapp.couch assets
    
9.  Repackage your application with the database file included

    ant debug