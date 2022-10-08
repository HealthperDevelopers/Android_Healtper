package com.umc.healthper.ui.main.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umc.healthper.data.entity.PrivacyInfo
import com.umc.healthper.databinding.ItemPrivacyInfoRulesContentBinding

class PrivacyInfoRVAdapter(private val privacyInfoList: ArrayList<PrivacyInfo>): RecyclerView.Adapter<PrivacyInfoRVAdapter.ViewHolder>(){
    private lateinit var binding: ItemPrivacyInfoRulesContentBinding

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemPrivacyInfoRulesContentBinding = ItemPrivacyInfoRulesContentBinding.inflate(
            LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(privacyInfoList[position])
        binding = holder.binding
    }

    override fun getItemCount(): Int = privacyInfoList.size

    inner class ViewHolder(val binding: ItemPrivacyInfoRulesContentBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(privacyInfo: PrivacyInfo) {

            if(privacyInfo.title != null) binding.itemPrivacyInfoRulesContentTitleTv.text = privacyInfo.title
            else binding.itemPrivacyInfoRulesContentTitleTv.text = ""
            binding.itemPrivacyInfoRulesContentContentTv.text = privacyInfo.content
        }
    }
}