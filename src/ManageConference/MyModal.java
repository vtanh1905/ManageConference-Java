package ManageConference;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class MyModal {

    public static void AlterSuccessfully(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thông báo");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void AlterERROR(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Thông báo");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static int AlterChoices(String message, String msgBtnYes, String msgBtnNo){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Thông báo");
        alert.setHeaderText(message);

        ButtonType yes = new ButtonType(msgBtnYes);
        ButtonType no = new ButtonType(msgBtnNo);


        // Loại bỏ các ButtonType mặc định
        alert.getButtonTypes().clear();

        alert.getButtonTypes().addAll(yes, no);

        // option != null.
        Optional<ButtonType> option = alert.showAndWait();

        if (option.get() == null) {
            return 0;
        } else if (option.get() == yes) {
            return 1;
        } else if (option.get() == no) {
            return 0;
        } else {
            return 0;
        }
    }
}
