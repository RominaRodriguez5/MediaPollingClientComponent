package mosqueira.mediaPollingClientComponent.component;
import mosqueira.mediaPollingClientComponent.model.Media;
import java.util.List;


/**
 *
 * @author Lulas
 */
public class MediaPollingEvent {
    private List<Media> newMedia;
    private String dateEvent;
    
    public MediaPollingEvent(List<Media> newMedia,String dateEvent){
        this.newMedia=newMedia;
        this.dateEvent=dateEvent;
    }

    public List<Media> getNewMedia() {
        return newMedia;
    }

    public String getDateEvent() {
        return dateEvent;
    }
      
} 

