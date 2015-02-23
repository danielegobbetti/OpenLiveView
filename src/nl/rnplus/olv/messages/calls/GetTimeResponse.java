package nl.rnplus.olv.messages.calls;
//Updated by RN+ to use Calendar in stead of Date

import java.nio.ByteBuffer;
import java.util.Calendar;
//import java.util.Date;
import nl.rnplus.olv.messages.LiveViewCall;
import nl.rnplus.olv.messages.MessageConstants;

public class GetTimeResponse extends LiveViewCall {

    private int time;
    private byte mode;
    
    public GetTimeResponse(boolean mode){//Date time) {
        super(MessageConstants.MSG_GETTIME_RESP);

        //this.time = (int) (time.getTime() / 1000);
        //this.time -= time.getTimezoneOffset() * 60;
        
        this.time = (int) (Calendar.getInstance().getTimeInMillis() / 1000);
        this.time += Calendar.getInstance().get(Calendar.ZONE_OFFSET)/1000;
        this.time += Calendar.getInstance().get(Calendar.DST_OFFSET) / 1000;
        if (mode) {
          this.mode = 0;
        } else {
          this.mode = 1;
        }
    }

    /*public GetTimeResponse() {
        this(new Date());
    }*/

    /*
     * (non-Javadoc)
     * @see net.sourcewalker.olv.messages.LiveViewRequest#getPayload()
     */
    @Override
    protected byte[] getPayload() {
        ByteBuffer buffer = ByteBuffer.allocate(5);
        buffer.putInt(time);
        buffer.put((byte) mode);
        return buffer.array();
    }

}
