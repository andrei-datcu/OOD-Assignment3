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

    private void handleTickEvent() {
        timeSinceLastTempChange++;
        if (timeSinceLastTempChange.equals(timeToChangeTemp)) {
            updateTemperature();
        }
    }

    protected void updateTemperature() {
        context.updateCurrentTemp(tempDelta);
    }

    private void handleDoorEvent() {
        context.changeCurrentState(otherDoorState);
        if (doorHandledEvent.equals(Events.DOOR_CLOSED)) {
            context.notifyObservers(Signals.DOOR_CLOSED);
        } else {
            context.notifyObservers(Signals.DOOR_OPENED);
        }
    }

    void run() {
        timeSinceLastTempChange = 0;
    }

    void setOtherUnitState(RefrigeratorComponentState otherUnitState) {
        this.otherUnitState = otherUnitState;
    }

    void setOtherDoorState(RefrigeratorComponentState otherDoorState) {
        this.otherDoorState = otherDoorState;
    }
}
