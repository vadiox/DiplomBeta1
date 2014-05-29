package ua.study.english;

import java.util.ArrayList;

import ua.study.english.other.AdapterAllSaveWords;
import ua.study.english.other.AdapterSelectWord;
import ua.study.english.other.DbHelper;
import ua.study.english.other.WordTranslEnt;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class AllSavingWords extends Activity{
	
	DbHelper dbhelper;
	ListView lv;
	ArrayList<WordTranslEnt> mlist;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.all_saving_words);
		
		dbhelper = new  DbHelper(AllSavingWords.this);
		lv = (ListView)findViewById(R.id.ll_all_save_word);
		
		mlist = dbhelper.get_all_words();
		lv.setAdapter(new AdapterAllSaveWords(AllSavingWords.this, mlist));
	}
}
