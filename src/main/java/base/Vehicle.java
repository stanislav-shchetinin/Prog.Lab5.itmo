package base;

import exceptions.ReadValueException;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.UUID;

public class  Vehicle implements Comparable<Vehicle>{
    private UUID id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private double enginePower; //Значение поля должно быть больше 0
    private Long capacity; //Поле может быть null, Значение поля должно быть больше 0
    private Double distanceTravelled; //Поле может быть null, Значение поля должно быть больше 0
    private VehicleType type; //Поле не может быть null

    public Vehicle(){}
    public Vehicle(String name, Coordinates coordinates, double enginePower,
                   Long capacity, Double distanceTravelled, VehicleType type) throws ReadValueException {
        this.name = name;
        this.coordinates = coordinates;
        this.enginePower = enginePower;
        this.capacity = capacity;
        this.distanceTravelled = distanceTravelled;
        this.type = type;

        this.creationDate = ZonedDateTime.now(); //текущая дата

        if (this.coordinates.getY() > -769){
            throw new ReadValueException("Координата Y должна быть не больше -769");
        }

        if (this.enginePower <= 0){
            throw new ReadValueException("enginePower должна быть больше 0, установлено значение 100 в enginePower");
        }

        if (this.capacity <= 0){
            throw new ReadValueException("capacity должна быть больше 0, установлено значение 4 в capacity");
        }

        if (this.distanceTravelled <= 0){
            throw new ReadValueException("distanceTravelled должна быть больше 0, установлено значение 0.1 в distanceTravelled");
        }

    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public void setEnginePower(double enginePower) {
        this.enginePower = enginePower;
    }

    public void setCapacity(Long capacity) {
        this.capacity = capacity;
    }

    public void setDistanceTravelled(Double distanceTravelled) {
        this.distanceTravelled = distanceTravelled;
    }

    public void setType(VehicleType type) {
        this.type = type;
    }

    public double getEnginePower() {
        return enginePower;
    }

    public UUID getId() {
        return id;
    }

    public Long getCapacity() {
        return capacity;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    private int compDouble (double l, double r){
        return Double.valueOf(l).compareTo(Double.valueOf(r));
    }

    @Override
    public int compareTo(Vehicle o) {

        int arrRes[] = new int[8];
        arrRes[0] = this.type.compareTo(o.type);
        arrRes[1] = compDouble(this.enginePower, o.enginePower);
        arrRes[2] = this.capacity.compareTo(o.capacity);
        arrRes[3] = compDouble(this.distanceTravelled, o.distanceTravelled);
        arrRes[4] = this.coordinates.compareTo(o.coordinates);
        arrRes[5] = this.name.compareTo(o.name);
        arrRes[6] = this.creationDate.compareTo(o.creationDate);
        arrRes[7] = this.id.compareTo(o.id);

        for (int x : arrRes){
            if (x != 0){
                return x;
            }
        }
        return  0;

    }

    @Override
    public String toString() {
        return String.format(
                "\nId: %s\nName: %s\nCoordinates: %s\nCreation Date: %s" +
                        "\nEngine Power: %f\nCapacity: %d\nDistance Travelled: %f\nType: %s\n******************\n",
                id.toString(), name, coordinates.toString(), creationDate.toString(), enginePower, capacity,
                distanceTravelled, type.toString()
        );
    }
}