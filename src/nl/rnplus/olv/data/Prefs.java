package nl.rnplus.olv.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import nl.rnplus.olv.R;

/**
 * Provides access to the preferences of this application.
 */
public class Prefs {

    private final SharedPreferences preferences;
    private final String keyDeviceAddress;

    public Prefs(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        keyDeviceAddress = context.getString(R.string.prefs_deviceaddress_key);
    }

    public String getDeviceAddress() {
        return preferences.getString("device.address", null);
    }

    public Boolean getMenuShowFindMyPhone() {
        return preferences.getBoolean("menu.findmyphonevisible", true);
    }

    public Boolean getMenuShowNotifications() {
        return preferences.getBoolean("menu.notificationsvisible", true);
    }

    public Boolean getMenuShowMediaNext() {
        return preferences.getBoolean("menu.medianextvisible", true);
    }

    public Boolean getMenuShowMediaPlay() {
        return preferences.getBoolean("menu.mediaplayvisible", true);
    }

    public Boolean getMenuShowMediaPrevious() {
        return preferences.getBoolean("menu.mediapreviousvisible", true);
    }

    public Boolean getenablenotificationbuzzer() {
        return preferences.getBoolean("system.enablenotificationbuzzer", false);
    }
    
    public Boolean getenablenotificationbuzzer2() {
        return preferences.getBoolean("system.enablenotificationbuzzer2", true);
    }

    public Boolean getEnableMediaMenu() {
        return preferences.getBoolean("media.enablemenu", true);
    }

    public Boolean getMenuShowBatteryStatus() {
        return preferences.getBoolean("menu.batterystatusvisible", true);
    }

    public Boolean getSetupCompleted() {
        return preferences.getBoolean("system.setupcompleted", false);
    }

    public Boolean getUpdateUnreadCountWhenMenuOpens() {
        return preferences.getBoolean("system.updateunreadcountwhenmenuopens", false);
    }
    
    public int getMenuVibrationTime() {
        return preferences.getInt("menu.menuvibrationtime", 0);
    }
    
    public int getInitialMenuItemId() {
        return preferences.getInt("menu.initialmenuitemid", 0);
    }
    
    public Boolean getMenuShowMediaMenuStatus() {
        return preferences.getBoolean("menu.mediamenuvisible", false);
    }    
    
    public Boolean getEnableIncomingCallNotify() {
        return preferences.getBoolean("system.incomingcallnotify", true);
    } 
    
    public Boolean getEnableIncomingCallVibrate() {
        return preferences.getBoolean("system.incomingcallvibrate", true);
    }    
    
    public void setMenuVibrationTime(int value) {
        Editor editor = preferences.edit();
        editor.putInt("menu.menuvibrationtime", value);
        editor.commit();
    }
    
    public void setInitialMenuItemId(int value) {
        Editor editor = preferences.edit();
        editor.putInt("menu.initialmenuitemid", value);
        editor.commit();
    }
    
    public void setSetupCompleted(Boolean value) {
        Editor editor = preferences.edit();
        editor.putBoolean("system.setupcompleted", value);
        editor.commit();
    }

    public void setDeviceAddress(String address) {
        Editor editor = preferences.edit();
        editor.putString(keyDeviceAddress, address);
        editor.commit();
    }
    
    public void setNotificationFilter(String filter) {
        Editor editor = preferences.edit();
        editor.putString("system.notificationfilter", filter);
        editor.commit();
    }
    
    public String getNotificationFilter() {
        return preferences.getString("system.notificationfilter", "");
    }   

    public int getMenuUpDownAction() {
        return Integer.parseInt(preferences.getString("menu.menuupdownaction", "1"));
    }
    
    // TpmKranz
    public void addFilterString(String string){
		int number = preferences.getInt("numberoffilters", 0);
		Editor editor = preferences.edit();
		editor.putString("filter"+String.valueOf(number), string);
		editor.putInt("numberoffilters", 1+number);
		editor.commit();
	}
	
	public void removeFilterString(int position){
		int number = preferences.getInt("numberoffilters", 0);
		Editor editor = preferences.edit();
		for(int i=position;i<number-1;i++){
			editor.putString("filter"+String.valueOf(i), preferences.getString("filter"+String.valueOf(i+1), ""));
		}
		editor.putInt("numberoffilters", number-1);
		editor.remove("filter"+String.valueOf(number-1));
		editor.commit();
	}
	
	public String getFilterString(int position){
		return preferences.getString("filter"+String.valueOf(position), "");
	}
	
	public int getNumberOfFilters(){
		return preferences.getInt("numberoffilters", 0);
	}
	
	public void setFilterToast(boolean value){
		Editor editor = preferences.edit();
		editor.putBoolean("filtertoast", value);
		editor.commit();
	}
	
	public boolean getFilterToast(){
		return preferences.getBoolean("filtertoast", false);
	}
	

	public void setFilterBlacklist(boolean value){
		Editor editor = preferences.edit();
		editor.putBoolean("filterblacklist", value);
		editor.commit();
	}
	
	public boolean getFilterBlacklist(){
		return preferences.getBoolean("filterblacklist", true);
	}
	
	public Boolean getWipeNotifications() {
        return preferences.getBoolean("system.wipenotifications", false);
	}
	
    public Boolean getClockMode() {
        return preferences.getBoolean("system.clockmode", false);
    }
}
