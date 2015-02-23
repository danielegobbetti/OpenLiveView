package nl.rnplus.olv.messages.calls;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import nl.rnplus.olv.messages.LiveViewCall;
import nl.rnplus.olv.messages.MessageConstants;
import nl.rnplus.olv.messages.UShort;

public class SetVibrate extends LiveViewCall {

    private final UShort delay;
    private final UShort time;

    public SetVibrate(int delay, int time) {
        super(MessageConstants.MSG_SETVIBRATE);
        this.delay = new UShort((short) delay);
        this.time = new UShort((short) time);
    }

    /*
     * (non-Javadoc)
     * @see net.sourcewalker.olv.messages.LiveViewRequest#getPayload()
     */
    @Override
    protected byte[] getPayload() {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.order(ByteOrder.BIG_ENDIAN);
        buffer.putShort(delay.getValue());
        buffer.putShort(time.getValue());
        return buffer.array();
    }
}
