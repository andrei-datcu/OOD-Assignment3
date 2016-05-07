package refrigerator;

import java.util.*;

/**
 * Created by andrei on 5/2/16.
 */
public class RefrigeratorComponent extends Observable implements Observer {

    private Integer roomTemp;
    private Integer minTemp;
    private Integer maxTemp;
    private Integer desiredTemp;
    private Integer currentTemp;
    private Integer deltaStartTemp;
    private RefrigeratorComponentState currentState;

    RefrigeratorComponent(Integer roomTemp, Integer minTemp, Integer maxTemp,
                                 final Integer deltaStartTemp, final Integer timeToRiseTempDoorOpen,
                                 final Integer timeToRiseTempDoorClosed, final Integer timeToLowerTemp) {
        this.roomTemp = roomTemp;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.desiredTemp = maxTemp;
        this.deltaStartTemp = deltaStartTemp;
        this.currentTemp = this.desiredTemp;

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
        changeCurrentState(doorClosedIdleState);
    }

    void updateCurrentTemp(Integer delta) {
        if (currentTemp + delta > roomTemp) {
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

    public boolean setDesiredTemp(Integer desiredTemp) {
        if (desiredTemp < this.minTemp || desiredTemp > this.maxTemp || desiredTemp > roomTemp) {
            return false;
        }

        this.desiredTemp = desiredTemp;
        return true;
    }

    public Integer getCurrentTemp() {
        return currentTemp;
    }

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
