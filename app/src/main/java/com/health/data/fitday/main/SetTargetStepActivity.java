package com.health.data.fitday.main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.health.data.fitday.mine.AboutActivity;
import com.health.data.fitday.utils.SpUtils;
import com.sinophy.smartbracelet.R;
import com.tjdL4.tjdmain.contr.L4Command;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import per.goweii.actionbarex.common.ActionBarCommon;
import per.goweii.actionbarex.common.OnActionBarChildClickListener;

public class SetTargetStepActivity extends BaseActivity {
    @BindView(R.id.test_wheelview) MyWheelView myWheelView;
    @BindView(R.id.simple_action_bar)
    ActionBarCommon actionBarCommon;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    int target = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_set_target_step;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        this.actionBarCommon.setOnLeftIconClickListener(new OnActionBarChildClickListener() {
            public void onClick(View param1View) {
                System.out.println("点击事件");
                SetTargetStepActivity.this.finish();
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (target == 0) {
                    target = 6000;
                }
                L4Command.TargetStepSet(target);
                SpUtils.putInt(SetTargetStepActivity.this, "goal", target);
                finish();
            }
        });

        List<String> data = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            data.add(i * 1000 + "");
        }
        int goal = SpUtils.getInt(SetTargetStepActivity.this, "goal");
        myWheelView.setDataWithSelectedItemIndex(data, goal / 1000 - 1);

        myWheelView.setWheelViewSelectedListener(new IWheelViewSelectedListener() {
            @Override
            public void wheelViewSelectedChanged(MyWheelView myWheelView, List<String> data, int position) {
                target = Integer.parseInt(data.get(position));
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}