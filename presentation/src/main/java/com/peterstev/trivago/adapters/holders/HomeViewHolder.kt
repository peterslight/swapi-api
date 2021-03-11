package com.peterstev.trivago.adapters.holders

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import com.peterstev.domain.models.Result
import com.peterstev.trivago.R
import com.peterstev.trivago.adapters.interfaces.OnClickListener

class HomeViewHolder(itemView: View) :
    RecyclerView.ViewHolder(itemView) {
    private val name: MaterialTextView = itemView.findViewById(R.id.home_item_name)
    private val desc: MaterialTextView = itemView.findViewById(R.id.home_item_desc)
    private val dateOfBirth: MaterialTextView = itemView.findViewById(R.id.home_item_dob)

    @SuppressLint("SetTextI18n")
    fun bindViews(item: Result, listener: OnClickListener?) {
        name.text = item.name
        desc.text = "A ${item.skinColor} skinned, ${item.eyeColor} eyed ${item.gender}"
        dateOfBirth.text = item.birthYear

        itemView.setOnClickListener {
            listener!!.onCharacterClick(item)
        }
    }
}
