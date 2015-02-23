/*
 * This file is contributed to OLV by Basty149
 */

package nl.rnplus.olv.content;

public class ContentNotification {
	private String title;
	private String content;
	private long timestamp;
	
	public ContentNotification() {
		super();
	}
	
	
	public ContentNotification(String title, String content, long timestamp) {
		super();
		this.title = title;
		this.content = content;
		this.timestamp = timestamp;
	}


	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
	
}