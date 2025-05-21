using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Shapes;

namespace match4padel_staff.View
{
    /// <summary>
    /// Lógica de interacción para JoinMatchWindowView.xaml
    /// </summary>
    public partial class JoinMatchWindowView : Window
    {
        public JoinMatchWindowView()
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
