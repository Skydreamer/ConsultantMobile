package data;

import items.AnswerItem;
import items.CategoryItem;
import items.Consultant;
import items.MessageItem;
import items.QuestionItem;

import java.util.ArrayList;

import utils.Constants;

import android.util.Log;
import enums.ApplicationState;
import enums.ConnectionState;


public class MasterData {
	protected static MasterData instance;
	
	public static ConnectionState connectionState;
	public static ApplicationState applicationState;
	
	public static ArrayList<CategoryItem> categories;
	public static ArrayList<MessageItem> messages;
	public static ArrayList<AnswerItem> answers;
	public static ArrayList<QuestionItem> questions;
	public static ArrayList<Consultant> consultants;
	
	public static ArrayList<QuestionItem> questionsQuery;
	public static ArrayList<QuestionItem> questionsUnsent;
	
	protected MasterData() {
		Log.d(Constants.TAG_CALL, "MasterData - MasterData()");
		categories = new ArrayList<CategoryItem>();
		messages = new ArrayList<MessageItem>();
		answers = new ArrayList<AnswerItem>();
		questions = new ArrayList<QuestionItem>();
		consultants = new ArrayList<Consultant>();
		
		questionsQuery = new ArrayList<QuestionItem>();
		questionsUnsent = new ArrayList<QuestionItem>();
		
		connectionState = ConnectionState.OFFLINE;
		applicationState = ApplicationState.PASSIVE;	
	}
	
	protected static MasterData getInstance()
	{
		if(instance == null)
			instance = new MasterData();
		return instance;
	}
}
