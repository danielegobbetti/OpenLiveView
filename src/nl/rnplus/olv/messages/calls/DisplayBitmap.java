package nl.rnplus.olv.messages.calls;
//Added by RN+

import java.nio.ByteBuffer;

import nl.rnplus.olv.messages.LiveViewCall;
import nl.rnplus.olv.messages.MessageConstants;

public class DisplayBitmap extends LiveViewCall {

    private byte x;
    private byte y;
    private byte[] image;

    public DisplayBitmap(byte x, byte y, byte[] image) {
        super(MessageConstants.MSG_DISPLAYBITMAP);
        this.x = x;
        this.y = y;
        this.image = image;
    }

    /*
     * (non-Javadoc)
     * @see net.sourcewalker.olv.messages.LiveViewRequest#getPayload()
     */
    @Override
    protected byte[] getPayload() {
        int size = 3 + image.length;
        ByteBuffer buffer = ByteBuffer.allocate(size);
        buffer.put((byte) x);
        buffer.put((byte) y);
        buffer.put((byte) 1);
        buffer.put(image);
        return buffer.array();
    }
}
