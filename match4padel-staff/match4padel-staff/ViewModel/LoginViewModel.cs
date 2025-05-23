using CommunityToolkit.Mvvm.Input;
using match4padel_staff.Model;
using match4padel_staff.Service;
using match4padel_staff.View;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Input;

namespace match4padel_staff.ViewModel
{
    public class LoginViewModel : BaseViewModel
    {
        private readonly LoginService loginService;
        public string Username { get; set; }
        public string Password { get; set; }
        public string ErrorMessage { get; set; }
        public IAsyncRelayCommand LoginCommand { get; }
        public ICommand RememberPasswordCommand { get; }
        public ICommand OpenSignUpWindowCommand { get; }
        public ICommand SignUpCommand { get; }

        public LoginViewModel()
        {
            Username = "";
            Password = "";
            ErrorMessage = "";
            loginService = new LoginService();
            LoginCommand = new AsyncRelayCommand(Login);
            OpenSignUpWindowCommand = new RelayCommand(OpenSignUpWindow);
        }

        private async Task Login()
        {
            var result = await loginService.LoginAsync(Username, Password);
            if (result is LoginResponse login)
            {
                SessionService.Instance.SetSession(login.Token, login.UserId);
                OpenMainWindow();
                var loginWindow = Application.Current.MainWindow;
                loginWindow.Close();

            }
            else if (result is ErrorResponse error)
            {
                ErrorMessage = $"❗{error.Error}";
            }
        }

        private static void OpenMainWindow()
        {
            var menuView = new MenuView();
            menuView.Show();
        }

        private void OpenSignUpWindow()
        {
            var signUpWindow = new SignUpWindow();
            signUpWindow.Owner = Application.Current.MainWindow;
            bool? windowResult = signUpWindow.ShowDialog();
            if (windowResult == true)
            {
                var userCreatedWindow = new UserCreatedWindow();
                userCreatedWindow.Owner = Application.Current.MainWindow;
                userCreatedWindow.ShowDialog();
            }

        }

    }

}

