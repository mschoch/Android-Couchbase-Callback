package com.couchbase.callback;

import java.io.IOException;

import android.content.ServiceConnection;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;

import com.couchbase.android.CouchbaseMobile;
import com.couchbase.android.ICouchbaseDelegate;
import com.phonegap.DroidGap;

public class AndroidCouchbaseCallback extends DroidGap
{
    public static final String TAG = AndroidCouchbaseCallback.class.getName();
    public static final String COUCHBASE_DATABASE_SUFFIX = ".couch";
    private CouchbaseMobile couchbaseMobile;
    private ServiceConnection couchbaseService;
    private String couchappDatabase;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // increase the default timeout
        super.setIntegerProperty("loadUrlTimeoutValue", 60000);

        couchbaseMobile = new CouchbaseMobile(getBaseContext(), couchCallbackHandler);
        try {
            // look for a .couch file in the assets folder
            couchappDatabase = findCouchApp();
            if(couchappDatabase != null) {
                // if we found one, install it
                couchbaseMobile.installDatabase(couchappDatabase + COUCHBASE_DATABASE_SUFFIX);
            }
        } catch (IOException e) {
            Log.e(TAG, "Error installing database", e);
        }

        // start couchbase
        couchbaseService = couchbaseMobile.startCouchbase();
    }

    /**
     * Look for the first .couch file that can be found in the assets folder
     *
     * @return the name of the database (without the .couch extension)
     * @throws IOException
     */
    public String findCouchApp() throws IOException {
        String result = null;
        AssetManager assetManager = getAssets();
        String[] assets = assetManager.list("");
        if(assets != null) {
            for (String asset : assets) {
                if(asset.endsWith(COUCHBASE_DATABASE_SUFFIX)) {
                    result = asset.substring(0, asset.length() - COUCHBASE_DATABASE_SUFFIX.length());
                    break;
                }
            }
        }
        return result;
    }

    /**
     * Clean up the Couchbase service
     */
    @Override
    public void onDestroy() {
        unbindService(couchbaseService);
        super.onDestroy();
    }

    /**
     * Implementation of the ICouchbaseDelegat inerface
     */
    private final ICouchbaseDelegate couchCallbackHandler = new ICouchbaseDelegate() {

        /**
         * Once Couchbase has started, load the couchapp, or the instructions if no couchapp is present
         */
        @Override
        public void couchbaseStarted(String host, int port) {
            if(couchappDatabase != null) {
                AndroidCouchbaseCallback.this.loadUrl("http://" + host + ":" + port + "/" + couchappDatabase + "/_design/" + couchappDatabase + "/index.html");
            }
            else {
                AndroidCouchbaseCallback.this.loadUrl("file:///android_asset/www/couchapp.html");
            }
        }

        @Override
        public void exit(String error) {}
    };
}

