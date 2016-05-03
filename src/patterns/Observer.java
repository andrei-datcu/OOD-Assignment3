package patterns;

/**
 * Interface implemented by Observer classes
 */
public interface Observer {
    /**
     * Notify that something has changed in the observed object
     */
    void notifyChange();
}
