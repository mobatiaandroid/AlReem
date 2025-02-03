package com.nas.alreem.fragment.calendar.adapter

import android.app.Dialog
import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.provider.CalendarContract
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.nas.alreem.R
import com.nas.alreem.constants.ConstantFunctions
import com.nas.alreem.constants.OnItemClickListener
import com.nas.alreem.constants.PreferenceManager
import com.nas.alreem.constants.addOnItemClickListener
import com.nas.alreem.fragment.calendar.model.CalendarDetailsModelUse
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

class CalendarDetailListAdapter(
    private var mContext: Context,
    private var calendarModels: ArrayList<CalendarDetailsModelUse>,
    private var colors: Int,
    private var mPosition: Int,
    private var isRead: Boolean,
    private var date: String
) : RecyclerView.Adapter<CalendarDetailListAdapter.MyViewHolder>() {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var eventName: TextView = view.findViewById(R.id.eventName)
        var eventTime: TextView = view.findViewById(R.id.eventTime)
        var addicon: ImageView = view.findViewById(R.id.addicon)
        var removeicon: ImageView = view.findViewById(R.id.removeicon)
        var statusImg: ImageView = view.findViewById(R.id.statusImg)



    }
    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_calendar_detail, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.eventName.text = calendarModels.get(position).title
        //Log.e("isalladay", calendarModels.get(position).isAllday)
        if (calendarModels.get(position).isAllday.equals("1")) {
            holder.eventTime.text = "All Day Event"
        } else {
          //  Log.e("satrttime",calendarModels.get(position).starttime)
          //  Log.e("endtime",calendarModels.get(position).endtime)
            if (!calendarModels.get(position).starttime.equals("") && !(calendarModels.get(position).endtime).equals("")
            ) {
                holder.eventTime.text =
                    calendarModels.get(position).starttime + " - " + calendarModels.get(
                        position
                    ).endtime
            } else if (!calendarModels.get(position).starttime.equals("") && (calendarModels.get(
                    position
                ).endtime).equals("")
            ) {
                //Log.e("Success1","Suucess")
                holder.eventTime.text = calendarModels.get(position).starttime
            } else if (calendarModels.get(position).starttime.equals("") && !(calendarModels.get(
                    position
                ).endtime).equals("")
            ) {
               // Log.e("Success2","Suucess")
                holder.eventTime.text = calendarModels.get(position).endtime
            }
        }



        holder.eventTime.setTextColor(colors)
        holder.eventName.setTextColor(colors)

        if (colors == mContext.resources.getColor(R.color.cal_row_1)) {
            holder.addicon.setImageResource(R.drawable.addicon4)
            holder.removeicon.setImageResource(R.drawable.minimize4)
        } else if (colors == mContext.resources.getColor(R.color.cal_row_2)) {
            holder.addicon.setImageResource(R.drawable.addicon3)
            holder.removeicon.setImageResource(R.drawable.minimize3)
        } else if (colors == mContext.resources.getColor(R.color.cal_row_3)) {
            holder.addicon.setImageResource(R.drawable.addicon2)
            holder.removeicon.setImageResource(R.drawable.minimize2)
        } else if (colors == mContext.resources.getColor(R.color.cal_row_4)) {
            holder.addicon.setImageResource(R.drawable.addicon1)
            holder.removeicon.setImageResource(R.drawable.minimize1)
        }

        if (calendarModels.get(position).status.equals("0")) {
            holder.statusImg.visibility = View.VISIBLE

            holder.statusImg.setImageResource(R.drawable.shape_circle_red)
        } else if (calendarModels.get(position).status.equals("2")) {
            holder.statusImg.visibility = View.VISIBLE

            holder.statusImg.setImageResource(R.drawable.shape_circle_navy)

        } else if (calendarModels.get(position).status.equals("1") || calendarModels.get(position).status.equals(
                ""
            )
        ) {
            holder.statusImg.visibility = View.INVISIBLE
        }
        holder.itemView.setOnClickListener {
            if (calendarModels.get(position).status.equals("0") || calendarModels.get(position).status.equals("2")
            ) {

                calendarModels.get(position).status = "1"
                PreferenceManager.setCalendarBadge(mContext, 0)
                PreferenceManager.setCalendarEditedBadge(mContext, 0)
                notifyDataSetChanged()
            }
            var dateString: String = ""
            if (calendarModels.get(position).isAllday.equals("1")) {
                dateString = "All Day Event"
            } else {
                if (!calendarModels.get(position).starttime.equals(
                        ""
                    ) && !calendarModels.get(position).endtime.equals(
                        ""
                    )
                ) {
                    dateString =
                        calendarModels.get(position).starttime + " - " + calendarModels.get(position).endtime
                } else if (!calendarModels.get(position).starttime.equals(
                        ""
                    ) && calendarModels.get(position).endtime.equals(
                        ""
                    )
                ) {
                    dateString =
                        calendarModels.get(position).starttime
                } else if (calendarModels.get(position).starttime.equals(
                        ""
                    ) && !calendarModels.get(position).endtime.equals(
                        ""
                    )
                ) {
                    dateString =
                        calendarModels.get(position).endtime
                }
            }


            showCalendarDetail(
                calendarModels.get(position).title,
                date,
                dateString,
                calendarModels,
                position,
                mContext
            )
        }


    }
    override fun getItemCount(): Int {
        return calendarModels.size

    }
    fun showCalendarDetail(
        eventNameStr: String,
        eventDateStr: String,
        eventTypeStr: String,
        mCalendarEventModels: ArrayList<CalendarDetailsModelUse>,
        eventPosition: Int,
        mContext: Context
    )
    {
        val dialog = Dialog(mContext)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialogue_calendar_detail)
        var iconImageView = dialog.findViewById(R.id.iconImageView) as? ImageView
        var eventType = dialog.findViewById(R.id.eventType) as? TextView
        var eventDate = dialog.findViewById(R.id.eventDate) as? TextView
        var dismiss = dialog.findViewById(R.id.dismiss) as Button
        var linkBtn = dialog.findViewById(R.id.linkBtn) as Button
        var deleteCalendar = dialog.findViewById(R.id.deleteCalendar) as Button
        var addToCalendar = dialog.findViewById(R.id.addToCalendar) as Button
        var eventNameText=dialog.findViewById(R.id.eventName) as TextView
        eventDate?.text =eventDateStr
        eventNameText?.text =eventNameStr
        if(eventTypeStr.equals(""))
        {

        }
        else{
            eventType?.text = "( "+eventTypeStr+" )"
        }

        dismiss.setOnClickListener()
        {
            dialog.dismiss()
        }
        if(mCalendarEventModels.get(eventPosition).vpml.equals(""))
        {
            linkBtn.visibility=View.GONE
        }
        else{
            linkBtn.visibility=View.VISIBLE
        }
        linkBtn.setOnClickListener(View.OnClickListener {

            val uri = Uri.parse(mCalendarEventModels[eventPosition].vpml)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            mContext.startActivity(intent)
            dialog.dismiss()

        })

        addToCalendar.setOnClickListener(View.OnClickListener {

            val reqSentence: String = ConstantFunctions.htmlparsing(
                Html.fromHtml(mCalendarEventModels[eventPosition].title).toString().replace(
                    "\\s+".toRegex(),
                    " "
                )
            ).toString()
            val splited = reqSentence.split("\\s+".toRegex()).toTypedArray()
            var dateString: Array<String?>
            var year = -1
            var month = -1
            var day = -1
            val timeString: Array<String>
            var hour = -1
            var min = -1
            val timeString1: Array<String>
            var hour1 = -1
            var min1 = -1
            var allDay: String? = "0"
            year = mCalendarEventModels[eventPosition].yearDate.toInt()

            month = getMonthDetails(mContext, mCalendarEventModels[eventPosition].monthDate)
            day = mCalendarEventModels[eventPosition].dayDate.toInt()
            if (mCalendarEventModels[eventPosition].starttime.equals("")) {
                hour = -1
                min = -1
            } else {
                val format1: DateFormat = SimpleDateFormat("hh:mm a", Locale.ENGLISH)
                val format2 = SimpleDateFormat("hh:mm", Locale.ENGLISH)
                val dateStart = format1.parse(mCalendarEventModels[eventPosition].starttime)
                val startTime = format2.format(dateStart)
                timeString = startTime.split(":").toTypedArray()

                hour = timeString[0].toInt()
                min = timeString[1].toInt()
            }
            allDay = mCalendarEventModels[eventPosition].isAllday

            if (mCalendarEventModels[eventPosition].endtime.equals("")) {
                hour1 = -1
                min1 = -1
            } else {
                val format1: DateFormat = SimpleDateFormat("hh:mm a", Locale.ENGLISH)
                val format2 = SimpleDateFormat("hh:mm", Locale.ENGLISH)
                val dateStart = format1.parse(mCalendarEventModels[eventPosition].endtime)
                val startTime = format2.format(dateStart)
                timeString1 = startTime.split(":").toTypedArray()

                hour = timeString1[0].toInt()
                min = timeString1[1].toInt()
//                timeString1 = mCalendarEventModels[eventPosition].endtime.split(":").toTypedArray()
//                hour = timeString1[0].toInt()
//                min = timeString1[1].toInt()
            }

            var addToCalendar = true
            val prefData: List<String> = PreferenceManager
                .getCalendarEventNames(mContext)!!.split(",")

            for (i in prefData.indices) {
                if (prefData[i].equals(
                        mCalendarEventModels[eventPosition].title + mCalendarEventModels[eventPosition].title,
                        ignoreCase = true
                    )
                ) {
                    addToCalendar = false
                    break
                }
            }
            println("addToCalendar---$addToCalendar")
            if (addToCalendar) {

                if (year != -1 && month != -1 && day != -1 && hour != -1 && min != -1) {

                    if (hour1 == -1 && min1 == -1) {

                        addReminder(year, month, day, hour, min, year, month, day,hour, min,
                            mCalendarEventModels[eventPosition].title, mCalendarEventModels[eventPosition].title, 0,
                            eventPosition, allDay, mCalendarEventModels)
                    }
                    else {

                        addReminder(year, month, day, hour, min, year, month, day, hour1, min1,
                            mCalendarEventModels[eventPosition].title, mCalendarEventModels[eventPosition].title, 0,
                            eventPosition, allDay, mCalendarEventModels)

                    }

                }
                else {

                    Toast.makeText(
                        mContext,
                        "Not enough details to add to calendar",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(mContext, "Event added to device calendar", Toast.LENGTH_SHORT)
                    .show()
            }
            notifyDataSetChanged()

        })

        deleteCalendar.setOnClickListener(View.OnClickListener {

            if (mCalendarEventModels.get(eventPosition).id != 0) {
                val deleteUri = ContentUris.withAppendedId(
                    CalendarContract.Events.CONTENT_URI, mCalendarEventModels[eventPosition]
                        .id.toLong()
                )
                mContext.contentResolver.delete(
                    deleteUri, null,
                    null
                )
                mCalendarEventModels[eventPosition].id = 0
                Toast.makeText(
                    mContext,
                    "Event removed from device calendar", Toast.LENGTH_SHORT
                ).show()
            }
            dialog.dismiss()
        })

        dialog.show()
    }
    fun addReminder(startYear: Int, startMonth: Int, startDay: Int, startHour: Int, startMinute: Int, endYear: Int, endMonth: Int,
                    endDay: Int, endHour: Int, endMinutes: Int, name: String, description: String, count: Int, position: Int, allDay: String,
                    mCalendarEventModelAdd:ArrayList<CalendarDetailsModelUse>) {
        val beginTime = Calendar.getInstance()
        beginTime[startYear, startMonth, startDay, startHour] = startMinute
        val startMillis = beginTime.timeInMillis
        val endTime = Calendar.getInstance()
        endTime[endYear, endMonth, endDay, endHour] = endMinutes
        val endMillis = endTime.timeInMillis
        val eventUriString = "content://com.android.calendar/events"
        val eventValues = ContentValues()
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            // Marshmallow+
            eventValues.put(CalendarContract.Events.CALENDAR_ID, 3) //1
        } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP || Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP_MR1) {
            // lollipop
            eventValues.put(CalendarContract.Events.CALENDAR_ID, 3) //1
        } else {
            //below Marshmallow
            eventValues.put(CalendarContract.Events.CALENDAR_ID, 1) //1
        }

