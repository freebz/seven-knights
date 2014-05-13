package com.freebz.sevenknight;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.mocoplex.adlib.AdlibActivity;
import com.mocoplex.adlib.AdlibConfig;

public class MainActivity extends AdlibActivity {
	
//	private Button btnGetHero;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		initAds();
		this.setAdsContainer(R.id.ads);
		
		initComponent();
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.main, menu);
//		return true;
//	}

	protected void initAds() {
    	AdlibConfig.getInstance().bindPlatform("ADAM", "adlib.ads.SubAdlibAdViewAdam");
    	AdlibConfig.getInstance().bindPlatform("ADMOB", "adlib.ads.SubAdlibAdViewAdmob");
    	AdlibConfig.getInstance().bindPlatform("CAULY", "adlib.ads.SubAdlibAdViewCauly");
    	AdlibConfig.getInstance().bindPlatform("NAVER", "adlib.ads.SubAdlibAdViewNaverAdPost");
    	AdlibConfig.getInstance().bindPlatform("ADHUB", "adlib.ads.SubAdlibAdViewAdHub");
    	AdlibConfig.getInstance().setAdlibKey("53700bf0e4b08bb0794d3fee");
    }
	
	protected void initComponent() {
		findViewById(R.id.btn_new_normal_card).setOnClickListener(mClickListener);
		findViewById(R.id.btn_new_advanecd_card).setOnClickListener(mClickListener);
		findViewById(R.id.btn_manage_card).setOnClickListener(mClickListener);
	}
	
	OnClickListener mClickListener = new OnClickListener() {
		public void onClick(View view) {
			Intent intent;
			switch(view.getId()) {
			case R.id.btn_new_normal_card:
				intent = new Intent(view.getContext(), NewCardActivity.class);
				intent.putExtra("ADVANCED_MODE", false);
				startActivity(intent);
				break;
			case R.id.btn_new_advanecd_card:
				intent = new Intent(view.getContext(), NewCardActivity.class);
				intent.putExtra("ADVANCED_MODE", true);
				startActivity(intent);
				break;
			case R.id.btn_manage_card:
				startActivity(new Intent(view.getContext(), CardActivity.class));
				break;
			}
		}
	};
}
