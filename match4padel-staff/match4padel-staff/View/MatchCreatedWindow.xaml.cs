using System.Windows;

namespace match4padel_staff.View
{
    /// <summary>
    /// Lógica de interacción para MatchCreatedWindow.xaml
    /// </summary>
    public partial class MatchCreatedWindow : Window
    {
        public MatchCreatedWindow()
        {
            InitializeComponent();
        }
        private void Button_Click(object sender, RoutedEventArgs e)
        {
            DialogResult = true;
        }
    }
}
