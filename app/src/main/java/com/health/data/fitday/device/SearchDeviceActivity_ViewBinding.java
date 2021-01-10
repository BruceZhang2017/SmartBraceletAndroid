package com.health.data.fitday.device;

import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import per.goweii.actionbarex.common.ActionBarCommon;

public class SearchDeviceActivity_ViewBinding implements Unbinder {
    private SearchDeviceActivity target;

    public SearchDeviceActivity_ViewBinding(SearchDeviceActivity paramSearchDeviceActivity) {
        this(paramSearchDeviceActivity, paramSearchDeviceActivity.getWindow().getDecorView());
    }

    public SearchDeviceActivity_ViewBinding(SearchDeviceActivity paramSearchDeviceActivity, View paramView) {
        this.target = paramSearchDeviceActivity;
        paramSearchDeviceActivity.actionBarCommon = (ActionBarCommon)Utils.findRequiredViewAsType(paramView, 2131231197, "field 'actionBarCommon'", ActionBarCommon.class);
        paramSearchDeviceActivity.searchLoadingView = (SearchLoadingView)Utils.findRequiredViewAsType(paramView, 2131231360, "field 'searchLoadingView'", SearchLoadingView.class);
    }

    public void unbind() {
        SearchDeviceActivity searchDeviceActivity = this.target;
        if (searchDeviceActivity != null) {
            this.target = null;
            searchDeviceActivity.actionBarCommon = null;
            searchDeviceActivity.searchLoadingView = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
