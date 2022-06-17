package fr.yanis.naturecollection.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.yanis.naturecollection.MainActivity
import fr.yanis.naturecollection.PlantRepository.Singleton.plantList
import fr.yanis.naturecollection.R
import fr.yanis.naturecollection.adapter.PlantAdapter
import fr.yanis.naturecollection.adapter.PlantItemDecoration

class CollectionFragment(
    private val context: MainActivity
) : Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater?.inflate(R.layout.fragment_collection, container, false)

        // Recuperer ma recyclerView déjà utilisé dans une autre vue
        val collectionRecyclerView = view.findViewById<RecyclerView>(R.id.collection_recycler_list)
        // Récupérer la data à mettre dans la vue
        collectionRecyclerView.adapter = PlantAdapter(context, plantList.filter { it.liked }, R.layout.item_vertical_plant)
        collectionRecyclerView.layoutManager = LinearLayoutManager(context)
        collectionRecyclerView.addItemDecoration(PlantItemDecoration())

        return view
    }
}