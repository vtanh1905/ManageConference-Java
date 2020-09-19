package ManageConference;

import dao.PartakerDAO;
import entities.ConferenceEntity;
import entities.PartakerEntity;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class ConferenceDetailController implements Initializable {
    @FXML
    private Label lblTen;

    @FXML
    private Text lblGioiThieu;

    @FXML
    private Text lblChiTiet;

    @FXML
    private Text lblDiaChi;

    @FXML
    private Text lblThoiGian;

    @FXML
    private Button btnDangKy;

    @FXML
    private ImageView imgConference;

    private ConferenceEntity conferenceEntity;
    private String address;

    public ConferenceDetailController(ConferenceEntity conferenceEntity1, String Address) {
        conferenceEntity = conferenceEntity1;
        address = Address;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lblTen.setText(conferenceEntity.getName());
        lblGioiThieu.setText(conferenceEntity.getShortDescription());
        lblChiTiet.setText(conferenceEntity.getDetailDescription());
        lblDiaChi.setText(address);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String dateStart = formatter.format(new Date(Long.parseLong(conferenceEntity.getTimeStartAt())));
        formatter = new SimpleDateFormat("HH:mm");
        String dateEnd = formatter.format(new Date(Long.parseLong(conferenceEntity.getTimeEndAt())));
        lblThoiGian.setText(dateStart + "->" + dateEnd);

        File temp = new File("src//images//conferences//"+ (conferenceEntity.getImage().equals("") ? "no-image.png" : conferenceEntity.getImage()));
        Image image = new Image(temp.toURI().toString());
        imgConference.setImage(image);

        if(System.currentTimeMillis() >= Long.parseLong(conferenceEntity.getTimeStartAt())){
            btnDangKy.setText("Sự kiện đã diễn ra");
            btnDangKy.setDisable(true);
            return;
        }

        // Kiem tra đã dk chưa
        if(MenuController.userLogined != null){
            int status = PartakerDAO.getStatus(MenuController.userLogined.getId(), conferenceEntity.getId());
            if(status == 1){
                btnDangKy.setText("Chờ phê duyệt");
                btnDangKy.setDisable(true);
            }else if(status == 2){
                btnDangKy.setText("Đã đăng ký");
                btnDangKy.setDisable(true);
            }else if(status == -1){
                btnDangKy.setText("Đã bị từ chối");
                btnDangKy.setDisable(true);
            }
        }


    }

    public void onActiion_btnDangKy(ActionEvent actionEvent) throws IOException {
        if(MenuController.userLogined == null){
            int answer = MyModal.AlterChoices("Vui lòng đăng nhập!", "Đăng nhập", "Quay lại");
            if(answer == 1){
                MenuController.getConPage.getChildren().setAll((AnchorPane) FXMLLoader.load(getClass().getResource("Login.fxml")));
            }
            return;
        }
        PartakerEntity partakerEntity = new PartakerEntity();
        partakerEntity.setUserId(MenuController.userLogined.getId());
        partakerEntity.setConferenceId(conferenceEntity.getId());
        int result = PartakerDAO.save(partakerEntity);
        if(result == 1) {
            MyModal.AlterSuccessfully("Đăng ký thành công!");
            btnDangKy.setText("Chờ phê duyệt");
            btnDangKy.setDisable(true);
        }else{
            MyModal.AlterERROR("Số lượng người tham gia đã đủ!");
        }

    }
}
