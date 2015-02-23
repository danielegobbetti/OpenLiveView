package nl.rnplus.olv.messages;

import android.util.Log;
import nl.rnplus.olv.messages.events.*;

import java.nio.ByteBuffer;

public final class MessageDecoder {

    private static final String TAG = "MessageDecoder";

    private static LiveViewEvent newInstanceForId(byte id)
            throws DecodeException {
        switch (id) {
            case MessageConstants.MSG_GETCAPS_RESP:
                return new CapsResponse();
            case MessageConstants.MSG_SETVIBRATE_ACK:
                return new ResultEvent(id);
            case MessageConstants.MSG_CLEARDISPLAY_ACK:
                return new ResultEvent(id);
            case MessageConstants.MSG_DISPLAYBITMAP_ACK:
                return new ResultEvent(id);
            case MessageConstants.MSG_SETSCREENMODE_ACK:
                return new ResultEvent(id);
            case MessageConstants.MSG_DISPLAYPANEL_ACK:
                return new ResultEvent(id);
            case MessageConstants.MSG_SETLED_ACK:
                return new ResultEvent(id);
            case MessageConstants.MSG_GETTIME:
                return new GetTime();
            case MessageConstants.MSG_GETMENUITEMS:
                return new GetMenuItems();
            case MessageConstants.MSG_GETALERT: //Added by RN+
                return new GetAlert();
            case MessageConstants.MSG_DEVICESTATUS:
                return new DeviceStatus();
            case MessageConstants.MSG_NAVIGATION:
                return new Navigation();
            default:
                throw new DecodeException("No message found matching ID: " + id);
        }
    }

    public static LiveViewEvent decode(byte[] message, int length)
            throws DecodeException {
        if (length < 4) {
            Log.w(TAG, "Got empty message!");
            throw new DecodeException("Can't decode empty message!");
        } else {
            ByteBuffer buffer = ByteBuffer.wrap(message, 0, length);
            byte msgId = buffer.get();
            buffer.get();
            int payloadLen = buffer.getInt();
            if (payloadLen + 6 == length) {
                LiveViewEvent result = newInstanceForId(msgId);
                result.readData(buffer);
                return result;
            } else {
                throw new DecodeException("Invalid message length: "
                        + message.length + " (should be " + (payloadLen + 6)
                        + ")");
            }
        }
    }

}
