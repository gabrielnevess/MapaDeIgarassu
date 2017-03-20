package iphan.pibex.igarassu.ifpe.edu.br;

/**
 * Created by Gabriel Neves on 19/03/2017.
 */

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

public class VejaMais extends FragmentActivity {

    private Location location;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_veja_mais);

        Bundle b = getIntent().getExtras();
        int index = b.getInt("id");

        CustomApplication application = (CustomApplication) getApplication();

        this.location = application.getLocations()[index];

        TextView tv_titulo = (TextView) findViewById(R.id.tv_titulo);
        tv_titulo.setText(this.location.getName());

//        TextView tv_endereco = (TextView) findViewById(R.id.tv_endereco);
//        tv_endereco.setText(this.location.getEndereco());

    }


}
