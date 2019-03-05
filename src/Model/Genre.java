package Model;

public class Genre implements Comparable<Genre> {
    private int genreId;
    private int userId;
    private String name;

    public Genre() {
        genreId = -1;
    }

    public Genre(int genreId, int userId, String name) {
        this.genreId = genreId;
        this.userId = userId;
        this.name = name;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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
