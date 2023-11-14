package stream.alchemists.simplenoteapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import stream.alchemists.simplenoteapp.R
import stream.alchemists.simplenoteapp.ui.viewmodel.LoginViewModel

class LoginFragment : Fragment() {
    private lateinit var loginButton: Button

    private val viewModel: LoginViewModel by viewModels { LoginViewModel.Factory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        loginButton = view.findViewById(R.id.login_button)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (viewModel.isLogged()) {
            val action = LoginFragmentDirections.navigateFromLoginToNotesList()
            Navigation.findNavController(view).navigate(action)
        }

        loginButton.setOnClickListener {
            viewModel.login()
            val action = LoginFragmentDirections.navigateFromLoginToNotesList()
            Navigation.findNavController(view).navigate(action)
        }
    }
}
