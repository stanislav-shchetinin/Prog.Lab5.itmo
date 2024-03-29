import base.Coordinates;
import base.Vehicle;
import base.VehicleType;
import commands.ExecuteScript;
import commands.Save;
import console.Console;

import lombok.extern.java.Log;
import service.CollectionClass;
import service.FileRead;
import service.command.Command;

import java.io.*;
import java.util.Scanner;

import static console.Console.*;
import static service.FileRead.fromFileVehicle;
import static service.Parse.parseFromCSVtoString;

/**
 * Это главный класс с методом main
*/
public class Main {

    /**
     * Метод main - стартовая точка проекта
     */
    public static void main(String[] args) {

            CollectionClass collectionClass = new CollectionClass(); //Менеджер коллекции
            File file = getFile(new Scanner(System.in)); //NAME_FILE

            fromFileVehicle(collectionClass, new Scanner(parseFromCSVtoString(file))); //Считывание файла и запись его в collectionClass

            Console.inputCommands(collectionClass, file); //Ввод команд из консоли

    }
}   