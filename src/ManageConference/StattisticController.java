package ManageConference;

import dao.LocationDAO;
import dao.PartakerDAO;
import dao.UserDAO;
import entities.ConferenceEntity;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class StattisticController implements Initializable {

    @FXML
    private Label lblTongCong;

    @FXML
    private Label lblTongPheDuyet;

    @FXML
    private Label lblTongChuaPheDuyet;

    @FXML
    private VBox conListConferences;

    @FXML
    private TextField txtPage;

    @FXML
    private ChoiceBox cbTrangThai;

    @FXML
    private ChoiceBox cbSapXep;

    @FXML
    private TextField txtTimKiem;

    private int indexPage;
    private int maxPage;
    private int filter_type;
    private int sort_type;
    private String textSearch = "";

    List<ConferenceEntity> conferenceEntityList;

    public void LoadData(){
        conListConferences.getChildren().clear();
        conferenceEntityList = PartakerDAO.getConferenceOfUser(MenuController.userLogined.getId(), 10, (indexPage - 1) * 10,filter_type, sort_type, textSearch);
        try{
            for (int i = 0; i < conferenceEntityList.size(); ++i){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ConferenceStatisticItem.fxml"));
                loader.setController(new ConferenceStatisticItemController(conferenceEntityList.get(i)));
                Parent root = loader.load();
                conListConferences.getChildren().add(root);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }

        indexPage = 1;
        maxPage = (conferenceEntityList.size() / 10) + 1;
        txtPage.setText(" " + indexPage + "/" + maxPage);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Load thông kê
        int TongPheDuyet = PartakerDAO.getUserApproved(MenuController.userLogined.getId());
        int TongChuaPheDuyet = PartakerDAO.getUserWaitApproved(MenuController.userLogined.getId());
        lblTongCong.setText("" + (TongPheDuyet + TongChuaPheDuyet));
        lblTongPheDuyet.setText("" + TongPheDuyet);
        lblTongChuaPheDuyet.setText("" + TongChuaPheDuyet);

        cbTrangThai.setItems(FXCollections.observableArrayList("Tất cả", "Đã duyệt", "Chưa duyệt"));
        cbTrangThai.setValue("Tất cả");
        filter_type = 0;
        cbTrangThai.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                String temp = (String) cbTrangThai.getItems().get((Integer) number2);
                if(temp.equals("Tất cả")){
                    filter_type = 0;
                    indexPage = 1;
                    maxPage = (conferenceEntityList.size() / 10) + 1;
                    txtPage.setText(" " + indexPage + "/" + maxPage);
                }else if(temp.equals("Đã duyệt")){
                    filter_type = 2;
                    indexPage = 1;
                    maxPage = (TongPheDuyet / 10) + 1;
                    txtPage.setText(" " + indexPage + "/" + maxPage);
                }else if(temp.equals("Chưa duyệt")){
                    filter_type = 1;
                    indexPage = 1;
                    maxPage = (TongChuaPheDuyet / 10) + 1;
                    txtPage.setText(" " + indexPage + "/" + maxPage);
                }

                txtPage.setText(" " + (indexPage + 1) + "/" + (maxPage + 1));
                LoadData();
            }
        });

        cbSapXep.setItems(FXCollections.observableArrayList("A->Z", "Z->A"));
        cbSapXep.setValue("A->Z");
        sort_type= 0;
        cbSapXep.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                String Temp = (String)cbSapXep.getItems().get((Integer) number2);
                if(Temp.equals("A->Z")){
                    sort_type = 0;
                }else {
                    sort_type = 1;
                }
                LoadData();
            }
        });

        txtTimKiem.textProperty().addListener((observable, oldValue, newValue) -> {
            textSearch = newValue;
            LoadData();
        });

        LoadData();
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
