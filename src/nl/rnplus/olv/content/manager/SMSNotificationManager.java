/*
 * This file is contributed to OLV by Basty149
 */
package nl.rnplus.olv.content.manager;

import nl.rnplus.olv.content.ContentNotification;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.PhoneLookup;
import android.telephony.SmsMessage;

public class SMSNotificationManager {

	public static ContentNotification getNotificationContent(Context context,
			Intent intent) {
		ContentNotification result = new ContentNotification();

		Bundle bundle = intent.getExtras();
		if (bundle != null) {
			Object[] pdus = (Object[]) bundle.get("pdus");

			final SmsMessage[] messages = new SmsMessage[pdus.length];
			for (int i = 0; i < pdus.length; i++) {
				messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
			}
			if (messages.length > -1) {
				final String messageBody = messages[0].getMessageBody();

				result.setContent(messageBody);
				result.setTitle(getContactByAddr(context, messages[0]));
				result.setTimestamp(messages[0].getTimestampMillis());
			}
		}

		return result;
	}

	/**
	 * Return the contact name based on the phone number.
	 * 
	 * @see http://stackoverflow.com/questions/4467232/how-to-get-the-contact-infomation-by-sms-message
	 * @param context
	 * @param sms SmsMessage used to find the contact name
	 * @return Contact name otherwise the phone number
	 */
	private static String getContactByAddr(Context context, final SmsMessage sms) {
		Uri personUri = null;
		Cursor cur = null;

		try {
			personUri = Uri.withAppendedPath(
					ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
					sms.getDisplayOriginatingAddress());
			cur = context.getContentResolver()
					.query(personUri,
							new String[] { PhoneLookup.DISPLAY_NAME }, null,
							null, null);
			if (cur.moveToFirst()) {
				int nameIdx = cur.getColumnIndex(PhoneLookup.DISPLAY_NAME);
				return cur.getString(nameIdx);
			}
			return sms.getDisplayOriginatingAddress();
		} finally {
			if (cur != null) {
				cur.close();
			}
		}
	}
}