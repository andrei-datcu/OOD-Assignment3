package refrigerator;

/**
 * Class that models the Refrigerator
 */
public class Refrigerator {

    // Default room temperature
    private final static Integer DEFAULT_TEMP = 20;
    private RefrigeratorComponent fridge;
    private RefrigeratorComponent freezer;
    private Integer minRoomTemp;
    private Integer maxRoomTemp;
    private Integer roomTemp;

    /**
     * Crewate a new Refrigerator using a specified config
     * @param config
     */
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

    /**
     * Set the room's temperature
     * @param temp the new temperature of the room
     * @return true if the temperature is valid i.e it is within limits
     */
    public boolean setRoomTemperature(Integer temp) {
        if (temp < minRoomTemp || temp > maxRoomTemp) {
            return false;
        }
        fridge.setRoomTemp(temp);
        freezer.setRoomTemp(temp);
        roomTemp = temp;
        return true;
    }

    /**
     * @return current room temperature
     */
    public Integer getRoomTemperature() {
        return roomTemp;
    }

    /**
     * @return the fridge component
     */
    public RefrigeratorComponent getFridge() {
        return fridge;
    }

    /**
     * @return the freezer component
     */
    public RefrigeratorComponent getFreezer() {
        return freezer;
    }
}
