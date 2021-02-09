package com.returntolife.jjcode.mydemolist.demo.function.exceltest;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.returntolife.jjcode.mydemolist.R;
import com.tools.jj.tools.utils.LogUtil;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/**
 * Created by HeJiaJun on 2021/2/9. Email:hejj@mama.cn des:
 */
public class ExcelActivity extends Activity implements View.OnClickListener {

    private Button btnOutput;
    private Button btnInput;
    private TextView mTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excel);

        btnOutput=findViewById(R.id.btnExport);
        btnInput=findViewById(R.id.btnInput);
        mTextView=findViewById(R.id.tvData);
        btnOutput.setOnClickListener(this);
        btnInput.setOnClickListener(this);
    }


    private void exportDataToExcel(){
        String[] title = { "姓名","编号","性别"};
         List<ProjectBean> students;
        String filePath = Environment.getExternalStorageDirectory()+"/AndroidExcelDemo";
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }

        String fileName = filePath+"/testExcel.xls";
        File fileExcel = new File(fileName);
        if (!fileExcel.exists()) {
            try {
                fileExcel.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        students = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            students.add(new ProjectBean("小红"+i,18,false));
            students.add(new ProjectBean("小明"+i,20,true));
        }

        ArrayList<ArrayList<String>> recordList = new ArrayList<>();
        for (int i = 0; i <students.size(); i++) {
            ProjectBean student = students.get(i);
            ArrayList<String> beanList = new ArrayList<String>();
            beanList.add(student.getName());
            beanList.add(student.getAge()+"");
            beanList.add(student.isBoy()?"男":"女");
            recordList.add(beanList);
        }

        ExcelUtils.initExcel(fileName, title);
        ExcelUtils.writeObjListToExcel(recordList, fileName, this);
    }

    private void inputExcel(){
        String filePath = Environment.getExternalStorageDirectory()+"/AndroidExcelDemo/testExcel.xls";
        try {
            Workbook book = Workbook.getWorkbook(new File(filePath));
            Sheet sheet = book.getSheet(0);

            StringBuilder stringBuilder=new StringBuilder();
            for (int j = 0; j < sheet.getRows(); j++) {
                stringBuilder.append("第"+j+"行");
                for (int i = 0; i < sheet.getColumns(); i++) {
                    stringBuilder.append(sheet.getCell(i,j).getContents());
                }
                stringBuilder.append("\n");
            }
            mTextView.setText(stringBuilder.toString());

        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btnExport){
            exportDataToExcel();
        }else if(v.getId()==R.id.btnInput){
            inputExcel();
        }
    }
}
