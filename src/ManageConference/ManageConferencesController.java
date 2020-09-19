package ManageConference;

import dao.ConferenceDAO;
import dao.LocationDAO;
import dao.PartakerDAO;
import entities.ConferenceEntity;
import entities.LocationEntity;
import entities.PartakerEntity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class ManageConferencesController  implements Initializable {
    @FXML
    private Button btnThemHoiNghi;

    @FXML
    private VBox conListConferences;

    @FXML
    private TextField txtPage;

    private int indexPage;
    private int maxPage;

    public void onAction_btnThemHoiNghi(ActionEvent actionEvent) throws IOException {
        System.out.println("btnThemHoiNghi");
        MenuController.getConPage.getChildren().setAll((ScrollPane) FXMLLoader.load(getClass().getResource("AddConference.fxml")));
    }

    public void LoadData(){
        conListConferences.getChildren().clear();
        List<ConferenceEntity> conferenceEntityList = ConferenceDAO.getAll(10, (indexPage - 1) * 10);
        try {
            for (int i = 0; i < conferenceEntityList.size(); ++i){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ConferenceItem.fxml"));

                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                String dateStart = formatter.format(new Date(Long.parseLong(conferenceEntityList.get(i).getTimeStartAt())));
                formatter = new SimpleDateFormat("HH:mm");
                String dateEnd = formatter.format(new Date(Long.parseLong(conferenceEntityList.get(i).getTimeEndAt())));
                LocationEntity locationEntity = LocationDAO.getById(conferenceEntityList.get(i).getLocationId());
                loader.setController(new ConferenceItemController(conferenceEntityList.get(i), locationEntity));
                Parent root = loader.load();
                conListConferences.getChildren().add(root);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        indexPage = 1;
        LoadData();
        maxPage = (ConferenceDAO.size("") / 10) + 1;
        txtPage.setText(" " + indexPage + "/" + maxPage);
    }

    public void onAction_btnLeft(ActionEvent actionEvent) throws IOException {
        System.out.println("onAction_btnLeft");
        if(indexPage == 1){
            return;
        }
        --indexPage;
        txtPage.setText(" " + indexPage + "/" + maxPage);
        LoadData();
    }

    public void onAction_btnRight(ActionEvent actionEvent) throws IOException {
        System.out.println("onAction_btnRight");
        if(indexPage == maxPage){
            return;
        }
        ++indexPage;
        txtPage.setText(" " + indexPage + "/" + maxPage);
        LoadData();
    }
}
