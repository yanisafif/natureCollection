package fr.yanis.naturecollection.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fr.yanis.naturecollection.MainActivity
import fr.yanis.naturecollection.PlantModel
import fr.yanis.naturecollection.PlantRepository
import fr.yanis.naturecollection.R

class PlantAdapter(
    private val context: MainActivity,
    private val plantList: List<PlantModel>,
    private val layoutId: Int)
    : RecyclerView.Adapter<PlantAdapter.ViewHolder>() {

    // Boite pour ranger tout les composants à controller
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // image de la plante
        val plantImage = view.findViewById<ImageView>(R.id.image_item)
        val plantName:TextView? = view.findViewById(R.id.name_item)
        val plantDescription:TextView? = view.findViewById(R.id.description_item)
        val starIcon = view.findViewById<ImageView>(R.id.star_icon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return  ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Recuper les informations de la plante
        val currentPlant = plantList[position]

        // Recuperer le repository
        val repo = PlantRepository()

        // Utiliser glide pour recuperer l'image à partir de son lien -> composant
        Glide.with(context).load(Uri.parse(currentPlant.imageUrl)).into(holder.plantImage)

        //Mettre à jour le nom de la plante
        holder.plantName?.text = currentPlant.name

        // Mettre à jour la descriont de la plante
        holder.plantDescription?.text = currentPlant.description

        // Verifier si la plante a été liké ou non
        if (currentPlant.liked) {
            holder.starIcon.setImageResource(R.drawable.ic_star)
        }else{
            holder.starIcon.setImageResource(R.drawable.ic_unstar)
        }

        // rajouter une interaction sur cette etoile
        holder.starIcon.setOnClickListener() {
            // inverse si le bouton est like ou non
            currentPlant.liked = !currentPlant.liked
            // mettre à jour l'objet plante
            repo.updatePlant(currentPlant)
        }
    }


    override fun getItemCount(): Int = plantList.size
}