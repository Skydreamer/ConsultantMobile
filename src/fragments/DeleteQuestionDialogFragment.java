package fragments;

import ru.romangolovan.consultantmobile.R;
import utils.Constants;
import activities.MainActivity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

public class DeleteQuestionDialogFragment extends android.support.v4.app.DialogFragment {
	String question;
	
	public static DeleteQuestionDialogFragment newInstance(String question)
	{
		DeleteQuestionDialogFragment dialog = new DeleteQuestionDialogFragment();
	
		Bundle args = new Bundle();
		args.putString("question", question);
		dialog.setArguments(args);
		
		return dialog;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		question = getArguments().getString("question");
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setIcon(R.drawable.ic_launcher);
		builder.setTitle(getString(R.string.dialogDeleteQuestionTitle));
		builder.setPositiveButton(R.string.positive, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				((MainActivity) getActivity()).onDialogPositiveClick(Constants.DIALOG_DELETE_QUESTION);
			}
		});
		
		builder.setNegativeButton(R.string.negative, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				((MainActivity) getActivity()).onDialogNegativeClick(Constants.DIALOG_DELETE_QUESTION);
			}
		});
		
		builder.setMessage("Удалить вопрос \"" + question + "\"?");
		
		return builder.create();
	}

}
