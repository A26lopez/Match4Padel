using match4padel_staff.ViewModel;
using System.Windows;

namespace match4padel_staff.View
{
    /// <summary>
    /// Lógica de interacción para MenuView.xaml
    /// </summary>
    public partial class MenuView : Window
    {
        private MenuViewModel viewModel;
        public MenuView()
        {
            InitializeComponent();
            viewModel = new MenuViewModel();
            DataContext = viewModel;
        }

    }
}
