package com.nas.alreem.activity.cca.adapter

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nas.alreem.R
import com.nas.alreem.activity.cca.CCAsReviewAfterSubmissionActivity
import com.nas.alreem.activity.cca.model.CCACancelRequestModel
import com.nas.alreem.activity.cca.model.CCACancelResponseModel
import com.nas.alreem.activity.cca.model.CCAReviewAfterSubmissionModel
import com.nas.alreem.constants.ConstantFunctions
import com.nas.alreem.constants.PreferenceManager
import com.nas.alreem.rest.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class CCAfinalReviewAfterSubmissionAdapter(
  var  mContext: Context,
  var  mCCADetailModelArrayList: ArrayList<CCAReviewAfterSubmissionModel>
) :
    RecyclerView.Adapter<CCAfinalReviewAfterSubmissionAdapter.MyViewHolder>() {
   // lateinit var mCCADetailModelArrayList: ArrayList<CCAReviewAfterSubmissionModel>
    var dialog: Dialog

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var textViewCCADay: TextView
        var textViewCCAChoice1: TextView
        var textViewCCAChoice2: TextView
        var attendanceListIcon: ImageView
        var deleteChoice1: ImageView
        var deleteChoice2: ImageView
        var linearChoice1: LinearLayout
        var linearChoice2: LinearLayout
        var textViewCCAaDateItemChoice1: TextView
        var textViewCCAaDateItemChoice2: TextView
        var locationTxt: TextView
        var descriptionTxt: TextView
        var location2Txt: TextView
        var description2Txt: TextView
        var readMore: TextView
        var readMore1: TextView

        init {
            textViewCCAaDateItemChoice1 =
                view.findViewById<View>(R.id.textViewCCAaDateItemChoice1) as TextView
            textViewCCAaDateItemChoice2 =
                view.findViewById<View>(R.id.textViewCCAaDateItemChoice2) as TextView
            textViewCCADay = view.findViewById<View>(R.id.textViewCCADay) as TextView
            textViewCCAChoice1 = view.findViewById<View>(R.id.textViewCCAChoice1) as TextView
            textViewCCAChoice2 = view.findViewById<View>(R.id.textViewCCAChoice2) as TextView
            attendanceListIcon = view.findViewById<View>(R.id.attendanceListIcon) as ImageView
            deleteChoice1 = view.findViewById<View>(R.id.deleteChoice1) as ImageView
            deleteChoice2 = view.findViewById<View>(R.id.deleteChoice2) as ImageView
            linearChoice1 = view.findViewById<View>(R.id.linearChoice1) as LinearLayout
            linearChoice2 = view.findViewById<View>(R.id.linearChoice2) as LinearLayout
            locationTxt = view.findViewById<View>(R.id.locationTxt) as TextView
            descriptionTxt = view.findViewById<View>(R.id.descriptionTxt) as TextView
            description2Txt = view.findViewById<View>(R.id.description2Txt) as TextView
            location2Txt = view.findViewById<View>(R.id.location2Txt) as TextView
            readMore = view.findViewById<View>(R.id.readMore) as TextView
            readMore1 = view.findViewById<View>(R.id.readMore1) as TextView
        }
    }

    init {
        this.mContext = mContext
        this.mCCADetailModelArrayList = mCCADetailModelArrayList
        dialog = Dialog(mContext)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_attendance_list)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_cca_review_after_submit, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.textViewCCADay.setText(mCCADetailModelArrayList[position].day)
        if (mCCADetailModelArrayList[position].cca_item_description!!.length > 40) {
            holder.readMore1.visibility = View.VISIBLE
        } else {
            holder.readMore1.visibility = View.GONE
        }
        holder.attendanceListIcon.setOnClickListener {
            if (!mCCADetailModelArrayList[position].choice1
                    .equals("0") || !mCCADetailModelArrayList[position].choice1
                    .equals("-1") || !mCCADetailModelArrayList[position].choice2
                    .equals("0") || !mCCADetailModelArrayList[position].choice2
                    .equals("-1")
            ) {
                showAttendanceList(position)
            }
        }
        holder.readMore.setOnClickListener {
            Log.e("click1","Click1")
            ConstantFunctions.showDialogueWithOk(
                mContext,
                mCCADetailModelArrayList[position].cca_item_description_2!!,
                "Description"
            )
        }
        holder.description2Txt.setOnClickListener {
            ConstantFunctions.showDialogueWithOk(
                mContext,
                mCCADetailModelArrayList[position].cca_item_description_2!!,
                "Description"
            )
        }
        holder.readMore1.setOnClickListener {
            Log.e("click1","Click1")
            ConstantFunctions.showDialogueWithOk(
                mContext,
                mCCADetailModelArrayList[position].cca_item_description!!,
                "Description"
            )
        }
        holder.descriptionTxt.setOnClickListener {
            ConstantFunctions.showDialogueWithOk(
                mContext,
                mCCADetailModelArrayList[position].cca_item_description!!,
                "Description"
            )
        }
        if (mCCADetailModelArrayList[position].choice1.equals("0")) {
            holder.linearChoice1.visibility = View.GONE
            holder.textViewCCAChoice1.text = "Choice 1 : None"
        } else if (mCCADetailModelArrayList[position].choice1.equals("-1")) {
            holder.linearChoice1.visibility = View.GONE
            holder.textViewCCAChoice1.text = "Choice 1 : Nil"
        } else {
            holder.linearChoice1.visibility = View.VISIBLE
            holder.textViewCCAChoice1.setText(mCCADetailModelArrayList[position].choice1)
            if (mCCADetailModelArrayList[position].venue
                    .equals("0") || mCCADetailModelArrayList[position].venue
                    .equals("")
            ) {
                holder.locationTxt.visibility = View.GONE
            } else {
                holder.locationTxt.visibility = View.VISIBLE
                holder.locationTxt.text =
                    "Location            : " + mCCADetailModelArrayList[position].venue
            }
            System.out.println("DESC EDIT" + mCCADetailModelArrayList[position].cca_item_description)
            if (mCCADetailModelArrayList[position].cca_item_description
                    .equals("0") || mCCADetailModelArrayList[position].cca_item_description
                    .equals("")
            ) {
                holder.descriptionTxt.visibility = View.GONE
                holder.readMore1.visibility = View.GONE
            } else {
                holder.descriptionTxt.visibility = View.VISIBLE
                holder.readMore1.visibility = View.VISIBLE
                holder.descriptionTxt.text =
                    "Description      : " + mCCADetailModelArrayList[position].cca_item_description
            }
            if (mCCADetailModelArrayList[position].cca_item_start_time != null && mCCADetailModelArrayList[position].cca_item_end_time != null) {
                holder.textViewCCAaDateItemChoice1.visibility = View.VISIBLE
                holder.textViewCCAaDateItemChoice1.text = "(" + convertTimeToAMPM(
                    mCCADetailModelArrayList[position].cca_item_start_time
                ) + " - " + convertTimeToAMPM(
                    mCCADetailModelArrayList[position].cca_item_end_time
                ) + ")"
            } else if (mCCADetailModelArrayList[position].cca_item_start_time != null) {
                holder.textViewCCAaDateItemChoice1.visibility = View.VISIBLE
                holder.textViewCCAaDateItemChoice1.text = "(" + convertTimeToAMPM(
                    mCCADetailModelArrayList[position].cca_item_start_time
                ) + ")"
            } else if (mCCADetailModelArrayList[position].cca_item_end_time != null) {
                holder.textViewCCAaDateItemChoice1.visibility = View.VISIBLE
                holder.textViewCCAaDateItemChoice1.text = "(" + convertTimeToAMPM(
                    mCCADetailModelArrayList[position].cca_item_end_time
                ) + ")"
            } else {
                holder.textViewCCAaDateItemChoice1.visibility = View.GONE
            }
            if (mCCADetailModelArrayList[position].attending_status.equals("1")) {
                holder.deleteChoice1.setImageResource(R.drawable.delete_red)
            } else if (mCCADetailModelArrayList[position].attending_status
                    .equals("3")
            ) {
                holder.deleteChoice1.setImageResource(R.drawable.delete_disabled)
                holder.textViewCCAaDateItemChoice1.setTextColor(mContext.resources.getColor(R.color.light_grey))
                holder.textViewCCAChoice1.setTextColor(mContext.resources.getColor(R.color.light_grey))
            } else {
                holder.deleteChoice2.setImageResource(R.drawable.delete_disabled)
                holder.textViewCCAaDateItemChoice2.setTextColor(mContext.resources.getColor(R.color.light_grey))
                holder.textViewCCAChoice2.setTextColor(mContext.resources.getColor(R.color.light_grey))
            }
        }
        if (mCCADetailModelArrayList[position].choice2.equals("0")) {
            holder.linearChoice2.visibility = View.GONE
            holder.textViewCCAChoice2.text = "Choice 2 : None"
        } else if (mCCADetailModelArrayList[position].choice2.equals("-1")) {
            holder.linearChoice2.visibility = View.GONE
            holder.textViewCCAChoice2.text = "Choice 2 : Nil"
        } else {
            holder.linearChoice2.visibility = View.VISIBLE
            holder.textViewCCAChoice2.setText(mCCADetailModelArrayList[position].choice2)
            if (mCCADetailModelArrayList[position].venue2
                    .equals("0") || mCCADetailModelArrayList[position].venue2
                    .equals("")
            ) {
                holder.location2Txt.visibility = View.GONE
                holder.readMore.visibility = View.GONE
            } else {
                holder.location2Txt.visibility = View.VISIBLE
                holder.readMore.visibility = View.VISIBLE
                holder.location2Txt.text =
                    "Location            : " + mCCADetailModelArrayList[position].venue2
            }
            if (mCCADetailModelArrayList[position].cca_item_description_2
                    .equals("0") || mCCADetailModelArrayList[position].cca_item_description_2
                    .equals("")
            ) {
                holder.description2Txt.visibility = View.GONE
            } else {
                holder.description2Txt.visibility = View.VISIBLE
                holder.description2Txt.text =
                    "Description      : " + mCCADetailModelArrayList[position].cca_item_description_2
            }
            if (mCCADetailModelArrayList[position].cca_item_start_time != null && mCCADetailModelArrayList[position].cca_item_end_time != null) {
                holder.textViewCCAaDateItemChoice2.visibility = View.VISIBLE
                holder.textViewCCAaDateItemChoice2.text = "(" + convertTimeToAMPM(
                    mCCADetailModelArrayList[position].cca_item_start_time
                ) + " - " + convertTimeToAMPM(
                    mCCADetailModelArrayList[position].cca_item_end_time
                ) + ")"
            } else if (mCCADetailModelArrayList[position].cca_item_start_time != null) {
                holder.textViewCCAaDateItemChoice2.visibility = View.VISIBLE
                holder.textViewCCAaDateItemChoice2.text = "(" + convertTimeToAMPM(
                    mCCADetailModelArrayList[position].cca_item_start_time
                ) + ")"
            } else if (mCCADetailModelArrayList[position].cca_item_end_time != null) {
                holder.textViewCCAaDateItemChoice2.visibility = View.VISIBLE
                holder.textViewCCAaDateItemChoice2.text = "(" + convertTimeToAMPM(
                    mCCADetailModelArrayList[position].cca_item_end_time
                ) + ")"
            } else {
                holder.textViewCCAaDateItemChoice2.visibility = View.GONE
            }
            if (mCCADetailModelArrayList[position].attending_status2.equals("1")) {
                holder.deleteChoice2.setImageResource(R.drawable.delete_red)
            } else if (mCCADetailModelArrayList[position].attending_status2
                    .equals("3")
            ) {
                holder.deleteChoice2.setImageResource(R.drawable.delete_disabled)
                holder.textViewCCAaDateItemChoice2.setTextColor(mContext.resources.getColor(R.color.light_grey))
                holder.textViewCCAChoice2.setTextColor(mContext.resources.getColor(R.color.light_grey))
            } else {
                holder.deleteChoice2.setImageResource(R.drawable.delete_disabled)
                holder.textViewCCAaDateItemChoice2.setTextColor(mContext.resources.getColor(R.color.light_grey))
                holder.textViewCCAChoice2.setTextColor(mContext.resources.getColor(R.color.light_grey))
            }
        }
        if ((mCCADetailModelArrayList[position].choice1
                .equals("0") || mCCADetailModelArrayList[position].choice1
                .equals("-1")) && (mCCADetailModelArrayList[position].choice2
                .equals("0") || mCCADetailModelArrayList[position].choice2
                .equals("-1"))
        ) {
            holder.attendanceListIcon.visibility = View.INVISIBLE
        } else {
            holder.attendanceListIcon.visibility = View.VISIBLE
        }
        holder.deleteChoice1.setOnClickListener {
            if (mCCADetailModelArrayList[position].attending_status.equals("1")) {
                showDialogAlertDelete(
                    mContext as Context,
                    "Alert",
                    mContext.resources.getString(R.string.deltechoicealertques),
                    R.drawable.questionmark_icon,
                    R.drawable.round,
                    position,
                    mCCADetailModelArrayList[position].cca_details_id!!
                )
                notifyItemChanged(position)
                notifyDataSetChanged()
            } else {
            }
        }
        holder.deleteChoice2.setOnClickListener {
            if (mCCADetailModelArrayList[position].attending_status2.equals("1")) {
                showDialogAlertDelete(
                    mContext as Context,
                    "Alert",
                    mContext.resources.getString(R.string.deltechoicealertques),
                    R.drawable.questionmark_icon,
                    R.drawable.round,
                    position,
                    mCCADetailModelArrayList[position].cca_details_id2!!
                )
                notifyItemChanged(position)
                notifyDataSetChanged()
            } else {
            }
        }
        //        holder.textViewCCADay.setText(mCCADetailModelArrayList.get(position).getDay());
