package ManageConference;

import dao.UserDAO;
import entities.UserEntity;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ManageUserController implements Initializable {
    @FXML
    private VBox conListUser;

    @FXML
    private TextField txtPage;

    @FXML
    private ChoiceBox cbLoc;

    @FXML
    private ChoiceBox cbSapXep;

    @FXML
    private TextField txtTimKiem;

    private int indexPage;
    private int maxPage;
    private String filter_type;
    private String sort_type;
    private String textSearch = "";

    public void LoadData(){

        indexPage = 0;
        maxPage = (UserDAO.size2(filter_type.equals("Không chặn") ? 0 : (filter_type.equals("Đã chặn") ? 1 : -1), sort_type.equals("A->Z") ? 0 : 1, textSearch) - 1)/ 10;
        txtPage.setText(" " + (indexPage + 1) + "/" + (maxPage + 1));
        conListUser.getChildren().clear();
        List<UserEntity> userEntityList = UserDAO.getAllWithoutAdmin(10, indexPage * 10, filter_type.equals("Không chặn") ? 0 : (filter_type.equals("Đã chặn") ? 1 : -1), sort_type.equals("A->Z") ? 0 : 1, textSearch);
        try {
            for (int i = 0; i < userEntityList.size(); ++i){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("UserItem.fxml"));
                loader.setController(new UserItemController(userEntityList.get(i).getId(), userEntityList.get(i).getFullname(), userEntityList.get(i).getUsername(), userEntityList.get(i).getEmail(), userEntityList.get(i).getIsLocked()));
                Parent root = loader.load();
                conListUser.getChildren().add(0, root);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Init
        cbLoc.setItems(FXCollections.observableArrayList("Tất cả", "Không chặn", "Đã chặn"));
        cbLoc.setValue("Tất cả");
        filter_type = "Tất cả";
        cbLoc.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                filter_type = (String)cbLoc.getItems().get((Integer) number2);
                maxPage = (UserDAO.size(filter_type.equals("Không chặn") ? 0 : (filter_type.equals("Đã chặn") ? 1 : -1)) - 1) / 10;
                txtPage.setText(" " + (indexPage + 1) + "/" + (maxPage + 1));
                LoadData();
            }
        });

        cbSapXep.setItems(FXCollections.observableArrayList("A->Z", "Z->A"));
        cbSapXep.setValue("A->Z");
        sort_type= "A->Z";
        cbSapXep.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                sort_type = (String)cbSapXep.getItems().get((Integer) number2);
                LoadData();
            }
        });

        txtTimKiem.textProperty().addListener((observable, oldValue, newValue) -> {
            textSearch = newValue;
            LoadData();
        });

        txtTimKiem.setText("");
        LoadData();
    }

    public void onAction_btnLeft(ActionEvent actionEvent) throws IOException {
        System.out.println("onAction_btnLeft");
        if(indexPage == 0){
            return;
        }
        --indexPage;
        txtPage.setText(" " + (indexPage + 1) + "/" + (maxPage + 1));
        conListUser.getChildren().clear();
        List<UserEntity> userEntityList = UserDAO.getAllWithoutAdmin(10, indexPage * 10, filter_type.equals("Không chặn") ? 0 : (filter_type.equals("Đã chặn") ? 1 : -1), sort_type.equals("A->Z") ? 0 : 1, textSearch);
        try {
            for (int i = 0; i < userEntityList.size(); ++i){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("UserItem.fxml"));
                loader.setController(new UserItemController(userEntityList.get(i).getId(), userEntityList.get(i).getFullname(), userEntityList.get(i).getUsername(), userEntityList.get(i).getEmail(), userEntityList.get(i).getIsLocked()));
                Parent root = loader.load();
                conListUser.getChildren().add(0, root);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onAction_btnRight(ActionEvent actionEvent) throws IOException {
        System.out.println("onAction_btnRight");
        if(indexPage == maxPage){
            return;
        }
        ++indexPage;
        txtPage.setText(" " + (indexPage + 1) + "/" + (maxPage + 1));
        conListUser.getChildren().clear();
        List<UserEntity> userEntityList = UserDAO.getAllWithoutAdmin(10, indexPage * 10, filter_type.equals("Không chặn") ? 0 : (filter_type.equals("Đã chặn") ? 1 : -1), sort_type.equals("A->Z") ? 0 : 1, textSearch);
        try {
            for (int i = 0; i < userEntityList.size(); ++i){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("UserItem.fxml"));
                loader.setController(new UserItemController(userEntityList.get(i).getId(), userEntityList.get(i).getFullname(), userEntityList.get(i).getUsername(), userEntityList.get(i).getEmail(), userEntityList.get(i).getIsLocked()));
                Parent root = loader.load();
                conListUser.getChildren().add(0, root);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
