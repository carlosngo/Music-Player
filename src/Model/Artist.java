package Model;

public class Artist implements Comparable<Artist>{
	private int artistId;
	private String name;
	private String genre;

	public Artist() {
		artistId = -1;
	}

	public int getArtistId() {
		return artistId;
	}

	public void setArtistId(int artistId) {
		this.artistId = artistId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	@Override
	public int compareTo(Artist o) {
		return name.compareTo(o.getName());
	}

}
