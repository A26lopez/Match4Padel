using CommunityToolkit.Mvvm.Input;
using match4padel_staff.Model;
using match4padel_staff.Service;
using match4padel_staff.View;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading.Tasks;
using System.Windows;
using Match = match4padel_staff.Model.Match;

namespace match4padel_staff.ViewModel
{
    class OpenMatchesViewModel : BaseViewModel
    {
        public ObservableCollection<Match> Matches { get; set; }
        public IAsyncRelayCommand joinMatchCommand { get; }

        private readonly MatchService matchService;

        public OpenMatchesViewModel()
        {
            matchService = new MatchService();
            Matches = new ObservableCollection<Match>();
            joinMatchCommand = new AsyncRelayCommand<Match>(joinMatch);
            LoadMatches();
        }

        private async Task LoadMatches()
        {
            var result = await matchService.getOpenMatches();
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

            var result = await matchService.joinMatch(match.Id, SessionService.Instance.UserId);

            if (result is Match m)
            {
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
