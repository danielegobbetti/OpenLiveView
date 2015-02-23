package nl.rnplus.olv;

import java.util.List;

import nl.rnplus.olv.R;
import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class AppDialog extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		PackageManager pm = getApplicationContext().getPackageManager();
		ApplicationInfo ai = null;
		List<PackageInfo> pkgList = pm.getInstalledPackages(0);
		String[] pkgNames = new String[pkgList.size()];
		String[] appNames = new String[pkgList.size()];
		Drawable[] icons = new Drawable[pkgList.size()];
		for(int i = 0;i<pkgList.size();i++){
			try{
				ai = pkgList.get(i).applicationInfo;
			}catch(Exception e)
			{
				ai = null;
			}
			pkgNames[i] = pkgList.get(i).packageName;
			appNames[i] = (ai != null ? pm.getApplicationLabel(ai).toString() : pkgList.get(i).packageName);
			icons[i] = (ai != null ? pm.getApplicationIcon(ai) : getResources().getDrawable(R.drawable.ic_default) );
		}
		AppArrayAdapter adapter = new AppArrayAdapter(this, appNames, pkgNames, icons);
		setListAdapter(adapter);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id){
		String item = (String) getListAdapter().getItem(position);
		Intent intent = new Intent();
		intent.putExtra("pkgName", item);
		setResult(42, intent);
		finish();
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
	    super.onConfigurationChanged(newConfig);
	}
}
