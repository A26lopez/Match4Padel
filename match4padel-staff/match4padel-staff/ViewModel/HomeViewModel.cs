using match4padel_staff.Model;
using match4padel_staff.Service;
using match4padel_staff.Util;
using match4padel_staff.View;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using static System.Windows.Forms.VisualStyles.VisualStyleElement.StartPanel;

namespace match4padel_staff.ViewModel
{

    public class HomeViewModel : BaseViewModel
    {
        private readonly CourtService courtService;
        public Court court1 { get; set; }
        public Court court2 { get; set; }
        public Court court3 { get; set; }
        public Court court4 { get; set; }
        public Court court5 { get; set; }
        public Court court6 { get; set; }
        public Court court7 { get; set; }
        public Court court8 { get; set; }

        public HomeViewModel()
        {
            courtService = new CourtService();
            LoadAllCourtsAsync();
        }

        private async Task LoadAllCourtsAsync()
        {
            var result = await courtService.getAllCourtsAsync();
            if (result is List<Court> courtList)
            {
                court1 = courtList[0];
                court2 = courtList[1];
                court3 = courtList[2];
                court4 = courtList[3];
                court5 = courtList[4];
                court6 = courtList[5];
                court7 = courtList[6];
                court8 = courtList[7];
            }
        }
    }
}
