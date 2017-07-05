package iphan.pibex.igarassu.ifpe.edu.br;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

public class SeeMore extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_more);

        Bundle b = getIntent().getExtras();
        String name = b.getString("name");
        String address = b.getString("address");

        TextView tv_titulo = (TextView) findViewById(R.id.tv_title);
        tv_titulo.setText(name);

        TextView tv_endereco = (TextView) findViewById(R.id.tv_address);
        tv_endereco.setText("Endereço: " + address);

    }
}
