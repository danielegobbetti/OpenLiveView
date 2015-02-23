package nl.rnplus.olv.messages.calls;
//Added by RN+

import nl.rnplus.olv.messages.LiveViewCall;
import nl.rnplus.olv.messages.MessageConstants;

public class ClearDisplay extends LiveViewCall {
	
    public ClearDisplay() {
        super(MessageConstants.MSG_CLEARDISPLAY);
    }

    /*
     * (non-Javadoc)
     * @see net.sourcewalker.olv.messages.LiveViewRequest#getPayload()
     */
    @Override
    protected byte[] getPayload() {
        return new byte[] { 0 };
    }

}
