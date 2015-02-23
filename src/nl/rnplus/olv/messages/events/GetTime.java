package nl.rnplus.olv.messages.events;

import java.nio.ByteBuffer;

import nl.rnplus.olv.messages.LiveViewEvent;
import nl.rnplus.olv.messages.MessageConstants;

public class GetTime extends LiveViewEvent {

    public GetTime() {
        super(MessageConstants.MSG_GETTIME);
    }

    /*
     * (non-Javadoc)
     * @see
     * net.sourcewalker.olv.messages.LiveViewResponse#readData(java.nio.ByteBuffer
     * )
     */
    @Override
    public void readData(ByteBuffer buffer) {
    }

}
