package mattoop.play.notesapp;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class NotesDataSource {
	private SQLiteDatabase database;
	private NotesHelper dbHelper;
	private String[] allColumns = { NotesHelper.NOTES_ID,
			NotesHelper.USER_NOTE };

	
	public NotesDataSource(Context context) {
		dbHelper = new NotesHelper(context);
	}
	
	public void open() {
		database = dbHelper.getWritableDatabase();
	}
	
	public void close() {
		dbHelper.close();
	}
	
	public Notes createNotes(String notes) {
	    ContentValues values = new ContentValues();
	    values.put(NotesHelper.USER_NOTE, notes);
	    long insertId = database.insert(NotesHelper.USER_NOTES, null, values);
	    Cursor cursor = database.query(NotesHelper.USER_NOTES,
	        allColumns, NotesHelper.NOTES_ID + " = " + insertId, null,
	        null, null, null);
	    cursor.moveToFirst();
	    Notes newNote = getNotesFromCursor(cursor);
	    cursor.close();
	    return newNote;
	  }
	
	private Notes getNotesFromCursor(Cursor cursor) {
		Notes note = new Notes();
	    note.setId(cursor.getLong(0));
	    note.setUserNotes((cursor.getString(1)));
	    return note;
	  }
	
	public void deleteUserNote(Notes note) {
	    long id = note.getId();
	    System.out.println("User note deleted with id: " + id);
	    database.delete(NotesHelper.USER_NOTES, NotesHelper.NOTES_ID
	        + " = " + id, null);
	  }
	
	public List<Notes> getAllNotes() {
	    List<Notes> notes = new ArrayList<Notes>();

	    Cursor cursor = database.query(NotesHelper.USER_NOTES,
	        allColumns, null, null, null, null, null);

	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	      Notes note = getNotesFromCursor(cursor);
	      notes.add(note);
	      cursor.moveToNext();
	    }
	    // make sure to close the cursor
	    cursor.close();
	    return notes;
	  }
}
