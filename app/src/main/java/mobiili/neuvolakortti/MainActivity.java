package mobiili.neuvolakortti;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.util.Log;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // RecyclerView
        ChildAdapter mAdapter;
        RecyclerView mChildrenList;

        //Database
        DbAdapter db;
        db = new DbAdapter(this);
        db.open();
        List<Child> children = db.getAllChildren(); //All childeren to list

        // RecyclerView
        mChildrenList = findViewById(R.id.rv_children);
        mAdapter = new ChildAdapter(children);
        mChildrenList.setAdapter(mAdapter);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mChildrenList.setLayoutManager(llm);

    }

    //Floating Action Button
    void goToAddChild(View view){
        Intent intent = new Intent(this, AddChildActivity.class);
        startActivity(intent);
    }
}
