package tk.djcrazy.MyCC98;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.baidu.mobstat.StatService;
import com.github.rtyley.android.sherlock.roboguice.activity.RoboSherlockActivity;
import com.orhanobut.logger.Logger;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

public class BaseActivity extends SwipeBackActivity {

	@Override
	protected void onResume() {
 		super.onResume();
		StatService.onResume(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		StatService.onPause(this);
	}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Crouton.cancelAllCroutons();
    }

    protected void mkToast(final String msg) {
        final Activity current_instance = this;
        try {
            Toast.makeText(current_instance, msg, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Logger.e(e, "Move to UI thread:%s", msg);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(current_instance, msg, Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

 }
