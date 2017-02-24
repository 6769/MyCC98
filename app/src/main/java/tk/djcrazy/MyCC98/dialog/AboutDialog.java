package tk.djcrazy.MyCC98.dialog;

import tk.djcrazy.MyCC98.R;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.widget.Button;

import com.orhanobut.logger.Logger;

public class AboutDialog extends Dialog {
    public static final String TAG="AboutDialog";
	WebView webView;
	Button okButton;
    Context upperContext;

	public AboutDialog(Context context) {
		super(context);
        upperContext=context;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_about);
		setTitle(setTitleVersion("About"));
		webView = (WebView) findViewById(R.id.about_web_view);
		okButton = (Button) findViewById(R.id.about_ok);
		webView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		Animation animation = AnimationUtils.loadAnimation(getContext(),
				R.anim.alpha_change);
		webView.startAnimation(animation);
		webView.loadUrl("file:///android_asset/about.html");
		okButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
                upperContext=null;
				dismiss();
			}
		});
	}


	private String setTitleVersion(String mainheader){
        StringBuilder cat=new StringBuilder(mainheader);
        String versionname=GetPackageInfomation.getPackageVersionName(upperContext);

        return cat.append('-').append(versionname).toString();
    }
    public static class GetPackageInfomation{
        public static String name;
        public static final String ERROR_NAME="VersionName Error";

        public static String getPackageVersionName(Context context){

            PackageManager pm =context.getPackageManager();

            try {
                PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
                //getPackageName()是你当前类的包名，0代表是获取版本信息
                name= pi.versionName;
                //int code = pi.versionCode;

            }catch (PackageManager.NameNotFoundException e){
                Logger.t(TAG).e(ERROR_NAME,e);
                name=ERROR_NAME;

            }
            return name;

        }
    }

}
