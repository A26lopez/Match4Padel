using match4padel_staff.Service;
using match4padel_staff.Util;
using match4padel_staff.View;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Input;

namespace match4padel_staff.ViewModel
{
    class MenuViewModel : BaseViewModel
    {
        public object CurrentView { get; set; }
        public string Username { get; set; }
        public ICommand HomeCommand { get; }
        public ICommand ReservateCourtCommand { get; }
        public ICommand MyReservationsCommand { get; }
        public ICommand CreateMatchLoginCommand { get; }
        public ICommand MyMatchesCommand { get; }
        public ICommand OpenMatchesCommand { get; }
        public ICommand MyProfileCommand { get; }
        public ICommand ConfigurationCommand { get; }

        public MenuViewModel()
        {
            CurrentView = new HomeView();
            HomeCommand = new RelayCommand(OpenHomeView);
            MyReservationsCommand = new RelayCommand(OpenMyReservationsView);
            MyMatchesCommand = new RelayCommand(OpenMyMatchesView);
        }

        private void OpenHomeView(Object obj)
        {
            CurrentView = new HomeView();
        }

        private void OpenMyReservationsView(Object obj)
        {
            CurrentView = new MyReservationsView();
        }

        private void OpenMyMatchesView(Object obj)
        {
            CurrentView = new MyMatchesView();
        }
    }
}
