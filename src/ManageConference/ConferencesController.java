package ManageConference;

import dao.ConferenceDAO;
import entities.ConferenceEntity;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ConferencesController implements Initializable {
    @FXML
    private ImageView imgGrid;

    @FXML
    private ImageView imgList;

    @FXML
    private BorderPane bpaneContent;

    @FXML
    private TextField txtPage;

    @FXML
    private TextField txtTimKiem;

    private String viewType = "GRID";

    private List<ConferenceEntity> conferenceEntityList;

    private int indexPage;
    private int maxPage;
    private String textSearch = "";

    private void LoadContent(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource( viewType.equals("GRID") ? "GridConferences.fxml": "ListConferences.fxml"));
            if(viewType.equals("GRID")){
                loader.setController(new GridConferencesController(conferenceEntityList));
            }else{
                loader.setController(new ListConferencesController(conferenceEntityList));
            }
            Parent root = loader.load();
            bpaneContent.setCenter(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        imgGrid.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (viewType.equals("GRID")){
                    return;
                }
                Class<?> clazz = this.getClass();
                imgGrid.setImage(new Image(clazz.getResourceAsStream("/images/icons8-grid-view-26-active.png")));
                imgList.setImage(new Image(clazz.getResourceAsStream("/images/icons8-bulleted-list-50.png")));
                viewType = "GRID";
                LoadContent();
                event.consume();
            }
        });

        imgList.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (viewType.equals("LIST")){
                    return;
                }
                Class<?> clazz = this.getClass();
                imgGrid.setImage(new Image(clazz.getResourceAsStream("/images/icons8-grid-view-26.png")));
                imgList.setImage(new Image(clazz.getResourceAsStream("/images/icons8-bulleted-list-50-active.png")));
                viewType = "LIST";
                LoadContent();
                event.consume();
            }
        });

        txtTimKiem.textProperty().addListener((observable, oldValue, newValue) -> {
            textSearch = newValue;
            conferenceEntityList = ConferenceDAO.getAll(9, (indexPage - 1) * 10, textSearch);
            LoadContent();
        });

        indexPage = 1;
        maxPage = (ConferenceDAO.size(textSearch) / 10) + 1;
        txtPage.setText(" " + indexPage + "/" + maxPage);

        conferenceEntityList = ConferenceDAO.getAll(9, (indexPage - 1) * 10, textSearch);
        LoadContent();
    }

    public void onAction_btnLeft(ActionEvent actionEvent) throws IOException {
        System.out.println("onAction_btnLeft");
        if(indexPage == 1){
            return;
        }
        --indexPage;
        txtPage.setText(" " + indexPage + "/" + maxPage);
        conferenceEntityList = ConferenceDAO.getAll(9, (indexPage - 1) * 10, textSearch);
        LoadContent();
    }

    public void onAction_btnRight(ActionEvent actionEvent) throws IOException {
        System.out.println("onAction_btnRight");
        if(indexPage == maxPage){
            return;
        }
        ++indexPage;
        txtPage.setText(" " + indexPage + "/" + maxPage);
        conferenceEntityList = ConferenceDAO.getAll(9, (indexPage - 1) * 10, textSearch);
        LoadContent();
    }
}
