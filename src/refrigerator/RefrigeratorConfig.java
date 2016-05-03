package refrigerator;

/**
 * Created by andrei on 5/3/16.
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

    public RefrigeratorConfig(String pathToConfigFile) {
        minFridgeTemp = -2;
        maxFridgeTemp = 5;
        minFreezerTemp = -9;
        maxFreezerTemp = 0;
        minRoomTemp = 20;
        maxRoomTemp = 25;
        timeToRiseDoorClosedFridge = 2;
        timeToRiseDoorOpenFridge = 8;
        timeToRiseDoorClosedFreezer = 1;
        timeToRiseDoorOpenFreezer = 2;
        tempDeltaToStartFridge = 2;
        tempDeltaToStartFreezer = 1;
        timeToCoolFridge = 3;
        timeToCoolFreezer = 2;
    }
}