//        eventValues.put(CalendarContract.Events.CALENDAR_ID, 1);//1
        eventValues.put(CalendarContract.Events.TITLE, name)
        eventValues.put(CalendarContract.Events.DESCRIPTION, description)
        eventValues.put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.SHORT)
        eventValues.put(CalendarContract.Events.DTSTART, startMillis)
        eventValues.put(CalendarContract.Events.DTEND, endMillis)
        if (allDay == "1") {
            eventValues.put(CalendarContract.Events.ALL_DAY, true)
        } else {
            eventValues.put(CalendarContract.Events.ALL_DAY, false)
        }
        eventValues.put("eventStatus", 1)
        eventValues.put(CalendarContract.Events.HAS_ALARM, 1)
        val eventUri = mContext.contentResolver.insert(
            Uri.parse(eventUriString), eventValues
        )
        val eventID = eventUri!!.lastPathSegment!!.toLong()
        Log.d("TAG", "1----$eventID")
        mCalendarEventModelAdd[position].id.toString()
        Log.d("TAG", "2----")
        PreferenceManager.setCalendarEventNames(
            mContext,
            PreferenceManager.getCalendarEventNames(mContext).toString() + name
                    + description + ","
        )
        if (count == 0) {
            Toast.makeText(
                mContext,"Event added to device calendar", Toast.LENGTH_SHORT
            ).show()
        }
        /***************** Event: Reminder(with alert) Adding reminder to event  */
        val reminderUriString = "content://com.android.calendar/reminders"
        val reminderValues = ContentValues()
        reminderValues.put("event_id", eventID)
        reminderValues.put("minutes", 1440)
        reminderValues.put("method", 1)
