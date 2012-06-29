package hungry.hungry.people;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class Muni extends Activity {
	GridView mGridView;

	final static String titles[] = {"Car", "Bus", "Bike", "Walk"};
	
	final static int images[] = {R.drawable.car, R.drawable.bus, R.drawable.bike, R.drawable.walk};
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.muni);
        
		mGridView = (GridView) findViewById(R.id.gridView2);
		mGridView.setAdapter(new MuniAdapter(this));
		mGridView.setOnItemClickListener(new OnMuniItemClickListener());
    }

	private class OnMuniItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View view, int pos, long id) {
			Intent i = new Intent(Muni.this, Result.class);
			startActivity(i);

		}
	}
	private class MuniAdapter extends BaseAdapter {
		private LayoutInflater mInflater;

		public MuniAdapter(Context context) {
			mInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			return titles.length;
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		/**
		 * Make a view to hold each row.
		 * 
		 * @see android.widget.ListAdapter#getView(int, android.view.View,
		 *      android.view.ViewGroup)
		 */
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;

			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.food_item, null);

				// Creates a ViewHolder and store references to the two children
				// views we want to bind data to.
				holder = new ViewHolder();
				holder.title = (TextView) convertView.findViewById(R.id.title);
				holder.image = (ImageView) convertView.findViewById(R.id.image);
				holder.image.setScaleType(ImageView.ScaleType.CENTER_CROP);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			// Bind the data efficiently with the holder.
			holder.title.setText(titles[position]);
			holder.image.setImageResource(images[position]);

			return convertView;
		}

		class ViewHolder {
			TextView title;
			ImageView image;
		}
	}

}