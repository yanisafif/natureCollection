package fr.yanis.naturecollection.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import fr.yanis.naturecollection.MainActivity
import fr.yanis.naturecollection.PlantRepository
import fr.yanis.naturecollection.R

class AddPlantFragment(
    private val context: MainActivity
) : Fragment() {

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

        return view
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
            val selectedImage = data.data

            // Mettre à jour l'appercu de l'image
            uploadedImage?.setImageURI(selectedImage)

            // Heberger sur le bucket firebase
            val repo = PlantRepository()
            repo.uploadImage(selectedImage!!)
        }
    }
}