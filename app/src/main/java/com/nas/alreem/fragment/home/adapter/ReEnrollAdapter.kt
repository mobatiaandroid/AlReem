package com.nas.alreem.fragment.home.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.nas.alreem.R
import com.nas.alreem.fragment.home.reenrollment.StudentEnrollList

internal class ReEnrollAdapter(
    var context: Context,
    var stud_enroll_list: java.util.ArrayList<StudentEnrollList>
) :
    RecyclerView.Adapter<ReEnrollAdapter.MyViewHolder>() {
    lateinit var reEnrollOptionList:ArrayList<String>

    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var student_name:TextView=view.findViewById(R.id.name)
        var student_class:TextView=view.findViewById(R.id.section)
        var status:TextView=view.findViewById(R.id.status)
        var studImg:ImageView=view.findViewById(R.id.studImg)
        var linear_list:LinearLayout=view.findViewById(R.id.linear)

    }

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_re_enroll, parent, false)
        return MyViewHolder(itemView)
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
    //Log.e("ad",stud_enroll_list[position].name)
        holder.student_name.setText(stud_enroll_list[position].name)
        holder.student_class.setText(stud_enroll_list[position].class_name)
        var stud_image=stud_enroll_list[position].photo
        if (stud_enroll_list[position].status.equals("")){

            holder.status.setText("Not Submitted")
            holder.status.setTextColor(ContextCompat.getColor(context, R.color.red))
        }else {

            holder.status.setText(stud_enroll_list[position].status)
            holder.status.setTextColor(ContextCompat.getColor(context, R.color.green))
        }
        if (stud_image!!.isNotEmpty()) {
            context.let {
                Glide.with(it)
                    .load(stud_image)
                    .into(holder.studImg)
            }
        }
        else
        {
            holder.studImg.setBackgroundResource(R.drawable.student)
        }

      /*  holder.linear_list.setOnClickListener(){

            if (stud_enroll_list[position].status.equals("")){

                if (stud_enroll_list[position].enrollment_status.equals("1")){
                    reEnrollApi(stud_enroll_list,position)
                    //showReEnroll()

                }else{
                    showPopup(context,"Re Enrollment is currently not enabled.Please wait for further update","Alert")
                }

            }else{
                val dialog = Dialog(context)
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog.setContentView(R.layout.alert_reenroll_settings)
                dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                var name=dialog.findViewById<TextView>(R.id.nametxt)
                name.text = stud_enroll_list[position].parent_name
                var department=dialog.findViewById<TextView>(R.id.mailtxt)
                department.text = stud_enroll_list[position].parent_email
                var role=dialog.findViewById<TextView>(R.id.statustxt)
                role.text =stud_enroll_list[position].status

                var icon_image=dialog.findViewById<ImageView>(R.id.iconImageView)

                var staffImage=stud_enroll_list[position].photo



                if (staffImage.isNotEmpty()) {
                    context.let {
                        Glide.with(it)
                            .load(staffImage)
                            .into(icon_image)
                    }
                }
                else
                {
                    icon_image.setBackgroundResource(R.drawable.student)
                }



                //dialog.dismiss()
                dialog.show()
            }

        }*/

    }

    override fun getItemCount(): Int {

        return stud_enroll_list.size

    }
    /*private fun showPopup(context: Context,message : String,msgHead : String){
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.alert_dialogue_ok_layout)
        var iconImageView = dialog.findViewById(R.id.iconImageView) as ImageView
        iconImageView.setImageResource(R.drawable.exclamationicon)
        var alertHead = dialog.findViewById(R.id.alertHead) as TextView
        var text_dialog = dialog.findViewById(R.id.text_dialog) as TextView
        var btn_Ok = dialog.findViewById(R.id.btn_Ok) as Button

        text_dialog.text = message
        alertHead.text = msgHead
        btn_Ok.setOnClickListener()
        {

            dialog.dismiss()

        }

        dialog.show()
    }*/

}