package juidesai.edu.csulb.com.airtouch;

import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;

import com.github.nisrulz.sensey.FlipDetector;
import com.github.nisrulz.sensey.LightDetector;
import com.github.nisrulz.sensey.Sensey;
import com.github.nisrulz.sensey.TouchTypeDetector;
import com.github.nisrulz.sensey.WaveDetector;
import com.github.nisrulz.sensey.WristTwistDetector;

public class MainActivity extends AppCompatActivity {

    Spinner handwave,flip,wrist,dark,threefinger;
    Switch callflash;

    SharedPreferences sf;
    String[] features = new String[]{"Action","Wifi","Silent","General","Vibrate","Flashlight","Mobile Data","Bluetooth"};
    Features myfeatures;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Init
        handwave = (Spinner) findViewById(R.id.handwave);
        flip = (Spinner) findViewById(R.id.flip);
        wrist = (Spinner) findViewById(R.id.wrist);
        dark = (Spinner) findViewById(R.id.dark);
        threefinger = (Spinner) findViewById(R.id.threefinger);
        callflash = (Switch) findViewById(R.id.callflash);
        sf = getSharedPreferences("AirTouch",MODE_PRIVATE);
        myfeatures = new Features(getApplicationContext());
        Sensey.getInstance().init(getApplicationContext());

        //Data Load
        ArrayAdapter<String> adp = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,features);
        handwave.setAdapter(adp);
        flip.setAdapter(adp);
        wrist.setAdapter(adp);
        dark.setAdapter(adp);
        threefinger.setAdapter(adp);

        if(sf.getBoolean("callflash",false)){
            callflash.setChecked(true);
        }
        else
        {
            callflash.setChecked(false);
        }

        handwave.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                myfeatures.setfeature("handwave",i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        WaveDetector.WaveListener waveListener=new WaveDetector.WaveListener() {
            @Override public void onWave() {
                // Wave of hand gesture detected
                myfeatures.usefeature("handwave");
            }
        };
        Sensey.getInstance().startWaveDetection(waveListener);


        flip.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                myfeatures.setfeature("flip",i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        FlipDetector.FlipListener flipListener=new FlipDetector.FlipListener() {
            @Override public void onFaceUp() {
                // Device Facing up

            }

            @Override public void onFaceDown() {
                // Device Facing down
                myfeatures.usefeature("flip");
            }
        };

        Sensey.getInstance().startFlipDetection(flipListener);

        wrist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                myfeatures.setfeature("wrist",i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        WristTwistDetector.WristTwistListener wristTwistListener=new WristTwistDetector.WristTwistListener() {
            @Override public void onWristTwist() {
                // Wrist Twist gesture detected, do something
                myfeatures.usefeature("wrist");
            }
        };

        Sensey.getInstance().startWristTwistDetection(wristTwistListener);

        dark.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                myfeatures.setfeature("dark",i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        LightDetector.LightListener lightListener=new LightDetector.LightListener() {
            @Override public void onDark() {
                // Dark
                myfeatures.usefeature("dark");
            }

            @Override public void onLight() {
                // Not Dark
            }
        };

        Sensey.getInstance().startLightDetection(lightListener);

        threefinger.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                myfeatures.setfeature("threefinger",i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        TouchTypeDetector.TouchTypListener touchTypListener=new TouchTypeDetector.TouchTypListener() {
            @Override public void onTwoFingerSingleTap() {
                // Two fingers single tap
            }

            @Override public void onThreeFingerSingleTap() {
                // Three fingers single tap
                myfeatures.usefeature("threefinger");

            }

            @Override public void onDoubleTap() {
                // Double tap
            }

            @Override public void onScroll(int scrollDirection) {
                switch (scrollDirection) {
                    case TouchTypeDetector.SCROLL_DIR_UP:
                        // Scrolling Up
                        break;
                    case TouchTypeDetector.SCROLL_DIR_DOWN:
                        // Scrolling Down
                        break;
                    case TouchTypeDetector.SCROLL_DIR_LEFT:
                        // Scrolling Left
                        break;
                    case TouchTypeDetector.SCROLL_DIR_RIGHT:
                        // Scrolling Right
                        break;
                    default:
                        // Do nothing
                        break;
                }
            }

            @Override public void onSingleTap() {
                // Single tap
            }

            @Override public void onSwipe(int swipeDirection) {
                switch (swipeDirection) {
                    case TouchTypeDetector.SWIPE_DIR_UP:
                        // Swipe Up
                        break;
                    case TouchTypeDetector.SWIPE_DIR_DOWN:
                        // Swipe Down
                        break;
                    case TouchTypeDetector.SWIPE_DIR_LEFT:
                        // Swipe Left
                        break;
                    case TouchTypeDetector.SWIPE_DIR_RIGHT:
                        // Swipe Right
                        break;
                    default:
                        //do nothing
                        break;
                }
            }

            @Override public void onLongPress() {
                // Long press
            }
        };

        Sensey.getInstance().startTouchTypeDetection(getApplicationContext(), touchTypListener);

        callflash.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                myfeatures.setflashfeature("callflash",b);
            }
        });




    }
}
