using System.Windows;

namespace match4padel_staff.View
{
    /// <summary>
    /// Lógica de interacción para UserCreatedWindow.xaml
    /// </summary>
    public partial class UserCreatedWindow : Window
    {
        public UserCreatedWindow()
        {
            InitializeComponent();
        }

        private void Button_Click(object sender, RoutedEventArgs e)
        {
            DialogResult = true;
        }
    }
}
