package ManageConference;

import entities.UserEntity;
import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import dao.UserDAO;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {
    @FXML
    private TextField txtHoTen;

    @FXML
    private TextField txtTaiKhoan;

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField txtMatKhau;

    @FXML
    private PasswordField txtXacNhan;

    @FXML
    private Button btnXacNhan;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void onAction_btnXacNhan(ActionEvent actionEvent) throws IOException {
        System.out.println("btnXacNhan");
        UserEntity userEntity = new UserEntity();

        if(txtHoTen.getText().length() == 0){
            MyModal.AlterERROR("Vui lòng nhập họ tên!");
            return;
        }

        if(txtTaiKhoan.getText().length() < 5){
            MyModal.AlterERROR("Tên tài khoản quá ngắn (hơn 5 ký tự)!");
            return;
        }

        if(txtEmail.getText().length() == 0){
            MyModal.AlterERROR("Vui lòng nhập email!");
            return;
        }

        if(txtMatKhau.getText().length() < 5){
            MyModal.AlterERROR("Mật khẩu quá ngắn (hơn 5 ký tự)!");
            return;
        }

        if(!txtMatKhau.getText().equals(txtXacNhan.getText())){
            MyModal.AlterERROR("Xác nhận mật khẩu không chính xác!");
            return;
        }

        userEntity.setFullname(txtHoTen.getText());
        userEntity.setUsername(txtTaiKhoan.getText());
        userEntity.setEmail(txtEmail.getText());
        userEntity.setPassword(txtMatKhau.getText());

        MenuController.getConLoading.setVisible(true);
       new Thread(() -> {
           try {
               Thread.sleep(500);
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
           Platform.runLater(() -> {
               if(UserDAO.save(userEntity) == 1){
                   MyModal.AlterSuccessfully("Đăng ký thành công!");
                   try {
                       MenuController.getConPage.getChildren().setAll((AnchorPane) FXMLLoader.load(getClass().getResource("Login.fxml")));
                   } catch (IOException e) {
                       e.printStackTrace();
                   }
               }else{
                   MyModal.AlterERROR("Tên tài khoản đã tồn tại!");
               }
               MenuController.getConLoading.setVisible(false);
           });
       }).start();

    }


}
