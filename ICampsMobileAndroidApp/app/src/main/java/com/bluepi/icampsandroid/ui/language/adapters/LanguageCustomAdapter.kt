package com.bluepi.icampsandroid.ui.language.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.recyclerview.widget.RecyclerView
import com.bluepi.icampsandroid.R
import com.bluepi.icampsandroid.ui.language.listeners.ItemClickListener
import com.bluepi.icampsandroid.ui.language.models.Language

class LanguageCustomAdapter(
    private val mList: List<Language>,
    private val itemClickListener: ItemClickListener
) :
    RecyclerView.Adapter<LanguageCustomAdapter.ViewHolder>() {
    var selectedPosition = -1

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_design, parent, false)

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
    @Suppress("DEPRECATION")
    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = mList[position]

        // sets the text to the textview from our itemHolder class
        // Set text on radio button
        holder.radioButton.text = item.lang_title
        // Checked selected radio button
        holder.radioButton.isChecked = position== selectedPosition

        // set listener on radio button
        holder.radioButton.setOnCheckedChangeListener { compoundButton, b ->
            // check condition
            if (b) {
                // When checked
                // update selected position
                selectedPosition = holder.adapterPosition
                // Call listener
                itemClickListener.onClick(
                    holder.radioButton.text
                        .toString()
                )
            }
        }
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val radioButton = itemView.findViewById<RadioButton>(R.id.radioButton)
    }
}