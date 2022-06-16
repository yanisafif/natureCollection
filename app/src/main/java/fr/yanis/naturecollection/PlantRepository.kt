package fr.yanis.naturecollection

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import fr.yanis.naturecollection.PlantRepository.Singleton.databaseRef
import fr.yanis.naturecollection.PlantRepository.Singleton.plantList
import javax.security.auth.callback.Callback

class PlantRepository {

    object Singleton {
        // se connecter à la reference 'plants' dans la firebase
        val databaseRef = FirebaseDatabase.getInstance().getReference("plants")

        // Créer une liste qui va contenir nos plantes
        val plantList = arrayListOf<PlantModel>()
    }

    fun updateData(callback: () -> Unit) {

        // Absorber les données dans la databaseRef -> liste de plantes
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Retier les anciennes
                plantList.clear()
                // recolter la liste
                for (ds in snapshot.children) {
                    // Construction de l'objet plante
                    val plant = ds.getValue(PlantModel::class.java)

                    // Verifier que la plante n'est pas null
                    if (plant != null) {
                        // Ajouter la plante à notre liste
                        plantList.add(plant)
                    }
                }
                // Actionner le callback
                callback()
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }


}