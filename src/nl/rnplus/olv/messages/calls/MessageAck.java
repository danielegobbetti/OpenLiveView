package nl.rnplus.olv.messages.calls;

import nl.rnplus.olv.messages.LiveViewCall;
import nl.rnplus.olv.messages.MessageConstants;

/**
 * @author Xperimental
 */
public class MessageAck extends LiveViewCall {

    private final byte ackMsgId;

    public MessageAck(byte ackMsgId) {
        super(MessageConstants.MSG_ACK);
        this.ackMsgId = ackMsgId;
    }

    /*
     * (non-Javadoc)
     * @see net.sourcewalker.olv.messages.LiveViewRequest#getPayload()
     */
    @Override
    protected byte[] getPayload() {
        return new byte[] { ackMsgId };
    }

}
