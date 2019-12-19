package life.sc.community.dto;

public class GithubUser {
    private String name;
    private long id;
    private String bio;

    public long getId() {
        return id;
    }

    public String getBio() {
        return bio;
    }

    public String getName() {
        return name;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
