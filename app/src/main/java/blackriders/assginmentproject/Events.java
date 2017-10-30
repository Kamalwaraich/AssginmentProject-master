package blackriders.assginmentproject;


/*
 * Created by Sanwal Singh on 18/09/17.
 */


public class Events {

    private String id;
    private String title;
    private String activity_type;
    private String place;
    private String duration;
    private String comment;
    private String lat;
    private String lng;
    private String date;
    private String photo;

    public Events() {
    }

    public Events(String id, String title, String activity_type, String place, String duration,
                  String comment, String lat, String lng, String date, String photo) {
        this.id = id;
        this.title = title;
        this.activity_type = activity_type;
        this.place = place;
        this.duration = duration;
        this.comment = comment;
        this.lat = lat;
        this.lng = lng;
        this.date = date;
        this.photo = photo;
    }

    public Events(String title, String activity_type, String place, String duration, String comment,
                  String lat, String lng, String date, String photo) {
        this.title = title;
        this.activity_type = activity_type;
        this.place = place;
        this.duration = duration;
        this.comment = comment;
        this.lat = lat;
        this.lng = lng;
        this.date = date;
        this.photo = photo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getActivity_type() {
        return activity_type;
    }

    public void setActivity_type(String activity_type) {
        this.activity_type = activity_type;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}