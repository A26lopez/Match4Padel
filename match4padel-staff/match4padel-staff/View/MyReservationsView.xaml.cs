using match4padel_staff.ViewModel;
using System.Windows.Controls;

namespace match4padel_staff.View
{
    /// <summary>
    /// Lógica de interacción para MyReservationsView.xaml
    /// </summary>
    public partial class MyReservationsView : UserControl
    {
        private MyReservationsViewModel viewModel;
        public MyReservationsView()
        {
            InitializeComponent();
            viewModel = new MyReservationsViewModel();
            DataContext = viewModel;
        }
    }
}
