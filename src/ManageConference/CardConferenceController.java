package ManageConference;

import dao.ConferenceDAO;
import entities.ConferenceEntity;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class CardConferenceController implements Initializable {
    @FXML
    private AnchorPane conCardConference;

    @FXML
    private Label lblTen;

    @FXML
    private Label lblDiaChi;

    @FXML
    private Label lblThoiGian;

    @FXML
    private ImageView imgConference;

    private ConferenceEntity conferenceEntity;
    private String address;


    public CardConferenceController(ConferenceEntity conferenceEntity1, String Address) {
        conferenceEntity = conferenceEntity1;
        address = Address;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(conferenceEntity.getName().length() > 18){
            lblTen.setText(conferenceEntity.getName().substring(0, 17) + "...");
        }else {
            lblTen.setText(conferenceEntity.getName());
        }

        lblDiaChi.setText(address);

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String dateStart = formatter.format(new Date(Long.parseLong(conferenceEntity.getTimeStartAt())));
        formatter = new SimpleDateFormat("HH:mm");
        String dateEnd = formatter.format(new Date(Long.parseLong(conferenceEntity.getTimeEndAt())));
        lblThoiGian.setText(dateStart + "->" + dateEnd);

        File temp = new File("src//images//conferences//"+ (conferenceEntity.getImage().equals("") ? "no-image.png" : conferenceEntity.getImage()));
        Image image = new Image(temp.toURI().toString());
        imgConference.setImage(image);
    }


//    public void setData(String ten, String diachi, String thoigian){
//        lblTen.setText(ten);
//        lblDiaChi.setText(diachi);
//        lblThoiGian.setText(thoigian);
//    }

    @FXML
    public void onAction_conCardConference(MouseEvent mouseEvent) throws IOException {
        System.out.println("onAction_conCardConference");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ConferenceDetail.fxml"));
        loader.setController(new ConferenceDetailController(conferenceEntity, address));
        Parent root = loader.load();
        MenuController.getConPage.getChildren().setAll(root);
    }

}
