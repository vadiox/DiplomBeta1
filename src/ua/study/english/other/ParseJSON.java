package ua.study.english.other;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ua.study.english.Statistics;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
	
	//відповідає за роботу з файлом JSON
public class ParseJSON {
	
	public static DbHelper mdb;
	static SQLiteDatabase sq_lite_db;
	static Context mContex;
	public static ArrayList<WordTranslEnt> list = new ArrayList<WordTranslEnt>();
	public static ArrayList<AsocCardEnt> list_asoc_card = new ArrayList<AsocCardEnt>();

	public static void parse_word(DbHelper db, final Context context){
		 mContex = context;
		 	try {
				JSONArray jsonAr = new JSONArray(loadJSONFromAsset("word_translate.json"));	//зчитує масив з папки assets
				ShPrefs.SaveIntPrefs(ShPrefs.MAX_POS, jsonAr.length(), context);	//записуємо кількість елементів у масиві в базу
				
				for(int i=0; i<jsonAr.length();i++){		//зчитуємо всі слова з JSON-масиву та записуэмо ъх у масив слів 
					JSONObject object = new JSONObject();
					object=jsonAr.getJSONObject(i);
					WordTranslEnt word = new WordTranslEnt();
					word.setId(i);
					word.setWord(object.getString("text"));
					word.setTranslate(object.getString("word"));
					list.add(word);
				}
			} catch (JSONException e) {
				Log.e("error parse", "------------");		//виводимо запис при винекнені помилки в розборі файлу
				e.printStackTrace();
			}
	}

	public static String loadJSONFromAsset(String name) {		//зчитує, та повертає у вигляді рядка файл "word_translate.json"
		    String json = null;
		    try {
		        AssetManager assetManager = mContex.getResources().getAssets();
		        InputStream is = assetManager.open(name);
		        int size = is.available();
		        byte[] buffer = new byte[size];
		        is.read(buffer);
		        is.close();
		        json = new String(buffer, "UTF-8");
		    } catch (IOException ex) {
		        ex.printStackTrace();
		        return null;
		    }
		    return json;
	}
	
	public static void parse_asoc_card(){
		try {
			JSONArray jsonAr = new JSONArray(loadJSONFromAsset("associative_cards.json"));
			for(int i=0; i<jsonAr.length();i++){		//зчитуємо всі слова з JSON-масиву та записуэмо ъх у масив слів 
				JSONObject object = jsonAr.getJSONObject(i);
				AsocCardEnt word = new AsocCardEnt();
				word.setImg(object.getString("img"));
				word.setVar1(object.getString("v1"));
				word.setVar2(object.getString("v2"));
				word.setVar3(object.getString("v3"));
				word.setVar4(object.getString("v4"));
				word.setRight_ans(object.getString("right"));
				list_asoc_card.add(word);

			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public static ArrayList<WordTranslEnt> get_10_words(int start){		//зчитує з всього масиву слів 10, починаючи з переданого номера та повертає їх  
		ArrayList<WordTranslEnt> words = new ArrayList<WordTranslEnt>();
		int max = ShPrefs.LoadIntPrefs(ShPrefs.MAX_POS, 0, mContex);
			for (int i=start;i<start+10;i++){
				words.add(list.get(i));
			}
		return words;
	}
	
	
}
