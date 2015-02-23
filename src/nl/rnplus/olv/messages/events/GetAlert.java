package nl.rnplus.olv.messages.events;

import java.nio.ByteBuffer;

import nl.rnplus.olv.messages.LiveViewEvent;
import nl.rnplus.olv.messages.MessageConstants;

public class GetAlert extends LiveViewEvent {
	private byte menuItemId;
	private byte alertAction;
	private short maxBodySize;

    public GetAlert() {
        super(MessageConstants.MSG_GETALERT);
    }

    
    public byte getMenuItemId() {
        return menuItemId;
    }
    
    public byte getAlertAction() {
        return alertAction;
    }
    
    public short getMaxBodySize() {
        return maxBodySize;
    }
    
    /*
     * (non-Javadoc)
     * @see
     * net.sourcewalker.olv.messages.LiveViewResponse#readData(java.nio.ByteBuffer
     * )
     */
    @Override
    public void readData(ByteBuffer buffer) {
    	//self.menuItemId, self.alertAction, self.maxBodySize, a, b, c
    	this.menuItemId = buffer.get();
    	this.alertAction = buffer.get();
    	this.maxBodySize = buffer.getShort();
    }

}
