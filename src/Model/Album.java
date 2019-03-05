package Model;

import java.sql.Blob;

public class Album implements Comparable<Album> {
    private int albumId;
    private int userId;
    private String name;
    private String artist;
    private String coverPath;
    private Blob file;


	public Album() {
    	albumId = -1;
	}
	public int getAlbumId() {
		return albumId;
	}
	public void setAlbumId(int albumId) {
		this.albumId = albumId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getArtist() {
		return artist;
	}
	public void setArtist(String artist) {
		this.artist = artist;
	}
	public String getCoverPath() {
		return coverPath;
	}
	public void setCoverPath(String coverPath) {
		this.coverPath = coverPath;
	}
    public Blob getFile() {
		return file;
	}
	public void setFile(Blob file) {
		this.file = file;
	}

	@Override
	public int compareTo(Album o) {
		return name.compareTo(o.getName());
	}
}
