package src.main.kotlin

fun main() {
    val test = CarCheckUpSystem
    println(test.isCheckUpNecessary("WP0AA29848S711827"))
    println(test.countCheckUps("BMW"))
    println(test.addCheckUp("WP0AA29848S711827").performedAt)
    println(test.isCheckUpNecessary("WP0AA29848S711827"))
    println(test.countCheckUps("BMW"))
}