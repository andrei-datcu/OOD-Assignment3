package refrigerator;

/**
 * Created by vlad on 05/05/16.
 */
public abstract class RefrigeratorComponentState {
    protected RefrigeratorComponent context;

    public abstract void handle(Object event);

    public abstract void run();
}
