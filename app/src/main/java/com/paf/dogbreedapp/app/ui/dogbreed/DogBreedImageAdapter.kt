package com.paf.dogbreedapp.app.ui.dogbreed

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.bumptech.glide.Glide
import com.paf.dogbreedapp.R
import com.paf.dogbreedapp.app.utils.WeakReference
import com.paf.dogbreedapp.core.model.DogBreed
import com.paf.dogbreedapp.core.model.getName
import kotlinx.android.synthetic.main.dogbreed_image_layout.view.*
import kotlinx.android.synthetic.main.dogbreed_item_layout.view.*

class DogBreedImageAdapter: Adapter<DogBreedImageViewHolder>() {

    interface Listener {
        fun onClickItemInList(dogBreed: DogBreed)
    }

    private var imageList: List<Uri>? = null
    var delegate: Listener? by WeakReference()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogBreedImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.dogbreed_image_layout, parent, false)
        return DogBreedImageViewHolder(view)
    }

    override fun getItemCount() = imageList?.size ?: 0

    override fun onBindViewHolder(holder: DogBreedImageViewHolder, position: Int) {
        imageList?.get(position)?.let {
            holder.bind(it)
        }
    }

    fun setItems(list: List<Uri>) {
        imageList = list
        notifyDataSetChanged()
    }
}

class DogBreedImageViewHolder(view: View): RecyclerView.ViewHolder(view) {

    fun bind(image: Uri){

        itemView.apply {
            Glide.with(this).load(image).into(imageIV)
        }
    }
}