package nl.rnplus.olv.messages.events;

import java.nio.ByteBuffer;

import nl.rnplus.olv.messages.LiveViewEvent;
import nl.rnplus.olv.messages.MessageConstants;

public class GetMenuItems extends LiveViewEvent {

    public GetMenuItems() {
        super(MessageConstants.MSG_GETMENUITEMS);
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
