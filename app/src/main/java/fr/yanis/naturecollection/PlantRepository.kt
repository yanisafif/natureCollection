package fr.yanis.naturecollection

import android.net.Uri
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import fr.yanis.naturecollection.PlantRepository.Singleton.databaseRef
import fr.yanis.naturecollection.PlantRepository.Singleton.downloadUri
import fr.yanis.naturecollection.PlantRepository.Singleton.plantList
import fr.yanis.naturecollection.PlantRepository.Singleton.storageReference
import java.net.URI
import java.util.*
import javax.security.auth.callback.Callback

class PlantRepository {

    object Singleton {
        // Le lien pour acceder au bucket
        private val BUCKET_URL: String = "gs://naturecollection-eec1f.appspot.com"

        // Se connecter espace de stockage
        val storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(BUCKET_URL)

        // se connecter à la reference 'plants' dans la firebase
        val databaseRef = FirebaseDatabase.getInstance().getReference("plants")

        // Créer une liste qui va contenir nos plantes
        val plantList = arrayListOf<PlantModel>()

        // Contenir le lien de l'image courante
        var downloadUri: Uri? = null
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
    // Création d'une function pour envoyer des fichiers sur le storage
    fun uploadImage(file: Uri, callback: () -> Unit) {
        // Vérifier que ce fichier n'est pas null
        if (file != null) {
            // Nom du fichier via un UUID random
            val fileName = UUID.randomUUID().toString() + ".jpg"
            // Envoyer
            val ref = storageReference.child(fileName)
            val uploadTask = ref.putFile(file)

            // demarer la tache d'envoi
            uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> {
                task ->
                // Si il y a eu un probleme lors de l'envoi du fichier
                if (!task.isSuccessful){
                    task.exception?.let { throw it }
                }
                return@Continuation ref.downloadUrl
            }).addOnCompleteListener { task ->
                // Verifier si tout a bien fonctionné
                if (task.isSuccessful) {
                    // Recuperer l'image
                    downloadUri = task.result
                    callback()
                }
            }
        }
    }

    // mettre à jour un objet plante en bdd
    fun updatePlant(plant: PlantModel) = databaseRef.child(plant.id).setValue(plant)

    // Inserer une nouvelle plante en bdd
    fun insertPlant(plant: PlantModel) = databaseRef.child(plant.id).setValue(plant)

    // supprimer une plante de la bdd
    fun deletePlant(plant: PlantModel) = databaseRef.child(plant.id).removeValue()

}