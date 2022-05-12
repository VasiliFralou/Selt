package by.vfdev.selt.UI

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import by.vfdev.selt.Model.Ads
import by.vfdev.selt.R
import by.vfdev.selt.loadImage

class AdsListAdapter (var mContext: Context, var adsList:List<Ads>):
    RecyclerView.Adapter<AdsListAdapter.ListViewHolder>()
{
    inner class ListViewHolder(var v: View): RecyclerView.ViewHolder(v){
        var imgT = v.findViewById<ImageView>(R.id.imgAds)
        var titleT = v.findViewById<TextView>(R.id.titleAdsTV)
        var descriT = v.findViewById<TextView>(R.id.descriptionAdsTV)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val infalter = LayoutInflater.from(parent.context)
        val v = infalter.inflate(R.layout.item_list_ads, parent,false)
        return ListViewHolder(v)
    }

    override fun getItemCount(): Int = adsList.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val newList = adsList[position]
        holder.descriT.text = newList.description
        holder.titleT.text = newList.title
        holder.imgT.loadImage(newList.imageUrl)
        holder.v.setOnClickListener {

            newList.description
            newList.imageUrl
            newList.title
        }
    }
}