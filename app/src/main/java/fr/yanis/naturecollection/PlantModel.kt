package fr.yanis.naturecollection

class PlantModel(
    val id: String = "plant0",
    val name: String = "Tulipe",
    val description: String = "Petite description",
    val imageUrl: String = "https://github.com/LErwan/Nature-Emoi/blob/master/img/nos-meilleures-ventes/3.jpg?raw=true",
    val grow: String = "Faible",
    val water: String = "Moyenne",
    var liked: Boolean = false
) {
}