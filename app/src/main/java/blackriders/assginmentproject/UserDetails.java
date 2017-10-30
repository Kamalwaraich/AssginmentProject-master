package blackriders.assginmentproject;

/**
 * Created by Sanwal Singh on 7/9/17.
 * Skype - sanwal.singh
 * Contact No. +91-774-252-6633
 */

public class UserDetails {

    private String name, email, gender, comment, isEdit;

    public UserDetails(String name, String email, String gender, String comment, String isEdit) {
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.comment = comment;
        this.isEdit = isEdit;
    }

    public UserDetails() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getIsEdit() {
        return isEdit;
    }

    public void setIsEdit(String isEdit) {
        this.isEdit = isEdit;
    }
}
