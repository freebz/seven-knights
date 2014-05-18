package com.freebz.sevenknight.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CardListDatabaseHelper {

	private static final int DATABASE_VERSION = 11;
	private static final String DATABASE_NAME = "sevenknight.db";
	
	private CardOpenHelper openHelper;
	private SQLiteDatabase database;
	
	public CardListDatabaseHelper(Context context) {
		openHelper = new CardOpenHelper(context);
		database = openHelper.getWritableDatabase();
	}
	
	public void insertCard(Hero hero) {
		ContentValues values = new ContentValues();
		values.put("HERO_ID", hero.getId());
		database.insert("CARD", null, values);
	}
	
	public Cursor getCardList() {
		return database.rawQuery(
				"SELECT A.*, B.NAME, B.STAR FROM CARD A "
				+ "INNER JOIN HERO B "
				+ "ON A.HERO_ID = B._id "
				, null);
	}
	
	public Cursor getCardListWithoutTarget(int id) {
		return database.rawQuery(
				"SELECT A.*, B.NAME, B.STAR FROM CARD A "
				+ "INNER JOIN HERO B "
				+ "ON A.HERO_ID = B._id "
				+ "WHERE A._id != " + id
				, null);
	}
	
	public Cursor getCardListForCombination(int id, int star) {
		return database.rawQuery(
				"SELECT A.*, B.NAME, B.STAR FROM CARD A "
				+ "INNER JOIN HERO B "
				+ "ON A.HERO_ID = B._id "
				+ "WHERE A._id != " + id
				+ " AND B.STAR = " + star
				+ " AND A.LEVEL = 5"
				, null);
	}
	
	public Card getCard(int id) {
		Cursor cursor = database.rawQuery(
				"SELECT * FROM CARD WHERE _id = " + id
				, null);
		
		cursor.moveToFirst();
		
		Card card = new Card();
		card.set_id(id);
		card.setHeroId(cursor.getInt(cursor.getColumnIndex("HERO_ID")));
		card.setLevel(cursor.getInt(cursor.getColumnIndex("LEVEL")));
		card.setBonus(cursor.getInt(cursor.getColumnIndex("BONUS_RATIO")));
		cursor.close();
		return card;
	}
	
	public int updateCard(Card card) {		
		ContentValues values = new ContentValues();
		values.put("HERO_ID", card.getHeroId());
		values.put("LEVEL", card.getLevel());
		values.put("BONUS_RATIO", card.getBonus());
		
		return database.update("CARD", values, "_id = ?",
				new String[]{ String.valueOf(card.get_id()) });
	}
	
	public int deleteCard(int id) {
		return database.delete("CARD", "_id = ?", new String[]{ String.valueOf(id) });
	}
	
	public int deleteCard(Card card) {
		return deleteCard(card.get_id());
	}
	
	public long getCardCount() {
		return getLongValue("SELECT COUNT(*) FROM CARD");
	}
	
	private long getLongValue(String sql) {
		Cursor cursor = database.rawQuery(sql, null);
		cursor.moveToFirst();
		long result = cursor.getLong(0);
		cursor.close();
		return result;
	}
	
	
	
	
	private class CardOpenHelper extends SQLiteOpenHelper {
		
		public CardOpenHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}
		
		@Override
		public void onCreate(SQLiteDatabase database) {
			onUpgrade(database, -1, 1);
		}
		
		@Override
		public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
			if (oldVersion < 10) {
				createTableCard(database);
				insertCard(database, HeroList.getInstance().get(22));		// 에반
				insertCard(database, HeroList.getInstance().get(25));		// 카린
			}
			
			createTableHero(database);
		}
		
		private void createTableHero(SQLiteDatabase database) {
			database.execSQL("DROP TABLE IF EXISTS HERO");
			database.execSQL(
					"CREATE TABLE HERO ( "
					+ "_id INTEGER PRIMARY KEY, "
					+ "NAME TEXT, "
					+ "GROUP_ID INTEGER, "
					+ "STAR INTEGER, "
					+ "RATIO INTEGER, "
					+ "NEXT_ID INTEGER, "
					+ "SEQ INTEGER)"
			);
			
			ContentValues values = new ContentValues();
			for (Hero hero : HeroList.getInstance().getHeroList()) {
				values.clear();
				values.put("_id", hero.getId());
				values.put("NAME", hero.getName());
				values.put("GROUP_ID", hero.getGroup());
				values.put("STAR", hero.getStar());
				values.put("RATIO", hero.getRatio());
				values.put("NEXT_ID", hero.getNextId());
				values.put("SEQ", hero.getSeq());
				database.insert("HERO", null, values);
			}
		}
		
		private void createTableCard(SQLiteDatabase database) {
			database.execSQL("DROP TABLE IF EXISTS CARD");
			database.execSQL(
					"CREATE TABLE CARD ( "
					+ "_id INTEGER PRIMARY KEY, "
					+ "HERO_ID INTEGER, "
					+ "LEVEL INTEGER, "
					+ "BONUS_RATIO INTEGER)"
			);
		}
		
		private void insertCard(SQLiteDatabase database, Hero hero) {
			ContentValues values = new ContentValues();
			values.put("HERO_ID", hero.getId());
			database.insert("CARD", null, values);
		}
	}
}
