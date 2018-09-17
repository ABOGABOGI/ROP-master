package rich.on.pay.base;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import rich.on.pay.R;
import rich.on.pay.activity.LoginActivity;
import rich.on.pay.api.API;

public abstract class BaseFragment extends Fragment {
    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getContentViewResource(), container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        onViewCreated();
    }

    protected abstract void onViewCreated();

    @LayoutRes
    protected abstract int getContentViewResource();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    protected void forceLogout() {
        API.setSessionError(true);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        if (Build.VERSION.SDK_INT >= 24) {
            builder.setMessage(Html.fromHtml(getString(R.string.session_over), Html.FROM_HTML_MODE_LEGACY));
        } else {
            builder.setMessage(Html.fromHtml(getString(R.string.session_over)));
        }
        builder.setPositiveButton("Ok", null);

        try {
            API.logOut();
            API.setSessionError(false);

            AlertDialog dialog = builder.show();
            TextView messageText = dialog.findViewById(android.R.id.message);
            messageText.setGravity(Gravity.CENTER);
            messageText.setText(R.string.session_over);
            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                public void onDismiss(final DialogInterface dialog) {
//                    final Realm realm = Realm.getDefaultInstance();
//                    final RealmResults<User> userList = realm.where(User.class).equalTo("isSelected", true).findAll();
//                    if (userList != null) {
//                        if (userList.size() > 0) {
//                            realm.executeTransaction(new Realm.Transaction() {
//                                @Override
//                                public void execute(@NonNull Realm realm) {
//                                    User currentUser = userList.get(0);
//                                    currentUser.deleteFromRealm();
//                                }
//                            });
//
//                            realm.executeTransactionAsync(new Realm.Transaction() {
//                                @Override
//                                public void execute(@NonNull Realm realm) {
//                                    final RealmResults<User> prevUserList = realm.where(User.class).findAll();
//                                    if (prevUserList != null) {
//                                        if (prevUserList.size() > 0) {
//                                            User user = realm.copyFromRealm(prevUserList.get(0));
//                                            API.setUser(user);
//                                            API.setToken(String.valueOf(user.getUserToken()));
//                                            API.setOtpSucess("Success");
//                                        }
//                                        Intent intent = new Intent(getActivity(), MainActivity.class);
//                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                        startActivity(intent);
//                                    } else {
//                                        Intent intent = new Intent(getActivity(), MainActivity.class);
//                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                        startActivity(intent);
//                                    }
//                                }
//                            });
//
//                        } else {
//                            Intent intent = new Intent(getActivity(), MainActivity.class);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                            startActivity(intent);
//                        }
//                    } else {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
//                    }
                }
            });
            dialog.show();
        } catch (Exception exception) {
            Log.e("ERROR", "LOGOUT : " + exception);
        }
    }
}