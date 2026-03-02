package mosqueira.mediaPollingClientComponent.component;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import mosqueira.mediaPollingClientComponent.controller.ApiClient;
import mosqueira.mediaPollingClientComponent.model.Media;

/**
 *
 * @author Lulas
 */
public class MediaPollingClientComponent extends JPanel implements Serializable, ActionListener {

    private ApiClient apiClient;
    private String apiUrl;
    private int pollingInterval = 0;
    private String token;
    private String lastChecked;
    private JLabel iconLabel;
    private List<MediaPollingClientListener> listeners;
    private Timer restartTimer;
    private boolean running;

    public MediaPollingClientComponent() {
        setLayout(new BorderLayout());
        iconLabel = new JLabel(new ImageIcon(getClass().getResource("/image/update.png")));
        add(iconLabel, BorderLayout.CENTER);
        listeners = new ArrayList<>();
        restartTimer=new Timer(pollingInterval,this);
        running = false;
        
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
        if (lastChecked == null || lastChecked.isBlank()) {
        this.lastChecked = null;
    } else {
        this.lastChecked = lastChecked;
    }
    }

    public JLabel getIconLabel() {
        return iconLabel;
    }

    public void setIconLabel(JLabel iconLabel) {
        this.iconLabel = iconLabel;
    }

    public List<MediaPollingClientListener> getListeners() {
        return listeners;
    }

    public void setListeners(List<MediaPollingClientListener> listeners) {
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

    public void addMediaPollingListener(MediaPollingClientListener l) {
        listeners.add(l);
    }

    public void fireNewMediaEvent(MediaPollingClientEvent e) {
        for (MediaPollingClientListener l : listeners) {
            l.onNewMediaDetected(e);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!running) {
            System.out.println("MediaPollingComponent: running = false, no se realiza polling.");
            return;
        }

        if (token == null || token.trim().isEmpty()) {
            System.out.println("MediaPollingComponent: token no configurado, no se realiza polling.");
            return;
        }
        if (apiUrl == null || apiUrl.trim().isEmpty()) {
            System.out.println("MediaPollingComponent: apiUrl no configurada, no se llama a la API.");
            return;
        }

        if (apiClient == null) {
            System.out.println("MediaPollingComponent: creando nuevo ApiClient con apiUrl = " + apiUrl);
            apiClient = new ApiClient(apiUrl);
        }
        String fechaAhora = OffsetDateTime.now().toString();

        if (lastChecked == null || lastChecked.isBlank()) {
            System.out.println("MediaPollingComponent: primera ejecución, inicializando lastChecked = " + fechaAhora);
            lastChecked = fechaAhora;
            return;
        }

        try {
            List<Media> newMedia = apiClient.getMediaAddedSince(lastChecked, token);

            if (!newMedia.isEmpty()) {
                MediaPollingClientEvent event = new MediaPollingClientEvent(newMedia, fechaAhora);
                System.out.println("MediaPollingComponent: respuesta recibida, nuevos elementos = " + newMedia);
                fireNewMediaEvent(event);
            }

        } catch (Exception ex) {
            System.out.println("MediaPollingComponent: error al consultar la API:");
            ex.printStackTrace();
        }

        lastChecked = fechaAhora;
    }
    
    //Wrappers de ApiClient
    public String login(String user,String pass)throws Exception{
        if(apiClient ==null){
           apiClient=new ApiClient(apiUrl);
        }
        return apiClient.login(user, pass);
    }
    
    public String getNickName(int id,String token)throws Exception{
       if(apiClient==null){
           apiClient=new ApiClient(apiUrl);
       }
       return apiClient.getNickName(id,token);
    }
    
    public List<Media> getAllMedia(String token)throws Exception{
        if(apiClient==null){
            apiClient=new ApiClient(apiUrl);
        }
        return apiClient.getAllMedia(token);
    }
    
    public void download(int id,File destFile, String token)throws Exception{
        if(apiClient==null){
            apiClient=new ApiClient(apiUrl);
        }
        apiClient.download(id, destFile, token);
    }
    
    public String uploadFileMulti(File file, String downloadedFromUrl,String token)throws Exception{
        if(apiClient==null){
           apiClient=new ApiClient(apiUrl);
        }
        return apiClient.uploadFileMultipart(file, downloadedFromUrl, token);
    }
}