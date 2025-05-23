using System.Windows;

namespace match4padel_staff.View
{
    /// <summary>
    /// Lógica de interacción para ReservationCreatedWindow.xaml
    /// </summary>
    public partial class ReservationCreatedWindow : Window
    {
        public ReservationCreatedWindow()
        {
            InitializeComponent();
        }

        private void Button_Click(object sender, RoutedEventArgs e)
        {
            DialogResult = true;
        }
    }
}
