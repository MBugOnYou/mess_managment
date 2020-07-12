package com.example.mealmanagement;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import androidx.multidex.MultiDex;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;


public class MyApplication extends Application {
    public static final String TAG = MyApplication.class
            .getSimpleName();





    private RequestQueue mRequestQueue;



   // private static SampleConfigs sampleConfigs;

    public static MyApplication instance;

    private static MyApplication mInstance;

    private static Context context;

    public static Context getContext() {
        return context;
    }
    //private DatabaseReference mUserDatabase;
    //private FirebaseAuth mAuth;

    private static boolean sIsChatActivityOpen = false;

    public static boolean isChatActivityOpen() {
        return sIsChatActivityOpen;
    }

    public static void setChatActivityOpen(boolean isChatActivityOpen) {
        MyApplication.sIsChatActivityOpen = isChatActivityOpen;
    }

    //Called when the application is starting, before any other application objects have been created.
    @Override
    public void onCreate() {
        super.onCreate();
        initApplication();
        context = getApplicationContext();

        //MyNotificationOpenedHandler : This will be called when a notification is tapped on.
        //MyNotificationReceivedHandler : This will be called when a notification is received while your app is running.
//        OneSignal.startInit(this)
//                .setNotificationOpenedHandler(new MyNotificationOpenedHandler())
//                .setNotificationReceivedHandler( new MyNotificationReceivedHandler() )
//                .init();


//        OneSignal.startInit(this)
//                .setNotificationOpenedHandler(new MyNotificationOpenedHandler())
//                .setNotificationReceivedHandler( new MyNotificationReceivedHandler() )
//                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
//                .unsubscribeWhenNotificationsAreDisabled(true)
//                .init();

        mInstance = this;





       // FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        /* Picasso */

//        Picasso.Builder builder = new Picasso.Builder(this);
//        builder.downloader(new OkHttpDownloader(this, Integer.MAX_VALUE));
//        Picasso built = builder.build();
//        built.setIndicatorsEnabled(true);
//        built.setLoggingEnabled(true);
//        Picasso.setSingletonInstance(built);

        //mAuth = FirebaseAuth.getInstance();

//        if(mAuth.getCurrentUser() != null) {
//
//            mUserDatabase = FirebaseDatabase.getInstance()
//                    .getReference().child("Users").child(mAuth.getCurrentUser().getUid());
//
//            mUserDatabase.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//
//                    if (dataSnapshot != null) {
//
//                        mUserDatabase.child("online").onDisconnect().setValue(ServerValue.TIMESTAMP);
//
//                    }
//
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//
//                }
//            });
//
//        }


        instance = this;
        //ActivityLifecycle.init(this);
        //initSampleConfigs();

    }

    private void initApplication(){
        instance = this;
    }

//    public synchronized QBResRequestExecutor getQbResRequestExecutor() {
//        return qbResRequestExecutor == null
//                ? qbResRequestExecutor = new QBResRequestExecutor()
//                : qbResRequestExecutor;
//    }



    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

//    public synchronized Tracker getGoogleAnalyticsTracker() {
//        AnalyticsTrackers analyticsTrackers = AnalyticsTrackers.getInstance();
//        return analyticsTrackers.get(AnalyticsTrackers.Target.APP);
//    }
//
//    /***
//     * Tracking screen view
//     *
//     * @param screenName screen name to be displayed on GA dashboard
//     */
//    public void trackScreenView(String screenName) {
//        Tracker t = getGoogleAnalyticsTracker();
//
//        // Set screen name.
//        t.setScreenName(screenName);
//
//        // Send a screen view.
//        t.send(new HitBuilders.ScreenViewBuilder().build());
//
//        GoogleAnalytics.getInstance(this).dispatchLocalHits();
//    }

    /***
     * Tracking exception
     *

     */
//    public void trackException(Exception e) {
//        if (e != null) {
//            Tracker t = getGoogleAnalyticsTracker();
//
//            t.send(new HitBuilders.ExceptionBuilder()
//                    .setDescription(
//                            new StandardExceptionParser(this, null)
//                                    .getDescription(Thread.currentThread().getName(), e))
//                    .setFatal(false)
//                    .build()
//            );
//        }
//    }


//    public void trackEvent(String category, String action, String label) {
//        Tracker t = getGoogleAnalyticsTracker();
//
//        // Build and send an Event.
//        t.send(new HitBuilders.EventBuilder().setCategory(category).setAction(action).setLabel(label).build());
//    }
//
//
//    private void initSampleConfigs() {
//        try {
//            sampleConfigs = ConfigUtils.getSampleConfigs(Consts.SAMPLE_CONFIG_FILE_NAME);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static SampleConfigs getSampleConfigs() {
//        return sampleConfigs;
//    }



    public RequestQueue getRequestQueue() {
        if (this.mRequestQueue == null) {
            this.mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return this.mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        if (TextUtils.isEmpty(tag)) {
            tag = TAG;
        }
        req.setTag(tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (this.mRequestQueue != null) {
            this.mRequestQueue.cancelAll(tag);
        }
    }
}
