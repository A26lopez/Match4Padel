using CommunityToolkit.Mvvm.Input;
using match4padel_staff.Model;
using match4padel_staff.Service;
using match4padel_staff.View;
using PropertyChanged;
using System.Threading.Tasks;
using System.Windows.Input;

namespace match4padel_staff.ViewModel
{
    [AddINotifyPropertyChangedInterface]
    public class MenuViewModel : BaseViewModel
    {
        public object CurrentView { get; set; }
        public string LoggedUsername => SessionService.Instance.Username;
        public ICommand HomeCommand { get; }
        public ICommand ReservateCourtCommand { get; }
        public ICommand MyReservationsCommand { get; }
        public ICommand CreateMatchCommand { get; }
        public ICommand MyMatchesCommand { get; }
        public ICommand OpenMatchesCommand { get; }
        public ICommand MyProfileCommand { get; }
        public ICommand ConfigurationCommand { get; }

        public MenuViewModel()
        {
            CurrentView = new HomeView();
            HomeCommand = new RelayCommand(OpenHomeView);
            ReservateCourtCommand = new RelayCommand(OpenReservateCourtView);
            MyReservationsCommand = new RelayCommand(OpenMyReservationsView);
            CreateMatchCommand = new RelayCommand(OpenCreateMatchView);
            MyMatchesCommand = new RelayCommand(OpenMyMatchesView);
            OpenMatchesCommand = new RelayCommand(OpenOpenMatchesView);
            MyProfileCommand = new RelayCommand(OpenMyProfile);
            
        }

        private void OpenHomeView()
        {
            CurrentView = new HomeView();
        }

        private void OpenReservateCourtView()
        {
            CurrentView = new ReservateCourtView();
        }

        private void OpenMyReservationsView()
        {
            CurrentView = new MyReservationsView();
        }

        private void OpenCreateMatchView()
        {
            CurrentView = new CreateMatchView();
        }

        private void OpenMyMatchesView()
        {
            CurrentView = new MyMatchesView();
        }

        private void OpenOpenMatchesView()
        {
            CurrentView = new OpenMatchesView();
        }

        private void OpenMyProfile()
        {
            CurrentView = new MyProfileView();
        }
    }
}
