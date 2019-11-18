package com.bw.yxs.frag;

import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bw.yxs.R;
import com.bw.yxs.adapter.MyRvAdapter;
import com.bw.yxs.base.BaseFrag;
import com.bw.yxs.base.BasePersent;
import com.bw.yxs.bean.MyBean;
import com.bw.yxs.contract.IContract;
import com.bw.yxs.persent.MyPersent;

import butterknife.BindView;

public class ShouFragment<P extends BasePersent> extends BaseFrag implements IContract.IView {

    public P p;

    @BindView(R.id.rv)
    RecyclerView rv;


    @Override
    protected int initLayout() {
        return R.layout.shou_frag;
    }

    @Override
    protected void initView(View view) {
        rv = view.findViewById(R.id.rv);
        LinearLayoutManager manager=new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(manager);
    }

    @Override
    protected void initData() {
        new MyPersent();
    }

    @Override
    public void success(MyBean myBean) {
        MyRvAdapter rvAdapter=new MyRvAdapter(myBean.getResult(),getActivity());
        rv.setAdapter(rvAdapter);
    }
}
