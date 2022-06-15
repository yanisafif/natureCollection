package fr.yanis.naturecollection.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import fr.yanis.naturecollection.R

class PlantAdapter : RecyclerView.Adapter<PlantAdapter.ViewHolder>() {

    // Boite pour ranger tout les composants à controller
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // image de la plante
        val plantImage = view.findViewById<ImageView>(R.id.image_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_horizontal_plant, parent, false)
        return  ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int = 5
}