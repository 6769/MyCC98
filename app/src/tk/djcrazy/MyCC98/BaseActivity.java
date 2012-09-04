package tk.djcrazy.MyCC98;

import com.github.rtyley.android.sherlock.roboguice.activity.RoboSherlockFragmentActivity;

import roboguice.activity.RoboActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;

public class BaseActivity extends RoboSherlockFragmentActivity { 
	
	@Override
	public void onStart() {
		super.onStart();
//		FlurryAgent.onStartSession(this, "5EXV7SIGMTTDKYNXTKR4");
	}

	@Override
	public void onStop() {
		super.onStop();
//		FlurryAgent.onEndSession(this);
	}
	
	public void onCreate(Bundle savedInstanceState) {
		setTheme(com.actionbarsherlock.R.style.Theme_Sherlock);
		super.onCreate(savedInstanceState);
 	}
	 
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			finish();
			overridePendingTransition(R.anim.backward_activity_move_in,
					R.anim.backward_activity_move_out);
			return true;
		}
		return false;
	}

}
