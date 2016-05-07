package refrigerator;

import java.util.*;

/**
 * Refrigerator component that manages its states. This implements the state pattern, as requested
 * This observs the Clock for tick events
 * This is observed by StatusPanel
 * Managed by Refrigerator
 */
public class RefrigeratorComponent extends Observable implements Observer {

    private Integer roomTemp;
    private Integer minTemp;
    private Integer maxTemp;
    private Integer desiredTemp;
    private Integer currentTemp;
    private Integer deltaStartTemp;
    private RefrigeratorComponentState currentState;

    /**
     * @param roomTemp Room temperature
     * @param minTemp minimum allowed temperature to be set as desired temp
     * @param maxTemp maximum allowed temperature to be set as desired temp
     * @param deltaStartTemp temperature diff between current temp and desired temp needed to start the cooling unit
     * @param timeToRiseTempDoorOpen time needed for the temperature to rise by 1 degree. Measured in: Clock.TICK_INTERVAL
     * @param timeToRiseTempDoorClosed time needed for the temperature to rise by 1 degree. Measured in Clock.TICK_INTERVAL
     * @param timeToLowerTemp time needed for the temperature to lower by 1 degree. Measured in Clock.TICK_INTERVAL
     */
    RefrigeratorComponent(Integer roomTemp, Integer minTemp, Integer maxTemp,
                                 final Integer deltaStartTemp, final Integer timeToRiseTempDoorOpen,
                                 final Integer timeToRiseTempDoorClosed, final Integer timeToLowerTemp) {
        this.roomTemp = roomTemp;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.desiredTemp = maxTemp;
        this.deltaStartTemp = deltaStartTemp;
        this.currentTemp = this.desiredTemp;

        // Create the 4 states

        // Since the state graph is a cyclic we initially set some next states as null, and add them after their creation
        CoolingState doorOpenCoolingState = new CoolingState(this, timeToLowerTemp, null, null, Events.DOOR_CLOSED);
        IdleState doorClosedIdleState = new IdleState(this, timeToRiseTempDoorClosed, null, null, Events.DOOR_OPENED);

        CoolingState doorClosedCoolingState = new CoolingState(this, timeToLowerTemp, doorOpenCoolingState,
                doorClosedIdleState, Events.DOOR_OPENED);
        IdleState doorOpenIdleState = new IdleState(this, timeToRiseTempDoorOpen, doorClosedIdleState,
                doorOpenCoolingState, Events.DOOR_CLOSED);

        doorOpenCoolingState.setOtherDoorState(doorClosedCoolingState);
        doorOpenCoolingState.setOtherUnitState(doorOpenIdleState);
        doorClosedIdleState.setOtherDoorState(doorOpenIdleState);
        doorClosedIdleState.setOtherUnitState(doorClosedCoolingState);

        Clock.instance().addObserver(this);

        // Initially, the component is idle and its door is clocked
        changeCurrentState(doorClosedIdleState);
    }

    /**
     * Update this unit's current temperature. This is only called by the states
     * @param delta the amount to change the current temperature with (can be negative as well)
     */
    void updateCurrentTemp(Integer delta) {
        if (delta > 0 && currentTemp + delta > roomTemp) {
            return;
        }
        currentTemp += delta;
        notifyObservers(Signals.TEMP_CHANGED);
    }

    @Override
    public void notifyObservers(Object arg) {
        setChanged();
        super.notifyObservers(arg);
    }

    /**
     * Set the component's desired temp. This is called from StatusPanel
     * @param desiredTemp wanted temperature
     * @return true if the temperature can be set
     */
    public boolean setDesiredTemp(Integer desiredTemp) {
        if (desiredTemp < this.minTemp || desiredTemp > this.maxTemp || desiredTemp > roomTemp) {
            return false;
        }

        this.desiredTemp = desiredTemp;
        return true;
    }

    /**
     * @return current component's temperature
     */
    public Integer getCurrentTemp() {
        return currentTemp;
    }

    /**
     * Set the room temperature. This is only called from Refrigerator and does not validate the temperature
     * @param temp room temperature
     */
    void setRoomTemp(Integer temp) {
        this.roomTemp = temp;
    }

    void changeCurrentState(RefrigeratorComponentState nextState) {
        currentState = nextState;
        currentState.run();
    }

    Integer getDeltaStartTemp() {
        return deltaStartTemp;
    }

    Integer getDesiredTemp() {
        return desiredTemp;
    }

    /**
     * For observer
     * @param observable will be the clock
     * @param arg the event that clock has ticked
     */
    @Override
    public void update(Observable observable, Object arg) {
        currentState.handle(arg);
    }

    /**
     * handle one of the several other events such as door close
     * @param arg the event from the GUI
     */
    public void processEvent(Object arg) {
        currentState.handle(arg);
    }
}
