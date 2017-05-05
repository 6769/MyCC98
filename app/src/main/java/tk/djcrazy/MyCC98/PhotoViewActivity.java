package tk.djcrazy.MyCC98;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.Menu;
import com.google.inject.Inject;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.FileOutputStream;

import roboguice.inject.ContentView;
import roboguice.inject.InjectExtra;
import roboguice.inject.InjectView;
import tk.djcrazy.MyCC98.util.Intents;
import tk.djcrazy.MyCC98.util.ToastUtils;
import tk.djcrazy.MyCC98.util.ViewUtils;
import tk.djcrazy.libCC98.NewCC98Service;
import tk.djcrazy.libCC98.util.RequestResultListener;
import uk.co.senab.photoview.PhotoViewAttacher;
import uk.co.senab.photoview.PhotoViewAttacher.OnViewTapListener;

@ContentView(R.layout.activity_photo_view)
public class
PhotoViewActivity extends BaseActivity implements RequestResultListener<Bitmap> {
    public static final String TAG = "PhotoViewActivity";
    public static final String SUF_FIX = ".jpg";

    @InjectExtra(value = Intents.EXTRA_DOWNLOAD_LINK, optional = true)
    private String mPhotoUrl;
    @InjectView(R.id.photo_load_progress)
    private ProgressBar mProgressBar;
    @InjectView(R.id.photo_view)
    private ImageView mImageView;
    @Inject
    private NewCC98Service service;
    private Menu mOptionmenu;
    private Bitmap currentPhoto;
    private Thread backPhotoSaveActionThread;


    public static Intent createIntent(String url) {
        Log.i("PhotoViewActivity", "Load url:" + url);
        Intent intent = new Intents.Builder("photo.VIEW").downloadLink(url).toIntent();
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        configureActionBar();
        service.submitBitmapRequest(this.getClass(), mPhotoUrl, this);
    }

    private void configureActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setLogo(new BitmapDrawable(getResources(), service.getCurrentUserAvatar()));
        actionBar.setTitle(getString(R.string.activity_title_image_view));
    }

    private boolean saveCurrentPhoto(final Bitmap photo) {
        if (photo == null) {
            Logger.t(TAG).w("used in NULL ptr!");
            return false;
        }
        if (backPhotoSaveActionThread != null && backPhotoSaveActionThread.isAlive()) {
            mkToast("还在保存中~");
            return false;
        }


        String filename = String.valueOf(System.currentTimeMillis()).concat(SUF_FIX);
        final File file_to_save = new File(Environment.getExternalStorageDirectory().getPath(), filename);
        if (file_to_save.exists()) {
            file_to_save.delete();
        }
        Runnable backPhotoSaveAction = new Runnable() {
            @Override
            public void run() {
                try {
                    FileOutputStream out = new FileOutputStream(file_to_save);
                    photo.compress(Bitmap.CompressFormat.JPEG, 50, out);
                    out.flush();
                    out.close();
                    mkToast(file_to_save.getPath());
                    Log.i(TAG, "photo saved");
                } catch (Exception e) {
                    Logger.t(TAG).e(e, "UnExpected.Error");
                    mkToast("图片保存失败");
                }
            }
        };
        backPhotoSaveActionThread = new Thread(backPhotoSaveAction, "backPhotoSaveAction");
        backPhotoSaveActionThread.start();


        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.mOptionmenu = menu;
        getSupportMenuInflater().inflate(R.menu.menu_photo_view, mOptionmenu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(
            com.actionbarsherlock.view.MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.menu_photo_view_save:

                return saveCurrentPhoto(currentPhoto);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onRequestComplete(Bitmap result) {
        currentPhoto = result;
        ViewUtils.setGone(mProgressBar, true);
        if (Build.VERSION.SDK_INT >= 11) {
            changeRenderTypeIfNessary(result);
        }
        mImageView.setImageBitmap(result);
        ViewUtils.setGone(mImageView, false);
        PhotoViewAttacher mAttacher = new PhotoViewAttacher(mImageView);
        mAttacher.setMaxScale((float) Math.max(
                Math.max(result.getHeight(), result.getWidth()) / 100,
                mAttacher.getMidScale() * 1.5));
        mAttacher.setOnViewTapListener(new OnViewTapListener() {
            @SuppressLint("NewApi")
            @Override
            public void onViewTap(View view, float x, float y) {
                if (getActionBar().isShowing()) {
                    getActionBar().hide();
                } else {
                    getActionBar().show();
                }
            }
        });
        getSupportActionBar().hide();
    }


    @Override
    protected void onStop() {
        super.onStop();
        service.cancelRequest(this.getClass());
    }

    @Override
    public void onRequestError(String msg) {
        ToastUtils.alert(this, msg);
    }


    private static final int GRAPH_LIMIT_SIZE = 4096;

    /**
     * OpenGL max bitmap supoorts 2048*2048, see http://stackoverflow.com/questions/10271020/bitmap-too-large-to-be-uploaded-into-a-texture
     */
    @TargetApi(11)
    private void changeRenderTypeIfNessary(Bitmap t) {
        if (t.getHeight() > GRAPH_LIMIT_SIZE || t.getWidth() > GRAPH_LIMIT_SIZE) {
            mImageView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            ToastUtils.info(PhotoViewActivity.this, too_large_image_stop_acclate);
        }
    }

    private static final String too_large_image_stop_acclate = "图片过大，关闭硬件加速";
}
