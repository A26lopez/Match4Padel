using match4padel_staff.ViewModel;
using System.Windows.Controls;

namespace match4padel_staff.View
{
    /// <summary>
    /// Lógica de interacción para ReservateCourtView.xaml
    /// </summary>
    public partial class ReservateCourtView : UserControl
    {
        private readonly ReservateCourtViewModel viewModel;
        public ReservateCourtView()
        {
            InitializeComponent();
            viewModel = new ReservateCourtViewModel();
            DataContext = viewModel;
        }
    }
}
