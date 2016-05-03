package refrigerator;

import patterns.Observable;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by andrei on 5/2/16.
 */
public class RefrigeratorComponent extends Observable {
    // minimum interval in which the state changes in MILLISECONDS -- defaults to 1 minute
    // change this for debug purposes
    public static final long TICK_INTERVAL = 5000;
    private Integer roomTemp;
    private Integer minTemp;
    private Integer maxTemp;
    private Integer desiredTemp;
    private Integer currentTemp;
    private Integer deltaStartTemp;
    private Integer timeToRiseTempDoorOpen;
    private Integer timeToRiseTempDoorClosed;
    private Integer timeToLowerTemp;
    private boolean isDoorClosed;
    private boolean isCoolingUnitOn;
    private Integer timeSinceLastTempChanged;

    private Timer tickTimer;

    public RefrigeratorComponent(Integer roomTemp, Integer minTemp, Integer maxTemp,
                                 final Integer deltaStartTemp, final Integer timeToRiseTempDoorOpen,
                                 final Integer timeToRiseTempDoorClosed, final Integer timeToLowerTemp) {
        this.roomTemp = roomTemp;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.desiredTemp = maxTemp;
        this.deltaStartTemp = deltaStartTemp;
        this.timeToRiseTempDoorOpen = timeToRiseTempDoorOpen;
        this.timeToRiseTempDoorClosed = timeToRiseTempDoorClosed;
        this.timeToLowerTemp = timeToLowerTemp;

        this.isDoorClosed = true;
        this.isCoolingUnitOn = false;
        this.currentTemp = this.desiredTemp;

        timeSinceLastTempChanged = 0;

        this.tickTimer = new Timer();
        this.tickTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                synchronized (RefrigeratorComponent.this) {
                    timeSinceLastTempChanged++;
                    if (isCoolingUnitOn) {

                        // Do we still need the unit to run?
                        if (currentTemp <= desiredTemp) {
                            setCoolingUnit(false);
                        }

                        // We decrease the temperature
                        if (timeSinceLastTempChanged >= timeToLowerTemp) {
                            updateCurrentTemp(-1);
                        }
                    } else {
                        Integer timeToRaise = isDoorClosed ? timeToRiseTempDoorClosed : timeToRiseTempDoorOpen;
                        if (timeSinceLastTempChanged >= timeToRaise) {
                            updateCurrentTemp(1);
                        }

                        if (currentTemp - desiredTemp >= deltaStartTemp) {
                            setCoolingUnit(true);
                        }
                    }
                }
            }
        }, TICK_INTERVAL, TICK_INTERVAL);
    }

    private void updateCurrentTemp(Integer delta) {
        if (currentTemp + delta > roomTemp) {
            return;
        }
        currentTemp += delta;
        timeSinceLastTempChanged = 0;
        notifyObservers();
    }

    private void setCoolingUnit(Boolean on) {
        isCoolingUnitOn = on;
        timeSinceLastTempChanged = 0;
        notifyObservers();
    }

    public boolean setDesiredTemp(Integer desiredTemp) {
        if (desiredTemp < this.minTemp || desiredTemp > this.maxTemp || desiredTemp > roomTemp) {
            return false;
        }

        synchronized (this) {
            this.desiredTemp = desiredTemp;
            return true;
        }
    }

    public void setDoor(Boolean closed) {
        synchronized (this) {
            this.isDoorClosed = closed;
        }
    }

    public Integer getCurrentTemp() {
        return currentTemp;
    }

    public boolean isCoolingUnitRunning() {
        return isCoolingUnitOn;
    }

    public boolean isDoorClosed() {
        return isDoorClosed;
    }

    void setRoomTemp(Integer temp) {
        this.roomTemp = temp;
    }
}
