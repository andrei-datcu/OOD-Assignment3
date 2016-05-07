package refrigerator;

/**
 * Signals sent by a RefrigeratorComponent to its Observer (StatusPanel)
 */
public enum Signals {
    IDLE,
    COOLING,
    DOOR_OPENED,
    DOOR_CLOSED,
    TEMP_CHANGED
}
