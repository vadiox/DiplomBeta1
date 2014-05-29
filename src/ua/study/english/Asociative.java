package ua.study.english;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import ua.study.english.other.AsocCardEnt;
import ua.study.english.other.DbHelper;
import ua.study.english.other.ParseJSON;
import ua.study.english.other.RezultEnt;
import ua.study.english.other.ShPrefs;
import android.R.integer;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Asociative extends Activity implements OnClickListener{
	
	Button v1, v2,v3,v4;
	ImageView image;
	int index;
	private final long size = 20;
	private int position = 0;
	Dialog dialog;
	String right_ans;
	private final int SLEEP = 1000;
	private int count_right_answer = 0;
	DbHelper dbhelper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.asociative);
		dbhelper = new DbHelper(Asociative.this);
		index = ShPrefs.LoadIntPrefs(ShPrefs.ASOC_INDEX, 0, Asociative.this);
		
		v1 = (Button)findViewById(R.id.btn_asoc_v1);
		v2 = (Button)findViewById(R.id.btn_asoc_v2);
		v3 = (Button)findViewById(R.id.btn_asoc_v3);
		v4 = (Button)findViewById(R.id.btn_asoc_v4);
		
		image = (ImageView)findViewById(R.id.iv_asoc);
		
		v1.setOnClickListener(this);
		v2.setOnClickListener(this);
		v3.setOnClickListener(this);
		v4.setOnClickListener(this);
		
		load_asoc_word();
	}

	public void load_asoc_word(){
		
			
			v1.setBackgroundResource(R.drawable.def);
			v2.setBackgroundResource(R.drawable.def);
			v3.setBackgroundResource(R.drawable.def);
			v4.setBackgroundResource(R.drawable.def);
		
		if(position<size){
			AsocCardEnt word = ParseJSON.list_asoc_card.get(index);
			index = (index+1)%ParseJSON.list_asoc_card.size();
			String img = word.getImg();
			ArrayList<String> listAnswer = new ArrayList<String>();
			
			listAnswer.add(word.getVar1());
			listAnswer.add(word.getVar2());
			listAnswer.add(word.getVar3());
			listAnswer.add(word.getVar4());
			
			
			
			long ts = System.currentTimeMillis();
			v1.setText(listAnswer.get((int) (ts%4)));
			v2.setText(listAnswer.get((int) ((ts+1)%4)));
			v3.setText(listAnswer.get((int) ((ts+2)%4)));
			v4.setText(listAnswer.get((int) ((ts+3)%4)));
			
			right_ans = word.getRight_ans();
			position++;
			try {
				Bitmap bm = getBitmapFromAsset(img);
				image.setImageBitmap(bm);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			ShPrefs.SaveIntPrefs(ShPrefs.ASOC_INDEX, index, Asociative.this);
			
			RezultEnt rezult = new RezultEnt();
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
				String currentDate = sdf.format(new Date());
			rezult.setDate(currentDate);
			rezult.setRezult((100*count_right_answer)/size);
			dbhelper.add_rezults(rezult);
			show_dialog();
		}
		
	}
	private Bitmap getBitmapFromAsset(String strName) throws IOException {
	    AssetManager assetManager = getAssets();
	    InputStream istr = assetManager.open(strName);
	    Bitmap bitmap = BitmapFactory.decodeStream(istr);
	    return bitmap;
	 }

	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.btn_asoc_v1:
			Log.e("btn 1", "------");
			show_answer(v1);
			break;
		case R.id.btn_asoc_v2:
			show_answer(v2);		
			break;
		case R.id.btn_asoc_v3:
			show_answer(v3);
			break;
		case R.id.btn_asoc_v4:
			show_answer(v4);
			break;
		default:
			break;
		}
		
		
		
	}
	public void show_answer(Button btn){
		
		if(btn.getText().toString().equals(right_ans)){
			btn.setBackgroundResource(R.drawable.ok);
			count_right_answer++;
			Log.e("asoc", "ok+++++++");
		}else{
			btn.setBackgroundResource(R.drawable.error);
			if(v1.getText().toString().contains(right_ans.toString())){
				v1.setBackgroundResource(R.drawable.ok);
			}
			if(v2.getText().toString().contains(right_ans.toString())){
				v2.setBackgroundResource(R.drawable.ok);
			}
			if(v3.getText().toString().contains(right_ans.toString())){
				v3.setBackgroundResource(R.drawable.ok);
			}
			if(v4.getText().toString().contains(right_ans)){
				v4.setBackgroundResource(R.drawable.ok);
			}
		}
		
		Handler handler = new Handler(); 
	    handler.postDelayed(new Runnable() { 
	         public void run() { 
	        	 load_asoc_word(); 
	         } 
	    }, SLEEP); 
		
			
        
		
	}
	public void show_dialog(){
		TextView last, now;
		Button menu, repeat;
		dialog = new Dialog(Asociative.this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog);
        dialog.setCancelable(false);
        dialog.getWindow().setLayout(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
        last = (TextView)dialog.findViewById(R.id.tv_dialog_last);
        now = (TextView)dialog.findViewById(R.id.tv_dialog_now);
        menu = (Button)dialog.findViewById(R.id.btn_dialog_menu);
        repeat = (Button)dialog.findViewById(R.id.btn_dialog_repeat);
        
        last.setText("" + ShPrefs.LoadIntPrefs(ShPrefs.ASOC_REZ, 0, Asociative.this));
        now.setText("" + count_right_answer);
        ShPrefs.SaveIntPrefs(ShPrefs.ASOC_REZ, count_right_answer, Asociative.this);
        
        menu.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent mIntent = new Intent(Asociative.this, MainMenu.class);
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
	public void restart_activity(){			//перезапускає активність
		Intent intent = new Intent(Asociative.this, Asociative.class);
		startActivity(intent);
		finish();
	}
	
	@Override
	protected void onStop() {
		ShPrefs.SaveIntPrefs(ShPrefs.ASOC_INDEX, index, Asociative.this);
		super.onStop();
	}
}
