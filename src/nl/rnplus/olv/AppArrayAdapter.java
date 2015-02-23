package nl.rnplus.olv;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import nl.rnplus.olv.R;

public class AppArrayAdapter extends ArrayAdapter<String> {
	private final Context context;
	private final String[] names;
	private final Drawable[] icons;
	
	public AppArrayAdapter(Context context, String[] names, String[] packages, Drawable[] icons){
		super(context, R.layout.app_dialog_rowlayout, packages);
		this.context = context;
		this.names = names;
		this.icons = icons;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.app_dialog_rowlayout, parent, false);
		TextView textView = (TextView) rowView.findViewById(R.id.label);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
		textView.setText(names[position]);
		imageView.setImageDrawable(icons[position]);
		return rowView;
	}
}
