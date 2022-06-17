package fr.yanis.naturecollection.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.ProxyFileDescriptorCallback
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import androidx.fragment.app.Fragment
import fr.yanis.naturecollection.MainActivity
import fr.yanis.naturecollection.PlantModel
import fr.yanis.naturecollection.PlantRepository
import fr.yanis.naturecollection.PlantRepository.Singleton.downloadUri
import fr.yanis.naturecollection.R
import java.util.*

class AddPlantFragment(
    private val context: MainActivity
) : Fragment() {

    private var file:Uri? = null
    private var uploadedImage:ImageView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater?.inflate(R.layout.fragment_add_plant, container, false)

        // Recuperer uploadedImage pour lui associer son composant
        uploadedImage = view.findViewById(R.id.preview_image)

        // Recuperer le boutton pour charger une image
        val pickupImageButton = view.findViewById<Button>(R.id.upload_button)
        // Lorsqu'on clique dessus ça ouvre les images du telephone
        pickupImageButton.setOnClickListener() {
            pickupImage()
        }

        // Recuperer le bouton confirmer
        val confirmButton = view.findViewById<Button>(R.id.confirm_button)
        confirmButton.setOnClickListener {
            sendForm(view)
        }

        return view
    }

    private fun sendForm(view: View) {
        val repo = PlantRepository()
        repo.uploadImage(file!!) {
            val plantName = view.findViewById<EditText>(R.id.name_input).text.toString()
            val plantDescription = view.findViewById<EditText>(R.id.description_input).text.toString()
            val grow = view.findViewById<Spinner>(R.id.grow_spinner).selectedItem.toString()
            val water = view.findViewById<Spinner>(R.id.water_spinner).selectedItem.toString()
            val downloadImageUrl = downloadUri

            // Créer un nouvel objet PlantModel
            val plant = PlantModel(
                UUID.randomUUID().toString(),
                plantName,
                plantDescription,
                downloadImageUrl.toString(),
                grow,
                water
            )
            // Envoyer en Bdd
            repo.insertPlant(plant)
        }
    }

    private fun pickupImage() {
        val intent = Intent()
        intent.type = "image/"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 47)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 47 && resultCode == Activity.RESULT_OK){
            // Verifier si les données sont nulles
            if (data == null) return

            // Recuperer l'image qui a été selectionné
            file = data.data

            // Mettre à jour l'appercu de l'image
            uploadedImage?.setImageURI(file)
        }
    }
}