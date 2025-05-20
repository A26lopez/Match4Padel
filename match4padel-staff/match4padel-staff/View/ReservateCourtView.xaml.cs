using match4padel_staff.ViewModel;
using System;
using System.Collections.Generic;
using System.Globalization;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Controls.Primitives;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;

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
