package ManageConference;

import com.sun.deploy.panel.RuleSetViewerDialog;
import entities.UserEntity;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

public class MenuController implements Initializable {

    @FXML
    private Button btnTrangChu;
    private static Button getBtnTrangChu;

    @FXML
    private Button btnDangNhap;
    private static Button getBtnDangNhap;

    @FXML
    private Button btnDangKy;
    private static Button getBtnDangKy;

    @FXML
    private Button btnCaNhan;
    private static Button getBtnCaNhan;

    @FXML
    private Button btnThongKe;
    private static Button getBtnThongKe;

    @FXML
    private Button btnNguoiDung;
    private static Button getBtnNguoiDung;

    @FXML
    private Button btnHoiNghi;
    private static Button getBtnHoiNghi;

    @FXML
    private Button btnThoat;
    private static Button getBtnThoat;

    @FXML
    private Label txtName;
    public static Label getTxtName;

    @FXML
    private AnchorPane conPage;
    public static AnchorPane getConPage;

    @FXML
    private AnchorPane conLoading;
    public static AnchorPane getConLoading;

    @FXML
    private VBox conButtons;
    public static VBox getConButtons;

    @FXML
    private AnchorPane nodeRoot;
    public static AnchorPane getNodeRoot;

    public static UserEntity userLogined;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getConPage = conPage;
        getConLoading = conLoading;
        getBtnTrangChu = btnTrangChu;
        getBtnDangKy = btnDangKy;
        getBtnDangNhap = btnDangNhap;
        getBtnCaNhan = btnCaNhan;
        getBtnThongKe = btnThongKe;
        getBtnNguoiDung = btnNguoiDung;
        getBtnHoiNghi = btnHoiNghi;
        getBtnThoat = btnThoat;
        getTxtName = txtName;
        getConButtons = conButtons;
        getNodeRoot = nodeRoot;

        getConButtons.getChildren().remove(4,8);

        try {
            conPage.getChildren().setAll((AnchorPane) FXMLLoader.load(getClass().getResource("Conferences.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void onAction_btnTrangChu(ActionEvent actionEvent) throws IOException {
        System.out.println("btnTrangChu");
        conPage.getChildren().setAll((AnchorPane) FXMLLoader.load(getClass().getResource("Conferences.fxml")));
    }

    public void onAction_btnDangNhap(ActionEvent actionEvent) throws IOException {
        System.out.println("btnDangNhap");
        conPage.getChildren().setAll((AnchorPane) FXMLLoader.load(getClass().getResource("Login.fxml")));
    }

    public void onAction_btnDangKy(ActionEvent actionEvent) throws IOException {
        System.out.println("btnDangKy");
        conPage.getChildren().setAll((AnchorPane) FXMLLoader.load(getClass().getResource("Register.fxml")));
    }

    public void onAction_btnThoat(ActionEvent actionEvent) throws IOException {
        System.out.println("btnThoat");
        Platform.exit();
        System.exit(0);
    }

    public void onAction_btnNguoiDung(ActionEvent actionEvent) throws IOException {
        System.out.println("onAction_btnNguoiDung");
        conPage.getChildren().setAll((AnchorPane) FXMLLoader.load(getClass().getResource("ManageUser.fxml")));
    }

    public void onAction_btnHoiNghi(ActionEvent actionEvent) throws IOException {
        System.out.println("onAction_btnHoiNghi");
        conPage.getChildren().setAll((AnchorPane) FXMLLoader.load(getClass().getResource("ManageConferences.fxml")));
    }

    public void onAction_btnCaNhan(ActionEvent actionEvent) throws IOException {
        System.out.println("onAction_btnHoiNghi");
        conPage.getChildren().setAll((AnchorPane) FXMLLoader.load(getClass().getResource("Profile.fxml")));
    }

    public void onAction_btnThongKe(ActionEvent actionEvent) throws IOException {
        System.out.println("onAction_btnThongKe");
        conPage.getChildren().setAll((AnchorPane) FXMLLoader.load(getClass().getResource("Statistic.fxml")));
    }

    public static void reloadMenu(UserEntity userEntity) throws IOException {
        userLogined = userEntity;
        getBtnDangKy.setVisible(false);
        getBtnDangNhap.setVisible(false);
        getTxtName.setText(userLogined.getFullname());

        //ROLE = USER
        if(userLogined.getRole().equals("USER")){
            getConButtons.getChildren().add(4, getBtnCaNhan);
            getConButtons.getChildren().add(5, getBtnThongKe);
            getConPage.getChildren().setAll((AnchorPane) FXMLLoader.load(MenuController.class.getResource("Conferences.fxml")));
        }

        //ROLE = ADMIN
        if(userLogined.getRole().equals("ADMIN")){
            getConButtons.getChildren().remove(3);
            getConButtons.getChildren().add(3, getBtnNguoiDung);
            getConButtons.getChildren().add(4, getBtnHoiNghi);
            getConPage.getChildren().setAll((AnchorPane) FXMLLoader.load(MenuController.class.getResource("ManageUser.fxml")));
        }
    }

    public static void reloadMenuLogout() throws IOException {
        userLogined = null;
        getBtnDangKy.setVisible(true);
        getBtnDangNhap.setVisible(true);
        getTxtName.setText("Khách");
        getConButtons.getChildren().remove(4);
        getConButtons.getChildren().remove(4);

        getConPage.getChildren().setAll((AnchorPane) FXMLLoader.load(MenuController.class.getResource("Conferences.fxml")));
    }
}
