using match4padel_staff.ViewModel;
using System.Windows.Controls;

namespace match4padel_staff.View
{
    /// <summary>
    /// Lógica de interacción para OpenMatchesView.xaml
    /// </summary>
    public partial class OpenMatchesView : UserControl
    {
        private readonly OpenMatchesViewModel viewModel;
        public OpenMatchesView()
        {
            InitializeComponent();
            viewModel = new OpenMatchesViewModel();
            DataContext = viewModel;
        }
    }
}
