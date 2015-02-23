package nl.rnplus.olv.messages.calls;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import nl.rnplus.olv.messages.EncodingException;
import nl.rnplus.olv.messages.LiveViewCall;
import nl.rnplus.olv.messages.MessageConstants;

public class CapsRequest extends LiveViewCall {

    public CapsRequest() {
        super(MessageConstants.MSG_GETCAPS);
    }

    /*
     * (non-Javadoc)
     * @see net.sourcewalker.olv.LiveViewMessage#getPayload()
     */
    @Override
    protected byte[] getPayload() {
        try {
            byte[] version = MessageConstants.CLIENT_SOFTWARE_VERSION
                    .getBytes("iso-8859-1");
            byte msgLength = (byte) version.length;
            ByteBuffer buffer = ByteBuffer.allocate(msgLength + 1);
            buffer.order(ByteOrder.BIG_ENDIAN);
            buffer.put(msgLength);
            buffer.put(version);
            return buffer.array();
        } catch (UnsupportedEncodingException e) {
            throw new EncodingException(e);
        }
    }
}
