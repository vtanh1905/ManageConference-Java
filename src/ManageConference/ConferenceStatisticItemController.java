package ManageConference;

import dao.LocationDAO;
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

public class ConferenceStatisticItemController implements Initializable {

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

    public ConferenceStatisticItemController(ConferenceEntity con) {
        conferenceEntity = con;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Load confercen
        lblTen.setText(conferenceEntity.getName());
        lblDiaDiem.setText(LocationDAO.getByConferenceId(conferenceEntity.getId()).getAddress());
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String dateStart = formatter.format(new Date(Long.parseLong(conferenceEntity.getTimeStartAt())));
        formatter = new SimpleDateFormat("HH:mm");
        String dateEnd = formatter.format(new Date(Long.parseLong(conferenceEntity.getTimeEndAt())));
        lblThoiGian.setText(dateStart+"->"+dateEnd);
        if(System.currentTimeMillis() >= Long.parseLong(conferenceEntity.getTimeStartAt())){
            lblThamGia.setText("Xong");
        }else{
            lblThamGia.setText("Chưa");
        }
        lblPheDuyet.setText(PartakerDAO.getStatus(MenuController.userLogined.getId(),conferenceEntity.getId()) == 2 ? "Duyệt" : "Chờ");
    }

    @FXML
    public void onAction_ConferenceStatisticItem(MouseEvent mouseEvent) throws IOException {
        System.out.println("onAction_ConferenceItemMix");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ConferenceDetailStatistic.fxml"));
        loader.setController(new ConferenceDetailStatisticController(conferenceEntity, "123123123123"));
        Parent root = loader.load();
        MenuController.getConPage.getChildren().setAll(root);
    }

}
