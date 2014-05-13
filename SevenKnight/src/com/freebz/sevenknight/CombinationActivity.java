package com.freebz.sevenknight;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.freebz.sevenknight.model.Card;
import com.freebz.sevenknight.model.CardAdapter;
import com.freebz.sevenknight.model.CardListDatabaseHelper;
import com.freebz.sevenknight.model.Hero;
import com.freebz.sevenknight.model.HeroList;
import com.mocoplex.adlib.AdlibActivity;

public class CombinationActivity extends AdlibActivity {
	
	private CardListDatabaseHelper databaseHelper;
	private CardAdapter cardAdapter;
	private ListView listView;
	private TextView target;
	private Card card;
	private Hero hero;
	
	private int selectedId;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_combination);
		
		this.setAdsContainer(R.id.ads);
		
		initComponent();
	}
	
	private void initComponent() {
		
		Intent intent = getIntent();
		int targetId = intent.getIntExtra("id", 0);
		
		databaseHelper = new CardListDatabaseHelper(this);
		
		card = databaseHelper.getCard(targetId);
		hero = HeroList.getInstance().get(card);
		
		listView = (ListView) findViewById(R.id.card_list);
		cardAdapter = new CardAdapter(this, databaseHelper.getCardListForCombination(targetId, hero.getStar()));
        listView.setAdapter(cardAdapter);
        
        target = (TextView) findViewById(R.id.target);
        target.setText(hero.getName());

        findViewById(R.id.btn_start_combination).setOnClickListener(mClickListener);
	}
	
	public void refresh() {
		selectedId = cardAdapter.getSelectedId();
	}
	
	public void startCombination() {
		if (selectedId == 0) {
			// 선택된 카드가 없는 경우
			showMessage("영웅을 선택해 주세요");
			return;
		}
		
		databaseHelper.deleteCard(card);
		databaseHelper.deleteCard(selectedId);
		
		Intent intent = new Intent(this, CombinationNewCardActivity.class);
		intent.putExtra("star", hero.getStar() + 1);
		startActivity(intent);
		finish();
	}
	
	private void showMessage(String message) {
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}
	
	OnClickListener mClickListener = new OnClickListener() {
		public void onClick(View view) {
			switch(view.getId()) {
			case R.id.btn_start_combination:
				startCombination();
				break;
			}
		}
	};
}
