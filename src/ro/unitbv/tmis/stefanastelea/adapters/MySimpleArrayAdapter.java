package ro.unitbv.tmis.stefanastelea.adapters;

import java.util.List;

import ro.unitbv.tmis.stefanastelea.R;
import ro.unitbv.tmis.stefanastelea.datamodel.Project;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MySimpleArrayAdapter extends ArrayAdapter<Project> {
	private final Context context;
	private final List<Project> values;

	public MySimpleArrayAdapter(Context context, List<Project> values) {
		super(context, R.layout.rowlayout, values);
		this.context = context;
		this.values = values;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.rowlayout, parent, false);
		TextView textViewName = (TextView) rowView.findViewById(R.id.title);
		TextView textViewDescription = (TextView) rowView
				.findViewById(R.id.description);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
		textViewName.setText(values.get(position).getName());
		textViewDescription.setText(values.get(position).getDescription());

		imageView.setImageResource(R.drawable._logo_small);

		return rowView;
	}
}