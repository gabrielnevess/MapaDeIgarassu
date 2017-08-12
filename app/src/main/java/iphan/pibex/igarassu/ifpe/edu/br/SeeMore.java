package iphan.pibex.igarassu.ifpe.edu.br;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

public class SeeMore extends AppCompatActivity {

    private CollapsingToolbarLayout collapsingToolbarLayout = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_more);

        Bundle b = getIntent().getExtras();
        String name = b.getString("name");
        String address = b.getString("address");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_see_more);
        setSupportActionBar(toolbar);

//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(name);

        TextView tv_address = (TextView) findViewById(R.id.tv_address);
        tv_address.setText("Endereço: " + address);

        toolbarTextAppearance();

    }


    private void toolbarTextAppearance() {
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.collapsedappbar);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.expandedappbar);
    }



}
