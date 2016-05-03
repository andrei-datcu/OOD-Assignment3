package refrigerator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Configuration used to create a Refrigerator
 * This is modeled closely to the assignment's requests
 */
public class RefrigeratorConfig {
    Integer minFridgeTemp;
    Integer maxFridgeTemp;
    Integer minFreezerTemp;
    Integer maxFreezerTemp;
    Integer minRoomTemp;
    Integer maxRoomTemp;
    Integer timeToRiseDoorClosedFridge;
    Integer timeToRiseDoorOpenFridge;
    Integer timeToRiseDoorClosedFreezer;
    Integer timeToRiseDoorOpenFreezer;
    Integer tempDeltaToStartFridge;
    Integer tempDeltaToStartFreezer;
    Integer timeToCoolFridge;
    Integer timeToCoolFreezer;

    /**
     * @param pathToConfigFile path to a configuration file to construct from
     */
    public RefrigeratorConfig(String pathToConfigFile) {

        try {
            Scanner input = new Scanner(new File(pathToConfigFile));

            minFridgeTemp = input.nextInt();
            maxFridgeTemp = input.nextInt();
            minFreezerTemp = input.nextInt();
            maxFreezerTemp = input.nextInt();
            minRoomTemp = input.nextInt();
            maxRoomTemp = input.nextInt();
            timeToRiseDoorClosedFridge = input.nextInt();
            timeToRiseDoorOpenFridge = input.nextInt();
            timeToRiseDoorClosedFreezer = input.nextInt();
            timeToRiseDoorOpenFreezer = input.nextInt();
            tempDeltaToStartFridge = input.nextInt();
            tempDeltaToStartFreezer = input.nextInt();
            timeToCoolFridge = input.nextInt();
            timeToCoolFreezer = input.nextInt();
            
            input.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
