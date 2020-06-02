package com.onesignal.sdktest.application;

import android.app.Application;
import android.util.Log;

import com.onesignal.OSNotificationGenerationJob.AppNotificationGenerationJob;
import com.onesignal.OneSignal;
import com.onesignal.sdktest.R;
import com.onesignal.sdktest.constant.Tag;
import com.onesignal.sdktest.constant.Text;
import com.onesignal.sdktest.util.OneSignalPrefs;

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);

        // OneSignal Initialization
        String appId = OneSignalPrefs.getOneSignalAppId(this);
        // If cached app id is null use the default, otherwise use cached.
        if (appId == null) {
            appId = getString(R.string.onesignal_app_id);
            OneSignalPrefs.cacheOneSignalAppId(this, appId);
        }
        OneSignal.setAppId(appId);
        OneSignal.setAppContext(this);

        OneSignal.setNotificationWillShowInForegroundHandler(new OneSignal.AppNotificationWillShowInForegroundHandler() {
            @Override
            public void notificationWillShowInForeground(AppNotificationGenerationJob notifJob) {
                OneSignal.onesignalLog(OneSignal.LOG_LEVEL.VERBOSE, "App notificationWillShowInForeground fired!!!!");

                notifJob.setNotificationDisplayOption(OneSignal.OSNotificationDisplay.NOTIFICATION);
                notifJob.complete();
            }
        });

        OneSignal.unsubscribeWhenNotificationsAreDisabled(true);
        OneSignal.pauseInAppMessages(true);
        OneSignal.setLocationShared(false);

        Log.d(Tag.DEBUG, Text.ONESIGNAL_SDK_INIT);
    }

}