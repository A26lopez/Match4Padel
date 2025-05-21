using CommunityToolkit.Mvvm.Input;
using match4padel_staff.Model;
using match4padel_staff.Service;
using match4padel_staff.View;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;

namespace match4padel_staff.ViewModel
{
    class CreateMatchViewModel : BaseViewModel
    {
        public IAsyncRelayCommand CreateMatchCommand { get; set; }
        public List<string> PaymentMethods { get; set; }
        public List<string> Levels { get; set; }
        public string SelectedMethod { get; set; }
        public string SelectedLevel { get; set; }
        public Court SelectedCourt { get; set; }
        public DateTime SelectedDate { get; set; }
        public TimeSpan SelectedHour { get; set; }
        public DateTime MinDate { get; set; } = DateTime.Today;
        public ObservableCollection<TimeSpan> freeHours { get; set; }
        public ObservableCollection<Court> Courts { get; set; }

        private readonly ReservationService reservationService;
        private readonly CourtService courtService;
        private readonly MatchService matchService;
        private readonly PaymentService paymentService;


        public CreateMatchViewModel()
        {
            reservationService = new ReservationService();
            courtService = new CourtService();
            paymentService = new PaymentService();
            matchService = new MatchService();

            PaymentMethods = new List<string> { "Tarjeta", "Efectivo", "ApplePay" };
            Levels = new List<string> { "Principiante", "Intermedio", "Avanzado", "Experto " };
            SelectedMethod = PaymentMethods.First();
            SelectedLevel = Levels.First();

            CreateMatchCommand = new AsyncRelayCommand<Court>(CreateMatch);
            Courts = new ObservableCollection<Court>();
            freeHours = new ObservableCollection<TimeSpan>();
            SelectedDate = DateTime.Today;
            InitializeAsync();
        }
        private async Task InitializeAsync()
        {
            await LoadFreeHours();

            while (freeHours.Count == 0)
            {
                SelectedDate = SelectedDate.AddDays(1);
                await LoadFreeHours();
            }

            if (freeHours.Any())
            {
                SelectedHour = freeHours.First();
            }
        }

        private async Task LoadFreeHours()
        {
            freeHours.Clear();
            var result = await reservationService.getFreeHoursByDate(SelectedDate);
            if (result is List<TimeSpan> hours)
            {
                foreach (TimeSpan h in hours)
                {
                    freeHours.Add(h);
                }
            }
        }

        async void OnSelectedDateChanged()
        {
            if (SelectedDate != null)
            {

                await LoadFreeHours();
                SelectedHour = default;

                if (freeHours.Any())
                    SelectedHour = freeHours.First();
            }
        }

        async void OnSelectedHourChanged()
        {
            if (SelectedHour != null)
            {
                Courts.Clear();
                var result = await courtService.getCourtsByDateAndTime(SelectedDate, SelectedHour);
                if (result is List<Court> courts)
                {
                    foreach (Court c in courts)
                    {
                        Courts.Add(c);
                    }
                }
            }
        }

        public async Task CreateMatch(Court court)
        {
            if (court != null)
            {
                SelectedCourt = court;
            }

            var window = new MatchWindowView
            {
                Owner = Application.Current.MainWindow,
                DataContext = this
            };

            bool? windowResult = window.ShowDialog();
            if (windowResult == true)
            {
                string level = "";
                switch (SelectedLevel)
                {
                    case "Principiante":
                        level = "BEGINNER";
                        break;
                    case "Intermedio":
                        level = "INTERMEDIATE";
                        break;
                    case "Avanzado":
                        level = "ADVANCED";
                        break;
                    case "Experto":
                        level = "EXPERT";
                        break;

                }
                var reservationResult = await matchService.CreateMatch(SessionService.Instance.UserId, SelectedCourt.Id, level, SelectedDate, SelectedHour);
                if (reservationResult is Match match)
                {
                    var paymentResult = await paymentService.getPaymentByReservationId(match.Reservation.Id);
                    if (paymentResult is List<Payment> payments)
                    {
                        foreach (Payment p in payments)
                        {
                            if (p.User.Id == SessionService.Instance.UserId)
                            {
                                switch (SelectedMethod)
                                {
                                    case "Tarjeta":
                                        SelectedMethod = "CARD";
                                        break;
                                    case "Efectivo":
                                        SelectedMethod = "CASH";
                                        break;
                                    case "ApplePay":
                                        SelectedMethod = "APPLE_PAY";
                                        break;

                                }
                                await paymentService.completePayment(p.Id, SelectedMethod);

                                var okWindow = new MatchCreatedWindow
                                {
                                    Owner = Application.Current.MainWindow
                                };
                                okWindow.ShowDialog();
                                OnSelectedHourChanged();
                            }
                        }
                    }
                }
            }
        }
    }
}

