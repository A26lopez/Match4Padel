using PropertyChanged;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Input;
using System.Windows;
using System.Xml.Linq;
using match4padel_staff.Service;
using System.Windows.Controls;
using match4padel_staff.Model;
using match4padel_staff.View;
using CommunityToolkit.Mvvm.Input;

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
        public ICommand SignUpCommand { get; }

        public LoginViewModel()
        {
            Username = "";
            Password = "";
            ErrorMessage = "";
            loginService = new LoginService();
            LoginCommand = new AsyncRelayCommand(Login);
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
                ErrorMessage = $"❗ {error.Error}";
            }
        }

        private static void OpenMainWindow()
        {
            var menuView = new MenuView();
            menuView.Show();
        }



    }

}

