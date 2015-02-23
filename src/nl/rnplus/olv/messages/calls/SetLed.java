package nl.rnplus.olv.messages.calls;
//Added by RN+

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import android.graphics.Color;

import nl.rnplus.olv.messages.LiveViewCall;
import nl.rnplus.olv.messages.MessageConstants;
import nl.rnplus.olv.messages.UShort;

public class SetLed extends LiveViewCall {

	//Python:
	//def EncodeSetLED(r, g, b, delayTime, onTime):
	//	return EncodeLVMessage(MSG_SETLED, struct.pack(">HHH", ((r & 0x31) << 10) | ((g & 0x31) << 5) | (b & 0x31), delayTime, onTime))
	
	private final short color;
	private final UShort delayTime;
	private final UShort onTime;
	
    public SetLed(int color_in, int delayTime, int onTime) {
        super(MessageConstants.MSG_SETLED);
        this.color = colorToRGB565(color_in);//new UShort((short) (((r & 0x31) << 10) | ((g & 0x31) << 5) | (b & 0x31)));
        this.delayTime = new UShort((short) delayTime);
        this.onTime = new UShort((short) onTime);
    }

    /*
     * (non-Javadoc)
     * @see net.sourcewalker.olv.messages.LiveViewRequest#getPayload()
     */
    @Override
    protected byte[] getPayload() {
        ByteBuffer buffer = ByteBuffer.allocate(6); //3*2
        buffer.order(ByteOrder.BIG_ENDIAN);
        buffer.putShort(color);
        buffer.putShort(delayTime.getValue());
        buffer.putShort(onTime.getValue());
        return buffer.array();
    }
    
	public static short colorToRGB565(int color) {
		int r = Color.red(color) >> 3;
		int g = Color.green(color) >> 2;
		int b = Color.blue(color) >> 3;
		return (short)((r<<(5+6)) + (g << 5) + (b));
	}    

}
