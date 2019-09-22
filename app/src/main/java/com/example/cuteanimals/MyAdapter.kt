package com.example.cuteanimals

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private var myDataset: ArrayList<Bitmap>) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    fun update(modelList:ArrayList<Bitmap>){
        myDataset = modelList
        notifyDataSetChanged()
    }


    class MyViewHolder(animalView: View) : RecyclerView.ViewHolder(animalView) {
        var imageView = animalView.findViewById<ImageView>(R.id.image_animal)

    }



    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MyAdapter.MyViewHolder {

        val animalLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.animal_layout, parent, false)

        return MyViewHolder(animalLayout)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.imageView.setImageBitmap(myDataset.get(position))
    }


    override fun getItemCount() = myDataset.size


}
