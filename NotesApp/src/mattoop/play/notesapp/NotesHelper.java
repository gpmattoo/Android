package mattoop.play.notesapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class NotesHelper extends SQLiteOpenHelper {
	public static final String NOTES_ID = "_id";
	public static final String USER_NOTE = "usernote";
	public static final String USER_NOTES = "usernotes";
	
	private static final String DATABASE_NAME = "usernotes.db";
	private static final int DATABASE_VERSION = 1;
	  
	// Database creation sql statement
	  private static final String DATABASE_CREATE = "create table "
	      + USER_NOTES + "(" + NOTES_ID
	      + " integer primary key autoincrement, " + USER_NOTE
	      + " text not null);";

	public NotesHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase arg0) {
		arg0.execSQL(DATABASE_CREATE);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		Log.w(NotesHelper.class.getName(),
		        "Upgrading database from version " + arg1 + " to "
		            + arg2 + ", which will destroy all old data");
		arg0.execSQL("DROP TABLE IF EXISTS " + USER_NOTES);
		onCreate(arg0);
	}

}
