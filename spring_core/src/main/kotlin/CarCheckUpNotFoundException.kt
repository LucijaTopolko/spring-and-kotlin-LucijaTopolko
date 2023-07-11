package src.main.kotlin

class CarCheckUpNotFoundException(id: Long) : RuntimeException("Car check-up ID $id not found")
