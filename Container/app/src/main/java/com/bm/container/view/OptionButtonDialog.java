package com.bm.container.view;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.bm.container.R;


/**
 * 下方抽屉dialog
 *
 * @author nfmomo
 */
public class OptionButtonDialog extends Dialog {

    public OptionButtonDialog(Context context) {
        super(context);
    }

    public OptionButtonDialog(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder {
        private Context context;
        private String name1;
        private String name2;
        private String name3;
        private OnClickListener level1Listener;
        private OnClickListener level2Listener;
        private OnClickListener level3Listener;

        private TextView level1;
        private TextView level2;
        private TextView level3;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setLevel1(String name, OnClickListener listener) {
            this.name1 = name;
            this.level1Listener = listener;
            return this;
        }

        public Builder setLevel2(String name, OnClickListener listener) {
            this.name2 = name;
            this.level2Listener = listener;
            return this;
        }

        public Builder setLevel3(String name, OnClickListener listener) {
            this.name3 = name;
            this.level3Listener = listener;
            return this;
        }

        public OptionButtonDialog create() {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final OptionButtonDialog dialog = new OptionButtonDialog(context, R.style.OptionButtonDialog);
            dialog.getWindow().getDecorView().setPadding(0, 0, 0, 0);
            View layout = inflater.inflate(R.layout.dialog_option_button, null);

            level1 = (TextView) layout.findViewById(R.id.tv_option_level1);
            level1.setText(name1);
            level1.setOnClickListener(v -> level1Listener.onClick(dialog, 1));
            level2 = (TextView) layout.findViewById(R.id.tv_option_level2);
            level2.setText(name2);
            level2.setOnClickListener(v -> level2Listener.onClick(dialog, 2));
            level3 = (TextView) layout.findViewById(R.id.tv_option_level3);
            level3.setText(name3);
            level3.setOnClickListener(v -> level3Listener.onClick(dialog, 3));

            Window win = dialog.getWindow();
            win.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams lp = win.getAttributes();
            lp.width = WindowManager.LayoutParams.FILL_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            win.setAttributes(lp);

            dialog.setContentView(layout);
            return dialog;
        }

    }
}
