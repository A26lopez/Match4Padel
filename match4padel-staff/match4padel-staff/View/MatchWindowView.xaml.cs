using System.Windows;

namespace match4padel_staff.View
{
    /// <summary>
    /// Lógica de interacción para MatchWindowView.xaml
    /// </summary>
    public partial class MatchWindowView : Window
    {
        public MatchWindowView()
        {
            InitializeComponent();
        }
        private void Cancel_Button_Click(object sender, RoutedEventArgs e)
        {
            this.DialogResult = false;
        }

        private void Acept_Button_Click(object sender, RoutedEventArgs e)
        {
            this.DialogResult = true;
        }
    }
}
