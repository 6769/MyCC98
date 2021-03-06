package tk.djcrazy.MyCC98.fragment;

import roboguice.inject.InjectView;
import tk.djcrazy.MyCC98.EditActivity;
import tk.djcrazy.MyCC98.HomeActivity;
import tk.djcrazy.MyCC98.LoginActivity;
import tk.djcrazy.MyCC98.ProfileActivity;
import tk.djcrazy.MyCC98.R;
import tk.djcrazy.MyCC98.SettingsActivity;
import tk.djcrazy.MyCC98.adapter.AccountListAdapter;
import tk.djcrazy.MyCC98.dialog.AboutDialog;
import tk.djcrazy.MyCC98.dialog.FeedbackDialog;
import tk.djcrazy.MyCC98.util.Intents;
import tk.djcrazy.libCC98.CachedCC98Service;
import tk.djcrazy.libCC98.ICC98UrlManager;
import tk.djcrazy.libCC98.data.LoginType;

import android.R.integer;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

import com.github.rtyley.android.sherlock.roboguice.fragment.RoboSherlockFragment;

import android.support.v4.app.FragmentManager;
import android.widget.Toast;

import com.google.inject.Inject;
 
public class HomeBehindMenuFragment extends RoboSherlockFragment implements OnItemLongClickListener, OnItemClickListener, android.view.View.OnClickListener {

	@InjectView(R.id.home_behind_listview)
	private ListView mAccountListView;
	
	@InjectView(R.id.add_caccount)
	private ImageView mAddAccountView;
	@InjectView(R.id.home_menu_profile)
	private Button mProfileButton;
	@InjectView(R.id.home_menu_about)
			private Button mAboutButton;
	@InjectView(R.id.home_menu_feedback)
	private Button mFeedbackButton;
	@InjectView(R.id.home_menu_setting)
	private Button mSettingButton;
	@Inject
	private CachedCC98Service service;
    @Inject
    private ICC98UrlManager urlManager;
	AccountListAdapter adapter;

    private ICC98UrlManager getUrlManager() {
        return urlManager;
    }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_home_menu, container, false);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		adapter = new AccountListAdapter(getActivity(), service.getusersInfo(),service.getUserAvatars());
 		mAccountListView.setAdapter(adapter);
 		mAccountListView.setOnItemClickListener(this);
 		mAddAccountView.setOnClickListener(this);
 		mProfileButton.setOnClickListener(this);
 		mFeedbackButton.setOnClickListener(this);
 		mSettingButton.setOnClickListener(this);
 		mAboutButton.setOnClickListener(this);
 		mAccountListView.setOnItemLongClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		final int pos = position;
		if (pos!=service.getusersInfo().currentUserIndex) {
			AlertDialog.Builder  builder = new Builder(getActivity());
			builder.setTitle("提醒").setMessage("确认切换账号？").setNegativeButton("取消", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});
			builder.setPositiveButton("确定", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					service.switchToUser(pos);
					dialog.dismiss();
					startActivity(new Intent(getActivity(), HomeActivity.class));
					getActivity().finish();
				}
			});
			builder.create().show();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.add_caccount:
			Intent intent = new Intents.Builder(getActivity(), LoginActivity.class).needLogin(true).toIntent();
			startActivity(intent);
			break;
		case R.id.home_menu_about:
			new AboutDialog(getActivity()).show();
			break;
		case R.id.home_menu_feedback:
			//tk.djcrazy.MyCC98.util.Intents.Builder builder = new Intents.Builder(getActivity(), EditActivity.class);
			//Intent intent1 = builder.requestType(EditActivity.REQUEST_PM)
			//		.pmToUser("MyCC.98").pmTitle("MyCC98软件反馈").toIntent();
			//startActivity(intent1);

            String msgshowFeedbackDialog="详情见论坛版面置顶客户端下载帖~";
            mkToast(msgshowFeedbackDialog,false);

            //showFeedbackDialog();
			break;
		case R.id.home_menu_profile:
			Intent profiIntent = new Intent();
			profiIntent.setClass(getActivity(), ProfileActivity.class);
			profiIntent.putExtra("userName", service.getCurrentUserName());
			startActivity(profiIntent);
			break;
		case R.id.home_menu_setting:
			startActivity(new Intent(getActivity(), SettingsActivity.class));
			break;
		default:
			break;
		}
	}

    private void showFeedbackDialog() {




        //2017-02-20 15:18:05 removed function;

        FragmentManager fm = getActivity().getSupportFragmentManager();
        FeedbackDialog feedbackDialog = new FeedbackDialog();
        String username = service.getCurrentUserName();
        feedbackDialog.setUserInfo(username, getUrlManager().getUserProfileUrl(LoginType.NORMAL, username));
        feedbackDialog.show(fm, "feedback_dialog");
    }

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		final int pos = position;
		if (pos!=service.getusersInfo().currentUserIndex) {
			AlertDialog.Builder  builder = new Builder(getActivity());
			builder.setTitle("提醒").setMessage("确认删除账号？").setNegativeButton("取消", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});
			builder.setPositiveButton("确定", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					service.deleteUserInfo(pos);
					adapter.notifyDataSetChanged();
 					dialog.dismiss();
 				}
			});
			builder.create().show();
		}
		return true;
	}

    private void mkToast(String msg,boolean showshort){
        if(showshort)
            Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(getActivity(),msg,Toast.LENGTH_LONG).show();
    }
}
