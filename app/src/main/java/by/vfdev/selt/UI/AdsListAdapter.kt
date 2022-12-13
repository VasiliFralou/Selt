package by.vfdev.selt.UI

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import by.vfdev.selt.Model.Ads
import by.vfdev.selt.R
import by.vfdev.selt.databinding.ItemListAdsBinding
import by.vfdev.selt.loadImage

class AdsListAdapter (val adsList:List<Ads>, val fragment: BoardFragment):
    RecyclerView.Adapter<AdsListAdapter.ViewHolder>() {

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val binding by viewBinding(ItemListAdsBinding::bind)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.item_list_ads, parent,false)
        val holder = ViewHolder(itemView)

        holder.itemView.setOnClickListener {
            var item = adsList[holder.bindingAdapterPosition]
            fragment.showDetails(item)
        }

        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val list = adsList[position]

        holder.binding.imgAds.loadImage(list.imageUrl)
        holder.binding.titleAdsTV.text = list.title.toString()
        holder.binding.descriptionAdsTV.text = list.description.toString()

        holder.binding.locationTV.text = list.location.toString()
        holder.binding.dateTV.text = list.date.toString()
    }

    override fun getItemCount(): Int = adsList.size
}