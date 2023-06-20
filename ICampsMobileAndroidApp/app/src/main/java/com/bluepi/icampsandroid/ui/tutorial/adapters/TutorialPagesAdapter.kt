package com.bluepi.icampsandroid.ui.tutorial.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.bluepi.icampsandroid.R
import com.bluepi.icampsandroid.ui.tutorial.models.TutorialPagesData

class TutorialPagesAdapter(
    private val context: Context,
    private val mList: List<TutorialPagesData>
) :
    RecyclerView.Adapter<TutorialPagesAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_page, parent, false)

        return ViewHolder(view)
    }

    override fun getItemId(position: Int): Long {
        // pass position
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        // pass position
        return position
    }

    // binds the list items to a view
    @SuppressLint("DiscouragedApi")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mList[position]
        val resDrawable: Int =
            context.resources.getIdentifier(item.page_icon, "drawable", context.packageName)
        holder.headerTitle.text = item.page_title
        holder.descriptionMessage.text = item.page_description
        holder.imageCentrePage.setImageResource(resDrawable)
        holder.btnNext.text = item.page_btn_title
        if(holder.btnNext.text == "Next"){
            holder.btnNext.setOnClickListener{
                //
            }
        }
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val headerTitle = itemView.findViewById<AppCompatTextView>(R.id.headerTitle)
        val descriptionMessage = itemView.findViewById<AppCompatTextView>(R.id.descriptionMessage)
        val imageCentrePage = itemView.findViewById<AppCompatImageView>(R.id.imageCentrePage)
        val btnNext = itemView.findViewById<AppCompatButton>(R.id.btnNext)
    }
}