package console;

import base.Vehicle;
import exceptions.ReadTypeException;
import exceptions.ReadValueException;
import service.CollectionClass;
import service.Validate;
import service.command.Command;
import service.command.InitMap;

import java.io.File;
import java.lang.reflect.Field;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;
import java.util.logging.Logger;

import static service.Validate.thisType;
import static service.Validate.uuidFromString;

public class Console {

    private static final Logger logger = Logger.getLogger(Console.class.getName());
    public static File getFile(){

        String nameFile = "";
        Map<String, String> mapEnv = System.getenv();
        Scanner in = new Scanner(System.in);

        while (true){
            System.out.print("\nВведите имя переменной среды: ");
            String nameVar = in.nextLine().trim();

            if (mapEnv.containsKey(nameVar)){
                nameFile = mapEnv.get(nameVar);
                File file = new File(nameFile);
                if (file.exists() && !file.isDirectory()){
                    logger.info("Файл успешно получен");
                    return file;
                } else {
                    System.out.print("Не существует файла по указанному пути");
                }
            } else {
                System.out.print("Не существует такой переменной окружения");
            }
        }
    }
    public static void inputCommands(CollectionClass collectionClass) {
        HashMap<String, Command> mapCommand = InitMap.mapCommand(collectionClass);
        Scanner in = new Scanner(System.in);
        while (true){
            String nameCommand = in.next();
            Command command = mapCommand.get(nameCommand);
            command.getParametr();
            command.execute();
        }

    }

    public static Vehicle inputVehicle(CollectionClass collectionClass) {
        Vehicle vehicle = new Vehicle();
        Scanner in = new Scanner(System.in);
        for (Field field : vehicle.getClass().getDeclaredFields()){
            field.setAccessible(true);
            boolean isCorrectValue = false;
            while (!isCorrectValue){
                try {
                    isCorrectValue = true;
                    System.out.println(String.format("\nВведите %s: ", field.getName()));
                    String value = in.nextLine();
                    field.set(vehicle, thisType(value, field, collectionClass));
                } catch (IllegalArgumentException e) {
                    isCorrectValue = false;
                    logger.warning(String.format("Неверный тип %s", field.getName()));
                } catch (IllegalAccessException e){
                    logger.fine("Запись в поле запрещена");
                } catch (ReadValueException e){
                    isCorrectValue = false;
                    logger.warning(e.getMessage());
                }
            }
        }
        return vehicle;
    }

    public static UUID inputUUID (CollectionClass collectionClass){
        Scanner in = new Scanner(System.in);
        while (true){
            String value = in.next();
            try {
                UUID uuid = uuidFromString(value, collectionClass);
                return uuid;
            } catch (ReadValueException e) {
                logger.warning(e.getMessage());
            } catch (IllegalArgumentException e) {
                logger.warning("Неверный тип id");
            }
        }
    }

}
