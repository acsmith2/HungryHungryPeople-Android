package hungry.hungry.people;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class Result extends Activity implements View.OnClickListener {
	private static final String TAG = Result.class.getSimpleName();
	View callBtn;
	View mapBtn;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.result);

		callBtn = findViewById(R.id.call);
		mapBtn = findViewById(R.id.mapit);

		callBtn.setOnClickListener(this);
		mapBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.call) {
			call();
		} else if (v.getId() == R.id.mapit) {
			map();
		}
	}

	private void call() {
		try {
			Intent callIntent = new Intent(Intent.ACTION_CALL);
			// TODO
			callIntent.setData(Uri.parse("tel:123456789"));
			startActivity(callIntent);
		} catch (ActivityNotFoundException e) {
			Log.e(TAG, "Call failed", e);
		}
	}

	private void map() {
		Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("google.navigation:q=731+market+st+san+francsico"));
		startActivity(intent);
	}
}
