package iphan.pibex.igarassu.ifpe.edu.br.Activity;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import iphan.pibex.igarassu.ifpe.edu.br.R;

public class AboutActivity extends AppCompatActivity {

    private CollapsingToolbarLayout collapsingToolbarLayout;
    private ActionBar actionBar;
    private TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_about);
        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        textView = (TextView) findViewById(R.id.tv_information);
        textView.setText("Projeto de Extensão do Instituto Federal de Pernambuco - Campus Igarassu\n\n"+
                "Desenvolvedores:\n" +
                "Gabriel Lima Gonçalves da Silva\n" +
                "José Gabriel Vicente das Neves da Silva\n\n" +
                "Orientador:\n" +
                "Allan Diego Silva Lima\n");

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle("Sobre");

        toolbarTextAppearance();

    }

    private void toolbarTextAppearance() {
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.collapsedappbar);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.expandedappbar);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (android.R.id.home == item.getItemId()) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


}
