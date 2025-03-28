package com.nas.alreem.activity.gallery;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.nas.alreem.R;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;



import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * Created by Rijo on 25/1/17.
 */
public class VideosPlayerViewActivity extends Activity {
    Bundle extras;
    String url;
    String video_type;
    Context mContext;
   // GiraffePlayer player;
    //String headName;
    Activity activity;
    ImageView downloadimageView;
    ImageView share;
    int count;
    NotificationManager mNotifyManager;
    NotificationCompat.Builder mBuilder;
    int id = 1;
    String fileName;
    Intent intent;
    long total = 0;
    public static final int REQUEST_READWRITE_PERMISSION_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_video_player);
        mContext = this;
        activity = this;

        if (CheckPermissions()) {
        } else {

            RequestPermissions();
        }


        extras = getIntent().getExtras();
        if (extras != null) {
            url = extras.getString("video_url");
            video_type = extras.getString("video_type");
        }

        fileName="NAS_VIDEO_" + System.currentTimeMillis() + ".mp4";
        downloadimageView = (ImageView) findViewById(R.id.download);
        share = (ImageView) findViewById(R.id.share);
        if (video_type.equals("Youtube"))
        {
            share.setVisibility(View.GONE);
            downloadimageView.setVisibility(View.GONE);
        }
        else
        {
            share.setVisibility(View.VISIBLE);
            downloadimageView.setVisibility(View.VISIBLE);
        }
        WebView webView = findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true); // Enable JavaScript if necessary

        webView.setWebViewClient(new WebViewClient());

        String videoUrl = url;
        webView.loadUrl(videoUrl);
        /*player = new GiraffePlayer(this);
        player.play(url.replaceAll(" ","%20"));
        player.onComplete(new Runnable() {
            @Override
            public void run() {
    //callback when video is finish
                Toast.makeText(getApplicationContext(), "Play completed", Toast.LENGTH_SHORT).show();
            }
        }).onInfo(new GiraffePlayer.OnInfoListener() {
            @Override
            public void onInfo(int what, int extra) {
                switch (what) {
                    case IMediaPlayer.MEDIA_INFO_BUFFERING_START:
//do something when buffering start
                        break;
                    case IMediaPlayer.MEDIA_INFO_BUFFERING_END:
//do something when buffering end
                        break;
                    case IMediaPlayer.MEDIA_INFO_NETWORK_BANDWIDTH:
//download speed
//                        ((TextView) findViewById(R.id.tv_speed)).setText(Formatter.formatFileSize(getApplicationContext(), extra)+"/s");
                        break;
                    case IMediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START:
//do something when video rendering
//                        findViewById(R.id.tv_speed).setVisibility(View.GONE);
                        break;
                }
            }
        }).onError(new GiraffePlayer.OnErrorListener() {
            @Override
            public void onError(int what, int extra) {
//UtilityMethods.showAlertFinish(activity,"Can't play this video","","OK",false);
                Toast.makeText(getApplicationContext(), "Can't play this video", Toast.LENGTH_SHORT).show();
            }
        });*/
        downloadimageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                new DownloadImage().execute(mImagesArrayListBg.get(position).getPhotoUrl().replaceAll(" ","%20"));
                File file = new File(Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DOWNLOADS),fileName);// "NAS_VIDEO_" + System.currentTimeMillis() + ".mp4");
                file.getParentFile().mkdirs();
//                down(url.replaceAll(" ", "%20"));
//                getLocalBitmapUri(imageView);
                GetImage imgtask = new GetImage(url.replaceAll(" ", "%20"), file);
                imgtask.execute();
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
// Get access to the URI for the bitmap
                shareIntent(url);
            }
        });

    }


    private void RequestPermissions() {
        ActivityCompat.requestPermissions(VideosPlayerViewActivity.this, new String[]{READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE}, REQUEST_READWRITE_PERMISSION_CODE);

    }

    private boolean CheckPermissions() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }

    public class GetImage extends AsyncTask<Void, String, Void> {
        File mFile;
        String URL;

        GetImage(String URLS, File file) {
            URL = URLS;
            mFile = file;
        }

        @Override
        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            downloadFile(url.replaceAll(" ", "%20"), mFile);
//            downloadFile("http://techslides.com/demos/sample-videos/small.mp4", mFile);
//            down(url.replaceAll(" ", "%20"));
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            //intent.setDataAndType(Uri.fromFile(mFile), "video/*");  //OLD DATA

            //ADDED NEWLY NTN
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(FileProvider.getUriForFile(mContext, mContext.getApplicationContext().getPackageName() + ".provider", mFile), "video/*");
            PendingIntent pIntent = PendingIntent.getActivity(activity, 0, intent, PendingIntent.FLAG_IMMUTABLE);
            mBuilder.setContentTitle(fileName)
                    .setContentText("Download completed")
                    .setSmallIcon(R.drawable.notify_small)
                    .setProgress(0, 0, false)
                    .setContentIntent(pIntent);
            mNotifyManager.notify(id, mBuilder.build());
            Toast.makeText(mContext, "Video downloaded successfully.", Toast.LENGTH_SHORT).show();


        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            showDialog(progress_bar_type);


            mNotifyManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            mBuilder = new NotificationCompat.Builder(activity);
            mBuilder.setContentTitle(fileName)
                    .setContentText("Download in progress.")
                    .setSmallIcon(R.drawable.notify_small);}