//        val reminderUri = mContext.contentResolver.insert(
//            Uri.parse(reminderUriString), reminderValues
//        )
        //  val eventIDlong = reminderUri!!.lastPathSegment!!.toLong()
//        AppController.eventIdList.add(eventID.toString())
    }
    fun getMonthDetails(mContext: Context, descStringTime: String):Int
    {
        if (descStringTime.equals("January"))
        {
            mnthId =0
        }
        else if (descStringTime.equals("February"))
        {
            mnthId =1
        }
        else if (descStringTime.equals("March"))
        {
            mnthId =2
        }
        else if (descStringTime.equals("April"))
        {
            mnthId =3
        }
        else if (descStringTime.equals("May"))
        {
            mnthId =4
        }
        else if (descStringTime.equals("June"))
        {
            mnthId =5
        }
        else if (descStringTime.equals("July"))
        {
            mnthId =6
        }
        else if (descStringTime.equals("August"))
        {
            mnthId =7
        }
        else if (descStringTime.equals("September"))
        {
            mnthId =8
        }
        else if (descStringTime.equals("October"))
        {
            mnthId =9
        }
        else if (descStringTime.equals("November"))
        {
            mnthId =10
        }
        else if (descStringTime.equals("December"))
        {
            mnthId =11
        }

        return mnthId
    }
}