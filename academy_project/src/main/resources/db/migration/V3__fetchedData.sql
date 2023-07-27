CREATE TABLE carinfo(
    modelid UUID,
    manufacturer VARCHAR(30) NOT NULL,
    model VARCHAR(30) NOT NULL,
    CONSTRAINT pk_carinfo PRIMARY KEY(modelid)
);

ALTER TABLE cars ADD COLUMN modelid UUID;

UPDATE cars SET modelid = (
    SELECT carinfo.modelid FROM carinfo WHERE manufacturer = cars.manufacturer AND model = cars.model
);

ALTER TABLE carCheckUps DROP CONSTRAINT fk_cars;
ALTER TABLE carCheckUps ADD CONSTRAINT fk_cars FOREIGN KEY(carId) REFERENCES cars(carId) ON DELETE CASCADE;

DELETE FROM cars WHERE modelid IS NULL;

ALTER TABLE cars ADD CONSTRAINT fk_carinfo FOREIGN KEY(modelid) REFERENCES carinfo(modelid);

ALTER TABLE cars DROP COLUMN manufacturer;
ALTER TABLE cars DROP COLUMN model;