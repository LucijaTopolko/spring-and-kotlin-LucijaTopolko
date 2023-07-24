CREATE TABLE cars(
    carId UUID PRIMARY KEY,
    date DATE NOT NULL,
    manufacturer VARCHAR(30) NOT NULL,
    model VARCHAR(30) NOT NULL,
    year INT NOT NULL,
    vin VARCHAR(12) NOT NULL,
    isCheckUpNecessary BOOLEAN DEFAULT TRUE
);

CREATE TABLE carCheckUps(
    id UUID PRIMARY KEY,
    dateTime TIMESTAMP NOT NULL,
    worker VARCHAR(30) NOT NULL,
    price INT NOT NULL,
    carId UUID NOT NULL,
    FOREIGN KEY (carId) REFERENCES cars(carId)
);