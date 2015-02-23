package nl.rnplus.olv;

import nl.rnplus.olv.data.LiveViewDbConstants;
import nl.rnplus.olv.data.LiveViewDbHelper;
import nl.rnplus.olv.data.Prefs;
import nl.rnplus.olv.receiver.LiveViewBrConstants;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
	
	private MainActivity myself;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myself = this;
        
        /**
         * Go to setup if its not completed yet
         */
        Prefs prefs = new Prefs(this);
        if (!prefs.getSetupCompleted()) {
            Intent myIntent = new Intent(this, ConfigWizardActivity.class);
            this.startActivity(myIntent);
            finish();
        }
        
        /**
         * Creating all buttons instances
         * */
        // Dashboard Settings button
        Button btn_settings = (Button) findViewById(R.id.btn_settings);
 
        // Dashboard Notifications button
        Button btn_notifications = (Button) findViewById(R.id.btn_notifications);
 
        // Add note button
        Button btn_add_note = (Button) findViewById(R.id.btn_add_note);
 
        // Expert button
        Button btn_expert = (Button) findViewById(R.id.btn_expert);
 
        // Plugins button
        Button btn_plugins = (Button) findViewById(R.id.btn_plugins);
 
        // About button
        Button btn_about = (Button) findViewById(R.id.btn_about);
        
        /**
         * Handling all button click events
         * */
 
        // Listening to Settings button click
        btn_settings.setOnClickListener(new View.OnClickListener() {
 
            public void onClick(View view) {
                // Launching Settings Screen
                Intent i = new Intent(getApplicationContext(), LiveViewPreferences.class);
                startActivity(i);
            }
        });
        
        // Listening to Notifications button click
        btn_notifications.setOnClickListener(new View.OnClickListener() {
 
            public void onClick(View view) {
                // Show all stored notifications
            	showAllStoredNotifications();
            }
        }); 
        
        // Listening to Add note button click
        btn_add_note.setOnClickListener(new View.OnClickListener() {
 
            public void onClick(View view) {
                // Add note
            	addNote();
            }
        });  
        
        // Listening to Expert settings button click
        btn_expert.setOnClickListener(new View.OnClickListener() {
 
            public void onClick(View view) {
            	openExpertSettings();
            }
        });  
        
        // Listening to Plugins button click
        btn_plugins.setOnClickListener(new View.OnClickListener() {
 
            public void onClick(View view) {
                openPluginManager();
            }
        }); 
        
        // Listening to About button click
        btn_about.setOnClickListener(new View.OnClickListener() {
 
            public void onClick(View view) {
                showAboutDialog();
            }
        });  
    }
      
    public void openExpertSettings() {
        Intent myIntent = new Intent(this, ExpertConfigActivity.class);
        this.startActivity(myIntent);
    }
    
    public void addNote() {
		final EditText input = new EditText(this);
		input.setText("");
		new AlertDialog.Builder(MainActivity.this)
		.setTitle(getString(R.string.main_add_note_btn_text))
		.setMessage(getString(R.string.add_note_help))
		.setView(input)
		.setPositiveButton(getString(R.string.ok_btn), new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int whichButton) {
		    	String value = input.getText().toString();		    	
                Intent add_alert_intent = new Intent(LiveViewBrConstants.ALERT_ADD);
                Bundle add_alert_bundle = new Bundle();
                add_alert_bundle.putString("contents", value);
                add_alert_bundle.putString("title", "Note");
                add_alert_bundle.putInt("type", LiveViewDbConstants.ALERT_NOTE);
                add_alert_bundle.putLong("timestamp", System.currentTimeMillis());
                add_alert_intent.putExtras(add_alert_bundle);
                sendBroadcast(add_alert_intent);         
				AlertDialog.Builder builder = new AlertDialog.Builder(myself);
				builder.setTitle("Info");
				builder.setMessage("Your note is added to the database.");
				builder.setPositiveButton(getString(R.string.close_btn), null);
				builder.show(); 
		    }
		}).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int whichButton) {
		        // Do nothing.
		    }
		}).show();     
    }
    
    public void showAboutDialog() {
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("About OpenLiveView");
        builder.setMessage(R.string.about);
        builder.setPositiveButton(getString(R.string.close_btn), null);
        builder.show();
    }
    
    public void deleteAllNotifications() {
    	try {
    		LiveViewDbHelper dbHelper;
    		dbHelper = new LiveViewDbHelper(this);	
    		dbHelper.openToWrite();
    		dbHelper.deleteAllAlerts();
    		dbHelper.close();
	    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
	        builder.setTitle("Info");
	        builder.setMessage("All stored notifications deleted.");
	        builder.setPositiveButton(getString(R.string.close_btn), null);
	        builder.show(); 
    	} catch(Exception e) {
	        AlertDialog.Builder builder = new AlertDialog.Builder(this);
	        builder.setTitle("Error");
	        builder.setMessage(e.toString());
	        builder.setPositiveButton(getString(R.string.close_btn), null);
	        builder.show();   		
    	}
    }
    
    public void openFilterSettings() {
    	Intent intent = new Intent(this, FilterEditor.class);
    	startActivity(intent);
    }
    
    public void openPluginManager() {
    	Intent intent = new Intent(this, PluginManagerActivity.class);
    	startActivity(intent);
    }
    
    public void showAllStoredNotifications() {
    	LiveViewDbHelper dbHelper;
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Stored notifications");
		dbHelper = new LiveViewDbHelper(this);	
		dbHelper.openToRead();
		String notifications = dbHelper.getAlertsAsString();
		dbHelper.close();
		if (notifications.equals("")) {
			builder.setMessage("There are currently no notifications in the database.");
		} else {
			builder.setMessage(notifications);
		}
		builder.setPositiveButton(getString(R.string.close_btn), null);
		builder.setNeutralButton("Filter settings...", 
		new OnClickListener() {
		public void onClick(DialogInterface dialog, int which) {
			openFilterSettings();
		}});
		if (notifications.equals("")==false) {
			builder.setNegativeButton("Wipe", 
	    	new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				deleteAllNotifications();
			}});
		}
		builder.show();
    }
}
