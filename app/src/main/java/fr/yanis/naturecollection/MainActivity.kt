package fr.yanis.naturecollection

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.yanis.naturecollection.fragments.AddPlantFragment
import fr.yanis.naturecollection.fragments.CollectionFragment
import fr.yanis.naturecollection.fragments.HomeFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Charger notre plant Repository
        val repo = PlantRepository()

        // Mettre à jour la liste des plantes
        repo.updateData() {
            // Injecter le fragment dans notre boite (fragment_container)
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, AddPlantFragment(this))
            transaction.addToBackStack(null)
            transaction.commit()
        }


    }
}