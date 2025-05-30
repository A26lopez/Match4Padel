using CommunityToolkit.Mvvm.Input;
using match4padel_staff.Model;
using match4padel_staff.Service;
using match4padel_staff.View;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Threading.Tasks;
using System.Windows;
using Match = match4padel_staff.Model.Match;

namespace match4padel_staff.ViewModel
{
    class OpenMatchesViewModel : BaseViewModel
    {
        public ObservableCollection<Match> Matches { get; set; }
        public IAsyncRelayCommand joinMatchCommand { get; }
        public Match SelectedMatch { get; set; }
        public string SelectedLevel { get; set; }
        public List<string> PaymentMethods { get; set; }
        public string SelectedMethod { get; set; }


        public OpenMatchesViewModel()
        {
            Matches = new ObservableCollection<Match>();
            joinMatchCommand = new AsyncRelayCommand<Match>(joinMatch);
            PaymentMethods = new List<string> { "Tarjeta", "Efectivo", "ApplePay" };
            LoadMatches();
        }

        private async Task LoadMatches()
        {
            var result = await MatchService.GetOpenMatches();
            if (result is List<Match> matchList)
            {
                foreach (var m in matchList)
                {
                    if (!m.IsUserJoined)
                    {
                        Matches.Add(m);
                    }
                }
            }

        }

        private async Task joinMatch(Match match)
        {
            if (match == null) return;
            SelectedMatch = match;
            SelectedMethod = PaymentMethods[0];
            var window = new JoinMatchWindowView();
            window.DataContext = this;
            bool? windowResult = window.ShowDialog();
            if (windowResult == true)
            {
                var result = await MatchService.JoinMatch(match.Id, SessionService.Instance.UserId);

                if (result is Match m)
                {
                    var paymentResult = await PaymentService.GetPaymentByReservationId(match.Reservation.Id);
                    if (paymentResult is List<Payment> payments)
                    {
                        foreach (Payment p in payments)
                        {
                            if (p.User.Id == SessionService.Instance.UserId && p.Status == "PENDING")
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
                                await PaymentService.CompletePayment(p.Id, SelectedMethod);

                                var okWindow = new JoinedMatchWindow();
                                okWindow.ShowDialog();
                                if (m.Player1 != null)
                                {
                                    match.Player1 = m.Player1;
                                }
                                if (m.Player2 != null)
                                {
                                    match.Player2 = m.Player2;
                                }
                                if (m.Player2 != null)
                                {
                                    match.Player3 = m.Player3;
                                }
                                match.Status = m.Status;
                            }
                        }

                    }
                }
            }


        }
    }
}
