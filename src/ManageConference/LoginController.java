package ManageConference;

import dao.UserDAO;
import entities.UserEntity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private TextField txtTaiKhoan;
    @FXML
    private PasswordField txtMatKhau;
    @Override
    public void initialize(URL location, ResourceBundle resources) { }
    public void onAction_btnXacNhan(ActionEvent actionEvent) throws IOException {
        if(txtTaiKhoan.getText().length() == 0 || txtMatKhau.getText().length() == 0){
            MyModal.AlterERROR("Vui lòng điền thông tin!");
            return;
        }
        UserEntity userEntity =  UserDAO.login(txtTaiKhoan.getText(), txtMatKhau.getText());
        if(userEntity == null || userEntity.getUsername().length() ==0){
            MyModal.AlterERROR("Tài khoản hoặc mật khẩu không chính xác!");
            return;
        }

        if(userEntity.getIsLocked() == 1){
            MyModal.AlterERROR("Tài khoản đã bị khóa!");
            return;
        }

        MenuController.reloadMenu(userEntity);
        MyModal.AlterSuccessfully("Đăng nhập thành công!");
    }

}
