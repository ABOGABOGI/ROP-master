package rich.on.pay.utils;

/**
 * Created by Winardi on 11/22/2017.
 */

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;

public class NumberTextWatcher implements TextWatcher {

    //    private DecimalFormat df;
    private DecimalFormat dfnd;
//    private boolean hasFractionalPart;

    private EditText et;

    public NumberTextWatcher(EditText et) {
//        df = new DecimalFormat("#,###.##");
//        df.setDecimalSeparatorAlwaysShown(true);
        dfnd = new DecimalFormat("#,###");
        DecimalFormatSymbols symbols = dfnd.getDecimalFormatSymbols();
        symbols.setGroupingSeparator(',');
        symbols.setDecimalSeparator('.');
        dfnd.setDecimalFormatSymbols(symbols);
        this.et = et;
//        hasFractionalPart = false;
    }

    @SuppressWarnings("unused")
    private static final String TAG = "NumberTextWatcher";

    @Override
    public void afterTextChanged(Editable s) {
        et.removeTextChangedListener(this);

        try {
            int inilen, endlen;
            inilen = et.getText().length();

            String v = s.toString().replace(String.valueOf(dfnd.getDecimalFormatSymbols().getGroupingSeparator()), "");
            Number n = dfnd.parse(v);
            int cp = et.getSelectionStart();
//            if (hasFractionalPart) {
//                et.setText(df.format(n));
//            } else {
            et.setText(dfnd.format(n));
//            }
            endlen = et.getText().length();
            int sel = (cp + (endlen - inilen));
            if (sel > 0 && sel <= et.getText().length()) {
                et.setSelection(sel);
            } else {
                // place cursor at the end?
                et.setSelection(et.getText().length() - 1);
            }
        } catch (NumberFormatException nfe) {
            // do nothing?
        } catch (ParseException e) {
            // do nothing?
        }

        et.addTextChangedListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
//        if (s.toString().contains(String.valueOf(df.getDecimalFormatSymbols().getDecimalSeparator()))) {
//            hasFractionalPart = true;
//        } else {
//            hasFractionalPart = false;
//        }
    }

}