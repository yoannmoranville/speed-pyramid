package eu.speedbadminton.pyramid.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Yoann Moranville
 * Date: 25/06/2013
 *
 * @author Yoann Moranville
 */

@Document(collection = "player")
public class Player {
    @Id
    private ObjectId id;

    private String name;

    private long pyramidPosition;

    private String email;

    private String password;

    private Role role;

    private Gender gender;

    private String avatarPath;

    private boolean enabled;

    public Player() {}

    public Player(String name, String email, String password, Gender gender) {
        super();
        this.name = name;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.enabled = true;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPyramidPosition() {
        return pyramidPosition;
    }

    public void setPyramidPosition(long pyramidPosition) {
        this.pyramidPosition = pyramidPosition;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public enum Role {
        ADMIN, NONE
    }

    public enum Gender {
        MALE, FEMALE
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "Player [id=" + id + ", name=" + name + ", pyramidPosition=" + pyramidPosition + ", email=" + email + ", role=" + role.toString() + ", gender=" + gender.toString() + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj==null) return false;
        if (! (obj instanceof Player)) return false;

        Player player = (Player) obj;

        if (player.getId()==null || this.getId()==null) return false;

        return this.getId().equals(player.getId());

    }

}
