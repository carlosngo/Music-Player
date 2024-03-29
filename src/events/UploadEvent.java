package events;

import model.Media;

public class UploadEvent {
    private Object source;
    private Media mediaUploaded;

    public UploadEvent(Object source, Media media) {
        this.source = source;
        mediaUploaded = media;
    }

    public Object getSource() {
        return source;
    }

    public void setSource(Object source) {
        this.source = source;
    }

    public Media getMediaUploaded() {
        return mediaUploaded;
    }

    public void setMediaUploaded(Media mediaUploaded) {
        this.mediaUploaded = mediaUploaded;
    }
}
