package nl.rnplus.olv.messages.events;

import java.nio.ByteBuffer;

import nl.rnplus.olv.messages.LiveViewEvent;
import nl.rnplus.olv.messages.MessageConstants;

public class DeviceStatus extends LiveViewEvent {

    private byte status;

    public byte getStatus() {
        return status;
    }

    public DeviceStatus() {
        super(MessageConstants.MSG_DEVICESTATUS);
    }

    /*
     * (non-Javadoc)
     * @see
     * net.sourcewalker.olv.messages.LiveViewResponse#readData(java.nio.ByteBuffer
     * )
     */
    @Override
    public void readData(ByteBuffer buffer) {
        status = buffer.get();
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "DeviceStatus = " + status;
    }

}
