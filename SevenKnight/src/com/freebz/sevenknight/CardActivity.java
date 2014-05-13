package com.freebz.sevenknight;

import android.content.Context;
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

public class CardActivity extends AdlibActivity {
	
	private static final int REINFORCE_ACTIVITY_REQUEST_CODE = 0;
	private static final int COMBINATION_ACTIVITY_REQUEST_CODE = 1;
	private static final int MAX_LEVEL = 5;

	private CardListDatabaseHelper databaseHelper;
	private CardAdapter cardAdapter;
	private ListView listView;
	private TextView cardCount;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_card);
		
		this.setAdsContainer(R.id.ads);
		
		initComponent();
	}
	
	private void initComponent() {
		
		databaseHelper = new CardListDatabaseHelper(this);
		
		listView = (ListView) findViewById(R.id.card_list);
		cardAdapter = new CardAdapter(this, databaseHelper.getCardList());
        listView.setAdapter(cardAdapter);
        
        cardCount = (TextView) findViewById(R.id.card_count);
        
        findViewById(R.id.btn_reinforce).setOnClickListener(mClickListener);
        findViewById(R.id.btn_combination).setOnClickListener(mClickListener);
		
        refreshCardCount();
	}
	
	private void refresh() {
		refreshCardCount();
		refreshList();
	}
	
	private void refreshCardCount() {
		long count = databaseHelper.getCardCount();
		cardCount.setText("보유 영웅 " + count + "/99");
	}
	
	private void refreshList() {
		cardAdapter.changeCursor(databaseHelper.getCardList());
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
			case REINFORCE_ACTIVITY_REQUEST_CODE:
			case COMBINATION_ACTIVITY_REQUEST_CODE:
				refresh();
				break;
		}
	}
	
	OnClickListener mClickListener = new OnClickListener() {
		
		private Context context;
		
		public void onClick(View view) {
			context = view.getContext();
			int selectedId = cardAdapter.getSelectedId();
			
			if (selectedId == 0) {
				// 선택된 카드가 없는 경우
				showMessage("영웅을 선택해 주세요");
				return;
			}
			
			Card card = databaseHelper.getCard(selectedId);
			Intent intent;
			
			switch(view.getId()) {
			
			// 강화
			case R.id.btn_reinforce:
				if (card.getLevel() >= MAX_LEVEL) {
					// 최대 레벨
					showMessage("선택한 영웅은 더이상 강화할 수 없습니다");
					return;
				}
				
				intent = new Intent(view.getContext(), ReinforceActivity.class);
				intent.putExtra("id", selectedId);
				startActivityForResult(intent, REINFORCE_ACTIVITY_REQUEST_CODE);
				break;

			// 합성
			case R.id.btn_combination:
				if (card.getLevel() != MAX_LEVEL) {
					showMessage("+5 강화 조건을 만족하지 않습니다");
					return;
				}
				Hero hero = HeroList.getInstance().get(card);
				if (hero.getStar() > 5) {
					// 6성이상 영웅
					showMessage("6성 영웅은 합성할 수 없습니다");
					return;
				}
				intent = new Intent(view.getContext(), CombinationActivity.class);
				intent.putExtra("id", selectedId);
				startActivityForResult(intent, COMBINATION_ACTIVITY_REQUEST_CODE);
				break;
			}
		}
		
		private void showMessage(String message) {
			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
		}
	};
}
