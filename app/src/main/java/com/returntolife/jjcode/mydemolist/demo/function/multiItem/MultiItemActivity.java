package com.returntolife.jjcode.mydemolist.demo.function.multiItem;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.returntolife.jjcode.mydemolist.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HeJiaJun on 2019/6/5.
 * Email:
 * des:多样式的adapter
 */
public class MultiItemActivity extends Activity {

    private RecyclerView rv;
    private List<HolderData> dataList;
    private MultiItemTypeAdapter<HolderData> multiItemTypeAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiitem);

        rv=findViewById(R.id.rv);

        dataList=new ArrayList<>();
        dataList.add(new HolderData("aaaa",HolderTypeConstants.HOLDER_TYPE_B));
        dataList.add(new HolderData("aaaa",HolderTypeConstants.HOLDER_TYPE_A));
        dataList.add(new HolderData("aaaa",HolderTypeConstants.HOLDER_TYPE_A));
        dataList.add(new HolderData("aaaa",HolderTypeConstants.HOLDER_TYPE_B));

        multiItemTypeAdapter=new MultiItemTypeAdapter<>(this,dataList);
        multiItemTypeAdapter.addItemViewDelegate(new TestAViewHolder());
        multiItemTypeAdapter.addItemViewDelegate(new TestBViewHolder());

        rv.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        rv.setAdapter(multiItemTypeAdapter);
    }
}
