using match4padel_staff.ViewModel;
using System.Windows.Controls;

namespace match4padel_staff.View
{
    /// <summary>
    /// Lógica de interacción para HomeView.xaml
    /// </summary>
    public partial class HomeView : UserControl
    {
        private HomeViewModel viewModel;
        public HomeView()
        {
            InitializeComponent();
            viewModel = new HomeViewModel();
            DataContext = viewModel;
        }
    }
}
