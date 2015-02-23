package nl.rnplus.olv.messages.calls;
//Added by RN+
//Sets brightness of LiveView display

import nl.rnplus.olv.messages.LiveViewCall;
import nl.rnplus.olv.messages.MessageConstants;

public class SetScreenMode extends LiveViewCall {
	
	private final byte brightness;
	
    public SetScreenMode(byte brightness) {
        super(MessageConstants.MSG_SETSCREENMODE);
        this.brightness = brightness;
    }

    /*
     * (non-Javadoc)
     * @see net.sourcewalker.olv.messages.LiveViewRequest#getPayload()
     */
    @Override
    protected byte[] getPayload() {
        return new byte[] { brightness };
    }

}
