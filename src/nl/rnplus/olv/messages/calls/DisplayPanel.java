package nl.rnplus.olv.messages.calls;

/* Renze Nicolai
 * Added 6-10-2012
 * DisplayPanel.java
 * Show a panel with an image and text on the LiveView
 */

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import nl.rnplus.olv.messages.LiveViewCall;
import nl.rnplus.olv.messages.MessageConstants;

public class DisplayPanel extends LiveViewCall
{
	private byte[] image;
	private String headerText;
	private String footerText;
	private boolean alertUser;
	
    public DisplayPanel(String headerText, String footerText, byte[] image, boolean alertUser)
    {
        super(MessageConstants.MSG_DISPLAYPANEL);
        this.image = image;
        this.headerText = headerText;
        this.footerText = footerText;
        this.alertUser = alertUser;
    }

    /*
     * (non-Javadoc)
     * @see net.sourcewalker.olv.messages.LiveViewRequest#getPayload()
     */
    @Override
    protected byte[] getPayload()
    {
    	try
    	{
	        byte[] headerTextArray = headerText.getBytes("iso-8859-1");//iso-8859-1 (UTF-8 is not working)
	    	byte[] footerTextArray = footerText.getBytes("iso-8859-1");
	    	String unusedText = "";
	    	byte[] unusedTextArray = unusedText.getBytes("iso-8859-1");
	        int size = 15 + headerTextArray.length + footerTextArray.length + image.length;
	        ByteBuffer buffer = ByteBuffer.allocate(size);
	        buffer.put((byte) 0);
	        buffer.putShort((short) 0);
	        buffer.putShort((short) 0);
	        buffer.putShort((short) 0);
	        if (alertUser)
	        {
	        	buffer.put((byte) 80); //id (alert)
	        }
	        else
	        {
	        	buffer.put((byte) 81); //id (no alert)
	        }
	        buffer.put((byte) 0); //0 is for plaintext vs bitmapimage (1) strings
	        buffer.putShort((short) headerTextArray.length); 
	        buffer.put(headerTextArray);
	        buffer.putShort((short) unusedTextArray.length); //Needed for the protocol...
	        buffer.put(unusedTextArray);
	        buffer.putShort((short) footerTextArray.length); 
	        buffer.put(footerTextArray);
	        //buffer.putShort((short)image.length);
	        buffer.put(image);
	        return buffer.array();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Encoding not found: " + e.getMessage(),
                    e);
        }
    }
}
