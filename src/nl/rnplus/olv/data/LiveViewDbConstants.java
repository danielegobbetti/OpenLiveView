package nl.rnplus.olv.data;

/**
 * Database constants
 * Currently contains only the notification types
 * 
 * @author Renze Nicolai
 */

public final class LiveViewDbConstants
{
  //Database
  public static final String DATABASE_NAME = "olv.db";
  public static final int DATABASE_VERSION = 3;
  
  //Tables
  public static final String TABLE_ALERT_ITEMS = "alert_items";
  public static final String TABLE_ALERT_FILTER = "alert_filter";
  public static final String TABLE_LOG = "log";
  public static final String TABLE_MENU_ITEMS = "menu_items";
  
  //Columns for TABLE_ALERT_ITEMS
  public static final String COLUMN_ALERT_ITEMS_ID = "_ID";
  public static final String COLUMN_ALERT_ITEMS_CONTENT = "content";
  public static final String COLUMN_ALERT_ITEMS_TITLE = "title";
  public static final String COLUMN_ALERT_ITEMS_TYPE = "type";
  public static final String COLUMN_ALERT_ITEMS_TIMESTAMP = "timestamp";
  public static final String COLUMN_ALERT_ITEMS_UNREAD = "unread";
  
  
  //Alert types
  public static final int ALERT_ALL = -1; //Use only when getting notifications from the database
  public static final int ALERT_GENERIC = 0; //Other notifications
  public static final int ALERT_ANDROID = 1; //Notifications received by the accessibility service
  public static final int ALERT_SMS = 2; //SMS messages
  public static final int ALERT_NOTE = 3; //Notes
  public static final int ALERT_PLUGIN = 4; //Plugin
  public static final int ALERT_PLUGIN_I = 5; //Plugin (with custom icon)
}
