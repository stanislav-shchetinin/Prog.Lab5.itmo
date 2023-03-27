package service;

import base.Coordinates;
import base.Vehicle;
import exceptions.ReadValueException;
import lombok.extern.java.Log;

import java.lang.reflect.Field;
import java.util.*;

import static service.Validate.*;
@Log
public class FileRead {

    public static void fromFileVehicle(CollectionClass collectionClass, Scanner in) {
        while (in.hasNext()){
            Vehicle vehicle = new Vehicle();
            boolean isCorrectVehicle = true;
            for (Field field : vehicle.getClass().getDeclaredFields()){
                field.setAccessible(true);
                if (in.hasNext()){
                    try {
                        String value = in.nextLine();
                        if (field.getType() == Coordinates.class && in.hasNext()){
                            value += " " + in.nextLine();
                            value.replaceAll(",", ".");
                        }
                        field.set(vehicle, thisType(value, field, collectionClass));
                    } catch (IllegalArgumentException e) {
                        isCorrectVehicle = false;
                        log.warning(String.format("Неверный тип %s", field.getName()));
                        break;
                    } catch (ReadValueException e) {
                        isCorrectVehicle = false;
                        log.warning(e.getMessage());
                    } catch (IllegalAccessException e) {
                        log.warning("Нет доступа к полю");
                    }
                }
            }
            if (isCorrectVehicle){
                collectionClass.add(vehicle);
            } else {
                break;
            }
        }
    }

}