//        @Override
//        protected void onProgressUpdate(String... values) {
//            super.onProgressUpdate(values);
//
//
//        }
    }

    @Override
    protected void onPause() {
        super.onPause();
       /* if (player != null) {
            player.onPause();
        }*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        /*if (player != null) {
            player.onResume();
        }*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        /*if (player != null) {
           // player.onDestroy();
        }*/
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
       /* if (player != null) {
            player.onConfigurationChanged(newConfig);
        }*/
    }

    @Override
    public void onBackPressed() {
       /* if (player != null && player.onBackPressed()) {
            return;
        }*/
        super.onBackPressed();
    }

    public void downloadFile(String url, File outputFile) {
        try {

            /*      URL u = new URL(url);
            URLConnection conn = u.openConnection();
            int contentLength = conn.getContentLength();

            DataInputStream stream = new DataInputStream(u.openStream());
            byte[] buffer = new byte[contentLength];


            stream.readFully(buffer);
            stream.close();

            DataOutputStream fos = new DataOutputStream(new FileOutputStream(outputFile));
            fos.write(buffer);
            fos.flush();
            fos.close();*/


            URL u = new URL(url);
            URLConnection conn = u.openConnection();
            int contentLength = conn.getContentLength();

            DataInputStream stream = new DataInputStream(u.openStream());
            byte[] buffer = new byte[contentLength];
            DataOutputStream fos = new DataOutputStream(new FileOutputStream(outputFile));
            while ((count = stream.read(buffer)) != -1) {
                total += count;
                mBuilder.setProgress(100, (int) ((total * 100) / contentLength), false);
                // Displays the progress bar for the first time.
                mNotifyManager.notify(id, mBuilder.build());
                fos.write(buffer,0,count);


            }
            fos.flush();
            fos.close();
            stream.close();

        } catch (FileNotFoundException e) {

            e.printStackTrace();
            return; // swallow a 404
        } catch (IOException e) {
            e.printStackTrace();

            return; // swallow a 404
        }
    }

    private void shareIntent(String link) {


        Intent emailIntent = new Intent(
                Intent.ACTION_SEND);

        emailIntent.setType("text/plain");

        emailIntent.putExtra(Intent.EXTRA_SUBJECT, activity
                .getResources().getString(R.string.app_name));

        emailIntent.putExtra(
                Intent.EXTRA_TEXT,
                link);
        startActivity(Intent.createChooser(
                emailIntent, getApplicationContext().getResources().getString(R.string.app_name)));
    }
// Find your VideoView in your video_main.xml layout
       /* videoview = (VideoView) findViewById(R.id.VideoView);
        // Execute StreamVideo AsyncTask


        final CustomProgressBar pDialog = new CustomProgressBar(activity,
                R.drawable.spinner);

        if (!activity.isFinishing()) {
            if (pDialog != null) {
                pDialog.show();
            }
        }

        try {
            // Start the MediaController
            MediaController mediacontroller = new MediaController(
                    Watch_WebView_Activity.this);
            mediacontroller.setAnchorView(videoview);
            // Get the URL from String VideoURL
            Uri video = Uri.parse(url);
            videoview.setMediaController(mediacontroller);
            videoview.setVideoURI(video);

        } catch (Exception e) {
            Log.e("Alert", e.getMessage());
            e.printStackTrace();
        }

        videoview.requestFocus();
        videoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            // Close the progress bar and play the video
            public void onPrepared(MediaPlayer mp) {
//                pDialog.dismiss();
                try {
                    if (!activity.isFinishing()) {
                        if (pDialog != null) {
                            if (pDialog.isShowing()) {
                                pDialog.dismiss();
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ((Activity) activity).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            CustomDialog customDialog = new CustomDialog
                                    (activity, activity.getResources().getString(R.string.error_heading), activity.getResources().getString(R.string.common_error));
                            customDialog.show();
                        }
                    });
                }
                videoview.start();
            }
        });
        videoview.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer player) {
                Log.i("VideoView", "onCompletion()");
                finish();
            }
        });*/


    public String replace(String str) {
        return str.replaceAll(" ", "%20");
    }
  /*  @Override
    public void onBackPressed() {
// TODO Auto-generated method stub
        super.onBackPressed();
//        webView.stopLoading();
//        webView.loadData("", "text/html", "utf-8");
        finish();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
//        // Checks the orientation of the screen
//        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            DisplayMetrics metrics = new DisplayMetrics(); getWindowManager().getDefaultDisplay().getMetrics(metrics);
//            android.widget.LinearLayout.LayoutParams params = (android.widget.LinearLayout.LayoutParams) videoview.getLayoutParams();
//            params.width =  metrics.widthPixels;
//            params.height = metrics.heightPixels;
//            params.leftMargin = 0;
//            videoview.setLayoutParams(params);
//            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
//
//        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
//            DisplayMetrics metrics = new DisplayMetrics(); getWindowManager().getDefaultDisplay().getMetrics(metrics);
//            android.widget.LinearLayout.LayoutParams params = (android.widget.LinearLayout.LayoutParams) videoview.getLayoutParams();
//            params.width =  (int) (300*metrics.density);
//            params.height = (int) (250*metrics.density);
//            params.leftMargin = 30;
//            videoview.setLayoutParams(params);
//            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
//        }
    }*/

}