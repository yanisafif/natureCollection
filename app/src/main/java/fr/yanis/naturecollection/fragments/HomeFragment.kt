package fr.yanis.naturecollection.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import fr.yanis.naturecollection.MainActivity
import fr.yanis.naturecollection.PlantModel
import fr.yanis.naturecollection.R
import fr.yanis.naturecollection.adapter.PlantAdapter
import fr.yanis.naturecollection.adapter.PlantItemDecoration

class HomeFragment(private val context: MainActivity) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater?.inflate(R.layout.fragment_home, container, false)

        // Créer une liste qui va stocker les plantes
        val plantList = arrayListOf<PlantModel>()
        // enregistrer une première plante dans note liste (pissenlit)
        plantList.add(
            PlantModel(
            "Pissenlit",
            "jaune soleil",
            "https://cdn.pixabay.com/photo/2017/04/27/20/17/dandelion-2266558_960_720.jpg",
                false
            )
        )
        //rose
        plantList.add(
            PlantModel(
                "Rose",
                "ça pique un peu",
                "https://cdn.pixabay.com/photo/2018/06/07/20/54/rose-3460971_960_720.jpg",
                false
            )
        )
        //Cactus
        plantList.add(
            PlantModel(
                "Cactus",
                "ça pique beaucoup",
                "https://cdn.pixabay.com/photo/2016/11/29/11/35/abstract-1869217_960_720.jpg",
                true
            )
        )
        //Tulipe
        plantList.add(
            PlantModel(
                "Tulipe",
                "C'est beau",
                "https://cdn.pixabay.com/photo/2018/03/26/16/38/nature-3263198_960_720.jpg",
                false
            )
        )

        // recuperer le recyclerView
        val horizontalRecyclerView = view.findViewById<RecyclerView>(R.id.horizontal_recycler_view)
        horizontalRecyclerView.adapter = PlantAdapter(context, plantList, R.layout.item_horizontal_plant)

        // recuperer le second recyclerView
        val verticalRecyclerView = view.findViewById<RecyclerView>(R.id.vertical_recycler_view)
        verticalRecyclerView.adapter = PlantAdapter(context, plantList, R.layout.item_vertical_plant)
        verticalRecyclerView.addItemDecoration(PlantItemDecoration())

        return view
    }
}