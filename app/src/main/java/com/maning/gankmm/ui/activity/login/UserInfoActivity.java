package com.maning.gankmm.ui.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.maning.gankmm.R;
import com.maning.gankmm.bean.mob.MobUserInfo;
import com.maning.gankmm.ui.activity.SettingActivity;
import com.maning.gankmm.ui.activity.SupportPayActivity;
import com.maning.gankmm.ui.base.BaseActivity;
import com.maning.gankmm.utils.DialogUtils;
import com.maning.gankmm.utils.IntentUtils;
import com.maning.gankmm.utils.MySnackbar;
import com.maning.gankmm.utils.UserUtils;
import com.maning.librarycrashmonitor.utils.StatusBarUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserInfoActivity extends BaseActivity {

    @Bind(R.id.avatar)
    CircleImageView mAvatar;
    @Bind(R.id.ll_content)
    LinearLayout mLlContent;

    private MobUserInfo mUserCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);

        StatusBarUtil.setTranslucentForImageView(this, 20, mLlContent);
    }


    @OnClick(R.id.btn_edit_info)
    public void btn_edit_info() {
        startActivity(new Intent(this, EditUserInfoActivity.class));
    }


    @OnClick(R.id.btn_back)
    public void btn_back() {
        this.finish();
    }


    @OnClick(R.id.item_app_collect)
    public void item_app_collect() {
        MySnackbar.makeSnackBarGreen(mAvatar,"正在开发中...");
    }

    @OnClick(R.id.item_app_setting)
    public void item_app_setting() {
        startActivity(new Intent(this, SettingActivity.class));
    }

    @OnClick(R.id.item_app_market)
    public void item_app_market() {
        IntentUtils.goToMarket(this, "com.maning.gankmm");
    }

    @OnClick(R.id.item_app_support)
    public void item_app_support() {
        startActivity(new Intent(this, SupportPayActivity.class));
    }


    @OnClick(R.id.btn_quit_login)
    public void btn_quit_login() {
        //退出登录
        DialogUtils.showMyDialog(mContext, "退出提示", "确定要退出当前用户吗?", "退出", "取消", new DialogUtils.OnDialogClickListener() {
            @Override
            public void onConfirm() {
                quitLogin();
            }

            @Override
            public void onCancel() {

            }
        });

    }

    private void quitLogin() {
        //清空登录信息
        UserUtils.quitLogin();
        this.finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshUserInfo();
    }

    private void refreshUserInfo() {
        //刷新数据
        mUserCache = UserUtils.getUserCache();
        //设置头像
        RequestOptions options = new RequestOptions();
        options.placeholder(R.mipmap.icon_view_background);
        options.error(R.mipmap.icon_view_background);
        Glide.with(mContext).load(mUserCache.getAvatarLocal()).apply(options).into(mAvatar);
    }
}