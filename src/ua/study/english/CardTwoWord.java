package ua.study.english;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import ua.study.english.other.DbHelper;
import ua.study.english.other.ParseJSON;
import ua.study.english.other.RezultEnt;
import ua.study.english.other.ShPrefs;
import ua.study.english.other.WordTranslEnt;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources.Theme;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class CardTwoWord extends Activity{

	LinearLayout card_bg;
	TextView card_tv;
	RadioGroup r_group;
	RadioButton rb_yes, rb_no;
	int position, cur_pos=0, right_ansver=0;
	private final long SIZE = 20;			//кількість слів у 1 завдані
	DbHelper dbHelper;
	private Animation scale_in=null;
	ArrayList<WordTranslEnt> list;
	Dialog dialog;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.card_two_word);
		dbHelper = new DbHelper(CardTwoWord.this);
		list = dbHelper.get_all_words();
		
		card_bg = (LinearLayout)findViewById(R.id.ll_card_bg);
		card_tv = (TextView)findViewById(R.id.tv_card);
		r_group = (RadioGroup)findViewById(R.id.r_group);
		rb_yes = (RadioButton)findViewById(R.id.rb_yes);
		rb_no = (RadioButton)findViewById(R.id.rb_no);
		
		scale_in = AnimationUtils.loadAnimation(this, R.anim.scale_in); //оголосили анімацію
		
		if(list.size()==0){
			Toast.makeText(CardTwoWord.this, R.string.no_added_words, Toast.LENGTH_SHORT).show();
		}else{
			position = ShPrefs.LoadIntPrefs(ShPrefs.CARD_TW_INTEX, 0, CardTwoWord.this); 	//зчитали номер останього слова яке відображалось
			load_new_word(position);	//показуємо слово
		}
		
		card_bg.setOnClickListener(new OnClickListener() { //обробка кліку по карті зі словом
			
			@Override
			public void onClick(View v) {
				if(list.size()!=0){
					card_bg.startAnimation(scale_in);					//показ анімації
				
					card_tv.setText(list.get(position).getTranslate());	//заміна слова на переклад
				}
				
			}
		});
		r_group.setOnCheckedChangeListener(new OnCheckedChangeListener() { //обробка результату вибору користувача
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(checkedId==R.id.rb_yes){			//якщо він правильно відповів, то 
					right_ansver++;					//збільшуємо кількість вірних відповідей на 1
				}
				load_new_word(++position);			//зчитуємо нове слово
			}
		});
	}
	
	public void load_new_word(int index){		//зчитує та відображає нове слово
		if(cur_pos<Math.min(SIZE, list.size())){				//якщо ми не пройшли всі слова
			cur_pos++;					
			card_tv.setText(list.get(index).getWord()); //відображуємо нове слово
		}else if(list.size()!=0){ //створюємо результат проходження та записуємо його в базу
			RezultEnt rezult = new RezultEnt();
			rezult.setRezult((100*right_ansver)/SIZE);
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
			String currentDate = sdf.format(new Date());
			rezult.setDate(currentDate);
			dbHelper.add_rezults(rezult);	
			show_dialog();
		}
		rb_yes.setChecked(false);				//прибираємо попередній вибір користувача
		rb_no.setChecked(false);				//прибираємо попередній вибір користувача
	}
	public void restart_activity(){			//перезапускає активність
		ShPrefs.SaveIntPrefs(ShPrefs.CARD_TW_INTEX, 0, CardTwoWord.this);
		Intent intent = new Intent(CardTwoWord.this, CardTwoWord.class);
		startActivity(intent);
		finish();
	}
	public void show_dialog(){
		TextView last, now;
		Button menu, repeat;
		dialog = new Dialog(CardTwoWord.this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog);
        dialog.setCancelable(false);
        dialog.getWindow().setLayout(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
        last = (TextView)dialog.findViewById(R.id.tv_dialog_last);
        now = (TextView)dialog.findViewById(R.id.tv_dialog_now);
        menu = (Button)dialog.findViewById(R.id.btn_dialog_menu);
        repeat = (Button)dialog.findViewById(R.id.btn_dialog_repeat);
        
        last.setText("" + ShPrefs.LoadIntPrefs(ShPrefs.LAST_REZULT, 0, CardTwoWord.this));
        now.setText("" + right_ansver);
        ShPrefs.SaveIntPrefs(ShPrefs.LAST_REZULT, right_ansver, CardTwoWord.this);
        
        menu.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent mIntent = new Intent(CardTwoWord.this, MainMenu.class);
				startActivity(mIntent);
				finish();
			}
		});
        repeat.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				restart_activity();
			}
		});
        
        dialog.show();
	}
}

