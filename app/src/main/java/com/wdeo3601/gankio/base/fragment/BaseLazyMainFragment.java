package com.wdeo3601.gankio.base.fragment;

import com.orhanobut.logger.Logger;
import com.wdeo3601.gankio.R;
import com.wdeo3601.gankio.utils.ToastUtil;

public abstract class BaseLazyMainFragment extends BaseFragment {
    private static final long WAIT_TIME = 2000L;    // 再点一次退出程序时间设置
    private static final java.lang.String TAG = "BaseLazyMainFragment";
    private long TOUCH_TIME = 0;

    @Override
    public void onSupportVisible() {
        Logger.d(TAG, "onSupportVisible");
//        EventBus.getDefault().post(new HideBottomBarEvent(false));
        super.onSupportVisible();
    }

    /**
     * 处理回退事件
     */
    @Override
    public boolean onBackPressedSupport() {
        if (getChildFragmentManager().getBackStackEntryCount() > 1) {
            popChild();
        } else {
            if (System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME) {
                _mActivity.finish();
                System.exit(0);
            } else {
                TOUCH_TIME = System.currentTimeMillis();
                ToastUtil.INSTANCE.showShort(R.string.press_again_exit);
            }
        }
        return true;
    }
}