package pl.kopocinski.lukasz.lukaszkopocinski.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.kopocinski.lukasz.lukaszkopocinski.R;
import pl.kopocinski.lukasz.lukaszkopocinski.UserPreferences;
import pl.kopocinski.lukasz.lukaszkopocinski.Utils;


public class LoginFragment extends Fragment {

    @Bind(R.id.input_email)
    EditText inputEmail;

    @Bind(R.id.input_password)
    EditText inputPassword;

    @Bind(R.id.input_layout_email)
    TextInputLayout inputLayoutEmail;

    @Bind(R.id.input_layout_password)
    TextInputLayout inputLayoutPassword;

    @Bind(R.id.button_login)
    Button buttonLogin;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @OnClick(R.id.button_login)
    public void onButtonLoginClicked() {
        if (validateData()) {
            saveUserLoggedIn();
            hideKeyboard(getView());
            loadMainFragment();
        }
    }

    private boolean validateData() {
        if (!validateEmail()) {
            return false;
        }

        if (!validatePassword()) {
            return false;
        }

        return true;
    }

    private boolean validateEmail() {
        String email = inputEmail.getText().toString().trim();

        if (email.isEmpty()) {
            inputLayoutEmail.setError(getString(R.string.error_message_empty_field));
            return false;
        }

        if (!isValidEmail(email)) {
            inputLayoutEmail.setError(getString(R.string.error_message_invalid_email));
            return false;
        } else {
            inputLayoutEmail.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePassword() {
        String password = inputPassword.getText().toString();

        if (password.isEmpty()) {
            inputLayoutPassword.setError(getString(R.string.error_message_empty_field));
            return false;
        }

        if (!isValidPassword(password)) {
            inputLayoutPassword.setError(getString(R.string.error_message_password_incorrect_form));
            return false;
        } else {
            inputLayoutPassword.setErrorEnabled(false);
        }

        return true;
    }

    private boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidPassword(String password) {
        final int MIN_REQUIRED_LENGTH = 8;

        if (password.length() < MIN_REQUIRED_LENGTH) {
            return false;
        }
        if (password.equals(password.toLowerCase())) {        // sprawdza duże litery
            return false;
        }
        if (password.equals(password.toUpperCase())) {        // sprawdza małe litery
            return false;
        }


        String NUMBER_PATTERN = "[0-9]+";                   // sprawdza występowanie cyfry
        Pattern pattern = Pattern.compile(NUMBER_PATTERN);
        Matcher matcher = pattern.matcher(password);

        return matcher.find();
    }

    private void saveUserLoggedIn() {
        UserPreferences.getInstance(getContext()).saveLoginStatus(UserPreferences.USER_LOGGED_IN);
    }

    private void loadMainFragment() {
        Utils.fragmentTransactionSetup(MainFragment.newInstance(), getFragmentManager(),
                MainFragment.class.getName());
    }

    public void hideKeyboard(View view){
        InputMethodManager keyboard = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        keyboard.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
