package com.nas.alreem.activity.notifications

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.nas.alreem.R
import com.nas.alreem.activity.home.HomeActivity
import com.nas.alreem.activity.notifications.model.MessageDetailApiModel
import com.nas.alreem.activity.notifications.model.MessageDetailModel
import com.nas.alreem.activity.notifications.model.MessageDetailNotificationModel
import com.nas.alreem.constants.ConstantFunctions
import com.nas.alreem.constants.DialogFunctions
import com.nas.alreem.constants.PreferenceManager
import com.nas.alreem.rest.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tcking.github.com.giraffeplayer.GiraffePlayer
import tv.danmaku.ijk.media.player.IMediaPlayer
private var mediaplayer: MediaPlayer? = null
private var handler2 = Handler()
private var seebbar: SeekBar? = null
class AudioPlayerDetailNew:AppCompatActivity() {
    lateinit var extras: Bundle
    lateinit var audio_title: String
    lateinit var audio_id: String
    lateinit var audio_updated: String
    private lateinit var relativeHeader: RelativeLayout
    private lateinit var backRelative: RelativeLayout
    private lateinit var logoClickImgView: ImageView
    private lateinit var btn_left: ImageView
    private lateinit var heading: TextView
    lateinit var playbutton_audio:ImageView
    lateinit var duration_time:TextView
    lateinit var textcurrent_time:TextView
    lateinit var back_arrow:ImageView
    var mesageDetailList = ArrayList<MessageDetailNotificationModel>()
    lateinit var mContext: Context
    var alert_type: String = ""
    var created_at: String = ""
    var title: String = ""
    var message: String = ""
    var updated_at: String = ""
    var url: String = ""
    var flag:Boolean = true
    private lateinit var progressDialog: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audio_player_details_new)
        extras = intent.extras!!
        audio_id = extras.getString("audio_id")!!
        audio_title = extras.getString("audio_title")!!
        audio_updated = extras.getString("audio_updated")!!

        mContext = this
        heading = findViewById(R.id.heading)
        btn_left = findViewById(R.id.btn_left)
        logoClickImgView = findViewById(R.id.logoClickImgView)
        backRelative = findViewById(R.id.backRelative)
        heading.text = "Notification"
        seebbar = findViewById(R.id.seebbar)
        playbutton_audio = findViewById(R.id.playbutton)
        textcurrent_time = findViewById(R.id.textcurrent_time)
        duration_time = findViewById(R.id.duration_time)
        back_arrow=findViewById(R.id.back_arrow)
        mediaplayer = MediaPlayer()
        seebbar!!.max = 100
        btn_left.setOnClickListener(View.OnClickListener {
            finish()
        })
        backRelative.setOnClickListener(View.OnClickListener {
            finish()
        })
        logoClickImgView.setOnClickListener(View.OnClickListener {
            val intent = Intent(mContext, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        })

        progressDialog = findViewById(R.id.progressDialog)
        val aniRotate: Animation =
            AnimationUtils.loadAnimation(mContext, R.anim.linear_interpolator)
        progressDialog.startAnimation(aniRotate)
        playbutton_audio.setOnClickListener {
            if(flag)
            {

                playbutton_audio.setImageResource(R.drawable.ic_baseline_pause_circle_outline_24)
                mediaplayer!!.start()
            }
            else
            {
                playbutton_audio.setImageResource(R.drawable.ic_baseline_play_circle_outline_24)
                mediaplayer!!.pause()
            }
            flag = !flag
        }
        audiodetails()


    }


    fun audiodetails() {
        val token = PreferenceManager.getaccesstoken(mContext)
        val studentbody= MessageDetailApiModel(audio_id)
        progressDialog.visibility = View.VISIBLE
        val call: Call<MessageDetailModel> = ApiClient.getClient.notifictaionDetail(studentbody,"Bearer "+token)
        call.enqueue(object : Callback<MessageDetailModel> {
            override fun onFailure(call: Call<MessageDetailModel>, t: Throwable) {
                  progressDialog.visibility = View.GONE
                Log.e("Error", t.localizedMessage)
                progressDialog.visibility = View.GONE
            }
            override fun onResponse(call: Call<MessageDetailModel>, response: Response<MessageDetailModel>) {
                progressDialog.visibility = View.GONE
                 progressDialog.visibility = View.GONE
                if (response.body()!!.status==100)

                {
                    title = response.body()!!.responseArray.notificationArray.title
                    message = response.body()!!.responseArray.notificationArray.message
                    alert_type = response.body()!!.responseArray.notificationArray.alert_type
                    created_at = response.body()!!.responseArray.notificationArray.created_at
                    updated_at = response.body()!!.responseArray.notificationArray.updated_at
                    url = response.body()!!.responseArray.notificationArray.url
                    if (mediaplayer!!.isPlaying()) {
                        handler2.removeCallbacks(updater)
                        mediaplayer!!.pause()
                        playbutton_audio.setImageResource(R.drawable.ic_baseline_pause_circle_outline_24)
                    } else {
                        Log.e("suess","success")
                        mediaplayer!!.start()
                        playbutton_audio.setImageResource(R.drawable.ic_baseline_pause_circle_outline_24)
                        try {
                            updateseekbar()
                        }catch (e:Exception)
                        {

                        }
                    }
                    setUpMediaPlayer(url)

                   // println("MSGRESPONSEAUDIO:" + response.body()!!.responseArray.notificationArray.url)
                }   else
                {

                    DialogFunctions.commonErrorAlertDialog(mContext.resources.getString(R.string.alert), ConstantFunctions.commonErrorString(response.body()!!.status), mContext)
                }


            }

        })

    }
    fun setUpMediaPlayer( filename: String) {




        try {

            mediaplayer!!.setDataSource(filename)

            mediaplayer!!.setOnPreparedListener(MediaPlayer.OnPreparedListener { mp -> mp.start() })
            mediaplayer!!.prepare()
            duration_time.setText("/" + milliseconds(player!!.getDuration().toLong()))

        } catch (exception: Exception) {
            //Toast.makeText(this, "failed to load audio" + exception.getMessage(), Toast.LENGTH_SHORT).show();
            println("failed for load" + exception.message)
        }


    }
    val updater = Runnable {
        try {
            updateseekbar()
            val currentduration: Long = player!!.getCurrentPosition().toLong()
            textcurrent_time.text=(milliseconds(currentduration).toString())
        }catch (e:Exception)
        {

        }


    }
    fun updateseekbar() {
        Log.e("success","success")

        Log.e("dfgdh","success")
        try {
            seebbar!!.setProgress((mediaplayer!!.getCurrentPosition().toFloat() / mediaplayer!!.getDuration() * 100).toInt())
            System.out.print("seekbar"+seebbar)
            handler2.postDelayed(updater, 1000)
        }catch (e:InterruptedException)
        {
            e.printStackTrace();
        }

        /* Toast.makeText(this, "successs", Toast.LENGTH_SHORT).show();*/

    }
    fun milliseconds(milliscnd: Long): String? {
        var timer = ""
        val secondString: String
        val hour = (milliscnd / (1000 * 60 * 60)).toInt()
        val min = (milliscnd % (1000 * 60 * 60)).toInt() / (1000 * 60)
        val sec = (milliscnd % (1000 * 60 * 60)).toInt() % (1000 * 60) / 1000
        if (hour > 0) {
            timer = "$hour;"
        }
        secondString = if (sec < 10) {
            "0$sec"
        } else {
            "" + sec
        }
        timer = "$timer$min:$secondString"
        return timer
    }
    override fun onPause() {
        super.onPause()
        mediaplayer!!.pause()
    }


    override fun onDestroy() {
        super.onDestroy()
        mediaplayer!!.stop()
        mediaplayer!!.release()
    }



    override fun onBackPressed() {

        finish()

    }


}