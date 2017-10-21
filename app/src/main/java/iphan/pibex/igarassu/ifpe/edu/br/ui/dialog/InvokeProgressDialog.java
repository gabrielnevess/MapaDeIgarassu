package iphan.pibex.igarassu.ifpe.edu.br.ui.dialog;

import android.app.ProgressDialog;
import android.content.Context;

public class InvokeProgressDialog {

    private static ProgressDialog progressDialog;

    /**
     * Método para exibir um janela de progresso(Dialog)
     * @param context
     * @param title
     * @param message
     */
    public static void progressDialogStart(Context context, String title, String message) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle(title);
        progressDialog.setMessage(message);
        progressDialog.show();
    }

    //Método para parar de exibir o dialog
    public static void progressDialogDismiss() {
        progressDialog.cancel();
    }

}