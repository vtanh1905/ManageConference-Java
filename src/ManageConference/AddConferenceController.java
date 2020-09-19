package ManageConference;

import dao.ConferenceDAO;
import dao.LocationDAO;
import entities.ConferenceEntity;
import entities.LocationEntity;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.util.Callback;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;

public class AddConferenceController implements Initializable {

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
    private ImageView imgConference;

    @FXML
    private ScrollPane nodeRoot;

    private String idLocationSelected;

    private File fileChoose;

    private List<LocationEntity> locationEntityList;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        locationEntityList = LocationDAO.getAll();
        ObservableList observableListDiaDiem = FXCollections.observableArrayList(locationEntityList);
        LocationEntity tempChon = new LocationEntity();
        tempChon.setName("Chọn");
        LocationEntity tempKhac = new LocationEntity();
        tempKhac.setName("Khác");
        observableListDiaDiem.add(0,tempChon);
        observableListDiaDiem.add(tempKhac);
        cbbDiaDiem.setItems(FXCollections.observableArrayList(observableListDiaDiem));
        cbbDiaDiem.getSelectionModel().select(0);
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
        dpNgay.setValue(LocalDate.now().plusDays(1));
        System.out.println(dpNgay.getValue().toString());

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
        cbbTGBatDau.getSelectionModel().select(0);
        cbbTGBatDau.valueProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue ov, String oldValue, String newValue) {
                if(cbbTGBatDau.getSelectionModel().getSelectedIndex() >= cbbTGKetThuc.getSelectionModel().getSelectedIndex()){
                    cbbTGKetThuc.getSelectionModel().select(cbbTGBatDau.getSelectionModel().getSelectedIndex());
                }
            }
        });

        cbbTGKetThuc.setItems(observableListKT);
        cbbTGKetThuc.getItems().remove(0);
        cbbTGKetThuc.getSelectionModel().select(0);
        cbbTGKetThuc.valueProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue ov, String oldValue, String newValue) {
                if(cbbTGKetThuc.getSelectionModel().getSelectedIndex() <= cbbTGBatDau.getSelectionModel().getSelectedIndex()){
                    cbbTGBatDau.getSelectionModel().select(cbbTGKetThuc.getSelectionModel().getSelectedIndex());
                }
            }
        });
    }

    public void onAction_btnXacNhan(ActionEvent actionEvent) throws IOException, ParseException {
        System.out.println("onAction_btnXacNhan");

        // Bat Loi
        if(txtTen.getText().equals("") || txtGioiThieu.getText().equals("") || txtChiTiet.getText().equals("") || txtTenDiaDiem.getText().equals("") || txtDiaChi.getText().equals("") || txtSucChua.getText().equals("") || cbbDiaDiem.getSelectionModel().getSelectedItem().getName().equals("Chọn")){
            MyModal.AlterERROR("Vui lòng điền đẩy đủ thông tin!");
            return;
        }

        ConferenceEntity conferenceEntity = new ConferenceEntity();
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

        String nameImage = UUID.randomUUID().toString() +".png";
        conferenceEntity.setImage(nameImage);

        int result = ConferenceDAO.save(conferenceEntity);
        if(result == 0){
            MyModal.AlterERROR("Đã có hội nghị diễn ra vào thời gian này");
            return;
        }
        Files.copy(Paths.get(fileChoose.getPath()), Paths.get("src//images//conferences//"+ nameImage));
        MyModal.AlterSuccessfully("Thêm Thành Công!");
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
}
