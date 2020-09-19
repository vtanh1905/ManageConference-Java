package ManageConference;

import dao.ConferenceDAO;
import dao.LocationDAO;
import entities.ConferenceEntity;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class ListConferencesController implements Initializable {

    @FXML
    private VBox conListConferences;

    private List<ConferenceEntity> conferenceEntityList;

    public ListConferencesController(List<ConferenceEntity> conferenceEntityList) {
        this.conferenceEntityList = conferenceEntityList;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            for (int i = 0; i < conferenceEntityList.size(); ++i){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ConferenceItemMin.fxml"));
                loader.setController(new ConferenceItemMinController(conferenceEntityList.get(i), LocationDAO.getById(conferenceEntityList.get(i).getLocationId()).getAddress()));

                Parent root = loader.load();
                conListConferences.getChildren().add(root);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
