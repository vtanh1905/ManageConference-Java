package ManageConference;

import entities.ConferenceEntity;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class ConferenceItemMinController implements Initializable {

    @FXML
    private Label lblTen;

    @FXML
    private Label lblDiaDiem;

    @FXML
    private Label lblThoiGian;

    private ConferenceEntity conferenceEntity;
    private String address;


    public ConferenceItemMinController(ConferenceEntity conferenceEntity1, String Address) {
        conferenceEntity = conferenceEntity1;
        address = Address;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lblTen.setText(conferenceEntity.getName());
        lblDiaDiem.setText(address);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String dateStart = formatter.format(new Date(Long.parseLong(conferenceEntity.getTimeStartAt())));
        formatter = new SimpleDateFormat("HH:mm");
        String dateEnd = formatter.format(new Date(Long.parseLong(conferenceEntity.getTimeEndAt())));
        lblThoiGian.setText(dateStart+"->"+dateEnd);
    }

    @FXML
    public void onAction_ConferenceItemMix(MouseEvent mouseEvent) throws IOException {
        System.out.println("onAction_ConferenceItemMix");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ConferenceDetail.fxml"));
        loader.setController(new ConferenceDetailController(conferenceEntity, address));
        Parent root = loader.load();
        MenuController.getConPage.getChildren().setAll(root);
    }

}
