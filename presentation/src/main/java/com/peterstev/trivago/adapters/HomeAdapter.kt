package com.peterstev.trivago.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.peterstev.domain.models.Result
import com.peterstev.trivago.adapters.holders.HomeViewHolder
import com.peterstev.trivago.adapters.interfaces.OnClickListener
import com.peterstev.trivago.databinding.FragmentHomeItemBinding

class HomeAdapter(
    private val itemList: MutableList<Result>,
    private val listener: OnClickListener?
) : RecyclerView.Adapter<HomeViewHolder>() {

    private lateinit var binding: FragmentHomeItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        binding =
            FragmentHomeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) =
        holder.bindViews(itemList[position], listener)

    fun updateList(list: List<Result>) {
        itemList.clear()
        itemList.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItemCount() = itemList.size
}