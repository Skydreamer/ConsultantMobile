package fragments;

import ru.romangolovan.consultantmobile.R;
import utils.Constants;
import activities.MainActivity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

public class AddQuestionDialogFragment extends android.support.v4.app.DialogFragment {
	String question;
	String category;
	
	public static AddQuestionDialogFragment newInstance(String question, String category)
	{
		AddQuestionDialogFragment dialog = new AddQuestionDialogFragment();
		
		Bundle args = new Bundle();
		args.putString("question", question);
		args.putString("category", category);
		dialog.setArguments(args);
		
		return dialog;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		question = getArguments().getString("question");
		category = getArguments().getString("category");
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setIcon(R.drawable.ic_launcher);
		builder.setTitle(getString(R.string.dialogAddQuestionTitle));
		builder.setPositiveButton(R.string.positive, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				((MainActivity) getActivity()).onDialogPositiveClick(Constants.DIALOG_ADD_QUESTION);
			}
		});
		
		builder.setNegativeButton(R.string.negative, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				((MainActivity) getActivity()).onDialogNegativeClick(Constants.DIALOG_ADD_QUESTION);
			}
		});
		
		builder.setMessage("Спросить \"" + question + "\" в категории \"" + category + "\"?");
		
		return builder.create();
	}
	
}
