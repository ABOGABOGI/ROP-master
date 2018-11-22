package com.richonpay.activity.payment_product;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.richonpay.R;
import com.richonpay.activity.SuccessTransactionActivity;
import com.richonpay.adapter.StringAdapter;
import com.richonpay.api.API;
import com.richonpay.api.APICallback;
import com.richonpay.api.BadRequest;
import com.richonpay.base.ToolbarActivity;
import com.richonpay.model.APIResponse;
import com.richonpay.model.ExploreCategory;
import com.richonpay.model.PayStoreRequest;
import com.richonpay.model.PaymentProduct;
import com.richonpay.model.PaymentProductBody;
import com.richonpay.utils.Extension;
import com.richonpay.utils.InsertPinDialog;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MultipartBody;

public class GameVoucherPhonePrepaidActivity extends ToolbarActivity {

    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvBalance)
    TextView tvBalance;
    @BindView(R.id.etPhone)
    TextInputEditText etPhone;
    @BindView(R.id.ivCarrier)
    ImageView ivCarrier;
    @BindView(R.id.btnContact)
    ImageButton btnContact;
    @BindView(R.id.etAmount)
    TextInputEditText etAmount;
    @BindView(R.id.btnDropdown)
    ImageButton btnDropdown;
    @BindView(R.id.tvTotalPrice)
    TextView tvTotalPrice;
    @BindView(R.id.llBuyBalance)
    LinearLayout llBuyBalance;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;
    @BindView(R.id.tvTypeLabel)
    TextView tvTypeLabel;
    @BindView(R.id.flPhoneNumber)
    FrameLayout flPhoneNumber;
    @BindView(R.id.llGame)
    LinearLayout llGame;
    @BindView(R.id.tvVoucherName)
    TextView tvVoucherName;

    private List<ExploreCategory> tempCategories = new ArrayList<>();
    private static final int CONTACT_PICKER_RESULT = 1001;

    private List<String> phoneNumber = new ArrayList<>();
    private ExploreCategory selectedCategory = null;
    private PaymentProduct selectedProduct = null;
    private String gameVoucherName = "";
    private boolean isGame = false;
    private String productId;
    private int categoryId;
    private Timer timer;

    private InsertPinDialog enterPinDialog;

    @Override
    protected int getContentViewResource() {
        return R.layout.activity_topup_phone_balance;
    }

    @Override
    protected void onViewCreated() {
        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            isGame = extra.getBoolean("IS_GAME", false);
            gameVoucherName = extra.getString("VOUCHER_NAME", "");
            productId = String.valueOf(extra.getInt("PRODUCT_ID"));
            categoryId = extra.getInt("CATEGORY_ID", 1);
        }

        tvTitle.setText(R.string.pulsa);
        llBuyBalance.setVisibility(View.GONE);
        btnContact.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                int calculatedSize = btnContact.getMeasuredHeight();
                btnContact.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                ivCarrier.getLayoutParams().height = calculatedSize;
                ivCarrier.getLayoutParams().width = calculatedSize;
                ivCarrier.requestFocus();
            }
        });

        if (API.isLoggedIn()) {
            tvBalance.setText(Extension.numberPriceFormat(API.currentUser().getWallets().get(0).getBalance()));
        }

        if (isGame) {
            flPhoneNumber.setVisibility(View.GONE);
            tvTypeLabel.setText(R.string.voucher_type);
            llBuyBalance.setVisibility(View.VISIBLE);
            llGame.setVisibility(View.VISIBLE);
            tvVoucherName.setText(gameVoucherName);
            tvTitle.setText(R.string.game_voucher);
        }
        getPaymentList();
    }

    @OnClick(R.id.btnClear)
    void clearText() {
        etPhone.setText("");
    }

    @OnClick({R.id.btnDropdown, R.id.etAmount, R.id.inputDropdown, R.id.llDropdown})
    void showSelection() {
        try {
            Intent intent = new Intent(this, PaymentProductSelectionActivity.class);
            intent.putExtra("CATEGORY", categoryId);
            intent.putExtra("PRODUCT_LIST", Parcels.wrap(selectedCategory));
            startActivityForResult(intent, PaymentProductSelectionActivity.PRODUCT_RESULT);
        } catch (Exception exception) {
            Log.e("ERROR", "SHOW PLANS " + exception);
        }
    }

    @OnClick(R.id.btnContact)
    void selectContact() {
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, CONTACT_PICKER_RESULT);
    }

    @OnClick(R.id.btnSubmit)
    void submitRequest() {
        String phoneInput = etPhone.getText().toString();
        if (!isGame) {
            if (phoneInput.length() < (phoneInput.startsWith("+62") ? 13 : 11)) {
                Toast.makeText(this, R.string.please_insert_valid_phone, Toast.LENGTH_SHORT).show();
                return;
            }
        }

        final PayStoreRequest paymentRequest = new PayStoreRequest();
        paymentRequest.setAmount(selectedProduct.getPrice());
        paymentRequest.setWalletType(0);
        paymentRequest.setNote("");

        enterPinDialog = new InsertPinDialog(this, R.style.darkPopupAnimation) {
            @Override
            protected void submitPassword() {
                paymentRequest.setPin(enterPinDialog.getPassword());
                requestPayment(paymentRequest);
            }

            @Override
            protected void forgotPassword() {
                requestForgotPassword();
            }
        };

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                enterPinDialog.show();
            }
        });
    }

    private void onMultiplePhone(String name) {
        try {
            final Dialog dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_multiple_phone_number);

            TextView tvName = dialog.findViewById(R.id.tvName);
            tvName.setText(String.valueOf(name));

            Button btnCancel = dialog.findViewById(R.id.btnCancel);
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            final RecyclerView listString = dialog.findViewById(R.id.recyclerView);
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(dialog.getWindow().getAttributes());
            lp.width = LinearLayout.LayoutParams.MATCH_PARENT;
            lp.height = LinearLayout.LayoutParams.WRAP_CONTENT;
            listString.setHasFixedSize(true);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            listString.setLayoutManager(layoutManager);

            StringAdapter mAdapter = new StringAdapter(new StringAdapter.OnItemClickListener() {
                @Override
                public void onClick(View view, int position) {
                    etPhone.setText(phoneNumber.get(position));
                    dialog.dismiss();
                }
            });
            listString.setAdapter(mAdapter);
            mAdapter.setItems(phoneNumber);
            dialog.show();
            dialog.getWindow().setAttributes(lp);
        } catch (Exception exception) {
            Log.e("ERROR", "SHOW PHONE NUMBERS " + exception);
        }
    }

    private void filterInput(boolean containsPlus, String input) {
        try {
            if (input.length() >= (containsPlus ? 6 : 4)) {
                if (!containsPlus) {
                    input = Extension.validatePhoneNumber(input);
                }

                for (int i = 0; i < tempCategories.size(); i++) {
                    if (tempCategories.get(i).getData().contains(input.substring(0, 6))) {
                        selectedCategory = tempCategories.get(i);
                        break;
                    } else {
                        selectedCategory = null;
                    }
                }
            } else {
                selectedCategory = null;
            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (selectedCategory != null) {
                        if (selectedCategory.getPaymentProducts().size() > 0) {
                            Extension.setImage(GameVoucherPhonePrepaidActivity.this, ivCarrier, selectedCategory.getPictureUrl());
                            llBuyBalance.setVisibility(View.VISIBLE);
                            selectedProduct = selectedCategory.getPaymentProducts().get(0);
                            etAmount.setText(selectedProduct.getName());
                            tvTotalPrice.setText(Extension.priceFormat(selectedProduct.getPrice()));
                        }
                    } else {
                        Extension.setImage(GameVoucherPhonePrepaidActivity.this, ivCarrier, 0);
                        llBuyBalance.setVisibility(View.GONE);
                    }
                }
            });
        } catch (Exception exception) {
            Log.e("filterInput", "" + exception);
        }
    }

    private void getPaymentList() {
        if (isGame) {
            API.service().getPaymentProductByCategory(categoryId, Integer.parseInt(productId)).enqueue(new APICallback<APIResponse>(this) {
                @Override
                protected void onSuccess(APIResponse response) {
                    if (response.getData().getPaymentProducts() != null) {

                        tempCategories = new ArrayList<>();

                        ExploreCategory category = new ExploreCategory();
                        category.setPaymentProducts(response.getData().getPaymentProducts());
                        category.setCategory(gameVoucherName);
                        tempCategories.add(category);
                        selectedCategory = tempCategories.get(0);
                        if (selectedCategory != null) {
                            if (selectedCategory.getPaymentProducts().size() > 0) {
                                selectedProduct = selectedCategory.getPaymentProducts().get(0);
                                etAmount.setText(selectedProduct.getName());
                                tvTotalPrice.setText(Extension.priceFormat(selectedProduct.getPrice()));
                            }
                        } else {
                            Extension.setImage(GameVoucherPhonePrepaidActivity.this, ivCarrier, 0);
                            llBuyBalance.setVisibility(View.GONE);
                        }
                    }
                }

                @Override
                protected void onError(BadRequest error) {
                    Extension.dismissLoading();
                    Toast.makeText(GameVoucherPhonePrepaidActivity.this, error.errorDetails, Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            API.service().getPaymentProductByType(categoryId).enqueue(new APICallback<APIResponse>(this) {
                @Override
                protected void onSuccess(APIResponse response) {
                    if (response.getData().getPaymentProducts() != null) {

                        tempCategories = new ArrayList<>();

                        // GROUP PAYMENT BY CATEGORY
                        for (int i = 0; i < response.getData().getPaymentProducts().size(); i++) {
                            for (int j = 0; j < response.getData().getCategories().size(); j++) {
                                if (response.getData().getPaymentProducts().get(i).getCategoryID() == response.getData().getCategories().get(j).getId()) {
                                    response.getData().getCategories().get(j).getPaymentProducts().add(response.getData().getPaymentProducts().get(i));
                                }
                            }
                        }

                        tempCategories = response.getData().getCategories();

                        etPhone.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                if (timer != null) {
                                    timer.cancel();
                                }
                            }

                            @Override
                            public void afterTextChanged(final Editable editable) {
                                timer = new Timer();
                                timer.schedule(new TimerTask() {
                                    @Override
                                    public void run() {
                                        if (editable.toString().length() > 0) {
                                            filterInput(editable.toString().startsWith("+62"), editable.toString());
                                        }
                                    }
                                }, 600);
                            }
                        });

                        etPhone.setText(String.valueOf(API.currentUser().getPhoneNumber()));
                    }
                }

                @Override
                protected void onError(BadRequest error) {
                    Extension.dismissLoading();
                    Toast.makeText(GameVoucherPhonePrepaidActivity.this, error.errorDetails, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (reqCode) {
                case CONTACT_PICKER_RESULT:
                    phoneNumber = new ArrayList<>();
                    Cursor cursor = null;
                    String name = "";
                    try {
                        Uri result = data.getData();
                        if (result != null) {
                            String id = result.getLastPathSegment();
                            cursor = this.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?", new String[]{id}, null);

                            if (cursor != null) {
                                int phoneIdx = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA);
                                int nameIdx = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                                if (cursor.moveToFirst()) {
                                    while (!cursor.isAfterLast()) {
                                        name = cursor.getString(nameIdx);

                                        if (!phoneNumber.contains(cursor.getString(phoneIdx).replace("-", "").replace(" ", ""))) {
                                            phoneNumber.add(cursor.getString(phoneIdx).replace("-", "").replace(" ", ""));
                                        }
                                        cursor.moveToNext();
                                    }
                                }
                            }
                        }
                    } catch (Exception exception) {
                        Log.e("SELECT CONTACT", "" + exception);
                    } finally {
                        if (cursor != null) {
                            cursor.close();
                        }
                        if (phoneNumber.size() > 1) {
                            onMultiplePhone(name);
                        } else {
                            etPhone.setText(phoneNumber.get(0));
                        }
                    }
                    break;
            }
        } else if (resultCode == PaymentProductSelectionActivity.PRODUCT_RESULT) {
            int selectedIndex = data.getIntExtra("SELECTED_INDEX", 0);
            selectedProduct = selectedCategory.getPaymentProducts().get(selectedIndex);
            etAmount.setText(selectedProduct.getName());

            tvTotalPrice.setText(Extension.priceFormat(selectedProduct.getPrice()));
        }
    }

    private void requestPayment(PayStoreRequest paymentRequest) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                enterPinDialog.startLoading();
            }
        });

        try {
            String number = etPhone.getText().toString();
            PaymentProductBody product = new PaymentProductBody();
            product.setPhoneNumber(number);
            product.setPaymentProductId(selectedProduct.getId());
            product.setPassword(enterPinDialog.getPassword());
            product.setAmount(paymentRequest.getAmount());
            product.setWalletType(0);

            API.service().payProduct(categoryId, product).enqueue(new APICallback<APIResponse>(GameVoucherPhonePrepaidActivity.this) {
                @Override
                protected void onSuccess(APIResponse apiResponse) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            enterPinDialog.stopLoading();
                            enterPinDialog.dismiss();
                        }
                    });
                    btnSubmit.setEnabled(true);

                    Intent intent = new Intent(GameVoucherPhonePrepaidActivity.this, SuccessTransactionActivity.class);
                    intent.putExtra("RECEIPT", Parcels.wrap(apiResponse.getData().getReceipt()));
                    intent.putExtra("IS_PRODUCT", true);
                    if (isGame) {
                        intent.putExtra("PRODUCT_TYPE", "GAME");
                    } else {
                        intent.putExtra("PRODUCT_TYPE", "PULSA");
                    }
                    startActivity(intent);
                }

                @Override
                protected void onError(BadRequest error) {
                    Toast.makeText(GameVoucherPhonePrepaidActivity.this, error.errorDetails, Toast.LENGTH_SHORT).show();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            enterPinDialog.stopLoading();
                        }
                    });
                }
            });
        } catch (Exception exception) {
            Log.e("PAY PPOB", "" + exception);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    enterPinDialog.stopLoading();
                }
            });
        }
    }

    private void requestForgotPassword() {
        enterPinDialog.enableForgotPassword(false);
        runOnUiThread(new Runnable() {
            public void run() {
                Extension.showLoading(GameVoucherPhonePrepaidActivity.this);
            }
        });

        MultipartBody.Builder buildernew = new MultipartBody.Builder();
        buildernew.setType(MultipartBody.FORM);
        buildernew.addFormDataPart("email", API.currentUser().getEmail());
        MultipartBody requestBody = buildernew.build();

        API.service().forgotPassword(requestBody).enqueue(new APICallback<APIResponse>(GameVoucherPhonePrepaidActivity.this) {
            @Override
            protected void onSuccess(APIResponse response) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        Extension.dismissLoading();
                    }
                });
                enterPinDialog.enableForgotPassword(true);

                AlertDialog alertDialog = new AlertDialog.Builder(GameVoucherPhonePrepaidActivity.this).create();
                alertDialog.setTitle(getString(R.string.successful));
                alertDialog.setMessage(getString(R.string.forgot_password_successful));
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.dialog_ok),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }

            @Override
            protected void onError(BadRequest error) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        Extension.dismissLoading();
                    }
                });
                enterPinDialog.enableForgotPassword(true);

                if (error.code == 400) {
                    AlertDialog alertDialog = new AlertDialog.Builder(GameVoucherPhonePrepaidActivity.this).create();
                    alertDialog.setTitle(getString(R.string.sorry));
                    alertDialog.setMessage(error.errorDetails);
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.dialog_ok),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();

                } else {
                    try {
                        StringBuilder errorMessage = new StringBuilder();
                        Set<Map.Entry<String, JsonElement>> entries = error.errors.entrySet();//will return members of your object
                        for (Map.Entry<String, JsonElement> entry : entries) {
                            errorMessage.append(entry.getValue().getAsString()).append("\n");
                            ;
                        }

                        AlertDialog alertDialog = new AlertDialog.Builder(GameVoucherPhonePrepaidActivity.this).create();
                        alertDialog.setTitle(getString(R.string.sorry));
                        alertDialog.setMessage(errorMessage.toString());
                        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.dialog_ok),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();

                    } catch (Exception exception) {
                        Log.e("forgotPassword", "" + exception);
                        AlertDialog alertDialog = new AlertDialog.Builder(GameVoucherPhonePrepaidActivity.this).create();
                        alertDialog.setTitle(getString(R.string.sorry));
                        alertDialog.setMessage(error.errorDetails);
                        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.dialog_ok),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                    }
                }
            }
        });
    }
}
