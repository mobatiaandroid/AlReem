import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.TextView
import com.nas.alreem.R
import com.nas.alreem.activity.bus_service.model.StateVO

class MyAdapter(
    context: Context,
    private val resource: Int,
    private val listState: ArrayList<StateVO>
) : ArrayAdapter<StateVO>(context, resource, listState) {

    private var isFromView = false

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, convertView, parent)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, convertView, parent)
    }

    private fun getCustomView(position: Int, convertView: View?, parent: ViewGroup): View {
        var holder: ViewHolder
        val view = convertView ?: LayoutInflater.from(context).inflate(resource, parent, false).apply {
            holder = ViewHolder().apply {
                mTextView = findViewById(R.id.text)
                mCheckBox = findViewById(R.id.checkbox)
            }
            tag = holder
        }

        holder = (view.tag as ViewHolder)

        holder.mTextView.text = listState[position].title

        // To check whether checked event fires from getView() or user input
        isFromView = true
        holder.mCheckBox.isChecked = listState[position].isSelected
        isFromView = false

        holder.mCheckBox.visibility = if (position == 0) View.INVISIBLE else View.VISIBLE
        holder.mCheckBox.tag = position
        holder.mCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
            val getPosition = buttonView.tag as Int
            if (!isFromView) {
                listState[getPosition].isSelected = isChecked
            }
        }

        return view
    }

    private class ViewHolder {
        lateinit var mTextView: TextView
        lateinit var mCheckBox: CheckBox
    }
}