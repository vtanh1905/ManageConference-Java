package ManageConference;

import dao.PartakerDAO;
import dao.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserApproveItemController implements Initializable {
    @FXML
    private Button btnTrangThai;

    @FXML
    private Label lblID;

    @FXML
    private  Label lblHoTen;

    @FXML
    private  Label lblTaiKhoan;

    @FXML
    private  Label lblEmail;

    private int ID;
    private String HoTen;
    private String TaiKhoan;
    private String Email;
    private int isLocked;
    private int conferenceID;
    private int isConferenceStarted;

    public UserApproveItemController(int ID, String hoTen, String taiKhoan, String email, int lock, int ConferenceID, int IsConferenceStarted) {
        this.ID = ID;
        HoTen = hoTen;
        TaiKhoan = taiKhoan;
        Email = email;
        isLocked = lock;
        conferenceID = ConferenceID;
        isConferenceStarted = IsConferenceStarted;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lblID.setText("" + ID);
        lblHoTen.setText(HoTen);
        lblEmail.setText(Email);
        lblTaiKhoan.setText(TaiKhoan);
        if(isLocked == 2){
            btnTrangThai.setStyle("-fx-border-color: #2A73FF; -fx-background-color: #EBE8F9; -fx-border-radius: 30");
            btnTrangThai.setText("Đã duyệt");
        }
    }

    public void onAction_btnTrangThai(ActionEvent actionEvent) throws IOException {
        System.out.println("onAction_conCardConference");
        if(isConferenceStarted == 1){
            return;
        }

        if(isLocked == 1){
            int result = PartakerDAO.updateStatus(ID, conferenceID, 2);
            if(result == 0){
                MyModal.AlterERROR("Số lượng người tham gia đã đủ");
                return;
            }
            isLocked = 2;
            btnTrangThai.setStyle("-fx-border-color: #2A73FF; -fx-background-color: #EBE8F9; -fx-border-radius: 30");
            btnTrangThai.setText("Đã duyệt");
        }else {
            int result = UserDAO.changeBlockUser(ID, 1);
            if(result == 0){
                MyModal.AlterERROR("Số lượng người tham gia đã đủ");
                return;
            }
            isLocked = 1;
            PartakerDAO.updateStatus(ID, conferenceID, 1);
            btnTrangThai.setStyle("-fx-border-color: red; -fx-background-color: #EBE8F9; -fx-border-radius: 30");
            btnTrangThai.setText("Chưa duyệt");
        }
    }
}
