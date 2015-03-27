package com.example.amigos;

//import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.graphics.drawable.AnimationDrawable;
import android.widget.ImageView;
public class MainActivity extends Activity {
	private Thread mSplashThread;    

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final MainActivity sPlashScreen = this;   
		/*ImageView gyroView = (ImageView) findViewById(R.id.SplashImageView);
		gyroView.setBackgroundResource(R.drawable.splash_animation);
		AnimationDrawable gyroAnimation = (AnimationDrawable) gyroView.getBackground();

		gyroAnimation.start();
*/
		mSplashThread =  new Thread(){
            @SuppressWarnings("deprecation")
			@Override
            public void run(){
                try {
                    synchronized(this){
                        // Wait given period of time or exit on touch
                        wait(5000);
                    }
                }
                catch(InterruptedException ex){                    
                }
                finally{
                finish();
                
                // Run next activity
                Intent intent = new Intent();
                intent.setClass(sPlashScreen, LoginActivity.class);
                startActivity(intent);
                //stop(); 
                }
            }
        };
        
        mSplashThread.start();
		
		
		
		
		/*Thread loadnext=new Thread(){
			public void run(){
				try{
					sleep(5000);
				}catch(Exception e){
					e.printStackTrace();
				}finally{
					Intent load=new Intent(MainActivity.this,LoginActivity.class);
					startActivity(load);
				}
			}
		};
		loadnext.start();
		*//*Button open=(Button)findViewById(R.id.button5);
		open.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent load=new Intent(MainActivity.this,LoginActivity.class);
				startActivity(load);
			}
		});*/
	}

	@Override
    public boolean onTouchEvent(MotionEvent evt)
    {
        if(evt.getAction() == MotionEvent.ACTION_DOWN)
        {
            synchronized(mSplashThread){
                mSplashThread.notifyAll();
            }
        }
        return true;
    }
	
}
