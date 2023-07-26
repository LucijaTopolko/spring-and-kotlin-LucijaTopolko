CREATE TABLE cars(
    carId INT,
    date DATE NOT NULL,
    manufacturer VARCHAR(30) NOT NULL,
    model VARCHAR(30) NOT NULL,
    year INT NOT NULL,
    vin VARCHAR(12) NOT NULL,
    isCheckUpNecessary BOOLEAN DEFAULT TRUE,
    CONSTRAINT pk_cars PRIMARY KEY (carId)
);

CREATE TABLE carCheckUps(
    id INT,
    dateTime TIMESTAMP NOT NULL,
    worker VARCHAR(30) NOT NULL,
    price INT NOT NULL,
    carId INT NOT NULL,
    CONSTRAINT pk_carCheckUps PRIMARY KEY (id),
    CONSTRAINT fk_cars FOREIGN KEY (carId) REFERENCES cars(carId)
);