package iphan.pibex.igarassu.ifpe.edu.br.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import agency.tango.materialintroscreen.SlideFragment;
import iphan.pibex.igarassu.ifpe.edu.br.Activity.MapActivity;
import iphan.pibex.igarassu.ifpe.edu.br.R;
import iphan.pibex.igarassu.ifpe.edu.br.Util.SharedPreferencesUtil;

public class TermsConditionsSlideFragment extends SlideFragment {

    private CheckBox checkBox;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_terms_conditions_slide, container, false);
    }

    @Override
    public boolean canMoveFurther() {
        checkBox = (CheckBox) getActivity().findViewById(R.id.cb_concordo);
        if (checkBox.isChecked()) {
            SharedPreferencesUtil.updateIntroStatus(getContext(), true);

            Intent intent = new Intent(getActivity(), MapActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            getActivity().finish();

        }
        return checkBox.isChecked();

    }

    @Override
    public String cantMoveFurtherErrorMessage() {
        return getActivity().getResources().getString(R.string.slide_3_checkbox_error);
    }

    @Override
    public int backgroundColor() {
        return R.color.slide_3;
    }

    @Override
    public int buttonsColor() {
        return R.color.slide_button;
    }
}
