package com.freebz.sevenknight;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.freebz.sevenknight.model.CardListDatabaseHelper;
import com.freebz.sevenknight.model.Constance;
import com.freebz.sevenknight.model.Hero;
import com.freebz.sevenknight.model.HeroList;
import com.mocoplex.adlib.AdlibActivity;

public class NewCardActivity extends AdlibActivity {
	
	private ImageView imgCard;
	private TextView name;
	private Button btnGetHero;
	private Button btnOk;
	private Boolean advancedMode;
	
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
		advancedMode = intent.getBooleanExtra("ADVANCED_MODE", false);
		
		databaseHelper = new CardListDatabaseHelper(this);
		
		imgCard = (ImageView) findViewById(R.id.img_card);
		name = (TextView) findViewById(R.id.name);
		btnGetHero = (Button) findViewById(R.id.btn_new_card);
		btnGetHero.setOnClickListener(mClickListener);
		
		btnOk = (Button) findViewById(R.id.btn_ok);
		btnOk.setOnClickListener(mClickListener);
		
		ready();
	}
	
	Button.OnClickListener mClickListener = new View.OnClickListener() {
		public void onClick(View view) {
			switch(view.getId()) {
			case R.id.btn_new_card:
				getNewCard();
				break;
			case R.id.btn_ok:
				ready();
			}
		}
	};
	
	private void ready() {
		
		long count = databaseHelper.getCardCount();
		if (count >= 99) {
			Toast.makeText(this, "더이상 영웅을 보유할 수 없습니다.", Toast.LENGTH_SHORT).show();
			finish();
		}
		
		btnOk.setVisibility(View.INVISIBLE);
		btnGetHero.setVisibility(View.VISIBLE);
		
		if (advancedMode) {
			imgCard.setImageResource(R.drawable.get_card2);
		} else {
			imgCard.setImageResource(R.drawable.get_card1);
		}
		name.setText("");
	}
	
	private void getNewCard() {
		
		btnOk.setVisibility(View.VISIBLE);
		btnGetHero.setVisibility(View.INVISIBLE);
		
		Hero hero;
		
		if (advancedMode) {
			hero = HeroList.getInstance().getAdvanced();
		} else {
			hero = HeroList.getInstance().getNormal();
		}
		
		int star = hero.getStar();
		
//		int resId = this.getResources().getIdentifier("_" + hero.getId(), "drawable", "com.freebz.sevenknight");
//		imgCard.setImageResource(resId);
		
		// 이미지가 없어 임시로 등급 표시
		int resId = this.getResources().getIdentifier("star_" + star, "drawable", "com.freebz.sevenknight");
		imgCard.setImageResource(resId);
		
		name.setText(hero.getName());
		name.setTextColor(Constance.nameColors[star - 1]);
		
		databaseHelper.insertCard(hero);
    }
}
