package pl.warp.test.launcher;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import pl.warp.launcher.*;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Hubertus
 *         Created 10.03.17
 */
public class LauncherController implements Initializable {

    private static final String ADDRESS = "warpdev.hubertus248.me";

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label infoLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void onSignIn(ActionEvent event) {
        AuthenticatedHttpsRemote remote = new AuthenticatedHttpsRemote(ADDRESS);
        remote.setDownloadPrefix("cdn");
        String username = usernameField.getText();
        String password = passwordField.getText();
        if (username.length() > 0 && password.length() > 0) {
            AuthResponse res = remote.getAuthToken(username, password);
            if (res.getStatusCode() == 200) {
                infoLabel.setText("Welcome, " + username);
                System.out.println(res.getAuthData().getAuthToken());
                BasicUpdater basicUpdater = new BasicUpdater();
                UpdateStatus status = new UpdateStatus();
                basicUpdater.update(new PathData("testdata", "."), true, status, remote);
            } else {
                infoLabel.setText("Error. Status code " + res.getStatusCode());
            }
        }
    }
}
