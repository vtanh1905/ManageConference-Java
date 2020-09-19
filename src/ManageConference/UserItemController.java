package ManageConference;

import dao.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserItemController implements Initializable {
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

    public UserItemController(int ID, String hoTen, String taiKhoan, String email, int lock) {
        this.ID = ID;
        HoTen = hoTen;
        TaiKhoan = taiKhoan;
        Email = email;
        isLocked = lock;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lblID.setText("" + ID);
        lblHoTen.setText(HoTen);
        lblEmail.setText(Email);
        lblTaiKhoan.setText(TaiKhoan);
        if(isLocked == 1){
            btnTrangThai.setStyle("-fx-border-color: red; -fx-background-color: #EBE8F9; -fx-border-radius: 30");
            btnTrangThai.setText("Chặn");
        }
    }

    public void onAction_btnTrangThai(ActionEvent actionEvent) throws IOException {
        System.out.println("onAction_conCardConference");
        if(isLocked == 1){
            UserDAO.changeBlockUser(ID, 0);
            isLocked = 0;
            btnTrangThai.setStyle("-fx-border-color: #2A73FF; -fx-background-color: #EBE8F9; -fx-border-radius: 30");
            btnTrangThai.setText("Không chặn");
        }else {
            UserDAO.changeBlockUser(ID, 1);
            isLocked = 1;
            btnTrangThai.setStyle("-fx-border-color: red; -fx-background-color: #EBE8F9; -fx-border-radius: 30");
            btnTrangThai.setText("Chặn");
        }
    }
}
