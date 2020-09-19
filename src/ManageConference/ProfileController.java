package ManageConference;

import dao.UserDAO;
import entities.UserEntity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {

    @FXML
    private TextField txtHoTen;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtMatKhauCu;

    @FXML
    private TextField txtMatKhauMoi;

    @FXML
    private TextField txtXacNhan;

    @FXML
    private Button btnThayDoi;

    @FXML
    private Button btnXacNhan;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        txtHoTen.setText(MenuController.userLogined.getFullname());
        txtEmail.setText(MenuController.userLogined.getEmail());
    }

    public void onAction_btnThayDoi(ActionEvent actionEvent) throws IOException {
        System.out.println("onAction_btnThayDoi");
        if(txtHoTen.getText().equals("")){
            MyModal.AlterERROR("Vui lòng điền thông tin!");
            return;
        }

        MenuController.userLogined.setFullname(txtHoTen.getText());
        MenuController.getTxtName.setText(txtHoTen.getText());
        UserDAO.update(MenuController.userLogined);
        MyModal.AlterSuccessfully("Thay đổi thành công!");
    }

    public void onAction_btnXacNhan(ActionEvent actionEvent) throws IOException, NoSuchAlgorithmException {
        System.out.println("onAction_btnXacNhan");
        if(txtMatKhauCu.getText().equals("") || txtMatKhauMoi.getText().equals("") || txtXacNhan.getText().equals("")){
            MyModal.AlterERROR("Vui lòng điền thông tin!");
            return;
        }

        if(!MenuController.userLogined.getPassword().equals(UserDAO.hashMD5Password(txtMatKhauCu.getText()))){
            MyModal.AlterERROR("Mật khẩu không chính xác!");
            return;
        }

        if(!txtMatKhauMoi.getText().equals(txtXacNhan.getText())){
            MyModal.AlterERROR("Xác nhận mật khẩu không khớp!");
            return;
        }

        if(txtMatKhauMoi.getText().length() < 5){
            MyModal.AlterERROR("Mật khẩu quá ngắn!");
            return;
        }

        MenuController.userLogined.setPassword(UserDAO.hashMD5Password(txtMatKhauMoi.getText()));

        UserDAO.update(MenuController.userLogined);
        txtMatKhauCu.setText("");
        txtMatKhauMoi.setText("");
        txtXacNhan.setText("");

        MyModal.AlterSuccessfully("Thay đổi thành công!");
    }

    public void onAction_btnDangXuat(ActionEvent actionEvent) throws IOException, NoSuchAlgorithmException {
        System.out.println("onAction_btnDangXuat");
        MenuController.reloadMenuLogout();
    }
}
