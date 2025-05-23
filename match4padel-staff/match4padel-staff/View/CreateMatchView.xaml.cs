using match4padel_staff.ViewModel;
using System.Windows.Controls;

namespace match4padel_staff.View
{
    /// <summary>
    /// Lógica de interacción para CreateMatchView.xaml
    /// </summary>
    public partial class CreateMatchView : UserControl
    {
        private readonly CreateMatchViewModel viewModel;
        public CreateMatchView()
        {
            InitializeComponent();
            viewModel = new CreateMatchViewModel();
            DataContext = viewModel;
        }
    }
}
