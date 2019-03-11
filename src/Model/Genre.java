package Model;

public class Genre implements Comparable<Genre> {
    private int genreId;
    private User user;
    private String name;

    public Genre() {
        genreId = -1;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(Genre o) {
        return name.compareTo(o.getName());
    }
}
