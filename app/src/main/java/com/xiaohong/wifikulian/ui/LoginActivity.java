package com.xiaohong.wifikulian.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.xiaohong.wifikulian.Interface.SubscriberOnNextListener;
import com.xiaohong.wifikulian.R;
import com.xiaohong.wifikulian.models.LoginBean;
import com.xiaohong.wifikulian.utils.NetworkRequestMethods1;
import com.xiaohong.wifikulian.utils.ProgressSubscriber;
import com.xiaohong.wifikulian.utils.Utils;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView txtPwdUpdate;
    private EditText edtUserName;
    private EditText edtPwd;
    private TextView txtNewUser;
    private TextView txtForgetPwd;
    private Button btnLogin;

    private SubscriberOnNextListener LoginListener;

    @SuppressLint("InlinedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.hideActiconBar(this);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        txtForgetPwd = (TextView) findViewById(R.id.txt_forget_pwd);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        txtPwdUpdate = (TextView) findViewById(R.id.txt_pwd_update);
        txtNewUser = (TextView) findViewById(R.id.txt_new_user);
        btnLogin = (Button) findViewById(R.id.btn_login);
        edtPwd = (EditText) findViewById(R.id.edt_pwd);
        edtPwd.addTextChangedListener(watcher);
        edtUserName = (EditText) findViewById(R.id.edt_username);
        edtUserName.addTextChangedListener(watcher);
        String exchange = getResources().getString(R.string.login_pwd_update);
        txtPwdUpdate.setText(Html.fromHtml(exchange));
        txtForgetPwd.setOnClickListener(this);
        txtNewUser.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        initRequestListenter();
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.txt_forget_pwd:
                intent.setClass(LoginActivity.this, ActivityForgetPwd.class);
                startActivity(intent);
                overridePendingTransition(R.anim.x_minus100_2_0, R.anim.x_0_2_0);
                break;
            case R.id.txt_new_user:
                intent.setClass(LoginActivity.this, ActivityNewUser.class);
                startActivity(intent);
                overridePendingTransition(R.anim.x_100_2_0, R.anim.x_0_2_0);
                break;
            case R.id.btn_login:
                String strUserName = edtUserName.getText().toString();
                String strPwd = edtPwd.getText().toString();
                NetworkRequestMethods1.getInstance().login(new ProgressSubscriber<LoginBean>(LoginListener, LoginActivity.this), strUserName,strPwd);
                break;
            default:
                break;
        }
    }

    private void initRequestListenter() {
        LoginListener = new SubscriberOnNextListener<LoginBean>() {
            @Override
            public void onNext(LoginBean loginBean) {
                if (loginBean.getRet_code() == 0)
                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(LoginActivity.this, "登录失败；" + loginBean.getRet_msg(), Toast.LENGTH_SHORT).show();
            }
        };
    }

    private TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if(edtUserName.getText().toString().length() != 0 && edtPwd.getText().toString().length() != 0)
                btnLogin.setEnabled(true);
            else
                btnLogin.setEnabled(false);
        }
    };
}