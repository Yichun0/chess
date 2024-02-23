package dataAccess.Model;
    public class AuthData {
        // constructor
        String username;
        String authToken;

        public AuthData(String username, String authToken) {
            this.username = username;
            this.authToken = authToken;
        }

        public String getUsername() {
            return username;
        }

        public String getAuthToken() {
            return authToken;
        }

    }
