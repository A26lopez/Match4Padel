using match4padel_staff.ViewModel;
using System.Windows.Controls;

namespace match4padel_staff.View
{
    /// <summary>
    /// Lógica de interacción para MyMatchesView.xaml
    /// </summary>


    public partial class MyMatchesView : UserControl
    {
        private readonly MyMatchesViewModel viewModel;
        public MyMatchesView()
        {
            InitializeComponent();
            viewModel = new MyMatchesViewModel();
            DataContext = viewModel;
        }
    }
}
