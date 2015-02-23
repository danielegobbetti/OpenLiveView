package nl.rnplus.olv;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.ServiceInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class PluginManagerActivity extends Activity {
	ProgressDialog loadingDialog;
	ArrayList<String> packages;
	ArrayList<String> packagenames;
	private PluginManagerActivity myself;
	TextView noplugins_text;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_plugin_manager);
		noplugins_text = (TextView)findViewById(R.id.noplugins_text);
		myself = this;
		loadingDialog = new ProgressDialog(this);
		loadingDialog.setMessage(getString(R.string.pluginmanager_popup_wait));		
	}

	@Override
	protected void onResume(){
		super.onResume();
		PackageManager pm = getPackageManager();
		
		packages = new ArrayList<String>();
		packagenames = new ArrayList<String>();
		   
		loadingDialog.show();
		final List <PackageInfo> appinstall=pm.getInstalledPackages(PackageManager.GET_PERMISSIONS|PackageManager.GET_RECEIVERS|
			      PackageManager.GET_SERVICES|PackageManager.GET_PROVIDERS);

			  for(PackageInfo pInfo:appinstall){
			      //PermissionInfo[] permission=pInfo.permissions;
			       String[] reqPermission=pInfo.requestedPermissions;
			       ServiceInfo[] services=pInfo.services;
			       ProviderInfo[] providers=pInfo.providers;

			  int versionCode=pInfo.versionCode;
			  //Log.d("versionCode-package ",Integer.toString(versionCode));
			  //Log.d("Installed Applications", pInfo.applicationInfo.loadLabel(pm).toString());
			  //Log.d("packegename",pInfo.packageName.toString());
			  if(reqPermission!=null)
			    for(int i=0;i<reqPermission.length;i++)
			       //Log.d("permission list",reqPermission[i]);
			       if (reqPermission[i].equals("nl.rnplus.olv.permission.plugin")) {
			    	   packages.add(pInfo.packageName.toString());
			    	   packagenames.add(pInfo.applicationInfo.loadLabel(pm).toString());
			       }
			  }
	    loadingDialog.dismiss();
		
		ListView listView = (ListView) findViewById(R.id.mylist);
				
		String [] values = packagenames.toArray(new String[packagenames.size()]);
		
		if (packagenames.isEmpty()) {
			noplugins_text.setVisibility(TextView.VISIBLE);
		} else {
			noplugins_text.setVisibility(TextView.INVISIBLE);
		}
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, values);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				String [] packagelist = packages.toArray(new String[packages.size()]);
				AlertDialog.Builder builder = new AlertDialog.Builder(myself);
		        /*builder.setTitle("Plugin");
		        builder.setMessage(packagelist[position]);
		        builder.setPositiveButton(getString(R.string.close_btn), null);
		        builder.show();*/
				Log.w("Debug", "Starting settings for "+packagelist[position]+"...");
				try {
					Intent intent = new Intent();
					intent.setComponent(new ComponentName(packagelist[position], packagelist[position]+".PluginSettings"));
					startActivity(intent);
					Log.w("Debug", "Done.");
				} catch (Exception e) {
					Log.w("Debug", "Failed: the PluginSettings activity is missing.");
					builder.setTitle("Sorry");
			        builder.setMessage("This plugin ("+packagelist[position]+") has no settings.");
			        builder.setPositiveButton(getString(R.string.close_btn), null);
			        builder.show();
				}
				onResume();
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.pluginmanager_menu, menu);
	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		AlertDialog.Builder builder;
		switch(item.getItemId()){
		case R.id.menu_pluginmanagerhelp:
			builder = new AlertDialog.Builder(this);
			builder.setMessage(R.string.pluginmanager_menu_help_text);
			builder.setPositiveButton("Close", null);
			builder.show();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
	    super.onConfigurationChanged(newConfig);
	}
}
