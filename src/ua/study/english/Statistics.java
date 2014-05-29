package ua.study.english;

import java.util.ArrayList;

import ua.study.english.other.AdapterGraphics;
import ua.study.english.other.DbHelper;
import ua.study.english.other.RezultEnt;
import ua.study.english.other.ShPrefs;
import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

public class Statistics extends Activity {

	ListView list;
	ArrayList<RezultEnt> rezults;
	DbHelper dbHelper;
	String user_name;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.statistics);
		dbHelper = new DbHelper(Statistics.this);
		
		list = (ListView)findViewById(R.id.ll_graphics);

		//зчитуємо всі середні результати користувача по днях
		rezults = dbHelper.day_get_middle_rezult(dbHelper.get_all_diferent_data());
		list.setAdapter(new AdapterGraphics(Statistics.this, rezults)); //відображаємо їх на екрані
	}
}
