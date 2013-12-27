package mattoop.play.notesapp;

public class Notes {
	private long id;
	private String userNotes;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUserNotes() {
		return userNotes;
	}
	public void setUserNotes(String userNotes) {
		this.userNotes = userNotes;
	}
	
	@Override
	public String toString() {
		return userNotes;
	}
}
