package src.main.kotlin

class NoVinException(vin: String): Exception("There is no car with vin $vin")