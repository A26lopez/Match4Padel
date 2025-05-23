using System.Windows;

namespace match4padel_staff.View
{
    /// <summary>
    /// Lógica de interacción para JoinedMatchWindow.xaml
    /// </summary>
    public partial class JoinedMatchWindow : Window
    {
        public JoinedMatchWindow()
        {
            InitializeComponent();
        }
        private void Button_Click(object sender, RoutedEventArgs e)
        {
            DialogResult = true;
        }
    }
}
