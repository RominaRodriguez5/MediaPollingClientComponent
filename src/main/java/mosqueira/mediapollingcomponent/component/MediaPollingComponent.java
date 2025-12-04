package mosqueira.mediapollingcomponent.component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import mosqueira.mediapollingcomponent.MediaPollingListener;
import mosqueira.mediapollingcomponent.controller.ApiClient;


/**
 *
 * @author Lulas
 */
public class MediaPollingComponent extends JPanel implements Serializable, ActionListener{

    private ApiClient apiClient;
    private String apiUrl;
    private int pollingInterval = 0;
    private String token;
    private String lastChecked;
    private JLabel iconLabel;
    private List<MediaPollingListener> listeners;
    private Timer restartTimer;
    private boolean running;
    
    public MediaPollingComponent(){
        
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
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

    public void setPollingInterval(int pollingInterval) {
        this.pollingInterval = pollingInterval;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
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

    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
}

