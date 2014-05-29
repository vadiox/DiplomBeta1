package ua.study.english;

import java.util.Timer;
import java.util.TimerTask;

import ua.study.english.other.DbHelper;
import ua.study.english.other.ParseJSON;
import ua.study.english.other.ShPrefs;
import android.support.v4.app.Fragment;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.os.Build;

public class Splash extends Activity {

	Context context;
	int TIME_OUT = 3;	
	DbHelper dbhelper;
    Timer t = new Timer();
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        context = Splash.this;
        dbhelper = new DbHelper(context);
        
		ParseJSON.parse_word(dbhelper, context);
		ParseJSON.parse_asoc_card();

		 t.scheduleAtFixedRate(new TimerTask() {
				@Override
				public void run() {
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							t.cancel();
							go_to_menu(); //переходимо до екрану меню
						}
					});
				}
	        }, TIME_OUT*1000, TIME_OUT*1000);
    }

    public void go_to_menu(){ 								//перходимо до меню
    	Intent intent = new Intent(context, MainMenu.class);
		startActivity(intent);
		finish();
    }

    

}
