package refrigerator;

/**
 * Created by andrei on 5/7/16.
 */
class IdleState extends RefrigeratorComponentState {

    protected IdleState(RefrigeratorComponent context,
                        Integer timeToChangeTemp,
                        IdleState otherDoorState,
                        CoolingState otherUnitState,
                        Events doorHandledEvent) {
        super(context, timeToChangeTemp, 1, otherDoorState, otherUnitState, doorHandledEvent);
    }

    @Override
    protected void updateTemperature() {
        super.updateTemperature();
        if (context.getCurrentTemp() - context.getDesiredTemp() >= context.getDeltaStartTemp()) {
            context.changeCurrentState(otherUnitState);
        }
    }

    @Override
    void run() {
        super.run();
        context.notifyObservers(Signals.IDLE);
    }
}
