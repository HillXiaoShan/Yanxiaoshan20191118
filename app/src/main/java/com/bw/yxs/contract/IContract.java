package com.bw.yxs.contract;

import com.bw.yxs.bean.MyBean;

public interface IContract {

    interface IPersent{
        void toRequest();
    }

    interface IView{
        void success(MyBean myBean);
    }
    interface CallBack{
        void success(MyBean myBean);
        void error(String error);
    }

}
