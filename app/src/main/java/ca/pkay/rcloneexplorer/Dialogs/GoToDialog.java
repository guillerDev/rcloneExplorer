package ca.pkay.rcloneexplorer.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import ca.pkay.rcloneexplorer.R;

public class GoToDialog extends DialogFragment {

    public interface Callbacks {
        void onRootClicked(boolean isSetAsDefault);
        void onHomeClicked(boolean isSetAsDefault);
    }

    private final String SAVED_IS_DARK_THEME = "ca.pkay.rcexplorer.GoToDialog.IS_DARK_THEME";
    private Context context;
    private boolean isDarkTheme;
    private Callbacks listener;
    private CheckBox checkBox;
    private boolean isDefaultSet;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            isDarkTheme = savedInstanceState.getBoolean(SAVED_IS_DARK_THEME);
        }

        if (getParentFragment() != null) {
            listener = (Callbacks) getParentFragment();
        }

        AlertDialog.Builder builder;

        if (isDarkTheme) {
            builder = new AlertDialog.Builder(context, R.style.DarkDialogTheme);
        } else {
            builder = new AlertDialog.Builder(context);
        }
        LayoutInflater inflater = ((FragmentActivity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_go_to, null);
        builder.setTitle(R.string.dialog_go_to_title);
        checkBox = view.findViewById(R.id.checkbox);
        view.findViewById(R.id.linearLayout_root).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                listener.onRootClicked(isDefaultSet);
            }
        });
        view.findViewById(R.id.linearLayout_home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                listener.onHomeClicked(isDefaultSet);
            }
        });
        view.findViewById(R.id.linearLayout_checkbox).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBox.setChecked(!checkBox.isChecked());
            }
        });
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isDefaultSet = isChecked;
            }
        });
        builder.setView(view);
        return builder.create();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_IS_DARK_THEME, isDarkTheme);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public GoToDialog isDarkTheme(boolean isDarkTheme) {
        this.isDarkTheme = isDarkTheme;
        return this;
    }
}
