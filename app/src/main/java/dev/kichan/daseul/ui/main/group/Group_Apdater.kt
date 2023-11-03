//package dev.kichan.daseul.ui.main.group
//
//import android.content.ClipData
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//
//class Group_Apdater (private val items: List<ClipData.Item>) : RecyclerView.Adapter<Group_Apdater.ViewHolder>() {
//    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val textView: TextView = itemView.findViewById(R.id.textView)
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
//        return ViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val item = items[position]
//        holder.textView.text = item.text
//    }
//
//    override fun getItemCount(): Int {
//        return items.size
//    }
//}