//        holder.attendanceListIcon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if ((!(mCCADetailModelArrayList.get(position).getChoice1().equalsIgnoreCase("0")) || !(mCCADetailModelArrayList.get(position).getChoice1().equalsIgnoreCase("-1"))) || (!(mCCADetailModelArrayList.get(position).getChoice2().equalsIgnoreCase("0")) || !(mCCADetailModelArrayList.get(position).getChoice2().equalsIgnoreCase("-1")))) {
//                    showAttendanceList(position);
//                    }
//                    }
//        });
//        if (mCCADetailModelArrayList.get(position).getChoice1().equalsIgnoreCase("0")) {
//            holder.linearChoice1.setVisibility(View.GONE);
//            holder.textViewCCAChoice1.setText("Choice 1 : None");
//            } else if (mCCADetailModelArrayList.get(position).getChoice1().equalsIgnoreCase("-1")) {
//            holder.linearChoice1.setVisibility(View.GONE);
//            holder.textViewCCAChoice1.setText("Choice 1 : Nil");
//            } else {
//            holder.linearChoice1.setVisibility(View.VISIBLE);
//            holder.textViewCCAChoice1.setText(mCCADetailModelArrayList.get(position).getChoice1());
//            if (mCCADetailModelArrayList.get(position).getCca_item_start_time() != null && mCCADetailModelArrayList.get(position).getCca_item_end_time() != null) {
//                holder.textViewCCAaDateItemChoice1.setVisibility(View.VISIBLE);
//                holder.textViewCCAaDateItemChoice1.setText("(" + convertTimeToAMPM(mCCADetailModelArrayList.get(position).getCca_item_start_time()) + " - " + convertTimeToAMPM(mCCADetailModelArrayList.get(position).getCca_item_end_time()) + ")");
//                } else if (mCCADetailModelArrayList.get(position).getCca_item_start_time() != null) {
//                holder.textViewCCAaDateItemChoice1.setVisibility(View.VISIBLE);
//                holder.textViewCCAaDateItemChoice1.setText("(" + convertTimeToAMPM(mCCADetailModelArrayList.get(position).getCca_item_start_time()) + ")"); } else if (mCCADetailModelArrayList.get(position).getCca_item_end_time() != null) {
//                holder.textViewCCAaDateItemChoice1.setVisibility(View.VISIBLE);
//                holder.textViewCCAaDateItemChoice1.setText("(" + convertTimeToAMPM(mCCADetailModelArrayList.get(position).getCca_item_end_time()) + ")");
//            } else {
//                holder.textViewCCAaDateItemChoice1.setVisibility(View.GONE);
//                }
//            if (mCCADetailModelArrayList.get(position).getAttending_status().equalsIgnoreCase("1")) {
//                holder.deleteChoice1.setImageResource(R.drawable.delete_red);
//            } else if (mCCADetailModelArrayList.get(position).getAttending_status().equalsIgnoreCase("3")) {
//                holder.deleteChoice1.setImageResource(R.drawable.delete_disabled);
//                holder.textViewCCAaDateItemChoice1.setTextColor(mContext.getResources().getColor(R.color.light_grey));
//                holder.textViewCCAChoice1.setTextColor(mContext.getResources().getColor(R.color.light_grey));
//                } else {
//                holder.deleteChoice2.setImageResource(R.drawable.delete_disabled);
//                holder.textViewCCAaDateItemChoice2.setTextColor(mContext.getResources().getColor(R.color.light_grey));
//                holder.textViewCCAChoice2.setTextColor(mContext.getResources().getColor(R.color.light_grey));
//                }
//                }
//        if (mCCADetailModelArrayList.get(position).getChoice2().equalsIgnoreCase("0")) {
//            holder.linearChoice2.setVisibility(View.GONE);
//            holder.textViewCCAChoice2.setText("Choice 2 : None");
//            } else if (mCCADetailModelArrayList.get(position).getChoice2().equalsIgnoreCase("-1")) {
//            holder.linearChoice2.setVisibility(View.GONE);
//            holder.textViewCCAChoice2.setText("Choice 2 : Nil");
//            } else {
//            holder.linearChoice2.setVisibility(View.VISIBLE);
//            holder.textViewCCAChoice2.setText(mCCADetailModelArrayList.get(position).getChoice2());
//            if (mCCADetailModelArrayList.get(position).getCca_item_start_time() != null && mCCADetailModelArrayList.get(position).getCca_item_end_time() != null) {
//                holder.textViewCCAaDateItemChoice2.setVisibility(View.VISIBLE);
//                holder.textViewCCAaDateItemChoice2.setText("(" + convertTimeToAMPM(mCCADetailModelArrayList.get(position).getCca_item_start_time()) + " - " + convertTimeToAMPM(mCCADetailModelArrayList.get(position).getCca_item_end_time()) + ")");
//                } else if (mCCADetailModelArrayList.get(position).getCca_item_start_time() != null) {
//                holder.textViewCCAaDateItemChoice2.setVisibility(View.VISIBLE);
//                holder.textViewCCAaDateItemChoice2.setText("(" + convertTimeToAMPM(mCCADetailModelArrayList.get(position).getCca_item_start_time()) + ")");
//            } else if (mCCADetailModelArrayList.get(position).getCca_item_end_time() != null) {
//                holder.textViewCCAaDateItemChoice2.setVisibility(View.VISIBLE);
//                holder.textViewCCAaDateItemChoice2.setText("(" + convertTimeToAMPM(mCCADetailModelArrayList.get(position).getCca_item_end_time()) + ")");
//            } else {
//                holder.textViewCCAaDateItemChoice2.setVisibility(View.GONE);
//                }
//            if (mCCADetailModelArrayList.get(position).getAttending_status2().equalsIgnoreCase("1")) {
//                holder.deleteChoice2.setImageResource(R.drawable.delete_red);
//                } else if (mCCADetailModelArrayList.get(position).getAttending_status2().equalsIgnoreCase("3")) {
//                holder.deleteChoice2.setImageResource(R.drawable.delete_disabled);
//                holder.textViewCCAaDateItemChoice2.setTextColor(mContext.getResources().getColor(R.color.light_grey));
//                holder.textViewCCAChoice2.setTextColor(mContext.getResources().getColor(R.color.light_grey));
//                } else {
//                holder.deleteChoice2.setImageResource(R.drawable.delete_disabled);
//                holder.textViewCCAaDateItemChoice2.setTextColor(mContext.getResources().getColor(R.color.light_grey));
//                holder.textViewCCAChoice2.setTextColor(mContext.getResources().getColor(R.color.light_grey));
//                }
//                }
//        if (((mCCADetailModelArrayList.get(position).getChoice1().equalsIgnoreCase("0")) || (mCCADetailModelArrayList.get(position).getChoice1().equalsIgnoreCase("-1"))) && ((mCCADetailModelArrayList.get(position).getChoice2().equalsIgnoreCase("0")) || (mCCADetailModelArrayList.get(position).getChoice2().equalsIgnoreCase("-1")))) {
//            holder.attendanceListIcon.setVisibility(View.INVISIBLE);
//        } else {
//            holder.attendanceListIcon.setVisibility(View.VISIBLE);
//            }
//            holder.deleteChoice1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mCCADetailModelArrayList.get(position).getAttending_status().equalsIgnoreCase("1")) {
//                    showDialogAlertDelete((Activity) mContext, "Alert", "Do you want to delete this choice?", R.drawable.questionmark_icon, R.drawable.round, position, mCCADetailModelArrayList.get(position).getCca_details_id());
//                                        notifyDataSetChanged();
//                } else {
//
//                }
//            }
//        });
//        holder.deleteChoice2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mCCADetailModelArrayList.get(position).getAttending_status2().equalsIgnoreCase("1")) {
//                    showDialogAlertDelete((Activity) mContext, "Alert", "Do you want to delete this choice?", R.drawable.questionmark_icon, R.drawable.round, position, mCCADetailModelArrayList.get(position).getCca_details_id2());
//                    notifyDataSetChanged();
//                } else {
//
//                }
//            }
//        });
    }

    override fun getItemCount(): Int {
        Log.e("size edit", mCCADetailModelArrayList.size.toString())
        return mCCADetailModelArrayList.size
    }

    fun showAttendanceList(mPosition: Int) {
        if (dialog.isShowing) {
            dialog.dismiss()
        }
        val dialogDismiss = dialog.findViewById<View>(R.id.btn_dismiss) as Button
        val linearChoice3 = dialog.findViewById<View>(R.id.linearChoice1) as LinearLayout
        val linearChoice4 = dialog.findViewById<View>(R.id.linearChoice2) as LinearLayout
        val alertHead = dialog.findViewById<View>(R.id.alertHead) as TextView
        val textViewCCAChoiceFirst = dialog.findViewById<View>(R.id.textViewCCAChoice1) as TextView
        val textViewCCAChoiceSecond = dialog.findViewById<View>(R.id.textViewCCAChoice2) as TextView
        val scrollViewMain = dialog.findViewById<View>(R.id.scrollViewMain) as ScrollView
        val socialMediaList =
            dialog.findViewById<View>(R.id.recycler_view_social_media) as RecyclerView
        val recycler_view_social_mediaChoice2 =
            dialog.findViewById<View>(R.id.recycler_view_social_mediaChoice2) as RecyclerView
        alertHead.text = "Attendance report of " + mCCADetailModelArrayList[mPosition].day
        var showdialog = 1
        //        scrollViewMain.pageScroll(View.FOCUS_DOWN);
        scrollViewMain.smoothScrollTo(0, 0)
        if (!mCCADetailModelArrayList[mPosition].choice1
                .equals("0") && !mCCADetailModelArrayList[mPosition].choice1
                .equals("-1")
        ) {
            linearChoice3.visibility = View.VISIBLE
            socialMediaList.visibility = View.VISIBLE
            socialMediaList.setHasFixedSize(true)
            val llm = LinearLayoutManager(mContext)
            llm.orientation = LinearLayoutManager.VERTICAL
            socialMediaList.layoutManager = llm
            if (mCCADetailModelArrayList[mPosition].calendarDaysChoice1!!.size <= 0) {
                textViewCCAChoiceFirst.visibility = View.GONE
                showdialog = 0
            } else {
                textViewCCAChoiceFirst.setText(mCCADetailModelArrayList[mPosition].choice1)
                textViewCCAChoiceFirst.visibility = View.VISIBLE
                showdialog = 1
            }
            val socialMediaAdapter = CCAAttendenceListAdapter(
                mContext,
                mCCADetailModelArrayList[mPosition].calendarDaysChoice1!!
            )
            socialMediaList.adapter = socialMediaAdapter
        } else {
            linearChoice3.visibility = View.GONE
            socialMediaList.visibility = View.GONE
        }
        if (!mCCADetailModelArrayList[mPosition].choice2
                .equals("0") && !mCCADetailModelArrayList[mPosition].choice2
                .equals("-1")
        ) {
            if (mCCADetailModelArrayList[mPosition].calendarDaysChoice2!!.size <= 0) {
                textViewCCAChoiceSecond.visibility = View.GONE
                showdialog = 0
            } else {
                textViewCCAChoiceSecond.setText(mCCADetailModelArrayList[mPosition].choice2)
                textViewCCAChoiceSecond.visibility = View.VISIBLE
                showdialog = 1
            }
            linearChoice4.visibility = View.VISIBLE
            recycler_view_social_mediaChoice2.visibility = View.VISIBLE
            recycler_view_social_mediaChoice2.setHasFixedSize(true)
            val llmrecycler_view_social_mediaChoice2 = LinearLayoutManager(mContext)
            llmrecycler_view_social_mediaChoice2.orientation = LinearLayoutManager.VERTICAL
            recycler_view_social_mediaChoice2.layoutManager = llmrecycler_view_social_mediaChoice2
            Log.e(
                "mCCACAcaldar",
                java.lang.String.valueOf(mCCADetailModelArrayList[mPosition].cca_details_id)
            )
            val socialMediaAdapterChoice2 = CCAAttendenceListAdapter(
                mContext,
                mCCADetailModelArrayList[mPosition].calendarDaysChoice2!!
            )
            recycler_view_social_mediaChoice2.adapter = socialMediaAdapterChoice2
        } else {
            linearChoice4.visibility = View.GONE
            recycler_view_social_mediaChoice2.visibility = View.GONE
        }
        dialogDismiss.setOnClickListener { dialog.dismiss() }
        if (showdialog == 1) {
            dialog.show()
        } else {
            Toast.makeText(mContext, "No attendance details available", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
      //  lateinit var mContext: Context

        fun showDialogAlertDelete(
            activity: Context,
            msgHead: String?,
            msg: String?,
            ico: Int,
            bgIcon: Int,
            position: Int,
            ccaDetailsId: String
        ) {
            val dialog = Dialog(activity!!)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.dialog_ok_cancel)
            val icon = dialog.findViewById<View>(R.id.iconImageView) as ImageView
            icon.setBackgroundResource(bgIcon)
            icon.setImageResource(ico)
            val text = dialog.findViewById<View>(R.id.text_dialog) as TextView
            val textHead = dialog.findViewById<View>(R.id.alertHead) as TextView
            text.text = msg
            textHead.text = msgHead
            val dialogButton = dialog.findViewById<View>(R.id.btn_Ok) as Button
            dialogButton.setOnClickListener {
                dialog.dismiss()
                ccaDeleteAPI(ccaDetailsId,activity)
            }
            val dialogButtonCancel = dialog.findViewById<View>(R.id.btn_Cancel) as Button
            dialogButtonCancel.visibility = View.VISIBLE
            dialogButtonCancel.setOnClickListener { dialog.dismiss() }
            dialog.show()
        }

        private fun ccaDeleteAPI(ccaDetailsId: String, activity: Context) {
            val ccaDetails: ArrayList<String?> = ArrayList()
            ccaDetails.add(ccaDetailsId)
            val token = PreferenceManager.getaccesstoken(activity)
            val body = CCACancelRequestModel(
                PreferenceManager.getStudIdForCCA(activity)!!,
                ccaDetails.toString()
            )
            //        String token = PreferenceManager.Companion.getUserCode(mContext);
//        Call call = new Call<CCACancelResponseModel>() {
//            @Override
//            public void enqueue(Callback<CCACancelResponseModel> callback) {
//
//            }
//        } = ApiClient.INSTANCE.getGetClient().ccaCancel(body,"Bearer &token");
            val call: Call<CCACancelResponseModel> = ApiClient.getClient.ccaCancel(
                body,
                "Bearer $token"
            )
            call.enqueue(object : Callback<CCACancelResponseModel?> {
                override fun onResponse(
                    call: Call<CCACancelResponseModel?>,
                    response: Response<CCACancelResponseModel?>
                ) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if (response.body()!!.status!!.equals(100)) {
//                            CommonMethods.Companion.showDialogueWithOk(mContext,"Successfully deleted the choice","Alert");
                                showDialogAlert(
                                    activity,
                                    "Alert",
                                    "Successfully Deleted the choice",
                                    R.drawable.exclamationicon,
                                    R.drawable.round
                                )
                            } else {
                                ConstantFunctions.showDialogueWithOk(
                                    activity,
                                    "Unable to delete the choice. Please try again later",
                                    "Alert"
                                )
                            }
                        }
                    } else {
                        ConstantFunctions.showDialogueWithOk(
                            activity,
                            "Cannot continue. Please try again later",
                            "Alert"
                        )
                    }
                }

                override fun onFailure(call: Call<CCACancelResponseModel?>, t: Throwable) {
                    ConstantFunctions.showDialogueWithOk(
                        activity,
                        "Cannot continue. Please try again later",
                        "Alert"
                    )
                }
            })
        }

        fun showDialogAlert(
            activity: Context?,
            msgHead: String?,
            msg: String?,
            ico: Int,
            bgIcon: Int
        ) {
            val dialog = Dialog(activity!!)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.dialog_common_error_alert)
            val icon = dialog.findViewById<View>(R.id.iconImageView) as ImageView
            icon.setBackgroundResource(bgIcon)
            icon.setImageResource(ico)
            val text = dialog.findViewById<View>(R.id.messageTxt) as TextView
            val textHead = dialog.findViewById<View>(R.id.alertHead) as TextView
            text.text = msg
            textHead.text = msgHead
            val dialogButton = dialog.findViewById<View>(R.id.btn_Ok) as Button
            dialogButton.setOnClickListener { v ->
                dialog.dismiss()
                activity.startActivity(
                    Intent(
                        v.context,
                        CCAsReviewAfterSubmissionActivity::class.java
                    ).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                )
            }
            dialog.show()
        }

        fun convertTimeToAMPM(date: String?): String {
            var strCurrentDate = ""
            var format = SimpleDateFormat("HH:mm:ss", Locale.ENGLISH)
            var newDate: Date? = null
            try {
                newDate = format.parse(date)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            format = SimpleDateFormat("hh:mm a", Locale.ENGLISH)
            strCurrentDate = format.format(newDate)
            return strCurrentDate
        }
    }
}