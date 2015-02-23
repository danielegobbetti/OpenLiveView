package nl.rnplus.olv.service;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
import nl.rnplus.olv.ConfigWizardActivity;
import nl.rnplus.olv.data.Prefs;

/**
 * This receiver listens for system broadcasts related to the bluetooth device
 * and controls the LiveView service. It is also used for starting the service
 * on boot (if bluetooth is available).
 *
 * @author Robert xperimental@solidproject.de;
 */
public class BTReceiver extends BroadcastReceiver {

    private static final String TAG = "BTReceiver";

    /*
     * (non-Javadoc)
     * @see android.content.BroadcastReceiver#onReceive(android.content.Context,
     * android.content.Intent)
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        Prefs prefs = new Prefs(context);
        String address = prefs.getDeviceAddress();
        if (address == null) {
            Log.w(TAG, "No device configured!");
            prefs.setSetupCompleted(false); //Show the setup to the user when there is not device configured.
            Toast.makeText(context, "No LiveView device configured! Please configure OLV by opening the app.", Toast.LENGTH_SHORT).show();
        } else {
            Log.d(TAG, "Device address: " + address);
            String action = intent.getAction();
            if (intent.getExtras() != null) {
                BluetoothDevice device = (BluetoothDevice) intent.getExtras()
                        .get(BluetoothDevice.EXTRA_DEVICE);
                Log.d(TAG, "Received broadcast: " + action);
                if (device != null && address.equals(device.getAddress())) {
                    if (BluetoothDevice.ACTION_ACL_CONNECTED.equals(action)) {
                        Log.d(TAG, "Connected -> Start service.");
                        sendIntent(context, true);
                    }
                    if (BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED
                            .equals(action)
                            || BluetoothDevice.ACTION_ACL_DISCONNECTED
                            .equals(action)) {
                        Log.d(TAG, "Disconnected -> Stop service.");
                        sendIntent(context, false);
                    }
                }
            }
        }
    }

    private void sendIntent(Context context, boolean newState) {
        String action = newState ? LiveViewService.ACTION_START
                : LiveViewService.ACTION_STOP;
        Intent intent = new Intent(context, LiveViewService.class);
        intent.setAction(action);
        context.startService(intent);
    }

}
