using match4padel_staff.ViewModel;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;

namespace match4padel_staff.View
{
    /// <summary>
    /// Lógica de interacción para MyProfileView.xaml
    /// </summary>
    public partial class MyProfileView : UserControl
    {
        private readonly MyProfileViewModel viewModel;
        public MyProfileView()
        {
            viewModel = new MyProfileViewModel();
            DataContext = viewModel;
            
            InitializeComponent();
        }

        private void PasswordBox_PasswordChanged(object sender, RoutedEventArgs e)
        {
            var passwordBox = sender as PasswordBox;
            viewModel.User.AccountSecurity.Password = passwordBox.Password;
        }

        private void ConfirmPasswordBox_PasswordChanged(object sender, RoutedEventArgs e)
        {
            var passwordBox = sender as PasswordBox;
            viewModel.ConfirmPassword = passwordBox.Password;
        }
    }
}
