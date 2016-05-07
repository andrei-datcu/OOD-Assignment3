package refrigerator;

/**
 * Idle state of a refrigerator component
 */
class IdleState extends RefrigeratorComponentState {

    private Integer deltaStartTemp; // temperature difference needed to start cooling

    /**
     * @param context the component that uses the state
     * @param timeToChangeTemp time needed to update the temperature (counts of Clock.TICK_INTERVAL)
     * @param deltaStartTemp temperature difference needed to start cooling
     * @param otherDoorState the next state when the door status is flipped
     * @param otherUnitState the next state when the cooling unit status is flipped
     * @param doorHandledEvent the door event that this state handles (Either Events.DOOR_OPENED or Events.DOOR_CLOSED)
     */
    IdleState(RefrigeratorComponent context,
                        Integer timeToChangeTemp,
                        Integer deltaStartTemp,
                        IdleState otherDoorState,
                        CoolingState otherUnitState,
                        Events doorHandledEvent) {
        super(context, timeToChangeTemp, 1, otherDoorState, otherUnitState, doorHandledEvent);
        this.deltaStartTemp = deltaStartTemp;
    }

    @Override
    protected void updateTemperature() {
        super.updateTemperature();
        if (context.getCurrentTemp() - context.getDesiredTemp() >= deltaStartTemp) {
            context.changeCurrentState(otherUnitState);
        }
    }

    @Override
    void run() {
        super.run();
        context.notifyObservers(Signals.IDLE);
    }
}
