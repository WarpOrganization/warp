package net.warpgame.launcher;

/**
 * @author Hubertus
 *         Created 10.03.17
 */
public class AuthData {

    private String authToken;
    private String nickname;

    public AuthData() {
    }

    public AuthData(String authToken, String nickname) {
        this.authToken = authToken;
        this.nickname = nickname;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
