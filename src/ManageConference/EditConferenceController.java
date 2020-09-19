package ManageConference;

import dao.ConferenceDAO;
import dao.LocationDAO;
import dao.PartakerDAO;
import entities.ConferenceEntity;
import entities.LocationEntity;
import entities.UserEntity;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.util.Callback;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class EditConferenceController implements Initializable {

    @FXML
    private TextField txtTen;

    @FXML
    private TextArea txtGioiThieu;

    @FXML
    private TextArea txtChiTiet;

    @FXML
    private ComboBox<LocationEntity> cbbDiaDiem;

    @FXML
    private ComboBox cbbTGBatDau;

    @FXML
    private ComboBox cbbTGKetThuc;

    @FXML
    private DatePicker dpNgay;

    @FXML
    private TextField txtTenDiaDiem;

    @FXML
    private TextField txtSucChua;

    @FXML
    private TextField txtDiaChi;

    @FXML
    private TextField txtPage;
    @FXML
    private VBox conListUser;

    @FXML
    private Button btnXacNhan;

    @FXML
    private ImageView imgConference;

    @FXML
    private ScrollPane nodeRoot;

    private int indexPage;
    private int maxPage;

    private String idLocationSelected;
    private ConferenceEntity conferenceEntity;

    private int isConferenceStarted = 0;

    private List<LocationEntity> locationEntityList;

    private File fileChoose;

    public EditConferenceController(ConferenceEntity conferenceEntity, String idLocationSelected) {
        this.idLocationSelected = idLocationSelected;
        this.conferenceEntity = conferenceEntity;

        if(System.currentTimeMillis() >= Long.parseLong(conferenceEntity.getTimeStartAt())){
            isConferenceStarted = 1;
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        txtTen.setText("" + conferenceEntity.getName());
        txtGioiThieu.setText(conferenceEntity.getShortDescription());
        txtChiTiet.setText(conferenceEntity.getDetailDescription());

        File temp = new File("src//images//conferences//"+ (conferenceEntity.getImage().equals("") ? "no-image.png" : conferenceEntity.getImage()));
        Image image = new Image(temp.toURI().toString());
        imgConference.setImage(image);

        locationEntityList = LocationDAO.getAll();
        ObservableList observableListDiaDiem = FXCollections.observableArrayList(locationEntityList);
        LocationEntity tempKhac = new LocationEntity();
        tempKhac.setName("Khác");
        observableListDiaDiem.add(tempKhac);
        cbbDiaDiem.setItems(FXCollections.observableArrayList(observableListDiaDiem));


        for (int i = 0; i < locationEntityList.size(); ++i){
            if(locationEntityList.get(i).getId().equals(idLocationSelected)){
                cbbDiaDiem.getSelectionModel().select(i);
                txtTenDiaDiem.setText(locationEntityList.get(i).getName());
                txtSucChua.setText("" + locationEntityList.get(i).getMaxPeople());
                txtDiaChi.setText(locationEntityList.get(i).getAddress());
                break;
            }
        }

        cbbDiaDiem.valueProperty().addListener(new ChangeListener<LocationEntity>() {
            @Override public void changed(ObservableValue ov, LocationEntity oldValue, LocationEntity newValue) {
                if(newValue.getName().equals("Khác") || newValue.getName().equals("Chọn")){
                    txtTenDiaDiem.setText("");
                    txtSucChua.setText("");
                    txtDiaChi.setText("");
                    txtTenDiaDiem.setDisable(false);
                    txtSucChua.setDisable(false);
                    txtDiaChi.setDisable(false);
                }else{
                    idLocationSelected = newValue.getId();
                    txtTenDiaDiem.setText(newValue.getName());
                    txtSucChua.setText("" + newValue.getMaxPeople());
                    txtDiaChi.setText(newValue.getAddress());
                    txtTenDiaDiem.setDisable(true);
                    txtSucChua.setDisable(true);
                    txtDiaChi.setDisable(true);

                }

            }
        });

        // Create a day cell factory
        Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>()
        {
            public DateCell call(final DatePicker datePicker)
            {
                return new DateCell()
                {
                    @Override
                    public void updateItem(LocalDate item, boolean empty)
                    {
                        // Must call super
                        super.updateItem(item, empty);

                        // Show Weekends in blue color
                        if (item.isBefore(LocalDate.now().plusDays(1))) {
                            setDisable(true);
                            setStyle("-fx-background-color: #ffc0cb;");
                        }
                    }
                };
            }
        };
        dpNgay.setDayCellFactory(dayCellFactory);
        dpNgay.setValue(Instant.ofEpochMilli(Long.parseLong(conferenceEntity.getTimeStartAt()))
                .atZone(ZoneId.systemDefault())
                .toLocalDate());

        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
//        String dateStart = formatter.format(new Date(Long.parseLong(conferenceEntity.getTimeStartAt())));
//        formatter = new SimpleDateFormat("HH:mm");
//        String dateEnd = formatter.format(new Date(Long.parseLong(conferenceEntity.getTimeEndAt())));

        // Set Time
        ObservableList observableListBD = FXCollections.observableArrayList();
        ObservableList observableListKT = FXCollections.observableArrayList();
        for (int i =0; i < 24; ++i){
            observableListBD.add((i < 10 ? "0" + i : i) + ":00" );
            observableListBD.add((i < 10 ? "0" + i : i) + ":30" );

            observableListKT.add((i < 10 ? "0" + i : i) + ":00" );
            observableListKT.add((i < 10 ? "0" + i : i) + ":30" );
        }
        cbbTGBatDau.setItems(observableListBD);
        cbbTGBatDau.getItems().remove(47);
        cbbTGBatDau.getSelectionModel().select(formatter.format(new Date(Long.parseLong(conferenceEntity.getTimeStartAt()))));
        cbbTGBatDau.valueProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue ov, String oldValue, String newValue) {
                if(cbbTGBatDau.getSelectionModel().getSelectedIndex() >= cbbTGKetThuc.getSelectionModel().getSelectedIndex()){
                    cbbTGKetThuc.getSelectionModel().select(cbbTGBatDau.getSelectionModel().getSelectedIndex());
                }
            }
        });

        cbbTGKetThuc.setItems(observableListKT);
        cbbTGKetThuc.getItems().remove(0);
        cbbTGKetThuc.getSelectionModel().select(formatter.format(new Date(Long.parseLong(conferenceEntity.getTimeEndAt()))));
        cbbTGKetThuc.valueProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue ov, String oldValue, String newValue) {
                if(cbbTGKetThuc.getSelectionModel().getSelectedIndex() <= cbbTGBatDau.getSelectionModel().getSelectedIndex()){
                    cbbTGBatDau.getSelectionModel().select(cbbTGKetThuc.getSelectionModel().getSelectedIndex());
                }
            }
        });


        // Phe Duyet
        List<UserEntity> userEntityListParkter = PartakerDAO.getPartakers(conferenceEntity.getId(), 10, 0);
        for (int i = 0; i < userEntityListParkter.size(); ++i){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("UserApproveItem.fxml"));
                loader.setController(new UserApproveItemController(userEntityListParkter.get(i).getId(), userEntityListParkter.get(i).getFullname(), userEntityListParkter.get(i).getUsername(), userEntityListParkter.get(i).getEmail(), PartakerDAO.getStatus(userEntityListParkter.get(i).getId(), conferenceEntity.getId()), conferenceEntity.getId(), isConferenceStarted ));
                Parent root = loader.load();
                conListUser.getChildren().add(root);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        indexPage = 1;
        maxPage = (userEntityListParkter.size() / 10) + 1;
        txtPage.setText(" " + indexPage + "/" + maxPage);

        if(isConferenceStarted == 1){
            txtTen.setDisable(true);
            txtGioiThieu.setDisable(true);
            txtChiTiet.setDisable(true);
            cbbDiaDiem.setDisable(true);
            dpNgay.setDisable(true);
            cbbTGBatDau.setDisable(true);
            cbbTGKetThuc.setDisable(true);
            btnXacNhan.setVisible(false);
        }
    }

    public void onAction_btnXacNhan(ActionEvent actionEvent) throws IOException, ParseException {
        System.out.println("onAction_btnXacNhan");

        // Bat Loi
        if(txtTen.getText().equals("") || txtGioiThieu.getText().equals("") || txtChiTiet.getText().equals("") || txtTenDiaDiem.getText().equals("") || txtDiaChi.getText().equals("") || txtSucChua.getText().equals("") || cbbDiaDiem.getSelectionModel().getSelectedItem().getName().equals("Chọn")){
            MyModal.AlterERROR("Vui lòng điền đẩy đủ thông tin!");
            return;
        }

        ConferenceEntity conferenceEntity = new ConferenceEntity();
        conferenceEntity.setId(this.conferenceEntity.getId());
        conferenceEntity.setName(txtTen.getText());
        conferenceEntity.setShortDescription(txtGioiThieu.getText());
        conferenceEntity.setDetailDescription(txtChiTiet.getText());
        conferenceEntity.setTimeStartAt("" + new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(dpNgay.getValue().toString() + " " + cbbTGBatDau.getSelectionModel().getSelectedItem().toString()).getTime());
        conferenceEntity.setTimeEndAt("" + new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(dpNgay.getValue().toString() + " " + cbbTGKetThuc.getSelectionModel().getSelectedItem().toString()).getTime());

        // process Location
        if(cbbDiaDiem.getSelectionModel().getSelectedItem().getName().equals("Khác")){
            String idLocation = UUID.randomUUID().toString();
            LocationEntity locationEntity = new LocationEntity();
            locationEntity.setName(txtTenDiaDiem.getText());
            locationEntity.setAddress(txtDiaChi.getText());
            locationEntity.setMaxPeople(Integer.valueOf(txtSucChua.getText()));
            locationEntity.setId(idLocation);
            LocationDAO.save(locationEntity);
            conferenceEntity.setLocationId(idLocation);
        }else{
            conferenceEntity.setLocationId(idLocationSelected);
        }
        String nameImage="";
        if (fileChoose != null) {
            nameImage = UUID.randomUUID().toString() +".png";
            conferenceEntity.setImage(nameImage);
        }

        int result =  ConferenceDAO.update(conferenceEntity);
        if(result == 0){
            MyModal.AlterERROR("Đã có hội nghị diễn ra vào thời gian này");
            return;
        }

        if (fileChoose != null) {
            Files.copy(Paths.get(fileChoose.getPath()), Paths.get("src//images//conferences//"+ nameImage));
        }


        MyModal.AlterSuccessfully("Thay đổi thành Công!");
        MenuController.getConPage.getChildren().setAll((AnchorPane) FXMLLoader.load(getClass().getResource("ManageConferences.fxml")));
    }

    public void onAction_btnImage(ActionEvent actionEvent) throws IOException, ParseException {
        System.out.println("onAction_btnImage");
        FileChooser fileChooser = new FileChooser();
        fileChoose = fileChooser.showOpenDialog(nodeRoot.getScene().getWindow());
        if (fileChoose != null) {
            Image image = new Image(fileChoose.toURI().toString());
            imgConference.setImage(image);
        }
    }

    public void onAction_btnLeft(ActionEvent actionEvent) throws IOException {
        System.out.println("onAction_btnLeft");
        if(indexPage == 1){
            return;
        }
        --indexPage;
        txtPage.setText(" " + indexPage + "/" + maxPage);
        List<UserEntity> userEntityListParkter = PartakerDAO.getPartakers(conferenceEntity.getId(), 10, (indexPage - 1) * 10);
        for (int i = 0; i < userEntityListParkter.size(); ++i){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("UserApproveItem.fxml"));
                loader.setController(new UserApproveItemController(userEntityListParkter.get(i).getId(), userEntityListParkter.get(i).getFullname(), userEntityListParkter.get(i).getUsername(), userEntityListParkter.get(i).getEmail(), PartakerDAO.getStatus(userEntityListParkter.get(i).getId(), conferenceEntity.getId()), conferenceEntity.getId(), isConferenceStarted ));
                Parent root = loader.load();
                conListUser.getChildren().add(root);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void onAction_btnRight(ActionEvent actionEvent) throws IOException {
        System.out.println("onAction_btnRight");
        if(indexPage == maxPage){
            return;
        }
        ++indexPage;
        txtPage.setText(" " + indexPage + "/" + maxPage);
        List<UserEntity> userEntityListParkter = PartakerDAO.getPartakers(conferenceEntity.getId(), 10, (indexPage - 1) * 10);
        for (int i = 0; i < userEntityListParkter.size(); ++i){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("UserApproveItem.fxml"));
                loader.setController(new UserApproveItemController(userEntityListParkter.get(i).getId(), userEntityListParkter.get(i).getFullname(), userEntityListParkter.get(i).getUsername(), userEntityListParkter.get(i).getEmail(), PartakerDAO.getStatus(userEntityListParkter.get(i).getId(), conferenceEntity.getId()), conferenceEntity.getId(), isConferenceStarted ));
                Parent root = loader.load();
                conListUser.getChildren().add(root);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
