using match4padel_staff.ViewModel;
using System.Windows;
using System.Windows.Controls;

namespace match4padel_staff.Views
{
    /// <summary>
    /// Lógica de interacción para LoginView.xaml
    /// </summary>
    public partial class LoginView : Window
    {
        private LoginViewModel viewModel;
        public LoginView()
        {
            InitializeComponent();
            viewModel = new LoginViewModel();
            DataContext = viewModel;
        }

        private void PasswordBox_PasswordChanged(object sender, RoutedEventArgs e)
        {
            var passwordBox = sender as PasswordBox;
            viewModel.Password = passwordBox.Password;
            if (passwordBox.Password.Length == 0)
            {
                passwordBox.Tag = "Escribe tu contraseña...";
            }
            else
            {
                passwordBox.Tag = "";
            }
        }
    }
}
