package com.freebz.sevenknight;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.freebz.sevenknight.model.CardListDatabaseHelper;
import com.freebz.sevenknight.model.Constance;
import com.freebz.sevenknight.model.Hero;
import com.freebz.sevenknight.model.HeroList;
import com.mocoplex.adlib.AdlibActivity;

public class CombinationNewCardActivity extends AdlibActivity {
	
	private ImageView imgCard;
	private TextView name;
	private Button btnGetHero;
	private Button btnOk;
	private int star;
	
	private CardListDatabaseHelper databaseHelper;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_card);
		
		this.setAdsContainer(R.id.ads);
		
		initComponent();
	}
	
	private void initComponent() {
		
		Intent intent = getIntent();
		star = intent.getIntExtra("star", 0);
		
		databaseHelper = new CardListDatabaseHelper(this);
		
		imgCard = (ImageView) findViewById(R.id.img_card);
		name = (TextView) findViewById(R.id.name);
		btnGetHero = (Button) findViewById(R.id.btn_new_card);
		btnGetHero.setOnClickListener(mClickListener);
		
		btnOk = (Button) findViewById(R.id.btn_ok);
		btnOk.setOnClickListener(mClickListener);
		
		getNewCard();
	}
	
	Button.OnClickListener mClickListener = new View.OnClickListener() {
		public void onClick(View view) {
			switch(view.getId()) {
			case R.id.btn_new_card:
				getNewCard();
				break;
			case R.id.btn_ok:
				finish();
			}
		}
	};
	
	private void getNewCard() {
		
		btnOk.setVisibility(View.VISIBLE);
		btnGetHero.setVisibility(View.INVISIBLE);
		
		Hero hero = HeroList.getInstance().getRandom(star);
		
//		int resId = this.getResources().getIdentifier("_" + hero.getId(), "drawable", "com.freebz.sevenknight");
//		imgCard.setImageResource(resId);
		
		// 이미지가 없어 임시로 등급 표시
		int resId = this.getResources().getIdentifier("star_" + hero.getStar(), "drawable", "com.freebz.sevenknight");
		imgCard.setImageResource(resId);
		
		name.setText(hero.getName());
		name.setTextColor(Constance.nameColors[hero.getStar() - 1]);
		
		databaseHelper.insertCard(hero);
    }
}
