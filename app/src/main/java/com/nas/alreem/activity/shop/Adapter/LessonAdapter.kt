package com.nas.alreem.activity.shop.Adapter

import android.content.Context
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nas.alreem.R
import com.nas.alreem.activity.shop.model.Lesson
import java.lang.String
import kotlin.Boolean
import kotlin.Int


class LessonAdapter(context: Context, lessonData: ArrayList<Lesson>, lessonAvailable: Boolean) :
    RecyclerView.Adapter<LessonAdapter.MyViewHolder>() {
    private val context: Context
    private val lessons: ArrayList<Lesson>
    private val selectedLessonsMap: SparseArray<Lesson> = SparseArray<Lesson>()
    private val lessonAvailable: Boolean

    init {
        lessons = lessonData
        this.context = context
        this.lessonAvailable = lessonAvailable
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_lesson, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val lesson: Lesson = lessons[position]
        holder.bind(lesson)
    }

    override fun getItemCount(): Int {
        return lessons.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val lessonNameTextView: TextView
        private val amountTextView: TextView
        private val lessonCheckBox: CheckBox

        init {
            lessonNameTextView = itemView.findViewById<TextView>(R.id.lessonNameTextView)
            amountTextView = itemView.findViewById<TextView>(R.id.amountTextView)
            lessonCheckBox = itemView.findViewById<CheckBox>(R.id.lessonCheckBox) // Add this line
            lessonCheckBox.setOnCheckedChangeListener { buttonView: CompoundButton?, isChecked: Boolean ->
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val lesson: Lesson = lessons[position]
                    lesson.setSelected(isChecked)
                }
            }
        }

        fun bind(lesson: Lesson) {
            lessonNameTextView.setText(lesson.getName())
            amountTextView.setText(lesson.getTotalAmount() + " AED")
            if (String.valueOf(lesson.getCourseSelected()).equals("1", ignoreCase = true)) {
                lessonCheckBox.isChecked = true
            } else {
                lessonCheckBox.isChecked = false
            }
            var disableCheckboxes =
                false // Flag to track if any lesson with orderSuccess = 1 is found

            // Iterate through all lessons to check for orderSuccess = 1
            for (lessonItem in lessons) { // Assuming lessonList is your list of lessons
                if (lessonItem.getOrderSuccess() === 1) {
                    disableCheckboxes = true
                    break // No need to check further if one lesson with orderSuccess = 1 is found
                }
            }
            if (disableCheckboxes) {
                if (lesson.getCourseSelected() === 1) {
                    lessonCheckBox.isChecked = true
                }
                lessonCheckBox.isEnabled = false // Disable the checkbox
                itemView.alpha = 0.5f
            } else {
                if (lessonAvailable) {
                    lessonCheckBox.isEnabled = true // Enable the checkbox
                } else {
                    lessonCheckBox.isEnabled = false // Enable the checkbox
                }
            }
            //            lessonCheckBox.setEnabled(lessonAvailable);
        }
    }
}
