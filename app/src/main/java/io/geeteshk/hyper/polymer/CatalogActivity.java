package io.geeteshk.hyper.polymer;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import io.geeteshk.hyper.R;
import io.geeteshk.hyper.helper.Decor;
import io.geeteshk.hyper.helper.Pref;

public class CatalogActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    FloatingActionButton mFinishCreate;
    GridView mCatalog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Pref.get(this, "dark_theme", false)) {
            setTheme(R.style.Hyper_Dark);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);
        Decor.setStatusBarColor(this, -1);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Polymer Elements Catalog");
        }

        mFinishCreate = (FloatingActionButton) findViewById(R.id.finish_create);
        mFinishCreate.setOnClickListener(this);

        mCatalog = (GridView) findViewById(R.id.elements_catalog);
        mCatalog.setAdapter(new CatalogAdapter(this));
        mCatalog.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent elementsIntent = new Intent(CatalogActivity.this, ElementsActivity.class);
         elementsIntent.putExtra("element_id", position);
        startActivity(elementsIntent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.finish_create:
                Intent intent = new Intent(CatalogActivity.this, SetupActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}
