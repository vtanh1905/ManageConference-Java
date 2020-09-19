package ManageConference;

import dao.PartakerDAO;
import entities.ConferenceEntity;
import entities.LocationEntity;
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

public class ConferenceItemController implements Initializable {
    @FXML
    private Label lblID;

    @FXML
    private Label lblTen;

    @FXML
    private Label lblDiaDiem;

    @FXML
    private Label lblThoiGian;

    @FXML
    private Label lblThamGia;

    @FXML
    private Label lblPheDuyet;

    private ConferenceEntity conferenceEntity;
    private LocationEntity locationEntity;

    public ConferenceItemController(ConferenceEntity con, LocationEntity loc) {
        conferenceEntity = con;
        locationEntity = loc;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lblID.setText("" + conferenceEntity.getId());
        lblTen.setText(conferenceEntity.getName());
        lblDiaDiem.setText(locationEntity.getAddress());

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String dateStart = formatter.format(new Date(Long.parseLong(conferenceEntity.getTimeStartAt())));
        formatter = new SimpleDateFormat("HH:mm");
        String dateEnd = formatter.format(new Date(Long.parseLong(conferenceEntity.getTimeEndAt())));
        lblThoiGian.setText(dateStart+"->"+dateEnd);
        lblThamGia.setText("" + PartakerDAO.getNumberApproved(conferenceEntity.getId()));
        lblPheDuyet.setText("" + PartakerDAO.getNumberWaitApproved(conferenceEntity.getId()));
    }

    @FXML
    public void onAction_conConferenceItem(MouseEvent mouseEvent) throws IOException {
        System.out.println("onAction_conCardConference");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("EditConference.fxml"));
        loader.setController(new EditConferenceController(conferenceEntity, locationEntity.getId()));
        Parent root = loader.load();
        MenuController.getConPage.getChildren().setAll(root);
    }
}
