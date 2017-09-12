package com.wdeo3601.gankio.base.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.wdeo3601.gankio.R;


/**
 * 带有 Toolbar 的 Activity
 */
public abstract class ToolbarActivity extends BaseActicity implements View.OnClickListener {
    protected AppBarLayout mAppbar;
    protected Toolbar mToolbar;
    protected boolean isHidden;
    private View mTitleDeviderLine;

    @Override
    protected void initView() {
        mAppbar = (AppBarLayout) findViewById(R.id.app_bar_layout);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTitleDeviderLine = findViewById(R.id.title_devider_line);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        if (mToolbar == null || mAppbar == null) {
            throw new IllegalStateException("No Toolbar");
        }
        mToolbar.setOnClickListener(this);
        setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        setSupportActionBar(mToolbar);
        if (canBack()) {
            ActionBar supportActionBar = getSupportActionBar();
            if (supportActionBar != null) supportActionBar.setDisplayHomeAsUpEnabled(true);
        }
//        if (Build.VERSION.SDK_INT >= 21) mAppbar.setElevation(10.6f);
    }

    //设置字子标题
    protected void setSubTitle(int resId) {
        mToolbar.setSubtitle(resId);
    }

    //设置logo图片
    protected void setLogo(int resId) {
        mToolbar.setLogo(resId);
    }

    protected void setLogo(Drawable res) {
        mToolbar.setLogo(res);
    }

    protected void setNavigationIcon(int redId) {
        mToolbar.setNavigationIcon(redId);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (getMenuId() > 0) {
            getMenuInflater().inflate(getMenuId(), menu);
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    //返回 menu 菜单的布局
    protected int getMenuId() {
        return 0;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar:
                onToolbarClick();
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    //toolbar被点击了
    private void onToolbarClick() {
    }

    public boolean canBack() {
        return false;
    }

    /**
     * 设置是否显示标题栏的分割线
     *
     * @param visiable
     */
    protected void setTitleDeviderLineVisiable(boolean visiable) {
        if (mTitleDeviderLine == null) {
            return;
        }
        if (visiable) {
            mTitleDeviderLine.setVisibility(View.VISIBLE);
        } else {
            mTitleDeviderLine.setVisibility(View.GONE);
        }
    }

    protected void setAppBarAlpha(float alpha) {
        mAppbar.setAlpha(alpha);
    }

    protected void hideOrShowToolbar() {
        mAppbar.animate()
                .translationY(isHidden ? 0 : -mAppbar.getHeight())
                .setInterpolator(new DecelerateInterpolator(2))
                .start();
        isHidden = !isHidden;
    }
}
