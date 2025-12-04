package mosqueira.mediapollingcomponent.component;

import mosqueira.mediapollingcomponent.MediaPollingEvent;

/**
 *
 * @author Lulas
 */
public interface MediaPollingListener {
    void onNewMediaDetected(MediaPollingEvent event);
}
