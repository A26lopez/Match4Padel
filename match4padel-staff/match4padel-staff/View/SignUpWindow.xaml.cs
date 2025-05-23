using match4padel_staff.ViewModel;
using System.Windows;
using System.Windows.Controls;

namespace match4padel_staff.View
{
    /// <summary>
    /// Lógica de interacción para SignUpWindow.xaml
    /// </summary>
    public partial class SignUpWindow : Window
    {
        private readonly SignUpViewModel viewModel;
        public SignUpWindow()
        {
            InitializeComponent();
            viewModel = new SignUpViewModel();
            DataContext = viewModel;
        }

        private void PasswordBox_PasswordChanged(object sender, RoutedEventArgs e)
        {
            var passwordBox = sender as PasswordBox;
            viewModel.Password = passwordBox.Password;
        }

        private void ConfirmPasswordBox_PasswordChanged(object sender, RoutedEventArgs e)
        {
            var passwordBox = sender as PasswordBox;
            viewModel.ConfirmPassword = passwordBox.Password;
        }

        private void Button_Click(object sender, RoutedEventArgs e)
        {
            this.DialogResult = true;
        }
    }
}
