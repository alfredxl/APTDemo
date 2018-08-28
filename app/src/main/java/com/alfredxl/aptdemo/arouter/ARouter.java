package com.alfredxl.aptdemo.arouter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

/**
 * <br> ClassName:   ${className}
 * <br> Description:
 * <br>
 * <br> @author:      谢文良
 * <br> Date:        2018/8/10 10:23
 */
public class ARouter {

    private IARouterService mARouterService;

    private static ARouter mARouter;

    public static ARouter getInstance() {
        if (mARouter == null) {
            mARouter = new ARouter();
        }
        return mARouter;
    }

    private ARouter() {
        try {
            setARouterService((IARouterService) Class.forName("com.alfredxl.aptdemo.ARouterService").newInstance());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setARouterService(IARouterService service) {
        this.mARouterService = service;
    }


    public void startActivity(String path, Context context) {
        if (mARouterService != null && context != null && !TextUtils.isEmpty(path)) {
            Class mActivityClass = mARouterService.getActivityClass(path);
            if (mActivityClass != null) {
                context.startActivity(new Intent(context, mActivityClass));
            }
        }
    }
}
