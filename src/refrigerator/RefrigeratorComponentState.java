package refrigerator;

/**
 * Base state class
 */
abstract class RefrigeratorComponentState {

    RefrigeratorComponent context;
    RefrigeratorComponentState otherDoorState;
    RefrigeratorComponentState otherUnitState;
    private Integer timeSinceLastTempChange;
    private Integer timeToChangeTemp;
    private Integer tempDelta; // either +1/-1
    private Events doorHandledEvent;

    /**
     * protected because only RefrigeratorComponent may use it
     * @param context the component that uses the state
     * @param timeToChangeTemp time needed to update the temperature (counts of Clock.TICK_INTERVAL)
     * @param tempDelta controls how the temperature is changed (+1/-1)
     * @param otherDoorState the next state when the door status is flipped
     * @param otherUnitState the next state when the cooling unit status is flipped
     * @param doorHandledEvent the door event that this state handles (Either Events.DOOR_OPENED or Events.DOOR_CLOSED)
     */
    RefrigeratorComponentState(RefrigeratorComponent context, Integer timeToChangeTemp, Integer tempDelta,
                               RefrigeratorComponentState otherDoorState,
                               RefrigeratorComponentState otherUnitState,
                               Events doorHandledEvent) {
        this.context = context;
        this.timeToChangeTemp = timeToChangeTemp;
        this.tempDelta = tempDelta;
        this.otherDoorState = otherDoorState;
        this.otherUnitState = otherUnitState;
        this.doorHandledEvent = doorHandledEvent;
    }

    /**
     * Handle an incoming event. This is called by the context
     * @param event must be from Events enum
     */
    final void handle(Object event) {
        if (event.equals(Events.CLOCK_TICKED_EVENT)) {
            handleTickEvent();
        } else if (event.equals(doorHandledEvent)) {
            handleDoorEvent();
        } else {
            throw new RuntimeException("Uknown/unsupported event");
        }
    }

    /**
     * Callback for every tick event
     */
    private void handleTickEvent() {
        timeSinceLastTempChange++;
        //System.out.println(timeSinceLastTempChange.toString() + " " + timeToChangeTemp.toString());
        if (timeSinceLastTempChange.equals(timeToChangeTemp)) {
            updateTemperature();
        }
    }

    /**
     * Called every time the temperature needs to be updated
     */
    protected void updateTemperature() {
        timeSinceLastTempChange = 0;
        context.updateCurrentTemp(tempDelta);
    }

    /**
     * Called every time door's state has changed
     */
    private void handleDoorEvent() {
        context.changeCurrentState(otherDoorState);
        if (doorHandledEvent.equals(Events.DOOR_CLOSED)) {
            context.notifyObservers(Signals.DOOR_CLOSED);
        } else {
            context.notifyObservers(Signals.DOOR_OPENED);
        }
    }

    /**
     * Called every time the state is entered in
     */
    void run() {
        timeSinceLastTempChange = 0;
    }

    /**
     * Set the state to enter in when the cooling unit flips its state (on or off)
     * @param otherUnitState
     */
    void setOtherUnitState(RefrigeratorComponentState otherUnitState) {
        this.otherUnitState = otherUnitState;
    }

    /**
     * Set the state to enter in when the door flips its state (on or off)
     * @param otherDoorState
     */
    void setOtherDoorState(RefrigeratorComponentState otherDoorState) {
        this.otherDoorState = otherDoorState;
    }
}
