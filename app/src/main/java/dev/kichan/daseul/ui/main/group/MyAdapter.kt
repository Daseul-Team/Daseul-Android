package dev.kichan.daseul.ui.main.group

import android.media.session.MediaSession.Token
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import dev.kichan.daseul.BuildConfig
import dev.kichan.daseul.R
import dev.kichan.daseul.model.RetrofitClient
import dev.kichan.daseul.model.data.test.data_infoGroup
import org.w3c.dom.Text

class MyAdapter(private var items: List<data_infoGroup>, private val token: String) :
    RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemImage: ImageView = view.findViewById(R.id.Group_image)
        val itemName: TextView = view.findViewById(R.id.Group_text)
        val itemWho: TextView = view.findViewById(R.id.Group_who)
    }
    fun setData(newItems: List<data_infoGroup>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        Log.d("fortest","리사이클러뷰에서 받은 이름"+item)
        val imageUrl = "${BuildConfig.BASE_URL}"+"file/download/"+item.image

        val headers = LazyHeaders.Builder()
            .addHeader("Authorization", token) // 필요한 경우 헤더를 추가
            .build()

        val glideUrl = GlideUrl(imageUrl, headers)

        Glide.with(holder.itemView.context)
            .load(glideUrl)
            .override(200, 200)
            .transform(CenterCrop(), CircleCrop())
            .into(holder.itemImage)
        holder.itemName.text = item.name
        holder.itemWho.text = item.who.joinToString("\n")
    }

    override fun getItemCount(): Int {
        return items.size
    }
}

