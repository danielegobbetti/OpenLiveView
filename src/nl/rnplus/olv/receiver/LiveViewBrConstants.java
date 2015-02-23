package nl.rnplus.olv.receiver;

/**
 * Broadcast constants 
 * @author Renze Nicolai
 */

public final class LiveViewBrConstants
{
  //Received by OLV receiver
  public static final String ALERT_ADD = "nl.rnplus.olv.alert.add";
	
  //Received by OLV service
  public static final String ALERT_NOTIFY = "nl.rnplus.olv.alert.notify";
  public static final String PLUGIN_COMMAND = "nl.rnplus.olv.plugin.command";
  
  //Transmitted to other applications
  public static final String PLUGIN_RESULT = "nl.rnplus.olv.plugin.result";
  
  //Other constants
  public static final String OTHER_SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
}
