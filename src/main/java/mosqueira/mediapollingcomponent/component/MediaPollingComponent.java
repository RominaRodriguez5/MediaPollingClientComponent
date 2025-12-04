package mosqueira.mediapollingcomponent.component;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import mosqueira.mediapollingcomponent.MediaPollingListener;
import mosqueira.mediapollingcomponent.controller.ApiClient;

/**
 *
 * @author Lulas
 */
public class MediaPollingComponent extends JPanel implements Serializable, ActionListener {

    private ApiClient apiClient;
    private String apiUrl;
    private int pollingInterval = 0;
    private String token;
    private String lastChecked;
    private JLabel iconLabel;
    private List<MediaPollingListener> listeners;
    private Timer restartTimer;
    private boolean running;

    public MediaPollingComponent() {
        setLayout(new BorderLayout());
        iconLabel = new JLabel(new ImageIcon(getClass().getResource("/image/update.png")));
        add(iconLabel, BorderLayout.CENTER);
        listeners = new ArrayList<>();
        running = false;
        lastChecked = null;

    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public int getPollingInterval() {
        return pollingInterval;
    }

    /**
     * Intervalo de polling en segundos.
     */
    public void setPollingInterval(int pollingInterval) {
        if (pollingInterval < 1) {
            return;
        }

        this.pollingInterval = pollingInterval;

        if (restartTimer != null) {
            restartTimer.stop();
        }
        // javax.swing.Timer en milisegundos
        restartTimer = new Timer(pollingInterval * 1000, this);

        if (running) {
            restartTimer.start();
        }
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        if (token == null || token.trim().isEmpty()) {
            this.token = null;
            return;
        }
        this.token = token;
    }

    public String getLastChecked() {
        return lastChecked;
    }

    public void setLastChecked(String lastChecked) {
        this.lastChecked = lastChecked;
    }

    public JLabel getIconLabel() {
        return iconLabel;
    }

    public void setIconLabel(JLabel iconLabel) {
        this.iconLabel = iconLabel;
    }

    public List<MediaPollingListener> getListeners() {
        return listeners;
    }

    public void setListeners(List<MediaPollingListener> listeners) {
        this.listeners = listeners;
    }

    public Timer getRestartTimer() {
        return restartTimer;
    }

    public void setRestartTimer(Timer restartTimer) {
        this.restartTimer = restartTimer;
    }

    public boolean isRunning() {
        return running;
    }

    /**
     * Si running = true, el componente empieza a hacer polling.
     */
    public void setRunning(boolean running) {
        this.running = running;

        if (!running) {
            if (restartTimer != null) {
                restartTimer.stop();
            }
            return;
        }

        if (restartTimer != null) {
            restartTimer.start();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
