using CommunityToolkit.Mvvm.Input;
using match4padel_staff.Model;
using match4padel_staff.Service;
using match4padel_staff.View;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Threading.Tasks;
using System.Windows;

namespace match4padel_staff.ViewModel
{
    public class MyMatchesViewModel : BaseViewModel
    {
        public ObservableCollection<Match> Matches { get; set; }
        public IAsyncRelayCommand CancelMatchCommand { get; }
        public IAsyncRelayCommand LeaveMatchCommand { get; }

        private readonly MatchService matchService;

        public MyMatchesViewModel()
        {
            matchService = new MatchService();
            Matches = new ObservableCollection<Match>();
            CancelMatchCommand = new AsyncRelayCommand<Match>(CancelMatch);
            LeaveMatchCommand = new AsyncRelayCommand<Match>(LeaveMatch);
            LoadMatches();
        }

        private async Task LoadMatches()
        {
            var result = await matchService.getMatchesByUserId(SessionService.Instance.UserId);
            if (result is List<Match> matchList)
            {
                foreach (var m in matchList)
                {
                    Matches.Add(m);
                }
            }
        }

        private async Task CancelMatch(Match match)
        {
            if (match == null) return;

            var boxResult = MessageBox.Show(
                "¿Estás seguro de que deseas cancelar este partido?",
                "Confirmar",
                MessageBoxButton.YesNo,
                MessageBoxImage.Warning
            );

            if (boxResult != MessageBoxResult.Yes)
                return;

            var result = await matchService.cancelMatchById(match.Id);

            if (result is Match m)
            {
                match.Reservation.Status = "CANCELLED";
                match.Status = "CLOSED";
            }
            else if (result is ErrorResponse e)
            {
                MessageBox.Show(e.Error, "Error", MessageBoxButton.OK, MessageBoxImage.Error);
            }
        }

        private async Task LeaveMatch(Match match)
        {
            if (match == null) return;

            var boxResult = MessageBox.Show(
                                "¿Estás seguro de que deseas abandonar este partido?",
                                "Confirmar",
                                MessageBoxButton.YesNo,
                                MessageBoxImage.Warning
                            );

            if (boxResult != MessageBoxResult.Yes)
                return;


            var result = await matchService.leaveMatch(match.Id, SessionService.Instance.UserId);

            if (result is Match m)
            {
                match.Player1 = null;
                match.Player2 = null;
                match.Player3 = null;

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
            else if (result is ErrorResponse e)
            {
                MessageBox.Show(e.Error, "Error", MessageBoxButton.OK, MessageBoxImage.Error);
            }
        }
    }

}

