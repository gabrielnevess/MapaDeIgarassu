package iphan.pibex.igarassu.ifpe.edu.br.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;

import agency.tango.materialintroscreen.MaterialIntroActivity;
import agency.tango.materialintroscreen.SlideFragmentBuilder;
import iphan.pibex.igarassu.ifpe.edu.br.R;
import iphan.pibex.igarassu.ifpe.edu.br.ui.fragments.TermsConditionsSlideFragment;
import iphan.pibex.igarassu.ifpe.edu.br.util.SharedPreferencesUtil;

public class IntroActivity extends MaterialIntroActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        verifyIntroActivity();
        addSlide(
                new SlideFragmentBuilder()
                        .backgroundColor(R.color.slide_1)
                        .buttonsColor(R.color.slide_button)
                        .title(getResources().getString(R.string.slide_1_title))
                        .description(getResources().getString(R.string.slide_1_description))
                        .image(R.mipmap.ic_launcher)
                        .build()

        );

        String[] neededPermissons = new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
        };
        addSlide(
                new SlideFragmentBuilder()
                        .backgroundColor(R.color.slide_2)
                        .buttonsColor(R.color.slide_button)
                        .title(getResources().getString(R.string.slide_2_title))
                        .description(getResources().getString(R.string.slide_2_description))
                        .image(R.mipmap.ic_launcher)
                        .neededPermissions(neededPermissons)
                        .build()

        );

        addSlide(new TermsConditionsSlideFragment());

    }

    /**
     * Método que verifica estado da activity de introdução
     */
    private void verifyIntroActivity() {
        if (SharedPreferencesUtil.isIntroActivityShow(this)) {
            Intent intent = new Intent(IntroActivity.this, MapActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() { //Método de back do botão do celular
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }

}
