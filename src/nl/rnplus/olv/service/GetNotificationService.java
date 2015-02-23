/*
 * Added by Renze Nicolai (RN+)
 * Filter added by Jan Korpegård
 * New code by: TpmKranz
 * GetNotificationService.java
 * Receives all notifications from the OS.
 */

package nl.rnplus.olv.service;

import java.util.List;

import nl.rnplus.olv.R;
import nl.rnplus.olv.data.LiveViewDbConstants;
import nl.rnplus.olv.data.Prefs;
import nl.rnplus.olv.receiver.LiveViewBrConstants;
import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.Notification;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.widget.RemoteViews;
import android.widget.TextView;

public class GetNotificationService extends AccessibilityService {

    private static final String LOG_TAG = "OLV Notification service";
    final public static String SHOW_NOTIFICATION = "OLV_ADD_NOTIFICATION";
    private String PLUGIN_COMMAND = "nl.rnplus.olv.plugin.command";

    @Override
    public void onServiceConnected() {
        AccessibilityServiceInfo info = new AccessibilityServiceInfo();
        info.eventTypes = AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED;
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;
        info.notificationTimeout = 100;

        this.setServiceInfo(info);
        Log.d(LOG_TAG, "Service started!");

    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        final int eventType = event.getEventType();
        switch (eventType) {
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:
                List<CharSequence> notificationList = event.getText();
                for (CharSequence aNotificationList : notificationList) {
                    //Log.d(LOG_TAG, "The notification: " + aNotificationList);
                    try {
                        Long time = System.currentTimeMillis();
                        String[] notificationContents = getNotifContents(event);
                        //Don't know how you would like to handle jkorp's filter, I'll just leave it as it is. Maybe someone could clean this up a little.
                        String notificationContentFilter = (new Prefs(this)).getNotificationFilter();
                        String content = aNotificationList.toString();
                        if (!notificationContentFilter.contains(content)) {
                                if (valid(event)) {
                                        String value = aNotificationList.toString();	    	
                                        Intent add_alert_intent = new Intent(LiveViewBrConstants.ALERT_ADD);
                                        Bundle add_alert_bundle = new Bundle();
                                        add_alert_bundle.putString("contents", notificationContents[1]);
                                        add_alert_bundle.putString("title", notificationContents[0]);
                                        add_alert_bundle.putInt("type", LiveViewDbConstants.ALERT_ANDROID);
                                        add_alert_bundle.putLong("timestamp", time);
                                        add_alert_intent.putExtras(add_alert_bundle);
                                        sendBroadcast(add_alert_intent);
                                                Prefs prefs = new Prefs(this);
                                                Boolean enable_notification_buzzer2 = prefs.getenablenotificationbuzzer2();
                                                if (enable_notification_buzzer2)
                                                {
                                                    Intent bci2 = new Intent(PLUGIN_COMMAND);
                                                    Bundle bcb2 = new Bundle();
                                                    bcb2.putString("command", "notify");
                                                    bcb2.putInt("delay", 0);
                                                    bcb2.putInt("time", 600);
                                                    bcb2.putLong("timestamp", time);
                                                    bcb2.putInt("displaynotification", 1);
                                                    bcb2.putString("line1", notificationContents[0]);
                                                    bcb2.putString("line2", notificationContents[1]);
                                                    bcb2.putInt("icon_type", 1);
                                                    bci2.putExtras(bcb2);
                                                    sendBroadcast(bci2);
                                                }
                                        String message = "Notification sent to LiveView: "+aNotificationList.toString();
                                        Log.d(LOG_TAG, message);
                                        //LiveViewDbHelper.logMessage(this, message);
                                } else {
                                        Log.d(LOG_TAG, "Notification not added because of TpmKranz filter (2). Content: " + content);
                                }
                        } else {
                                Log.d(LOG_TAG, "Notification not added because of filter (2). Content: " + content);
                        }
                    } catch (IllegalArgumentException e) {
                        String message = "Error while reading notifications!";
                        Log.e(LOG_TAG, message);
                    }
                }
                break;
            default:
                String message = "Error: unknown event type (" + eventType + ")";
                Log.e(LOG_TAG, message);
                //LiveViewDbHelper.logMessage(this, message);                
        }
    }
    
        private String[] getNotifContents(AccessibilityEvent event) {
                String[] notifContents = new String[2];
                //Ids from http://t.co/AmjZB0l2 and http://t.co/VaxK16f4
                int messageId = (android.os.Build.VERSION.SDK_INT < 11 ? 16908352 : 16908358);
                PackageManager pM = this.getPackageManager();
                String tickerText = (event.getText().size() == 0 ? "" : event.getText().get(0).toString());
                String appName;
                try{
                        ApplicationInfo aI = pM.getApplicationInfo(event.getPackageName().toString(), 0);
                        appName = " ("+pM.getApplicationLabel(aI).toString()+")";
                }catch(Exception e){
                        appName = "";
                }
                if(event.getClassName().equals("android.widget.Toast$TN")){
                        notifContents[0] = getString(R.string.backup_title_toast)+appName;
                        notifContents[1] = tickerText;
                }else{
                        try{
                                //Method from http://stackoverflow.com/questions/9292032/extract-notification-text-from-parcelable-contentview-or-contentintent
                                Notification notification = (Notification) event.getParcelableData();
                                RemoteViews remoteViews = notification.contentView;
                                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                                ViewGroup localViews = (ViewGroup) inflater.inflate(remoteViews.getLayoutId(), null);
                                remoteViews.reapply(this, localViews);
                                String title = ((TextView) localViews.findViewById(android.R.id.title)).getText().toString();
                                String message = ((TextView) localViews.findViewById(messageId)).getText().toString();
                                notifContents[0] = title+appName;
                                notifContents[1] = message;
                                if(android.os.Build.VERSION.SDK_INT > 13){
                                        messageId = 16908309;
                                        message = ((TextView) localViews.findViewById(messageId)).getText().toString();
                                        if(!message.equals(""))
                                                notifContents[1] = notifContents[1]+":\n"+message;
                                }
                        }catch(Exception e){
                                notifContents[0] = getString(R.string.backup_title_toast)+appName;
                                notifContents[1] = tickerText; 
                        }
                }
                return notifContents;
        }

    private boolean valid(AccessibilityEvent event) {
                boolean valid = true;
                String typeNotification = event.getClassName().toString();
                Prefs prefs = new Prefs(this);
                if(prefs.getFilterToast() && typeNotification.equals("android.widget.Toast$TN")) return false;
                String packageNotification=event.getPackageName().toString();
                for(int i = 0; i<prefs.getNumberOfFilters() && valid;i++){
                        if(prefs.getFilterString(i).equals(packageNotification)) valid = false;
                }
                if(!prefs.getFilterBlacklist()) valid = !valid;
                return valid;
        }
    
    @Override
    public void onInterrupt() {
        String message = "OnInterrupt() triggered in NotificationService.";
        Log.v(LOG_TAG, message);
        //LiveViewDbHelper.logMessage(this, message);              
    }

}