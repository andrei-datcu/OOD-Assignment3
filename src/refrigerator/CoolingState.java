package refrigerator;

/**
 * Cooling state for refrigerator component
 */
class CoolingState extends RefrigeratorComponentState {

    /**
     * protected because only RefrigeratorComponent may use it
     *
     * @param context          the component that uses the state
     * @param timeToChangeTemp time needed to update the temperature (counts of Clock.TICK_INTERVAL)
     * @param otherDoorState   the next state when the door status is flipped
     * @param otherUnitState   the next state when the cooling unit status is flipped
     * @param doorHandledEvent the door event that this state handles (Either Events.DOOR_OPENED or Events.DOOR_CLOSED)
     */
    CoolingState(RefrigeratorComponent context,
                 Integer timeToChangeTemp,
                 CoolingState otherDoorState,
                 IdleState otherUnitState,
                 Events doorHandledEvent) {
        super(context, timeToChangeTemp, -1, otherDoorState, otherUnitState, doorHandledEvent);
    }

    @Override
    protected void updateTemperature() {
        super.updateTemperature();
        if (context.getCurrentTemp() <= context.getDesiredTemp()) {
            context.changeCurrentState(otherUnitState);
        }
    }

    @Override
    void run() {
        super.run();
        context.notifyObservers(Signals.COOLING);
    }
}
