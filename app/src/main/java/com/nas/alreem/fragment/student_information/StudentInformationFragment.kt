package com.nas.alreem.fragment.student_information

import android.animation.Animator
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.nas.alreem.R
import com.nas.alreem.activity.login.model.SignUpResponseModel
import com.nas.alreem.activity.payments.adapter.StudentListAdapter
import com.nas.alreem.activity.payments.model.StudentList
import com.nas.alreem.activity.payments.model.StudentListModel
import com.nas.alreem.constants.ApiClient
import com.nas.alreem.constants.ConstantFunctions
import com.nas.alreem.constants.DialogFunctions
import com.nas.alreem.constants.OnItemClickListener
import com.nas.alreem.constants.PreferenceManager
import com.nas.alreem.constants.addOnItemClickListener
import com.nas.alreem.fragment.payments.model.SendEmailApiModel
import com.nas.alreem.fragment.student_information.adapter.StudentInfoAdapter
import com.nas.alreem.fragment.student_information.model.StudentInfoApiModel
import com.nas.alreem.fragment.student_information.model.StudentInfoDetail
import com.nas.alreem.fragment.student_information.model.StudentInfoModel

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StudentInformationFragment : Fragment(){

    private lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var studentInfoRecycler: RecyclerView
    lateinit var studentNameTxt: TextView
    lateinit var studImg: ImageView
    var studentListArrayList = ArrayList<StudentList>()
    lateinit var studentName: String
    lateinit var studentId: String
    lateinit var studentImg: String
lateinit var email_icon : ImageView
var email : String=""
    private val EMAIL_PATTERN =
        "^[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-z0-9])?\\.)+[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?$"
    private val pattern = "^([a-zA-Z ]*)$"
    lateinit var progressDialog: RelativeLayout
    lateinit var imageView6: ImageView
    lateinit var imageView4: ImageView
    lateinit var mContext:Context
    private var currentAnimator: Animator? = null
    private var shortAnimationDuration: Int = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_student_information, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mContext = requireContext()
        PreferenceManager.setStudentID(mContext,"")
        PreferenceManager.setStudentName(mContext,"")
        PreferenceManager.setStudentPhoto(mContext,"")
        initializeUI()

        if (ConstantFunctions.internetCheck(mContext))
        {
            callStudentListApi()
        }
        else
        {
            DialogFunctions.showInternetAlertDialog(mContext)
        }


//        studImg.setOnClickListener {
//            zoomImageFromThumb(studImg, studentImg)
//
////            val dialog = activity?.let { it1 -> Dialog(it1) }
////            dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
////            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
////            dialog?.setCancelable(true)
////            dialog?.setContentView(R.layout.studentimg_zoomlayout)
////            val zoomedstud_img = dialog?.findViewById(R.id.zoomedstud_img) as ImageView
////
////            Glide.with(mContext)
////                .load(studentImg)
////                .into(zoomedstud_img)
////            dialog.show()
//        }
//        shortAnimationDuration = resources.getInteger(android.R.integer.config_shortAnimTime)

    }

    @SuppressLint("WrongViewCast")
    private fun initializeUI() {
        studentName=""
        studentImg=""
        studentId=""
        linearLayoutManager = LinearLayoutManager(mContext)
        studentInfoRecycler = view!!.findViewById(R.id.studentInfoRecycler) as RecyclerView
        email_icon = view!!.findViewById(R.id.email_icon) as ImageView
        studentNameTxt = view!!.findViewById(R.id.studentName) as TextView
        studImg = view!!.findViewById(R.id.studImg) as ImageView
        imageView6 = view!!.findViewById(R.id.imageView6) as ImageView
        imageView4 = view!!.findViewById(R.id.imageView4) as ImageView
        studentInfoRecycler.layoutManager = linearLayoutManager
        studentInfoRecycler.itemAnimator = DefaultItemAnimator()
        progressDialog = view!!.findViewById(R.id.progressDialog) as RelativeLayout
        progressDialog.visibility=View.VISIBLE
        val aniRotate: Animation =
            AnimationUtils.loadAnimation(mContext, R.anim.linear_interpolator)
        progressDialog.startAnimation(aniRotate)
        studentNameTxt.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                //your implementation goes here
                showStudentList(mContext,studentListArrayList)

            }
        })
        imageView6.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                //your implementation goes here
                showStudentList(mContext,studentListArrayList)

            }
        })
        imageView4.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                //your implementation goes here
                showStudentList(mContext,studentListArrayList)

            }
        })

    }

    fun callStudentListApi()
    {
        progressDialog.visibility = View.VISIBLE
        val token = PreferenceManager.getaccesstoken(mContext)
        val call: Call<StudentListModel> = ApiClient(mContext).getClient.studentList("Bearer "+token)
        call.enqueue(object : Callback<StudentListModel>{
            override fun onFailure(call: Call<StudentListModel>, t: Throwable) {
               // Log.e("Error", t.localizedMessage)
            }
            override fun onResponse(call: Call<StudentListModel>, response: Response<StudentListModel>) {
             //   val arraySize :Int =response.body()!!.responseArray!!.studentList.size
                progressDialog.visibility = View.GONE
                if (response.body()!!.status==100)
                {
                    studentListArrayList.addAll(response.body()!!.responseArray.studentList)
                    if (PreferenceManager.getStudentID(mContext).equals(""))
                    {
                        studentName=studentListArrayList.get(0).name
                        studentImg=studentListArrayList.get(0).photo
                        studentId=studentListArrayList.get(0).id
                        PreferenceManager.setStudentID(mContext,studentId)
                        PreferenceManager.setStudentName(mContext,studentName)
                        PreferenceManager.setStudentPhoto(mContext,studentImg)
                        studentNameTxt.text=studentName
                       if(!studentImg.equals(""))
                       {
                           Glide.with(mContext) //1
                               .load(studentImg)
                               .placeholder(R.drawable.student)
                               .error(R.drawable.student)
                               .skipMemoryCache(true) //2
                               .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                               .transform(CircleCrop()) //4
                               .into(studImg)
                       }
                        else{
                         studImg.setImageResource(R.drawable.student)
                       }

                    }
                    else{
                        studentName= PreferenceManager.getStudentName(mContext)!!
                        studentImg= PreferenceManager.getStudentPhoto(mContext)!!
                        studentId= PreferenceManager.getStudentID(mContext)!!
                        studentNameTxt.text=studentName
                        if(!studentImg.equals(""))
                        {
                            Glide.with(mContext) //1
                                .load(studentImg)
                                .placeholder(R.drawable.student)
                                .error(R.drawable.student)
                                .skipMemoryCache(true) //2
                                .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                                .transform(CircleCrop()) //4
                                .into(studImg)
                        }
                        else{
                            studImg.setImageResource(R.drawable.student)
                        }
                    }
                    if (ConstantFunctions.internetCheck(mContext))
                    {
                        if(studentListArrayList.size>0)
                        {
                            callStudentInfoApi()
                        }
                    }
                    else
                    {
                        DialogFunctions.showInternetAlertDialog(mContext)
                    }

                }
                else if(response.body()!!.status==116)
                {
                   /* var internetCheck = InternetCheckClass.isInternetAvailable(mContext)
                    if (internetCheck)
                    {
                        AccessTokenClass.getAccessToken(mContext)
                        callStudentListApi()
                    }
                    else{
                        InternetCheckClass.showSuccessInternetAlert(com.mobatia.bisad.fragment.home.mContext)
                    }*/

                }
                else
                {
                    DialogFunctions.commonErrorAlertDialog(mContext.resources.getString(R.string.alert), ConstantFunctions.commonErrorString(response.body()!!.status), mContext)

                }


            }

        })
    }

    fun callStudentInfoApi()
    {
        progressDialog.visibility = View.VISIBLE
        var studentInfoArrayList = ArrayList<StudentInfoDetail>()
        val token = PreferenceManager.getaccesstoken(mContext)
        val studentbody= StudentInfoApiModel(PreferenceManager.getStudentID(mContext)!!)
        val call: Call<StudentInfoModel> = ApiClient(mContext).getClient.studentInfo(studentbody,"Bearer "+token)
        call.enqueue(object : Callback<StudentInfoModel>{
            override fun onFailure(call: Call<StudentInfoModel>, t: Throwable) {
                progressDialog.visibility = View.GONE
            }
            override fun onResponse(call: Call<StudentInfoModel>, response: Response<StudentInfoModel>) {
                progressDialog.visibility = View.GONE
             //   val arraySize :Int =response.body()!!.responseArray!!.studentInfo.size
                if (response.body()!!.status==100)
                {

                    if(response.body()!!.responseArray.studentInfo.size>0)
                    {
                        studentInfoRecycler.visibility=View.VISIBLE
                        studentInfoArrayList.addAll(response.body()!!.responseArray.studentInfo)

                        val studentInfoAdapter = StudentInfoAdapter(studentInfoArrayList,mContext,email_icon)
                        studentInfoRecycler.adapter = studentInfoAdapter
/*if(PreferenceManager.getEmail(mContext).equals(""))
{
    email_icon.visibility=View.GONE
}
                        else
{
    email_icon.visibility=View.VISIBLE

}*/
                        email_icon.setOnClickListener {
                            showSendEmailDialog(mContext!!)

                        }

                    }
                    else
                    {
                        studentInfoRecycler.visibility=View.GONE
                        showSuccessAlert(mContext,"Student Information is not available.","Alert")
                    }

                }
                else if (response.body()!!.status==132)
                {
                    showSuccessAlert(mContext,"Student Information is not available.","Alert")
                }
                else
                {
                    //InternetCheckClass.checkApiStatusError(response.body()!!.status, mContext)

                }


            }

        })
    }
    private fun showSendEmailDialog(mContext: Context)
    {
        val dialog = Dialog(mContext)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.dialog_send_email)
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        val btn_submit = dialog.findViewById<Button>(R.id.submitButton)
        val btn_cancel = dialog.findViewById<Button>(R.id.cancelButton)
        val text_dialog = dialog.findViewById<EditText?>(R.id.text_dialog)
        val text_content = dialog.findViewById<EditText>(R.id.text_content)

        btn_cancel.setOnClickListener(View.OnClickListener {
            dialog.dismiss()
        })

        btn_submit.setOnClickListener {
            if (text_dialog.text.toString().trim().equals("")) {
                DialogFunctions.commonErrorAlertDialog(
                    mContext.resources.getString(R.string.alert), resources.getString(R.string.enter_subject),
                    mContext
                )


            } else {
                if (text_content.text.toString().trim().equals("")) {
                    DialogFunctions.commonErrorAlertDialog(
                        mContext.resources.getString(R.string.alert), resources.getString(R.string.enter_content),
                        mContext
                    )

                } else if (PreferenceManager.getEmail(mContext)!!.matches(EMAIL_PATTERN.toRegex())) {
                    if (text_dialog.text.toString().trim ().matches(pattern.toRegex())) {
                        if (text_content.text.toString().trim ()
                                .matches(pattern.toRegex())) {

                            if (ConstantFunctions.internetCheck(mContext))
                            {
                                callSendEmailToStaffApi(text_dialog.text.toString().trim(), text_content.text.toString().trim(), dialog)
                            }
                            else
                            {
                                DialogFunctions.showInternetAlertDialog(mContext)
                            }

                        } else {
                            val toast: Toast = Toast.makeText(mContext, mContext.getResources().getString(R.string.enter_valid_contents), Toast.LENGTH_SHORT)
                            toast.show()
                        }
                    } else {
                        val toast: Toast = Toast.makeText(
                            mContext,
                            mContext.getResources()
                                .getString(
                                    R.string.enter_valid_subjects
                                ),
                            Toast.LENGTH_SHORT
                        )
                        toast.show()
                    }
                } else {
                    val toast: Toast = Toast.makeText(
                        mContext,
                        mContext.getResources()
                            .getString(
                                R.string.enter_valid_email
                            ),
                        Toast.LENGTH_SHORT
                    )
                    toast.show()
                }
            }
        }
        dialog.show()
    }
    fun callSendEmailToStaffApi(
        title: String, message: String, dialog: Dialog)
    {
        val sendMailBody = SendEmailApiModel(PreferenceManager.getEmail(mContext)!!, title, message)
        val call: Call<SignUpResponseModel> = ApiClient(mContext).getClient.sendEmailStaff(sendMailBody, "Bearer " + PreferenceManager.getaccesstoken(mContext!!))
        call.enqueue(object : Callback<SignUpResponseModel> {
            override fun onFailure(call: Call<SignUpResponseModel>, t: Throwable) {
                //progressDialog.visibility = View.GONE
            }

            override fun onResponse(call: Call<SignUpResponseModel>, response: Response<SignUpResponseModel>) {
                val responsedata = response.body()
                //progressDialog.visibility = View.GONE
                if (responsedata != null) {
                    try {


                        if (response.body()!!.status==100) {
                            dialog.dismiss()
                            showSuccessAlertt(
                                mContext!!,
                                "Email sent Successfully ",
                                "Success",
                                dialog
                            )
                        }else {
                            DialogFunctions.commonErrorAlertDialog(
                                mContext!!.resources.getString(R.string.alert),
                                ConstantFunctions.commonErrorString(response.body()!!.status), mContext!!
                            )

                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

        })
    }
    fun showStudentList(context: Context ,mStudentList : ArrayList<StudentList>)
    {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialogue_student_list)
        var iconImageView = dialog.findViewById(R.id.iconImageView) as ImageView
        var btn_dismiss = dialog.findViewById(R.id.btn_dismiss) as Button
        var studentListRecycler = dialog.findViewById(R.id.studentListRecycler) as RecyclerView
        iconImageView.setImageResource(R.drawable.boy)
        //if(mSocialMediaArray.get())
        val sdk = Build.VERSION.SDK_INT
        if (sdk < Build.VERSION_CODES.JELLY_BEAN) {
            btn_dismiss.setBackgroundDrawable(
                mContext.resources.getDrawable(R.drawable.button_new)
            )
        } else {
            btn_dismiss.background = mContext.resources.getDrawable(R.drawable.button_new)
        }

        studentListRecycler.setHasFixedSize(true)
        val llm = LinearLayoutManager(mContext)
        llm.orientation = LinearLayoutManager.VERTICAL
        studentListRecycler.layoutManager = llm
        if(mStudentList.size>0)
        {
            val studentAdapter = StudentListAdapter(mContext,mStudentList)
            studentListRecycler.adapter = studentAdapter
        }

        btn_dismiss.setOnClickListener()
        {
            dialog.dismiss()
        }
        studentListRecycler.addOnItemClickListener(object: OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {
                // Your logic
                studentName=studentListArrayList.get(position).name
                studentImg=studentListArrayList.get(position).photo
                studentId=studentListArrayList.get(position).id
                PreferenceManager.setStudentID(mContext,studentId)
                PreferenceManager.setStudentName(mContext,studentName)
                PreferenceManager.setStudentPhoto(mContext,studentImg)
                studentNameTxt.text=studentName
                if(!studentImg.equals(""))
                {
                    Glide.with(mContext) //1
                        .load(studentImg)
                        .placeholder(R.drawable.student)
                        .error(R.drawable.student)
                        .skipMemoryCache(true) //2
                        .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                        .transform(CircleCrop()) //4
                        .into(studImg)
                }
                else{
                    studImg.setImageResource(R.drawable.student)
                }
                progressDialog.visibility=View.VISIBLE
                studentInfoRecycler.visibility=View.GONE

                callStudentInfoApi()
                dialog.dismiss()
            }
        })
        dialog.show()
    }

    fun showSuccessAlertt(context: Context, message: String, msgHead: String, mdialog: Dialog) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_common_error_alert)
        var iconImageView = dialog.findViewById(R.id.iconImageView) as ImageView
        var alertHead = dialog.findViewById(R.id.alertHead) as TextView
        var text_dialog = dialog.findViewById(R.id.messageTxt) as TextView
        var btn_Ok = dialog.findViewById(R.id.btn_Ok) as Button
        text_dialog.text = message
        alertHead.text = msgHead
        iconImageView.setImageResource(R.drawable.tick)
        btn_Ok.setOnClickListener()
        {
            dialog.dismiss()
            mdialog.dismiss()
        }
        dialog.show()
    }

    fun showSuccessAlert(context: Context,message : String,msgHead : String)
    {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_common_error_alert)
        var iconImageView = dialog.findViewById(R.id.iconImageView) as ImageView
        var alertHead = dialog.findViewById(R.id.alertHead) as TextView
        var text_dialog = dialog.findViewById(R.id.text_dialog) as TextView
        var btn_Ok = dialog.findViewById(R.id.btn_Ok) as Button
        text_dialog.text = message
        alertHead.text = msgHead
        iconImageView.setImageResource(R.drawable.exclamationicon)
        btn_Ok.setOnClickListener()
        {
            dialog.dismiss()

        }
        dialog.show()
    }

   /* private fun zoomImageFromThumb(thumbView: View, imageResId: String) {
        // If there's an animation in progress, cancel it
        // immediately and proceed with this one.
        currentAnimator?.cancel()

        // Load the high-resolution "zoomed-in" image.
        val expandedImageView: ImageView? = view?.findViewById(R.id.zoomedstud_img)
        if (expandedImageView != null) {
            Glide.with(mContext) //1
                .load(imageResId)
                .transform(CircleCrop()) //4
                .into(expandedImageView)
        }

        // Calculate the starting and ending bounds for the zoomed-in image.
        // This step involves lots of math. Yay, math.
        val startBoundsInt = Rect()
        val finalBoundsInt = Rect()
        val globalOffset = Point()

        // The start bounds are the global visible rectangle of the thumbnail,
        // and the final bounds are the global visible rectangle of the container
        // view. Also set the container view's offset as the origin for the
        // bounds, since that's the origin for the positioning animation
        // properties (X, Y).
        thumbView.getGlobalVisibleRect(startBoundsInt)
        view?.findViewById<View>(R.id.relMain)
            ?.getGlobalVisibleRect(finalBoundsInt, globalOffset)
        startBoundsInt.offset(-globalOffset.x, -globalOffset.y)
        finalBoundsInt.offset(-globalOffset.x, -globalOffset.y)

        val startBounds = RectF(startBoundsInt)
        val finalBounds = RectF(finalBoundsInt)

        // Adjust the start bounds to be the same aspect ratio as the final
        // bounds using the "center crop" technique. This prevents undesirable
        // stretching during the animation. Also calculate the start scaling
        // factor (the end scaling factor is always 1.0).
        val startScale: Float
        if ((finalBounds.width() / finalBounds.height() > startBounds.width() / startBounds.height())) {
            // Extend start bounds horizontally
            startScale = startBounds.height() / finalBounds.height()
            val startWidth: Float = startScale * finalBounds.width()
            val deltaWidth: Float = (startWidth - startBounds.width()) / 2
            startBounds.left -= deltaWidth.toInt()
            startBounds.right += deltaWidth.toInt()
        } else {
            // Extend start bounds vertically
            startScale = startBounds.width() / finalBounds.width()
            val startHeight: Float = startScale * finalBounds.height()
            val deltaHeight: Float = (startHeight - startBounds.height()) / 2f
            startBounds.top -= deltaHeight.toInt()
            startBounds.bottom += deltaHeight.toInt()
        }

        // Hide the thumbnail and show the zoomed-in view. When the animation
        // begins, it will position the zoomed-in view in the place of the
        // thumbnail.
        thumbView.alpha = 0f
        if (expandedImageView != null) {
            expandedImageView.visibility = View.VISIBLE
        }

        // Set the pivot point for SCALE_X and SCALE_Y transformations
        // to the top-left corner of the zoomed-in view (the default
        // is the center of the view).
        if (expandedImageView != null) {
            expandedImageView.pivotX = 0f
        }
        if (expandedImageView != null) {
            expandedImageView.pivotY = 0f
        }

        // Construct and run the parallel animation of the four translation and
        // scale properties (X, Y, SCALE_X, and SCALE_Y).
        currentAnimator = AnimatorSet().apply {
            play(
                ObjectAnimator.ofFloat(
                expandedImageView,
                View.X,
                startBounds.left,
                finalBounds.left)
            ).apply {
                with(ObjectAnimator.ofFloat(expandedImageView, View.Y, startBounds.top, finalBounds.top))
                with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X, startScale, 1f))
                with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_Y, startScale, 1f))
            }
            duration = shortAnimationDuration.toLong()
            interpolator = DecelerateInterpolator()
            addListener(object : AnimatorListenerAdapter() {

                override fun onAnimationEnd(animation: Animator) {
                    currentAnimator = null
                }

                override fun onAnimationCancel(animation: Animator) {
                    currentAnimator = null
                }
            })
            start()
        }

        // Upon clicking the zoomed-in image, it should zoom back down
        // to the original bounds and show the thumbnail instead of
        // the expanded image.
        if (expandedImageView != null) {
            expandedImageView.setOnClickListener {
                currentAnimator?.cancel()

                // Animate the four positioning/sizing properties in parallel,
                // back to their original values.
                currentAnimator = AnimatorSet().apply {
                    play(ObjectAnimator.ofFloat(expandedImageView, View.X, startBounds.left)).apply {
                        with(ObjectAnimator.ofFloat(expandedImageView, View.Y, startBounds.top))
                        with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X, startScale))
                        with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_Y, startScale))
                    }
                    duration = shortAnimationDuration.toLong()
                    interpolator = DecelerateInterpolator()
                    addListener(object : AnimatorListenerAdapter() {

                        override fun onAnimationEnd(animation: Animator) {
                            thumbView.alpha = 1f
                            expandedImageView.visibility = View.GONE
                            currentAnimator = null
                        }

                        override fun onAnimationCancel(animation: Animator) {
                            thumbView.alpha = 1f
                            expandedImageView.visibility = View.GONE
                            currentAnimator = null
                        }
                    })
                    start()
                }
            }
        }
    }*/

}




