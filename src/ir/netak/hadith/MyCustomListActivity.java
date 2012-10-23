package ir.netak.hadith;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;

public class MyCustomListActivity extends ListActivity {
	public String fonts = new String();
	public String fa = new String();
	public Typeface face;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		SharedPreferences SP = PreferenceManager
				.getDefaultSharedPreferences(getBaseContext());
		fa = SP.getString("FaMethod", "1");
		fonts = SP.getString("Font", "none");
		if (fonts.compareTo("none") == 0) {
			face = Typeface.DEFAULT;
		} else {
			face = Typeface.createFromAsset(getAssets(), "font/" + fonts + "");
		}

	}

	public String farsi(String str) {
		String tmp = new String();
		if (Integer.valueOf(fa) == 1) {
			tmp = Farsi.Convert(str);
		} else if (Integer.valueOf(fa) == 2) {
			tmp = str;
		} else if (Integer.valueOf(fa) == 3) {
			tmp = PersianReshape.reshape(str);
		}
		return tmp;
	}

	/* Creates the menu items */

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		menu.add(0, 0, 0, farsi("تنظیمات"));
		return true;
	}

	/* Handles item selections */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 0:
			Intent intent = new Intent(this, Setting.class);
			startActivity(intent);
			return true;
		}
		return false;
	}

}
