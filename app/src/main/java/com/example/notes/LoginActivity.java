package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
    public void zaloguj(){
            /*signInRequest = BeginSignInRequest.builder()
    .setGoogleIdTokenRequestOptions(GoogleIdTokenRequestOptions.builder()
            .setSupported(true)
            // Your server's client ID, not your Android client ID.
            .setServerClientId(getString(R.string.default_web_client_id))
            // Only show accounts previously used to sign in.
            .setFilterByAuthorizedAccounts(true)
            .build())
    .build();*/
    /*private FirebaseAuth mAuth;
// ...
// Initialize Firebase Auth
mAuth = FirebaseAuth.getInstance();*/
    /*@Override
public void onStart() {
    super.onStart();
    // Check if user is signed in (non-null) and update UI accordingly.
    FirebaseUser currentUser = mAuth.getCurrentUser();
    updateUI(currentUser);
}*/
    /*SignInCredential googleCredential = oneTapClient.getSignInCredentialFromIntent(data);
String idToken = googleCredential.getGoogleIdToken();
if (idToken !=  null) {
    // Got an ID token from Google. Use it to authenticate
    // with Firebase.
    AuthCredential firebaseCredential = GoogleAuthProvider.getCredential(idToken, null);
    mAuth.signInWithCredential(firebaseCredential)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithCredential:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        updateUI(user);
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithCredential:failure", task.getException());
                        updateUI(null);
                    }
                }
            });
}*/
        //FirebaseAuth.getInstance().signOut();
    }
}