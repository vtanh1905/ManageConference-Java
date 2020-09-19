package ManageConference;

import dao.LocationDAO;
import entities.ConferenceEntity;
import entities.LocationEntity;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class GridConferencesController implements Initializable {
    @FXML
    private GridPane gridCardConference;

    private List<ConferenceEntity> conferenceEntityList;

    public GridConferencesController(List<ConferenceEntity> conferenceEntityList) {
        this.conferenceEntityList = conferenceEntityList;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            for (int i = 0; i < conferenceEntityList.size(); ++i){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("CardConference.fxml"));
                loader.setController(new CardConferenceController(conferenceEntityList.get(i), LocationDAO.getById(conferenceEntityList.get(i).getLocationId()).getAddress()));
                Parent root = loader.load();
                gridCardConference.add(root, i % 3, i / 3);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
