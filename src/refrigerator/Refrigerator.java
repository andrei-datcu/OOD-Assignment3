package refrigerator;

/**
 * Created by andrei on 5/3/16.
 */
public class Refrigerator {
    private final static Integer DEFAULT_TEMP = 20;
    private RefrigeratorComponent fridge;
    private RefrigeratorComponent freezer;
    private Integer minRoomTemp;
    private Integer maxRoomTemp;
    private Integer roomTemp;

    public Refrigerator(RefrigeratorConfig config) {
        minRoomTemp = config.minRoomTemp;
        maxRoomTemp = config.maxRoomTemp;
        fridge = new RefrigeratorComponent(DEFAULT_TEMP, config.minFridgeTemp, config.maxFridgeTemp,
                config.tempDeltaToStartFridge, config.timeToRiseDoorOpenFridge, config.timeToRiseDoorClosedFridge,
                config.timeToCoolFridge);
        freezer = new RefrigeratorComponent(DEFAULT_TEMP, config.minFreezerTemp, config.maxFreezerTemp,
                config.tempDeltaToStartFreezer, config.timeToRiseDoorOpenFreezer, config.timeToRiseDoorClosedFreezer,
                config.timeToCoolFreezer);
        roomTemp = DEFAULT_TEMP;
    }

    public boolean setRoomTemperature(Integer temp) {
        if (temp < minRoomTemp || temp > maxRoomTemp) {
            return false;
        }
        fridge.setRoomTemp(temp);
        freezer.setRoomTemp(temp);
        roomTemp = temp;
        return true;
    }

    public Integer getRoomTemperature() {
        return roomTemp;
    }

    public RefrigeratorComponent getFridge() {
        return fridge;
    }

    public RefrigeratorComponent getFreezer() {
        return freezer;
    }
}
