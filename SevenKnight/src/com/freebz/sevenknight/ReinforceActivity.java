package com.freebz.sevenknight;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.freebz.sevenknight.model.Card;
import com.freebz.sevenknight.model.CardAdapter;
import com.freebz.sevenknight.model.CardListDatabaseHelper;
import com.freebz.sevenknight.model.Constance;
import com.freebz.sevenknight.model.Hero;
import com.freebz.sevenknight.model.HeroList;
import com.mocoplex.adlib.AdlibActivity;

public class ReinforceActivity extends AdlibActivity {
	
//	private static String TAG = ReinforceActivity.class.getSimpleName();

	private CardListDatabaseHelper databaseHelper;
	private CardAdapter cardAdapter;
	private ListView listView;
	private TextView target;
	private TextView info;
	private Card card;
	private Hero hero;
	
	private int[] ratio = {100, 50, 25, 10, 1, 0};
	private int[] bonus = {12, 6, 3, 1, 0};
	private int base;
	private int bonusRatio;
	private int selectedId;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reinforce);
		
		this.setAdsContainer(R.id.ads);
		
		initComponent();
	}
	
	private void initComponent() {
		
		Intent intent = getIntent();
		int targetId = intent.getIntExtra("id", 0);
		
		databaseHelper = new CardListDatabaseHelper(this);
		
		listView = (ListView) findViewById(R.id.card_list);
		cardAdapter = new CardAdapter(this, databaseHelper.getCardListWithoutTarget(targetId));
        listView.setAdapter(cardAdapter);
        
//        ImageView imgStar = (ImageView) findViewById(R.id.img_star);
//        TextView level = (TextView) findViewById(R.id.level);
        
        target = (TextView) findViewById(R.id.target);
        info = (TextView) findViewById(R.id.info);
        
        card = databaseHelper.getCard(targetId);
        hero = HeroList.getInstance().get(card);
        
//        int star = hero.getStar();
//		int resId = getResources().getIdentifier("star_" + star, "drawable", "com.freebz.sevenknight");
//		imgStar.setImageResource(resId);
		
        target.setText(hero.getName());
        
//        target.setTextColor(Constance.nameColors[star - 1]);
        
//        if (card.getLevel() > 0) {
//			level.setText("+" + card.getLevel());
//		}
//		else {
//			level.setText("");
//		}

        findViewById(R.id.btn_start_reinforce).setOnClickListener(mClickListener);
        
        refreshInfo();
	}
	
	public void refreshInfo() {
		
		base = 0;
		selectedId = cardAdapter.getSelectedId();
		if (selectedId != 0) {
			int diff = diff(selectedId);
			base = ratio[diff];
			bonusRatio = bonus[diff];
		}
		
		info.setText("성공률 " + base + "% + 보너스 " + card.getBonus() + "%");
	}
	
	private int diff(int selectedId) {
		Card stuff = databaseHelper.getCard(selectedId);
		Hero material = HeroList.getInstance().get(stuff);
		
		int diff = hero.getStar() - material.getStar();
		if (diff < 0) {
			diff = 0;
		}
		
		return diff;
	}
	
	private void startReinforce() {
		if (selectedId == 0) {
			// 선택된 카드가 없는 경우
			showMessage("영웅을 선택해 주세요");
			return;
		}
		
		int ratio = base + card.getBonus();
		int random = (int) (Math.random() * 100);
		if (ratio > random) {
			// 강화성공
			showMessage("강화성공");
			card.levelUp();
		} else {
			// 강화실패
			showMessage("강화실패");
			card.addBonus(bonusRatio);
		}
		
		databaseHelper.updateCard(card);
		
		// 재료카드 삭제
		databaseHelper.deleteCard(selectedId);
		finish();
	}
	
	private void showMessage(String message) {
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}
	
	OnClickListener mClickListener = new OnClickListener() {
		public void onClick(View view) {
			switch(view.getId()) {
			case R.id.btn_start_reinforce:
				startReinforce();
				break;
			}
		}
	};
}


