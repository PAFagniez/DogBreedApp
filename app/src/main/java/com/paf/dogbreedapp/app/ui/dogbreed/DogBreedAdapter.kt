package com.paf.dogbreedapp.app.ui.dogbreed

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.paf.dogbreedapp.R
import com.paf.dogbreedapp.app.utils.WeakReference
import com.paf.dogbreedapp.core.model.DogBreed
import com.paf.dogbreedapp.core.model.getName
import kotlinx.android.synthetic.main.dogbreed_item_layout.view.*

class DogBreedAdapter: Adapter<DogBreedViewHolder>(), DogBreedViewHolder.Listener {

    interface Listener {
        fun onClickItemInList(dogBreed: DogBreed)
    }

    private var dogBreedList: List<DogBreed>? = null
    var delegate: Listener? by WeakReference()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogBreedViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.dogbreed_item_layout, parent, false)
        return DogBreedViewHolder(view)
    }

    override fun getItemCount() = dogBreedList?.size ?: 0

    override fun onBindViewHolder(holder: DogBreedViewHolder, position: Int) {
        dogBreedList?.get(position)?.let {
            holder.bind(it)
            holder.delegate = this
        }
    }

    override fun onClickItem(dogBreed: DogBreed) {
        delegate?.onClickItemInList(dogBreed)
    }

    fun setItems(list: List<DogBreed>) {
        dogBreedList = list
        notifyDataSetChanged()
    }
}

class DogBreedViewHolder(view: View): RecyclerView.ViewHolder(view) {
    interface Listener {
        fun onClickItem(dogBreed: DogBreed)
    }

    var delegate: Listener? by WeakReference()

    fun bind(dogBreed: DogBreed){

        itemView.apply {
            name.text = dogBreed.getName()
            setOnClickListener { delegate?.onClickItem(dogBreed) }
        }
    }
}