package net.warpgame.launcher;

/**
 * @author Hubertus
 *         Created 10.03.17
 */
public class AuthResponse {
    private AuthData authData;
    private int statusCode;

    AuthResponse(){
    }

    AuthResponse(AuthData authData, int statusCode){

        this.authData = authData;
        this.statusCode = statusCode;
    }

    public AuthData getAuthData() {
        return authData;
    }

    public void setAuthData(AuthData authData) {
        this.authData = authData;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
