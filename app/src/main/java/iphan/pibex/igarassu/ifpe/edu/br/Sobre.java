package iphan.pibex.igarassu.ifpe.edu.br;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Sobre extends FragmentActivity {

    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sobre);


        text = (TextView) findViewById(R.id.textAboutMe);
        text.setText("Projeto de Extensão do Instituto Federal de Pernambuco - Campus Igarassu\n\n"+
                        "Criadores:\n\n" +
                        "Gabriel Lima\n" +
                        "José Gabriel Vicente das Neves da Silva\n\n" +
                        "Orientador:\n\n" +
                        "Allan Diego Souza Lima\n");

    }
}
