using System.Windows;

namespace match4padel_staff.View
{
    /// <summary>
    /// Lógica de interacción para ReservationWindowView.xaml
    /// </summary>
    public partial class ReservationWindowView : Window
    {
        public ReservationWindowView()
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
