package mattoop.play.notesapp;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Random;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends ListActivity {

	private EditText editText;
	private NotesDataSource notesDataSource;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		notesDataSource = new NotesDataSource(this); 
		notesDataSource.open();
		List<Notes> notesList = notesDataSource.getAllNotes();
		if (notesList.size() >0) {
			// use the SimpleCursorAdapter to show the elements in a ListView
			ArrayAdapter<Notes> adapter = new ArrayAdapter<Notes>(this,
					android.R.layout.simple_list_item_1, notesList);
			setListAdapter(adapter);
		}
		editText = (EditText) findViewById(R.id.editText1);
		readFileInEditor();
	}
	
	public void onClick(View view) {
		@SuppressWarnings("unchecked")
		ArrayAdapter<Notes> adapter = (ArrayAdapter<Notes>) getListAdapter();
		Notes note = null;
		switch (view.getId()) {
		case R.id.button2:
			try {
				InputStream in = openFileInput("saveItem.txt");
				if (in != null) {
					InputStreamReader tmp = new InputStreamReader(in);
					BufferedReader reader = new BufferedReader(tmp);
					String str;
					StringBuilder buf = new StringBuilder();
					while ((str = reader.readLine()) != null) {
						buf.append(str + "n");
					}
					in.close();
					note = notesDataSource.createNotes(buf.toString());
					adapter.add(note);
					break;
				}
			} catch (java.io.FileNotFoundException e) {
				// that's OK, we probably haven't created it yet
			} catch (Throwable t) {
				Toast.makeText(this, "Exception: " + t.toString(),
						Toast.LENGTH_LONG).show();
			}
			
		case R.id.button3:
			if (getListAdapter().getCount() > 0) {
				note = (Notes) getListAdapter().getItem(0);
				notesDataSource.deleteUserNote(note);
				adapter.remove(note);
			}
			break;
		}
		adapter.notifyDataSetChanged();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void saveClicked(View v) {
		try {
			OutputStreamWriter out = new OutputStreamWriter(openFileOutput(
					"saveItem.txt", 0));
			out.write(editText.getText().toString());
			out.close();
			Toast.makeText(this, "The contents are saved in the file.",
					Toast.LENGTH_LONG).show();
		} catch (Throwable t) {
			Toast.makeText(this, "Exception: " + t.toString(),
					Toast.LENGTH_LONG).show();
		}
	}
	
	public void readFileInEditor() {
		try {
			InputStream in = openFileInput("saveItem.txt");
			if (in != null) {
				InputStreamReader tmp = new InputStreamReader(in);
				BufferedReader reader = new BufferedReader(tmp);
				String str;
				StringBuilder buf = new StringBuilder();
				while ((str = reader.readLine()) != null) {
					buf.append(str + "n");
				}
				in.close();
				editText.setText(buf.toString());
			}
		} catch (java.io.FileNotFoundException e) {
			// that's OK, we probably haven't created it yet
		} catch (Throwable t) {
			Toast.makeText(this, "Exception: " + t.toString(),
					Toast.LENGTH_LONG).show();
		}
	}
	
	@Override
	  protected void onResume() {
		notesDataSource.open();
	    super.onResume();
	  }

	  @Override
	  protected void onPause() {
		notesDataSource.close();
	    super.onPause();
	  }
}