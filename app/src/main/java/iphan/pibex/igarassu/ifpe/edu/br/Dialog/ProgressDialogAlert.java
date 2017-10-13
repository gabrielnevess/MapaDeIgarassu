package iphan.pibex.igarassu.ifpe.edu.br.Dialog;

import android.app.ProgressDialog;
import android.content.Context;

public class ProgressDialogAlert {

    private static ProgressDialog progressDialog;

    public static void progressDialogStart(Context context, String title, String message) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle(title);
        progressDialog.setMessage(message);
        progressDialog.show();
    }

    public static void progressDialogDismiss() {
        progressDialog.cancel();
    }

}