package com.health.data.fitday;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import com.tjdL4.tjdmain.Dev;
import com.tjdL4.tjdmain.L4M;
import com.tjdL4.tjdmain.contr.Health_HeartBldPrs;
import com.tjdL4.tjdmain.contr.Health_Sleep;
import com.tjdL4.tjdmain.contr.Health_TodayPedo;
import com.tjdL4.tjdmain.contr.L4Command;
import com.tjdL4.tjdmain.contr.TimeUnitSet;
import java.util.List;
import com.sinophy.smartbracelet.R;

public class MainActivity extends Activity implements View.OnClickListener{


    private static final String TAG = "MainActivity";

    private Button btn_search,btn_step,btn_sleep,btn_startHrt,btn_Hrt,btn_startBldPrs,btn_BldPrs,btn_unitcm,btn_unitin;
    private ImageButton btn_last,btn_next;
    private TextView tv_date,tv_unit;
    private Activity mMainActivity;
    static final String startTime="22:00:00";
    static final String endTime="10:00:00";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        PermissionUtil.checkPermissions(this);
        Test_InMainActivity();
    }


    //如果使用外部存储方式，使用以下接口接收手环的健康数据,这个接口可以放在service或者主Activity里面注册

    //TypeInfo 类型
    //HEART_NOW         --当前测量的心率值
    //BLDPRESS_NOW      --当前测量的血压值
    //HEART_HISTORY     --历史测量的心率值
    //BLDPRESS_HISTORY  --历史测量的血压值
    //PEDO_DAY          --天记步数据
    //PEDO_TIME_HISTORY --时间段记步数据
    //SLEEP_DAY         --天睡眠数据
    //SLEEP_TIME_HISTORY--天睡眠时间段数

     //注意:如果使用外部存储方式,下文所使用数据读取接口获取数据将失效,失效的接口如下
    //Health_TodayPedo.GetHealth_Data
    //Health_Sleep.GetSleep_Data
    //Health_HeartBldPrs.GetHeart_Data
    //Health_HeartBldPrs.GetBloodPrs_Data
    public void Test_InMainActivity()
    {
        L4M.SetResultListener(new L4M.BTResultListenr() {
            @Override
            public void On_Result(String TypeInfo, String StrData, Object DataObj) {

            }
        });

        L4M.SetResultToDBListener(new L4M.BTResultToDBListenr(){
            @Override
            public void On_Result(String TypeInfo, String StrData, Object DataObj) {

            }

            @Override
            public void On_ProgressResult(String TypeInfo, int datTotal, int datIdx, String StrData, Object DataObj) {
                //返回数据
                Log.w(TAG," TypeInfo:"+TypeInfo+"  Tatal:"+datTotal+"  index:"+datIdx+"  StrData:"+StrData);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unconfigure_View();

    }

    public void configure_view()
    {

//        //计步
//        Dev.SetUpdateUiListener(Dev.L4UI_PageDATA_PEDO, myUpDateUiCb);
//        Dev.EnUpdateUiListener(myUpDateUiCb, 1);
//
//        //睡眠
//        Dev.SetUpdateUiListener(Dev.L4UI_PageDATA_SLEEP,myUpDateUiCbSleep);
//        Dev.EnUpdateUiListener(myUpDateUiCbSleep, 1);
//
//        //心率
//        Dev.SetUpdateUiListener(Dev.L4UI_PageDATA_HEARTRATE,myUpDateUiCbHrt);
//        Dev.EnUpdateUiListener(myUpDateUiCbHrt, 1);
//
//        //血压
//        Dev.SetUpdateUiListener(Dev.L4UI_PageDATA_BLOODPRESS,myUpDateUiCbBldPrs);
//        Dev.EnUpdateUiListener(myUpDateUiCbBldPrs, 1);

//        //计步
//        Dev.SetUpdateUiListener(Dev.L4UI_PageDATA_PEDO, myUpDateUiCb,Dev.L4UI_DATA_PEDO);
//        Dev.EnUpdateUiListener(myUpDateUiCb, 1);
//
//        //睡眠
//        Dev.SetUpdateUiListener(Dev.L4UI_PageDATA_SLEEP,myUpDateUiCb,Dev.L4UI_DATA_SLEEP);
//        Dev.EnUpdateUiListener(myUpDateUiCb, 1);
//
//        //心率
//        Dev.SetUpdateUiListener(Dev.L4UI_PageDATA_HEARTRATE,myUpDateUiCb,Dev.L4UI_DATA_HEARTRATE);
//        Dev.EnUpdateUiListener(myUpDateUiCb, 1);
//
//        //血压
//        Dev.SetUpdateUiListener(Dev.L4UI_PageDATA_BLOODPRESS,myUpDateUiCb,Dev.L4UI_DATA_BLOODPRESS);
//        Dev.EnUpdateUiListener(myUpDateUiCb, 1);

        Dev.SetUpdateUiListener(Dev.L4UI_PageDATA_PEDO, myUpDateUiCb,0);
        Dev.EnUpdateUiListener(myUpDateUiCb, 1);
    }

    public void unconfigure_View()
    {

        Dev.EnUpdateUiListener(myUpDateUiCb, 0);
//        Dev.EnUpdateUiListener(myUpDateUiCbSleep, 0);
//        Dev.EnUpdateUiListener(myUpDateUiCbHrt, 0);
//        Dev.EnUpdateUiListener(myUpDateUiCbBldPrs, 0);
    }



    private void initView() {
        mMainActivity=this;
        btn_search=findViewById(R.id.btn_search);
        btn_step=findViewById(R.id.btn_step);
        btn_sleep=findViewById(R.id.btn_sleep);
        btn_startHrt=findViewById(R.id.btn_startHrt);
        btn_Hrt=findViewById(R.id.btn_Hrt);
        btn_startBldPrs=findViewById(R.id.btn_startBldPrs);
        btn_BldPrs=findViewById(R.id.btn_BldPrs);
        btn_last=findViewById(R.id.btn_last);
        btn_next=findViewById(R.id.btn_next);
        btn_unitcm=findViewById(R.id.btn_unitcm);
        btn_unitin=findViewById(R.id.btn_unitin);
        tv_date=findViewById(R.id.tv_date);
        tv_unit=findViewById(R.id.tv_unit);
        tv_date.setText("2020-04-12");

        btn_search.setOnClickListener(this);
        btn_step.setOnClickListener(this);
        btn_sleep.setOnClickListener(this);
        btn_startHrt.setOnClickListener(this);
        btn_Hrt.setOnClickListener(this);
        btn_startBldPrs.setOnClickListener(this);
        btn_BldPrs.setOnClickListener(this);
        btn_last.setOnClickListener(this);
        btn_next.setOnClickListener(this);
        btn_unitcm.setOnClickListener(this);
        btn_unitin.setOnClickListener(this);

        configure_view();

    }
    String in="IN LB";
    String cm="CM KG";
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_search:
                startActivity(new Intent(mMainActivity,SearchActivity.class));
                break;
            case R.id.btn_step:
                pedoData(tv_date.getText().toString(), true);
                break;
            case R.id.btn_sleep:
                sleep(tv_date.getText().toString(), true);
                break;
            case R.id.btn_startHrt:
                sartHrt();
                break;
            case R.id.btn_Hrt:
                Hrt(tv_date.getText().toString(), true);
                break;
            case R.id.btn_startBldPrs:
                startBldPrs();
                break;
            case R.id.btn_BldPrs:
                BldPrs(tv_date.getText().toString(), true);
                break;
            case R.id.btn_unitcm:
                tv_unit.setText( "CM KG");

                L4M. SaveUser_Unit(tv_unit.getText().toString());
                TimeUnitSet(0,255,0);
                break;
            case R.id.btn_unitin:
                tv_unit.setText( "IN LB");

                L4M. SaveUser_Unit(tv_unit.getText().toString());
                TimeUnitSet(0,255,1);
                break;
        }
    }


    void TimeUnitSet(int ss,int hh,int mm)
    {
        TimeUnitSet.TimeUnitSetData myTimeUnitSetData=new TimeUnitSet.TimeUnitSetData();
        myTimeUnitSetData.Sett1=ss;
        myTimeUnitSetData.Sett2=hh;
        myTimeUnitSetData.Sett3=mm;
        String ret=L4Command.Brlt_LANGSet(myTimeUnitSetData);
        Log.e(TAG,"ret:"+ret);
    }

    private void BldPrs(String dateStr, boolean LoadDB) {
        if (LoadDB){
            String tempAddr = L4M.GetConnectedMAC();
            if (tempAddr!=null){
                Health_HeartBldPrs.BloodPrsData mBldPrsData = Health_HeartBldPrs.GetBloodPrs_Data(tempAddr, dateStr);
                List<Health_HeartBldPrs.BldPrsDiz>   BldPrsDizList = mBldPrsData.mBldPrsDiz;
                Log.e(TAG, "血压 " +mBldPrsData.BloodPrs);
                if (BldPrsDizList != null) {
                    for (int i = 0; i < BldPrsDizList.size(); i++) {
                        Health_HeartBldPrs.BldPrsDiz mBldPrsDiz = BldPrsDizList.get(i);
                        Log.e(TAG, "血压详细数据  时间  " + mBldPrsDiz.mMsrTime+" 血压值  "+mBldPrsDiz.mHPress+"/"+mBldPrsDiz.mLPress);
                    }
                }
            }
        }
    }

    private void startBldPrs() {
            Health_HeartBldPrs.Get_BldPrsMeasureResult(
                    new Health_HeartBldPrs.BldPrsResultListener(){

                        @Override
                        public void OnErr(String EventStr, String ErrInfo) {

                            if(EventStr.equals(Health_HeartBldPrs.START)) {
                                if(ErrInfo.equals(Health_HeartBldPrs.NOTSUPPORT)) {
                                    Log.e(TAG,"手环不支持血压测量");
                                }
                            }
                            else if(EventStr.equals(Health_HeartBldPrs.CONNECT)) {
                                switch (ErrInfo)
                                {
                                    case Health_HeartBldPrs.WRONGCONNECTION:
                                        Log.e(TAG,"蓝牙连接不正常");
                                        break;
                                    case Health_HeartBldPrs.ARESYNCHRONIZED:
                                        Log.e(TAG,"正在同步数据,稍后再试!");
                                        break;
                                    case Health_HeartBldPrs.CONNECTLATER:
                                        break;
                                }
                            }
                        }

                        @Override
                        public void OnOpen(String EventStr) {

                            if(EventStr.equals(Health_HeartBldPrs.OPENSTART)) {

                            }
                            else if(EventStr.equals(Health_HeartBldPrs.OPENOK)) {
                                //可以在界面做等待超时处理，超时后可以使用Health_HeartBldPrs.ForceClose_BldPrsMeasure()强制关闭
                                Health_HeartBldPrs.ForceClose_BldPrsMeasure();

                            }
                        }

                        @Override
                        public void OnClose(String EventStr) {
                            if(EventStr.equals(Health_HeartBldPrs.CLOSE)) {

                            }
                            else if(EventStr.equals(Health_HeartBldPrs.END)) {

                            }
                        }


                        @Override
                        public void OnData(String EventStr, String DataInfo) {
                            if(EventStr.equals(Health_HeartBldPrs.RESULTDATA)) {
                                BldPrs(tv_date.getText().toString(), true);
                                Log.e(TAG,"血压测量成功,返回数据:"+DataInfo);
                            }
                            else if(EventStr.equals(Health_HeartBldPrs.FAIL)) {
                                Log.e(TAG,"血压测量失败");
                            }
                        }
                    }
            );


    }

    private void Hrt(String dateStr, boolean LoadDB) {
        if (LoadDB){
            String tempAddr = L4M.GetConnectedMAC();
            if (tempAddr!=null){
                Health_HeartBldPrs.HeartPageData mHeartData = Health_HeartBldPrs.GetHeart_Data(tempAddr, dateStr);
                List<Health_HeartBldPrs.HrtDiz> HrtRateDizList = mHeartData.mHrtDiz;
                Log.e(TAG, "心率  " + mHeartData.HeartRate);
                if (HrtRateDizList != null) {
                    for (int i = 0; i < HrtRateDizList.size(); i++) {
                        Health_HeartBldPrs.HrtDiz mHrtRateDiz = HrtRateDizList.get(i);
                        Log.e(TAG, "心率详细数据  时间  " + mHrtRateDiz.mMsrTime+" 心率值  "+mHrtRateDiz.mHrtRate);
                    }
                }
            }

        }
    }

    private void sartHrt() {

        Health_HeartBldPrs.Get_HeartrateMeasureResult(
                new Health_HeartBldPrs.HeartResultListener(){

                    @Override
                    public void OnErr(String EventStr, String ErrInfo) {

                        if(EventStr.equals(Health_HeartBldPrs.START)) {
                            if(ErrInfo.equals(Health_HeartBldPrs.NOTSUPPORT)) {
                                Log.e(TAG,"手环不支持血压测量");
                            }
                        }
                        else if(EventStr.equals(Health_HeartBldPrs.CONNECT)) {
                            switch (ErrInfo)
                            {
                                case Health_HeartBldPrs.WRONGCONNECTION:
                                    Log.e(TAG,"蓝牙连接不正常");
                                    break;
                                case Health_HeartBldPrs.ARESYNCHRONIZED:
                                    Log.e(TAG,"正在同步数据,稍后再试!");
                                    break;
                                case Health_HeartBldPrs.CONNECTLATER:

                                    break;
                            }
                        }
                    }

                    @Override
                    public void OnOpen(String EventStr) {

                        if(EventStr.equals(Health_HeartBldPrs.OPENSTART)) {

                        }
                        else if(EventStr.equals(Health_HeartBldPrs.OPENOK)) {
                            //打开测量成功，等待结果
                            //可以在界面做等待超时处理，超时后可以使用Health_HeartBldPrs.ForceClose_HeartrateMeasure()强制关闭
                            Health_HeartBldPrs.ForceClose_HeartrateMeasure();

                        }
                    }

                    @Override
                    public void OnClose(String EventStr) {
                        if(EventStr.equals(Health_HeartBldPrs.CLOSE)) {

                        }
                        else if(EventStr.equals(Health_HeartBldPrs.END)) {

                        }
                    }

                    @Override
                    public void OnData(String EventStr, String DataInfo) {
                        if(EventStr.equals(Health_HeartBldPrs.RESULTDATA)) {
                            Hrt(tv_date.getText().toString(), true);
                            Log.e(TAG,"心率测量成功,返回数据:"+DataInfo);
                        }
                        else if(EventStr.equals(Health_HeartBldPrs.FAIL)) {
                            Log.e(TAG,"心率测量失败");
                        }
                    }
                }
        );
    }


    private void sleep(String dateStr, boolean LoadDB) {
        if (LoadDB){
            String tempAddr = L4M.GetConnectedMAC();
            String dateSlp="2020-04-11";
            if (tempAddr!=null){
                Health_Sleep.HealthSleepData sleepData= Health_Sleep.GetSleep_Data(tempAddr,dateSlp,startTime,endTime);
                List<Health_Sleep.TimeSlpDiz> TimeSlpDizList=sleepData.mTimeSlpDiz;
                Log.e(TAG, "睡眠  质量  " + sleepData.sleelLevel
						+ "  清醒  " + sleepData.awakeHour+":"+sleepData.awakeMinute
						+ "  浅睡  " + sleepData.lightHour+":"+sleepData.lightMinute
						+ "  深睡  " + sleepData.deepHour+":"+sleepData.deepMinute
						+ "  总时长  " + sleepData.sumHour+":"+sleepData.sumMinute);

				if (TimeSlpDizList!=null && TimeSlpDizList.size()>0){
					for (int i = 0; i < TimeSlpDizList.size(); i++) {
                        Health_Sleep.TimeSlpDiz mTimeSlpDiz=TimeSlpDizList.get(i);

                        Log.e(TAG,"睡眠详细数据  "+mTimeSlpDiz.mRcdTime+"  "+mTimeSlpDiz.mSlpMode);
					}
				}
            }

        }
    }


    private void pedoData(String dateStr, boolean LoadDB) {

        if (LoadDB) {
            String tempAddr = L4M.GetConnectedMAC();
            if (tempAddr!=null){
                Health_TodayPedo.TodayStepPageData todayData = Health_TodayPedo.GetHealth_Data(tempAddr, dateStr);
                Health_TodayPedo.StepDiz[] mStepDiz=todayData.stepDIZ;
                if(mStepDiz!=null && mStepDiz.length>0){
                    for(int i=0;i<mStepDiz.length;i++){
                        String mTime=L4M.getCrtValStr(mStepDiz[i].mTime,"0");
                        String mStep=L4M.getCrtValStr(mStepDiz[i].mStep,"0");
                        String mDistance=L4M.getCrtValStr(mStepDiz[i].mDistance,"0");
                        String mCalorie=L4M.getCrtValStr(mStepDiz[i].mCalorie,"0");
                        if (mTime.equals("00") || mTime.equals("0") || mStep.equals("0"))
                        {
                            //madapter.add(new Vw_StepAdapter.ContentData("","","",""+""));
                        }
                        else
                        {
                            Log.e(TAG, "计步详细数据  时间  " + mTime
                                    + "  步数  " + mStep
                                    + "  距离  " + mDistance
                                    + "  能量  " +mCalorie);
                        }
                    }

                }

                Log.e(TAG, "  步数  " + todayData.step
						+ "  距离 " + todayData.distance
						+ "  距离单位  " + todayData.distanceUnit
						+ "  能量  " +todayData.energy
                        + "  心率  " +todayData.heart
                        + "  血压  " +todayData.bloodPrs);

            }

        }

    }


//    private void initReceiver()
//    {
//        L4M.registerBTStReceiver(mMainActivity,DataReceiver);
//    }
//
//    private void unReceiver()
//    {
//        L4M.unregisterBTStReceiver(mMainActivity,DataReceiver);
//    }
//
//    private L4M.BTStReceiver DataReceiver = new L4M.BTStReceiver() {
//        @Override
//        public void  OnRec(String InfType ,String Info)
//        {
//            Log.i(TAG,"Info:"+Info);
//            if (Info.contains("Connecting"))
//            {
//                isConnect();
//            }
//            else if (Info.contains("BT_BLE_Connected"))
//            {
//                isConnect();
//            }
//            else if (Info.contains("close"))
//            {
//                isConnect();
//            }
//
//        }
//    };
//
//
//    public void isConnect()
//    {
//
//        if(L4M.Get_Connect_flag()==1)
//        {
//            Log.e(TAG,"正在连接");
//        }
//        else if(L4M.Get_Connect_flag()==2)
//        {
//            Log.e(TAG,"已连接");
//        }
//        else
//        {
//            Log.e(TAG,"未连接");
//        }
//
//    }

    Dev.UpdateUiListenerImpl myUpDateUiCb = new Dev.UpdateUiListenerImpl() {
        @Override
        public void UpdateUi(int ParaA, String StrData)
        {
            final int dataType=ParaA;
            Log.e(TAG,"ParaA:"+ParaA);
            runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    if (dataType==Dev.L4UI_DATA_PEDO){
                        pedoData(tv_date.getText().toString(), true);
                    }else if (dataType==Dev.L4UI_DATA_SLEEP){
                        sleep(tv_date.getText().toString(), true);
                    }else if (dataType==Dev.L4UI_DATA_HEARTRATE){
                        Hrt(tv_date.getText().toString(), true);
                    }else if (dataType==Dev.L4UI_DATA_BLOODPRESS){
                        BldPrs(tv_date.getText().toString(), true);
                    }


                }
            });
        }
    };

    Dev.UpdateUiListenerImpl myUpDateUiCbSleep = new Dev.UpdateUiListenerImpl() {
        @Override
        public void UpdateUi(int ParaA, String StrData)
        {
            runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    sleep(tv_date.getText().toString(), true);
                }
            });
        }
    };

    Dev.UpdateUiListenerImpl myUpDateUiCbHrt = new Dev.UpdateUiListenerImpl() {
        @Override
        public void UpdateUi(int ParaA, String StrData)
        {
            runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    Hrt(tv_date.getText().toString(), true);
                }
            });
        }
    };

    Dev.UpdateUiListenerImpl myUpDateUiCbBldPrs = new Dev.UpdateUiListenerImpl() {
        @Override
        public void UpdateUi(int ParaA, String StrData)
        {
            runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    BldPrs(tv_date.getText().toString(), true);
                }
            });
        }
    };


}
